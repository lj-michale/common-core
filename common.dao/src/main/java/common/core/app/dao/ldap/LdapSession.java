package common.core.app.dao.ldap;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import common.core.app.log.Logger;
import common.core.app.log.LoggerFactory;
import common.core.common.util.ObjectUtil;

public class LdapSession {
	private final Logger logger = LoggerFactory.getLogger(LdapSession.class);
	private LdapSetting ldapSetting;

	public LdapSession(LdapSetting ldapSetting) {
		this.ldapSetting = ldapSetting;
	}

	public DirContext buildDirContext() {
		Hashtable<String, String> env = new Hashtable<>();
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, ldapSetting.getProviderUrl());
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		env.put(Context.SECURITY_PRINCIPAL, ldapSetting.getSecurityPrincipal());
		env.put(Context.SECURITY_CREDENTIALS, ldapSetting.getSecurityCredentials());

		try {
			// 链接ldap
			return new InitialDirContext(env);
		} catch (NamingException e) {
			throw new RuntimeException(e);
		}
	}

	public <T> T findEntity(Class<T> entityClass, String filter) {
		List<T> result = this.findEntitys(entityClass, filter);
		if (null == result || result.isEmpty())
			return null;
		return result.get(0);
	}

	public <T> List<T> findEntitys(Class<T> entityClass, String filter) {
		List<T> result = new ArrayList<>();
		DirContext dirContext = buildDirContext();
		try {
			SearchControls constraints = new SearchControls();
			constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);
			// constraints.setSearchScope(SearchControls.ONELEVEL_SCOPE);
			NamingEnumeration<SearchResult> en = dirContext.search("", filter, constraints); // 查询所有用户
			while (en != null && en.hasMoreElements()) {
				Object obj = en.nextElement();
				if (obj instanceof SearchResult) {
					SearchResult si = (SearchResult) obj;
					logger.debug("name:{}", si.getName());
					Attributes attrs = si.getAttributes();
					if (attrs == null) {
						logger.debug("No   attributes");
					} else {
						NamingEnumeration<String> ids = attrs.getIDs();
						Map<String, String> dataMap = new HashMap<>();
						while (ids.hasMoreElements()) {
							String attrID = (String) ids.nextElement();
							Object value = attrs.get(attrID).get();
							if (value == null)
								continue;
							dataMap.put(attrID, value.toString());
						}
						result.add(ObjectUtil.fromStringMap(entityClass, dataMap));
					}

				}
			}
		} catch (NamingException ex) {
			throw new RuntimeException(ex);
		} finally {
			closeDirContext(dirContext);
		}
		return result;
	}

	public boolean verifySHA(String ldappw, String inputpw) {
		// MessageDigest提供了消息摘要算法，如MD5或SHA，的功能，这里LDAP使用的是SHA-1
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}

		// 取出加密字符
		if (ldappw.startsWith("{SSHA}")) {
			ldappw = ldappw.substring(6);
		} else if (ldappw.startsWith("{SHA}")) {
			ldappw = ldappw.substring(5);
		}

		// 解码BASE64
		byte[] ldappwbyte = Base64.getDecoder().decode(ldappw);
		byte[] shacode;
		byte[] salt;

		// 前20位是SHA-1加密段，20位后是最初加密时的随机明文
		if (ldappwbyte.length <= 20) {
			shacode = ldappwbyte;
			salt = new byte[0];
		} else {
			shacode = new byte[20];
			salt = new byte[ldappwbyte.length - 20];
			System.arraycopy(ldappwbyte, 0, shacode, 0, 20);
			System.arraycopy(ldappwbyte, 20, salt, 0, salt.length);
		}

		// 把用户输入的密码添加到摘要计算信息
		md.update(inputpw.getBytes());
		// 把随机明文添加到摘要计算信息
		md.update(salt);

		// 按SSHA把当前用户密码进行计算
		byte[] inputpwbyte = md.digest();

		// 返回校验结果
		return MessageDigest.isEqual(shacode, inputpwbyte);
	}

	public boolean authenticate(String userAccount, String userPassword) {
		DirContext dirContext = buildDirContext();
		try {
			SearchControls constraints = new SearchControls();
			constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);
			// constraints.setSearchScope(SearchControls.ONELEVEL_SCOPE);
			NamingEnumeration<SearchResult> en = dirContext.search("", "cn=" + userAccount, constraints); // 查询所有用户
			while (en != null && en.hasMoreElements()) {
				Object obj = en.nextElement();
				if (obj instanceof SearchResult) {
					SearchResult si = (SearchResult) obj;
					logger.debug("name:{}", si.getName());
					Attributes attrs = si.getAttributes();
					if (attrs == null) {
						logger.debug("No   attributes");
					} else {
						Attribute attr = attrs.get("userPassword");
						Object o = attr.get();
						byte[] s = (byte[]) o;
						String password = new String(s);
						return this.verifySHA(password, userPassword);
					}
				} else {
					logger.debug("not SearchResult,{}", obj);
				}
			}
		} catch (NamingException ex) {
			throw new RuntimeException(ex);
		} finally {
			closeDirContext(dirContext);
		}
		return false;
	}

	private void closeDirContext(DirContext dirContext) {
		try {
			if (dirContext != null) {
				dirContext.close();
			}
		} catch (NamingException namingException) {
			logger.warn("close DirContext exception", namingException);
		}
	}

}
