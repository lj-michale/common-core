package common.core.app.context;

import java.util.Locale;

import org.springframework.context.support.ResourceBundleMessageSource;

public class Message extends ResourceBundleMessageSource {
	public String getMessage(String key, Object... arguments) {
		return super.getMessage(key, arguments, Locale.getDefault());
	}
}
