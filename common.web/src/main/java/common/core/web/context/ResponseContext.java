package common.core.web.context;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

public class ResponseContext {

	private final static ThreadLocal<HttpServletResponse> response = new ThreadLocal<>();

	public static void initContext(HttpServletResponse servletResponse) {
		response.set(servletResponse);
	}

	public static void cleanContext() {
		response.remove();
	}

	public static HttpServletResponse get() {
		return response.get();
	}

	public static void addCookie(Cookie cookie) {
		ResponseContext.get().addCookie(cookie);
	}

	public static boolean containsHeader(String name) {
		return ResponseContext.get().containsHeader(name);
	}

	public static String encodeURL(String url) {
		return ResponseContext.get().encodeURL(url);
	}

	public static String getCharacterEncoding() {
		return ResponseContext.get().getCharacterEncoding();
	}

	public static String encodeRedirectURL(String url) {
		return ResponseContext.get().encodeRedirectURL(url);
	}

	public static String getContentType() {
		return ResponseContext.get().getContentType();
	}

	/*
	 * @deprecated
	 */
	@SuppressWarnings("deprecation")
	public static String encodeUrl(String url) {
		return ResponseContext.get().encodeUrl(url);
	}

	/*
	 * @deprecated
	 */
	@SuppressWarnings("deprecation")
	public static String encodeRedirectUrl(String url) {
		return ResponseContext.get().encodeRedirectUrl(url);
	}

	public static ServletOutputStream getOutputStream() throws IOException {
		return ResponseContext.get().getOutputStream();
	}

	public static void sendError(int sc, String msg) throws IOException {
		ResponseContext.get().sendError(sc, msg);
	}

	public static PrintWriter getWriter() throws IOException {
		return ResponseContext.get().getWriter();
	}

	public static void sendError(int sc) throws IOException {
		ResponseContext.get().sendError(sc);
	}

	public static void setCharacterEncoding(String charset) {
		ResponseContext.get().setCharacterEncoding(charset);
	}

	public static void sendRedirect(String location) throws IOException {
		ResponseContext.get().sendRedirect(location);
	}

	public static void setDateHeader(String name, long date) {
		ResponseContext.get().setDateHeader(name, date);
	}

	public static void setContentLength(int len) {
		ResponseContext.get().setContentLength(len);
	}

	public static void addDateHeader(String name, long date) {
		ResponseContext.get().addDateHeader(name, date);
	}

	public static void setContentType(String type) {
		ResponseContext.get().setContentType(type);
	}

	public static void setHeader(String name, String value) {
		ResponseContext.get().setHeader(name, value);
	}

	public static void addHeader(String name, String value) {
		ResponseContext.get().addHeader(name, value);
	}

	public static void setBufferSize(int size) {
		ResponseContext.get().setBufferSize(size);
	}

	public static void setIntHeader(String name, int value) {
		ResponseContext.get().setIntHeader(name, value);
	}

	public static void addIntHeader(String name, int value) {
		ResponseContext.get().addIntHeader(name, value);
	}

	public static void setStatus(int sc) {
		ResponseContext.get().setStatus(sc);
	}

	public static int getBufferSize() {
		return ResponseContext.get().getBufferSize();
	}

	public static void flushBuffer() throws IOException {
		ResponseContext.get().flushBuffer();
	}

	/*
	 * @deprecated
	 */
	@SuppressWarnings("deprecation")
	public static void setStatus(int sc, String sm) {
		ResponseContext.get().setStatus(sc, sm);
	}

	public static void resetBuffer() {
		ResponseContext.get().resetBuffer();
	}

	public static int getStatus() {
		return ResponseContext.get().getStatus();
	}

	public static boolean isCommitted() {
		return ResponseContext.get().isCommitted();
	}

	public static String getHeader(String name) {
		return ResponseContext.get().getHeader(name);
	}

	public static void reset() {
		ResponseContext.get().reset();
	}

	public static Collection<String> getHeaders(String name) {
		return ResponseContext.get().getHeaders(name);
	}

	public static void setLocale(Locale loc) {
		ResponseContext.get().setLocale(loc);
	}

	public static Collection<String> getHeaderNames() {
		return ResponseContext.get().getHeaderNames();
	}

	public static Locale getLocale() {
		return ResponseContext.get().getLocale();
	}

}
