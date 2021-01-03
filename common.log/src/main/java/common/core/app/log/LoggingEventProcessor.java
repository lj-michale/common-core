package common.core.app.log;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.Flushable;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.MDC;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.spi.ILoggingEvent;

public class LoggingEventProcessor {
	public final static String DEFAULT_CHAR_SET_STRING = "UTF-8";
	private static final Integer MAX_HOLD_SIZE = 5000;
	private final PatternLayout layout;
	private final String logFolder;
	private final Queue<ILoggingEvent> events = new ConcurrentLinkedQueue<>();
	private final AtomicInteger eventSize = new AtomicInteger(0);
	private volatile boolean hold = true;
	private volatile Writer writer;

	public LoggingEventProcessor(PatternLayout layout, String logFolder) {
		this.layout = layout;
		this.logFolder = logFolder;
	}

	public void process(ILoggingEvent event) throws IOException {
		if (hold) {
			addEvent(event);
			if (flushLog(event)) {
				flushTraceLogs(event.getLevel().toString());
				hold = false;
			}
		} else {
			write(event);
		}
	}

	private void addEvent(ILoggingEvent event) {
		event.getThreadName(); // force "logback" to remember current thread
		events.add(event);
		eventSize.getAndIncrement();
	}

	private boolean flushLog(ILoggingEvent event) {
		return event.getLevel().isGreaterOrEqual(Level.WARN) || eventSize.get() > MAX_HOLD_SIZE;
	}

	private synchronized void flushTraceLogs(String level) throws IOException {
		// this method to maintain order
		if (writer == null)
			writer = createWriter(level);
		while (true) {
			ILoggingEvent event = events.poll();
			if (event == null) {
				return;
			}
			write(event);
		}
	}

	void write(ILoggingEvent event) throws IOException {
		String log = layout.doLayout(event);
		writer.write(log);
	}

	private Writer createWriter(String level) throws FileNotFoundException, UnsupportedEncodingException {
		if (logFolder == null)
			return new BufferedWriter(new OutputStreamWriter(System.err, Charset.defaultCharset().name()));
		String logFilePath = generateLogFilePath(getAction(), new Date(), getRequestId(), level);
		File logFile = new File(logFilePath);
		createParentFolder(logFile);
		return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(logFile, true), Charset.defaultCharset().name()));
	}

	private void createParentFolder(File logFile) {
		File folder = logFile.getParentFile();
		folder.mkdirs();
	}

	void cleanup(boolean forceFlushTraceLog) throws IOException {
		if (forceFlushTraceLog) {
			flushTraceLogs("forceFlushTraceLog");
		}
		if (logFolder == null) {
			flush(writer); // do not close System.err (when logFolder is
							// null)
		} else {
			close(writer);
		}
	}

	public static void close(Closeable closeable) {
		try {
			if (closeable != null) {
				if (closeable instanceof Flushable) {
					flush((Flushable) closeable);
				}
				closeable.close();
			}
		} catch (IOException e) {
			ExceptionTraceUtil.stackTrace(e);
		}
	}

	public static void flush(Flushable flushable) {
		try {
			if (flushable != null) {
				flushable.flush();
			}
		} catch (IOException e) {
			ExceptionTraceUtil.stackTrace(e);
		}
	}

	private String getRequestId() {
		String requestId = MDC.get(LogConstants.MDC_REQUEST_ID);
		if (requestId == null) {
			requestId = "unknown";
		}
		return requestId;
	}

	private String getAction() {
		String action = MDC.get(LogConstants.MDC_ACTION);
		if (action == null) {
			action = "unknown";
		}
		return action;
	}

	String generateLogFilePath(String action, Date targetDate, String requestId, String level) {
		return String.format("%1$s/%5$s/%2$tY/%2$tm/%2$td/%2$tH/%2$tY%2$tm%2$td%2$tH%2$tM.%4$s.%3$s.log", logFolder, targetDate, action, requestId, level);
	}
}
