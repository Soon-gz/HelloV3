package com.abings.baby.ui.album.edit;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.abings.baby.R;
import com.abings.baby.ui.album.AlbumDetailActivity;
import com.abings.baby.ui.album.AlbumMvpView;
import com.abings.baby.ui.album.AlbumPresenter;
import com.abings.baby.ui.album.WaterFallAdapter;
import com.abings.baby.ui.album.WaterFallItem;
import com.abings.baby.ui.base.BaseTitleActivity;
import com.abings.baby.ui.main.MainActivity;
import com.foamtrace.photopicker.PhotoPickerActivity;
import com.foamtrace.photopicker.SelectModel;
import com.foamtrace.photopicker.intent.PhotoPickerIntent;
import com.hellobaby.library.Const;
import com.hellobaby.library.data.model.AlbumModel;
import com.hellobaby.library.utils.CameraUtils;
import com.hellobaby.library.widget.BottomDialogUtils;
import com.hellobaby.library.widget.IPopupWindowMenuOnClick;
import com.hellobaby.library.widget.PopupWindowMenu;
import com.hellobaby.library.widget.baseadapter.OnItemClickListeners;
import com.hellobaby.library.widget.baseadapter.ViewHolder;
import com.hellobaby.library.widget.crop.FileUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * Created by zwj on 2017/2/15.
 * description : 相册编辑
 * 1.相册编辑界面  修改标题|内容 | 添加照片 | 删除照片 | 完成后直接返回到首页
 */

public class AlbumEditActivity extends BaseTitleActivity implements AlbumMvpView, SwipeRefreshLayout.OnRefreshListener {

    @Inject
    AlbumPresenter presenter;

    @BindView(R.id.albumEdit_recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.albumEdit_ll_bottom)
    LinearLayout llBottom;//底部编辑
    @BindView(R.id.albumEdit_iv_edit)
    ImageView ivBottomEdit;
    @BindView(R.id.albumEdit_et_editTitle)
    EditText etEditTitle;
    @BindView(R.id.albumEdit_et_editContent)
    EditText etEditContent;
    @BindView(R.id.albumEdit_ll_edit)
    LinearLayout llEdit;

    public final static String kImgList = "WaterFallItems";
    private String mCurrentPhotoPath;

    //当前编辑的状态
    private boolean isAdd = true;
    /**
     * 删除选中的列表的position
     */
    private List<Integer> listDel = new ArrayList<>();
    /**
     * 要删除的id
     */
    private List<Map<String, String>> listDelMap = new ArrayList<>();
    /**
     * 添加新图的地址
     */
    private List<String> listAdd = new ArrayList<>();


    private WaterFallAdapter mAdapter;
    private String titleStr = "";//标题
    private String contentStr = "";//内容
    private String commonId;//相册的id
    private AlbumModel albumModel;
    private List<WaterFallItem> imgList;


    @Override
    protected void btnRightOnClick(View v) {
        addOrDelOpt();
    }

    /**
     * 编辑
     */
    private void editAlbum() {

//        //编辑状态
//        hintKb();
//        titleBottomBarState(true);
    }

