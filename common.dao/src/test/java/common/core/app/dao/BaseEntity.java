package common.core.app.dao;

import javax.persistence.Column;
import javax.persistence.Id;

public class BaseEntity {

	@Id
	@Column(name = "id")
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
