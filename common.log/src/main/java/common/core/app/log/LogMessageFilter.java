package common.core.app.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class LogMessageFilter {
	private final Logger logger = LoggerFactory.getLogger(LogMessageFilter.class);
	private final ConcurrentMap<String, Pattern> patternCache = new ConcurrentHashMap<String, Pattern>();

	protected String mask(String message, String patternExpression) {
		Pattern pattern;
		try {
			pattern = getPattern(patternExpression);
		} catch (Exception e) {
			logger.error("failed to parse pattern, pattern=" + patternExpression, e);
			return "failed to parse pattern, entire message masked, pattern=" + patternExpression;
		}
		Matcher matcher = pattern.matcher(message);
		StringBuilder builder = filterMessage(message, matcher);
		if (builder.length() > 0)
			return builder.toString();
		return maskEntireMessage(patternExpression);
	}

	private StringBuilder filterMessage(String message, Matcher matcher) {
		StringBuilder builder = new StringBuilder();
		int current = 0;
		while (matcher.find()) {
			int groupCount = matcher.groupCount();
			for (int i = 1; i <= groupCount; i++) {
				int start = matcher.start(i);
				int end = matcher.end(i);
				builder.append(message.subSequence(current, start)).append("--masked--");
				current = end;
			}
		}
		if (current > 0 && current < message.length())
			builder.append(message.subSequence(current, message.length()));
		return builder;
	}

	private Pattern getPattern(String patternExpression) {
		Pattern pattern = patternCache.get(patternExpression);
		if (pattern == null) {
			pattern = Pattern.compile(patternExpression);
			patternCache.putIfAbsent(patternExpression, pattern);
		}
		return pattern;
	}

	private String maskEntireMessage(String pattern) {
		return "pattern or group not found, entire message masked, pattern=" + pattern;
	}

	public abstract String filter(String loggerName, String message);
}
