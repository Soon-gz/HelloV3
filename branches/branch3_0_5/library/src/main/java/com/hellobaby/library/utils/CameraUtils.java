package com.hellobaby.library.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;


import com.hellobaby.library.widget.crop.FileUtils;

import java.io.File;

/**
 * Created by zwj on 2017/1/13.
 * description :
 */

public class CameraUtils {
    public static final int REQUEST_TAKEPHOTO_CODE = 30132;

    public static String dispatchTakePictureIntent(Activity activity) {
        String mCurrentPhotoPath = null;
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(activity.getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = FileUtils.getOutputMediaFileUri();
            mCurrentPhotoPath = "file:" + (photoFile != null ? photoFile.getAbsolutePath() : "");
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(activity, activity.getApplication().getPackageName() + ".provider", photoFile);
//              Uri photoURI = Uri.fromFile(photoFile)
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                activity.startActivityForResult(takePictureIntent, REQUEST_TAKEPHOTO_CODE);
            }
        }
        return mCurrentPhotoPath;
    }
}
