package com.abings.baby.ui.Information.infomationNew;

import com.alibaba.fastjson.JSONObject;
import com.hellobaby.library.data.DataManager;
import com.hellobaby.library.data.model.BaseModel;
import com.hellobaby.library.data.model.CommentModel;
import com.hellobaby.library.data.model.SelectInfoDetailModel;
import com.hellobaby.library.data.remote.rx.RxThread;
import com.hellobaby.library.data.remote.rx.SubscriberClass;
import com.hellobaby.library.ui.base.BasePresenter;
import com.hellobaby.library.utils.LogZS;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by ShuWen on 2017/6/13.
 */

public class BaseInfoDetailPresenter extends BasePresenter<BaseInfoDetailMVP> {
    private final DataManager mDataManager;

    @Inject
    public BaseInfoDetailPresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }

    public void getCommentList(String topicType, String topicId){
        LogZS.i("评论上传数据：topicType"+topicType+"   topicId:"+topicId);
        mDataManager.getCommentList(topicType, topicId)
                .compose(RxThread.<BaseModel<List<CommentModel>>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<List<CommentModel>>(bMvpView) {
                    @Override
                    protected void callSuccess(List<CommentModel> commentModels) {
                        bMvpView.showData(commentModels);
                    }
                });
    }

    public void addLikeInfo(String topicType ,String topicId){
        mDataManager.addLikeInfo(topicType, topicId)
                .compose(RxThread.<BaseModel<JSONObject>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONObject>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONObject jsonObject) {
                    }
                });
    }

    public void addComment(String topicType, String topicId, String commentContent, String toReplyUid, String toReplyUtype, String commentUtype){
        mDataManager.addComment(topicType, topicId, commentContent, toReplyUid, toReplyUtype, commentUtype)
                .compose(RxThread.<BaseModel<String>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<String>(bMvpView) {
                    @Override
                    protected void callSuccess(String result) {
                        bMvpView.addCommentSuccess();
                    }
                });
    }

    public void deleteComment(String tInfoCommId,String topicId,String topicType){
        mDataManager.deleteComment(tInfoCommId,topicId,topicType)
                .compose(RxThread.<BaseModel<String>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<String>(bMvpView) {
                    @Override
                    protected void callSuccess(String result) {
                        bMvpView.deleteCommentSuccess();
                    }
                });
    }

    public void deleteAlbum(String subAlbumId){
        mDataManager.deleteAlbum(subAlbumId)
                .compose(RxThread.<BaseModel<String>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<String>(bMvpView) {
                    @Override
                    protected void callSuccess(String s) {
                        bMvpView.deleteAlbumSuccess();
                    }
                });
    }

    public void selectInfoDetails(String topicId, String topicType){
        mDataManager.selectInfoDetails(topicId, topicType)
                .compose(RxThread.<BaseModel<SelectInfoDetailModel>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<SelectInfoDetailModel>(bMvpView) {
                    @Override
                    protected void callSuccess(SelectInfoDetailModel s) {
                        bMvpView.selectInfoDetails(s);
                    }

                    @Override
                    protected void callError(BaseModel baseModel) {
                        super.callError(baseModel);
                        bMvpView.noContent();
                    }
                });
    }
}
