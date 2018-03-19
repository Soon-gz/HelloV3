package com.hellobaby.library.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.flyco.dialog.widget.base.BottomBaseDialog;
import com.hellobaby.library.R;
import com.hellobaby.library.utils.DateUtil;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by zwj on 2016/7/25.
 */
public class BottomPickerDateDialog extends BottomBaseDialog<BottomPickerDateDialog> {
    private Context mContext;
    private BottomOnDateChangedListener mBottomOnDateChangedListener;

    private int mYear = -1;
    private int mMonth = -1;
    private int mDay = -1;
    private boolean neddmax = false;
    private String minday="";

    public BottomPickerDateDialog(Context context, View animateView, int year, int month, int day,boolean needmax , BottomOnDateChangedListener bottomOnDateChangedListener) {
        super(context, animateView);
        this.mContext = context;
        this.mYear = year;
        this.mMonth = month - 1;
        this.mDay = day;
        this.neddmax=needmax;
        this.mBottomOnDateChangedListener = bottomOnDateChangedListener;
    }

    public BottomPickerDateDialog(Context context, View animateView, int year, int month, int day,boolean needmax,String minday, BottomOnDateChangedListener bottomOnDateChangedListener) {
        super(context, animateView);
        this.mContext = context;
        this.mYear = year;
        this.mMonth = month - 1;
        this.mDay = day;
        this.neddmax=needmax;
        this.minday=minday;
        this.mBottomOnDateChangedListener = bottomOnDateChangedListener;
    }


    @Override
    public View onCreateView() {

        View view = LayoutInflater.from(mContext).inflate(R.layout.custom_datepicker_aboutme, null);
        TextView tvCancel = (TextView) view.findViewById(R.id.customDatePicker_tv_cancel);
        TextView tvOk = (TextView) view.findViewById(R.id.customDatePicker_tv_ok);


        final DatePicker datePicker = (DatePicker) view.findViewById(R.id.custom_datepicker_view);
        setDatePickerDividerColor(datePicker);
        if(neddmax)
        datePicker.setMaxDate(System.currentTimeMillis());
        if(!minday.equals(""))
            try {
                datePicker.setMinDate(DateUtil.day2time(minday));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        if (mYear < 0 && mMonth < 0 && mDay < 0) {
            Calendar mycalendar = Calendar.getInstance(Locale.CHINA);
            Date mydate = new Date(); //获取当前日期Date对象
            mycalendar.setTime(mydate);////为Calendar对象设置时间为当前日期
            //获取Calendar对象中的年
            mYear = mycalendar.get(Calendar.YEAR);
            //获取Calendar对象中的月
            mMonth = mycalendar.get(Calendar.MONTH);
            //获取这个月的第几天
            mDay = mycalendar.get(Calendar.DAY_OF_MONTH);
        }
        datePicker.init(mYear, mMonth, mDay, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                mYear = year;
                mMonth = monthOfYear;
                mDay = dayOfMonth;
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomOnDateChangedListener.onDateChanged(datePicker, mYear, mMonth + 1, mDay, mYear + "年" + (mMonth + 1) + "月" + mDay + "日");
                dismiss();
            }
        });
        return view;
    }

    @Override
    public void setUiBeforShow() {

    }

    public interface BottomOnDateChangedListener {
        void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth, String showData);
    }


    private void setDatePickerDividerColor(DatePicker datePicker) {
        // Divider changing:

        // 获取 mSpinners
        LinearLayout llFirst = (LinearLayout) datePicker.getChildAt(0);

        // 获取 NumberPicker
        LinearLayout mSpinners = (LinearLayout) llFirst.getChildAt(0);
        for (int i = 0; i < mSpinners.getChildCount(); i++) {
            NumberPicker picker = (NumberPicker) mSpinners.getChildAt(i);

            Field[] pickerFields = NumberPicker.class.getDeclaredFields();
            for (Field pf : pickerFields) {
                if (pf.getName().equals("mSelectionDivider")) {
                    pf.setAccessible(true);
                    try {
                        pf.set(picker, new ColorDrawable(0xff808080));
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    } catch (Resources.NotFoundException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }
    }
}
