package common.core.web.api.view.method;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import common.core.common.util.ClassUtil;
import common.core.site.view.BaseRestResult;

public class ApiOptionBuilderTest {

	List<Option> apiOptions;

	public Option test1(Option apiOption) {
		return apiOption;
	}

	public Option[] test2(Option[] apiOptions) {
		return apiOptions;
	}

	public List<Option> test3(List<Option> apiOptions) {
		return apiOptions;
	}

	public List<Object> test4(List<Object> apiOptions) {
		return apiOptions;
	}

	public Object test5(Object apiOptions) {
		return apiOptions;
	}

	public int test6(int apiOption) {
		return apiOption;
	}

	public BaseRestResult<String> test7() {
		return BaseRestResult.buildSuccessResult();
	}

	public BaseRestResult<ApiOptionBuilderTest> test8() {
		return null;
	}

	public ApiOptionBuilderTest test9() {
		return null;
	}

	@Test
	public void buildOptions() {
		Assert.assertTrue(List.class.isAssignableFrom(this.getField(ApiOptionBuilderTest.class, "apiOptions").getType()));
		Assert.assertTrue(Collection.class.isAssignableFrom(this.getField(ApiOptionBuilderTest.class, "apiOptions").getType()));
		Assert.assertTrue(java.sql.Date.class.isAssignableFrom(java.sql.Date.class));
		Assert.assertTrue(java.util.Date.class.isAssignableFrom(java.sql.Date.class));
		Assert.assertEquals(Integer.TYPE, this.getParamType(ApiOptionBuilderTest.class, "test6"));
		Assert.assertEquals(Integer.TYPE, this.getReturnType(ApiOptionBuilderTest.class, "test6"));
		Assert.assertEquals(Integer.TYPE, this.getGenericReturnType(ApiOptionBuilderTest.class, "test6"));
		Assert.assertEquals(Object.class, this.getParamType(ApiOptionBuilderTest.class, "test5"));
		Assert.assertEquals(Object.class, this.getReturnType(ApiOptionBuilderTest.class, "test5"));
		Assert.assertEquals(Object.class, this.getGenericReturnType(ApiOptionBuilderTest.class, "test5"));
		Assert.assertEquals(this.getReturnType(ApiOptionBuilderTest.class, "test5"), this.getGenericReturnType(ApiOptionBuilderTest.class, "test5"));
		Assert.assertEquals(Option.class, ClassUtil.getActualType(this.getGenericReturnType(ApiOptionBuilderTest.class, "test3")));
		Assert.assertEquals(Option[].class, this.getParamType(ApiOptionBuilderTest.class, "test2"));
		Assert.assertEquals(Option[].class, this.getReturnType(ApiOptionBuilderTest.class, "test2"));
		Assert.assertEquals(Option[].class, this.getGenericReturnType(ApiOptionBuilderTest.class, "test2"));
		Assert.assertEquals(Option.class, ClassUtil.getArrayType(this.getReturnType(ApiOptionBuilderTest.class, "test2")));
		Method test6 = this.getMethod(ApiOptionBuilderTest.class, "test6");
		Assert.assertEquals(1, ParamBuilder.buildApiParams(test6.getGenericReturnType()).size());
		Assert.assertEquals(1, ParamBuilder.buildApiParams(test6.getGenericParameterTypes()[0]).size());
		Method test1 = this.getMethod(ApiOptionBuilderTest.class, "test1");
		Assert.assertEquals(1, ParamBuilder.buildApiParams(test1.getGenericReturnType()).size());
		Assert.assertEquals(1, ParamBuilder.buildApiParams(test1.getGenericParameterTypes()[0]).size());
		Method test2 = this.getMethod(ApiOptionBuilderTest.class, "test2");
		Assert.assertEquals(2, ParamBuilder.buildApiParams(test2.getGenericReturnType()).size());
		Assert.assertEquals(2, ParamBuilder.buildApiParams(test2.getGenericParameterTypes()[0]).size());
		Method test3 = this.getMethod(ApiOptionBuilderTest.class, "test3");
		Assert.assertEquals(2, ParamBuilder.buildApiParams(test3.getGenericReturnType()).size());
		Assert.assertEquals(2, ParamBuilder.buildApiParams(test3.getGenericParameterTypes()[0]).size());
		Assert.assertEquals(2, ParamBuilder.buildApiParams(test3.getGenericParameterTypes()[0]).size());
		Assert.assertEquals(ClassA.class, ClassUtil.getGenericTypeFromListField(getField(ClassB.class, "classAList")));
		Assert.assertEquals(1, ParamBuilder.buildApiParams(ClassA.class).size());
		Assert.assertEquals(2, ParamBuilder.buildApiParams(ClassB.class).size());
		Assert.assertEquals(2, ParamBuilder.buildApiParams(ClassC.class).size());
		Assert.assertEquals(1, ParamBuilder.buildApiParams(ClassD.class).size());
		Method test9 = this.getMethod(ApiOptionBuilderTest.class, "test9");
		Assert.assertEquals(2, ParamBuilder.buildApiParams(test9.getGenericReturnType()).size());
		Method test7 = this.getMethod(ApiOptionBuilderTest.class, "test7");
		Assert.assertEquals(1, ParamBuilder.buildApiParams(test7.getGenericReturnType()).size());

	}

	public Field getField(Class<?> clz, String fieldName) {
		try {
			return clz.getDeclaredField(fieldName);
		} catch (NoSuchFieldException e) {
			throw new RuntimeException(e);
		}
	}

	public Method getMethod(Class<?> clz, String method) {
		try {
			Method[] methods = clz.getMethods();
			for (Method item : methods) {
				if (method.equals(item.getName()))
					return item;
			}
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		}

		return null;
	}

	public Class<?> getArrayType(Class<?> clz) {
		if (!clz.isArray())
			return null;
		return clz.getComponentType();
	}

	public Class<?> getParamType(Class<?> clz, String method) {
		Method m = this.getMethod(clz, method);
		return m.getParameterTypes()[0];
	}

	public Class<?> getReturnType(Class<?> clz, String method) {
		Method m = this.getMethod(clz, method);
		return m.getReturnType();
	}

	public Type getGenericReturnType(Class<?> clz, String method) {
		Method m = this.getMethod(clz, method);
		return m.getGenericReturnType();
	}

	static class ClassA {
		private String id;
		private String name;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

	}

	static class ClassB {
		private List<ClassA> classAList;

		public List<ClassA> getClassAList() {
			return classAList;
		}

		public void setClassAList(List<ClassA> classAList) {
			this.classAList = classAList;
		}

	}

	static class ClassC {
		private ClassA[] classAs;

		public ClassA[] getClassAs() {
			return classAs;
		}

		public void setClassAs(ClassA[] classAs) {
			this.classAs = classAs;
		}

	}

	static class ClassD {
		private List<ClassD> classDList;

		public List<ClassD> getClassDList() {
			return classDList;
		}

		public void setClassDList(List<ClassD> classDList) {
			this.classDList = classDList;
		}

	}

}
