package common.core.common.util;

import org.apache.commons.lang3.StringEscapeUtils;

public class StringEscapeUtil {
	// Java and JavaScript
	// --------------------------------------------------------------------------
	/**
	 * <p>
	 * Escapes the characters in a {@code String} using Java String rules.
	 * </p>
	 *
	 * <p>
	 * Deals correctly with quotes and control-chars (tab, backslash, cr, ff,
	 * etc.)
	 * </p>
	 *
	 * <p>
	 * So a tab becomes the characters {@code '\\'} and {@code 't'}.
	 * </p>
	 *
	 * <p>
	 * The only difference between Java strings and JavaScript strings is that
	 * in JavaScript, a single quote and forward-slash (/) are escaped.
	 * </p>
	 *
	 * <p>
	 * Example:
	 * 
	 * <pre>
	 * input string: He didn't say, "Stop!"
	 * output string: He didn't say, \"Stop!\"
	 * </pre>
	 * </p>
	 *
	 * @param input
	 *            String to escape values in, may be null
	 * @return String with escaped values, {@code null} if null string input
	 */
	public static final String escapeJava(String input) {
		return StringEscapeUtils.escapeJava(input);
	}

	/**
	 * <p>
	 * Escapes the characters in a {@code String} using EcmaScript String rules.
	 * </p>
	 * <p>
	 * Escapes any values it finds into their EcmaScript String form. Deals
	 * correctly with quotes and control-chars (tab, backslash, cr, ff, etc.)
	 * </p>
	 *
	 * <p>
	 * So a tab becomes the characters {@code '\\'} and {@code 't'}.
	 * </p>
	 *
	 * <p>
	 * The only difference between Java strings and EcmaScript strings is that
	 * in EcmaScript, a single quote and forward-slash (/) are escaped.
	 * </p>
	 *
	 * <p>
	 * Note that EcmaScript is best known by the JavaScript and ActionScript
	 * dialects.
	 * </p>
	 *
	 * <p>
	 * Example:
	 * 
	 * <pre>
	 * input string: He didn't say, "Stop!"
	 * output string: He didn\'t say, \"Stop!\"
	 * </pre>
	 * </p>
	 *
	 * @param input
	 *            String to escape values in, may be null
	 * @return String with escaped values, {@code null} if null string input
	 *
	 * @since 3.0
	 */
	public static final String escapeEcmaScript(String input) {
		return StringEscapeUtils.escapeEcmaScript(input);
	}

	/**
	 * <p>
	 * Unescapes any Java literals found in the {@code String}. For example, it
	 * will turn a sequence of {@code '\'} and {@code 'n'} into a newline
	 * character, unless the {@code '\'} is preceded by another {@code '\'}.
	 * </p>
	 * 
	 * @param input
	 *            the {@code String} to unescape, may be null
	 * @return a new unescaped {@code String}, {@code null} if null string input
	 */
	public static final String unescapeJava(String input) {
		return StringEscapeUtils.unescapeJava(input);
	}

	/**
	 * <p>
	 * Unescapes any EcmaScript literals found in the {@code String}.
	 * </p>
	 *
	 * <p>
	 * For example, it will turn a sequence of {@code '\'} and {@code 'n'} into
	 * a newline character, unless the {@code '\'} is preceded by another
	 * {@code '\'}.
	 * </p>
	 *
	 * @see #unescapeJava(String)
	 * @param input
	 *            the {@code String} to unescape, may be null
	 * @return A new unescaped {@code String}, {@code null} if null string input
	 *
	 * @since 3.0
	 */
	public static final String unescapeEcmaScript(String input) {
		return StringEscapeUtils.unescapeEcmaScript(input);
	}

