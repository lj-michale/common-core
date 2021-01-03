package common.core.common.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import common.core.app.log.Logger;
import common.core.app.log.LoggerFactory;
import common.core.common.exception.RuntimeIOException;
import common.core.common.util.IoUtil;
import common.core.common.util.StringUtil;

public class FtpHelper {
	private static Logger logger = LoggerFactory.getLogger(FtpHelper.class);

	public static void upload(FtpConfig ftpConfig, InputStream inputStream, String fileName) {
		logger.debug("ftp upload fileName: {}", fileName);

		FTPClient ftp = openFtp(ftpConfig);
		try {
			String filePath = getFilePath(fileName);
			if (StringUtil.hasText(filePath)) {
				changeWorkingDirectory(ftp, filePath);
				fileName = fileName.substring(filePath.length());
				logger.debug("get path! path: {},name: {}", filePath, fileName);
			}
			upload(ftp, inputStream, fileName);
		} catch (IOException e) {
			closeFtp(ftp);
		} finally {
			IoUtil.close(inputStream);
			closeFtp(ftp);
		}
	}

	private static void upload(FTPClient ftp, InputStream inputStream, String fileName) throws IOException {
		logger.debug("ftp storeFile start: {} ", fileName);
		ftp.storeFile(fileName, inputStream);
		logger.debug("ftp storeFile end: {} ", fileName);
		logger.debug("ftp logout");
		ftp.logout();
	}

	private static List<FtpFile> list(FTPClient ftp) throws IOException {
		List<FtpFile> ftpFiles = new ArrayList<FtpFile>();
		FTPFile[] files = ftp.listFiles();
		for (FTPFile ftpFile : files) {
			FtpFile file = new FtpFile();
			file.setName(ftpFile.getName());
			file.setPath(ftpFile.getLink());
			file.setSize(ftpFile.getSize());
			file.setLastModificationTime(ftpFile.getTimestamp().getTime());
			String[] types = new String[] { FtpFile.FILE_TYPE, FtpFile.DIRECTORY_TYPE, FtpFile.SYMBOLIC_LINK_TYPE, FtpFile.UNKNOWN_TYPE };
			file.setType(types[ftpFile.getType()]);
			ftpFiles.add(file);
		}
		ftp.logout();
		return ftpFiles;
	}

	public static List<FtpFile> list(FtpConfig ftpConfig, String filePath) {
		FTPClient ftp = openFtp(ftpConfig);
		try {
			if (StringUtil.hasText(filePath))
				changeWorkingDirectory(ftp, filePath);
			return list(ftp);
		} catch (IOException e) {
			closeFtp(ftp);
		} finally {
			closeFtp(ftp);
		}
		return null;
	}

	public static void upload(FtpConfig ftpConfig, InputStream inputStream, String fileName, String filePath) {
		FTPClient ftp = openFtp(ftpConfig);
		try {
			changeWorkingDirectory(ftp, filePath);
			upload(ftp, inputStream, fileName);
		} catch (IOException e) {
			closeFtp(ftp);
		} finally {
			IoUtil.close(inputStream);
			closeFtp(ftp);
		}
	}

	public static void download(FtpConfig ftpConfig, OutputStream outputStream, String fileName) {
		logger.debug("ftp download fileName: {}", fileName);

		FTPClient ftp = openFtp(ftpConfig);
		try {
			String filePath = getFilePath(fileName);
			if (StringUtil.hasText(filePath)) {
				changeWorkingDirectory(ftp, filePath);
				fileName = fileName.substring(filePath.length());
				logger.debug("get path! path: {},name: {}", filePath, fileName);
			}
			logger.debug("ftp retrieveFile stat {}", fileName);
			ftp.retrieveFile(fileName, outputStream);
			logger.debug("ftp retrieveFile stat {}", fileName);
		} catch (IOException e) {
			closeFtp(ftp);
		} finally {
			IoUtil.close(outputStream);
			closeFtp(ftp);
		}
	}

