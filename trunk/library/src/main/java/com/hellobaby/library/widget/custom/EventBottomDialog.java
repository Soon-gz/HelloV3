package com.hellobaby.library.widget.custom;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.flyco.dialog.widget.base.BottomBaseDialog;
import com.foamtrace.photopicker.Image;
import com.hellobaby.library.R;
import com.hellobaby.library.ui.event.BaseEventDetailActivity;
import com.hellobaby.library.widget.BottomDialogUtils;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

public class EventBottomDialog extends BottomBaseDialog<EventBottomDialog> implements View.OnClickListener {
    TextView tv_babyCount;
    TextView tv_parentCount;
    ImageView bt_babydel;
    ImageView bt_parentdel;
    private Context context;
    public static final int EVENTJOIN_BTN=4;

    public EventBottomDialog(Context context, View animateView) {
        super(context, animateView);
    }

    public EventBottomDialog(Context context) {
        super(context);
    }

    @Override
    public View onCreateView() {
//        showAnim(new FlipVerticalSwingEnter());
        dismissAnim(null);
        View inflate = View.inflate(mContext, R.layout.popup_joinevent, null);
        bt_babydel = (ImageView) inflate.findViewById(R.id.joinevent_bt_babydel);
        bt_babydel.setOnClickListener(this);
        inflate.findViewById(R.id.joinevent_bt_babyadd).setOnClickListener(this);
        tv_babyCount = (TextView) inflate.findViewById(R.id.joinevent_tv_babycount);
        bt_parentdel = (ImageView) inflate.findViewById(R.id.joinevent_bt_parentdel);
        bt_parentdel.setOnClickListener(this);
//        inflate.findViewById(R.id.joinevent_bt_parentdel).setOnClickListener(this);
        inflate.findViewById(R.id.joinevent_bt_parentadd).setOnClickListener(this);
        tv_parentCount = (TextView) inflate.findViewById(R.id.joinevent_tv_parentcount);
        inflate.findViewById(R.id.joinevent_tv_ok).setOnClickListener(this);
        inflate.findViewById(R.id.joinevent_tv_cancel).setOnClickListener(this);
        return inflate;
    }

    @Override
    public void setUiBeforShow() {
    }

    @Override
    public void onClick(View v) {
        if (listner == null) {
            return;
        }
        int i = v.getId();
        if (i == R.id.joinevent_bt_babydel) {
//            listner.onItemClick(0);
            tv_babyCount.setText(Integer.valueOf(tv_babyCount.getText().toString()) - 1 + "");
        } else if (i == R.id.joinevent_bt_babyadd) {
//            listner.onItemClick(1);
            tv_babyCount.setText(Integer.valueOf(tv_babyCount.getText().toString()) + 1 + "");
        } else if (i == R.id.joinevent_bt_parentdel) {
//            listner.onItemClick(2);
            tv_parentCount.setText(Integer.valueOf(tv_parentCount.getText().toString()) - 1 + "");
        } else if (i == R.id.joinevent_bt_parentadd) {
            tv_parentCount.setText(Integer.valueOf(tv_parentCount.getText().toString()) + 1 + "");
//            listner.onItemClick(3);
        } else if (i == R.id.joinevent_tv_ok) {
            ((BaseEventDetailActivity)mContext).joinEvent(tv_babyCount.getText().toString(),tv_parentCount.getText().toString());
            listner.onItemClick();
            dismiss();
        } else if (i == R.id.joinevent_tv_cancel) {
//            listner.onItemClick(5);
            dismiss();
        } else {
        }
        if (tv_babyCount.getText().toString().equals("1")||tv_babyCount.getText().toString().equals("0")) {
            tv_babyCount.setText("1");
            bt_babydel.setEnabled(false);
            bt_babydel.setImageResource(R.drawable.joinevent_del_unclick);
        } else {
            bt_babydel.setEnabled(true);
            bt_babydel.setImageResource(R.drawable.selector_joinevent_del);
        }
        if (tv_parentCount.getText().toString().equals("-1")||tv_parentCount.getText().toString().equals("0")) {
            tv_parentCount.setText("0");
            bt_parentdel.setEnabled(false);
            bt_parentdel.setImageResource(R.drawable.joinevent_del_unclick);
        } else {
            bt_parentdel.setEnabled(true);
            bt_parentdel.setImageResource(R.drawable.selector_joinevent_del);
        }
    }

    public onItemClickLitener getListner() {
        return listner;
    }

    public EventBottomDialog setListner(onItemClickLitener listner) {
        this.listner = listner;
        return this;
    }

    private onItemClickLitener listner;

    public interface onItemClickLitener {
        void onItemClick();
    }

    public static EventBottomDialog getEventJoinBottomDialog(final Activity activity, View animateView, final onItemClickLitener onItemClickListener) {
        final EventBottomDialog dialog = new EventBottomDialog(activity, animateView);
        dialog.setListner(onItemClickListener).show();
        return dialog;
    }
}
