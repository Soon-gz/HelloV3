package com.abings.baby.ui.album;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.ServiceException;
import com.hellobaby.library.data.DataManager;
import com.hellobaby.library.data.model.AlbumModel;
import com.hellobaby.library.data.model.BaseModel;
import com.hellobaby.library.data.remote.rx.Func1Class;
import com.hellobaby.library.data.remote.rx.RxThread;
import com.hellobaby.library.data.remote.rx.SubscriberClass;
import com.hellobaby.library.ui.base.BasePresenter;
import com.hellobaby.library.uploadpic.UploadPicUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by zwj on 2016/12/29.
 * description : 相册
 */

public class AlbumPresenter extends BasePresenter<AlbumMvpView> {

    @Inject
    DataManager dataManager;
    int uploadaddImageCount = 0;

    @Inject
    public AlbumPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }


    public void ablumGetImgs(String commonId) {
        resetSubscription();
        dataManager.albumGetImgs(commonId)
                .compose(RxThread.<BaseModel<AlbumModel>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<AlbumModel>(bMvpView) {
                    @Override
                    protected void callSuccess(AlbumModel albumModel) {
                        bMvpView.initAlbumImgs(albumModel);
                    }
                });
    }


    /**
     * 操作整个相册
     */
    public void ablumOptImgs(final String commonId, final String title, final String content, List<Map<String, String>> delListMap, final List<String> listAdd, List<WaterFallItem> waterFallItemsList, final Context mContent) {

        resetSubscription();
        bMvpView.showProgress(true);
        final int sizeAdd = listAdd.size();
        //确认删除
        int sizeDel = delListMap.size();
        StringBuffer imageNames = new StringBuffer();
        StringBuffer imageIds = new StringBuffer();
        for (int i = 0; i < sizeDel; i++) {
            Map<String, String> map = delListMap.get(i);
            imageNames.append(map.get("name"));
            imageIds.append(map.get("id"));
            if (i < sizeDel - 1) {
                imageNames.append(",");
                imageIds.append(",");
            }
        }
        String names = imageNames.toString().trim();
        String ids = imageIds.toString().trim();


        if (sizeDel > 0 && sizeAdd > 0) {
            /**删除  增加*/
            final List<String> imgNames = new ArrayList<String>();
            boolean isDelAllServerImg = true;
            for (WaterFallItem item : waterFallItemsList) {
                if (item.getId() != null && !item.getId().isEmpty()) {
                    isDelAllServerImg = false;
                    break;
                }
            }

            final boolean finalIsDelAllServerImg = isDelAllServerImg;
            dataManager
                    .albumDelImgs(ids, names)
                    .flatMap(new Func1<BaseModel<String>, Observable<String>>() {
                        @Override
                        public Observable<String> call(BaseModel<String> stringBaseModel) {
                            return Observable.from(listAdd);
                        }
                    })
                    .flatMap(new Func1<String, Observable<BaseModel<JSONObject>>>() {
                        @Override
                        public Observable<BaseModel<JSONObject>> call(String s) {
                            uploadaddImageCount++;
                            try {
                                imgNames.add(UploadPicUtils.upLoadPic(mContent, s));
                            } catch (ClientException e) {
                                bMvpView.showError("网络异常");
                            } catch (ServiceException e) {
                                bMvpView.showError("服务异常");
                            }
                             if (sizeAdd == uploadaddImageCount) {
                                return dataManager.uploadIndexFile(commonId, imgNames.toString());
                            } else {
                                return null;
                            }
                        }
                    })
                    .flatMap(new Func1<BaseModel<JSONObject>, Observable<BaseModel<JSONObject>>>() {
                        @Override
                        public Observable<BaseModel<JSONObject>> call(BaseModel<JSONObject> albumModelBaseModel) {
                            if (finalIsDelAllServerImg) {
                                return dataManager.setAlbumCoverByCommonId(commonId);
                            }
                            BaseModel<JSONObject> base = new BaseModel<>();
                            base.setCode("200");
                            return Observable.just(base);
                        }
                    })
                    .flatMap(new Func1<BaseModel<JSONObject>, Observable<BaseModel<AlbumModel>>>() {
                        @Override
                        public Observable<BaseModel<AlbumModel>> call(BaseModel<JSONObject> stringBaseModel) {
                            return dataManager.albumUpdateContent(commonId, title, content);
                        }
                    })
                    .compose(RxThread.<BaseModel<AlbumModel>>subscribe_Io_Observe_On())
                    .subscribe(new SubscriberClass<AlbumModel>(bMvpView) {
                        @Override
                        protected void callSuccess(AlbumModel jsonObject) {
                            bMvpView.albumOptFinish();
                        }
                    });
        } else if (sizeDel > 0) {
            //删除
            dataManager
                    .albumDelImgs(ids, names)
                    .flatMap(new Func1<BaseModel<String>, Observable<BaseModel<AlbumModel>>>() {
                        @Override
                        public Observable<BaseModel<AlbumModel>> call(BaseModel<String> stringBaseModel) {
                            return dataManager.albumUpdateContent(commonId, title, content);
                        }
                    })
                    .compose(RxThread.<BaseModel<AlbumModel>>subscribe_Io_Observe_On())
                    .subscribe(new SubscriberClass<AlbumModel>(bMvpView) {
                        @Override
                        protected void callSuccess(AlbumModel albumModel) {
                            bMvpView.albumOptFinish();
                        }
                    });
        } else if (sizeAdd > 0) {
            //增加
            final List<String> imgNames = new ArrayList<String>();
            Observable
                    .from(listAdd)
                    .flatMap(new Func1<String, Observable<BaseModel<JSONObject>>>() {
                        @Override
                        public Observable<BaseModel<JSONObject>> call(String s) {
                            uploadaddImageCount++;
                            try {
                                imgNames.add(UploadPicUtils.upLoadPic(mContent, s));
                            } catch (ClientException e) {
                                bMvpView.showError("网络异常");
                            } catch (ServiceException e) {
                                bMvpView.showError("服务异常");
                            }
                            if (sizeAdd == uploadaddImageCount) {
                                return dataManager.uploadIndexFile(commonId, imgNames.toString());
                            } else {
                                return null;
                            }
                        }
                    })
                    .flatMap(new Func1<BaseModel<JSONObject>, Observable<BaseModel<AlbumModel>>>() {
                        @Override
                        public Observable<BaseModel<AlbumModel>> call(BaseModel<JSONObject> stringBaseModel) {
                            return dataManager.albumUpdateContent(commonId, title, content);
                        }
                    })
                    .compose(RxThread.<BaseModel<AlbumModel>>subscribe_Io_Observe_On())
                    .subscribe(new SubscriberClass<AlbumModel>(bMvpView) {
                        @Override
                        protected void callSuccess(AlbumModel albumModel) {
                            bMvpView.albumOptFinish();
                        }
                    });
        } else {
            dataManager
                    .albumUpdateContent(commonId, title, content)
                    .compose(RxThread.<BaseModel<AlbumModel>>subscribe_Io_Observe_On())
                    .subscribe(new SubscriberClass<AlbumModel>(bMvpView) {
                        @Override
                        protected void callSuccess(AlbumModel albumModel) {
                            bMvpView.albumOptFinish();
                        }
                    });
        }
    }
    /**
     * 删除
     *
     * @param imageIds
     * @param imageNames
     */
    public void ablumDelImgs(final String commonId, final String title, final String content, final String imageIds, final String imageNames) {

        resetSubscription();
        dataManager.albumDelImgs(imageIds, imageNames)
                .flatMap(new Func1Class<String, BaseModel<AlbumModel>>(bMvpView) {
                    @Override
                    protected Observable<BaseModel<AlbumModel>> callSuccess(String s) {
                        return dataManager.albumUpdateContent(commonId, title, content);
                    }
                })
                .compose(RxThread.<BaseModel<AlbumModel>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<AlbumModel>(bMvpView) {
                    @Override
                    protected void callSuccess(AlbumModel albumModelBaseModel) {
                        bMvpView.albumDelImgs(imageIds, imageNames);
                        bMvpView.albumOptFinish();
                    }
                });
    }

    private int uploadImgCount = 0;