	// HTML and XML
	// --------------------------------------------------------------------------
	/**
	 * <p>
	 * Escapes the characters in a {@code String} using HTML entities.
	 * </p>
	 *
	 * <p>
	 * For example:
	 * </p>
	 * <p>
	 * <code>"bread" & "butter"</code>
	 * </p>
	 * becomes:
	 * <p>
	 * <code>&amp;quot;bread&amp;quot; &amp;amp; &amp;quot;butter&amp;quot;</code>
	 * .
	 * </p>
	 *
	 * <p>
	 * Supports all known HTML 4.0 entities, including funky accents. Note that
	 * the commonly used apostrophe escape character (&amp;apos;) is not a legal
	 * entity and so is not supported).
	 * </p>
	 *
	 * @param input
	 *            the {@code String} to escape, may be null
	 * @return a new escaped {@code String}, {@code null} if null string input
	 * 
	 * @see <a href=
	 *      "http://hotwired.lycos.com/webmonkey/reference/special_characters/">
	 *      ISO Entities</a>
	 * @see <a href="http://www.w3.org/TR/REC-html32#latin1">HTML 3.2 Character
	 *      Entities for ISO Latin-1</a>
	 * @see <a href="http://www.w3.org/TR/REC-html40/sgml/entities.html">HTML
	 *      4.0 Character entity references</a>
	 * @see <a href="http://www.w3.org/TR/html401/charset.html#h-5.3">HTML 4.01
	 *      Character References</a>
	 * @see <a href="http://www.w3.org/TR/html401/charset.html#code-position">
	 *      HTML 4.01 Code positions</a>
	 * 
	 * @since 3.0
	 */
	public static final String escapeHtml4(String input) {
		return StringEscapeUtils.escapeHtml4(input);
	}

	/**
	 * <p>
	 * Escapes the characters in a {@code String} using HTML entities.
	 * </p>
	 * <p>
	 * Supports only the HTML 3.0 entities.
	 * </p>
	 *
	 * @param input
	 *            the {@code String} to escape, may be null
	 * @return a new escaped {@code String}, {@code null} if null string input
	 * 
	 * @since 3.0
	 */
	public static final String escapeHtml3(String input) {
		return StringEscapeUtils.escapeHtml3(input);
	}

	// -----------------------------------------------------------------------
	/**
	 * <p>
	 * Unescapes a string containing entity escapes to a string containing the
	 * actual Unicode characters corresponding to the escapes. Supports HTML 4.0
	 * entities.
	 * </p>
	 *
	 * <p>
	 * For example, the string "&amp;lt;Fran&amp;ccedil;ais&amp;gt;" will become
	 * "&lt;Fran&ccedil;ais&gt;"
	 * </p>
	 *
	 * <p>
	 * If an entity is unrecognized, it is left alone, and inserted verbatim
	 * into the result string. e.g. "&amp;gt;&amp;zzzz;x" will become
	 * "&gt;&amp;zzzz;x".
	 * </p>
	 *
	 * @param input
	 *            the {@code String} to unescape, may be null
	 * @return a new unescaped {@code String}, {@code null} if null string input
	 * 
	 * @since 3.0
	 */
	public static final String unescapeHtml4(String input) {
		return StringEscapeUtils.unescapeHtml4(input);
	}

	/**
	 * <p>
	 * Unescapes a string containing entity escapes to a string containing the
	 * actual Unicode characters corresponding to the escapes. Supports only
	 * HTML 3.0 entities.
	 * </p>
	 *
	 * @param input
	 *            the {@code String} to unescape, may be null
	 * @return a new unescaped {@code String}, {@code null} if null string input
	 * 
	 * @since 3.0
	 */
	public static final String unescapeHtml3(String input) {
		return StringEscapeUtils.unescapeHtml3(input);
	}

