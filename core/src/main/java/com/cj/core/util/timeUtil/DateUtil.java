package com.cj.core.util.timeUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil{

    // ======================日期格式化常量=====================//

    public static final String YYYY_MM_DDHHMMSS = "yyyy-MM-dd HH:mm:ss";

    public static final String YYYY_MM_DD = "yyyy-MM-dd";

    public static final String YYYY_MM = "yyyy-MM";

    public static final String YYYY = "yyyy";

    public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    public static final String YYYYMMDDHHMMSSSSS = "yyyyMMddHHmmssSSS";

    public static final String YYYYMMDD = "yyyyMMdd";

    public static final String YYYYMM = "yyyyMM";

    public static final String YYYYMMDDHHMMSS_1 = "yyyy/MM/dd HH:mm:ss";

    public static final String YYYY_MM_DD_1 = "yyyy/MM/dd";

    public static final String YYYY_MM_1 = "yyyy/MM";

    /**
     *
     * 自定义取值，Date类型转为String类型
     *
     * @param date 日期
     * @param pattern 格式化常量
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static String dateToStr(Date date, String pattern)
    {
        SimpleDateFormat format = null;

        if (null == date){
            return null;
        }
        format = new SimpleDateFormat(pattern, Locale.getDefault());

        return format.format(date);
    }

    /**
     * 将字符串转换成Date类型的时间
     * <hr>
     *
     * @param str 日期类型的字符串<br>
     *            datePattern :YYYY_MM_DD<br>
     * @return java.config.Date
     */
    public static Date strToDate(String str, String pattern)
    {
        if (str == null)
        {
            return null;
        }
        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try
        {
            date = sdf.parse(str);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        return date;
    }

    //==================================================日期加減=============================


    /**
     * 日期相加减
     * @param time
     *             时间字符串 yyyy-MM-dd HH:mm:ss
     * @param num
     *             加的数，-num就是减去
     * @return
     *             减去相应的数量的年的日期
     * @throws ParseException
     */
    public static Date yearAddNum(Date time, Integer num) {
        //SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //Date date = format.parse(time);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        calendar.add(Calendar.YEAR, num);
        Date newTime = calendar.getTime();
        return newTime;
    }

    /**
     *
     * @param time
     *           时间
     * @param num
     *           加的数，-num就是减去
     * @return
     *          减去相应的数量的月份的日期
     * @throws ParseException Date
     */
    public static Date monthAddNum(Date time, Integer num){
        //SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //Date date = format.parse(time);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        calendar.add(Calendar.MONTH, num);
        Date newTime = calendar.getTime();
        return newTime;
    }

    /**
     *
     * @param time
     *           时间
     * @param num
     *           加的数，-num就是减去
     * @return
     *          减去相应的数量的天的日期
     * @throws ParseException Date
     */
    public static Date dayAddNum(Date time, Integer num){
        //SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //Date date = format.parse(time);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        calendar.add(Calendar.DAY_OF_MONTH, num);
        Date newTime = calendar.getTime();
        return newTime;
    }


    /**
     * 获取本月第一天时间
     */
    public static Date getMonthStartDate(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH,1);
        return calendar.getTime();
    }

    /**
     * 获取本月最后一天
     *
     */
    public static Date getMonthEndDate(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return calendar.getTime();
    }
    /**
     * 获取本周的开始时间
     */
    public static Date getBeginWeekDate(){
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
        //周日是1 ，周一是 2 ，周二是 3
        //所以，当周的第一天 = 当前日期 - 距离周一过了几天（周一过了0天，周二过了1天，   周日过了6天）
        // 2 - 周一的（dayofweek：2 ）= 0
        // 2 - 周二的（dayofweek：3 ）= -1
        //                      .
        //                      .
        // 2 - 周日的（dayofweek：1） = 1（这个是不符合的需要我们修改）===》2 - 周日的（dayofweek：1 ==》8 ） = -6
        if (dayofweek == 1) {
            dayofweek += 7;
        }
        cal.add(Calendar.DATE, 2 - dayofweek);
        return cal.getTime();
    }


    /**
     * 本周的结束时间
     * 开始时间 + 6天
     */
    public static Date getEndWeekDate(){
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
        if (dayofweek == 1) {
            dayofweek += 7;
        }
        cal.add(Calendar.DATE, 8 - dayofweek);//2 - dayofweek + 6
        return cal.getTime();
    }
}
