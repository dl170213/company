package com.fims.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 日期处理
 */
public class DateUtils {
    private final static Logger logger = LoggerFactory.getLogger(DateUtils.class);
    /**
     * 时间格式(yyyy-MM-dd)
     */
    public final static String DATE_PATTERN = "yyyy-MM-dd";
    /**
     * 时间格式(yyyy-MM-dd HH:mm:ss)
     */
    public final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static String format(Date date) {
        return format(date, DATE_PATTERN);
    }

    public static String format(Date date, String pattern) {
        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.format(date);
        }
        return null;
    }

    /**
     * 计算距离现在多久，非精确
     *
     * @param date
     * @return
     */
    public static String getTimeBefore(Date date) {
        Date now = new Date();
        long l = now.getTime() - date.getTime();
        long day = l / (24 * 60 * 60 * 1000);
        long hour = (l / (60 * 60 * 1000) - day * 24);
        long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        String r = "";
        if (day > 0) {
            r += day + "天";
        } else if (hour > 0) {
            r += hour + "小时";
        } else if (min > 0) {
            r += min + "分";
        } else if (s > 0) {
            r += s + "秒";
        }
        r += "前";
        return r;
    }

    /**
     * 计算距离现在多久，精确
     *
     * @param date
     * @return
     */
    public static String getTimeBeforeAccurate(Date date) {
        Date now = new Date();
        long l = now.getTime() - date.getTime();
        long day = l / (24 * 60 * 60 * 1000);
        long hour = (l / (60 * 60 * 1000) - day * 24);
        long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        String r = "";
        if (day > 0) {
            r += day + "天";
        }
        if (hour > 0) {
            r += hour + "小时";
        }
        if (min > 0) {
            r += min + "分";
        }
        if (s > 0) {
            r += s + "秒";
        }
        r += "前";
        return r;
    }


    /**
     * 获取当前周范围
     * */
    public static Map getTimeInterval(Date date) {
        Map<String,Object> map = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        // 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        // System.out.println("要计算日期为:" + sdf.format(cal.getTime())); // 输出要计算日期
        // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        // 获得当前日期是一个星期的第几天
        int day = cal.get(Calendar.DAY_OF_WEEK);
        // 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
        String imptimeBegin = sdf.format(cal.getTime());
        cal.add(Calendar.DATE, 6);
        String imptimeEnd = sdf.format(cal.getTime());
        map.put("starttime",imptimeBegin+" 00:00:00");
        map.put("endtime",imptimeEnd+" 23:59:59");
        System.out.println("本周,周一：" + imptimeBegin+",,,周日：" + imptimeEnd);
        return map;
    }


    /**
     * 根据当前日期获得上一周日期范围
     * */
    public static Map getLastTimeInterval() {
        Map<String,Object> map = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        int dayOfWeek = calendar1.get(Calendar.DAY_OF_WEEK) - 1;
        int offset1 = 1 - dayOfWeek;
        int offset2 = 7 - dayOfWeek;
        calendar1.add(Calendar.DATE, offset1 - 7);
        calendar2.add(Calendar.DATE, offset2 - 7);
        String lastBeginDate = sdf.format(calendar1.getTime());
        String lastEndDate = sdf.format(calendar2.getTime());
        map.put("starttime",lastBeginDate+" 00:00:00");
        map.put("endtime",lastEndDate+" 23:59:59");
        System.out.println("上周,周一：" + lastBeginDate+",,,周日：" + lastEndDate);
        return map;
    }

    /**
     * 获取上个月范围
     * */
    public static Map getMonthInterval(int index) {
        Map<String,Object> map = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MONTH,(0-index));
        String imptimeBegin = sdf.format(cal.getTime());
        cal.add(Calendar.MONTH,index);
        String imptimeEnd = sdf.format(cal.getTime());
        map.put("starttime",imptimeBegin+"-01 00:00:00");
        map.put("endtime",imptimeEnd+"-01 00:00:00");
        System.out.println("上月,月初：" + imptimeBegin+"-01 00:00:00,,,月末：" + imptimeEnd+"-01 00:00:00");
        return map;
    }

    /**
     * 获取这个月范围
     * */
    public static Map getMonthIntervalNow() {
        Map<String,Object> map = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String imptime = sdf.format(new Date());
        String imptime1 = sdf1.format(new Date());
        map.put("starttime",imptime+"-01 00:00:00");
        map.put("endtime",imptime1);
        System.out.println("本月,月初：" + imptime+"-01 00:00:00,,,月末：" + imptime1);
        return map;
    }

    /**
     * 获取这个月范围
     * */
    public static Map getMonthIntervalNow1() {
        Map<String,Object> map = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MONTH,0);
        String imptimeBegin = sdf.format(cal.getTime());
        cal.add(Calendar.MONTH,1);
        String imptimeEnd = sdf.format(cal.getTime());
        map.put("starttime",imptimeBegin+"-01 00:00:00");
        map.put("endtime",imptimeEnd+"-01 00:00:00");
        System.out.println("本月,月初：" + imptimeBegin+"-01 00:00:00,,,月末：" + imptimeEnd+"-01 00:00:00");
        return map;
    }

    public static String getdep(int x,int y){

        // 创建一个数值格式化对象
        NumberFormat numberFormat = NumberFormat.getInstance();
        // 设置精确到小数点后2位
        numberFormat.setMaximumFractionDigits(2);
        return numberFormat.format((float) x / (float) y * 100)+"%";
    }

    /**
     * 精确加法
     */
    public static double add(double value1, double value2) {
        BigDecimal b1 = BigDecimal.valueOf(value1);
        BigDecimal b2 = BigDecimal.valueOf(value2);

        return b1.add(b2).doubleValue();
    }

    /**
     * 精确减法
     */
    public static double subtract(double value1, double value2) {
        BigDecimal b1 = BigDecimal.valueOf(value1);
        BigDecimal b2 = BigDecimal.valueOf(value2);
        return b1.subtract(b2).doubleValue();
    }

    //相乘
    public static double mul(double d1,double d2){
        BigDecimal b1=new BigDecimal(Double.toString(d1));
        BigDecimal b2=new BigDecimal(Double.toString(d2));
        return b1.multiply(b2).doubleValue();

    }

    //String四舍五入两位小数
    public static double string22double(String a){
        BigDecimal bd = new BigDecimal(a);
        return bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    //String四舍五入两位小数
    public static String string22doubleString(String a){
        Double d= Double.parseDouble(a);
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(d);
    }


    public static void main(String[] args){
//        System.out.println(DateUtils.format(new Date("2018-05-01")));

        List<Map<String,String>> lisst = new ArrayList<>();
        for(int i=0;i<10;i++)
        {
            Map<String,String> map = new HashMap<>();
            map.put(i+"",i+"");
            lisst.add(map);
        }
        for(int i = 0;i<lisst.size();i++){
            System.out.println(lisst.toString());
            lisst.remove(i);
            i--;
        }

        System.out.println(Double.parseDouble("12")*Double.parseDouble("103.4"));
        System.out.println(mul(Double.parseDouble("12"),Double.parseDouble("103.4")));
    }
}
