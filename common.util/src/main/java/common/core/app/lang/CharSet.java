package common.core.app.lang;

import java.nio.charset.Charset;

import org.apache.commons.codec.CharEncoding;

public class CharSet extends CharEncoding {
	public final static String DEFAULT_CHAR_SET_STRING = CharSet.UTF_8;
	public final static Charset DEFAULT_CHAR_SET_CLASS = Charset.forName(CharSet.DEFAULT_CHAR_SET_STRING);
}
