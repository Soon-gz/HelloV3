package com.abings.baby.ui.login;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.abings.baby.ZSApp;
import com.abings.baby.jpush.ExampleUtil;
import com.abings.baby.util.SharedPreferencesUtils;
import com.hellobaby.library.Const;
import com.hellobaby.library.data.DataManager;
import com.hellobaby.library.data.model.AppVersionModel;
import com.hellobaby.library.data.model.BabyModel;
import com.hellobaby.library.data.model.BaseModel;
import com.hellobaby.library.data.model.UserModel;
import com.hellobaby.library.data.remote.rx.Func1Class;
import com.hellobaby.library.data.remote.rx.RxThread;
import com.hellobaby.library.data.remote.rx.SubscriberBase;
import com.hellobaby.library.data.remote.rx.SubscriberClass;
import com.hellobaby.library.injection.ActivityContext;
import com.hellobaby.library.ui.base.BasePresenter;
import com.tencent.bugly.crashreport.CrashReport;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import rx.Observable;

/**
 * Created by zwj on 2016/9/28.
 * description :
 */

public class LoginPresenter extends BasePresenter<LoginMvpView> {
    private final DataManager mDataManager;
    private String mUserId;
    private static final String TAG = "tag00";
    private Context context;


    @Inject
    public LoginPresenter(DataManager dataManager, @ActivityContext Context context) {
        this.context = context;
        mDataManager = dataManager;
    }

    /**
     * 登录
     *
     * @param name 手机号
     * @param pwd  密码
     */
    public void login(final String name, final String pwd) {
        resetSubscription();
//        bMvpView.showProgress(true);
        mDataManager.checkLoginUser(name, pwd)
                .compose(RxThread.<BaseModel<String>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<String>(bMvpView) {
                    @Override
                    protected void callSuccess(String token) {
                        DataManager.token = token;
                        resetSubscription();
//                        bMvpView.showProgress(true);
                        //继续登录
                        mDataManager.checkToken()
                                .flatMap(new Func1Class<String, BaseModel<List<BabyModel>>>(bMvpView) {
                                    @Override
                                    protected Observable<BaseModel<List<BabyModel>>> callSuccess(String userId) {
                                        mUserId = userId;
                                        //userId 作为bugly显示的id
                                        Log.i("tag00", "正在登陆的id：" + userId);
                                        CrashReport.setUserId(mUserId);
                                        ZSApp.getInstance().setUserId(userId);
                                        return mDataManager.selectTUserbabyById(userId);
                                    }
                                })
                                .flatMap(new Func1Class<List<BabyModel>, BaseModel<UserModel>>(bMvpView) {
                                    @Override
                                    protected Observable<BaseModel<UserModel>> callSuccess(List<BabyModel> listBaseModel) {
                                        if (listBaseModel.size() <= 0) {
                                            bMvpView.toNeedBaby();
                                            return null;
                                        } else {
                                            ZSApp.getInstance().setBabyModel(listBaseModel.get(0));
                                            ZSApp.getInstance().setListBaby(listBaseModel);
                                            Log.i("tag00", "拥有宝宝");
                                            setTag(mUserId);
                                            setAlias();

                                            return mDataManager.selectUserExtendByUBId(mUserId, ZSApp.getInstance().getBabyId());
                                        }
                                    }
                                })
                                .compose(RxThread.<BaseModel<UserModel>>subscribe_Io_Observe_On())
                                .subscribe(new SubscriberClass<UserModel>(bMvpView) {
                                    @Override
                                    protected void callSuccess(UserModel userModel) {
                                        userModel.setSelected(true);//设置为选中
                                        ZSApp.getInstance().setLoginUser(userModel);
                                        bMvpView.loginSuccess();
                                    }
                                });
                    }
                });

    }

