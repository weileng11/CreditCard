package app.card.util;

import android.annotation.SuppressLint;
import java.text.*;
import java.util.*;

/**
 * @class describe
 * @anthor ${bruce} QQ:275762645
 * @time 2017/11/14 15:26
 * @change
 * @chang time
 * @class describe  时间转换类
 */
public class DateTimeUtil
{
	static SimpleDateFormat format;
	/**
	 日期格式：yyyy-MM-dd HH:mm:ss
	 **/
	public static final String DF_YYYY_MM_DD_HH_MM_SS="yyyy-MM-dd HH:mm:ss";
	/**
	 日期格式：yyyy-MM-dd HH:mm
	 **/
	public static final String DF_YYYY_MM_DD_HH_MM="yyyy-MM-dd HH:mm";
	/**
	 日期格式：yyyy-MM-dd
	 **/
	public static final String DF_YYYY_MM_DD="yyyy-MM-dd";
	/**
	 日期格式：yyyy-MM-dd
	 **/
	public static final String DF_MM_DD="MM-dd";
	/**
	 日期格式：HH:mm:ss
	 **/
	public static final String DF_HH_MM_SS="HH:mm:ss";
	/**
	 日期格式：HH:mm
	 **/
	public static final String DF_HH_MM="HH:mm";
	private final static long minute=60*1000;// 1分钟
	private final static long hour=60*minute;// 1小时
	private final static long day=24*hour;// 1天
	private final static long month=31*day;// 月
	private final static long year=12*month;// 年
	/**
	 日期格式：yyyy-MM-dd HH:mm
	 **/
	public static final String DF_YYYY_MM_DD_HH_MM_XX="yyyy/MM/dd HH:mm";
	/**
	 日期格式：yyyy/MM/dd
	 **/
	public static final String DF_YYYY_MM_DD_PIE="yyyy/MM/dd";
	
	public DateTimeUtil(){
	}
	
	/**
	 将日期格式化成友好的字符串：几分钟前、几小时前、几天前、几月前、几年前、刚刚
	 */
	public static String formatFriendly(Date date){
		if(date==null){
			return null;
		}
		long diff=new Date().getTime()-date.getTime();
		long r=0;
		if(diff>year){
			r=(diff/year);
			return r+"年前";
		}
		if(diff>month){
			r=(diff/month);
			return r+"个月前";
		}
		if(diff>day){
			r=(diff/day);
			return r+"天前";
		}
		if(diff>hour){
			r=(diff/hour);
			return r+"个小时前";
		}
		if(diff>minute){
			r=(diff/minute);
			return r+"分钟前";
		}
		return "刚刚";
	}
	
	/**
	 将日期以yyyy-MM-dd HH:mm:ss格式化
	 */
	public static Date stringToDateOk(String dateString){
		ParsePosition position=new ParsePosition(0);
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date dateValue=simpleDateFormat.parse(dateString, position);
		return dateValue;
	}
	
	/**
	 将日期以yyyy-MM-dd HH:mm:ss格式化
	 @param dateL 日期
	 */
	@SuppressLint("SimpleDateFormat")
	public static String formatDateTimeXXHHSS(long dateL){
		SimpleDateFormat sdf=new SimpleDateFormat(DF_YYYY_MM_DD_HH_MM_XX);
		Date date=new Date(dateL);
		return sdf.format(date);
	}
	
	/**
	 将日期以yyyy/MM/DD
	 @param dateL 日期
	 */
	@SuppressLint("SimpleDateFormat")
	public static String formatDateTimeMMDD(long dateL){
		SimpleDateFormat sdf=new SimpleDateFormat(DF_YYYY_MM_DD_PIE);
		Date date=new Date(dateL);
		return sdf.format(date);
	}
	
	/**
	 将日期以yyyy-MM-dd HH:mm:ss格式化
	 @param dateL 日期
	 */
	@SuppressLint("SimpleDateFormat")
	public static String formatDateTime(long dateL){
		SimpleDateFormat sdf=new SimpleDateFormat(DF_YYYY_MM_DD_HH_MM_SS);
		Date date=new Date(dateL);
		return sdf.format(date);
	}
	
	/**
	 将日期以yyyy-MM-dd
	 @param dateL 年月日
	 */
	@SuppressLint("SimpleDateFormat")
	public static String formatDateTimeDayHHMM(long dateL){
		SimpleDateFormat sdf=new SimpleDateFormat(DF_YYYY_MM_DD_HH_MM);
		Date date=new Date(dateL);
		return sdf.format(date);
	}
	
	/**
	 将日期以yyyy-MM-dd
	 @param dateL 年月日
	 */
	@SuppressLint("SimpleDateFormat")
	public static String formatDateTimeDay(long dateL){
		SimpleDateFormat sdf=new SimpleDateFormat(DF_YYYY_MM_DD);
		Date date=new Date(dateL);
		return sdf.format(date);
	}
	
	/**
	 获得指定日期的前一天
	 */
	public static String getDayBefore(String specifiedDay){
		//SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c=Calendar.getInstance();
		Date date=null;
		try{
			date=new SimpleDateFormat("yyyy-MM-dd").parse(specifiedDay);
		}catch(ParseException e){
			e.printStackTrace();
		}
		c.setTime(date);
		int day=c.get(Calendar.DATE);
		c.set(Calendar.DATE, day-1);
		String dayBefore=new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
		return dayBefore;
	}
	
