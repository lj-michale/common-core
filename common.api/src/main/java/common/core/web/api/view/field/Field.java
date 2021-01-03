package common.core.web.api.view.field;

import java.util.Arrays;

public class Field {

	/**
	 * 字段的值
	 */
	private String value;

	/**
	 * 字段的类似
	 */
	private String type = FieldTypes.STRING;

	/**
	 * 字段的扩展属性
	 */
	private String attribute;
	
	/*
	 * 联动隐藏表单
	 */
	private String[] linkageCode;
	/*
	 * 内审终端贷----增加9%得率
	 */
	private String nineRate;
	

	public String getValue() {
		return value;
	}

	public Field setValue(String value) {
		this.value = value;
		return this;
	}

	public String getType() {
		return type;
	}

	public Field setType(String type) {
		this.type = type;
		return this;
	}
    
	public String[] getLinkageCode() {
		return linkageCode;
	}

	public Field setLinkageCode(String[] linkageCode) {
		this.linkageCode = linkageCode;
		return this;
	}
    
	
	public String getNineRate() {
		return nineRate;
	}

	public Field setNineRate(String nineRate) {
		this.nineRate = nineRate;
		return this;
	}

	public Field() {
		super();
	}

	public Field(String value, String type) {
		super();
		this.value = value;
		this.type = type;
	}

	public Field(String type) {
		super();
		this.type = type;
	}

	public String getAttribute() {
		return attribute;
	}
	public Field setAttribute(String attribute) {
		this.attribute = attribute;
		return this;
	}

	public Field addAttribute(String name, String value) {
		if (null == name || "".equals(name.trim()) || null == value || "".equals(value.trim())) {
			return this;
		}
		if (this.attribute == null) {
			this.attribute = "";
		}
		if (this.attribute.length() > 0 && !this.attribute.endsWith(";")) {
			this.attribute += ";";
		}
		this.attribute += name + "=" + value;
		return this;
	}

	@Override
	public String toString() {
		return "Field [value=" + value + ", type=" + type + ", attribute=" + attribute + ", linkageCode="
				+ Arrays.toString(linkageCode) + ", nineRate=" + nineRate + "]";
	}

	
}
