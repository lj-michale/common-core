package common.core.common.doc.html.reader;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import common.core.app.log.Logger;
import common.core.app.log.LoggerFactory;
import common.core.common.assertion.util.AssertErrorUtils;
import common.core.common.converter.BigDecimalConverter;
import common.core.common.doc.html.annotation.HtmlReaderElement;
import common.core.common.doc.html.annotation.HtmlReaderType;
import common.core.common.util.ClassUtil;
import common.core.common.util.IoUtil;
import common.core.common.util.StringConverterUtil;
import common.core.common.util.StringUtil;

public class HtmlReader {
	private final Logger logger = LoggerFactory.getLogger(HtmlReader.class);

	private Document doc;
	private static final BigDecimalConverter BIGDECIMAL_CONVERTER = new BigDecimalConverter();

	public HtmlReader(InputStream inputStream, String charsetName, String baseUri) {
		logger.debug("HtmlReader parse doc from inputStream start");
		try {
			doc = Jsoup.parse(inputStream, charsetName, baseUri);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			logger.debug("HtmlReader parse doc from inputStream end");
			IoUtil.close(inputStream);
		}
	}

	private boolean check(Field field, Element element) {
		HtmlReaderElement htmlReaderElement = field.getAnnotation(HtmlReaderElement.class);
		String[] paths = htmlReaderElement.checkPath();
		String[] values = htmlReaderElement.checkValue();
		if (null == paths || paths.length == 0 || null == values || values.length == 0)
			return true;
		for (String path : paths) {
			Elements eles = element.select(path);
			for (int i = 0; i < eles.size(); i++) {
				String value = eles.get(i).text();
				for (String v : values) {
					if (v.startsWith("%") && v.endsWith("%") && v.length() > 2) {
						if (value.contains(v.subSequence(1, v.length() - 1)))
							return true;
					}
					if (v.startsWith("%")) {
						if (value.endsWith(v.substring(1)))
							return true;
					}
					if (v.endsWith("%")) {
						if (value.startsWith(v.substring(0, v.length() - 1)))
							return true;
					}
					if (v.equals(value))
						return true;
				}
			}
		}
		return false;
	}

	private Object read(Field field, Element element) {
		if (!check(field, element)) {
			return null;
		}

		int count = 0;
		BigDecimal sum = new BigDecimal(0);
		StringBuffer result = null;

		HtmlReaderElement htmlReaderElement = field.getAnnotation(HtmlReaderElement.class);
		String[] paths = htmlReaderElement.path();
		HtmlReaderType type = htmlReaderElement.type();
		for (String path : paths) {
			Elements eles = element.select(path);
			if (HtmlReaderType.COUNT.equals(type)) {
				count = count + eles.size();
				continue;
			}

			if (HtmlReaderType.WRAPER_OBJECT.equals(type)) {
				if (eles.size() == 0)
					return null;
				Object obj = ClassUtil.newInstance(field.getType());
				for (int i = 0; i < eles.size(); i++) {
					this.bind(obj, eles.get(i));
				}
				return obj;
			}

			for (int i = 0; i < eles.size(); i++) {
				if (HtmlReaderType.WRAPER_LIST.equals(type)) {
					return buildListField(field, element);
				}
				String value = eles.get(i).text();
				if (!StringUtil.hasText(value))
					continue;
				if (HtmlReaderType.SUM.equals(type)) {
					if (StringUtil.isBlank(value))
						continue;
					sum = sum.add(BIGDECIMAL_CONVERTER.convert(value.trim()));
					continue;
				}

				if (null == result)
					result = new StringBuffer();
				if (result.length() > 0)
					result.append(";");
				result.append(value.trim());
			}
		}

		if (HtmlReaderType.COUNT.equals(type)) {
			return count;
		} else if (HtmlReaderType.SUM.equals(type)) {
			return sum;
		} else {
			return null == result ? null : result.toString();
		}
	}

	public Object bind(Object obj) {
		return this.bind(obj, doc);
	}

	private Object bind(Object obj, Element element) {
		List<Field> htmlReaderElementFields = ClassUtil.findAnnotationFields(obj.getClass(), HtmlReaderElement.class);
		for (Field field : htmlReaderElementFields) {
			Object value = this.read(field, element);
			if (null == value)
				continue;
			try {
				if (StringConverterUtil.isSupportConvert(field.getType())) {
					field.set(obj, StringConverterUtil.convert(value.toString(), field.getType()));
				} else {
					field.set(obj, value);
				}

			} catch (IllegalArgumentException | IllegalAccessException e) {
				throw new RuntimeException(e);
			}
		}

		return obj;

	}

	@SuppressWarnings("unchecked")
	public List<?> buildListField(Field field, Element element) {
		AssertErrorUtils.assertTrue(field.getType().isAssignableFrom(List.class), "{} field type is not list", field.toGenericString());
		Class<?> type = ClassUtil.getGenericTypeFromListField(field);
		@SuppressWarnings("rawtypes")
		List objs = new ArrayList<>();
		HtmlReaderElement htmlReaderElement = field.getAnnotation(HtmlReaderElement.class);
		for (String path : htmlReaderElement.path()) {
			Elements eles = element.select(path);
			for (int i = 0; i < eles.size(); i++) {
				if (!this.check(field, eles.get(i)))
					continue;
				objs.add(this.bindToType(type, eles.get(i)));
			}
		}
		return objs.isEmpty() ? null : objs;
	}

	public <T> T bindToType(Class<T> type) {
		logger.debug("HtmlReader bindToType type={} start", type);
		try {
			T obj = bindToType(type, doc);
			return obj;
		} finally {
			logger.debug("HtmlReader bindToType type={} end", type);
		}
	}

	private <T> T bindToType(Class<T> type, Element element) {
		T obj = ClassUtil.newInstance(type);
		this.bind(obj, element);
		return obj;
	}
}
