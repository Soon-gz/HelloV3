package com.abings.baby.camera.Task;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.abings.baby.ui.publishpicture.PhotoActivity;
import com.abings.baby.helper.BitmapHelper;

import static com.hellobaby.library.Const.BACK_CAMERA_IN_USE;
import static com.hellobaby.library.Const.BACK_CAMERA_ROTATION;
import static com.hellobaby.library.Const.FLAG_DECODE_BITMAP;
import static com.hellobaby.library.Const.FRONT_CAMERA_ROTATION;


/**
 * Created by vishnu on 06/07/15.
 */
public class ImageDecodeTask extends AsyncTask<Void, Void, Bitmap> {

    private Context context;
    private byte[] data;

    private int layoutHeight;
    private int surfaceViewHeight;

//    private Bitmap bitmap;

    public ImageDecodeTask(Context context, byte[] data, int layoutHeight, int surfaceViewHeight){
        this.context = context;
        this.data = data;
        this.layoutHeight = layoutHeight;
        this.surfaceViewHeight = surfaceViewHeight;
    }

    @Override
    protected Bitmap doInBackground(Void... params) {

        //make a call to the garbage collected to force a garbage collection cycle.
        System.gc();

        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0 , data.length);

        //for back camera
        if(BACK_CAMERA_IN_USE) {
            Bitmap rotatedBitmap = BitmapHelper.rotateBitmap(bitmap, BACK_CAMERA_ROTATION, layoutHeight, surfaceViewHeight);
            bitmap = null;
            bitmap = rotatedBitmap;
        }else{
            //for front camera
            bitmap = BitmapHelper.rotateBitmap(bitmap, FRONT_CAMERA_ROTATION);
            bitmap = BitmapHelper.flip(bitmap);


            final int bitmapWidth = bitmap.getWidth();
            final int bitmapHeight = bitmap.getHeight();

            int croppedHeight = (int) ((float)(bitmapHeight * layoutHeight) / surfaceViewHeight);

            if(croppedHeight > bitmapHeight){
                //do not crop anything
                croppedHeight = bitmapHeight;
            }

            Bitmap finalBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmapWidth, croppedHeight);
            bitmap = null;
            bitmap = finalBitmap;
        }

        Log.d("decode", "decode_complete");

        this.data = null;
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {

        //checking if this task was started from main activity.
        //a callback interface can be used here and let the activity that wants to be notified implement it.
        if(context instanceof PhotoActivity && FLAG_DECODE_BITMAP){
            ((PhotoActivity)context).decodeBitmapComplete(bitmap);
        }
    }

}
