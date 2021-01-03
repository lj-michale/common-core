package common.core.app.dao.converter;

import java.lang.reflect.Field;
import java.math.BigDecimal;

import common.core.app.log.Logger;
import common.core.app.log.LoggerFactory;
import common.core.common.util.StringUtil;

/**
 * 
 * @author: yangjun 
 * @date: 2019年4月25日下午4:38:15   
 * @Description: 
 *
 */
public class MoneyFieldBuilder implements ConverterFieldBuilder {
    
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Override
	public Object convert(Object obj, Field field, Object fieldValue) {
	    MoneyField moneyField = field.getAnnotation(MoneyField.class);
		if (null == moneyField || StringUtil.isEmpty(fieldValue)) {
			return fieldValue;
	    }
		try {
            //是否BigDecimal类型
            boolean isBigDecimal = fieldValue instanceof BigDecimal;
            
            //金额字段类型转换：例如60.00 => 60, 60.10 => 60.1,  60.12 不变还是60.12
            String strValue = fieldValue.toString();
            if (!strValue.contains(".")) {
                return fieldValue;
            }
            String end = strValue.substring(strValue.indexOf(".") + 1, strValue.indexOf(".") + 3);
            if (end.equals("00")) {
                return isBigDecimal ? new BigDecimal(strValue.substring(0, strValue.indexOf("."))) : strValue.substring(0, strValue.indexOf("."));
            }
            if (end.substring(1).equals("0")) {
                return isBigDecimal ? new BigDecimal(strValue.substring(0, strValue.indexOf(".") + 2)) : strValue.substring(0, strValue.indexOf(".") + 2);
            }
            return isBigDecimal ? new BigDecimal(strValue) : strValue;
        } catch (Exception e) {
             logger.error("", e);
             return fieldValue;
        }
	}

    @Override
    public Object intercept(Object obj, Field field, Object fieldValue) {
        // TODO Auto-generated method stub
         return null;
    }
}
