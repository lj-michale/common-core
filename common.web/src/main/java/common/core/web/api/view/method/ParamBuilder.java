package common.core.web.api.view.method;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import common.core.common.util.ClassUtil;
import common.core.common.util.ObjectUtil;

public class ParamBuilder {

	public static List<Param> buildApiParams(Type type) {
		Map<Type, Param> context = new LinkedHashMap<>();
		ParamBuilder.buildApiParamMap(type, context);
		return new ArrayList<>(context.values());
	}

	public static void buildApiParamMap(Type type, Map<Type, Param> context) {
		if (null == type)
			return;

		if (context.containsKey(type))
			return;

		if (context.size() > 0 && ClassUtil.isSimpaleType(type))
			return;

		if (type instanceof TypeVariable) {
			return;
		}

		Param apiParam = new Param(type);
		context.put(type, apiParam);

		if (ClassUtil.isSimpaleType(type))
			return;

		if (ClassUtil.isArray(type)) {
			ParamBuilder.buildApiParamMap(ClassUtil.getArrayType(type), context);
			return;
		}

		if (type instanceof ParameterizedType) {
			ParamBuilder.buildApiParamMap(ClassUtil.getActualType(type), context);
			ParameterizedType parameterizedType = (ParameterizedType) type;
			Type[] types = parameterizedType.getActualTypeArguments();
			for (int i = 0; types != null && i < types.length; i++) {
				Type typeItem = types[i];
				ParamBuilder.buildApiParamMap(typeItem, context);
			}
			type = parameterizedType.getRawType();
			if (type instanceof List)
				return;
		}

		if (!(type instanceof Class))
			return;

		List<Option> options = new ArrayList<>();
		Map<String, Field> fieldMap = ObjectUtil.buildXmlNameWithFieldMap((Class<?>) type);
		for (Map.Entry<String, Field> entry : fieldMap.entrySet()) {
			options.add(new Option(entry.getKey(), entry.getValue()));
			Type subType = entry.getValue().getGenericType();
			if (ClassUtil.isSimpaleType(subType))
				continue;
			if (subType instanceof ParameterizedType)
				subType = ClassUtil.getActualType(subType);
			else if (ClassUtil.isArray(subType)) {
				subType = ClassUtil.getArrayType(subType);
			}
			ParamBuilder.buildApiParamMap(subType, context);
		}
		apiParam.setOptions(options);

	}
}
