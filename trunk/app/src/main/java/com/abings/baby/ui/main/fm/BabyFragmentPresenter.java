package com.abings.baby.ui.main.fm;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.abings.baby.ZSApp;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.ServiceException;
import com.google.gson.Gson;
import com.hellobaby.library.data.DataManager;
import com.hellobaby.library.data.model.AlbumModel;
import com.hellobaby.library.data.model.BaseModel;
import com.hellobaby.library.data.model.PageModel;
import com.hellobaby.library.data.model.TAlertModel;
import com.hellobaby.library.data.remote.rx.RxThread;
import com.hellobaby.library.data.remote.rx.SubscriberClass;
import com.hellobaby.library.ui.base.BasePresenter;
import com.hellobaby.library.uploadpic.UploadPicUtils;
import com.hellobaby.library.utils.LogZS;
import com.hellobaby.library.widget.ProgressDialogHelper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by Administrator on 2016/12/30.
 */

public class BabyFragmentPresenter extends BasePresenter<BabyFragmentMvpView> {
    @Inject
    DataManager dataManager;
    ProgressDialog progressDialog;
    private double uploadImageCount = 0;
    private int imgSum = 0;

    @Inject
    public BabyFragmentPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    public void getIndexCommon() {
//        bMvpView.showProgress(true);
        resetSubscription();
        String babyId = ZSApp.getInstance().getBabyId();
        LogZS.i("进来了");
        dataManager.getIndexCommon(babyId,1).compose(RxThread.<BaseModel<JSONObject>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONObject>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONObject jsonObject) {
                        LogZS.i(jsonObject.getString("result"));
                        List<AlbumModel> lists = new ArrayList<AlbumModel>();
                       JSONArray jsonArray=jsonObject.getJSONArray("result");
                        LogZS.i(jsonArray.toJSONString());
                        lists=JSON.parseArray(jsonArray.toJSONString(),AlbumModel.class);
                        bMvpView.refershIndexCommon(lists);
                        JSONArray timeArray=jsonObject.getJSONArray("resultDate");
                        bMvpView.refershIndexDate(timeArray);
                    }
                    @Override
                    protected void callError(BaseModel baseModel) {
//                        super.callError(baseModel);
                        bMvpView.refershIndexCommon(new ArrayList<AlbumModel>());
                    }
                });
//        getIndexCommonOld();
    }
////TODO 公司外测试使用
//    public void getIndexCommonOld() {
//        bMvpView.showProgress(true);
//        resetSubscription();
//        String babyId = ZSApp.getInstance().getBabyId();
//        LogZS.i("进来了");
//        dataManager.getIndexCommon(babyId).compose(RxThread.<BaseModel<JSONObject>>subscribe_Io_Observe_On())
//                .subscribe(new SubscriberClass<JSONObject>(bMvpView) {
//                    @Override
//                    protected void callSuccess(JSONObject jsonObject) {
//                        LogZS.i(jsonObject.getString("result"));
//                        List<AlbumModel> lists = new ArrayList<AlbumModel>();
//                        JSONArray jsonArray=jsonObject.getJSONArray("result");
//                        LogZS.i(jsonArray.toJSONString());
//                        lists=JSON.parseArray(jsonArray.toJSONString(),AlbumModel.class);
//                        bMvpView.refershIndexCommon(lists);
//                    }
//                    @Override
//                    protected void callError(BaseModel baseModel) {
//                        super.callError(baseModel);
//                        bMvpView.refershIndexCommon(new ArrayList<AlbumModel>());
//                    }
//                });
//    }

    public void getIndexCommonPage(final int pageNum) {
//        bMvpView.showProgress(true);
        resetSubscription();
        String babyId = ZSApp.getInstance().getBabyId();
        LogZS.i("进来了");
        dataManager.getIndexCommon(babyId,pageNum).compose(RxThread.<BaseModel<JSONObject>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONObject>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONObject jsonObject) {
                        LogZS.i(jsonObject.getString("result"));
                        List<AlbumModel> lists = new ArrayList<AlbumModel>();
                        JSONArray jsonArray=jsonObject.getJSONArray("result");
                        LogZS.i(jsonArray.toJSONString());
                        lists=JSON.parseArray(jsonArray.toJSONString(),AlbumModel.class);
                        JSONArray timeArray=jsonObject.getJSONArray("resultDate");
                        PageModel Page=jsonObject.getJSONObject("page").toJavaObject(PageModel.class);
                        bMvpView.addIndexCommon(lists,Page);
//                        bMvpView.refershIndexDate(timeArray);
                    }

                    @Override
                    protected void callError(BaseModel baseModel) {
                        if(pageNum==1){
                            bMvpView.refershIndexCommon(new ArrayList<AlbumModel>());
                        }else{
                            super.callError(baseModel);
                        }

                    }
                });
    }
    //   15.2  更新Alert  系统消息、收发件箱、校园、宝宝评语、考勤、教学计划等。
    public void updateAlert(TAlertModel tAlert) {
//        bMvpView.showProgress(true);
        String userId= ZSApp.getInstance().getUserId();
        resetSubscription();
        tAlert.setUserId(Integer.valueOf(userId));
        tAlert.setBabyId(Integer.valueOf(ZSApp.getInstance().getBabyId()));
        String tAlertStr=new Gson().toJson(tAlert);
        dataManager.updateAlert(tAlertStr).compose(RxThread.<BaseModel<JSONObject>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONObject>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONObject jsonObject) {
                        LogZS.i("更新时间成功");
                    }

                    @Override
                    public void onError(Throwable e) {
//                        super.onError(e);
                    }

                    @Override
                    protected void callError(BaseModel baseModel) {
//                        super.callError(baseModel);
                    }
                });
    }

    public void createAlbum(final String title, final String content, final List<String> imageList, final Context bContent,final String existCommonId,final String isPublic) {
        uploadImageCount = 0;
        imgSum = imageList.size();
        progressDialog = ProgressDialogHelper.getInstance().showUploadProgressDialog((Activity) bContent, "准备上传");
        progressDialog.show();
        resetSubscription();
        final String userId = ZSApp.getInstance().getUserId();
        final String babyId = ZSApp.getInstance().getBabyId();
        final List<String> imgNames = new ArrayList<String>();
        AsyncTask asyncTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                for (String imagepath : imageList) {
                    uploadImageCount++;
                    bMvpView.uploadProgress(uploadImageCount/imgSum*100);
                    try {
                        imgNames.add(UploadPicUtils.upLoadPic(bContent, imagepath));
                    } catch (ClientException e) {
                        bMvpView.showError("网络异常");
                        uploadImageCount--;
                        return null;
                    } catch (ServiceException e) {
                        bMvpView.showError("服务异常");
                        uploadImageCount--;
                        return null;
                    }
                }
                if (uploadImageCount >= imgSum) {
                    dataManager.insertAlbum(userId, babyId, title, content, imgNames.toString(),existCommonId,isPublic).compose(RxThread.<BaseModel<JSONObject>>subscribe_Io_Observe_On())
                            .subscribe(new SubscriberClass<JSONObject>(bMvpView) {
                                @Override
                                protected void callSuccess(JSONObject s) {
                                    bMvpView.uploadFinish(s.getInteger("commonId") + "");
                                }
                            });
                }
                return null;
            }
        };
        asyncTask.execute();
    }

    public void setAlbumCoverByCommonId(String commonId) {
        dataManager.setAlbumCoverByCommonId(commonId).compose(RxThread.<BaseModel<JSONObject>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONObject>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONObject jsonObject) {
                        bMvpView.showMsg("上传成功");
                    }
                });
    }
}
