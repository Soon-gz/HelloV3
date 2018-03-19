package com.hellobaby.timecard.ui.main;

import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.hellobaby.timecard.R;
import com.hellobaby.timecard.ZSApp;


/**
 * Created by zwj on 2016/10/18.
 * description :接送设置
 */

public class PopupWindowAgentSet {
    private PopupWindow popupWindow;

    private static PopupWindowAgentSet ppw;


    private PopupWindowAgentSet() {
    }

    public static PopupWindowAgentSet getInstance() {
        if (ppw == null) {
            ppw = new PopupWindowAgentSet();
        }
        return ppw;
    }

    public void showPPWPublish(Activity activity) {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
            popupWindow = null;
        }
        // 初始化布局文件
        View rootView = LayoutInflater.from(activity).inflate(R.layout.ppw_agent_set, null);

        TextView tvJie = (TextView) rootView.findViewById(R.id.ppwAgentSet_tv_jie);
        TextView tvSong = (TextView) rootView.findViewById(R.id.ppwAgentSet_tv_song);


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

    private AgentOnClickListener clickListener;

    public interface AgentOnClickListener {
        public void agentOnclick(String eventType);
    }

    public PopupWindowAgentSet setClickListener(AgentOnClickListener clickListener) {
        this.clickListener = clickListener;
        return this;
    }

    public void dismiss() {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
    }
}
