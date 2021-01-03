package common.core.app.log;

import java.io.PrintWriter;
import java.io.StringWriter;

public final class ExceptionTraceUtil {
	public static void stackTrace(Throwable e) {
		StringWriter writer = new StringWriter();
		e.printStackTrace(new PrintWriter(writer));
		System.err.println("failed to clean up TraceLogger, exception=" + writer.toString());
	}

	private ExceptionTraceUtil() {
	}
}
