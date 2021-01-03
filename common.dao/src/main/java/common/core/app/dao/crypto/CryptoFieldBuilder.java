package common.core.app.dao.crypto;

import java.lang.reflect.Field;

public interface CryptoFieldBuilder {

	public Object encode(Object obj, Field field, Object fieldValue);
	
	public Object decode(Object obj, Field field, Object fieldValue);
	
}
