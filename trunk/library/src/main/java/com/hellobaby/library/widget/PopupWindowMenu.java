package com.hellobaby.library.widget;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LayoutAnimationController;
import android.view.animation.OvershootInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.hellobaby.library.R;
import com.hellobaby.library.utils.GaussLayoutUtils;
import com.hellobaby.library.utils.ScreenUtils;

import static com.hellobaby.library.R.id.ll;
import static com.umeng.analytics.a.p;


/**
 * Created by zwj on 2016/10/18.
 * description :通用全屏的菜单
 */

public class PopupWindowMenu {
    private final int duration = 300;//动画时间
    private final int startDelay = 150;//动画启动拖延时间
    private PopupWindow popupWindow;
    private Activity mContext;
    private ImageView ivAdd;
    private LinearLayout llContent;
    private View mGaussedLayout;
    /**
     * 正在关闭
     */
    private boolean isPopupWindowClosing = false;
    private LinearLayout llRoot;
    private LinearLayout llFourth;
    private LinearLayout llThird;
    private LinearLayout llSecond;
    private LinearLayout llFirst;

    /**
     * @param context
     * @param gaussLayout
     * @param items
     */
    /**
     *
     * @param context
     * @param items 每个item的属性
     * @param isRootBackground 是否整个背景
     * @param gaussLayout 需要模糊的界面
     */
    public PopupWindowMenu(Activity context, Item[] items, boolean isRootBackground, View gaussLayout) {
        mContext = context;
        showPPWPublish(context, items, isRootBackground, gaussLayout);
    }


