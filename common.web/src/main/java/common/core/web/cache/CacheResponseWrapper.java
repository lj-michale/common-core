package common.core.web.cache;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class CacheResponseWrapper extends HttpServletResponseWrapper {

	private CacheOutputStream outStream;

	// 替换OutputStream和PrintWriter
	private ServletOutputStream stream;
	private PrintWriter writer;

	static class CacheOutputStream extends ServletOutputStream {

		private ByteArrayOutputStream bos;

		CacheOutputStream() {
			bos = new ByteArrayOutputStream();
		}

		@Override
		public void write(int param) throws IOException {
			bos.write(param);
		}

		@Override
		public void write(byte[] b, int off, int len) throws IOException {
			bos.write(b, off, len);
		}

		protected byte[] getBytes() {
			return bos.toByteArray();
		}
	}

	public CacheResponseWrapper(HttpServletResponse original) {
		super(original);
	}

	protected ServletOutputStream createOutputStream() throws IOException {
		outStream = new CacheOutputStream();
		return outStream;
	}

	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		if (stream != null) {
			return stream;
		}

		if (writer != null) {
			throw new IOException("Writer already in use");
		}

		stream = createOutputStream();
		return stream;
	}

	@Override
	public PrintWriter getWriter() throws IOException {
		if (writer != null) {
			return writer;
		}

		if (stream != null) {
			throw new IOException("OutputStream already in use");
		}

		writer = new PrintWriter(new OutputStreamWriter(createOutputStream(),"utf8"));
		return writer;
	}

	public byte[] getBytes() throws IOException {
		if (outStream != null) {
			return outStream.getBytes();
		}

		return null;
	}
}