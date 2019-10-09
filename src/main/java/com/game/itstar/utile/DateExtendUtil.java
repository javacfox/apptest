package com.game.itstar.utile;


import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 创建时间：2019/4/1 16:45
 * 版本：1.0
 * 描述：日期工具扩展
 */
public class DateExtendUtil{
    public static final String YEAR_BEGIN = "-01-01 00:00:00";
    public static final String YEAR_END = "-12-31 23:59:59";
    public static final long ONE_DAY = 24 * 60 * 60 * 1000L;


    public static String dateToString(Date date, String formartStr) {
        String strDate = null;
        if (formartStr != null && !"".equals(formartStr)) {
            SimpleDateFormat sdf = new SimpleDateFormat(formartStr);
            strDate = sdf.format(date);
        }

        return strDate;
    }

    public static Date stringToDate(String strDate, String formartStr) {
        Date date = null;
        if (formartStr != null && !"".equals(formartStr)) {
            SimpleDateFormat sdf = new SimpleDateFormat(formartStr);

            try {
                date = sdf.parse(strDate);
            } catch (ParseException var5) {
                date = null;
                var5.printStackTrace();
            }
        }

        return date;
    }

    public static String nowTime(String formartStr) {
        String strDate = null;
        if (formartStr != null && !"".equals(formartStr)) {
            SimpleDateFormat sdf = new SimpleDateFormat(formartStr);
            strDate = sdf.format(new Date());
        }

        return strDate;
    }

    public static boolean checkDateInToday(Date date) {
        if (date == null) {
            return true;
        } else {
            boolean flag = false;
            Date now = new Date();
            String nowStr = dateToString(now, "yyyy-MM-dd");
            String dateStr = dateToString(date, "yyyy-MM-dd");
            if (!nowStr.equals(dateStr)) {
                flag = true;
            }

            return flag;
        }
    }

    public static Timestamp getYearBegin() {
        Calendar calendar = Calendar.getInstance();
        String year = String.valueOf(calendar.get(Calendar.YEAR));

        return Timestamp.valueOf(year + YEAR_BEGIN);
    }

    public static Timestamp getYearEnd() {
        Calendar calendar = Calendar.getInstance();
        String year = String.valueOf(calendar.get(Calendar.YEAR));

        return Timestamp.valueOf(year + YEAR_END);
    }

    public static Date dateBackOrInto(Date date, Integer day) {
        if (date != null && day != null) {
            long result = date.getTime() + ONE_DAY * day;
            return new Date(result);
        }
        return date;
    }
}
