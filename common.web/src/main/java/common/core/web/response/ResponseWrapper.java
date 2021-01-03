package common.core.web.response;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import common.core.app.log.Logger;
import common.core.app.log.LoggerFactory;

public class ResponseWrapper extends HttpServletResponseWrapper {
	private static Logger logger = LoggerFactory.getLogger(ResponseWrapper.class);

	public ResponseWrapper(HttpServletResponse response) {
		super(response);
	}

	@Override
	public void addCookie(Cookie cookie) {
		super.addCookie(cookie);
		logger.debug("add cookie name={},secure={}", cookie.getName(), cookie.getSecure());
	}

	@Override
	public boolean containsHeader(String name) {
		return super.containsHeader(name);
	}

	@Override
	public String encodeURL(String url) {
		return super.encodeURL(url);
	}

	@Override
	public String encodeRedirectURL(String url) {
		return super.encodeRedirectURL(url);
	}

	@Override
	public void sendError(int sc, String msg) throws IOException {
		super.sendError(sc, msg);
	}

	@Override
	public void sendError(int sc) throws IOException {
		super.sendError(sc);
	}

	@Override
	public void sendRedirect(String location) throws IOException {
		super.sendRedirect(location);
	}

	@Override
	public void setDateHeader(String name, long date) {
		super.setDateHeader(name, date);
	}

	@Override
	public void addDateHeader(String name, long date) {
		super.addDateHeader(name, date);
	}

	@Override
	public void setHeader(String name, String value) {
		super.setHeader(name, value);
	}

	@Override
	public void addHeader(String name, String value) {
		super.addHeader(name, value);
	}

	@Override
	public void setIntHeader(String name, int value) {
		super.setIntHeader(name, value);
	}

	@Override
	public void addIntHeader(String name, int value) {
		super.addIntHeader(name, value);
	}

	@Override
	public void setStatus(int sc) {
		super.setStatus(sc);
	}

	@Override
	public int getStatus() {
		return super.getStatus();
	}

	@Override
	public String getHeader(String name) {
		return super.getHeader(name);
	}

	@Override
	public Collection<String> getHeaders(String name) {
		return super.getHeaders(name);
	}

	@Override
	public Collection<String> getHeaderNames() {
		return super.getHeaderNames();
	}

	@Override
	public ServletResponse getResponse() {
		return super.getResponse();
	}

	@Override
	public void setResponse(ServletResponse response) {
		super.setResponse(response);
	}

	@Override
	public void setCharacterEncoding(String charset) {
		super.setCharacterEncoding(charset);
	}

	@Override
	public String getCharacterEncoding() {
		return super.getCharacterEncoding();
	}

	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		return super.getOutputStream();
	}

	@Override
	public PrintWriter getWriter() throws IOException {
		return super.getWriter();
	}

	@Override
	public void setContentLength(int len) {
		super.setContentLength(len);
	}

	@Override
	public void setContentType(String type) {
		super.setContentType(type);
	}

	@Override
	public String getContentType() {
		return super.getContentType();
	}

	@Override
	public void setBufferSize(int size) {
		super.setBufferSize(size);
	}

	@Override
	public int getBufferSize() {
		return super.getBufferSize();
	}

	@Override
	public void flushBuffer() throws IOException {
		super.flushBuffer();
	}

	@Override
	public boolean isCommitted() {
		return super.isCommitted();
	}

	@Override
	public void reset() {
		super.reset();
	}

	@Override
	public void resetBuffer() {
		super.resetBuffer();
	}

	@Override
	public void setLocale(Locale loc) {
		super.setLocale(loc);
	}

	@Override
	public Locale getLocale() {
		return super.getLocale();
	}

	@Override
	public boolean isWrapperFor(ServletResponse wrapped) {
		return super.isWrapperFor(wrapped);
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	@Override
	public String toString() {
		return super.toString();
	}

	

}
