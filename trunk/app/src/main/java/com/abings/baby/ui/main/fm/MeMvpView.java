package com.abings.baby.ui.main.fm;

import com.hellobaby.library.data.model.BabyModel;
import com.hellobaby.library.data.model.UserModel;
import com.hellobaby.library.ui.base.MvpView;

import java.util.List;

/**
 * Created by zwj on 2016/12/30.
 * description :
 */

public interface MeMvpView<T> extends MvpView<T> {
    /**
     * 修改宝宝信息的点击事件
     *
     * @param babyModel
     */
    public void babyUpdateInfo(BabyModel babyModel);

    /**
     * 修改用户信息的点击事件
     *
     * @param babyModel
     */
    public void userUpdateInfoClick(UserModel babyModel);


    /**
     * 拉取关系人后修改头像
     */
    public void babySetCareUsers(List<UserModel> list);


    public void getRelationsClick();

    /**
     * 用户点击头像后上传头像
     *
     * @param path
     */
    public void userUploadHeadImgClick(String path);

    /**
     * 修改关系人的关系
     *
     * @param userModel
     */
    public void relationUpdateRelationClick(UserModel userModel);


    public void changePublicClick(boolean onOff);

    /**
     * 退出点击事件
     */
    public void logoutClick();

    /**
     * 退出成功
     */
    public void logoutSuccess();
    /**
     * 关注宝宝成功
     */
    public void careBabySuccess();
    /**
     * 取消关注点击
     */
    public void cancelCareBabyClick(String userId,String babyId,boolean isBaby);

    /**
     * 取消关注成功
     * @param isBaby 删除宝宝，删除家长
     */
    public void cancelCareBabySuccess(String userId, String babyId,boolean isBaby);

    /**
     * 清空数据
     * @param babyId
     */
    public void deleteHeightWeightByBabyId(String babyId);


    /**
     * 改变权限
     * @param on
     */
    public void updatePickChange(UserModel userModel,BabyModel babyModel,boolean on);
}
