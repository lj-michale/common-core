package common.core.common.mail;

import common.core.app.context.ConfigContext;

import junit.framework.Assert;

public class MailHelperTest {

//	@Test
	public void getMailSubject() {
		MailConfig mailConfig = new MailConfig();
		ConfigContext.bindConfigObject(mailConfig);
		MailSubject mailSubject = null;
		long start = System.currentTimeMillis();
		mailSubject = MailHelper.getMailSubjectBy(mailConfig, "<20160813091130.17090.qmail@quickmail.51job.com>");
		Assert.assertNotNull(mailSubject);
		System.out.println(mailSubject.toString());
		System.out.println(System.currentTimeMillis() - start);
		mailSubject = MailHelper.findMailSubjectById(mailConfig, "<20160813091130.17090.qmail@quickmail.51job.com>");
		Assert.assertNotNull(mailSubject);
		System.out.println(mailSubject.toString());
		System.out.println(System.currentTimeMillis() - start);
		mailSubject = MailHelper.findMailBodyById(mailConfig, "<20160813091130.17090.qmail@quickmail.51job.com>");
		Assert.assertNotNull(mailSubject);
		System.out.println(mailSubject.toString());
		System.out.println(System.currentTimeMillis() - start);

	}
}
