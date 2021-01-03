package common.core.app.dao;

import java.lang.reflect.Field;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import common.core.app.context.RSABindConfig;
import common.core.app.dao.crypto.AesField;
import common.core.app.dao.crypto.AesFieldBuilder;
import common.core.app.dao.crypto.MixField;
import common.core.app.dao.crypto.MixFieldBuilder;

public class CryptoFieldBuilderTest {
	AesFieldBuilder aesFieldBuilder;
	MixFieldBuilder masterFieldBuilder;

	@Before
	public void init() {
		RSABindConfig rsaConfig = new RSABindConfig();
		aesFieldBuilder = new AesFieldBuilder(rsaConfig);
		masterFieldBuilder = new MixFieldBuilder(rsaConfig);
	}

	@Test
	public void aes() throws NoSuchFieldException, SecurityException {
		User user = this.buildUser();
		Field telField = user.getClass().getDeclaredField("tel");
		String tel = user.getTel();
		String value = (String) aesFieldBuilder.encode(user, telField, tel);
		Assert.assertNotSame(tel, value);
		value = (String) aesFieldBuilder.decode(user, telField, value);
		Assert.assertEquals(tel, value);
	}

	@Test
	public void marker() throws NoSuchFieldException, SecurityException {
		User user = this.buildUser();
		Field telField = user.getClass().getDeclaredField("pwd");
		String pwd = user.getPwd();
		String value = (String) masterFieldBuilder.encode(user, telField, pwd);
		Assert.assertNotSame(pwd, value);
		value = (String) masterFieldBuilder.decode(user, telField, value);
		Assert.assertNotSame(pwd, value);
	}

	private User buildUser() {
		User user = new User();
		user.setId("idvalue");
		user.setName("myname");
		user.setTel("18912345678");
		user.setPwd("mypassword");
		return user;
	}

	static class User {
		private String id;
		private String name;
		@AesField
		private String tel;
		@MixField(assemblyFields = { "id" })
		private String pwd;

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

		public String getTel() {
			return tel;
		}

		public void setTel(String tel) {
			this.tel = tel;
		}

		public String getPwd() {
			return pwd;
		}

		public void setPwd(String pwd) {
			this.pwd = pwd;
		}

	}

}
