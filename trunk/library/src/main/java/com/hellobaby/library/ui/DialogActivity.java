package com.hellobaby.library.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.hellobaby.library.R;


/**
 * Created by zwj on 2016/9/29.
 * description:
 */

public class DialogActivity extends Activity {

    private Button btnOk;
    private Button btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay(); // 为获取屏幕宽、高
        android.view.WindowManager.LayoutParams p = getWindow().getAttributes();
//        p.height = (int) (d.getHeight() * 0.5); // 高度设置为屏幕的0.8
        p.width = (int) (d.getWidth() * 0.9); // 宽度设置为屏幕的0.9
        getWindow().setAttributes(p);

        initView();
        initOnclick();
    }



    private void initView() {
        btnOk = (Button) findViewById(R.id.dialog_btn_ok);
        btnCancel = (Button) findViewById(R.id.dialog_btn_cancel);
    }
    private void initOnclick() {
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
