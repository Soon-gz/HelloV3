package com.abings.baby.ui.album;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.abings.baby.R;
import com.abings.baby.ZSApp;
import com.abings.baby.ui.album.edit.AlbumEditActivity;
import com.abings.baby.ui.base.BaseTitleActivity;
import com.abings.baby.ui.main.MainActivity;
import com.hellobaby.library.Const;
import com.hellobaby.library.data.model.AlbumModel;
import com.hellobaby.library.utils.LogZS;
import com.hellobaby.library.utils.StringUtils;
import com.hellobaby.library.widget.BottomDialogUtils;
import com.hellobaby.library.widget.ProgressDialogHelper;
import com.hellobaby.library.widget.baseadapter.OnItemClickListeners;
import com.hellobaby.library.widget.baseadapter.ViewHolder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;


/**
 * Created by zwj on 2016/10/27.
 * description : 相册，瀑布流，需不需要下拉刷新？
 * 需要下拉刷新：刷新的什么数据？刷第一页吗？
 * <p>
 * 这里的规则定义：
 * 编辑：
 * 1.选择了添加，就不能删除
 * 2.开始选择了删除，就不能再添加
 */

public class AlbumActivity extends BaseTitleActivity implements AlbumMvpView, SwipeRefreshLayout.OnRefreshListener {

    @Inject
    AlbumPresenter presenter;

    @BindView(R.id.album_recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.album_tv_content)
    TextView tvContent;
    @BindView(R.id.album_ll_show)
    LinearLayout llShow;
    @BindView(R.id.album_swipeRefresh)
    SwipeRefreshLayout mSwipeRefreshLayout;

    //是否添加
    private boolean isLoadMore = false;


    private WaterFallAdapter mAdapter;
    private String commonId;//相册的id
    private AlbumModel albumModel;

    @Override
    protected void btnRightOnClick(View v) {

        String[] itemsRight = {"编辑", "删除该相册"};
        BottomDialogUtils.getBottomListDialog(bContext, itemsRight, new BottomDialogUtils.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, String item, long id) {
                if (position == 0) {
                    Intent intent = new Intent(bContext, AlbumEditActivity.class);
                    Bundle b = getIntent().getExtras();
                    List<WaterFallItem> waterFallItems = mAdapter.getDatas();
                    b.putParcelableArrayList(AlbumEditActivity.kImgList, (ArrayList<? extends Parcelable>) waterFallItems);
                    intent.putExtras(b);
                    startActivityForResult(intent, MainActivity.BabyFragmentEditResultCode);
                } else if (position == 1) {
                    delAlbum();
                }
            }
        });
    }

    /**
     * 删除相册
     */
    public void delAlbum() {
        String[] items = {"确定", "取消"};
        BottomDialogUtils.getBottomListDialog(bContext, items, new BottomDialogUtils.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, String item, long id) {
                if (position == 0) {
                    presenter.deleteAlbumById(albumModel);
                }
            }
        });
    }


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_album;
    }

    @Override
    protected void initDaggerInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initViewsAndEvents(@Nullable Bundle savedInstanceState) {

        setBtnLeftClickFinish();

        presenter.attachView(this);
        Bundle bundle = getIntent().getExtras();
        albumModel = (AlbumModel) bundle.getSerializable("AlbumModel");
        {
            TextView tvTime = (TextView) findViewById(R.id.lib_bottom_tv_time);
            TextView tvUserName = (TextView) findViewById(R.id.lib_bottom_tv_userName);
            tvTime.setText(albumModel.getLastmodifyTimeNYRHm());
            tvUserName.setText(albumModel.getUserName());
        }

        setTitleText(albumModel != null ? albumModel.getTitle() : null);
        if (albumModel.getContent().isEmpty()) {
            tvContent.setVisibility(View.GONE);
        } else {
            tvContent.setVisibility(View.VISIBLE);
            tvContent.setText(albumModel.getContent());
        }
        if (ZSApp.getInstance().getBabyModel().getIsCreator().equals(ZSApp.getInstance().getUserId())||albumModel.getUserId() == null || (albumModel.getUserId() != null && albumModel.getUserId().equals(ZSApp.getInstance().getUserId()))) {
            setBtnRightDrawableRes(R.drawable.title_more_black);
        }
        EventBus.getDefault().register(this);

        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent, R.color.colorPrimaryDark);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        StaggeredGridLayoutManager sgm = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        sgm.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        recyclerView.setLayoutManager(sgm);

        mAdapter = new WaterFallAdapter(bContext, new ArrayList<WaterFallItem>());

        mAdapter.setOnItemClickListener(new OnItemClickListeners<WaterFallItem>() {
            @Override
            public void onItemClick(ViewHolder viewHolder, WaterFallItem data, int position) {
                Intent intent = new Intent(bContext, AlbumDetailActivity.class);
                intent.putParcelableArrayListExtra(AlbumDetailActivity.kDatas, (ArrayList<? extends Parcelable>) mAdapter.getDatas());
                intent.putExtra(AlbumDetailActivity.kCurrentPosition, position);
                intent.putExtra(AlbumDetailActivity.KALBUMID, commonId);
                if (ZSApp.getInstance().getBabyModel().getIsCreator().equals(ZSApp.getInstance().getUserId())||albumModel.getUserId() == null || (albumModel.getUserId() != null && albumModel.getUserId().equals(ZSApp.getInstance().getUserId()))) {
                    intent.putExtra("canDelete", true);
                }
                startActivityForResult(intent, AlbumDetailActivity.RESULT_CODE_DELETE);
            }
        });
        //加载更多的接口
