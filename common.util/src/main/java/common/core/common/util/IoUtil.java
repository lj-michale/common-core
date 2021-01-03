package common.core.common.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.CharArrayReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.Flushable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;

import common.core.common.exception.RuntimeIOException;

public class IoUtil {

	public static final int BUFFER_SIZE = 1024;

	public static void fileToOutputStream(File file, OutputStream outputStream) throws IOException {
		byte buffer[] = new byte[IoUtil.BUFFER_SIZE];
		int length = -1;
		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream(file);
			while ((length = inputStream.read(buffer, 0, IoUtil.BUFFER_SIZE)) != -1) {
				outputStream.write(buffer, 0, length);
			}
		} finally {
			IoUtil.close(inputStream);
		}

	}

	public static String text(InputStream inputStream) {
		StringBuffer out = new StringBuffer();
		byte[] b = new byte[4096];
		try {
			for (int n; (n = inputStream.read(b)) != -1;) {
				out.append(new String(b, 0, n));
			}
		} catch (IOException e) {
			throw new RuntimeIOException(e);
		}
		return out.toString();
	}

	public static String text(Reader reader) {
		Reader bufferedReader = reader;
		if (!(reader instanceof BufferedReader)) {
			bufferedReader = new BufferedReader(reader);
		}
		StringBuilder builder = new StringBuilder();
		char[] buffer = new char[BUFFER_SIZE];
		try {
			int length;
			while (true) {
				length = bufferedReader.read(buffer);
				if (length == -1)
					break;
				builder.append(buffer, 0, length);
			}
			return builder.toString();
		} catch (IOException e) {
			throw new RuntimeIOException(e);
		} finally {
			close(reader);
		}
	}

	public static void createDirectorieIfMissing(File dirFile) {
		if (IoUtil.isParentDirectoryCreationRequired(dirFile)) {
			IoUtil.createDirectorieIfMissing(dirFile.getParentFile());
		}
		if (dirFile.exists())
			return;
		dirFile.mkdir();
	}

	private static boolean isParentDirectoryCreationRequired(File dirFile) {
		return dirFile.getParentFile() != null && !dirFile.getParentFile().exists();
	}

	public static void writeToFile(char[] data, File file) throws IOException {
		PrintWriter writer = null;
		try {
			CharArrayReader reader = new CharArrayReader(data);
			writer = new PrintWriter(file);
			char[] array = new char[IoUtil.BUFFER_SIZE];
			int length = -1;
			while ((length = reader.read(array, 0, IoUtil.BUFFER_SIZE)) != -1) {
				writer.write(array, 0, length);
			}
		} finally {
			IoUtil.close(writer);
		}

	}

	public static void writeToFile(byte[] data, File file) throws IOException {
		OutputStream outputStream = null;
		try {
			IoUtil.createDirectorieIfMissing(file.getParentFile());
			outputStream = new FileOutputStream(file);
			outputStream.write(data);
		} finally {
			IoUtil.close(outputStream);
		}

	}

	public static void close(Closeable closeable) {
		try {
			if (closeable != null) {
				if (closeable instanceof Flushable) {
					IoUtil.flush((Flushable) closeable);
				}
				closeable.close();
			}
		} catch (IOException e) {
			throw new RuntimeIOException(e);
		}
	}

	public static void flush(Flushable flushable) {
		try {
			if (flushable != null) {
				flushable.flush();
			}
		} catch (IOException e) {
			throw new RuntimeIOException(e);
		}
	}

	public static byte[] bytes(InputStream stream) {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

		byte[] buf = new byte[BUFFER_SIZE];
		int len;
		try {
			while (true) {
				len = stream.read(buf);
				if (len < 0)
					break;
				byteArrayOutputStream.write(buf, 0, len);
			}
		} catch (IOException e) {
			throw new RuntimeIOException(e);
		}

		return byteArrayOutputStream.toByteArray();
	}

}
