package common.core.common.util;

import java.io.PrintWriter;
import java.io.StringWriter;

public final class ExceptionUtils {
	public static String stackTrace(Throwable e) {
		StringWriter writer = new StringWriter();
		e.printStackTrace(new PrintWriter(writer));
		return writer.toString();
	}

	private ExceptionUtils() {
	}
}
