package common.core.web.api;

import org.hibernate.validator.constraints.NotEmpty;
import org.junit.Test;

import common.core.app.exception.FieldValidationException;

public class BaseApiControllerTest {

	static class User {
		@NotEmpty
		String id;
		String name;

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

	@Test(expected = FieldValidationException.class)
	public void validateParameterFieldValidationException() {
		User user = new User();
		BaseApiController baseApiController = new BaseApiController();
		baseApiController.validateParameter(user);
	}

	@Test()
	public void validateParameter() {
		User user = new User();
		user.setId("id");
		BaseApiController baseApiController = new BaseApiController();
		baseApiController.validateParameter(user);
	}
}
