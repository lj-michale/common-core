package common.core.web.api;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface ApiMethod {
	public static final String DEFAULT = "#";

	String group() default DEFAULT;

	/**
	 * 接口名称
	 * 
	 * @return
	 */
	String name() default DEFAULT;

	/**
	 * 接口匹配，"ios<=1.0.0|android<=1.0.1"
	 * 
	 * @return
	 */
	String match() default DEFAULT;

	/**
	 * 接口描述
	 * 
	 * @return
	 */
	String description();

}