//        mAdapter.setLoadingView(R.layout.load_loading_layout);
//        mAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
//            @Override
//            public void onLoadMore(boolean isReload) {
//                isLoadMore = true;
//                //TODO 这里得判断
//                loadData(url2);
//            }
//        });

        recyclerView.setAdapter(mAdapter);
        if (isBabyFragmentEmpty()) {
            //首页没数据的时候展示

            mSwipeRefreshLayout.setEnabled(false);
            bIvRight.setVisibility(View.GONE);
            initAlbumImgs(albumModel);
        } else {
            mSwipeRefreshLayout.setRefreshing(true);
            commonId = albumModel.getCommonId();
            presenter.ablumGetImgs(commonId);
            mSwipeRefreshLayout.setEnabled(true);
        }
    }

    private boolean isBabyFragmentEmpty() {
        return albumModel.getCommonId().equals(String.valueOf(Integer.MAX_VALUE));
    }

    @Override
    public void showData(Object o) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void doData(List<WaterFallItem> items) {
        ProgressDialogHelper.getInstance().hideProgressDialog();
        if (isLoadMore) {
            if (items.size() == 0) {
                //添加没有新数据的底部菜单
                mAdapter.setLoadEndView(R.layout.load_end_layout);
            } else {
                //添加新的
                mAdapter.setLoadMoreData(items);
            }
        } else {
            mAdapter.setNewData(items);
            if (mSwipeRefreshLayout != null) {
                mSwipeRefreshLayout.setRefreshing(false);
            } else {
                LogZS.i("mSwipeRefreshLayout == null");
            }
        }
    }


    private void loadData(List<WaterFallItem> list) {
        ProgressDialogHelper.getInstance().showProgressDialog(bContext, "拼命加载中...");
        DataService.startService(bContext, list);
    }

    @Override
    public void onRefresh() {
        isLoadMore = false;
        presenter.ablumGetImgs(commonId);
    }






    private final int REQUEST_ALBUM_CODE = 10;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(StringUtils.isEmpty(commonId)){
            return ;
        }
        presenter.ablumGetImgs(commonId);
        if (resultCode == MainActivity.BabyFragmentEditResultCode) {
            setResult(MainActivity.BabyFragmentEditResultCode);
            finish();
        }else if(resultCode == AlbumDetailActivity.RESULT_CODE_DELETE_ALBUM){
            setResult(MainActivity.BabyFragmentEditResultCode);
            finish();
        }

    }


    private void loadPhotos(ArrayList<String> list) {
        List<WaterFallItem> list2 = new ArrayList<>();
        for (String path : list) {
            WaterFallItem items = new WaterFallItem();
            items.setUrl(path);
            Bitmap bitmap2 = BitmapFactory.decodeFile(path);
            if (bitmap2 != null) {
                items.setWidth(bitmap2.getWidth());
                items.setHeight(bitmap2.getHeight());
            }
            list2.add(items);
        }
//添加到后面
        mAdapter.getDatas().addAll(list2);
        setLayoutManager();
        mAdapter.notifyDataSetChanged();
//        mAdapter.setData(list2);
    }

    private void setLayoutManager() {
        StaggeredGridLayoutManager sgm = new StaggeredGridLayoutManager(mAdapter.getDatas().size() == 1 ? 1 : 2, StaggeredGridLayoutManager.VERTICAL);
        sgm.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        recyclerView.setLayoutManager(sgm);
    }


    @Override
    public void initAlbumImgs(AlbumModel albumModel) {
        String[] names = albumModel.getImageNames().split(",");
        String[] ids = albumModel.getImageIds().split(",");
        List<WaterFallItem> list = new ArrayList<>();
        for (int i = 0; i < names.length; i++) {
            WaterFallItem waterFallItem = new WaterFallItem();
            waterFallItem.setUrl(Const.URL_Album + names[i]);
            waterFallItem.setId(ids[i]);
            list.add(waterFallItem);
        }

        StaggeredGridLayoutManager sgm = new StaggeredGridLayoutManager(list.size() == 1 ? 1 : 2, StaggeredGridLayoutManager.VERTICAL);
        sgm.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        if(recyclerView==null) {
            recyclerView.findViewById(R.id.recyclerView);
        }
        recyclerView.setLayoutManager(sgm);
        loadData(list);
    }

    @Override
    public void albumDelImgs(String imageIds, String imageNames) {
        if (mAdapter.getDatas().size() <= 0) {
            presenter.deleteAlbumById(albumModel);
        } else {
            showToast("删除成功");
        }
    }


    @Override
    public void albumOptFinish() {
        setLayoutManager();
        mAdapter.notifyDataSetChanged();
        presenter.ablumGetImgs(commonId);
    }

    @Override
    public void albumDelFinish() {
        setResult(MainActivity.BabyFragmentEditResultCode);
        finish();
    }

    @Override
    public void showDelOneImg(String fileName) {

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }



}