	/**
	 将日期以yyyy-MM-dd HH:mm:ss格式化
	 @param dateL 日期
	 */
	@SuppressLint("SimpleDateFormat")
	public static String formatLongToStringTime(long dateL){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");
		Date date=new Date(dateL);
		return sdf.format(date);
	}
	
	/**
	 将日期以yyyy-MM-dd HH:mm:ss格式化
	 @param dateL 日期
	 */
	@SuppressLint("SimpleDateFormat")
	public static String formatLongToStringHHMMTime(long dateL){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date date=new Date(dateL);
		return sdf.format(date);
	}
	
	/**
	 将日期以yyyy-MM-dd HH:mm:ss格式化
	 @param dateL 日期
	 */
	@SuppressLint("SimpleDateFormat")
	public static String formatLongToStringTimeHengXian(long dateL){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Date date=new Date(dateL);
		return sdf.format(date);
	}
	
	/**
	 将日期以yyyy-MM-dd HH:mm:ss格式化
	 @param dateL 日期
	 */
	@SuppressLint("SimpleDateFormat")
	public static String formatLongToStringTimHHMMMSS(long dateL){
		SimpleDateFormat sdf=new SimpleDateFormat(DF_HH_MM_SS);
		Date date=new Date(dateL);
		return sdf.format(date);
	}
	
	/**
	 获得指定日期的前一天
	 */
	public static String getSpecifiedDayBefore(String specifiedDay){
		//SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c=Calendar.getInstance();
		Date date=null;
		try{
			date=new SimpleDateFormat("yyyy/MM/dd").parse(specifiedDay);
		}catch(ParseException e){
			e.printStackTrace();
		}
		c.setTime(date);
		int day=c.get(Calendar.DATE);
		c.set(Calendar.DATE, day-1);
		String dayBefore=new SimpleDateFormat("yyyy/MM/dd").format(c.getTime());
		return dayBefore;
	}
	
	/**
	 获得指定日期的前一天
	 */
	public static String getSpecifiedDayBeforeHengXian(String specifiedDay){
		//SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c=Calendar.getInstance();
		Date date=null;
		try{
			date=new SimpleDateFormat("yyyy-MM-dd").parse(specifiedDay);
		}catch(ParseException e){
			e.printStackTrace();
		}
		c.setTime(date);
		int day=c.get(Calendar.DATE);
		c.set(Calendar.DATE, day-1);
		String dayBefore=new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
		return dayBefore;
	}
	
	/**
	 获得指定日期的后一天
	 */
	public static String getSpecifiedDayAfterHengXian(String specifiedDay){
		//SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c=Calendar.getInstance();
		Date date=null;
		try{
			date=new SimpleDateFormat("yyyy-MM-dd").parse(specifiedDay);
		}catch(ParseException e){
			e.printStackTrace();
		}
		c.setTime(date);
		int day=c.get(Calendar.DATE);
		c.set(Calendar.DATE, day+1);
		String dayBefore=new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
		return dayBefore;
	}
	
	/**
	 将日期以MM-dd
	 @param dateL 年月日
	 */
	@SuppressLint("SimpleDateFormat")
	public static String formatDatePianMMDD(long dateL){
		SimpleDateFormat sdf=new SimpleDateFormat("MM/dd");
		Date date=new Date(dateL);
		return sdf.format(date);
	}
	
	/**
	 将日期以MM-dd
	 @param dateL 年月日
	 */
	@SuppressLint("SimpleDateFormat")
	public static String formatDateMMDD(long dateL){
		SimpleDateFormat sdf=new SimpleDateFormat(DF_MM_DD);
		Date date=new Date(dateL);
		return sdf.format(date);
	}
	
	public static String GetDate(long millis){
		Calendar cal=Calendar.getInstance();
		cal.setTimeInMillis(millis);
		return String.format("%1$d年%2$d月%3$d日", cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1,
				cal.get(Calendar.DAY_OF_MONTH));
	}
	
	/**
	 将long类型转化为Date
	 */
	public static Date LongToDare(long str){
		return new Date(str*1000);
	}
	
	public static int getDay(long millis){
		Calendar calendar=Calendar.getInstance();
		calendar.setTimeInMillis(millis);
		//        // 获取年
		//        int year = calendar.get(Calendar.YEAR);
		//// 这里要注意，月份是从0开始。
		//        int month = calendar.get(Calendar.MONTH);
		// 获取天
		int day=calendar.get(Calendar.DAY_OF_MONTH);
		return day;
	}
	
	public static int getMonth(long millis){
		Calendar calendar=Calendar.getInstance();
		calendar.setTimeInMillis(millis);
		//        // 获取年
		//        int year = calendar.get(Calendar.YEAR);
		//// 这里要注意，月份是从0开始。
		int month=calendar.get(Calendar.MONTH);
		// 获取天
		//        int day = calendar.get(Calendar.DAY_OF_MONTH);
		return month;
	}
	
	public static String getMonthDay(long millis){
		Calendar calendar=Calendar.getInstance();
		calendar.setTimeInMillis(millis);
		//        // 获取年
		//        int year = calendar.get(Calendar.YEAR);
		// 这里要注意，月份是从0开始。
		int month=calendar.get(Calendar.MONTH)+1;
		// 获取天
		int day=calendar.get(Calendar.DAY_OF_MONTH)-1;
		//参数值
		String monthStr=String.valueOf(month);
		String daythStr=String.valueOf(day);
		if(String.valueOf(month).length()==1){
			monthStr=getSbMonth(month);
		}
		if(String.valueOf(day).length()==1){
			daythStr=getSbDay(day);
		}
		return monthStr+"/"+daythStr;
	}
	