//    public void albumUpdateImgs(final AlbumModel albumModelFinal, final List<String> imageList) {
//        uploadImgCount = 0;
//        bMvpView.showProgress(true);
//        resetSubscription();
//        Observable.from(imageList)
//                .flatMap(new Func1<String, Observable<BaseModel<AlbumModel>>>() {
//                    @Override
//                    public Observable<BaseModel<AlbumModel>> call(String s) {
//                        return dataManager.uploadAlbumImg(albumModelFinal.getCommonId(), s);
//                    }
//                })
//                .flatMap(new Func1Class<AlbumModel, BaseModel<AlbumModel>>(bMvpView) {
//                    @Override
//                    protected Observable<BaseModel<AlbumModel>> callSuccess(AlbumModel albumModel) {
//                        return dataManager.albumUpdateContent(albumModelFinal.getCommonId(), albumModelFinal.getTitle(), albumModelFinal.getContent());
//                    }
//                })
//                .compose(RxThread.<BaseModel<AlbumModel>>subscribe_Io_Observe_On())
//                .subscribe(new SubscriberClass<AlbumModel>(bMvpView) {
//                    @Override
//                    protected void callSuccess(AlbumModel albumModel) {
//                        uploadImgCount++;
//                        if (imageList.size() == uploadImgCount) {
//                            bMvpView.showMsg("上传成功");
//                            bMvpView.albumOptFinish();
//                        }
//                    }
//                });
//    }

    /**
     * 修改相册标题和内容
     *
     * @param albumModelFinal
     */
    public void albumUpdateTitleContent(AlbumModel albumModelFinal) {
        dataManager
                .albumUpdateContent(albumModelFinal.getCommonId(), albumModelFinal.getTitle(), albumModelFinal.getContent())
                .compose(RxThread.<BaseModel<AlbumModel>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<AlbumModel>(bMvpView) {
                    @Override
                    protected void callSuccess(AlbumModel albumModel) {
                        bMvpView.showMsg("修改相册完成");
                        bMvpView.albumOptFinish();
                    }
                });
    }

    public void deleteAlbumById(AlbumModel albumModel) {
        resetSubscription();
        bMvpView.showProgress(true);
        dataManager.deleteAlbumById(albumModel.getCommonId())
                .compose(RxThread.<BaseModel<String>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<String>(bMvpView) {
                    @Override
                    protected void callSuccess(String s) {
                        bMvpView.showMsg("删除成功");
                        bMvpView.albumDelFinish();
                    }
                });
    }

    public void setAlbumCoverByImageId(String commonId, String imageId) {
        resetSubscription();
        bMvpView.showProgress(true);
        dataManager.setAlbumCoverByImageId(commonId, imageId)
                .compose(RxThread.<BaseModel<JSONObject>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONObject>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONObject jsonObject) {
                        bMvpView.showMsg("设置成功");
                    }
                });
    }

    public void deleteSingleImgByIds(String imageIds, final String imageNames) {
        resetSubscription();
        bMvpView.showProgress(true);
        dataManager.albumDelImgs(imageIds, imageIds)
                .compose(RxThread.<BaseModel<String>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<String>(bMvpView) {
                    @Override
                    protected void callSuccess(String jsonObject) {
//                        bMvpView.showMsg("删除单张照片成功");
                        bMvpView.showDelOneImg(imageNames);
                    }
                });
    }
}