	// -----------------------------------------------------------------------
	/**
	 * <p>
	 * Escapes the characters in a {@code String} using XML entities.
	 * </p>
	 *
	 * <p>
	 * For example: <tt>"bread" & "butter"</tt> =>
	 * <tt>&amp;quot;bread&amp;quot; &amp;amp; &amp;quot;butter&amp;quot;</tt>.
	 * </p>
	 *
	 * <p>
	 * Supports only the five basic XML entities (gt, lt, quot, amp, apos). Does
	 * not support DTDs or external entities.
	 * </p>
	 *
	 * <p>
	 * Note that Unicode characters greater than 0x7f are as of 3.0, no longer
	 * escaped. If you still wish this functionality, you can achieve it via the
	 * following:
	 * {@code StringEscapeUtils.ESCAPE_XML.with( NumericEntityEscaper.between(0x7f, Integer.MAX_VALUE) );}
	 * </p>
	 *
	 * @param input
	 *            the {@code String} to escape, may be null
	 * @return a new escaped {@code String}, {@code null} if null string input
	 * @see #unescapeXml(java.lang.String)
	 */
	public static final String escapeXml(String input) {
		return StringEscapeUtils.escapeXml(input);
	}

	// -----------------------------------------------------------------------
	/**
	 * <p>
	 * Unescapes a string containing XML entity escapes to a string containing
	 * the actual Unicode characters corresponding to the escapes.
	 * </p>
	 *
	 * <p>
	 * Supports only the five basic XML entities (gt, lt, quot, amp, apos). Does
	 * not support DTDs or external entities.
	 * </p>
	 *
	 * <p>
	 * Note that numerical \\u Unicode codes are unescaped to their respective
	 * Unicode characters. This may change in future releases.
	 * </p>
	 *
	 * @param input
	 *            the {@code String} to unescape, may be null
	 * @return a new unescaped {@code String}, {@code null} if null string input
	 * @see #escapeXml(String)
	 */
	public static final String unescapeXml(String input) {
		return StringEscapeUtils.unescapeXml(input);
	}

	// -----------------------------------------------------------------------

	/**
	 * <p>
	 * Returns a {@code String} value for a CSV column enclosed in double
	 * quotes, if required.
	 * </p>
	 *
	 * <p>
	 * If the value contains a comma, newline or double quote, then the String
	 * value is returned enclosed in double quotes.
	 * </p>
	 * </p>
	 *
	 * <p>
	 * Any double quote characters in the value are escaped with another double
	 * quote.
	 * </p>
	 *
	 * <p>
	 * If the value does not contain a comma, newline or double quote, then the
	 * String value is returned unchanged.
	 * </p>
	 * </p>
	 *
	 * see
	 * <a href="http://en.wikipedia.org/wiki/Comma-separated_values">Wikipedia
	 * </a> and <a href="http://tools.ietf.org/html/rfc4180">RFC 4180</a>.
	 *
	 * @param input
	 *            the input CSV column String, may be null
	 * @return the input String, enclosed in double quotes if the value contains
	 *         a comma, newline or double quote, {@code null} if null string
	 *         input
	 * @since 2.4
	 */
	public static final String escapeCsv(String input) {
		return StringEscapeUtils.unescapeCsv(input);
	}

	/**
	 * <p>
	 * Returns a {@code String} value for an unescaped CSV column.
	 * </p>
	 *
	 * <p>
	 * If the value is enclosed in double quotes, and contains a comma, newline
	 * or double quote, then quotes are removed.
	 * </p>
	 *
	 * <p>
	 * Any double quote escaped characters (a pair of double quotes) are
	 * unescaped to just one double quote.
	 * </p>
	 *
	 * <p>
	 * If the value is not enclosed in double quotes, or is and does not contain
	 * a comma, newline or double quote, then the String value is returned
	 * unchanged.
	 * </p>
	 * </p>
	 *
	 * see
	 * <a href="http://en.wikipedia.org/wiki/Comma-separated_values">Wikipedia
	 * </a> and <a href="http://tools.ietf.org/html/rfc4180">RFC 4180</a>.
	 *
	 * @param input
	 *            the input CSV column String, may be null
	 * @return the input String, with enclosing double quotes removed and
	 *         embedded double quotes unescaped, {@code null} if null string
	 *         input
	 * @since 2.4
	 */
	public static final String unescapeCsv(String input) {
		return StringEscapeUtils.unescapeCsv(input);
	}
}