	/**
	 data转换获取前一天数据
	 */
	public static String getYesterday(Date date, int i){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");
		Calendar cYesterday=Calendar.getInstance();
		//        Date dy = new Date();
		//        try {
		//            dy = sdf.parse(date);
		//        } catch (ParseException e) {
		//            e.printStackTrace();
		//        }
		cYesterday.setTime(date);
		cYesterday.add(Calendar.DATE, -i);
		String yesterday=sdf.format(cYesterday.getTime());
		return yesterday;
	}
	
	/**
	 data转换获取前一天数据
	 */
	public static String getYesterdayHengXian(Date date, int i){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Calendar cYesterday=Calendar.getInstance();
		//        Date dy = new Date();
		//        try {
		//            dy = sdf.parse(date);
		//        } catch (ParseException e) {
		//            e.printStackTrace();
		//        }
		cYesterday.setTime(date);
		cYesterday.add(Calendar.DATE, -i);
		String yesterday=sdf.format(cYesterday.getTime());
		return yesterday;
	}
	
	/**
	 返回日期和月份
	 */
	public static String getSbMonth(int month){
		StringBuffer sb=new StringBuffer();
		if(String.valueOf(month).length()==1){
			sb.append("0");
			sb.append(String.valueOf(month));
		}
		return sb.toString();
	}
	
	/**
	 返回日期和月份
	 */
	public static String getSbDay(int day){
		StringBuffer sb=new StringBuffer();
		if(String.valueOf(day).length()==1){
			sb.append("0");
			sb.append(String.valueOf(day));
		}
		return sb.toString();
	}
	
	/**
	 年月日
	 */
	public static String getYearMonthDay(long millis){
		Calendar calendar=Calendar.getInstance();
		calendar.setTimeInMillis(millis);
		//        // 获取年
		int year=calendar.get(Calendar.YEAR);
		//// 这里要注意，月份是从0开始。
		int month=calendar.get(Calendar.MONTH)+1;
		// 获取天
		int day=calendar.get(Calendar.DAY_OF_MONTH);
		return year+"/"+month+"/"+day;
	}
	
	/**
	 将日期以yyyy-MM-dd HH:mm:ss格式化
	 @param dateL 日期
	 */
	@SuppressLint("SimpleDateFormat")
	public static String formatDateTime(long dateL, String formater){
		SimpleDateFormat sdf=new SimpleDateFormat(formater);
		return sdf.format(new Date(dateL));
	}
	
	/**
	 将日期以yyyy-MM-dd HH:mm:ss格式化
	 @param date 日期
	 */
	@SuppressLint("SimpleDateFormat")
	public static String formatDateTime(Date date, String formater){
		SimpleDateFormat sdf=new SimpleDateFormat(formater);
		return sdf.format(date);
	}
	
	/**
	 将日期字符串转成日期
	 @param strDate 字符串日期
	 @return java.util.date日期类型
	 */
	@SuppressLint("SimpleDateFormat")
	public static Date parseDate(String strDate){
		DateFormat dateFormat=new SimpleDateFormat(DF_YYYY_MM_DD_HH_MM_SS);
		Date returnDate=null;
		try{
			returnDate=dateFormat.parse(strDate);
		}catch(ParseException e){
		}
		return returnDate;
	}
	
	/**
	 获取系统当前日期
	 */
	public static Date gainCurrentDate(){
		return new Date();
	}
	
	/**
	 验证日期是否比当前日期早
	 @param target1 比较时间1
	 @param target2 比较时间2
	 @return true 则代表target1比target2晚或等于target2，否则比target2早
	 */
	public static boolean compareDate(Date target1, Date target2){
		boolean flag=false;
		try{
			String target1DateTime=formatDateTime(target1, DF_YYYY_MM_DD_HH_MM_SS);
			String target2DateTime=formatDateTime(target2, DF_YYYY_MM_DD_HH_MM_SS);
			if(target1DateTime.compareTo(target2DateTime)<=0){
				flag=true;
			}
		}catch(Exception e){
			System.out.println("比较失败，原因："+e.getMessage());
		}
		return flag;
	}
	
	/**
	 对日期进行增加操作
	 @param target 需要进行运算的日期
	 @param hour 小时
	 */
	public static Date addDateTime(Date target, double hour){
		if(null==target || hour<0){
			return target;
		}
		return new Date(target.getTime()+(long)(hour*60*60*1000));
	}
	
	/**
	 对日期进行相减操作
	 @param target 需要进行运算的日期
	 @param hour 小时
	 */
	public static Date subDateTime(Date target, double hour){
		if(null==target || hour<0){
			return target;
		}
		return new Date(target.getTime()-(long)(hour*60*60*1000));
	}
	
	/**
	 获取系统时间的方法:月/日 时:分:秒
	 */
	public static String getFormateDate(){
		Calendar calendar=Calendar.getInstance();
		int month=(calendar.get(Calendar.MONTH)+1);
		int day=calendar.get(Calendar.DAY_OF_MONTH);
		int hour=calendar.get(Calendar.HOUR_OF_DAY);
		int minute=calendar.get(Calendar.MINUTE);
		int second=calendar.get(Calendar.SECOND);
		String systemTime=(month<10? "0"+month: month)+"/"+(day<10? "0"+day: day)+"  "+
		                  (hour<10? "0"+hour: hour)+":"+(minute<10? "0"+minute: minute)+":"+
		                  (second<10? "0"+second: second);
		return systemTime;
	}
	
