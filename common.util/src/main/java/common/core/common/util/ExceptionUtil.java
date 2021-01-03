package common.core.common.util;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionUtil {
	public static String stackTrace(Throwable e) {
		StringWriter writer = new StringWriter();
		e.printStackTrace(new PrintWriter(writer));
		return writer.toString();
	}

}
