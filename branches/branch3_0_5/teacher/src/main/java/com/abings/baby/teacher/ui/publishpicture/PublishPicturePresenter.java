package com.abings.baby.teacher.ui.publishpicture;

import com.abings.baby.teacher.ZSApp;
import com.alibaba.fastjson.JSONObject;
import com.hellobaby.library.data.DataManager;
import com.hellobaby.library.data.model.BaseModel;
import com.hellobaby.library.data.model.ClassModel;
import com.hellobaby.library.data.remote.rx.Func1Class;
import com.hellobaby.library.data.remote.rx.RxThread;
import com.hellobaby.library.data.remote.rx.SubscriberBase;
import com.hellobaby.library.ui.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;


public class PublishPicturePresenter extends BasePresenter<PublishPictureMvpView> {

    private final DataManager dataManager;

    @Inject
    public PublishPicturePresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    /**
     * 创建动态-图片
     *
     * @param description
     * @param listClass
     * @param listImg
     */
    public void dynamicUploadImgs(String description, List<ClassModel> listClass, List<String> listImg) {
        bMvpView.showProgress(true);
        String teacherId = ZSApp.getInstance().getTeacherId();
        List<ClassModel> listSelectClass = new ArrayList<>();
        for (ClassModel classM : listClass) {
            if (classM.isSelected()) {
                listSelectClass.add(classM);
            }
        }
        int listSelectClassSize = listSelectClass.size();
        String[] classIds = new String[listSelectClassSize];

        int listImgSize = listImg.size();
        String[] paths = new String[listImgSize];
        for (int i = 0; i < listSelectClassSize; i++) {
            classIds[i] = listSelectClass.get(i).getClassId();
        }
        for (int i = 0; i < listImgSize; i++) {
            paths[i] = listImg.get(i);
        }
        dataManager.dynamicUploadFiles(teacherId, description, classIds, paths)
                .compose(RxThread.<BaseModel<String>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberBase<BaseModel<String>>(bMvpView) {
                    @Override
                    public void onNext(BaseModel<String> stringBaseModel) {
                        bMvpView.showMsg("动态发布成功");
                        bMvpView.publishSuccess();
                    }
                });
    }

    /**
     * 创建动态-小视频
     *
     * @param description
     * @param listClass
     * @param firstFrameImgPath
     * @param videoPath
     */
    public void dynamicUploadVideo(String description, List<ClassModel> listClass, String firstFrameImgPath, final String videoPath) {
        bMvpView.showProgress(true);
        String teacherId = ZSApp.getInstance().getTeacherId();
        List<ClassModel> listSelectClass = new ArrayList<>();
        for (ClassModel classM : listClass) {
            if (classM.isSelected()) {
                listSelectClass.add(classM);
            }
        }
        int listSelectClassSize = listSelectClass.size();
        String[] classIds = new String[listSelectClassSize];
        for (int i = 0; i < listSelectClassSize; i++) {
            classIds[i] = listSelectClass.get(i).getClassId();
        }
        String[] paths = {firstFrameImgPath};

        //第一帧
        dataManager
                .dynamicUploadFirstFrame(teacherId, description, classIds, paths)
                .flatMap(new Func1Class<JSONObject, BaseModel<JSONObject>>(bMvpView) {
                    @Override
                    protected Observable<BaseModel<JSONObject>> callSuccess(JSONObject jsonObject) {
                        String videoName = jsonObject.getString("videoNames");
                        String videoId = jsonObject.getString("videoIds");
                        String[] videoPaths = {videoPath};
                        //小视频
                        return dataManager.dynamicUploadSmallVideo(videoId, videoName, videoPaths);
                    }
                })
                .compose(RxThread.<BaseModel<JSONObject>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberBase<BaseModel<JSONObject>>(bMvpView) {
                    @Override
                    public void onNext(BaseModel<JSONObject> jsonObjectBaseModel) {
                        bMvpView.showMsg("动态发布成功");
                        bMvpView.publishSuccess();
                    }
                });

    }
}
