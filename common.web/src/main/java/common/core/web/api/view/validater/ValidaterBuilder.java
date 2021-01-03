package common.core.web.api.view.validater;

import java.util.ArrayList;
import java.util.List;

public class ValidaterBuilder {

	public static List<Validater> buildValidate(Validater validater){
		List<Validater> validaters=new ArrayList<Validater>();
		validaters.add(validater);
		return validaters;
	}
}
