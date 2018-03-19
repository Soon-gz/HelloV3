package com.abings.baby.teacher.ui.PrizeDraw;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.abings.baby.teacher.R;
import com.abings.baby.teacher.ui.PrizeDraw.Adress.PrizeAdressActivity;
import com.abings.baby.teacher.ui.PrizeDraw.history.PrizeHistoryActivity;
import com.abings.baby.teacher.ui.PrizeDraw.prizeRule.PrizeRuleActivity;
import com.abings.baby.teacher.ui.base.BaseTitleActivity;
import com.abings.baby.teacher.ui.main.MainActivity;
import com.hellobaby.library.data.model.PrizeDrawBean;
import com.hellobaby.library.data.model.PrizeLuckyModel;
import com.hellobaby.library.ui.webview.BaseWebViewActivity;
import com.hellobaby.library.utils.CustomAlertDialog;
import com.hellobaby.library.widget.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class LuckyDrawActivity extends BaseTitleActivity implements LuckyDrawMvpView{

    @Inject
    LuckyDrawPresenter luckyDrawPresenter;

    @BindView(R.id.lucky_start)
    ImageView lucky_start;

    @BindView(R.id.prizeGridView)
    FullGridView prizeGridView;

    @BindView(R.id.prize_scroll_view)
    ScrollView scrollView;

    @BindView(R.id.text_score_num)
    TextView text_score_num;

    ObjectAnimator animator;

    FullGridViewAdapter baseAdapter;

    List<PrizeDrawBean> prizeDrawBeenS;

    boolean isFirstIn = true;

    boolean isReturnLuckyNumber = false;

    float startAngle = 0f;


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_lucky_draw;
    }

    @Override
    protected void initDaggerInject() {
        getActivityComponent().inject(this);
        luckyDrawPresenter.attachView(this);
    }

    @Override
    protected void btnRightOnClick(View v) {
        startActivity(new Intent(this, PrizeHistoryActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        luckyDrawPresenter.getScore();
        if (isFirstIn){
            scrollView.smoothScrollTo(0, 0);
            isFirstIn = false;
            return;
        }
    }

    @Override
    protected void initViewsAndEvents(@Nullable Bundle savedInstanceState) {
//        animator = (ObjectAnimator) AnimatorInflater.loadAnimator(this,R.animator.rotate_anmator);
//        animator = ObjectAnimator.ofFloat(lucky_start,"rotation",0,72000);
//        animator.setDuration(30000);
        setBtnRightDrawableRes(R.drawable.history);
        setBtnLeftClickFinish();

        prizeDrawBeenS = new ArrayList<>();

        baseAdapter = new FullGridViewAdapter(prizeDrawBeenS,this);
        prizeGridView.setAdapter(baseAdapter);

        luckyDrawPresenter.getRecordDrawList();
    }


    @OnClick({R.id.start_btn,R.id.rule_btn})
    public void click(View view){
        switch (view.getId()){
            case R.id.start_btn:
                if(!isReturnLuckyNumber){
                    if(animator != null && animator.isStarted()){
                        ToastUtils.showNormalToast(this,"正在计算中奖结果信息~请稍后");
                    }else{
                        isReturnLuckyNumber = true;
                        luckyDrawPresenter.getLuckDraw();
                    }
                }else{
                    ToastUtils.showNormalToast(this,"正在计算中奖结果~请稍后");
                }
                break;
            case  R.id.rule_btn:
                startActivity(new Intent(this, PrizeRuleActivity.class).putExtra(BaseWebViewActivity.kWebUrl,"http://www.baidu.com"));
                break;
        }
    }


    @Override
    public void showData(Object o) {
        isReturnLuckyNumber = false;
    }

    @Override
    public void getScores(String integer) {
        text_score_num.setText(integer);
    }

    @Override
    public void getLuckyDraw(final PrizeLuckyModel prizeLuckyModel) {
        float prizeAngle = getPrizeAngle(prizeLuckyModel.getId());
        float toAngle = 360*40 + prizeAngle;
        animator = ObjectAnimator.ofFloat(lucky_start,"rotation",startAngle,toAngle);
        animator.setDuration(5000);
        animator.start();
        String numberStr = text_score_num.getText().toString();
        int number = Integer.parseInt(numberStr) - 100;
        text_score_num.setText(number+"");
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                String msg = "恭喜抽中奖品："+prizeLuckyModel.getName()+"\n"+"点击领取。";
                if(prizeLuckyModel.getEntity() > 0){
                    CustomAlertDialog.dialogWithSure(LuckyDrawActivity.this, msg, new CustomAlertDialog.OnDialogClickListener() {
                        @Override
                        public void doSomeThings() {
                            luckyDrawPresenter.getScore();
                        }

                        @Override
                        public void cancle() {

                        }
                    });
                }else{
                    CustomAlertDialog.dialogExSureCancel(msg, LuckyDrawActivity.this, new CustomAlertDialog.OnDialogClickListener() {
                        @Override
                        public void doSomeThings() {
                            Intent intent = new Intent(LuckyDrawActivity.this, PrizeAdressActivity.class);
                            intent.putExtra("name",prizeLuckyModel.getName());
                            intent.putExtra("orderNum",prizeLuckyModel.getOrderNum());
                            intent.putExtra("price",prizeLuckyModel.getPoints()+"");
                            intent.putExtra("drawId",prizeLuckyModel.getDrawId()+"");
                            startActivity(intent);
                        }

                        @Override
                        public void cancle() {
                            luckyDrawPresenter.getScore();
                        }
                    });
                }
            }
        });
        startAngle = prizeAngle;
        isReturnLuckyNumber = false;
    }

    @Override
    public void getRecordDrawList(List duiPrizeBeanList) {
        if (duiPrizeBeanList != null){
            prizeDrawBeenS.addAll(duiPrizeBeanList);
            baseAdapter.notifyDataSetChanged();
        }
    }


    public float getPrizeAngle(int prizeId){
        float prizeAngle = 90f;
        switch (prizeId){
            case 1:
                prizeAngle = 0f;
                break;
            case 2:
                prizeAngle = 45f;
                break;
            case 3:
                prizeAngle = 90f;
                break;
            case 4:
                prizeAngle = 135f;
                break;
            case 5:
                prizeAngle = 180f;
                break;
            case 6:
                prizeAngle = 225f;
                break;
            case 7:
                prizeAngle = 270f;
                break;
            case 8:
                prizeAngle = 315f;
                break;
        }
        return prizeAngle;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

}
