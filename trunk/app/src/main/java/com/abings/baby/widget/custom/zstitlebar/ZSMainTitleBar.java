package com.abings.baby.widget.custom.zstitlebar;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.IdRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LayoutAnimationController;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.abings.baby.R;
import com.abings.baby.ZSApp;
import com.abings.baby.ui.measuredata.MeasureActivity;
import com.abings.baby.ui.message.MsgCenterActivity;
import com.abings.baby.ui.search.BabyIndexSearchActivity;
import com.abings.baby.ui.search.SearchActivity;
import com.alibaba.fastjson.JSONObject;
import com.hellobaby.library.data.model.BabyModel;
import com.hellobaby.library.utils.DESUtils;
import com.hellobaby.library.utils.GaussLayoutUtils;
import com.hellobaby.library.utils.ImageLoader;
import com.hellobaby.library.utils.ScreenUtils;
import com.hellobaby.library.widget.IZSMainTitleScreenLightListener;
import com.hellobaby.library.widget.PopupWindowQRCode;
import com.hellobaby.library.widget.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

/**
 * Created by zwj on 2016/10/20.
 * description :
 */

public class ZSMainTitleBar extends LinearLayout {
    private Context mContext;
    private PopupWindow popupWindow;
    /**
     * 正在关闭
     */
    private boolean isTitleBarClosing = false;
    /**
     * 正在打开
     */
    private boolean isTitleBarOpening = false;

    LinearLayout ppwLlParent;
    // 点击消失使用的
    View ppwOtherView;

    private View ppwView;

    public boolean isMain;
    //ppw的
    private int ppwHeightLLRoot = -1;
    private WidgetBean barBean;
    private WidgetBean ppwBarBean;
    List<BabyModel> mIds = new ArrayList<>();
    protected Activity activityMain;
    /**
     * 当前宝宝
     */
    private BabyModel mCurrentBaby;
    private View mGaussLayout;
    private boolean isOpenEnable = false;
    private FrameLayout flContent;//布局中的背景，隐藏和显示
    private final int duration = 400;
    public Badge dataBadgeView;
    public Badge messageBadgeView;
    public ZSMainTitleBar(Context context) {
        this(context, null);
    }

