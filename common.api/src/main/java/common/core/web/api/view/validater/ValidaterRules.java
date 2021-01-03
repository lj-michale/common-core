package common.core.web.api.view.validater;

public class ValidaterRules {

	/**
	 * "请输入内容"
	 */
	public final static String REQUIRED = "required";
	/**
	 * "请输入EMAIL."
	 */
	public final static String EMAIL = "email";
	/**
	 * "请输入URL."
	 */
	public final static String URL = "url";
	/**
	 * "请输入日期."
	 */
	public final static String DATE = "date";
	/**
	 * "请输入数字"
	 */
	public final static String NUMBER = "number";
	/**
	 * "请输入数字（正数）."
	 */
	public final static String DIGITS = "digits";
	/**
	 * "请输入相同的值"
	 */
	public final static String EQUALTO = "equalTo";
	/**
	 * "请输入至多{0}个字符"
	 */
	public final static String MAXLENGTH = "maxlength";
	/**
	 * "请输入至少{0}个字符"
	 */
	public final static String MINLENGTH = "minlength";
	/**
	 * "请输入不大于{0}的数"
	 */
	public final static String MAX = "max";
	/**
	 * "请输入不小于{0}的数"
	 */
	public final static String MIN = "min";
	/**
	 * "请输入合法的字符"
	 */
	public final static String REG = "reg";
	/**
	 * "请输入正确的身份证号码"
	 */
	public final static String IDNUMBER = "idNumber"; 
	/**
	 * "联系电话校验（只能是11位的数字）"
	 */
	public final static String PHONE = "phone"; 
	/**
	 * 姓名（是否是中文并且不超过五个字符）"
	 */
	public final static String NAME = "name"; 

}
