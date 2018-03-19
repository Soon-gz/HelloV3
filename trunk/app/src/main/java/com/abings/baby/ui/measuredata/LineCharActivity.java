package com.abings.baby.ui.measuredata;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.abings.baby.R;
import com.abings.baby.ZSApp;
import com.abings.baby.ui.base.BaseTitleActivity;
import com.alibaba.fastjson.JSONObject;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.utils.MPPointF;
import com.hellobaby.library.data.model.MeasureModel;
import com.hellobaby.library.data.model.TAlertBooleanModel;
import com.hellobaby.library.utils.DateUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.hellobaby.library.Const.NORMAL_ACTIVITY_RESULT;

/**
 * 折线图
 */
public class LineCharActivity extends BaseTitleActivity implements  LineCharMvpView{
    @Inject
    LineCharPresenter presenter;
    List<Entry> entries = new ArrayList<>();
    public static final int NEW_RESULTCODE=1001;
    @BindView(R.id.lineChart)
    LineChart mChart;
    @BindView(R.id.linechar_tv1)
    TextView linchar1;
    @BindView(R.id.linechar_tv2)
    TextView linchar2;
    @BindView(R.id.linechar_tv3)
    TextView linchar3;
    @BindView(R.id.linechar_ll_history)
    LinearLayout linearLayout;
    public static final int FLAG=30;

    XAxis xAxis;
    int color;
    String isHeight;
    LineData data;
    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_linechar;
    }

    @Override
    protected void initDaggerInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initViewsAndEvents(@Nullable Bundle savedInstanceState) {
        presenter.attachView(this);
        setBtnLeftClickFinish();
        Intent intent= getIntent();
        isHeight=intent.getStringExtra("isHeight");
        if(isHeight.equals("1")){
            bTvTitle.setText("身高");
            color=getResources().getColor(R.color.colorHeight);
            presenter.selectHisHeight(ZSApp.getInstance().getBabyId());
        }else{
            bTvTitle.setText("体重");
            color=getResources().getColor(R.color.colorWeight);
//            presenter.selectHisWeight(ZSApp.getInstance().getBabyId());
            presenter.selectHisWeight(ZSApp.getInstance().getBabyId());
        }
        setBtnLeftClickFinish();
        setBtnRightDrawableRes(R.drawable.title_add);
        initChart();
        linchar1.setText("");
    linchar2.setText("");
    linchar3.setText("");
        if(getIntent().getStringExtra("isNew").equals("1"))
        startAdd();
    }

    private void initChart() {
        mChart.setDrawGridBackground(false);
        mChart.setDrawBorders(false);
        mChart.setTouchEnabled(true);
        Description description=new Description();
        description.setText("");
        mChart.setDescription(description);
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        mChart.setPinchZoom(true);
        mChart.setDragEnabled(true);
        mChart.setScaleXEnabled(true);
        mChart.setScaleYEnabled(false);
        mChart.setPinchZoom(false);
        mChart.setMarker(new MyMarkView(this));
        mChart.setBorderWidth(0.0f);
        data = getLineData();
        mChart.setData(data);
        mChart.animateY(3000);
        mChart.setScaleMinima(1f, 1f);
//        mChart.setVisibleXRangeMinimum(4f*FLAG);
//        mChart.setMinimumWidth(4*FLAG);
//        mChart.setVisibleXRangeMinimum(4*FLAG);
        Legend l = mChart.getLegend();
        l.setEnabled(false);
        xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(12*FLAG);
        xAxis.setAxisLineWidth(0.1f);
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(false);
        xAxis.setTextColor(Color.BLACK);
        IAxisValueFormatter formatter = new IAxisValueFormatter() {

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
               int i=(int)(value/FLAG);
                if(i==0)
                    return  "出生";
                if(i%12==0){
                    return i/12+"周岁";
                }else{
                    return i+"个月";
                }
            }
            @Override
            public int getDecimalDigits() {  return 0; }
        };
        xAxis.setValueFormatter(formatter);
        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setDrawAxisLine(false);
        leftAxis.setStartAtZero(false);
        leftAxis.setEnabled(false);
        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setEnabled(false);
        rightAxis.setStartAtZero(false);
        mChart.setOnChartGestureListener(new OnChartGestureListener(){

            @Override
            public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
            }

            @Override
            public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
            }

            @Override
            public void onChartLongPressed(MotionEvent me) {

            }

            @Override
            public void onChartDoubleTapped(MotionEvent me) {
                xAxis.setGranularity(1*FLAG);
                mChart.setMaxVisibleValueCount(4*FLAG);
            }

            @Override
            public void onChartSingleTapped(MotionEvent me) {

            }

            @Override
            public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {

            }

            @Override
            public void onChartScale(MotionEvent me, float scaleX, float scaleY) {
                if(scaleX>=1){
                    xAxis.setGranularity(FLAG);
                }else
                    xAxis.setGranularity(12*FLAG);
            }

            @Override
            public void onChartTranslate(MotionEvent me, float dX, float dY) {
            }
        });
        // 刷新图表
