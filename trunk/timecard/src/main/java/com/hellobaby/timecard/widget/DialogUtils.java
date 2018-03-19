package com.hellobaby.timecard.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hellobaby.timecard.R;

/**
 * Created by zwj on 2017/3/23.
 * description :
 */

public class DialogUtils {

    public static void showTCMDialog(Context context,String text, final View.OnClickListener onClickListener) {
        LayoutInflater inflater = LayoutInflater.from(context.getApplicationContext());
        View layout = inflater.inflate(R.layout.tcm_dialog_layout, null);
        TextView tvMsg = (TextView) layout.findViewById(R.id.tcmDialog_tv_msg);
        TextView tvYes = (TextView) layout.findViewById(R.id.tcmDialog_tv_yes);
        TextView tvNo = (TextView) layout.findViewById(R.id.tcmDialog_tv_no);
        tvMsg.setText(text);

        AlertDialog.Builder builder = new AlertDialog.Builder(context,R.style.Theme_Transparent);
        final AlertDialog dialog = builder.create();
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setView(layout);
        dialog.show();
//        dialog.setContentView(layout);
        tvYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onClickListener!=null){
                    onClickListener.onClick(v);
                }
                dialog.dismiss();
            }
        });
        tvNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}
