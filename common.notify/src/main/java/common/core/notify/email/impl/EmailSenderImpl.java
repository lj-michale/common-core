package common.core.notify.email.impl;

import common.core.app.log.Logger;
import common.core.app.log.LoggerFactory;
import common.core.notify.email.EmailSender;
import common.core.notify.email.entity.EmailContentEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

@Service
public class EmailSenderImpl implements EmailSender {
	private final Logger logger = LoggerFactory.getLogger(EmailSenderImpl.class);

	@Override
	public boolean send(EmailContentEntity entity) {
		Transport transport = null;
		try {
			logger.info("start send mail...");

			if (StringUtils.isEmpty(entity.getHost()) || StringUtils.isEmpty(entity.getUsername()) || StringUtils.isEmpty(entity.getPassword()) || StringUtils.isEmpty(entity.getFrom()) || StringUtils.isEmpty(entity.getTo())) {
				logger.info("send mail,prameter error...");
				return false;
			}

			// 构造mail session
			Properties props = new Properties();
			props.put("mail.smtp.host", entity.getHost());
			props.put("mail.smtp.auth", "true");
			Session session = Session.getDefaultInstance(props, new Authenticator() {
				public PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(entity.getUsername(), entity.getPassword());
				}
			});

			// 构造MimeMessage 并设定基本的值
			MimeMessage msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(entity.getFrom()));
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(entity.getTo()));
			msg.setSubject(transferChinese(entity.getSubject()));

			// 构造Multipart
			Multipart mp = new MimeMultipart();
			// 向Multipart添加正文
			MimeBodyPart mbpContent = new MimeBodyPart();
			mbpContent.setContent(entity.getContent(), "text/html;charset=UTF-8");
			// 向MimeMessage添加（Multipart代表正文）
			mp.addBodyPart(mbpContent);

			if (null != entity.getAttachmentDirectories()) {
				for (String directory : entity.getAttachmentDirectories()) {
					logger.info("send mail with dir {}", directory);
					File[] files = new File(directory).listFiles();
					List<String> fileNames = new ArrayList<>();
					for (int i = 0; null != files && i < files.length; i++) {
						File file = files[i];
						if (file.isFile())
							fileNames.add(file.getAbsolutePath());
					}
					attachmentFiles(mp, fileNames);
				}
			}

			// 向Multipart添加MimeMessage
			msg.setContent(mp);
			msg.setSentDate(new Date());
			msg.saveChanges();

			// 发送邮件
			transport = session.getTransport("smtp");
			transport.connect(entity.getHost(), entity.getUsername(), entity.getPassword());
			transport.sendMessage(msg, msg.getAllRecipients());

			logger.info("send mail success...");
		} catch (MessagingException | UnsupportedEncodingException e) {
			logger.error("send mail error...", e);
			return false;
		} finally {
			closeTransport(transport);
		}
		return true;
	}

	private void closeTransport(Transport transport) {
		if (transport != null) {
			try {
				transport.close();
			} catch (MessagingException e) {
				logger.error("send mail error...", e);
			}
		}
	}

	private void attachmentFiles(Multipart mp, List<String> attches) throws MessagingException, UnsupportedEncodingException {
		if (attches != null && !attches.isEmpty()) {
			for (String attach : attches) {
				logger.info("set email attche: {}", attach);
				MimeBodyPart mbpFile = new MimeBodyPart();
				FileDataSource fds = new FileDataSource(attach);
				DataHandler dh2 = new DataHandler(fds);
				mbpFile.setDataHandler(dh2);
				mbpFile.setFileName(transferChinese(fds.getName()));
				mp.addBodyPart(mbpFile);
			}
		}
	}

	private String transferChinese(String str) throws UnsupportedEncodingException {
		return MimeUtility.encodeWord(str);
	}
}