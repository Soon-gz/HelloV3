package com.abings.baby.helper;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;


import com.hellobaby.library.Const;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.hellobaby.library.Const.FAIL;

/**
 * Created by vishnu on 06/07/15.
 */
public class FileHelper {

    public static String saveFile(Context context, int type, Bitmap bitmap){

        File pictureFile = getOutputMediaFile(type);

        if(pictureFile == null){
            Log.d("Save failed", "Error creating media file, check storage permissions:");
            return FAIL;
        }

        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            fos.write(BitmapHelper.getByteArrayFromBitmap(bitmap));
            fos.close();

            sendBroadcastToRefreshGallery(context, pictureFile);

            return pictureFile.toString();
            
        } catch (FileNotFoundException e) {
            Log.d("Save failed", "File not found: " + e.getMessage());
            return FAIL;
        } catch (IOException e) {
            Log.d("Save failed", "Error accessing file: " + e.getMessage());
            return FAIL;
        }
    }

    private static void sendBroadcastToRefreshGallery(Context context, File pictureFile) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Intent mediaScanIntent = new Intent(
                    Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri contentUri = Uri.fromFile(pictureFile);
            mediaScanIntent.setData(contentUri);
            context.sendBroadcast(mediaScanIntent);
        } else {
            context.sendBroadcast(new Intent(
                    Intent.ACTION_MEDIA_MOUNTED,
                    Uri.parse("file://"
                            + Environment.getExternalStorageDirectory())));
        }
    }

    private static File getOutputMediaFile(int type){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), Const.DIRECTORY_NAME);
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d(Const.DIRECTORY_NAME, "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat(Const.DATE_PATTERN).format(new Date());
        File mediaFile;
        if (type == Const.MEDIA_TYPE_IMAGE){
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    Const.IMAGE_POSTFIX + timeStamp + Const.IMAGE_EXTENSION);
        } else if(type == Const.MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    Const.VIDEO_POSTFIX + timeStamp + Const.VIDEO_EXTENSION);
        } else {
            return null;
        }

        return mediaFile;
    }
}
