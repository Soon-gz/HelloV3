package com.abings.baby.teacher.ui.publishpicture;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.abings.baby.teacher.R;
import com.abings.baby.teacher.ZSApp;
import com.abings.baby.teacher.ui.PrizeDraw.FullGridView;
import com.abings.baby.teacher.ui.base.BaseTitleActivity;
import com.abings.baby.teacher.ui.main.MainActivity;
import com.abings.baby.teacher.ui.publishvideo.VideoEditActivity;
import com.abings.baby.teacher.ui.publishvideo.VideoRecordActivity;
import com.foamtrace.photopicker.PhotoPickerActivity;
import com.foamtrace.photopicker.PhotoPreviewActivity;
import com.foamtrace.photopicker.SelectModel;
import com.foamtrace.photopicker.intent.PhotoPickerIntent;
import com.foamtrace.photopicker.intent.PhotoPreviewIntent;
import com.hellobaby.library.Const;
import com.hellobaby.library.data.model.ClassModel;
import com.hellobaby.library.ui.LoginUtils;
import com.hellobaby.library.utils.ImageLoader;
import com.hellobaby.library.widget.BottomDialogUtils;
import com.hellobaby.library.widget.ClassSelectedAdapter;
import com.hellobaby.library.widget.ProgressDialogHelper;
import com.hellobaby.library.widget.ToastUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
    FullGridView gridView;
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
    private static final int REQUEST_VIDEO_LOCAL_CODE = 40;
    private static final int REQUEST_VIDEO_EDIT_CODE = 50;
    public final static String kVIDEO_MP4 = "video_mp4";

    MediaMetadataRetriever metadataRetriever;
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
    private int maxImgCount = 50;

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
        metadataRetriever = new MediaMetadataRetriever();
        // preview
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String imgs = (String) parent.getItemAtPosition(position);
                images = new ArrayList<>();
                images.addAll(imageSelectPaths);
                if (position == 0 && GridAdapter.ADD.equals(imgs)) {
                    //第一个什么都没有的时候，进行选择
                    String[] items = {"照片", "录制小视频","本地小视频"};
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
                            }else if (position == 2){
                                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(intent, REQUEST_VIDEO_LOCAL_CODE);
                            }
                        }
                    });
                    return;
                }
                selectPicture(imgs, position);
            }
        });
        if (images.size() < maxImgCount) {
            imageSelectPaths.add(GridAdapter.ADD);
        }
        gridAdapter = new GridAdapter(this, imageSelectPaths);
        gridView.setAdapter(gridAdapter);
        listClass = new ArrayList<>();
        if (ZSApp.getInstance().getClassModelList() != null){
            listClass.addAll(ZSApp.getInstance().getClassModelList());
        }
        classSelectedAdapter = new ClassSelectedAdapter(listClass, bContext);
        listView.setAdapter(classSelectedAdapter);
        setListViewHeightBasedOnChildren(listView);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        classSelectedAdapter.setOnCheckedChangeListener(new ClassSelectedAdapter.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(int position, boolean isChecked) {
                listClass.get(position).setSelected(isChecked);
                classSelectedAdapter.notifyDataSetChanged();
                checkEnableUpdate();
                setListViewHeightBasedOnChildren(listView);
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
    private boolean checkEnableUpdate() {
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
                return true;
            } else {
                bIvRight.setVisibility(View.GONE);
                return false;
            }
        } else {
            bIvRight.setVisibility(View.GONE);
            return false;
        }

    }

    private void selectPicture(String imgs, int position) {
        if (images.contains(GridAdapter.ADD) || images.size() >= maxImgCount) {
            //包含添加
            images.remove(GridAdapter.ADD);
            checkEnableUpdate();
        }
        if (GridAdapter.ADD.equals(imgs)) {
            PhotoPickerIntent intent = new PhotoPickerIntent(PublishPictureActivity.this);
            intent.setSelectModel(SelectModel.MULTI);
            intent.setShowCarema(true); // 是否显示拍照
            intent.setMaxTotal(maxImgCount); // 最多选择照片数量，默认为6
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
//            老版本视频预览之后关掉发布界面  不合逻辑  2017/10/11  sw
//            finish();

            //选择本地视频回调
        } else if (resultCode == RESULT_OK && requestCode == REQUEST_VIDEO_LOCAL_CODE){
            Uri selectedVideo = data.getData();
            String[] filePathColumn = { MediaStore.Video.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedVideo ,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String videoPath_cursor = cursor.getString(columnIndex);
            mVideoPath = videoPath_cursor;

            MediaPlayer mediaPlayer = new MediaPlayer();
            try {
                mediaPlayer.setDataSource(mVideoPath);
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
            int  duration = mediaPlayer.getDuration();
            if (duration > 10000){
                Intent intent = new Intent(bContext, VideoEditActivity.class);
                intent.putExtra(kVIDEO_MP4,mVideoPath);
                startActivityForResult(intent,REQUEST_VIDEO_EDIT_CODE);
            }else{
                metadataRetriever.setDataSource(mVideoPath);
                Bitmap bitmap = metadataRetriever.getFrameAtTime();
                String pic_new_file = Const.videoPath+ File.separator+"local_file_"+System.currentTimeMillis()+".png";
                File file = new File(pic_new_file);
                saveBitmap(file,bitmap);

                if (bitmap != null){
                    rlVideo.setVisibility(View.VISIBLE);
                    gridView.setVisibility(View.GONE);
                    mVideoThumbPath = pic_new_file;
                    ImageLoader.loadRoundCenterCrop(bContext, mVideoThumbPath, ivVideoImg);
                    checkEnableUpdate();
                }

            }
            cursor.close();
            //视频编辑回调
        } else if (resultCode == RESULT_OK && requestCode == REQUEST_VIDEO_EDIT_CODE){
            mVideoPath = data.getStringExtra(kVIDEO_MP4);
            MediaMetadataRetriever metadataRetriever0 = new MediaMetadataRetriever();
            metadataRetriever0.setDataSource(mVideoPath);
            Bitmap bitmap = metadataRetriever0.getFrameAtTime();
            String pic_new_file = Const.videoPath+File.separator+"local_file_cut_"+System.currentTimeMillis()+".png";
            File file = new File(pic_new_file);
            saveBitmap(file,bitmap);
            rlVideo.setVisibility(View.VISIBLE);
            gridView.setVisibility(View.GONE);
            mVideoThumbPath = pic_new_file;
            ImageLoader.loadRoundCenterCrop(bContext, mVideoThumbPath, ivVideoImg);
            checkEnableUpdate();
        }else if (resultCode == RESULT_OK) {
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

    public void saveBitmap(File f,Bitmap bm) {
        if (f.exists()) {
            f.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建小视频的查看
     */
    private void loadVideo(String videoThumbPath) {
        rlVideo.setVisibility(View.VISIBLE);
        ImageLoader.loadRoundCenterCrop(bContext, videoThumbPath, ivVideoImg);
        gridView.setVisibility(View.GONE);
        checkEnableUpdate();
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
        if (paths.size() < maxImgCount){
            paths.add(GridAdapter.ADD);
        }
        imageSelectPaths.addAll(paths);
        gridAdapter = new GridAdapter(this, imageSelectPaths);
        gridView.setAdapter(gridAdapter);
        checkEnableUpdate();
    }

    @Override
    protected void btnRightOnClick(View v) {
        String content = etContent.getText().toString().trim();

        if (!checkEnableUpdate()) {
            showMsg("请填写正确内容再提交");
            return;
        }

        if (mVideoPath == null) {
            if (imageSelectPaths.contains(GridAdapter.ADD)) {
                imageSelectPaths.remove(GridAdapter.ADD);
            }
            presenter.dynamicUploadImgs(content, listClass, imageSelectPaths, bContext);
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
        presenter.insertRecord();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            backListener();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    ProgressDialog progressDialog;

    @Override
    public void uploadProgress(final String text) {
        if (progressDialog == null) {
            progressDialog = ProgressDialogHelper.getInstance().showUploadProgressDialog((Activity) bContext, "准备上传");
            progressDialog.show();
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressDialog.setMessage("上传中" + "(" + text + ")");
            }
        });
    }

    @Override
    public void insertRecord() {
        finish();
    }

    private String mCurrentPhotoPath;

    @Override
    public void showError(final String err) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ToastUtils.showErrToast(bContext, err);
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }
            }
        });
    }


    public void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        ((ViewGroup.MarginLayoutParams) params).setMargins(10, 10, 10, 10);
        listView.setLayoutParams(params);
    }

}
