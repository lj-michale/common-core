package common.core.app.dao;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "my_table_name")
public class UpdateTestEntity extends BaseEntity {

	protected static final UpdateTestEntity ME = new UpdateTestEntity();

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
