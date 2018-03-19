package com.abings.baby.teacher.ui.publishpicture;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.abings.baby.teacher.R;
import com.abings.baby.teacher.ZSApp;
import com.abings.baby.teacher.ui.base.BaseTitleActivity;
import com.abings.baby.teacher.ui.main.MainActivity;
import com.abings.baby.teacher.ui.publishvideo.VideoRecordActivity;
import com.foamtrace.photopicker.PhotoPickerActivity;
import com.foamtrace.photopicker.PhotoPreviewActivity;
import com.foamtrace.photopicker.SelectModel;
import com.foamtrace.photopicker.intent.PhotoPickerIntent;
import com.foamtrace.photopicker.intent.PhotoPreviewIntent;
import com.hellobaby.library.data.model.ClassModel;
import com.hellobaby.library.ui.LoginUtils;
import com.hellobaby.library.utils.ImageLoader;
import com.hellobaby.library.widget.BottomDialogUtils;
import com.hellobaby.library.widget.ClassSelectedAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.hellobaby.library.Const.videoPath;

/**
 * 发布消息
 */
public class PublishPictureActivity extends BaseTitleActivity implements PublishPictureMvpView {

    @Inject
    PublishPicturePresenter presenter;

    @BindView(R.id.publish_gridView)
    GridView gridView;
    @BindView(R.id.publish_tvplus_class)
    TextView tvPlusClass;
    @BindView(R.id.publish_listView)
    ListView listView;

    @BindView(R.id.publish_rl_video)
    RelativeLayout rlVideo;
    @BindView(R.id.publish_iv_videoImg)
    ImageView ivVideoImg;//播放
    @BindView(R.id.publish_et_content)
    EditText etContent;//内容

