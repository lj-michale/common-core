package common.core.app.dao;

import java.util.Date;

import javax.persistence.Column;

public class TestEntity extends BaseEntity{

	protected static final TestEntity ME = new TestEntity();

	private String name;

	@Column(name = "BIRTHDATE")
	private Date birthDate;

	private boolean isVip;

	private String gender;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public boolean isVip() {
		return isVip;
	}

	public void setVip(boolean isVip) {
		this.isVip = isVip;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

}
