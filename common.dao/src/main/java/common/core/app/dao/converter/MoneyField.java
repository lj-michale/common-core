package common.core.app.dao.converter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @author: yangjun 
 * @date: 2019年5月7日上午10:05:17   
 * @Description: 格式化金额注解
 *
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface MoneyField {
    
}
