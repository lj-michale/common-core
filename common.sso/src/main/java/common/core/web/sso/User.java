package common.core.web.sso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User implements java.io.Serializable {
	private static final long serialVersionUID = -8773073180277889860L;
	public final static String SESSION_USER_NAME = User.class.getName();

	private String id;
	private String no;
	private String code;
	private java.lang.String account;
	private java.lang.String cname;
	private java.lang.String email;
	private java.lang.String mobile;
	private java.lang.String qq;
	private String phone;
	private boolean superAdministrator;
	private boolean closeAuth; // 是否有关闭权限
	private String org;// 人员省区--员工表的province_code
	private String status;// 员工状态:试用PRO,转正 NOR,离职DMS
	private String nodenamecn;// 部门

	private List<MenuItem> menuItemTree = new ArrayList<MenuItem>();
	private Map<String, Boolean> permissionCodeMap = new HashMap<>();
	private Map<String, Boolean> permissionUrlMap = new HashMap<>();
	// 角色Code List
	private List<String> roleCodeList = new ArrayList<String>();

	/** 数据权限字符串 */
	private String dataPermissionStr;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public java.lang.String getAccount() {
		return account;
	}

	public void setAccount(java.lang.String account) {
		this.account = account;
	}

	public java.lang.String getCname() {
		return cname;
	}

	public void setCname(java.lang.String cname) {
		this.cname = cname;
	}

	public java.lang.String getEmail() {
		return email;
	}

	public void setEmail(java.lang.String email) {
		this.email = email;
	}

	public java.lang.String getMobile() {
		return mobile;
	}

	public void setMobile(java.lang.String mobile) {
		this.mobile = mobile;
	}

	public java.lang.String getQq() {
		return qq;
	}

	public void setQq(java.lang.String qq) {
		this.qq = qq;
	}

	public boolean isSuperAdministrator() {
		return superAdministrator;
	}

	public void setSuperAdministrator(boolean superAdministrator) {
		this.superAdministrator = superAdministrator;
	}

	public List<MenuItem> getMenuItemTree() {
		return menuItemTree;
	}

	public void setMenuItemTree(List<MenuItem> menuItemTree) {
		this.menuItemTree = menuItemTree;
	}

	public Map<String, Boolean> getPermissionCodeMap() {
		return permissionCodeMap;
	}

	public void setPermissionCodeMap(Map<String, Boolean> permissionCodeMap) {
		this.permissionCodeMap = permissionCodeMap;
	}

	public Map<String, Boolean> getPermissionUrlMap() {
		return permissionUrlMap;
	}

	public void setPermissionUrlMap(Map<String, Boolean> permissionUrlMap) {
		this.permissionUrlMap = permissionUrlMap;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public boolean isCloseAuth() {
		return closeAuth;
	}

	public void setCloseAuth(boolean closeAuth) {
		this.closeAuth = closeAuth;
	}

	public String getOrg() {
		return org;
	}

	public void setOrg(String org) {
		this.org = org;
	}

	public String getDataPermissionStr() {
		return dataPermissionStr;
	}

	public void setDataPermissionStr(String dataPermissionStr) {
		this.dataPermissionStr = dataPermissionStr;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getNodenamecn() {
		return nodenamecn;
	}

	public void setNodenamecn(String nodenamecn) {
		this.nodenamecn = nodenamecn;
	}

	public List<String> getRoleCodeList() {
		return roleCodeList;
	}

	public void setRoleCodeList(List<String> roleCodeList) {
		this.roleCodeList = roleCodeList;
	}
}
