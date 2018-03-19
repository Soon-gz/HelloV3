package com.hellobaby.library.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.hellobaby.library.R;


/**
 * Created by Shuwen on 2016/9/25.
 */
public class CustomAlertDialog {
    private static AlertDialog myDialog = null;

    private static final int DIALOG_CANCEL = 5;//进入视频弹出的dialog取消键
    private static final int DIALOG_SURE = 6;//进入视频弹窗的dialog确定键
    private static final int DIALOG_UUID = 7;//确认uuid


    public static void dialogExSureCancel(String mes, final Context context, final OnDialogClickListener onDialogClickListener) {
        createDialog(context, R.layout.alert_dialog_layout);
        TextView textView = (TextView) myDialog.getWindow().findViewById(R.id.dialog_msg);
        textView.setText(mes);
        setItemOnClickListener(R.id.dialog_cancel,onDialogClickListener,DIALOG_CANCEL,null);
        setItemOnClickListener(R.id.dialog_sure,onDialogClickListener,DIALOG_SURE,null);
    }


    /**
     * 创建dialog实例
     * @param context 传入的上下，在其上显示弹窗
     * @param layoutId 自定义布局id
     */
    private static void createDialog(Context context,int layoutId){
        myDialog = new AlertDialog.Builder(context).create();
        myDialog.show();
        Window window = myDialog.getWindow();
        window.setWindowAnimations(R.style.dialog_anim);
        myDialog.setCanceledOnTouchOutside(false);
        myDialog.getWindow().setContentView(layoutId);
    }


    /**
     * 网络设置对话框
     * @param context
     * @param onItemClickListener
     */
    public static void dialogNetSet(Context context,String msg,onItemClickListener onItemClickListener){
        createDialog(context,R.layout.alert_dialog_layout_kaoqin);
        setItemOnClickListener(R.id.dialog_sure,onItemClickListener,DIALOG_SURE,null);
        setItemOnClickListener(R.id.dialog_cancel,onItemClickListener,DIALOG_CANCEL,null);
        ((TextView)myDialog.getWindow().findViewById(R.id.dialog_msg)).setText(msg);
    }

    /**
     * 考勤机输入机器码提示
     * @param context
     */
    public static void dialogInputUUID(final Context context, onItemClickListener onItemClickListener){
        createDialog(context,R.layout.alert_dialog_layout_inputuuid);
        final EditText uuid_ed = (EditText)myDialog.getWindow().findViewById(R.id.dialog_input);
        setItemOnClickListener(R.id.dialog_sure,onItemClickListener,DIALOG_UUID,uuid_ed);
    }


    /**
     * 设置指定项点击事件
     * @param id
     * @param onDialogItemClickListener
     * @param position
     */
    private static void setItemOnClickListener(final int id, final onItemClickListener onDialogItemClickListener, final int position, final EditText uuid){
        myDialog.getWindow()
                .findViewById(id)
                .setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        if (onDialogItemClickListener == null){
                            myDialog.dismiss();
                            return;
                        }
                        switch (position){
                            case DIALOG_CANCEL:
                                ((OnDialogClickListener)onDialogItemClickListener).cancle();
                                break;
                            case DIALOG_SURE:
                                ((OnDialogClickListener)onDialogItemClickListener).doSomeThings();
                                break;
                            case DIALOG_UUID:
                                ((OndialogUUIDListener)onDialogItemClickListener).sureUUID(uuid.getText().toString().trim());
                                break;
                        }
                        myDialog.dismiss();
                        myDialog = null;
                    }

                });
    }


    private interface onItemClickListener{

    }

    /**
     * 创建只带确定按钮的提示弹窗
     * @param context
     */
    public static void dialogWithSure(Context context,String msg,OnDialogClickListener onDialogClickListener){
        createDialog(context,R.layout.alert_dialog_sure);
        TextView textView = (TextView) myDialog.getWindow().findViewById(R.id.dialog_sure_msg);
        textView.setText(msg);
        setItemOnClickListener(R.id.dialog_sure,onDialogClickListener,DIALOG_SURE,null);
    }


    public interface OnDialogClickListener extends onItemClickListener{
        void doSomeThings();
        void cancle();
    }

    public interface OndialogUUIDListener extends onItemClickListener{
        void sureUUID(String uuid);
    }
}
