package com.abings.baby.ui.publishpicture;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.abings.baby.ZSApp;
import com.abings.baby.ui.base.BaseActivity;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.ServiceException;
import com.hellobaby.library.data.DataManager;
import com.hellobaby.library.data.model.AlbumModel;
import com.hellobaby.library.data.model.BabyModel;
import com.hellobaby.library.data.model.BaseModel;
import com.hellobaby.library.data.remote.rx.Func1Class;
import com.hellobaby.library.data.remote.rx.RxThread;
import com.hellobaby.library.data.remote.rx.SubscriberBase;
import com.hellobaby.library.data.remote.rx.SubscriberClass;
import com.hellobaby.library.ui.base.BasePresenter;
import com.hellobaby.library.uploadpic.UploadPicUtils;
import com.hellobaby.library.widget.ProgressDialogHelper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Func1;


public class PublishPicturePresenter extends BasePresenter<PublishPictureMvpView> {
    @Inject
    DataManager dataManager;
    ProgressDialog progressDialog;
    private int uploadImageCount = 0;
    private int imgSum = 0;

    @Inject
    public PublishPicturePresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

//    public void createAlbum(final String title, final String content, final List<String> imageList, final Context bContent, final String existCommonId, final String isPublic) {
//        uploadImageCount = 0;
//        imgSum = imageList.size();
//        progressDialog = ProgressDialogHelper.getInstance().showUploadProgressDialog((Activity) bContent, "准备上传");
//        progressDialog.show();
//        resetSubscription();
//        final String userId = ZSApp.getInstance().getUserId();
//        final String babyId = ZSApp.getInstance().getBabyId();
//        final List<String> imgNames = new ArrayList<String>();
//        AsyncTask asyncTask = new AsyncTask() {
//            @Override
//            protected Object doInBackground(Object[] objects) {
//                for (String imagepath : imageList) {
//                    uploadImageCount++;
//                    bMvpView.uploadProgress(uploadImageCount + "/" + imgSum);
//                    try {
//                        imgNames.add(UploadPicUtils.upLoadPic(bContent, imagepath));
//                    } catch (ClientException e) {
//                        bMvpView.showError("网络异常");
//                        uploadImageCount--;
//                        return null;
//                    } catch (ServiceException e) {
//                        bMvpView.showError("服务异常");
//                        uploadImageCount--;
//                        return null;
//                    }
//                }
//                if (uploadImageCount >= imgSum) {
//                    dataManager.insertAlbum(userId, babyId, title, content, imgNames.toString(),existCommonId,isPublic).compose(RxThread.<BaseModel<JSONObject>>subscribe_Io_Observe_On())
//                            .subscribe(new SubscriberClass<JSONObject>(bMvpView) {
//                                @Override
//                                protected void callSuccess(JSONObject s) {
//                                    bMvpView.uploadFinish(s.getInteger("commonId") + "");
//                                }
//                            });
//                }
//                return null;
//            }
//        };
//        asyncTask.execute();
//    }

    public void setAlbumCoverByCommonId(String commonId) {
        dataManager.setAlbumCoverByCommonId(commonId).compose(RxThread.<BaseModel<JSONObject>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONObject>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONObject jsonObject) {
//                        bMvpView.showMsg("上传成功");
                    }
                });
    }

    public void selectAlbumList(String userId, String babyId) {
        dataManager.selectAlbumList(userId, babyId).compose(RxThread.<BaseModel<JSONObject>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONObject>(bMvpView) {
                               @Override
                               protected void callSuccess(JSONObject jsonObject) {
                                   bMvpView.reflushalbumlist(jsonObject.getJSONArray("result"));
                               }

                               @Override
                               protected void callError(BaseModel baseModel) {
                                   bMvpView.reflushalbumlist(null);
                               }

                               @Override
                               public void onError(Throwable e) {
                                   bMvpView.reflushalbumlist(null);
                               }
                           }
                );
    }
}
