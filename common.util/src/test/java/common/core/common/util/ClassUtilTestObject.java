package common.core.common.util;

import javax.xml.bind.annotation.XmlElement;

public class ClassUtilTestObject extends ObjectUtilTestBaseObject implements ClassUtilInterface {

	public static String STATIC_VAR = "STATIC_VAR";

	@ClassUtilTestObjectAnnotation
	private String name;
	@XmlElement(name = "Email")
	private String email;

	public String getName() {
		return name;
	}

	@ClassUtilTestObjectAnnotation
	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see common.core.common.util.ClassUtilInterface#interfaceAnnotation()
	 */
	@Override
	public void interfaceAnnotation() {

	}
}
