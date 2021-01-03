package common.core.web.api.view.api.method;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import common.core.web.api.ApiMethodInvokeObject;
import common.core.web.api.view.method.InvokeObjectSimpleView;

public class ApiMethodInvokeObjectSimpleViewContext {
	public static List<InvokeObjectSimpleView> listApiMethodInvokeObjectView() {
		List<InvokeObjectSimpleView> result = new ArrayList<>();
		Map<String, ApiMethodInvokeObject> methodInvokeObjectMap = ApiMethodInvokeObjectContext.getMethodInvokeObjectMap();
		for (Entry<String, ApiMethodInvokeObject> item : methodInvokeObjectMap.entrySet()) {
			result.add(new InvokeObjectSimpleView(item.getKey(), item.getValue().getMethodAnnotation().description(),item.getValue().getMethodAnnotation().group(), item.getValue().getMethod()));
		}
		result.sort(new Comparator<InvokeObjectSimpleView>() {
			@Override
			public int compare(InvokeObjectSimpleView o1, InvokeObjectSimpleView o2) {
				return o1.getGroup().compareTo(o2.getGroup());
			}
		});
		return result;
	}
}
