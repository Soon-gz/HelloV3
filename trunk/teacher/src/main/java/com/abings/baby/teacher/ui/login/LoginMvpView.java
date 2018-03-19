package com.abings.baby.teacher.ui.login;

import com.hellobaby.library.data.model.AppVersionModel;
import com.hellobaby.library.ui.base.MvpView;

/**
 * Created by zwj on 2017/01/03
 * description : 登录
 */

public interface LoginMvpView<T> extends MvpView<T> {

   public void toMain();

   /**
    * 升级使用
    * @param model
    */
   public void toUpdate(AppVersionModel model);
}