//        data = getLineData();
//        mChart.setData(data);
        mChart.invalidate();
    }
    private LineData getLineData() {
        LineDataSet set1 = new LineDataSet(entries, "LineChart Test");
        set1.setCubicIntensity(0.1f);
        set1.setDrawFilled(true);  //设置包括的范围区域填充颜色
        set1.setFillColor(color);
        set1.setFillAlpha(255);
        set1.setCircleColor(Color.WHITE);
        set1.setCircleColorHole(color);
        set1.setDrawCircles(true);  //设置有圆点
        set1.setLineWidth(2f);    //设置线的宽度
        set1.setColor(color);    //设置曲线的颜色
//        set1.setHighlightEnabled(false);
        set1.setDrawHighlightIndicators(false);
        set1.setCircleRadius(4f);
        set1.setCircleHoleRadius(3f);
        set1.setDrawValues(false);
        return new LineData(set1);
    }

    @Override
    protected void btnRightOnClick(View v) {
        Intent intent=new Intent(this,AddMeasureDataActivity.class);
        intent.putExtra("isHeight",isHeight);
        startActivityForResult(intent,NORMAL_ACTIVITY_RESULT);
    }

    @Override
    public void showMsg(String msg) {

    }

    @Override
    public void showData(Object o) {

    }

    @Override
    public void showError(String err) {

    }
    public void reflush(String text1,String text2,String text3){
        if(isHeight.equals("0")){
        linchar1.setText(text1+"kg");
        }else {
            linchar1.setText(text1+"cm");
        }
        linchar2.setText(text2);
        linchar3.setText(text3);
    }

    @Override
    public void setLastData(JSONObject jsonObject) {

    }

    @Override
    public void selectHisHeight(List<MeasureModel> models) {
        entries=new ArrayList<>();
        for(MeasureModel model:models)
        {
            float y,x;
            if(model.getHeight()!=0.0f){
                y=model.getHeight();
            }else
                y=model.getWeight();
            x= -DateUtil.daysBetween(model.getInputDate(),ZSApp.getInstance().getBirthday());
            entries.add(new Entry(x,y,DateUtil.ServerDate2NYRFormat(model.getInputDate())));
        }
        Collections.reverse(entries);
        data = getLineData();
        mChart.setData(data);
        mChart.invalidate();
        mChart.notifyDataSetChanged();
        Entry e=entries.get(entries.size()-1);
        String val="";
        int i=(int)(e.getX()/FLAG);
        if(i==0)
            val=  "出生";
        else if(i%12==0){
            val= i/12+"周岁";
        }else if(i<12){
            val=i+"个月";
        }else {
            val=i/12+"周岁"+i%12+"个月";
        }
        reflush(e.getY()+"",val,e.getData().toString());
    }

    @Override
    public void showBadgeView(TAlertBooleanModel tAlertBooleanModel) {

    }

    public class MyMarkView extends MarkerView {
        private TextView tvMarkText;

        public MyMarkView(Context context) {
            super(context, R.layout.mark_view);
            tvMarkText = (TextView) findViewById(R.id.tvMarkText);
        }

        @Override
        public void refreshContent(Entry e, Highlight highlight) {
            tvMarkText.setText("" + e.getY());
            String val="";
            int i=(int)(e.getX()/FLAG);
            if(i==0)
                val=  "出生";
            else if(i%12==0){
                val= i/12+"周岁";
            }else if(i<12){
                val=i+"个月";
            }else {
                val=i/12+"周岁"+i%12+"个月";
            }
            reflush(e.getY()+"",val,e.getData().toString());
            super.refreshContent(e, highlight);
        }

        private MPPointF mOffset;

        @Override
        public MPPointF getOffset() {

            if(mOffset == null) {
                mOffset = new MPPointF(-(getWidth() / 2), -getHeight());
            }

            return mOffset;
        }
    }
    @OnClick(R.id.linechar_ll_history)
    public void onClick(){
        Intent intent=new Intent(this,MeasureDetailActivity.class);
        intent.putExtra("isHeight",isHeight);
        startActivityForResult(intent,NORMAL_ACTIVITY_RESULT);
    }
    public void startAdd(){
        Intent intent=new Intent(this,AddMeasureDataActivity.class);
        intent.putExtra("isNew",1);
        intent.putExtra("isHeight",isHeight);
        startActivityForResult(intent,NEW_RESULTCODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == NEW_RESULTCODE) {
            if (null == data ) {
                finish();
            }
        }
        if (isHeight.equals("1")) {
            bTvTitle.setText("身高");
            color = getResources().getColor(R.color.colorHeight);
            presenter.selectHisHeight(ZSApp.getInstance().getBabyId());
        } else {
            bTvTitle.setText("体重");
            color = getResources().getColor(R.color.colorWeight);
            presenter.selectHisWeight(ZSApp.getInstance().getBabyId());
        }
    }
}
