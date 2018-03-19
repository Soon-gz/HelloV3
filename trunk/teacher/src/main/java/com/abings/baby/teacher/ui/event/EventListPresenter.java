package com.abings.baby.teacher.ui.event;

import com.abings.baby.teacher.ZSApp;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hellobaby.library.Const;
import com.hellobaby.library.data.DataManager;
import com.hellobaby.library.data.model.BaseModel;
import com.hellobaby.library.data.model.EventModel;
import com.hellobaby.library.data.model.PageModel;
import com.hellobaby.library.data.remote.rx.RxThread;
import com.hellobaby.library.data.remote.rx.SubscriberClass;
import com.hellobaby.library.ui.base.BasePresenter;

import java.text.ParseException;

import javax.inject.Inject;

/**
 * 2016/01/01.
 */

public class EventListPresenter extends BasePresenter<EventListMvpView> {
    @Inject
    DataManager dataManager;

    @Inject
    public EventListPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }
    //拉自己发布的活动
    public void selecteventsByteacher(){
        String teacherid = ZSApp.getInstance().getTeacherModel().getTeacherId();
        dataManager.selecteventsByteacher(teacherid)
                .compose(RxThread.<BaseModel<JSONArray>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONArray>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONArray jsonObject) {
                        bMvpView.showEventData(JSONArray.parseArray(jsonObject.toJSONString(),EventModel.class));
                    }
                });
    }
    //拉活动
    public void selectEventOnTeacher(String type){
        String teacherid = String.valueOf(ZSApp.getInstance().getTeacherModel().getTeacherId());
        String schoolid = String.valueOf(ZSApp.getInstance().getSchoolModel().getSchoolId());
        dataManager.selectEventOnTeacher(teacherid,type,schoolid)
                .compose(RxThread.<BaseModel<JSONArray>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONArray>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONArray jsonObject) {
                        bMvpView.showEventData(JSONArray.parseArray(jsonObject.toJSONString(),EventModel.class));
                    }
                });
    }
    //拉活动
    public void selectEventOnTeacherPage(String type,int pageNum){
        String teacherid = String.valueOf(ZSApp.getInstance().getTeacherModel().getTeacherId());
        String schoolid = String.valueOf(ZSApp.getInstance().getSchoolModel().getSchoolId());
        dataManager.selectEventOnTeacherPage(teacherid,type,schoolid,pageNum)
                .compose(RxThread.<BaseModel<JSONObject>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONObject>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONObject jsonObject) {
                        bMvpView.showEventData(JSONArray.parseArray(jsonObject.getJSONArray("result").toJSONString(),EventModel.class));
                        bMvpView.setPageModel(JSONObject.toJavaObject(jsonObject.getJSONObject("page"),PageModel.class));
                    }
                });
    }

    /**
     * 作者：ShuWen
     * 日期：2018/1/3  11:39
     * 描述：校长端根据选择查看指定班级的全部，动态，活动
     *
     * @return [返回类型说明]
     */
    public void selectEventBySchoolAdminPage(String classIds, final String type, int pageNum){
        String schoolid = String.valueOf(ZSApp.getInstance().getSchoolModel().getSchoolId());
        if (classIds.equals("") || classIds == null){
            return;
        }
        dataManager.selectEventBySchoolAdminPage(classIds,schoolid,type,pageNum)
                .compose(RxThread.<BaseModel<JSONObject>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONObject>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONObject jsonObject) {
                        if (!type.equals(Const.SCHOOL_EVENT)){
                            bMvpView.showListData(jsonObject.getJSONArray("result"));
                            bMvpView.setPageModel(JSONObject.toJavaObject(jsonObject.getJSONObject("page"),PageModel.class));
                        }else {
                            bMvpView.showEventData(JSONArray.parseArray(jsonObject.getJSONArray("result").toJSONString(),EventModel.class));
                            bMvpView.setPageModel(JSONObject.toJavaObject(jsonObject.getJSONObject("page"),PageModel.class));
                        }
                    }
                });
    }

    //拉校园所有
    public void selectSchoolOnTeacher(String type){
        String teacherid = String.valueOf(ZSApp.getInstance().getTeacherModel().getTeacherId());
        String schoolid = String.valueOf(ZSApp.getInstance().getSchoolModel().getSchoolId());
        dataManager.selectEventOnTeacher(teacherid,type,schoolid)
                .compose(RxThread.<BaseModel<JSONArray>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONArray>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONArray jsonObject){
                            bMvpView.showListData(jsonObject);
                    }
                });
    }
    //拉校园所有
    public void selectSchoolOnTeacherPage(String type,int pageNum){
        String teacherid = String.valueOf(ZSApp.getInstance().getTeacherModel().getTeacherId());
        String schoolid = String.valueOf(ZSApp.getInstance().getSchoolModel().getSchoolId());
        dataManager.selectEventOnTeacherPage(teacherid,type,schoolid,pageNum)
                .compose(RxThread.<BaseModel<JSONObject>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONObject>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONObject jsonObject){
                        bMvpView.showListData(jsonObject.getJSONArray("result"));
                        bMvpView.setPageModel(JSONObject.toJavaObject(jsonObject.getJSONObject("page"),PageModel.class));
                    }
                });
    }

    //删除自己发的动态
    public void deleteDynamicById(String dynamicId){
        dataManager.deleteDynamicById(dynamicId)
                .compose(RxThread.<BaseModel<JSONObject>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONObject>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONObject jsonObject){
                    }
                });
    }
}
