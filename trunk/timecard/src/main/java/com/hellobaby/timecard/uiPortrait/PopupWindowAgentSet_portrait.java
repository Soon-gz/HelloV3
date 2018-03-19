package com.hellobaby.timecard.uiPortrait;

import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.hellobaby.timecard.R;
import com.hellobaby.timecard.ZSApp;
import com.hellobaby.timecard.ui.main.PopupWindowAgentSet;

/**
 * Created by ShuWen on 2017/8/3.
 */

public class PopupWindowAgentSet_portrait {
    private PopupWindow popupWindow;

    private static PopupWindowAgentSet_portrait ppw;


    private PopupWindowAgentSet_portrait() {
    }

    public static PopupWindowAgentSet_portrait getInstance() {
        if (ppw == null) {
            ppw = new PopupWindowAgentSet_portrait();
        }
        return ppw;
    }

    public void showPPWPublish(Activity activity) {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
            popupWindow = null;
        }
        // 初始化布局文件
        View rootView = LayoutInflater.from(activity).inflate(R.layout.ppw_agent_set_portrait, null);

        ImageView tvJie = (ImageView) rootView.findViewById(R.id.ppwAgentSet_tv_jie);
        ImageView tvSong = (ImageView) rootView.findViewById(R.id.ppwAgentSet_tv_song);


        tvJie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ZSApp.getInstance().setEventTypeJie();
                if(clickListener!=null){
                    clickListener.agentOnclick("1");
                }
            }
        });
        tvSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ZSApp.getInstance().setEventTypeSong();
                if(clickListener!=null){
                    clickListener.agentOnclick("0");
                }
            }
        });


        popupWindow = new PopupWindow(rootView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        // 设置此参数获得焦点
        popupWindow.setFocusable(true);
        popupWindow.showAtLocation(activity.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);

        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }

    private PopupWindowAgentSet_portrait.AgentOnClickListener clickListener;

    public interface AgentOnClickListener {
        public void agentOnclick(String eventType);
    }

    public PopupWindowAgentSet_portrait setClickListener(PopupWindowAgentSet_portrait.AgentOnClickListener clickListener) {
        this.clickListener = clickListener;
        return this;
    }

    public void dismiss() {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
    }
}
