package common.core.app.dao.converter;

import java.lang.reflect.Field;

import common.core.app.context.ConfigContext;
import common.core.common.util.StringUtil;

/**
 * @author: jerry
 * @date: 2018年12月20日下午8:18:01
 * @Description: ImageUrlFieldBuilder
 * 
 */
public class ImageUrlFieldBuilder implements ConverterFieldBuilder {

	@Override
	public Object convert(Object obj, Field field, Object fieldValue) {
		ImageUrlField imageUrlField = field.getAnnotation(ImageUrlField.class);
		if (null == imageUrlField || StringUtil.isEmpty(fieldValue))
			return fieldValue;

		// getkey
		String imageUrlPrefixConfig = imageUrlField.imageUrlPrefixConfig();
		String[] imageUrlPrefixs = ConfigContext.getStringValues(imageUrlPrefixConfig);
		// add prefix
		if (imageUrlPrefixs != null && imageUrlPrefixs.length != 0) {
			int hash = fieldValue.hashCode();
			int index = Math.abs(hash % imageUrlPrefixs.length);
			String imageUrlPrefix = imageUrlPrefixs[index].trim();
			StringBuffer imageUrl = new StringBuffer();
			fieldValue = imageUrl.append(imageUrlPrefix).append(fieldValue).toString();
		}
		return fieldValue;
	}

	@Override
	public Object intercept(Object obj, Field field, Object fieldValue) {
		ImageUrlField imageUrlField = field.getAnnotation(ImageUrlField.class);
		if (null == imageUrlField || StringUtil.isEmpty(fieldValue))
			return fieldValue;

		// getkey
		String imageUrlPrefixConfig = imageUrlField.imageUrlPrefixConfig();
		String[] imageUrlPrefixs = ConfigContext.getStringValues(imageUrlPrefixConfig);
		// add prefix
		if (imageUrlPrefixs != null && imageUrlPrefixs.length != 0) {
			for(String imageUrlPerfix:imageUrlPrefixs){
				int index=fieldValue.toString().indexOf(imageUrlPerfix.trim());
				if(index!=-1) {//有匹配字符串
					fieldValue=fieldValue.toString().replace(imageUrlPerfix.trim(), "").trim();
					break;
				}
			}
		}
		return fieldValue;
	}

}
