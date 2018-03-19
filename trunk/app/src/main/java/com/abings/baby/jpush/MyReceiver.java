package com.abings.baby.jpush;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.abings.baby.BuildConfig;
import com.abings.baby.ZSApp;
import com.abings.baby.ui.attendance.AttendanceActivity;
import com.abings.baby.ui.attendance.AttendanceHistoryListActivity;
import com.abings.baby.ui.carebaby.CareBabySureActivity;
import com.abings.baby.ui.measuredata.remark.ReMarkActivity;
import com.abings.baby.ui.message.MsgCenterActivity;
import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.hellobaby.library.Const;
import com.hellobaby.library.data.DataManager;
import com.hellobaby.library.data.model.JPushModel;
import com.hellobaby.library.data.model.PatchBean;
import com.hellobaby.library.data.remote.APIService;
import com.hellobaby.library.data.remote.rx.RxThread;
import com.hellobaby.library.data.remote.rx.SubscriberBase;
import com.hellobaby.library.utils.DownloadHelper;
import com.hellobaby.library.utils.LogZS;
import com.hellobaby.library.utils.StringUtils;
import com.hellobaby.library.widget.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import cn.jpush.android.api.JPushInterface;
import rx.Subscriber;

/**
 * 自定义接收器
 * 
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class MyReceiver extends BroadcastReceiver{
	private static final String TAG = "tag00";

	@Override
	public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
		LogZS.i("[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));
		
        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            LogZS.i("[MyReceiver] 接收Registration Id : " + regId);
            //send the Registration Id to your server...

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
        	LogZS.i("[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
        	LogZS.i("[MyReceiver] 接收到推送下来的自定义消携带参数: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
			/**
			 * 热更新，当需要及时更新bug时，使用友盟在线参数和极光推送来修复bug
			 */
			String jsonStr = bundle.getString(JPushInterface.EXTRA_EXTRA);
			if (!StringUtils.isEmpty(jsonStr)){
				PatchBean bean = new Gson().fromJson(jsonStr,PatchBean.class);
				if (bean.isIsNeedUpdate()){
					String url = Const.getBabyAndfixURL(bean.getUrl(),bean.getApp_v(),bean.getPath_v());
					LogZS.i("url:"+url);
					if (bean.getApp_v().equals(BuildConfig.VERSION_NAME)){
						String fileName = Const.ANDFIX_FILE_PATH + Const.getBabyAndfixFileName(bean.getApp_v(),bean.getPath_v());
						LogZS.i("fileName:"+fileName);
						File file = new File(fileName);
						if (file.exists()){
							file.delete();
						}
						DownloadHelper.downloadFile(context,url,fileName)
								.compose(RxThread.<String>subscribe_Io_Observe_On())
								.subscribe(new Subscriber<String>() {
									@Override
									public void onCompleted() {
									}

									@Override
									public void onError(Throwable e) {

									}

									@Override
									public void onNext(String s) {
										if (!StringUtils.isEmpty(s)){
											try {
												ZSApp.getInstance().getPatchManager().addPatch(s);
											} catch (IOException e) {
												e.printStackTrace();
											}
										}else {
											LogZS.e("推送更新   修复文件下载失败！");
										}
									}
								});
					}else{
						LogZS.i("当前版本"+BuildConfig.VERSION_NAME+"暂无热更新需求！");
					}
				}

			}


        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            LogZS.i("[MyReceiver] 接收到推送下来的通知:"+JPushInterface.EXTRA_EXTRA);
            String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);

            JPushModel jPushModel = new Gson().fromJson(extras,JPushModel.class);
            ZSApp.getInstance().setjPushModel(jPushModel);
			if ("4".equals(ZSApp.getInstance().getjPushModel().getJsonObject().getType())){
				EventBus.getDefault().post(jPushModel);
				ZSApp.getInstance().setCarebaby(true);
			}
			ZSApp.isAgreeClicked = false;
			if ("3".equals(ZSApp.getInstance().getjPushModel().getJsonObject().getType()) && ZSApp.isAtMainActivity && !ZSApp.isAgreeClicked){
				//打开自定义的Activity
				ZSApp.isAgreeClicked = true;
				Intent i = new Intent(context, CareBabySureActivity.class);
				i.putExtra(Const.AGREELIST, JSON.toJSONString(ZSApp.getInstance().getjPushModel()));
				i.putExtra(Const.FROMWHERE,"receiver");
				i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
				context.startActivity(i);
			}

            LogZS.i("[MyReceiver] 接收到推送下来的通知的内容: " + jPushModel.getJsonObject().toString());

        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {

			if (ZSApp.getInstance().getjPushModel() != null){
				switch (ZSApp.getInstance().getjPushModel().getJsonObject().getType()){
					//家长的收件箱
					case "2":
						Intent msg = new Intent(context, MsgCenterActivity.class);
						msg.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
						context.startActivity(msg);
						break;
					//请求关注宝宝
					case "3":
						if (!ZSApp.isAgreeClicked){
							//打开自定义的Activity
							Intent i = new Intent(context, CareBabySureActivity.class);
							i.putExtra(Const.AGREELIST, JSON.toJSONString(ZSApp.getInstance().getjPushModel()));
							i.putExtra(Const.FROMWHERE,"receiver");
							i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
							context.startActivity(i);
						}
						break;
					//成功关注他人宝宝的通知
					case "4":
						break;
					//宝宝评语
					case "5":
						Intent babypy = new Intent(context, ReMarkActivity.class);
						babypy.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
						context.startActivity(babypy);
						break;
					//签到、离校
					case "6":
						Intent babyLift = new Intent(context, AttendanceActivity.class);
						babyLift.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
						context.startActivity(babyLift);
						break;
				}

			}

        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
            LogZS.i("[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
            //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..
        	
        } else if(JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
        	boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
        	Log.w(TAG, "[MyReceiver]" + intent.getAction() +" connected state change to "+connected);
        } else {
        	LogZS.i("[MyReceiver] Unhandled intent - " + intent.getAction());
        }
	}

	// 打印所有的 intent extra 数据
	private static String printBundle(Bundle bundle) {
		StringBuilder sb = new StringBuilder();
		for (String key : bundle.keySet()) {
			if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
				sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
			}else if(key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)){
				sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
			} else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
				if (TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_EXTRA))) {
					LogZS.i("This message has no Extra data");
					continue;
				}

				try {
					JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
					Iterator<String> it =  json.keys();

					while (it.hasNext()) {
						String myKey = it.next().toString();
						sb.append("\nkey:" + key + ", value: [" +
								myKey + " - " +json.optString(myKey) + "]");
					}
				} catch (JSONException e) {
					Log.e(TAG, "Get message extra JSON error!");
				}

			} else {
				sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
			}
		}
		return sb.toString();
	}
	
}
