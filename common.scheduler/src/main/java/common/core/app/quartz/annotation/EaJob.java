package common.core.app.quartz.annotation;


import java.lang.annotation.*;

/**
 * Created by user on 2016/12/29.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface EaJob {
	String value();
	String group() default "EA_SCHEDULER";
}
