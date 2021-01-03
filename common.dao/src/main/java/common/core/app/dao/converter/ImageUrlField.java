package common.core.app.dao.converter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**     
 * @author: jerry 
 * @date: 2018年12月20日下午8:15:21   
 * @Description: 目前只支持对数据库查询返回字段处理，想在传入数据库的参数上做转换的如果后续确实有需要再加上。
 * 
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ImageUrlField {

	String imageUrlPrefixConfig() default "image.url.prefix";
}