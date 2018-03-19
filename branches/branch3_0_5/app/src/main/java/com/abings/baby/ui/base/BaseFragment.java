package com.abings.baby.ui.base;

import com.abings.baby.data.injection.DaggerUtils;
import com.abings.baby.data.injection.component.ActivityComponent;
import com.hellobaby.library.ui.base.BaseLibActivity;
import com.hellobaby.library.ui.base.BaseLibFragment;

/**
 * Created by zwj on 2016/10/10.
 * description :
 */

public abstract class BaseFragment<T> extends BaseLibFragment<T>{

   protected ActivityComponent getActivityComponent(){
        return DaggerUtils.getActivityComponent(((BaseLibActivity)getActivity()).getActivityComponent(),getActivity());
    }
}