    /**
     * 添加和改动的时候上传
     */
    private void addOrDelOpt() {
        if (mAdapter.getDatas().size() <= 0) {
            String[] items = {"该相册没有照片将被删除", "删除", "取消"};
            BottomDialogUtils.getBottomListDialog(bContext, items, new BottomDialogUtils.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, String item, long id) {
                    if (position == 1) {
                        presenter.deleteAlbumById(albumModel);
                    }
                }
            });
        } else {
            String[] items = {"保存本次编辑", "取消"};
            BottomDialogUtils.getBottomListDialog(bContext, items, new BottomDialogUtils.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, String item, long id) {
                    if (position == 0) {
                        String title = etEditTitle.getText().toString().trim();
                        String content = etEditContent.getText().toString().trim();
                        presenter.ablumOptImgs(commonId, title, content, listDelMap, listAdd, mAdapter.getDatas(), bContext);
                    }
                }
            });
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
     */
    private void titleBottomBarState() {

        //编辑状态
        setBtnRightDrawableRes(R.drawable.selector_title_right_tick);
        bIvRight.setEnabled(false);
        setBtnLeftDrawableRes(R.drawable.title_x_black);
    }


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_album_edit;
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
        imgList = bundle.getParcelableArrayList(kImgList);

        titleStr = albumModel.getTitle();
        contentStr = albumModel.getContent();
        etEditTitle.setText(titleStr);
        etEditContent.setText(contentStr);
        bIvLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backClick();
            }
        });

        setLayoutManager(imgList);

        mAdapter = new WaterFallAdapter(bContext, imgList);

        mAdapter.setOnItemClickListener(new OnItemClickListeners<WaterFallItem>() {
            @Override
            public void onItemClick(ViewHolder viewHolder, WaterFallItem data, int position) {
                longClickAfter(data, position);
//                if (listAdd.size() <= 0) {
//
//                } else {
//                    Intent intent = new Intent(bContext, AlbumDetailActivity.class);
//                    intent.putParcelableArrayListExtra(AlbumDetailActivity.kDatas, (ArrayList<? extends Parcelable>) mAdapter.getDatas());
//                    intent.putExtra(AlbumDetailActivity.kCurrentPosition, position);
//                    intent.putExtra(AlbumDetailActivity.KALBUMID, commonId);
//                    if (albumModel.getUserId().equals(ZSApp.getInstance().getUserId())) {
//                        intent.putExtra("canDelete", true);
//                    }
//                    startActivityForResult(intent, AlbumDetailActivity.RESULT_CODE_DELETE);
//                }
            }
        });
        recyclerView.setAdapter(mAdapter);
        commonId = albumModel.getCommonId();

        titleBottomBarState();
    }

    @Override
    public void showData(Object o) {

    }


    @Override
    public void onRefresh() {
        presenter.ablumGetImgs(commonId);
    }


    @OnTextChanged(value = R.id.albumEdit_et_editTitle, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void octTitleAfter(CharSequence charSequence) {
        String title = charSequence.toString();
        if (title.isEmpty() || albumModel.getTitle().equals(title)) {
            //标题的判断
            setBtnEnabled(charSequence, titleStr, bIvRight, etEditContent, contentStr, true);
        } else {
            bIvRight.setEnabled(true);
        }
    }

    @OnTextChanged(value = R.id.albumEdit_et_editContent, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void octContentAfter(CharSequence charSequence) {
        String content = charSequence.toString();
        if (albumModel.getContent().equals(content)) {
            //内容的判断
            setBtnEnabled(charSequence, contentStr, bIvRight, etEditTitle, titleStr, false);
        } else {
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
    public void setBtnEnabled(CharSequence charSequence, String currentValue, View btnEnable, EditText otherEt, String otherOldValue, boolean isTitle) {
        String etOtherValue = getEditTextString(otherEt);
        String charSequenceStr = charSequence.toString().trim();
        if (!etOtherValue.isEmpty() && (!charSequenceStr.isEmpty() || !isTitle)) {
            int sizeDel = listDelMap.size();
            int sizeAdd = listAdd.size();
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
    }

    private String getEditTextString(EditText et) {
        return et.getText().toString().trim();
    }

    private void longClickAfter(WaterFallItem data, int position) {
        if (data.isSelected()) {
            listDel.remove(Integer.valueOf(position));
            data.setSelected(false);
        } else {
            listDel.add(position);
            data.setSelected(true);
        }

//        isAdd = !isStartDeleted;

        //这里本来的判断是，如果去掉所有的选中的图片，就是选中删除的图片为0，那底部的图片变回变色
        if (listDel.size() > 0) {
            isAdd = false;
            ivBottomEdit.setImageResource(R.drawable.album_delete);
        } else {
            isAdd = true;
            ivBottomEdit.setImageResource(R.drawable.album_add);
        }
        listAddOrDelChangeRightBtn(listDelMap, listAdd);
        mAdapter.notifyItemChanged(position);
    }

    private final int REQUEST_albumEdit_CODE = 10;

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
                case REQUEST_albumEdit_CODE:
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
                    setLayoutManager(mAdapter.getDatas());
                    mAdapter.notifyDataSetChanged();
                    listAdd.add(imagePath);
                    break;
            }
            listAddOrDelChangeRightBtn(listDelMap, listAdd);
        }
    }

    /**
     * 添加和删除图片的时候的判断
     *
     * @param _listDel
     * @param _listAdd
     */
    private void listAddOrDelChangeRightBtn(List _listDel, List _listAdd) {
        int delSize = _listDel.size();
        int addSize = _listAdd.size();
        String title = etEditTitle.getText().toString().trim();
//        String content = etEditContent.getText().toString().trim();
        if (!title.isEmpty()) {//&& !content.isEmpty()
            if (!title.equals(albumModel.getTitle()) || addSize > 0 || delSize > 0) {//|| !content.equals(albumModel.getContent())
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
            WaterFallItem item = new WaterFallItem();
            item.setUrl(path);
            Bitmap bitmap2 = BitmapFactory.decodeFile(path);
            if (bitmap2 != null&&(bitmap2.getHeight()/bitmap2.getWidth()<8)) {
                item.setWidth(bitmap2.getWidth());
                item.setHeight(bitmap2.getHeight());
            }else{
                item.setTypeLongImage();
                int height =bitmap2.getWidth()*8;
                item.setWidth(bitmap2.getWidth());
                item.setHeight(height);
            }
            list2.add(item);
        }
//添加到后面
        mAdapter.getDatas().addAll(list2);
        setLayoutManager(mAdapter.getDatas());
        mAdapter.notifyDataSetChanged();
    }

    private void setLayoutManager(List<WaterFallItem> list) {
        StaggeredGridLayoutManager sgm = new StaggeredGridLayoutManager(list.size() == 1 ? 1 : 2, StaggeredGridLayoutManager.VERTICAL);
        sgm.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        recyclerView.setLayoutManager(sgm);
    }

    @OnClick(R.id.albumEdit_ll_bottom)
    public void bottomClick(View view) {

//        if (listDel.size() <= 0) {
        if (isAdd) {
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
                        mCurrentPhotoPath = CameraUtils.dispatchTakePictureIntent(AlbumEditActivity.this);
                    } else if (1 == position) {
                        //相册
                        PhotoPickerIntent intent = new PhotoPickerIntent(bContext);
                        intent.setSelectModel(SelectModel.MULTI);
                        intent.setShowCarema(true); // 是否显示拍照
                        intent.setMaxTotal(9); // 最多选择照片数量，默认为6
//                        intent.setSelectedPaths(images); // 已选中的照片地址， 用于回显选中状态
                        startActivityForResult(intent, REQUEST_albumEdit_CODE);
                    }
                }
            });
            menu.showPpw(view);
        } else {
            final int delSize = listDel.size();
            if (delSize < 1) {
                //没有选中一张要删除的照片
                return;
            }
            //删除
            String[] items = {"删除" + delSize + "张照片", "取消"};
            BottomDialogUtils.getBottomListDialog(bContext, items, new BottomDialogUtils.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, String item, long id) {
                    if (position == 0) {
                        //确定删除
                        isAdd = true;
                        ivBottomEdit.setImageResource(R.drawable.album_add);
                        Collections.sort(listDel, new Comparator<Integer>() {
                            @Override
                            public int compare(Integer o1, Integer o2) {
                                return o1 > o2 ? -1 : 1;
                            }
                        });
                        for (int pos : listDel) {
                            String delId = mAdapter.getDatas().get(pos).getId();
                            if (delId != null && !delId.isEmpty()) {
                                Map<String, String> map = new HashMap<>();
                                map.put("id", delId);
                                map.put("name", mAdapter.getDatas().get(pos).getUrl().replace(Const.URL_Album, ""));
                                listDelMap.add(map);
                            } else {
                                listAdd.remove(mAdapter.getDatas().get(pos).getUrl());
                            }
                            mAdapter.getDatas().remove(pos);
                        }
                        listDel.clear();
                        mAdapter.notifyDataSetChanged();
                        listAddOrDelChangeRightBtn(listDelMap, listAdd);
                    }
                }
            });
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
        setLayoutManager(list);
    }

    @Override
    public void albumDelImgs(String imageIds, String imageNames) {
        if (mAdapter.getDatas().size() <= 0) {
            presenter.deleteAlbumById(albumModel);
        } else {
            showToast("删除成功");
        }
    }

    private boolean isEdit = false;

    @Override
    public void albumOptFinish() {
        isEdit = true;
        listAdd.clear();
        listDel.clear();
//        setLayoutManager(mAdapter.getDatas());
//        mAdapter.notifyDataSetChanged();
//        presenter.ablumGetImgs(commonId);
        setResult(MainActivity.BabyFragmentEditResultCode);
        finish();
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
                        finish();
                    } else {
                        //继续编辑
                    }
                }
            });
        } else {
            //没有做任何编辑
            finish();
        }
    }
}