	/**
	 获取系统时间的方法:时:分:秒
	 */
	public static String getHourAndMinute(){
		Calendar calendar=Calendar.getInstance();
		int hour=calendar.get(Calendar.HOUR_OF_DAY);
		int minute=calendar.get(Calendar.MINUTE);
		return (hour<10? "0"+hour: hour)+":"+(minute<10? "0"+minute: minute);
	}
	
	/**
	 获取系统时间的方法:时
	 */
	public static String getHour(){
		Calendar calendar=Calendar.getInstance();
		int hour=calendar.get(Calendar.HOUR_OF_DAY);
		return ((hour<10? "0"+hour: hour)+"");
	}
	
	/**
	 将2017-07-10 00:00:00 换2017-07-10
	 */
	public static String strFormatStr(String strDate){
		if(strDate.equals("")){
			return "";
		}
		return dateToStr(strToDate(strDate));
	}
	
	/**
	 2015-01-07 15:05:34
	 */
	@SuppressLint("SimpleDateFormat")
	public static Date strToDateHHMMSS(String strDate){
		SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ParsePosition pos=new ParsePosition(0);
		Date strtodate=formatter.parse(strDate, pos);
		return strtodate;
	}
	
	/**
	 2015-01-07
	 */
	@SuppressLint("SimpleDateFormat")
	public static Date strToDate(String strDate){
		SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");
		ParsePosition pos=new ParsePosition(0);
		Date strtodate=formatter.parse(strDate, pos);
		return strtodate;
	}
	
	/**
	 2015.01.07
	 */
	@SuppressLint("SimpleDateFormat")
	public static Date strToDateDorp(String strDate){
		SimpleDateFormat formatter=new SimpleDateFormat("yyyy.MM.dd");
		ParsePosition pos=new ParsePosition(0);
		Date strtodate=formatter.parse(strDate, pos);
		return strtodate;
	}
	
	@SuppressLint("SimpleDateFormat")
	public static String dateToStr(Date dateDate){
		SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");
		String dateString=formatter.format(dateDate);
		return dateString;
	}
	