    private void showPPWPublish(Activity activity, Item[] items, boolean isRootBackground, View gaussLayout) {
        isPopupWindowClosing = false;
        // 初始化布局文件
        View rootView = LayoutInflater.from(activity).inflate(R.layout.libppw_menu, null);
        llRoot = (LinearLayout) rootView.findViewById(R.id.ppwMenu_ll_root);
        llContent = (LinearLayout) rootView.findViewById(R.id.ppwMenu_ll_content);

        if (isRootBackground) {
            mGaussedLayout = GaussLayoutUtils.setGaussLayout(mContext,llRoot, gaussLayout);
        } else {
            mGaussedLayout = GaussLayoutUtils.setGaussLayout(mContext,llContent, gaussLayout);
        }
        //第四个
        llFourth = (LinearLayout) rootView.findViewById(R.id.ppwMenu_ll_fourth);
        //第三个
        llThird = (LinearLayout) rootView.findViewById(R.id.ppwMenu_ll_third);
        //第二个
        llSecond = (LinearLayout) rootView.findViewById(R.id.ppwMenu_ll_second);
        //最下方
        llFirst = (LinearLayout) rootView.findViewById(R.id.ppwMenu_ll_first);

        TextView tvFourth = findTextView(rootView, R.id.ppwMenu_tv_fourth);
        TextView tvThird = findTextView(rootView, R.id.ppwMenu_tv_third);
        TextView tvSecond = findTextView(rootView, R.id.ppwMenu_tv_second);
        TextView tvFirst = findTextView(rootView, R.id.ppwMenu_tv_first);

        int itemsLength = items.length;
        if (itemsLength == 4) {
            items[0].llContent = llFirst;
            items[0].textView = tvFirst;
            items[0].textView.setText(items[0].getText());
            setItemCLick(activity, items[0],0);
            if (itemsLength >= 2) {
                items[1].llContent = llSecond;
                items[1].textView = tvSecond;
                items[1].textView.setText(items[1].getText());
                setItemCLick(activity, items[1],1);
                if (itemsLength >= 3) {
                    items[2].llContent = llThird;
                    items[2].textView = tvThird;
                    items[2].textView.setText(items[2].getText());
                    setItemCLick(activity, items[2],2);
                    if (itemsLength == 4) {
                        items[3].llContent = llFourth;
                        items[3].textView = tvFourth;
                        items[3].textView.setText(items[3].getText());
                        setItemCLick(activity, items[3],3);
                    }
                }
            }
        }else if(itemsLength==3){
            setItem(items[0],0,llSecond,tvSecond);
            setItem(items[1],1,llThird,tvThird);
            setItem(items[2],2,llFourth,tvFourth);
        }else if(itemsLength==2){
            setItem(items[0],0,llThird,tvThird);
            setItem(items[1],1,llFourth,tvFourth);
        }else if(itemsLength==1){
            setItem(items[0],0,llFourth,tvFourth);
        }


        ivAdd = (ImageView) rootView.findViewById(R.id.ppwMenu_iv_add);

        popupWindow = new PopupWindow(rootView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        // 设置此参数获得焦点
        popupWindow.setFocusable(true);


        showItemsAnimation(llContent);

        rotation(ivAdd, 0, 45);

        ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissPPW(mGaussedLayout);
            }
        });
        llContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissPPW(mGaussedLayout);
            }
        });
    }

    private void setItem(Item item,int position,LinearLayout llContent,TextView textView){
        item.llContent = llContent;
        item.textView = textView;
        item.textView.setText(item.getText());
        setItemCLick(mContext,item,position);
    }

    public void dismissPPW(final View gaussedLayout) {
        if (isPopupWindowClosing) {
            return;
        }
//        gaussedLayout.setBackgroundResource(R.color.transparent);
        rotation(ivAdd, 45, 90);
        dismissItemsAnimation(llFirst).setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                isPopupWindowClosing = true;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                gaussedLayout.setVisibility(View.INVISIBLE);
                popupWindow.dismiss();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });     dismissItemsAnimation2(llSecond,2).setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                isPopupWindowClosing = true;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                gaussedLayout.setVisibility(View.INVISIBLE);
                popupWindow.dismiss();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });     dismissItemsAnimation2(llThird,3).setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                isPopupWindowClosing = true;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                gaussedLayout.setVisibility(View.INVISIBLE);
                popupWindow.dismiss();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });     dismissItemsAnimation2(llFourth,4).setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                isPopupWindowClosing = true;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                gaussedLayout.setVisibility(View.INVISIBLE);
                popupWindow.dismiss();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }

    /**
     * 展现ppw
     *
     * @param parentView 布局上的以view
     */
    public void showPpw(View parentView) {
        popupWindow.showAtLocation(parentView, Gravity.NO_GRAVITY, 0, ScreenUtils.getStatusHeight(mContext));
    }

    /**
     * 最下方的按钮的旋转
     *
     * @param target
     * @param from
     * @param to
     * @return
     */
    private ObjectAnimator rotation(Object target, int from, int to) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(target, "rotation", from, to);
        animator.setDuration(duration);
        animator.setStartDelay(startDelay);
        animator.start();
        return animator;
    }

    /**
     * 展示布局动画
     *
     * @param linearLayout
     */
    private void showItemsAnimation(LinearLayout linearLayout) {
        TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 4.0f, Animation.RELATIVE_TO_SELF, 0);
        animation.setInterpolator(new OvershootInterpolator(1.3f));
        animation.setDuration(duration);
        animation.setStartOffset(startDelay);
        LayoutAnimationController mLac = new LayoutAnimationController(animation, 0.12f);
        mLac.setInterpolator(new DecelerateInterpolator());
        linearLayout.setLayoutAnimation(mLac);
    }


    /**
     * 消除动画
     *
     * @param linearLayout
     * @return
     */
    private TranslateAnimation dismissItemsAnimation(LinearLayout linearLayout) {
        TranslateAnimation animation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF,//int fromXType
                0f,                        //float fromXValue
                Animation.RELATIVE_TO_SELF,//int toXType
                0f,                        //float toXValue
                Animation.RELATIVE_TO_SELF,//int fromYType
                0f,                       //float fromYValue
                Animation.RELATIVE_TO_SELF,//int toYType
                1f                          //int toYType
        );
        animation.setInterpolator(new DecelerateInterpolator());
        animation.setDuration(duration);
        animation.setStartOffset(startDelay);
        linearLayout.startAnimation(animation);
        return animation;
    }
    private TranslateAnimation dismissItemsAnimation2(LinearLayout linearLayout,float toSelfY) {
        TranslateAnimation animation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF,//int fromXType
                0f,                        //float fromXValue
                Animation.RELATIVE_TO_SELF,//int toXType
                0f,                        //float toXValue
                Animation.RELATIVE_TO_SELF,//int fromYType
                0f,                       //float fromYValue
                Animation.RELATIVE_TO_SELF,//int toYType
                toSelfY                         //int toYType
        );
        animation.setInterpolator(new DecelerateInterpolator());
        animation.setDuration(duration);
        animation.setStartOffset(startDelay);
        linearLayout.startAnimation(animation);
        return animation;
    }
    private IPopupWindowMenuOnClick itemOnClick;

    public void setItemOnClick(IPopupWindowMenuOnClick itemOnClick) {
        this.itemOnClick = itemOnClick;
    }

    /**
     * item的点击事件
     *
     * @param activity
     * @param item
     */
    private void setItemCLick(final Activity activity, final Item item, final int position) {
        item.llContent.setVisibility(View.VISIBLE);
        setTextViewDrawable(activity, item);
        item.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                if(itemOnClick!=null){
                    itemOnClick.onItemClick(v,position);
                }else{
                    Intent intent = new Intent(activity, item.getClazz());
                    intent.putExtra("type", item.getText());
                    activity.startActivity(intent);
                }
            }
        });
    }


    private void setTextViewDrawable(Activity activity, Item item) {
        Drawable drawable = activity.getResources().getDrawable(item.getDrawableResId());
        int x = ScreenUtils.dip2px(activity, 55);
        drawable.setBounds(0, 0, x, x);
        item.textView.setCompoundDrawablePadding(7);
        item.textView.setCompoundDrawables(null, drawable, null, null);
    }

    private TextView findTextView(View rootView, int tvId) {
        return (TextView) rootView.findViewById(tvId);
    }




    public static class Item {
        /**
         * 图片资源id
         */
        private int drawableResId;
        /**
         * 图片下不的文字
         */
        private String text;
        /**
         * 需要跳转的下一个界面
         */
        private Class clazz;

        private View llContent;
        private TextView textView;

        public Item(@DrawableRes int drawableResId, String text, Class clazz) {
            this.drawableResId = drawableResId;
            this.text = text;
            this.clazz = clazz;
        }

        public int getDrawableResId() {
            return drawableResId;
        }

        public void setDrawableResId(int drawableResId) {
            this.drawableResId = drawableResId;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public Class getClazz() {
            return clazz;
        }

        public void setClazz(Class clazz) {
            this.clazz = clazz;
        }
    }
}