    /**
     * 设置Alias回调方法
     */
    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs;
            switch (code) {
                case 0:
                    logs = "Set alias success";
                    Log.i(TAG, logs);
                    break;
                case 6002:
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                    Log.i(TAG, logs);
                    if (ExampleUtil.isConnected(context)) {
                        mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
                    } else {
                        Log.i(TAG, "No network");
                    }
                    break;
                default:
                    logs = "Failed with errorCode = " + code;
                    Log.e(TAG, logs);
            }
//            ExampleUtil.showToast(logs, getApplicationContext());
        }
    };

    private final TagAliasCallback mTagsCallback = new TagAliasCallback() {

        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs;
            switch (code) {
                case 0:
                    logs = "Set tag success";
                    Log.i(TAG, logs);
                    break;

                case 6002:
                    logs = "Failed to set tags due to timeout. Try again after 60s.";
                    Log.i(TAG, logs);
                    if (ExampleUtil.isConnected(context)) {
                        mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_TAGS, tags), 1000 * 60);
                    } else {
                        Log.i(TAG, "No network");
                    }
                    break;

                default:
                    logs = "Failed with errorCode = " + code;
                    Log.e(TAG, logs);
            }

        }

    };

    private static final int MSG_SET_ALIAS = 1001;
    private static final int MSG_SET_TAGS = 1002;
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_SET_ALIAS:
                    Log.i(TAG, "Set alias in handler.");
                    JPushInterface.setAliasAndTags(context, (String) msg.obj, null, mAliasCallback);
                    break;
                case MSG_SET_TAGS:
                    Log.i(TAG, "Set tags in handler.");
                    JPushInterface.setAliasAndTags(context, null, (Set<String>) msg.obj, mTagsCallback);
                    break;
                default:
                    Log.i(TAG, "Unhandled msg - " + msg.what);
            }
        }
    };

    //设置别名
    public void setAlias() {
        //拼接Alias
        String alias = "U_" + (String) SharedPreferencesUtils.getParam(context, Const.keyPhoneNum, "");
        //调用JPush API设置Alias
        mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, alias));
    }

    //设置标签
    public void setTag(String mUserId) {
        Set<String> tagSet = new LinkedHashSet<String>();
        final StringBuilder builder = new StringBuilder("[");
        List<BabyModel> babyModels = ZSApp.getInstance().getListBaby();
        List<Integer> hasClassIds = new ArrayList<>();
        if (babyModels != null && babyModels.size() > 0) {
            Log.i("tag00", "当前登录用户id：" + mUserId + "当前宝宝数量：" + babyModels.size());
            for (int i = 0; i < babyModels.size(); i++) {
                int classId = babyModels.get(i).getClassId();
                if (classId == 0) {
                    if (i == babyModels.size() - 1) {
                        builder.append("]");
                    }
                    continue;
                }
                Log.i("tag00", "宝宝" + babyModels.get(i).getBabyId() + "是否当前创建：" + babyModels.get(i).isCreator());
                if (babyModels.get(i).isCreator()) {
                    hasClassIds.add(classId);
                    builder.append(classId);
                    if (i == babyModels.size() - 1) {
                        builder.append("]");
                    } else {
                        builder.append(",");
                    }
                    tagSet.add("U_" + classId);
                }
            }
        }

        if (builder.lastIndexOf("]") < 0) {
            builder.append("]");
        }
        String[] prexes = builder.toString().split(",");
        if (prexes[prexes.length - 1].equals("]")) {
            builder.deleteCharAt(builder.lastIndexOf(","));
        }
        Log.i(TAG, "上传tags： " + builder.toString());

        postJpushTags(builder.toString());
        //调用JPush API设置Tag
        mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_TAGS, tagSet));
    }


    public void postJpushTags(String tags) {
        mDataManager.postJpushTags(ZSApp.getInstance().getUserId(), tags)
                .compose(RxThread.<BaseModel<String>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<String>(bMvpView) {
                    @Override
                    protected void callSuccess(String s) {
                        Log.i("tag00", "上传tags成功。");
                    }
                });
    }


    /**
     * 获取宝宝的列表
     */
    public void getUserBabys() {
        resetSubscription();
//        bMvpView.showProgress(true);
        mDataManager.selectUserByToken()
                .flatMap(new Func1Class<UserModel, BaseModel<List<BabyModel>>>(bMvpView) {
                    @Override
                    protected Observable<BaseModel<List<BabyModel>>> callSuccess(UserModel userModel) {
                        ZSApp.getInstance().setLoginUser(userModel);
                        Log.i("ZLog", "selectUserByToken--->" + userModel.toString());
                        return mDataManager.selectTUserbabyById(String.valueOf(userModel.getUserId()));
                    }
                })
                .compose(RxThread.<BaseModel<List<BabyModel>>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberBase<BaseModel<List<BabyModel>>>(bMvpView) {
                    @Override
                    public void onNext(BaseModel<List<BabyModel>> listBaseModel) {
                        if (listBaseModel.getData().size() <= 0) {
//                            bMvpView.showMsg("对不起，您还没有宝宝");
                            bMvpView.toNeedBaby();
                        } else {
                            ZSApp.getInstance().setBabyModel(listBaseModel.getData().get(0));
                            ZSApp.getInstance().setListBaby(listBaseModel.getData());
                            Log.i("ZLog", "getUserBabys--->" + ZSApp.getInstance().getBabyModel().toString());
                            bMvpView.loginSuccess();
                        }
                    }
                });
    }

    public void emptyLogin() {
        UserModel userModel = new UserModel();
        userModel.setUserId(100);
        ZSApp.getInstance().setLoginUser(userModel);
        BabyModel babyModel = new BabyModel();
        babyModel.setBabyId(100);
        babyModel.setClassId(100);
        babyModel.setMasterId(100);
        babyModel.setSchoolId(199);
        ZSApp.getInstance().setBabyModel(babyModel);
        List<BabyModel> list = new ArrayList<>();
        list.add(babyModel);
        list.add(babyModel);
        ZSApp.getInstance().setListBaby(list);
        bMvpView.loginSuccess();
    }

    public void appVersionGet() {
        mDataManager.appVersionGetUser()
                .compose(RxThread.<BaseModel<AppVersionModel>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<AppVersionModel>(bMvpView) {
                    @Override
                    protected void callSuccess(AppVersionModel model) {
                        bMvpView.toUpdate(model);
                        ZSApp.getInstance().setAppVersionModel(model);
                    }
                });
    }

}
