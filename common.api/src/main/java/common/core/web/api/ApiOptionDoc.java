package common.core.web.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ApiOptionDoc {
	public static final String DEFAULT = "#";

	/**
	 * 接口名称
	 * 
	 * @return
	 */
	String demoValue();

	/**
	 * 接口描述
	 * 
	 * @return
	 */
	String description() default DEFAULT;

	/**
	 * 接口规则
	 * 
	 * @return
	 */
	String demoRule() default "";
}
