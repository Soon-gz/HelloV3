package com.abings.baby.teacher.ui.scan;

import android.widget.Toast;

import com.hellobaby.library.widget.scan.CaptureActivity;

/**
 * Created by zwj on 2016/11/30.
 * description :扫描界面
 */

public class ScanActivity extends CaptureActivity {

    @Override
    protected void scanResult(String scanResult) {
//        Intent intent = new Intent();
//        intent.putExtra(kSCAN_RESULT, scanResult);
//        setResult(kSCAN_RESULT_CODE, intent);
//        finish();
        //TODO 可以用result进行返回参数，也可以使用这里处理一些数据
        Toast.makeText(this,"扫描结果:"+scanResult,Toast.LENGTH_SHORT).show();
        finish();
    }
}
