package fd.ng.core.utils;

import java.util.Collection;
import java.util.Map;

public class Validator {
	public Validator() {
		throw new AssertionError("No Validator instances for you!");
	}

	public static CharSequence notBlank(final CharSequence cs, String message) {
		if (StringUtil.isBlank(cs)) {
			throw new IllegalArgumentException(message);
		}
		return cs;
	}

	public static CharSequence notBlank(final CharSequence cs) {
		notBlank(cs, "[validate failed] - the argument is required; it must not null,not a space");
		return cs;
	}

	public static <T> T notNull(T object) {
		return notNull(object, "[validate failed] - the argument is required; it must not null!");
	}

	public static <T> T notNull(T object, String message) {
		if (object == null)
			throw new IllegalArgumentException(message);
		return object;
	}

	public static <E> Collection<E> notEmpty(Collection<E> c) {
		return notEmpty(c, "[validate failed] - the argument must not null or empty!");
	}

	public static <E> Collection<E> notEmpty(Collection<E> c, String message) {
		if (c == null || c.isEmpty())
			throw new IllegalArgumentException(message);
		return c;
	}

	public static <K, V> Map<K, V> notEmpty(Map<K, V> object) {
		return notEmpty(object, "[validate failed] - the argument must not null or empty!");
	}

	public static <K, V> Map<K, V> notEmpty(Map<K, V> object, String message) {
		if (object == null || object.size() < 1)
			throw new IllegalArgumentException(message);
		return object;
	}

	public static String notEmpty(String object) {
		return notEmpty(object, "[validate failed] - the argument is required; it must not null or empty!");
	}

	public static String notEmpty(String object, String message) {
		if (object == null || object.length() == 0)
			throw new IllegalArgumentException(message);
		return object;
	}

	public static boolean isTrue(boolean expression) {
		return isTrue(expression, "[validate failed] - the argument must be true!");
	}

	public static boolean isTrue(boolean expression, String message) {
		if (!expression)
			throw new IllegalArgumentException(message);
		return expression;
	}

	public static boolean isFalse(boolean expression) {
		return isFalse(expression, "[validate failed] - the argument must be false!");
	}

	public static boolean isFalse(boolean expression, String message) {
		if (expression)
			throw new IllegalArgumentException(message);
		return expression;
	}

	public static String isIpAddr(final String ipAddr) {
		return isIpAddr(ipAddr, "[validate failed] - the argument must be ip!");
	}

	public static String isIpAddr(final String ipAddr, String message) {
		final String reg = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
				+ "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
				+ "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
				+ "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
		if (!ipAddr.matches(reg))
			throw new IllegalArgumentException(message);
		return ipAddr;
	}

	public static void isPort(final int port) {
		isPort(port, "[validate failed] - the argument must be port!");
	}

	public static void isPort(final int port, String message) {
		if (port < 0 || port > 65535)
			throw new IllegalArgumentException(message);
	}
}
