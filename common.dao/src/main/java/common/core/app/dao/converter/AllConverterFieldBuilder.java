package common.core.app.dao.converter;

import java.lang.reflect.Field;

/**
 * @author: jerry
 * @date: 2018年12月20日下午8:34:05
 * @Description: AllConverterFieldBuilder
 * 
 */
public class AllConverterFieldBuilder implements ConverterFieldBuilder {

	private ConverterFieldBuilder[] converterFieldBuilders = null;
	
	private ConverterFieldBuilder[] interceptFieldBuilders = null;

	private static final AllConverterFieldBuilder ME = new AllConverterFieldBuilder();

	public static AllConverterFieldBuilder get() {
		return ME;
	}

	private AllConverterFieldBuilder() {
		super();
		converterFieldBuilders = new ConverterFieldBuilder[] { new ImageUrlFieldBuilder(), new MoneyFieldBuilder() };
		interceptFieldBuilders= new ConverterFieldBuilder[] {new ImageUrlFieldBuilder() };
	}

	@Override
	public Object convert(Object obj, Field field, Object fieldValue) {
		Object result = fieldValue;
		for (ConverterFieldBuilder converterFieldBuilder : converterFieldBuilders) {
			result = converterFieldBuilder.convert(obj, field, result);
		}
		return result;
	}

	@Override
	public Object intercept(Object obj, Field field, Object fieldValue) {
		Object result = fieldValue;
		for (ConverterFieldBuilder converterFieldBuilder : interceptFieldBuilders) {
			result = converterFieldBuilder.intercept(obj, field, result);
		}
		return result;
	}

}
