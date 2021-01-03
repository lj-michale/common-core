package common.core.site.cookie;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;

import org.springframework.util.StringUtils;

import common.core.app.log.Logger;
import common.core.app.log.LoggerFactory;
import common.core.common.assertion.util.AssertErrorUtils;
import common.core.web.context.RequestContext;
import common.core.web.context.ResponseContext;

public class CookieContext {
	private static final Logger LOGGER = LoggerFactory.getLogger(CookieContext.class);

	private static final ThreadLocal<CookieContext> COOKIE_CONTEXT = new ThreadLocal<>();

	Map<String, String> cookieValueMap = new HashMap<>();

	public static void initContext() {
		CookieContext.COOKIE_CONTEXT.set(new CookieContext());
		RequestContext.putCookiesToMap(CookieContext.currentCookieContext().cookieValueMap);
	}

	public static void cleanContext() {
		CookieContext.COOKIE_CONTEXT.remove();
	}

	private static CookieContext currentCookieContext() {
		return CookieContext.COOKIE_CONTEXT.get();
	}

	protected static String getCookieValue(CookieSpec cookieOption) {
		checkSecure(cookieOption);
		return CookieContext.currentCookieContext().cookieValueMap.get(cookieOption.getName());
	}

	protected static void setCookieValue(CookieSpec cookieOption, String value) {
		CookieContext.updateCookie(cookieOption, value);
	}

	private static void updateCookie(CookieSpec cookieOption, String value) {
		checkSecure(cookieOption);
		CookieContext.LOGGER.debug("update cookie,{}={}", cookieOption.getName(), value);
		Cookie cookie = buildCookie(cookieOption, value);
		if (cookie.getMaxAge() == CookieSpec.AGE_REMOVE) {
			CookieContext.currentCookieContext().cookieValueMap.remove(cookieOption.getName());
		} else {
			CookieContext.currentCookieContext().cookieValueMap.put(cookieOption.getName(), value);
		}
		ResponseContext.addCookie(cookie);
	}

	private static void checkSecure(CookieSpec cookieOption) {
		if (cookieOption.isSecure()) {
			AssertErrorUtils.assertTrue(RequestContext.isSecure(), "cookie {} must use in https", cookieOption.getName());
		}
	}

	protected static void deleteCookie(CookieSpec cookieOption) {
		updateCookie(cookieOption, null);
	}

	private static Cookie buildCookie(CookieSpec cookieOption, String value) {
		Cookie cookie = new Cookie(cookieOption.getName(), value);
		cookie.setPath(cookieOption.getPath());
		cookie.setHttpOnly(cookieOption.isHttpOnly());
		cookie.setSecure(cookieOption.isSecure());
		cookie.setMaxAge(cookieOption.getMaxAge());
		if (StringUtils.hasText(cookieOption.getDomain())) {
			cookie.setDomain(cookieOption.getDomain());
		}
		if (StringUtils.isEmpty(value))
			cookie.setMaxAge(CookieSpec.AGE_REMOVE);
		return cookie;
	}

}
