package common.core.common.doc.html.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target({ FIELD })
public @interface HtmlReaderElement {
	HtmlReaderType type()

	default HtmlReaderType.GET;

	String[] path();

	String[] checkPath() default {};

	String[] checkValue() default {};
}
