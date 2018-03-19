package com.abings.baby.ui.publishpicture;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.hellobaby.library.Const;
import com.abings.baby.R;
import com.abings.baby.camera.CustomView.PreviewSurfaceView;
import com.abings.baby.camera.Task.ImageDecodeTask;
import com.abings.baby.camera.Task.SaveImageTask;

import java.util.ArrayList;

import butterknife.OnClick;

import static com.hellobaby.library.Const.FLAG_SAVE_IMAGE;


public class PhotoActivity extends Activity {

    private Camera.Parameters parameters = null;
    private Camera camera;
    private PreviewSurfaceView previewSurfaceView;
    private FrameLayout previewFrame;
    private ImageView captureButton;
    private ImageView switchCameraButton;
    private ImageView finishButton;
    private ImageView leftButton;
    private TextView textView;
    private int maxPhotos=9;
    //拍照结果
    private ArrayList<String> mResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mResults=new ArrayList<>();
        setContentView(R.layout.activity_takephoto);
        previewFrame = (FrameLayout)findViewById(R.id.camera_preview);
        captureButton = (ImageView)findViewById(R.id.button_capture);
        switchCameraButton = (ImageView)findViewById(R.id.button_switch);
        finishButton =(ImageView)findViewById(R.id.titleBase_iv_right);
        leftButton=(ImageView)findViewById(R.id.titleBase_iv_left);
        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                data.putStringArrayListExtra("select_result", mResults);
                setResult(RESULT_CANCELED, data);
                finish();
            }
        });
        textView=(TextView)findViewById(R.id.count);
        captureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                camera.takePicture(null, null, pictureCallback);
            }
        });

        switchCameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Const.BACK_CAMERA_IN_USE) {
                    showFrontCamera();
                } else {
                    showBackCamera();
                }
            }
        });
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                data.putStringArrayListExtra("select_result", mResults);
                setResult(RESULT_OK, data);
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
          showBackCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        removePreview();
        releaseCamera();
    }

    private void showBackCamera() {
        releaseCamera();
        camera = getCameraInstance(Camera.CameraInfo.CAMERA_FACING_BACK);
        if (camera != null) {
            if (previewSurfaceView != null) {
                removePreview();
            }
            Const.BACK_CAMERA_IN_USE = true;
            attachCameraToPreview();
        }
    }

    private void showFrontCamera() {
        int numberOfCameras = Camera.getNumberOfCameras();

        if (numberOfCameras > 1) {
            releaseCamera();
            camera = getCameraInstance(Camera.CameraInfo.CAMERA_FACING_FRONT);
            Const.BACK_CAMERA_IN_USE = false;
            removePreview();
            attachCameraToPreview();
        } else {
            Toast.makeText(PhotoActivity.this, "Front camera not available", Toast.LENGTH_SHORT).show();
        }
    }

    private void attachCameraToPreview() {
        previewSurfaceView = new PreviewSurfaceView(this, camera);
        previewFrame.addView(previewSurfaceView);
    }

    private void removePreview() {
        previewFrame.removeView(previewSurfaceView);
    }

    private void releaseCamera(){
        if (camera != null){
            camera.release();        // release the camera for other applications
            camera = null;
        }
    }

    public Camera getCameraInstance(int cameraId){
        Camera c = null;
        try {
            c = Camera.open(cameraId); // attempt to get a Camera instance
        }
        catch (Exception e){
            // Camera is not available (in use or does not exist)
            e.printStackTrace();
        }
        return c; // returns null if camera is unavailable
    }

    private Camera.PictureCallback pictureCallback = new Camera.PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {

            captureButton.setEnabled(false);

            camera.stopPreview();

            //decodes and returns the bitmap if this flag is set in constants.
            if(Const.FLAG_DECODE_BITMAP){
                new ImageDecodeTask(PhotoActivity.this, data, previewFrame.getHeight(), previewSurfaceView.getHeight()).execute();
            }else{
                //the method decodeBitmapComplete will not be called if the task is not started. So enable the button
                captureButton.setEnabled(true);
            }

            camera.startPreview();
        }
    };

    public void decodeBitmapComplete(Bitmap bitmap){
        captureButton.setEnabled(true);
        //The decoded bitmap is passed as a parameter. Use this for all further operations.
        if(bitmap != null){

            //Save the image to disk if this flag is set
            if(FLAG_SAVE_IMAGE){
                new SaveImageTask(this).execute(bitmap);
            }
        }
        //set the bitmap to null if it is no longer needed
        bitmap = null;

    }

    public void fileSaveComplete(String path){
       int count= Integer.parseInt(textView.getText().toString());
        if(count!=0){
            mResults.add(path);
            textView.setText(--count+"");
        }
       else{
            Toast.makeText(PhotoActivity.this,"拍摄已经达到9张",Toast.LENGTH_SHORT).show();
        }
    }

    private void showToast(String message){
        Toast.makeText(PhotoActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    @OnClick({R.id.takephoto_btn_flus1,R.id.takephoto_btn_flus2,R.id.takephoto_btn_flus3})
    public void onFlushClick(View view){
        switch(view.getId()){
            case R.id.takephoto_btn_flus1:
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                camera.setParameters(parameters);
                break;
            case R.id.takephoto_btn_flus2:
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                camera.setParameters(parameters);
                break;
            case R.id.takephoto_btn_flus3:
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_AUTO);
                camera.setParameters(parameters);
                break;
        }
        removePreview();
        attachCameraToPreview();
    }
}
