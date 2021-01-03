package common.core.app.dao.crypto;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface MixField {
	String privateKeyFileConfig() default "rsa.private.key";

	String[] assemblyFields();
}
