package fd.ng.core.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Instant			代替 Date，且性能更高（比LocalDateTime高）
 * LocalDateTime	代替 Calendar
 * Instant是计算机时间，LocalDateTime是人类时间。
 * 值一样，但形式不一样，Instant只有面向计算机的数字，而LocalDateTime可以按照人类的要求格式化成各种展现格式。
 */
public class DateUtil {
	public static final DateTimeFormatter TIMESTAMP_DEFAULT = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
	public static final DateTimeFormatter DATETIME_DEFAULT = DateTimeFormatter.ofPattern("yyyyMMdd HHmmss");
	public static final DateTimeFormatter DATETIME_ZHCN = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH点mm分ss秒");
	public static final DateTimeFormatter DATE_DEFAULT = DateTimeFormatter.ofPattern("yyyyMMdd");
	public static final DateTimeFormatter TIME_DEFAULT = DateTimeFormatter.ofPattern("HHmmss");
	public static final DateTimeFormatter MONTHDAY_DEFAULT = DateTimeFormatter.ofPattern("MMdd");

	public DateUtil() { throw new AssertionError("No DateUtil instances for you!"); }

	/**
	 * 获取当前系统日期，格式为： yyyyMMdd
	 * @return
	 */
	public static String getSysDate() {
		return getSysDate(DATE_DEFAULT);
	}

	/**
	 * 获取当前系统日期。
	 * @param dtf 应该被定义成 static final，以提高性能
	 * @return
	 */
	public static String getSysDate(DateTimeFormatter dtf) {
		LocalDate date = LocalDate.now();
		return date.format(dtf);
	}

	/**
	 * 获取当前系统时间，格式为： HHmmss
	 * @return
	 */
	public static String getSysTime() {
		return getSysTime(TIME_DEFAULT);
	}

	/**
	 * 获取当前系统时间。
	 * @param dtf 应该被定义成 static final，以提高性能
	 * @return
	 */
	public static String getSysTime(DateTimeFormatter dtf) {
		LocalTime time = LocalTime.now();
		return time.format(dtf);
	}

	/**
	 * 获取当前系统日期和时间，格式为： yyyyMMdd HHmmss
	 * @return
	 */
	public static String getDateTime() {
		return getDateTime(DATETIME_DEFAULT);
	}

	/**
	 * 获取当前系统日期和时间。
	 * @param dtf 应该被定义成 static final，以提高性能
	 * @return
	 */
	public static String getDateTime(DateTimeFormatter dtf) {
		LocalDateTime dateTime = LocalDateTime.now();
		return dateTime.format(dtf);
	}

	/**
	 * 获取当前系统时间戳（带3位毫秒值），格式为： yyyyMMddHHmmssSSS
	 * @return
	 */
	public static String getTimestamp() {
		return getTimestamp(TIMESTAMP_DEFAULT);
	}

	/**
	 * 获取当前系统时间戳（带3位毫秒值）。
	 * @param dtf 应该被定义成 static final，以提高性能
	 * @return
	 */
	public static String getTimestamp(DateTimeFormatter dtf) {
		LocalDateTime dateTime = LocalDateTime.now();
		return dateTime.format(dtf);
	}
	/**
	 * 8位字符的字符串转换为Date对象
	 * @author Tiger.Wang
	 * @date 2019/8/30
	 * @param dateStr	8位字符的字符串
	 * @return java.time.LocalDate	日期对象
	 */
	public static LocalDate parseStr2DateWith8Char(String dateStr) {

		return LocalDate.parse(dateStr, DATE_DEFAULT);
	}

	/**
	 * 6位字符的字符串转换为Time对象
	 * @author Tiger.Wang
	 * @date 2019/8/30
	 * @param timeStr	6位字符的字符串
	 * @return java.time.LocalTime	Time对象
	 */
	public static LocalTime parseStr2TimeWith6Char(String timeStr) {

		return LocalTime.parse(timeStr, TIME_DEFAULT);
	}
	/**
	 * 计算两个日期相差的天数
	 * @param startDate 格式：yyyyMMdd
	 * @param endDate 格式：yyyyMMdd
	 * @return 返回两日期相差的天数
	 */
	public static int dateMargin(String startDate, String endDate) {

		LocalDate d1 = parseStr2DateWith8Char(startDate);
		LocalDate d2 = parseStr2DateWith8Char(endDate);
		return (int) (d2.toEpochDay() - d1.toEpochDay());
	}

	/**
	 *  根据日期格式化对象验证传入的日期型字符串是否符合规则
	 *  该对象可调用
	 * @param dateStr 日期型字符串
	 * @param dateTimeFormatter jdk1.8 引入的日期格式化对象
	 * @return 传入的日期型字符串是否符合规则
	 * @author Mick Yuan
	 */
	public static boolean validDateStr(String dateStr, DateTimeFormatter dateTimeFormatter) {
		try {
			LocalDate.parse(dateStr,dateTimeFormatter);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * 根据日期格式化字符串验证传入的日期型字符串是否符合规则
	 *
	 * @param dateStr 日期型字符串
	 * @param pattern 日期格式化字符串
	 * @return 传入的日期型字符串是否符合规则
	 * @author Mick Yuan
	 */
	public static boolean validDateStr(String dateStr, String pattern) {
		if (StringUtil.isBlank(dateStr)) {
			return false;
		}
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
		return validDateStr(dateStr, dateTimeFormatter);
	}

	/**
	 * 验证传入的日期型字符串 是否是 yyyyMMdd 格式的
	 * @param dateStr 日期型字符串
	 * @return 传入的日期型字符串是否是 yyyyMMdd 格式的
	 * @author Mick Yuan
	 */
	public static boolean validDateStr(String dateStr) {
		return validDateStr(dateStr, DATE_DEFAULT);
	}
}
