package com.abings.baby.camera.Task;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.abings.baby.ui.publishpicture.PhotoActivity;
import com.abings.baby.helper.FileHelper;

import static com.hellobaby.library.Const.MEDIA_TYPE_IMAGE;


/**
 * Created by vishnu on 08/07/15.
 */
public class SaveImageTask extends AsyncTask<Bitmap, Void, String> {

    private Context context;

    public SaveImageTask(Context context){
        this.context = context;
    }

    @Override
    protected String doInBackground(Bitmap... params) {

        return FileHelper.saveFile(context, MEDIA_TYPE_IMAGE, params[0]);
    }

    @Override
    protected void onPostExecute(String path ) {
        if(context instanceof PhotoActivity){
            ((PhotoActivity)context).fileSaveComplete(path);
        }
    }
}
