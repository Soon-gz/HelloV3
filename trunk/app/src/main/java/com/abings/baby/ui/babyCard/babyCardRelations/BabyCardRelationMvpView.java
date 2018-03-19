package com.abings.baby.ui.babyCard.babyCardRelations;

import com.hellobaby.library.ui.base.MvpView;

/**
 * Created by ShuWen on 2017/3/14.
 */

public interface BabyCardRelationMvpView<T> extends MvpView<T> {
    void uploadWithOutHeadImg();
    void uploadSuccess();
    void updateSuccess();
    void updateHead();
    void deleteSuccess();
}
