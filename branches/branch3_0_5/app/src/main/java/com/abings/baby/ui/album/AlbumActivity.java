package com.abings.baby.ui.album;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.abings.baby.R;
import com.abings.baby.ZSApp;
import com.abings.baby.ui.base.BaseTitleActivity;
import com.abings.baby.ui.main.MainActivity;
import com.abings.baby.widget.baseadapter.OnItemClickListeners;
import com.abings.baby.widget.baseadapter.ViewHolder;
import com.foamtrace.photopicker.PhotoPickerActivity;
import com.foamtrace.photopicker.SelectModel;
import com.foamtrace.photopicker.intent.PhotoPickerIntent;
import com.hellobaby.library.Const;
import com.hellobaby.library.data.model.AlbumModel;
import com.hellobaby.library.utils.CameraUtils;
import com.hellobaby.library.utils.DateUtil;
import com.hellobaby.library.utils.LogZS;
import com.hellobaby.library.widget.BottomDialogUtils;
import com.hellobaby.library.widget.IPopupWindowMenuOnClick;
import com.hellobaby.library.widget.PopupWindowMenu;
import com.hellobaby.library.widget.ProgressDialogHelper;
import com.hellobaby.library.widget.crop.FileUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;


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
    @BindView(R.id.album_ll_bottom)
    LinearLayout llBottom;//底部编辑
    @BindView(R.id.album_iv_edit)
    ImageView ivBottomEdit;
    @BindView(R.id.album_et_editTitle)
    EditText etEditTitle;
    @BindView(R.id.album_et_editContent)
    EditText etEditContent;
    @BindView(R.id.album_ll_edit)
    LinearLayout llEdit;
    @BindView(R.id.album_tv_content)
    TextView tvContent;
    @BindView(R.id.album_tv_update)
    TextView tvUpdate;
    @BindView(R.id.album_ll_show)
    LinearLayout llShow;
    @BindView(R.id.album_swipeRefresh)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private String mCurrentPhotoPath;

    //是否添加
    private boolean isLoadMore = false;
    //当前编辑的状态
    private boolean isDdd = true;
    /**
     * 删除选中的列表的position
     */
    private List<Integer> listDel = new ArrayList<>();
    /**
     * 添加新图的地址
     */
    private List<String> listAdd = new ArrayList<>();


    private WaterFallAdapter mAdapter;
    private boolean isEditState = false;
    private String titleStr = "";//标题
    private String contentStr = "";//内容
    private String commonId;//相册的id
    private AlbumModel albumModel;


    @Override
    protected void btnRightOnClick(View v) {
        if (isEditState) {
            addOrDelOpt();
        } else {
            String[] itemsRight = {"编辑", "删除该相册"};
            BottomDialogUtils.getBottomListDialog(bContext, itemsRight, new BottomDialogUtils.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, String item, long id) {
                    if (position == 0) {
                        editAlbum();
                    } else if (position == 1) {
                        delAlbum();
                    }
                }
            });
        }

    }

    /**
     * 删除相册
     */
    public void delAlbum() {
        String[] items = {"是否要删除该相册?", "是", "否"};
        BottomDialogUtils.getBottomListDialog(bContext, items, new BottomDialogUtils.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, String item, long id) {
                if (position == 1) {
                    presenter.deleteAlbumById(albumModel);
                }
            }
        });
    }

    /**
     * 编辑
     */
    private void editAlbum() {

        if (isEditState) {
            //编辑状态
            hintKb();
            contentStr = getEditTextString(etEditContent);
            titleStr = getEditTextString(etEditTitle);
            if(contentStr.isEmpty()){
                tvContent.setVisibility(View.GONE);
            }else {
                tvContent.setVisibility(View.VISIBLE);
                tvContent.setText(contentStr);
            }
            setTitleText(titleStr);
        }
        isEditState = !isEditState;
        titleBottomBarState(isEditState);
    }

    /**
     * 添加和改动的时候上传
     */
    private void addOrDelOpt() {

        if (listDel.size() > 0) {

            String[] items = {"删除" + listDel.size() + "张照片", "取消"};
            BottomDialogUtils.getBottomListDialog(bContext, items, new BottomDialogUtils.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, String item, long id) {
                    if (position == 0) {

                        //确认删除
                        int size = listDel.size();
                        StringBuffer sbImages = new StringBuffer();
                        StringBuffer sbIds = new StringBuffer();
                        for (int i = 0; i < size; i++) {
                            WaterFallItem waterFallItem = mAdapter.getDatas().get(listDel.get(i));
                            sbImages.append(waterFallItem.getUrl().replace(Const.URL_Album, ""));
                            sbIds.append(waterFallItem.getId());
                            if (i < size - 1) {
                                sbImages.append(",");
                                sbIds.append(",");
                            }
                        }
                        String title = etEditTitle.getText().toString().trim();
                        String content = etEditContent.getText().toString().trim();
                        presenter.ablumDelImgs(commonId, title, content, sbIds.toString(), sbImages.toString());

                        //不编辑
                        Collections.sort(listDel, new Comparator<Integer>() {
                            @Override
                            public int compare(Integer o1, Integer o2) {
                                return o1 > o2 ? -1 : 1;
                            }
                        });
                        for (int pos : listDel) {
                            mAdapter.getDatas().remove(pos);
                        }
                        mAdapter.notifyDataSetChanged();
                        listDel.clear();
                    } else if (position == 1) {
                        clearDel();
                    }
                }
            }).setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    clearDel();
                    return false;
                }
            });
        } else if (listAdd.size() > 0) {
            albumModel.setTitle(etEditTitle.getText().toString().trim());
            albumModel.setContent(etEditContent.getText().toString().trim());
            presenter.albumUpdateImgs(albumModel, listAdd);
        } else {
            String title = etEditTitle.getText().toString().trim();
            String content = etEditContent.getText().toString().trim();
            albumModel.setTitle(title);
            albumModel.setContent(content);
            presenter.albumUpdateTitleContent(albumModel);
        }
        editAlbum();
    }
    private void clearDel(){
        //取消
        listDel.clear();
        for (WaterFallItem it : mAdapter.getDatas()) {
            it.setSelected(false);
        }
        mAdapter.notifyDataSetChanged();
        if(contentStr.isEmpty()){
            etEditContent.setVisibility(View.GONE);
        }else{
            etEditContent.setText(contentStr);
        }
    }
    /**
     * 1. 删除帮助类，从详细界面中单张图片删除后回带该界面
     * 2. 相册列表删除未做操作
     *
     * @param dlist
     */
    private void deleteImgsHelp(List<WaterFallItem> dlist) {
        int size = dlist.size();
        StringBuffer sbImages = new StringBuffer();
        StringBuffer sbIds = new StringBuffer();
        for (int i = 0; i < size; i++) {
            WaterFallItem waterFallItem = dlist.get(i);
            sbImages.append(waterFallItem.getUrl().replace(Const.URL_Album, ""));
            sbIds.append(waterFallItem.getId());
            if (i < size - 1) {
                sbImages.append(",");
                sbIds.append(",");
            }
        }
        String title = etEditTitle.getText().toString().trim();
        String content = etEditContent.getText().toString().trim();
        presenter.ablumDelImgs(commonId, title, content, sbIds.toString(), sbImages.toString());
    }

    /**
     * 标题的状态
     *
     * @param isEdit
     */
    private void titleBottomBarState(boolean isEdit) {

        if (isEdit) {
            //编辑状态
            llEdit.setVisibility(View.VISIBLE);
            llShow.setVisibility(View.GONE);
            bTvTitle.setVisibility(View.GONE);
            etEditTitle.setText(titleStr);
            etEditContent.setText(contentStr);
            setBtnRightDrawableRes(R.drawable.selector_title_right_tick);
            bIvRight.setEnabled(false);
            setBtnLeftDrawableRes(R.drawable.title_x_black);
            mSwipeRefreshLayout.setEnabled(false);
        } else {
            llEdit.setVisibility(View.GONE);
            llShow.setVisibility(View.VISIBLE);
            bTvTitle.setVisibility(View.VISIBLE);
            setBtnLeftDrawableRes(R.drawable.title_left_arrow);
            setBtnRightDrawableRes(R.drawable.title_more_black);
            bIvRight.setEnabled(true);
            mSwipeRefreshLayout.setEnabled(true);
        }
        bottomState(isEdit);
    }

    /**
     * 底部菜单动画
     *
     * @param isShow
     */
    private void bottomState(boolean isShow) {
        if (isShow) {
            //编辑
            llBottom.setVisibility(View.VISIBLE);
            isDdd = true;
            ivBottomEdit.setImageResource(R.drawable.album_add);
            TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0,//X from
                    Animation.RELATIVE_TO_SELF, 0,//X to
                    Animation.RELATIVE_TO_SELF, 1,
                    Animation.RELATIVE_TO_SELF, 0);
            translateAnimation.setDuration(500);
            LayoutAnimationController layoutAnimationController = new LayoutAnimationController(translateAnimation);
            llBottom.setLayoutAnimation(layoutAnimationController);
        } else {
            llBottom.setVisibility(View.GONE);
        }
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

        presenter.attachView(this);
        Bundle bundle = getIntent().getExtras();
        albumModel = (AlbumModel) bundle.getSerializable("AlbumModel");
        setTitleText(albumModel != null ? albumModel.getTitle() : null);
        if(albumModel.getContent().isEmpty()){
            tvContent.setVisibility(View.GONE);
        }else {
            tvContent.setVisibility(View.VISIBLE);
            tvContent.setText(albumModel.getContent());
        }
        tvUpdate.setText(DateUtil.ServerDate2NYRHMFormat(albumModel.getLastmodifyTime()));
        setBtnRightDrawableRes(R.drawable.title_more_black);
        EventBus.getDefault().register(this);
        titleStr = bTvTitle.getText().toString().trim();
        contentStr = tvContent.getText().toString().trim();

        bIvLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backClick();
            }
        });

        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent, R.color.colorPrimaryDark);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        StaggeredGridLayoutManager sgm = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        sgm.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        recyclerView.setLayoutManager(sgm);

        mAdapter = new WaterFallAdapter(bContext, new ArrayList<WaterFallItem>());

        mAdapter.setOnItemClickListener(new OnItemClickListeners<WaterFallItem>() {
            @Override
            public void onItemClick(ViewHolder viewHolder, WaterFallItem data, int position) {
                if (isEditState) {
                    if (listAdd.size() <= 0) {
                        longClickAfter(true, data, position);
                    }

                } else {
                    Intent intent = new Intent(bContext, AlbumDetailActivity.class);
                    intent.putParcelableArrayListExtra(AlbumDetailActivity.kDatas, (ArrayList<? extends Parcelable>) mAdapter.getDatas());
                    intent.putExtra(AlbumDetailActivity.kCurrentPosition, position);
                    if (albumModel.getUserId()==null||(albumModel.getUserId()!=null&&albumModel.getUserId().equals(ZSApp.getInstance().getUserId()))){
                        intent.putExtra("canDelete", true);
                    }
                    startActivityForResult(intent, AlbumDetailActivity.RESULT_CODE_DELETE);
                }
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
//            ivEmpty.setImageResource(albumModel.getImageResId());
//            svEmpty.setVisibility(View.VISIBLE);
//            recyclerView.setVisibility(View.GONE);
//            ivEmpty.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(bContext,DrawablePhotoActivity.class);
//                    intent.putExtra(DrawablePhotoActivity.kImageResId,albumModel.getImageResId());
//                    startActivity(intent);
//                }
//            });
        } else {
            mSwipeRefreshLayout.setRefreshing(true);
            commonId = albumModel.getCommonId();
            presenter.ablumGetImgs(commonId);
            mSwipeRefreshLayout.setEnabled(true);
        }
    }

    private boolean isBabyFragmentEmpty(){
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


    @OnTextChanged(value = R.id.album_et_editTitle, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void octTitleAfter(CharSequence charSequence) {
        String title = charSequence.toString();
        if(title.isEmpty()||albumModel.getTitle().equals(title)) {
            //标题的判断
            setBtnEnabled(charSequence, titleStr, bIvRight, etEditContent, contentStr);
        }else{
            bIvRight.setEnabled(true);
        }
    }

    @OnTextChanged(value = R.id.album_et_editContent, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void octContentAfter(CharSequence charSequence) {
        String content = charSequence.toString();
        if(albumModel.getContent().equals(content)){
            //内容的判断
            setBtnEnabled(charSequence, contentStr, bIvRight, etEditTitle, titleStr);
        }else{
            bIvRight.setEnabled(true);
        }
    }

    /**
     * @param charSequence
     * @param currentValue  当前值
     * @param btnEnable     被控制的按钮
     * @param otherEt       其它的输入框
     * @param otherOldValue 其它输入框的老数据
     */
    public void setBtnEnabled(CharSequence charSequence, String currentValue, View btnEnable, EditText otherEt, String otherOldValue) {
        String etValue = getEditTextString(otherEt);
        String charSequenceStr = charSequence.toString().trim();

        if (!etValue.isEmpty() && !charSequenceStr.isEmpty()) {
            int sizeDel = listDel.size();
            int sizeAdd = listAdd.size();
//            if (sizeAdd > 0 || sizeDel > 0) {
//                btnEnable.setEnabled(true);
//            } else {
//                btnEnable.setEnabled(false);
//            }
            //输入框不为空
            if (!currentValue.equals(charSequenceStr) || !otherOldValue.equals(otherEt.getText().toString().trim()) || sizeAdd > 0 || sizeDel > 0) {
                //当前值||其它输入框
                btnEnable.setEnabled(true);
            } else {
                btnEnable.setEnabled(false);
            }
        } else {
            btnEnable.setEnabled(false);
        }


//        if (!etValue.isEmpty() || !charSequenceStr.isEmpty() || currentValue.equals(charSequenceStr)) {
//            //1.其它输入框为空，当前输入是否为空，当前的输入框的值和之前的值是否一致
//            if (otherOldValue.equals(otherEt.getText().toString().trim())) {
//                //其它输入框是否有变动
//                int sizeDel = listDel.size();
//                int sizeAdd = listAdd.size();
//                if (sizeAdd > 0 || sizeDel > 0) {
//                    btnEnable.setEnabled(true);
//                } else {
//                    btnEnable.setEnabled(false);
//                }
//            } else {
//                btnEnable.setEnabled(true);
//            }
//        } else {
//            btnEnable.setEnabled(true);
//        }
    }

    private String getEditTextString(EditText et) {
        return et.getText().toString().trim();
    }

    private void longClickAfter(boolean isStartDeleted, WaterFallItem data, int position) {
        if (data.isSelected()) {
            listDel.remove(Integer.valueOf(position));
            data.setSelected(false);
        } else {
            listDel.add(position);
            data.setSelected(true);
            bIvRight.setEnabled(true);
        }
        //这里本来的判断是，如果去掉所有的选中的图片，就是选中删除的图片为0，那底部的图片变回变色
//        if (listDel.size() == 0) {
//            ivBottomEdit.setImageResource(R.drawable.album_add);
//        } else {
        ivBottomEdit.setImageResource(R.drawable.album_delete);
        isDdd = false;
//        }
        if (isStartDeleted) {
            //有需要删除的
//            ivBottomEdit.setImageResource(R.drawable.album_delete);
//            if (listDel.size() > 0 || !titleStr.equals(getEditTextString(etEditTitle)) || !contentStr.equals(getEditTextString(etEditContent))) {
//                //文字和图片都有改变
//                bIvRight.setEnabled(true);
//            } else {
//                bIvRight.setEnabled(false);
//            }
            listAddOrDelChangeRightBtn(listDel);
        }
        mAdapter.notifyItemChanged(position);
    }

    private final int REQUEST_ALBUM_CODE = 10;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == AlbumDetailActivity.RESULT_CODE_DELETE) {
            List<WaterFallItem> items = data.getParcelableArrayListExtra(AlbumDetailActivity.kDatas);
            List<WaterFallItem> delList = data.getParcelableArrayListExtra(AlbumDetailActivity.kDelete);
            mAdapter.setNewData(items);
            deleteImgsHelp(delList);
        } else if (resultCode == RESULT_OK) {
            switch (requestCode) {
                // 选择照片
                case REQUEST_ALBUM_CODE:
                    ArrayList<String> list = data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT);
                    listAdd.addAll(list);
                    loadPhotos(list);
                    break;
                // 预览
                case CameraUtils.REQUEST_TAKEPHOTO_CODE:
                    Uri uri = Uri.parse(mCurrentPhotoPath);
                    String imagePath = FileUtils.getFilePathFromUri(bContext, uri);
                    Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
                    WaterFallItem items = new WaterFallItem();
                    if (bitmap != null) {
                        items.setWidth(bitmap.getWidth());
                        items.setHeight(bitmap.getHeight());
                    }
                    items.setUrl(imagePath);
                    List<WaterFallItem> list2 = new ArrayList<>();
                    list2.add(items);
                    mAdapter.getDatas().addAll(list2);
                    setLayoutManager();
                    mAdapter.notifyDataSetChanged();
                    listAdd.add(imagePath);
                    break;
            }
            listAddOrDelChangeRightBtn(listAdd);
//            if (addSize > 0) {
//                if (etEditTitle.getText().toString().trim().isEmpty() || etEditContent.getText().toString().trim().isEmpty()) {
//                    bIvRight.setEnabled(false);
//                } else {
//                    bIvRight.setEnabled(true);
//                }
//            } else {
//                if (title.equals(albumModel.getTitle()) && content.equals(albumModel.getContent())) {
//                    bIvRight.setEnabled(false);
//                } else {
//                    bIvRight.setEnabled(true);
//                }
//            }
        }

    }

    /**
     * 添加和删除图片的时候的判断
     *
     * @param list
     */
    private void listAddOrDelChangeRightBtn(List list) {
        int addSize = list.size();
        String title = etEditTitle.getText().toString().trim();
//        String content = etEditContent.getText().toString().trim();
        if (!title.isEmpty() ) {//&& !content.isEmpty()
            if (!title.equals(albumModel.getTitle()) || addSize > 0) {//|| !content.equals(albumModel.getContent())
                bIvRight.setEnabled(true);
            } else {
                bIvRight.setEnabled(false);
            }
        } else {
            bIvRight.setEnabled(false);
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

    @OnClick(R.id.album_ll_bottom)
    public void bottomClick(View view) {

//        if (listDel.size() <= 0) {
        if (isDdd) {
            //没有删除，就添加
            PopupWindowMenu.Item[] items = {
                    new PopupWindowMenu.Item(R.drawable.ppw_camera, "相机", MainActivity.class),
                    new PopupWindowMenu.Item(R.drawable.ppw_picture, "相册", MainActivity.class)};

            PopupWindowMenu menu = new PopupWindowMenu(bContext, items, true, bLlRoot);
            menu.setItemOnClick(new IPopupWindowMenuOnClick() {
                @Override
                public void onItemClick(View view, int position) {
                    if (0 == position) {
                        //相机
//                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                        startActivityForResult(intent, REQUEST_CAMERA_CODE);
                        mCurrentPhotoPath = CameraUtils.dispatchTakePictureIntent(AlbumActivity.this);
                    } else if (1 == position) {
                        //相册
                        PhotoPickerIntent intent = new PhotoPickerIntent(bContext);
                        intent.setSelectModel(SelectModel.MULTI);
                        intent.setShowCarema(true); // 是否显示拍照
                        intent.setMaxTotal(9); // 最多选择照片数量，默认为6
//                        intent.setSelectedPaths(images); // 已选中的照片地址， 用于回显选中状态
                        startActivityForResult(intent, REQUEST_ALBUM_CODE);
                    }
                }
            });
            menu.showPpw(view);
        }
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
        recyclerView.setLayoutManager(sgm);
        loadData(list);
    }

    @Override
    public void albumDelImgs(String imageIds, String imageNames) {
        if (mAdapter.getDatas().size() <= 0) {
            presenter.deleteAlbumById(albumModel);
        } else {
            showToast("删除图片成功");
        }
    }

    private boolean isEdit = false;

    @Override
    public void albumOptFinish() {
        isEdit = true;
        listAdd.clear();
        listDel.clear();
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
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void finish() {
        if (isEdit) {
            setResult(MainActivity.BabyFragmentEditResultCode);
        }
        super.finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            backClick();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void backClick() {
        if (isEditState) {
            //编辑状态
            if (!titleStr.equals(etEditTitle.getText().toString().trim()) || !contentStr.equals(etEditContent.getText().toString().trim()) || listAdd.size() > 0 || listDel.size() > 0) {
                //是否为空, 内容有变动,是否有添加
                BottomDialogUtils.getBottomExitEditDialog(bContext, new BottomDialogUtils.OnClickExitEditClickListener() {
                    @Override
                    public void onClickExitEdit(boolean bool) {
                        if (bool) {
                            if (listDel.size() > 0) {
                                for (WaterFallItem item : mAdapter.getDatas()) {
                                    item.setSelected(false);
                                    mAdapter.notifyDataSetChanged();
                                }
                            }
                            //放弃编辑
                            isEditState = false;
                            titleBottomBarState(false);
                        } else {
                            //继续编辑
                        }
                    }
                });
            } else {
                //没有做任何编辑
                isEditState = false;
                titleBottomBarState(false);
            }
        } else {
            //返回
            finish();
        }
    }
}
