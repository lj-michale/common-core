/**
 * 
 */
package common.core.web.queue;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zhengmuwang
 *
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface QueueRequired {

	int value();

	String message() default "请求排队中，请稍后再偿试";

	String valueConfig() default "";
}
