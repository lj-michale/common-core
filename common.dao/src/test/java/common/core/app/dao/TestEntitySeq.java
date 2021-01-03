package common.core.app.dao;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class TestEntitySeq {

	protected static final TestEntitySeq ME = new TestEntitySeq();

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Seq1")
	private String id;

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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

}
