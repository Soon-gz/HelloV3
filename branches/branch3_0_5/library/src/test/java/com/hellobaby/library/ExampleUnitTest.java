package com.hellobaby.library;

import com.hellobaby.library.utils.DateUtil;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.CHINA);
        String timeStr = "2010-11-30 10:12:23";
        String timeStr2 = "2012-11-30 10:12:23";
        try {
            Date date = sdf.parse(timeStr);
//            System.out.println(DateUtil.getDescriptionTimeFromTimestamp(date.getTime()));
//            System.out.println(DateUtil.getDescriptionTimeFromTimestamp(new Date().getTime()));
            System.out.println(DateUtil.getFormatTimeFromTimestamp(date.getTime(), "yyyy年MM月dd日"));
//            System.out.println(DateUtil.getFormatTimeFromTimestamp(date.getTime(), null));
//            System.out.println(DateUtil.getFormatTimeFromTimestamp(new Date().getTime(), null));
//            System.out.println(DateUtil.getMixTimeFromTimestamp(date.getTime(), 3 * 24 * 60 * 60, "yyyy年MM月dd日 hh:mm"));
//            System.out.println(DateUtil.getMixTimeFromTimestamp(date.getTime(), 24 * 60 * 60, null));
//            System.out.println(DateUtil.getMixTimeFromTimestamp(new Date().getTime(), 3 * 24 * 60 * 60, "yyyy年MM月dd日 " +
//                    "" + "hh:mm"));
//            DateUtil.daysBetween(timeStr,timeStr2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}