package com.hellobaby.timecard.ui.camera;

import android.content.Context;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.RelativeLayout;


import com.hellobaby.timecard.KeyConst;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by zwj on 2017/3/14.
 */

public class PictureSurfaceViewHalf extends SurfaceView implements SurfaceHolder.Callback {

    private SurfaceHolder mSurfaceHolder;
    private Camera mCamera;
    private Camera.Size mSize;
    private boolean isFirst = false;
    private RelativeLayout rlPreview;

    public PictureSurfaceViewHalf(Context context) {
        this(context, null);
    }

    public PictureSurfaceViewHalf(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public PictureSurfaceViewHalf(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);
        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        isFirst = false;
        resumeCamera();
    }

    public void setWH(RelativeLayout relativeLayout) {
        rlPreview = relativeLayout;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        if (mCamera != null) {
            try {
                mCamera.setPreviewDisplay(holder);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Camera.Size getBestSupportedSize(List<Camera.Size> sizes) {
        // 取能适用的最大的SIZE
        Camera.Size largestSize = sizes.get(0);
        int largestArea = sizes.get(0).height * sizes.get(0).width;
        for (Camera.Size s : sizes) {
            int area = s.width * s.height;
            if (area > largestArea) {
                largestArea = area;
                largestSize = s;
            }
        }
        return largestSize;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // SurfaceView尺寸发生改变时（首次在屏幕上显示同样会调用此方法），初始化mCamera参数，启动Camera预览


        try {
            mCamera.startPreview();
        } catch (Exception e) {
            if (mCamera != null) {
                mCamera.release();
                mCamera = null;
            }
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // SurfaceView销毁时，取消Camera预览
        if (mCamera != null) {
            mCamera.stopPreview();
        }
    }

    private PictureCallback mCallback;

    public void takePicture(PictureCallback callback) {
        mCallback = callback;
        mCamera.takePicture(new Camera.ShutterCallback() {
            @Override
            public void onShutter() {
                // 一般显示进度条
            }
        }, null, mJpeg);
    }

    private String save(byte[] data) {
        String fileName = System.currentTimeMillis() + ".jpg";
        File dir = new File(KeyConst.cameraSavePath);
        if (!dir.exists()) {
            boolean mkdirs = dir.mkdirs();
        }

        File file = new File(dir, fileName);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            fos.write(data);
            fos.close();
            return file.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /* 图像数据处理完成后的回调函数 */
    private Camera.PictureCallback mJpeg = new Camera.PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            // 保存图片
            String save = save(data);
            mCallback.onPictureTaken(save);
        }
    };

    public interface PictureCallback {
        void onPictureTaken(String path);
    }

    public void pauseCamera() {
        // 释放相机
        if (mCamera != null) {
            mCamera.release();
            mCamera = null;
        }
        isFirst = false;
    }

    /**
     * 开启相机
     */
    public void resumeCamera() {
        if (isFirst) {
            return;
        }

        // 开启相机
        // SurfaceView创建时，建立Camera和SurfaceView的联系
        int CammeraIndex = findFrontCamera();//默认启动前置摄像头
        if (CammeraIndex == -1) {
            CammeraIndex = findBackCamera();
        }
        mCamera = Camera.open(CammeraIndex);

        Camera.Parameters parameters = mCamera.getParameters();// 获取mCamera的参数对象
        Camera.Size largestSize = getBestSupportedSize(parameters.getSupportedPreviewSizes());
        parameters.setPreviewSize(largestSize.height, largestSize.width);// 设置预览图片尺寸
        parameters.setPictureSize(largestSize.height, largestSize.width);
        mSize = largestSize;
        mCamera.setParameters(parameters);
        isFirst = true;
    }

    /**
     * 查找前置摄像头
     *
     * @return
     */
    private int findFrontCamera() {
        int cameraCount = 0;
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        cameraCount = Camera.getNumberOfCameras(); // get cameras number

        for (int camIdx = 0; camIdx < cameraCount; camIdx++) {
            Camera.getCameraInfo(camIdx, cameraInfo); // get camerainfo
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                // 代表摄像头的方位，目前有定义值两个分别为CAMERA_FACING_FRONT前置和CAMERA_FACING_BACK后置
                return camIdx;
            }
        }
        return -1;
    }

    /**
     * 查找后置摄像头
     *
     * @return
     */
    private int findBackCamera() {
        int cameraCount = 0;
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        cameraCount = Camera.getNumberOfCameras(); // get cameras number

        for (int camIdx = 0; camIdx < cameraCount; camIdx++) {
            Camera.getCameraInfo(camIdx, cameraInfo); // get camerainfo
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                // 代表摄像头的方位，目前有定义值两个分别为CAMERA_FACING_FRONT前置和CAMERA_FACING_BACK后置
                return camIdx;
            }
        }
        return -1;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        Point p = DisplayUtil.getScreenMetrics(getContext());
//        float wb = (float)(p.x)/mSize.height;
//        Log.i("tag00","p.x:"+p.x + "  p.y:"+p.y +" largestSize.w:"+mSize.width +"  largestSize.h:"+mSize.height +" wb: "+wb +" hb: "+hb);

        int wms = MeasureSpec.makeMeasureSpec((int) (mSize.width*2.3), MeasureSpec.EXACTLY);
        int hms = MeasureSpec.makeMeasureSpec((int) (mSize.height*2.65), MeasureSpec.EXACTLY);
        super.onMeasure(wms, hms);
    }

//    @Override
//    public void draw(Canvas canvas) {
//        Path path = new Path();  //用矩形表示SurfaceView宽高
//        //指定屏幕尺寸，未考虑适配各种屏幕
//
//        int height = 738;
//        int width = 761;
////        Point p = DisplayUtil.getScreenMetrics(getContext());
////
////        int height = p.x;
////        int width = p.y;
//
//        RectF rect = new RectF(-getLeft(), 0, width - getLeft(), height);//15.0f即是圆角半径
//        path.addRoundRect(rect, 15.0f, 15.0f, Path.Direction.CCW);     //裁剪画布，并设置其填充方式
//        canvas.clipPath(path, Region.Op.REPLACE);
//        super.draw(canvas);
//    }
}
