package common.core.common.util;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.BeanUtils;

public class ObjectArrayUtil {
	/**
	 * 根据指定数据位置重新组合数组
	 * 
	 * @param objsList
	 * @param itemIndexs
	 * @return
	 */
	public static List<Object[]> buildObjectArrayList(List<Object[]> objsList, int[] itemIndexs) {
		List<Object[]> list = new ArrayList<Object[]>();
		for (Object[] objects : objsList) {
			Object[] objs = new Object[itemIndexs.length];
			for (int i = 0; i < objs.length; i++) {
				objs[i] = objects[itemIndexs[i]];
			}
			list.add(objs);
		}
		return list;
	}

	/**
	 * 根据指定对象属性重新组合数组
	 * 
	 * @param objList
	 * @param propertyNames
	 * @return
	 */
	public static List<Object[]> buildObjectArrayList(List<Object> objList, String[] propertyNames) {
		List<Object[]> list = new ArrayList<Object[]>();
		for (Object obj : objList) {
			Object[] objs = new Object[propertyNames.length];
			for (int i = 0; i < objs.length; i++) {
				try {
					objs[i] = PropertyUtils.getProperty(obj, propertyNames[i]);
				} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
					throw new RuntimeException(e);
				}
			}
			list.add(objs);
		}
		return list;
	}

	/**
	 * list复制
	 * 
	 * @param sourceList
	 * @param targetList
	 * @return
	 */
	public static <T> List<T> listCopy(List<?> sourceList, Class<T> target) {
		if (sourceList == null || sourceList.isEmpty()) {
			return null;
		}
		List<T> targetList = new ArrayList<T>(sourceList.size());
		try {
			for (int i = 0; i < sourceList.size(); i++) {
				T targetObj = target.newInstance();
				BeanUtils.copyProperties(sourceList.get(i), targetObj);
				targetList.add(targetObj);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return targetList;
	}
}