    private GridAdapter gridAdapter;
    private static final int REQUEST_CAMERA_CODE = 10;
    private static final int REQUEST_PREVIEW_CODE = 20;
    private static final int REQUEST_VIDEO_CODE = 30;
    /**
     * 图片真正选中的路径
     */
    private ArrayList<String> imageSelectPaths = new ArrayList<>();
    /**
     * 每次
     */
    private ArrayList<String> images = new ArrayList<>();
    Boolean isExpanded = false;
    private String mVideoPath;//视频的路径
    private List<ClassModel> listClass;
    private String mVideoThumbPath;
    private ClassSelectedAdapter classSelectedAdapter;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_publishpicture;
    }

    @Override
    protected void initDaggerInject() {
        getActivityComponent().inject(this);
    }


    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {
        presenter.attachView(this);
        setBtnRightDrawableRes(R.drawable.title_update);
        bIvRight.setVisibility(View.GONE);
        int cols = getResources().getDisplayMetrics().widthPixels / getResources().getDisplayMetrics().densityDpi;
        cols = cols < 3 ? 3 : cols;
        gridView.setNumColumns(3);
        // preview
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String imgs = (String) parent.getItemAtPosition(position);
                images = new ArrayList<>();
                images.addAll(imageSelectPaths);
                if (position == 0 && GridAdapter.ADD.equals(imgs)) {
                    //第一个什么都没有的时候，进行选择
                    String[] items = {"照片", "小视频"};
                    BottomDialogUtils.getBottomListDialog(bContext, items, new BottomDialogUtils.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, String item, long id) {
                            if (position == 0) {
                                //照片
                                selectPicture(imgs, position);
                            } else if (position == 1) {
                                //小视频
                                Intent intent = VideoRecordActivity.startRecordActivity(videoPath);
                                intent.setClass(bContext, VideoRecordActivity.class);
                                startActivityForResult(intent, REQUEST_VIDEO_CODE);
                            }
                        }
                    });
                    return;
                }
                selectPicture(imgs, position);
            }
        });
        if (images.size() < 9) {
            imageSelectPaths.add(GridAdapter.ADD);
        }
        gridAdapter = new GridAdapter(this, imageSelectPaths);
        gridView.setAdapter(gridAdapter);
        listClass = new ArrayList<>();
        listClass.addAll(ZSApp.getInstance().getClassModelList());
        classSelectedAdapter = new ClassSelectedAdapter(listClass, bContext);
        listView.setAdapter(classSelectedAdapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        classSelectedAdapter.setOnCheckedChangeListener(new ClassSelectedAdapter.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(int position, boolean isChecked) {
                listClass.get(position).setSelected(isChecked);
                classSelectedAdapter.notifyDataSetChanged();
                checkEnableUpdate();
            }
        });
        etContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                checkEnableUpdate();
            }
        });

        bIvLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backListener();
            }
        });
    }

    private void backListener() {
        boolean isSelectClass = false;
        for (ClassModel cl : listClass) {
            if (cl.isSelected()) {
                isSelectClass = true;
            }
        }
        if (!LoginUtils.isEmptyEdit(etContent) || mVideoPath != null || isSelectClass || !imageSelectPaths.get(0).equals(GridAdapter.ADD)) {
            //内容
            BottomDialogUtils.getBottomExitEditDialog(bContext);
        } else {
            finish();
        }
    }

    /**
     * 检查
     */
    private void checkEnableUpdate() {
        if (!LoginUtils.isEmptyEdit(etContent) && (mVideoPath != null || (!imageSelectPaths.get(0).equals(GridAdapter.ADD)))) {
            //1.输入不为空  &&  2.判断有数小视频的链接  ||     3.第一个图片不是add&&
            boolean isSelected = false;
            for (ClassModel c : listClass) {
                if (c.isSelected()) {
                    isSelected = true;
                }
            }
            if (isSelected) {
                bIvRight.setVisibility(View.VISIBLE);
            } else {
                bIvRight.setVisibility(View.GONE);
            }
        } else {
            bIvRight.setVisibility(View.GONE);
        }
    }

    private void selectPicture(String imgs, int position) {
        if (images.contains(GridAdapter.ADD) || images.size() >= 9) {
            //包含添加
            images.remove(GridAdapter.ADD);
            checkEnableUpdate();
        }
        if (GridAdapter.ADD.equals(imgs)) {
            PhotoPickerIntent intent = new PhotoPickerIntent(PublishPictureActivity.this);
            intent.setSelectModel(SelectModel.MULTI);
            intent.setShowCarema(true); // 是否显示拍照
            intent.setMaxTotal(9); // 最多选择照片数量，默认为6
            intent.setSelectedPaths(images); // 已选中的照片地址， 用于回显选中状态
            startActivityForResult(intent, REQUEST_CAMERA_CODE);
            checkEnableUpdate();
        } else {
            PhotoPreviewIntent intent = new PhotoPreviewIntent(PublishPictureActivity.this);
            intent.setCurrentItem(position);
            intent.setPhotoPaths(images);
            startActivityForResult(intent, REQUEST_PREVIEW_CODE);
            checkEnableUpdate();
        }
    }


    @Override
    public void showData(Object o) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == REQUEST_VIDEO_CODE) {
            mVideoThumbPath = data.getStringExtra(VideoRecordActivity.kVIDEO_THUMB);
            mVideoPath = data.getStringExtra(VideoRecordActivity.kVIDEO_MP4);
            loadVideo(mVideoThumbPath);
        } else if (requestCode == VideoPlayActivity.resultCode) {
            finish();
        } else if (resultCode == RESULT_OK) {
            switch (requestCode) {
                // 选择照片
                case REQUEST_CAMERA_CODE:
                    ArrayList<String> list = data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT);
                    loadAdapter(list);
                    break;
                // 预览
                case REQUEST_PREVIEW_CODE:
                    ArrayList<String> ListExtra = data.getStringArrayListExtra(PhotoPreviewActivity.EXTRA_RESULT);
                    loadAdapter(ListExtra);
                    break;
            }
        }
    }

    /**
     * 创建小视频的查看
     */
    private void loadVideo(String videoThumbPath) {
        rlVideo.setVisibility(View.VISIBLE);
        ImageLoader.loadRoundCenterCrop(bContext, videoThumbPath, ivVideoImg);
        gridView.setVisibility(View.GONE);
    }

    private void loadAdapter(ArrayList<String> paths) {
        rlVideo.setVisibility(View.GONE);
        gridView.setVisibility(View.VISIBLE);
        if (imageSelectPaths != null && imageSelectPaths.size() > 0) {
            imageSelectPaths.clear();
        }
        if (paths.contains(GridAdapter.ADD)) {
            paths.remove(GridAdapter.ADD);
        }
        if (paths.size() < 9)
            paths.add(GridAdapter.ADD);
        imageSelectPaths.addAll(paths);
        gridAdapter = new GridAdapter(this, imageSelectPaths);
        gridView.setAdapter(gridAdapter);
    }

    @Override
    protected void btnRightOnClick(View v) {
        String content = etContent.getText().toString().trim();
        if (mVideoPath == null) {
            if (imageSelectPaths.contains(GridAdapter.ADD)) {
                imageSelectPaths.remove(GridAdapter.ADD);
            }
            presenter.dynamicUploadImgs(content, listClass, imageSelectPaths);
        } else {
            presenter.dynamicUploadVideo(content, listClass, mVideoThumbPath, mVideoPath);
        }
    }

    @OnClick(R.id.publish_rl_video)
    public void onVideoClick(View view) {
        VideoPlayActivity.startActivityForResultVideoPlayRecord(bContext, mVideoPath, VideoPlayActivity.MORE_TYPE_RECORD, VideoPlayActivity.class);
    }

    @OnClick(R.id.publish_tvplus_class)
    public void onClassClick() {
        //班级点击
        if (!isExpanded) {
            Drawable drawable = getResources().getDrawable(R.drawable.et_closeicon);
            drawable.setBounds(0, 0, drawable.getMinimumWidth() / 3 * 2, drawable.getMinimumHeight() / 3 * 2);
            tvPlusClass.setCompoundDrawables(null, null, drawable, null);
            listView.setVisibility(View.VISIBLE);
        } else {
            Drawable drawable = getResources().getDrawable(R.drawable.et_openicon);
            drawable.setBounds(0, 0, drawable.getMinimumWidth() / 3 * 2, drawable.getMinimumHeight() / 3 * 2);
            tvPlusClass.setCompoundDrawables(null, null, drawable, null);
            listView.setVisibility(View.GONE);
        }
        isExpanded = !isExpanded;
    }

    @Override
    public void publishSuccess() {
        setResult(MainActivity.mainRequestCode);
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            backListener();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