	/**
	 传入一个String转化为long
	 */
	@SuppressLint("SimpleDateFormat")
	public static Long stringParserLong(String param){
		format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try{
			return format.parse(param).getTime();
		}catch(ParseException e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 传入一个String转化为long
	 */
	@SuppressLint("SimpleDateFormat")
	public static Long stringParserLongMMDD(String param) throws ParseException{
		format=new SimpleDateFormat("MM-dd");
		return format.parse(param).getTime();
	}
	
	/**
	 传入一个String转化为long
	 */
	@SuppressLint("SimpleDateFormat")
	public static Long stringParserLongHHMM(String param) throws ParseException{
		format=new SimpleDateFormat(DF_HH_MM);
		return format.parse(param).getTime();
	}
	
	/**
	 当前时间转换为long
	 */
	@SuppressLint("SimpleDateFormat")
	public static Long currentDateParserLong() throws ParseException{
		format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.parse(format.format(Calendar.getInstance().getTime())).getTime();
	}
	
	/**
	 当前时间 如: 2013-04-22 10:37:00
	 */
	@SuppressLint("SimpleDateFormat")
	public static String getCurrentDate(){
		format=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return format.format(Calendar.getInstance().getTime());
	}
	
	/**
	 当前时间 如: 10:37
	 */
	@SuppressLint("SimpleDateFormat")
	public static String getCurrentDateHHMM(){
		format=new SimpleDateFormat("HH:mm");
		return format.format(Calendar.getInstance().getTime());
	}
	
	/**
	 当前时间 如: 10:37
	 */
	@SuppressLint("SimpleDateFormat")
	public static String getCurrentDateHHMMSS(){
		format=new SimpleDateFormat("HH:mm:ss");
		return format.format(Calendar.getInstance().getTime());
	}
	
	/**
	 当前时间 如: 20130422
	 */
	@SuppressLint("SimpleDateFormat")
	public static String getCurrentDateString(){
		format=new SimpleDateFormat("yyyyMMddHHmm");
		return format.format(Calendar.getInstance().getTime());
	}
	
	/**
	 当前时间 如: 2013-04-22
	 */
	@SuppressLint("SimpleDateFormat")
	public static String getCurrentTime(){
		format=new SimpleDateFormat("yyyy-MM-dd");
		return format.format(Calendar.getInstance().getTime());
	}
	
	@SuppressLint("SimpleDateFormat")
	public static String getSWAHDate(){
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(Calendar.getInstance().getTime());
	}
	
	@SuppressLint("SimpleDateFormat")
	public static Long stringToLongAddFile(String param){
		try{
			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return format.parse(param).getTime();
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public static Date stringToDate(String dateString){
		ParsePosition position=new ParsePosition(0);
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date dateValue=simpleDateFormat.parse(dateString, position);
		return dateValue;
	}
	
	@SuppressLint("SimpleDateFormat")
	public static Long stringToLongD(String param) throws ParseException{
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		return format.parse(param.substring(0, param.length()-4)).getTime();
	}
	
	@SuppressLint("SimpleDateFormat")
	public static Long stringToLong(String param) throws ParseException{
		SimpleDateFormat format=new SimpleDateFormat("yyyyMMddHHmm");
		return format.parse(param).getTime();
	}
	
	/**
	 获取两个日期之间的间隔天数
	 */
	@SuppressLint("SimpleDateFormat")
	public static int getGapCount(Date startDate, Date endDate){
		Calendar fromCalendar=Calendar.getInstance();
		fromCalendar.setTime(startDate);
		fromCalendar.set(Calendar.HOUR_OF_DAY, 0);
		fromCalendar.set(Calendar.MINUTE, 0);
		fromCalendar.set(Calendar.SECOND, 0);
		fromCalendar.set(Calendar.MILLISECOND, 0);
		Calendar toCalendar=Calendar.getInstance();
		toCalendar.setTime(endDate);
		toCalendar.set(Calendar.HOUR_OF_DAY, 0);
		toCalendar.set(Calendar.MINUTE, 0);
		toCalendar.set(Calendar.SECOND, 0);
		toCalendar.set(Calendar.MILLISECOND, 0);
		return (int)((toCalendar.getTime().getTime()-fromCalendar.getTime().getTime())/
		             (1000*60*60*24));
	}
	
	/**
	 日期转换成Java字符串
	 @return str
	 */
	@SuppressLint("SimpleDateFormat")
	public static String DateToStr(Date date){
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str=format.format(date);
		return str;
	}
	
	/**
	 日期转换成Java字符串
	 @return str
	 */
	@SuppressLint("SimpleDateFormat")
	public static String DateToYYYYStr(Date date){
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		String str=format.format(date);
		return str;
	}
	
	/**
	 字符串转换成日期
	 @return date
	 */
	@SuppressLint("SimpleDateFormat")
	public static Date StrToDate(String str){
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		Date date=null;
		try{
			date=format.parse(str);
		}catch(ParseException e){
			e.printStackTrace();
		}
		return date;
	}
	
	/**
	 字符串转换成日期
	 @return date
	 */
	@SuppressLint("SimpleDateFormat")
	public static Date strToDateYMD(String str){
		SimpleDateFormat format=new SimpleDateFormat("yyyy/MM/dd");
		Date date=null;
		try{
			date=format.parse(str);
		}catch(ParseException e){
			e.printStackTrace();
		}
		return date;
	}
	
	/**
	 字符串转换成日期
	 @return date
	 */
	@SuppressLint("SimpleDateFormat")
	public static Date StrToDateDrop(String str){
		SimpleDateFormat format=new SimpleDateFormat("yyyy.MM.dd");
		Date date=null;
		try{
			date=format.parse(str);
		}catch(ParseException e){
			e.printStackTrace();
		}
		return date;
	}
	
	/**
	 * @param time
	 * @return
	 */
	@SuppressLint("SimpleDateFormat")
	public static long getLongTime(String time){
		long ct=0;
		try{
			format=new SimpleDateFormat("HH:mm:ss");
			ct=format.parse(time).getTime();
		}catch(Exception e){
			e.printStackTrace();
		}
		return ct;
	}
	
	/**
	 判断两日期是否同一天
	 */
	@SuppressLint("SimpleDateFormat")
	public static boolean isSameDay(String str1, String str2){
		Date day1=null, day2=null;
		day1=DateTimeUtil.strToDate(str1);
		day2=DateTimeUtil.strToDate(str2);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		String ds1=sdf.format(day1);
		String ds2=sdf.format(day2);
		if(ds1.equals(ds2)){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 获取两个日期的时间差
	 */
	@SuppressLint("SimpleDateFormat")
	public static int getTimeInterval(String date){
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		int interval=0;
		try{
			Date currentTime=new Date();// 获取现在的时间
			Date beginTime=dateFormat.parse(date);
			//            interval = (int) ((beginTime.getTime() - currentTime.getTime()) / (1000));// 时间差
			interval=(int)((currentTime.getTime()-beginTime.getTime())/(1000));// 时间差
			// 单位秒
		}catch(ParseException e){
			e.printStackTrace();
		}
		return interval;
	}
	
	/**
	 @param datestr 日期字符串
	 @param day 相对天数，为正数表示之后，为负数表示之前
	 @return 指定日期字符串n天之前或者之后的日期
	 */
	public static java.sql.Date getBeforeAfterDate(String datestr, int day){
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		java.sql.Date olddate=null;
		try{
			df.setLenient(false);
			olddate=new java.sql.Date(df.parse(datestr).getTime());
		}catch(ParseException e){
			throw new RuntimeException("日期转换错误");
		}
		Calendar cal=new GregorianCalendar();
		cal.setTime(olddate);
		int Year=cal.get(Calendar.YEAR);
		int Month=cal.get(Calendar.MONTH);
		int Day=cal.get(Calendar.DAY_OF_MONTH);
		int NewDay=Day+day;
		cal.set(Calendar.YEAR, Year);
		cal.set(Calendar.MONTH, Month);
		cal.set(Calendar.DAY_OF_MONTH, NewDay);
		return new java.sql.Date(cal.getTimeInMillis());
	}
	
	/**
	 指定日期加上天数后的日期
	 @param num 为增加的天数
	 @param newDate 创建时间
	 */
	public static String plusDay(int num, String newDate){
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date currdate=null;
		String enddate="";
		try{
			currdate=format.parse(newDate);
			System.out.println("现在的日期是："+currdate);
			Calendar ca=Calendar.getInstance();
			ca.add(Calendar.DATE, num);// num为增加的天数，可以改变的
			currdate=ca.getTime();
			enddate=format.format(currdate);
			System.out.println("增加天数以后的日期："+enddate);
		}catch(ParseException e){
			e.printStackTrace();
		}
		return enddate;
	}
	
	/**
	 获取两个日期的时间差 yyyy.MM.dd HH.mm.ss
	 */
	@SuppressLint("SimpleDateFormat")
	public static int getInterval(String bDate, String eDate){
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy.MM.dd");
		int interval=0;
		try{
			Date currentTime=dateFormat.parse(eDate);// 获取现在的时间
			Date beginTime=dateFormat.parse(bDate);
			interval=(int)((beginTime.getTime()-currentTime.getTime()));// 时间差
			// 单位秒
		}catch(ParseException e){
			e.printStackTrace();
		}
		return interval;
	}
	
	/**
	 两个时间之差 求出一个long Time
	 */
	@SuppressLint("SimpleDateFormat")
	public static long getTime(String date){
		DateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long diff=0;
		try{
			Date currentTime=new Date();// 获取现在的时间
			Date getdate=df.parse(date);
			diff=getdate.getTime()-currentTime.getTime();
		}catch(Exception e){
		}
		return diff;
	}
	
	/**
	 日期转换成Java字符串
	 */
	@SuppressLint("SimpleDateFormat")
	public static int compareDate(String startTime, String endTime){
		DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		try{
			Date startDt=df.parse(startTime);
			Date endDt=df.parse(endTime);
			if(startDt.getTime()>endDt.getTime()){  //开始时间大于结束时间
				return 1;
			}else if(startDt.getTime()<endDt.getTime()){ //开始时间小于结束时间
				return -1;
			}else{ //相等
				return 0;
			}
		}catch(Exception exception){
			exception.printStackTrace();
		}
		return 0;
	}
	
	/**
	 传入时间 算出星期几
	 @param str 2014年1月3日
	 @param days 1:2014年1月4日 类推
	 */
	@SuppressLint("SimpleDateFormat")
	public static String formatDate(String str, int days){
		String dateStr="";
		try{
			DateFormat df=DateFormat.getDateInstance(DateFormat.LONG);
			Date date=df.parse(str);
			SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
			Calendar c=Calendar.getInstance();
			Date d=dateFormat.parse(dateFormat.format(date));
			c.setTime(d);
			c.add(Calendar.DAY_OF_MONTH, days);
			switch(c.get(Calendar.DAY_OF_WEEK)-1){
			case 0:
				dateStr="周日";
				break;
			case 1:
				dateStr="周一";
				break;
			case 2:
				dateStr="周二";
				break;
			case 3:
				dateStr="周三";
				break;
			case 4:
				dateStr="周四";
				break;
			case 5:
				dateStr="周五";
				break;
			case 6:
				dateStr="周六";
				break;
			default:
				break;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return dateStr;
	}
	
	/**
	 日期转星期
	 */
	public static String dateToWeek(String datetime){
		SimpleDateFormat f=new SimpleDateFormat("yyyy-MM-dd");
		String[] weekDays={"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
		Calendar cal=Calendar.getInstance(); // 获得一个日历
		Date datet=null;
		try{
			datet=f.parse(datetime);
			cal.setTime(datet);
		}catch(ParseException e){
			e.printStackTrace();
		}
		int w=cal.get(Calendar.DAY_OF_WEEK)-1; // 指示一个星期中的某天。
		if(w<0) w=0;
		return weekDays[w];
	}
	
	/**
	 两个日期相隔的天数
	 */
	public static int getDifferDay(String startTime, String endTime){
		Date earlydate=new Date();
		Date latedate=new Date();
		DateFormat df=DateFormat.getDateInstance();
		try{
			earlydate=df.parse(startTime);
			latedate=df.parse(endTime);
		}catch(ParseException e){
			e.printStackTrace();
		}
		int days=daysBetween(earlydate, latedate);
		return days;
	}
	
	/**
	 加上多少个小时  如2016-07-02 到2016-07-03
	 */
	public static String addDateMinut(String day, int x)//返回的是字符串型的时间，输入的
	//是String day, int x
	{
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 24小时制
		//引号里面个格式也可以是 HH:mm:ss或者HH:mm等等，很随意的，不过在主函数调用时，要和输入的变
		//量day格式一致
		Date date=null;
		try{
			date=format.parse(day);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		if(date==null) return "";
		System.out.println("front:"+format.format(date)); //显示输入的日期
		Calendar cal=Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MINUTE, x);// 24小时制
		date=cal.getTime();
		System.out.println("after:"+format.format(date));  //显示更新后的日期
		cal=null;
		return format.format(date);
	}
	
	/**
	 加上多少个小时  如2016-07-02 到2016-07-03
	 */
	public static String addDateDay(String day, int x)//返回的是字符串型的时间，输入的
	//是String day, int x
	{
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");// 24小时制
		//引号里面个格式也可以是 HH:mm:ss或者HH:mm等等，很随意的，不过在主函数调用时，要和输入的变
		//量day格式一致
		Date date=null;
		try{
			date=format.parse(day);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		if(date==null) return "";
		System.out.println("front:"+format.format(date)); //显示输入的日期
		Calendar cal=Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MINUTE, x);// 24小时制
		date=cal.getTime();
		System.out.println("after:"+format.format(date));  //显示更新后的日期
		cal=null;
		return format.format(date);
	}
	
	/**
	 计算两个日期相差的时间
	 @param early 2017-11-21
	 @param late 2017-11-24
	 */
	public static final int daysBetween(Date early, Date late){
		Calendar calst=Calendar.getInstance();
		Calendar caled=Calendar.getInstance();
		calst.setTime(early);
		caled.setTime(late);
		//设置时间为0时
		calst.set(Calendar.HOUR_OF_DAY, 0);
		calst.set(Calendar.MINUTE, 0);
		calst.set(Calendar.SECOND, 0);
		caled.set(Calendar.HOUR_OF_DAY, 0);
		caled.set(Calendar.MINUTE, 0);
		caled.set(Calendar.SECOND, 0);
		//得到两个日期相差的天数
		int days=((int)(caled.getTime().getTime()/1000)-(int)(calst.getTime().getTime()/1000))/3600/
		         24;
		return days;
	}
	
	/**
	 字符串的日期格式的计算
	 */
	public static int daysBetweenHHMM(String smdate, String bdate){
		long between_days=0;
		try{
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Calendar cal=Calendar.getInstance();
			cal.setTime(sdf.parse(smdate));
			long time1=cal.getTimeInMillis();
			cal.setTime(sdf.parse(bdate));
			long time2=cal.getTimeInMillis();
			between_days=(time2-time1)/(1000*3600*24);
		}catch(ParseException e){
			e.printStackTrace();
		}
		return Integer.parseInt(String.valueOf(between_days));
	}
	
	/**
	 字符串的日期格式的计算
	 */
	public static int daysBetween(String smdate, String bdate){
		long between_days=0;
		try{
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			Calendar cal=Calendar.getInstance();
			cal.setTime(sdf.parse(smdate));
			long time1=cal.getTimeInMillis();
			cal.setTime(sdf.parse(bdate));
			long time2=cal.getTimeInMillis();
			between_days=(time2-time1)/(1000*3600*24);
		}catch(ParseException e){
			e.printStackTrace();
		}
		return Integer.parseInt(String.valueOf(between_days));
	}
	
	/**
	 字符串的日期格式的计算
	 */
	public static int daysBetweenTask(String smdate, String bdate){
		long between_days=0;
		try{
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");
			Calendar cal=Calendar.getInstance();
			cal.setTime(sdf.parse(smdate));
			long time1=cal.getTimeInMillis();
			cal.setTime(sdf.parse(bdate));
			long time2=cal.getTimeInMillis();
			between_days=(time2-time1)/(1000*3600*24);
		}catch(ParseException e){
			e.printStackTrace();
		}
		return Integer.parseInt(String.valueOf(between_days));
	}
	
	/***
	 * 返回具体的天 小时  和秒 分钟
	 * @param startTime
	 * @param endTime
	 * @param format
	 * @return
	 */
	public static long dateDiff(String startTime, String endTime, String format){
		// 按照传入的格式生成一个simpledateformate对象
		SimpleDateFormat sd=new SimpleDateFormat(format);
		long nd=1000*23*60*60;// 一天的毫秒数
		long nh=1000*60*60;// 一小时的毫秒数
		long nm=1000*60;// 一分钟的毫秒数
		long ns=1000;// 一秒钟的毫秒数
		long diff;
		long day=0;
		try{
			// 获得两个时间的毫秒时间差异
			diff=sd.parse(endTime).getTime()-sd.parse(startTime).getTime();
			day=diff/nd;// 计算差多少天
			long hour=diff%nd/nh;// 计算差多少小时
			long min=diff%nd%nh/nm;// 计算差多少分钟
			long sec=diff%nd%nh%nm/ns;// 计算差多少秒
			// 输出结果
			System.out.println("时间相差："+day+"天"+hour+"小时"+min+"分钟"+sec+"秒。");
			if(day>=1){
				return day;
			}else{
				if(day==0){
					if(hour>0){
						return hour;
					}else if(min>0){
						return min;
					}else if(sec>0){
						return sec;
					}else{
						return hour+min+sec;
					}
				}else{
					return 0;
				}
			}
		}catch(ParseException e){
			e.printStackTrace();
		}
		return 0;
	}
	
	/***
	 * 返回具体的 天数
	 * @param startTime
	 * @param endTime
	 * @param format
	 * @return
	 */
	public static long dateDiffDay(String startTime, String endTime, String format){
		// 按照传入的格式生成一个simpledateformate对象
		SimpleDateFormat sd=new SimpleDateFormat(format);
		long nd=1000*24*60*60;// 一天的毫秒数
		long nh=1000*60*60;// 一小时的毫秒数
		long nm=1000*60;// 一分钟的毫秒数
		long ns=1000;// 一秒钟的毫秒数
		long diff;
		long day=0;
		try{
			// 获得两个时间的毫秒时间差异
			diff=sd.parse(endTime).getTime()-sd.parse(startTime).getTime();
			day=diff/nd;// 计算差多少天
			long hour=diff%nd/nh;// 计算差多少小时
			long min=diff%nd%nh/nm;// 计算差多少分钟
			long sec=diff%nd%nh%nm/ns;// 计算差多少秒
			// 输出结果
			System.out.println("时间相差："+day+"天"+hour+"小时"+min+"分钟"+sec+"秒。");
			if(day>=1){
				return day;
			}else{
				if(day==0){
					return day;
				}else{
					return -1;
				}
			}
		}catch(ParseException e){
			e.printStackTrace();
		}
		return -1;
	}
	
	/***
	 * 返回具体的 分钟
	 * @param startTime
	 * @param endTime
	 * @param format
	 * @return
	 */
	public static long dateDiffMin(String startTime, String endTime, String format){
		// 按照传入的格式生成一个simpledateformate对象
		SimpleDateFormat sd=new SimpleDateFormat(format);
		long nd=1000*24*60*60;// 一天的毫秒数
		long nh=1000*60*60;// 一小时的毫秒数
		long nm=1000*60;// 一分钟的毫秒数
		long ns=1000;// 一秒钟的毫秒数
		long diff;
		long day=0;
		try{
			// 获得两个时间的毫秒时间差异
			diff=sd.parse(endTime).getTime()-sd.parse(startTime).getTime();
			day=diff/nd;// 计算差多少天
			long hour=diff%nd/nh;// 计算差多少小时
			long min=diff%nd%nh/nm;// 计算差多少分钟
			long sec=diff%nd%nh%nm/ns;// 计算差多少秒
			// 输出结果
			System.out.println("时间相差："+day+"天"+hour+"小时"+min+"分钟"+sec+"秒。");
			return min;
		}catch(ParseException e){
			e.printStackTrace();
		}
		return 0;
	}
	
	/***
	 * 返回具体的 小时数目
	 * @param startTime
	 * @param endTime
	 * @param format
	 * @return
	 */
	public static long dateDiffHour(String startTime, String endTime, String format){
		// 按照传入的格式生成一个simpledateformate对象
		SimpleDateFormat sd=new SimpleDateFormat(format);
		long nd=1000*24*60*60;// 一天的毫秒数
		long nh=1000*60*60;// 一小时的毫秒数
		long nm=1000*60;// 一分钟的毫秒数
		long ns=1000;// 一秒钟的毫秒数
		long diff;
		long day=0;
		try{
			// 获得两个时间的毫秒时间差异
			diff=sd.parse(endTime).getTime()-sd.parse(startTime).getTime();
			day=diff/nd;// 计算差多少天
			long hour=diff%nd/nh;// 计算差多少小时
			long min=diff%nd%nh/nm;// 计算差多少分钟
			long sec=diff%nd%nh%nm/ns;// 计算差多少秒
			// 输出结果
			System.out.println("时间相差："+day+"天"+hour+"小时"+min+"分钟"+sec+"秒。");
			if(hour>0){
				return hour;
			}
		}catch(ParseException e){
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 Long 类型转化为 String 类型
	 */
	public static Date longToDate(Long time){
		return new Date(time);
	}
	
	/**
	 获取当年的最后一天
	 */
	public static Date getCurrYearLast(){
		Calendar currCal=Calendar.getInstance();
		int currentYear=currCal.get(Calendar.YEAR);
		return getYearLast(currentYear);
	}
	
	/**
	 获取某年最后一天日期
	 @param year 年份
	 @return Date  compareDate
	 */
	public static Date getYearLast(int year){
		Calendar calendar=Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		calendar.roll(Calendar.DAY_OF_YEAR, -1);
		Date currYearLast=calendar.getTime();
		return currYearLast;
	}
	
	/**
	 验证日期是否比当前日期早
	 @param target1 比较时间1
	 @param target2 比较时间2
	 @return true 则代表target1比target2晚或等于target2，否则比target2早
	 */
	public static boolean compareDateHHMM(Date target1, Date target2){
		boolean flag=false;
		try{
			String target1DateTime=formatDateTime(target1, DF_YYYY_MM_DD_HH_MM);
			String target2DateTime=formatDateTime(target2, DF_YYYY_MM_DD_HH_MM);
			System.out.println("比较失败"+target1DateTime+"target2DateTime--"+target2DateTime);
			if(target1DateTime.compareTo(target2DateTime)<=0){
				flag=true;
			}
		}catch(Exception e){
			System.out.println("比较失败，原因："+e.getMessage());
		}
		return flag;
	}
	
	//两个日期之间相差天数
	public static int getDiscrepantDays(Date dateStart, Date dateEnd){
		return (int)((dateEnd.getTime()-dateStart.getTime())/1000/60/60/24);
	}
	
	private static SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	
	/**
	 某一年某个月的每一天
	 */
	public static List<String> getMonthFullDay(int year, int month, int day){
		List<String> fullDayList=new ArrayList<String>();
		if(day<=0) day=1;
		Calendar cal=Calendar.getInstance();// 获得当前日期对象
		cal.clear();// 清除信息
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month-1);// 1月从0开始
		cal.set(Calendar.DAY_OF_MONTH, day);// 设置为1号,当前日期既为本月第一天
		int count=cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		for(int j=0; j<=(count-1); ){
			if(sdf.format(cal.getTime()).equals(getLastDay(year, month))) break;
			cal.add(Calendar.DAY_OF_MONTH, j==0? +0: +1);
			j++;
			fullDayList.add(sdf.format(cal.getTime()));
		}
		return fullDayList;
	}
	
	public static String getLastDay(int year, int month){
		Calendar cal=Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DAY_OF_MONTH, 0);
		return sdf.format(cal.getTime());
	}
	
	//获取某一段时间内的每一天
	public static List<String> getDiffDay(String startTime, String endTime){
		List<String> list=new ArrayList<>();
		Date date0;
		Date date1;
		try{
			date0=new SimpleDateFormat("yyyy-MM-dd").parse(startTime);
			date1=new SimpleDateFormat("yyyy-MM-dd").parse(endTime);
			Calendar cal=Calendar.getInstance();
			cal.setTime(date0);
			while(cal.getTime().compareTo(date1)<=0){
				cal.add(Calendar.DAY_OF_MONTH, 1);
				list.add(sdf.format(cal.getTime()));
			}
		}catch(ParseException e){
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 月份加一
	 */
	public static String monthAddFrist(String date){
		DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		try{
			Calendar ct=Calendar.getInstance();
			ct.setTime(df.parse(date));
			ct.add(Calendar.MONTH, +1);
			return df.format(ct.getTime());
		}catch(ParseException e){
			e.printStackTrace();
		}
		return "";
	}
}
