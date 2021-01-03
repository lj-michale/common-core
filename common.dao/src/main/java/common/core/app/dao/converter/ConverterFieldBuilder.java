package common.core.app.dao.converter;

import java.lang.reflect.Field;

/**
 * @author: jerry
 * @date: 2018年12月20日下午8:34:08
 * @Description: ConverterFieldBuilder
 * 
 */
public interface ConverterFieldBuilder {

	public Object convert(Object obj, Field field, Object fieldValue);
	
	/**
	 * @param obj
	 * @param field
	 * @param fieldValue
	 * @return
	 * @Author: icesong
	 * @Date: 2018年12月26日下午4:01:10   
	 * @Description: 截取绝对路径图片路径
	 */
	public Object intercept(Object obj, Field field, Object fieldValue);

}
