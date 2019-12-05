package cn.center.tool;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author song
 * @title 时间处理工具类
 * @projectName demo
 * @description TODO
 * @date 2019年11月21日 下午6:24:23
 */
public class DateUtils {

    /**
     * yyyy-MM-dd HH:mm:ss
     */
    public static final String yyyyMMddHHmmss = "yyyy-MM-dd HH:mm:ss";
    /**
     * yyyyMMdd
     */
    public static final String yyyyMMdd = "yyyy-MM-dd";
    /**
     * yyyy-MM-dd'T'HH:mm:ss.SSSZ
     */
    public static final String YYYYMMddTHHmmssSSSZ = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    
    /**
     * @description: 格式化日期<br>yyyy-MM-dd HH:mm:ss
     * @param date
     * @return String
     * @author song
     * @date 2019年11月21日 下午5:25:40
     */
    public static String toString(Date date) {
        SimpleDateFormat f = new SimpleDateFormat(yyyyMMddHHmmss);
        return f.format(date);
    }
    
    /**
     * @description: 将日期对象转换成指定格式的字符串
     * @param date
     * @param pattern
     * @return
     * @author song
     * @date 2019年11月21日 下午5:58:12
     */
    public static String toString(Date date, String pattern) {
    	SimpleDateFormat f = new SimpleDateFormat(pattern);
    	return f.format(date);
    }
    
    /**
     * @description: 格式化日期<br>yyyy-MM-dd HH:mm:ss
     * @param str
     * @return
     * @author song
     * @throws ParseException 
     * @date 2019年11月21日 下午5:43:12
     */
    public static Date toDate(String str) throws ParseException {
        SimpleDateFormat f = new SimpleDateFormat(yyyyMMddHHmmss);
        return f.parse(str);
    }
    /**
     * @description: 格式化日期
     * @param str
     * @return
     * @author song
     * @throws ParseException 
     * @date 2019年11月21日 下午5:43:12
     */
    public static Date toDate(String str, String pattern) throws ParseException {
    	SimpleDateFormat f = new SimpleDateFormat(pattern);
    	return f.parse(str);
    }
    
    /**
     * @description: 10位时间戳转字符串<br>yyyy-MM-dd HH:mm:ss
     * @param str
     * @return
     * @author song
     * @date 2019年11月21日 下午5:46:10
     */
    public static String getDateByTimeOfTen(long time) {
    	long lt = new Long(time + "000");
    	return toString(new Date(lt));
    }
    
    /**
     * @description: 10位时间戳转字符串<br>yyyy-MM-dd HH:mm:ss
     * @param str
     * @return
     * @author song
     * @param pattern 
     * @date 2019年11月21日 下午5:46:10
     */
    public static String getDateByTimeOfTen(long time, String pattern) {
    	long lt = new Long(time + "000");
    	return toString(new Date(lt), pattern);
    }
    
    /**
     * @description: 获取当前时间格式化后字符串<br>yyyy-MM-dd HH:mm:ss
     * @return
     * @author song
     * @date 2019年11月21日 下午5:47:53
     */
    public static String getNow() {
        // 当前时间重写更新时间
        SimpleDateFormat sdfTimeStamp = new SimpleDateFormat(yyyyMMddHHmmss);
        return sdfTimeStamp.format(new Date());
    }
    
    /**
     * @description: 获取当前时间
     * @param pattern
     * @return
     * @author song
     * @date 2019年11月21日 下午5:56:21
     */
    public static String getNow(String pattern) {
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        return df.format(new Date());
    }
    
    /**
     * @description: 获取当前13位时间戳
     * @return
     * @author song
     * @date 2019年11月21日 下午5:49:25
     */
    public static long getTimeThirteenCurrent() {
        return System.currentTimeMillis();
    }
    
    /**
     * @description: 获取当前10位时间戳
     * @return
     * @author song
     * @date 2019年11月21日 下午5:49:25
     */
    public static long getTimeTenCurrent() {
    	return System.currentTimeMillis()/1000;
    }
    
    /**
	 * @description: 获取指定时间戳	yyyy-MM-dd
	 * @param date
	 * @return 10位的时间戳
	 * @throws Exception
	 * @author song
	 * @date 2019年11月21日 下午5:24:31
	 */
	public static long getTimeTen(java.util.Date date) throws Exception {
    	return date.getTime()/1000;
	}
	
    /**
     * @description: 获取某日的前N天日期
     * @param inputDate
     * @param days
     * @return
     * @author song
     * @throws ParseException 
     * @date 2019年11月21日 下午5:50:09
     */
    public static Date getDateDaysBefore(Date inputDate, int days) throws ParseException {
        Calendar cal = Calendar.getInstance();
        cal.setTime(inputDate);
        int dayOfYear = cal.get(Calendar.DAY_OF_YEAR);
        cal.set(Calendar.DAY_OF_YEAR, dayOfYear - days);
        
        return cal.getTime();
    }
    
    /**
     * @description: 获取某日的后N天日期
     * @param inputDate
     * @param days
     * @return
     * @author song
     * @throws ParseException 
     * @date 2019年11月21日 下午5:53:07
     */
    public static Date getDaysAfter(Date inputDate, int days) throws ParseException {
        Calendar cal = Calendar.getInstance();
        cal.setTime(inputDate);
        
        int dayOfYear = cal.get(Calendar.DAY_OF_YEAR);
        cal.set(Calendar.DAY_OF_YEAR, dayOfYear + days);

        return cal.getTime();
    }
}
