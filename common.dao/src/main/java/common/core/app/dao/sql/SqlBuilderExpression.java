package common.core.app.dao.sql;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target({ FIELD })
public @interface SqlBuilderExpression {
	SqlExpression value() default SqlExpression.EQUAL;

	String columnName() default "";
}