	public static String getFilePath(String fileName) {
		int index = fileName.lastIndexOf("/");
		if (index < 0) {
			return null;
		}
		return fileName.substring(0, index + 1);
	}

	private static void changeWorkingDirectory(FTPClient ftp, String filePath) throws IOException {
		logger.debug("ftp changeWorkingDirectory  {}", filePath);

		if (filePath.startsWith("/")) {
			filePath = filePath.substring(1);
		}
		if (StringUtil.isEmpty(filePath))
			return;

		int dirIndex = filePath.indexOf("/") > 0 ? filePath.indexOf("/") : filePath.length();
		String dir = filePath.substring(0, dirIndex);

		boolean ret = false;
		ret = ftp.makeDirectory(dir);
		logger.debug("ftp make makeDirectory ret={}, {}", ret, dir);
		ret = ftp.changeWorkingDirectory(dir);
		logger.debug("ftp make changeWorkingDirectory ret={}, {}", ret, dir);
		logger.debug("ftp printWorkingDirectory  {}", ftp.printWorkingDirectory());

		filePath = filePath.substring(dirIndex);
		changeWorkingDirectory(ftp, filePath);
	}

	private static void closeFtp(FTPClient ftp) {
		if (ftp != null && ftp.isConnected()) {
			try {
				logger.debug("ftp disconnect");
				ftp.disconnect();
			} catch (IOException ioe) {
				logger.warn(ioe.getMessage(), ioe);
			}
		}
	}

	public static FTPClient openFtp(FtpConfig ftpConfig) {

		FTPClient ftp = new FTPClient();
		try {
			logger.debug("ftp connet : {} {}", ftpConfig.getServer(), ftpConfig.getPort());
			ftp.connect(ftpConfig.getServer(), ftpConfig.getPort());
			logger.debug("ftp login : {} ", ftpConfig.getUser());
			ftp.login(ftpConfig.getUser(), ftpConfig.getPassword());
			ftp.setBufferSize(ftpConfig.getBufferSize());
			ftp.setConnectTimeout(ftpConfig.getConnectTimeout());
			ftp.setControlEncoding(ftpConfig.getControlEncoding());
			ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
			int reply = ftp.getReplyCode();
			logger.debug("ftp getReplyCode:{}", reply);
			if (!FTPReply.isPositiveCompletion(reply)) {
				closeFtp(ftp);
				throw new RuntimeIOException("ftp getReplyCode with " + reply);
			}
			changeWorkingDirectory(ftp, ftpConfig.getPath());
		} catch (IOException e) {
			throw new RuntimeIOException(e);
		}
		return ftp;
	}

	public static void main(String[] args) throws FileNotFoundException {
		FtpConfig ftpConfig = new FtpConfig();
		ftpConfig.setServer("172.29.12.224");
		ftpConfig.setPort(21);
		ftpConfig.setUser("o2odev");
		ftpConfig.setPassword("xd135dev");
		ftpConfig.setServer("127.0.0.1");
		ftpConfig.setPort(21);
		ftpConfig.setUser("ftp");
		ftpConfig.setPassword("ftp");
		ftpConfig.setPath("/0");
		ftpConfig.setBufferSize(1024);
		ftpConfig.setConnectTimeout(10 * 1000);
		ftpConfig.setControlEncoding("GBK");
		File file = new File("E:/src/eascs/eascs-common/eascs.net/src/main/java/com/eascs/common/ftp/FtpHelper2.java");
		FtpHelper.upload(ftpConfig, new FileInputStream(file), file.getName(), "p2/p3/");
		FtpHelper.upload(ftpConfig, new FileInputStream(file), "p5/p6/p1.java");
		FtpHelper.upload(ftpConfig, new FileInputStream(file), "/p7/p8/p2.java");
		FtpHelper.download(ftpConfig, new FileOutputStream("F:/ftp/d/d.java"), "/p7/p8/p2.java");
	}
}