    public ZSMainTitleBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZSMainTitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();

    }

    private void init() {
        initBar();
        initBarClick();
    }

    public void setMainActivity(Activity activity){
        activityMain = activity;
    }

    /**
     * 初始化标题
     */
    private void initBar() {
        LayoutInflater.from(mContext).inflate(R.layout.include_main_titlebar, this);
        barBean = new WidgetBean(this);
        barBean.setIvBabyHead(R.id.mainTitleBar_iv_babyHead);
        barBean.setTvBabyName(R.id.mainTitleBar_tv_babyName);
        barBean.setIvQrCode(R.id.mainTitleBar_iv_qrCode);
        barBean.setIvLocation(R.id.mainTitleBar_iv_location);
        barBean.setIvData(R.id.mainTitleBar_iv_data);
        barBean.setIvMessage(R.id.mainTitleBar_iv_message);
        barBean.setIvSearch(R.id.mainTitleBar_iv_search);
        barBean.setLlFunction(R.id.mainTitleBar_ll_function);
        barBean.setRvOtherBabys(R.id.mainTitleBar_rv_otherBabys);
        barBean.setLlRoot(R.id.mainTitleBar_ll_root);
    }

    /**
     * 初始化弹窗标题
     */
    private void initPopupWindow() {
        ppwView = LayoutInflater.from(mContext).inflate(R.layout.ppw_main_titlebar, null);
        View view_empty = ppwView.findViewById(R.id.mainTitleBar_view_empty);
        ppwBarBean = new WidgetBean(ppwView);
        ppwBarBean.setIvBabyHead(R.id.mainTitleBar_iv_babyHead);
        ppwBarBean.setTvBabyName(R.id.mainTitleBar_tv_babyName);
        ppwBarBean.setIvQrCode(R.id.mainTitleBar_iv_qrCode);
        ppwBarBean.setIvLocation(R.id.mainTitleBar_iv_location);
        ppwBarBean.setIvData(R.id.mainTitleBar_iv_data);
        ppwBarBean.setIvMessage(R.id.mainTitleBar_iv_message);
        ppwBarBean.setIvSearch(R.id.mainTitleBar_iv_search);
        ppwBarBean.setLlFunction(R.id.mainTitleBar_ll_function);
        ppwBarBean.setRvOtherBabys(R.id.mainTitleBar_rv_otherBabys);
        ppwBarBean.setLlRoot(R.id.mainTitleBar_ll_root);
        ppwOtherView = ppwView.findViewById(R.id.ppwMainTitle_other_view);
        ppwLlParent = (LinearLayout) ppwView.findViewById(R.id.ppwMainTitle_ll_parent);
        popupWindow = new PopupWindow(ppwView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);

        view_empty.setFocusableInTouchMode(true);
        view_empty.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    closeTitleBar();
                    return true;
                }
                return false;
            }
        });
        setPpwCurrentBaby();
    }

    private void initBarClick() {
        barBean.getIvBabyHead().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOpenEnable) {
                    showPopupWindow();
                }
            }
        });
    }

    private void initPopupWindowClick() {
        ppwOtherView.setOnClickListener(new OnClickCloseTitleBarListener() {
            @Override
            public void onClickTitleBar(View v) {
            }
        });

        ppwBarBean.getIvBabyHead().setOnClickListener(new OnClickCloseTitleBarListener() {
            @Override
            public void onClickTitleBar(View v) {
            }
        });
        ppwBarBean.addOtherBabys(mIds);
        setOnItemClickListener(new OtherBabysRVAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                closeTitleBar();
                barBean.exChangeBaby(mCurrentBaby, position);
                mCurrentBaby = ppwBarBean.exChangeBaby(mCurrentBaby, position);
                mBarListen.clickOtherBaby(mCurrentBaby);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setFlContentIsShow(true);
            }
        });
    }
    /**
     * 设置当前宝宝信息
     */
    public void setPpwCurrentBaby() {
        if (mCurrentBaby.isEmptyHeadImgUrl()) {
            ppwBarBean.getIvBabyHead().setImageResource(R.drawable.head_holder);
        } else {
            loadBabyHead(mCurrentBaby.getHeadImgUrlAbs(), ppwBarBean.getIvBabyHead());
        }
        ppwBarBean.getTvBabyName().setText(mCurrentBaby.getBabyName());
    }
    /**
     * 设置当前宝宝信息
     *
     * @param currentBaby
     */
    public void setCurrentBaby(BabyModel currentBaby) {
        this.mCurrentBaby = currentBaby;
        if (mCurrentBaby.isEmptyHeadImgUrl()) {
            barBean.getIvBabyHead().setImageResource(R.drawable.head_holder);
        } else {
            loadBabyHead(mCurrentBaby.getHeadImgUrlAbs(), barBean.getIvBabyHead());
        }
        barBean.getTvBabyName().setText(mCurrentBaby.getBabyName());
    }


    /**
     * 展现popupWindow
     */
    private void showPopupWindow() {

        initPopupWindow();
        initPopupWindowClick();
        if (mGaussLayout != null) {
            ppwLlParent = (LinearLayout) GaussLayoutUtils.setGaussLayout(mContext, ppwLlParent, mGaussLayout);
        }
        popupWindow.showAtLocation(this, Gravity.NO_GRAVITY, 0, ScreenUtils.getStatusHeight(mContext));
        ScaleAnimation scaleAnimation = new ScaleAnimation(0, 0, 0, 0);
        LayoutAnimationController lac = new LayoutAnimationController(scaleAnimation);
        ppwLlParent.setLayoutAnimation(lac);
        scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                setAnimateStart(true);
                isTitleBarOpening = true;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }

    /**
     * 关闭
     */
    public void closeTitleBar() {
        setAnimateStart(false);
    }

    /**
     * 开启动画
     *
     * @param isToShow
     */
    private void setAnimateStart(boolean isToShow) {
        setBabyHeadAnimate(isToShow, !isToShow);
    }


    /**
     * 1.标题高度边为原来的2倍
     * 2.头像变大，目测2倍
     * 3.文字移动到头像下面
     * 4.右边的功能键都向下滑动
     * 5.多出来的宝宝从右侧滑动出来
     *
     * @param isOpenOrClose 开启(true)or关闭(true)
     * @param isBack        true开启动画，false关闭动画
     */
    private void setBabyHeadAnimate(final boolean isOpenOrClose, final boolean isBack) {
        if (!isOpenOrClose && isBack && (isTitleBarClosing || isTitleBarOpening)) {
            return;
        }
        if (isOpenOrClose) {
            setFlContentIsShow(false);
            mCloseOrOpenListen.titleBarOpenFinish();
            final ViewGroup.LayoutParams params = ppwBarBean.getLlRoot().getLayoutParams();

            if (ppwHeightLLRoot < 0) {
                ppwHeightLLRoot = params.height;
            }

            //2.头像变大，目测2倍
            float changeNum = 2.5f;
            final int widthImage = ppwBarBean.getIvBabyHead().getWidth();
            int heightImage = ppwBarBean.getIvBabyHead().getHeight();
            int heightTextView = ppwBarBean.getTvBabyName().getHeight();
            int widthTextView = ppwBarBean.getTvBabyName().getWidth();
            ppwBarBean.getIvBabyHead().setPivotX(0);
            ppwBarBean.getIvBabyHead().setPivotY(heightImage * 3 / 4);
            float babyHeadScaleStart = getCurrentValue(!isBack, 1, changeNum);
            float babyHeadScaleEnd = getCurrentValue(isBack, 1, changeNum);
            ObjectAnimator babyHeadScaleX = ObjectAnimator.ofFloat(ppwBarBean.getIvBabyHead(), "scaleX", babyHeadScaleStart, babyHeadScaleEnd);
            ObjectAnimator babyHeadScaleY = ObjectAnimator.ofFloat(ppwBarBean.getIvBabyHead(), "scaleY", babyHeadScaleStart, babyHeadScaleEnd);

            //3.设置文字的变化
            float babyNameTransXStart = getCurrentValue(!isBack, 0, (-widthImage * (changeNum - 1.5f) + ScreenUtils.dip2px(mContext, 5)));
            float babyNameTransXEnd = getCurrentValue(isBack, 0, (-widthImage * (changeNum - 1.5f) + ScreenUtils.dip2px(mContext, 8)));
            float babyNameTransYStart = getCurrentValue(!isBack, 0, (heightImage + heightTextView / 3) + 8);
            float babyNameTransYEnd = getCurrentValue(isBack, 0, (heightImage + heightTextView / 3) + 8);

            ObjectAnimator babyNameTranslationX = ObjectAnimator.ofFloat(ppwBarBean.getTvBabyName(), "translationX", babyNameTransXStart, widthImage - widthTextView / changeNum, babyNameTransXEnd);
            ObjectAnimator babyNameTranslationY = ObjectAnimator.ofFloat(ppwBarBean.getTvBabyName(), "translationY", babyNameTransYStart, babyNameTransYEnd);


            float functionTransYStart = getCurrentValue(!isBack, 0, (heightImage + heightTextView / 4));
            float functionTransYEnd = getCurrentValue(isBack, 0, (heightImage + heightTextView / 4));
            //4.右边的功能按键花落下来
            ObjectAnimator llFunctionTranslationY = ObjectAnimator.ofFloat(ppwBarBean.getLlFunction(), "translationY", functionTransYStart, functionTransYEnd);

            AnimatorSet set = new AnimatorSet();
            set.setDuration(duration);
            set.setStartDelay(100);
            set.play(babyHeadScaleX).with(babyHeadScaleY).//头像
                    with(babyNameTranslationX).with(babyNameTranslationY)//姓名
                    .with(llFunctionTranslationY);//右侧的功能栏
            set.start();

            babyHeadScaleX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float value = (float) animation.getAnimatedValue();
                    //1.
                    params.height = (int) (ppwHeightLLRoot * (value));
                    ppwBarBean.getLlRoot().setLayoutParams(params);
                }
            });


            //展示
            babyHeadScaleX.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    if (!isBack) {
                        //5.
                        ppwBarBean.getRvOtherBabys().setVisibility(View.VISIBLE);
//                        ppwBarBean.getRvOtherBabys().smoothScrollToPosition(mIds.size()-4);
//                        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 4,//X from
//                                Animation.RELATIVE_TO_SELF, 0,//X to
//                                Animation.RELATIVE_TO_SELF, 0,
//                                Animation.RELATIVE_TO_SELF, 0);
//                        translateAnimation.setDuration(duration);
//                        translateAnimation.setInterpolator(new DecelerateInterpolator());//
//
//                        LayoutAnimationController layoutAnimationController = new LayoutAnimationController(translateAnimation);
//                        ppwBarBean.getRvOtherBabys().setLayoutAnimation(layoutAnimationController);
                        isTitleBarOpening = false;
                    } else {
                        popupWindow.dismiss();
                        isTitleBarClosing = false;
                        setFlContentIsShow(true);
                        mCloseOrOpenListen.titleBarCloseFinish();
                    }
                }
            });
        }

        //收起
        if (!isOpenOrClose && isBack) {
//            ppwBarBean.getRvOtherBabys().setVisibility(View.VISIBLE);
//            TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0,//X from
//                    Animation.RELATIVE_TO_SELF, 1,//X to
//                    Animation.RELATIVE_TO_SELF, 0,
//                    Animation.RELATIVE_TO_SELF, 0);
//            translateAnimation.setDuration(duration);
//            ppwBarBean.getRvOtherBabys().startAnimation(translateAnimation);
//            translateAnimation.setAnimationListener(new Animation.AnimationListener() {
//                @Override
//                public void onAnimationStart(Animation animation) {
//                    isTitleBarClosing = true;
//                }
//
//                @Override
//                public void onAnimationEnd(Animation animation) {
                    setBabyHeadAnimate(true, true);
                    ppwBarBean.getRvOtherBabys().setVisibility(View.GONE);
//                }
//
//                @Override
//                public void onAnimationRepeat(Animation animation) {
//                }
//            });
        }
    }

    /**
     * 获取正确的开始值
     *
     * @param isShow
     */
    private float getCurrentValue(boolean isShow, float start, float end) {
        return isShow ? start : end;
    }

    public void setGaussLayout(View view) {
        mGaussLayout = view;
    }

    /**
     * 设置其他宝宝
     * @param ids
     */
    public void setOtherBabys(List<BabyModel> ids){
        mIds.clear();
        mIds.addAll(ids);
        barBean.addOtherBabys(mIds);
    }

    private IZSMainTitleBarListener mBarListen;
    private IZSMainTitleScreenLightListener screenLightListener;
    public void setListener(IZSMainTitleBarListener listener) {
        mBarListen = listener;
    }

    public void setScreenLightListener(IZSMainTitleScreenLightListener screenLightListener){
        this.screenLightListener = screenLightListener;
    }

    private IZSMainTitleBarCloseOrOpenListener mCloseOrOpenListen;
    public void setListenerCloseOrOpen(IZSMainTitleBarCloseOrOpenListener closeOrOpenListen) {
        mCloseOrOpenListen = closeOrOpenListen;
    }

    private void setOnItemClickListener(OtherBabysRVAdapter.OnItemClickListener clickListener) {
        ppwBarBean.setOnItemClickListener(clickListener);
    }

    /**
     * 关闭的监听事件
     */
    public abstract class OnClickCloseTitleBarListener implements OnClickListener {
        @Override
        public void onClick(View v) {
            if (popupWindow!=null &&popupWindow.isShowing()) {
                closeTitleBar();
            }
            onClickTitleBar(v);
        }

        public abstract void onClickTitleBar(View v);
    }


    public void setFlContent(FrameLayout flContent) {
        this.flContent = flContent;
    }
    private void setFlContentIsShow(boolean isShow){
        flContent.setVisibility(isShow?VISIBLE:GONE);
    }

    private class WidgetBean {
        private View mView;
        private List<BabyModel> mList = new ArrayList<>();
        private OtherBabysRVAdapter mAdapter;

        private WidgetBean() {
        }

        public WidgetBean(View view) {
            mView = view;
        }

        //头像
        private CircleImageView ivBabyHead;
        //姓名
        private TextView tvBabyName;
        //二维码
        private ImageView ivQrCode;
        //定位
        private ImageView ivLocation;
        //数据
        private ImageView ivData;
        //消息
        private ImageView ivMessage;
        //搜索
        private ImageView ivSearch;
        //功能键的外部布局
        private LinearLayout llFunction;
        //其他宝宝的头像
        private RecyclerView rvOtherBabys;
        // 标题栏的布局
        private LinearLayout llRoot;

        public void setIvBabyHead(@IdRes int idRes) {
            this.ivBabyHead = (CircleImageView) findViewById(idRes);
            ivBabyHead.setImageResource(R.drawable.head_holder);
        }

        public void setTvBabyName(@IdRes int idRes) {
            this.tvBabyName = (TextView) findViewById(idRes);
        }

        public void setIvQrCode(@IdRes int idRes) {
            this.ivQrCode = (ImageView) findViewById(idRes);
            ivQrCode.setOnClickListener(new OnClickCloseTitleBarListener() {
                @Override
                public void onClickTitleBar(View v) {
                    String babyId = ZSApp.getInstance().getBabyId();
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("babyId",babyId);
                    jsonObject.put("userId",ZSApp.getInstance().getUserId());
                    String encode = DESUtils.encodeUrl(jsonObject.toJSONString());
                    PopupWindowQRCode.getInstance(screenLightListener).showPopup(mContext, v, encode,ZSApp.getInstance().getBabyModel(),ZSApp.getInstance().getLoginUser(),activityMain);
                    if (Build.VERSION.SDK_INT >= 21 && activityMain!= null) {
                        activityMain.getWindow().setStatusBarColor(getResources().getColor(R.color.qrcode_bg));
                        activityMain.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                    }
                    if (screenLightListener!= null){
                        screenLightListener.changeScreenLight();
                    }
                }
            });
        }

        public void setIvLocation(@IdRes int idRes) {
            this.ivLocation = (ImageView) findViewById(idRes);
            ivLocation.setOnClickListener(new OnClickCloseTitleBarListener() {
                @Override
                public void onClickTitleBar(View v) {
                    ToastUtils.showNormalToast(mContext,"更多功能敬请期待");
                }
            });
        }

        public void setIvData(@IdRes int idRes) {
            this.ivData = (ImageView) findViewById(idRes);
//            if(ZSApp.getInstance().gettAlertBooleanModel()!=null&&(ZSApp.getInstance().gettAlertBooleanModel().getAttendance()==0||ZSApp.getInstance().gettAlertBooleanModel().getEvaluation()==0||ZSApp.getInstance().gettAlertBooleanModel().getTeaching()==0)) {
//                dataBadgeView = new QBadgeView(mContext).bindTarget(ivData).setBadgeNumber(-1).setGravityOffset(4f, 1f, true);
//            }else {
//                dataBadgeView = new QBadgeView(mContext).bindTarget(ivData).setBadgeNumber(0).setGravityOffset(4f, 1f, true);
//            }
            dataBadgeView = new QBadgeView(mContext).bindTarget(ivData).setGravityOffset(4f, 1f, true).setShowShadow(false);
            ivData.setOnClickListener(new OnClickCloseTitleBarListener() {
                @Override
                public void onClickTitleBar(View v) {
                    Intent intent = new Intent(mContext, MeasureActivity.class);
                    mContext.startActivity(intent);
                }
            });
        }

        public void setIvMessage(@IdRes int idRes) {
            this.ivMessage = (ImageView) findViewById(idRes);
//            if(ZSApp.getInstance().gettAlertBooleanModel()!=null&&ZSApp.getInstance().gettAlertBooleanModel().getMsg()==0) {
//                messageBadgeView = new QBadgeView(mContext).bindTarget(ivMessage).setBadgeNumber(-1).setGravityOffset(4f, 1f, true);
//            }else {
//                messageBadgeView= new QBadgeView(mContext).bindTarget(ivMessage).setBadgeNumber(0).setGravityOffset(4f, 1f, true);
//            }
            messageBadgeView = new QBadgeView(mContext).bindTarget(ivMessage).setGravityOffset(4f, 1f, true).setShowShadow(false);
            ivMessage.setOnClickListener(new OnClickCloseTitleBarListener() {
                @Override
                public void onClickTitleBar(View v) {
                    Intent intent = new Intent(mContext, MsgCenterActivity.class);
                    mContext.startActivity(intent);
                }
            });
        }

        public void setIvSearch(@IdRes int idRes) {
            this.ivSearch = (ImageView) findViewById(idRes);
            ivSearch.setOnClickListener(new OnClickCloseTitleBarListener() {
                @Override
                public void onClickTitleBar(View v) {
                    if(isMain) {
                        Intent intent = new Intent(mContext, BabyIndexSearchActivity.class);
                        mContext.startActivity(intent);
                    }else {
                        Intent intent = new Intent(mContext, SearchActivity.class);
                        mContext.startActivity(intent);
                    }
                }
            });
        }

        public void setLlFunction(@IdRes int idRes) {
            this.llFunction = (LinearLayout) findViewById(idRes);
        }

        public void setRvOtherBabys(@IdRes int idRes) {
            this.rvOtherBabys = (RecyclerView) findViewById(idRes);
            //设置布局管理器
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            rvOtherBabys.setLayoutManager(linearLayoutManager);
            //设置适配器
            mAdapter = new OtherBabysRVAdapter(mContext, mList);
            rvOtherBabys.setAdapter(mAdapter);
        }

        public void addOtherBabys(List<BabyModel> list) {
            mList.clear();
            mList.addAll(list);
            mAdapter.notifyDataSetChanged();
        }

        public void setOnItemClickListener(OtherBabysRVAdapter.OnItemClickListener clickListener) {
            mAdapter.setOnItemClickListener(clickListener);
        }


        public void setLlRoot(@IdRes int idRes) {
            this.llRoot = (LinearLayout) findViewById(idRes);
        }

        private View findViewById(@IdRes int idRes) {
            return mView.findViewById(idRes);
        }


        public ImageView getIvBabyHead() {
            return ivBabyHead;
        }

        public TextView getTvBabyName() {
            return tvBabyName;
        }

        public ImageView getIvQrCode() {
            return ivQrCode;
        }

        public ImageView getIvLocation() {
            return ivLocation;
        }

        public ImageView getIvData() {
            return ivData;
        }

        public ImageView getIvMessage() {
            return ivMessage;
        }

        public ImageView getIvSearch() {
            return ivSearch;
        }

        public LinearLayout getLlFunction() {
            return llFunction;
        }

        public RecyclerView getRvOtherBabys() {
            return rvOtherBabys;
        }

        public LinearLayout getLlRoot() {
            return llRoot;
        }

        /**
         * 换宝宝的信息
         *
         * @param currentBabyBean 当前宝宝的信息
         * @param selectPosition  替换宝宝的位置
         * @return
         */
        public BabyModel exChangeBaby(BabyModel currentBabyBean, int selectPosition) {
            BabyModel selectBabyBean = mList.get(selectPosition);//选中的宝宝
            mList.remove(selectPosition);
            mList.add(selectPosition, currentBabyBean);
//            ivBabyHead.setImageDrawable(selectBabyBean.getHeadDrawable());
            if (currentBabyBean.isEmptyHeadImgUrl()) {
                ivBabyHead.setImageResource(R.drawable.head_holder);
            } else {
                loadBabyHead(selectBabyBean.getHeadImgUrlAbs(), ivBabyHead);
            }

            tvBabyName.setText(selectBabyBean.getBabyName());
            mAdapter.notifyItemChanged(selectPosition);
            return selectBabyBean;
        }
    }


    public void setOpenEnable(boolean openEnable) {
        isOpenEnable = openEnable;
    }

    private void loadBabyHead(String url, ImageView iv) {
        ImageLoader.loadHeadTarget(mContext, url, iv);
    }

    public void setfromMain(boolean isMain) {
        this.isMain=isMain;
    }

    public void setMsgBadgeViewShow(boolean isMain) {
        if(isMain){
            messageBadgeView.setBadgeNumber(-1);
        }else {
            messageBadgeView.setBadgeNumber(0);
        }
    }

    public void setDateBadgeViewShow(boolean isMain) {
        if(isMain){
            dataBadgeView.setBadgeNumber(-1);
        }else {
            dataBadgeView.setBadgeNumber(0);
        }
    }
}
