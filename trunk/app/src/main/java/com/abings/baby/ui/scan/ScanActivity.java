package com.abings.baby.ui.scan;

import android.content.Intent;

import com.hellobaby.library.widget.scan.CaptureActivity;

/**
 * Created by zwj on 2016/11/9.
 * description :扫描界面
 */

public class ScanActivity extends CaptureActivity {

    @Override
    protected void scanResult(String scanResult) {
        Intent intent = new Intent();
        intent.putExtra(kSCAN_RESULT,scanResult);
        setResult(kSCAN_RESULT_CODE,intent);
        finish();
    }
}
