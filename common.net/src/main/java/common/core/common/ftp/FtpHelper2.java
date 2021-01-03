package common.core.common.ftp;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.TimeZone;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import common.core.app.log.Logger;
import common.core.app.log.LoggerFactory;
import common.core.common.exception.RuntimeIOException;

public class FtpHelper2 {
	private static Logger logger = LoggerFactory.getLogger(FtpHelper2.class);

	public void upload(FtpConfig ftpConfig, InputStream input, String fileName) {
		FTPClient ftp = openFtp(ftpConfig);
		try {

		} finally {
			closeFtp(ftp);
		}
	}

	private void closeFtp(FTPClient ftp) {
		if (ftp != null && ftp.isConnected()) {
			try {
				ftp.disconnect();
			} catch (IOException ioe) {
				logger.warn(ioe.getMessage(), ioe);
			}
		}
	}

	public FTPClient openFtp(FtpConfig ftpConfig) {

		FTPClient ftp = new FTPClient();
		try {
			logger.debug("ftp connet : {} {}", ftpConfig.getServer(), ftpConfig.getPort());
			ftp.connect(ftpConfig.getServer(), ftpConfig.getPort());
			logger.debug("ftp login : {} ", ftpConfig.getUser());
			ftp.login(ftpConfig.getUser(), ftpConfig.getPassword());
			ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
			int reply = ftp.getReplyCode();
			logger.debug("ftp getReplyCode:{}", reply);
			if (!FTPReply.isPositiveCompletion(reply)) {
				closeFtp(ftp);
				throw new RuntimeIOException("ftp getReplyCode with " + reply);
			}
			ftp.makeDirectory(ftpConfig.getPath());
			ftp.changeWorkingDirectory(ftpConfig.getPath());
		} catch (IOException e) {
			throw new RuntimeIOException(e);
		} finally {
			closeFtp(ftp);
		}
		return ftp;
	}

	private FTPClient ftpClient;
	private FtpConfig ftpConfig;

	public FtpHelper2(FtpConfig ftpConfig) {
		super();
		this.ftpConfig = ftpConfig;
	}

	/**
	 * @return 判断是否登入成功
	 */
	public boolean login() {
		FTPClientConfig ftpClientConfig = new FTPClientConfig();
		ftpClientConfig.setServerTimeZoneId(TimeZone.getDefault().getID());
		this.ftpClient.setControlEncoding("GBK");
		this.ftpClient.configure(ftpClientConfig);
		try {
			this.ftpClient.connect(this.ftpConfig.getServer(), this.ftpConfig.getPort());
			// FTP服务器连接回答
			int reply = this.ftpClient.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				this.ftpClient.disconnect();
				logger.error("登录FTP服务失败！");
				return false;
			}
			this.ftpClient.login(this.ftpConfig.getUser(), this.ftpConfig.getPassword());
			// 设置传输协议
			this.ftpClient.enterLocalPassiveMode();
			this.ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			this.ftpClient.setBufferSize(1024 * 2);
			this.ftpClient.setDataTimeout(30 * 1000);
			logger.debug("user[{}] ftp login success!", this.ftpConfig.getUser());
			return true;
		} catch (Exception e) {
			logger.error(this.ftpConfig.getUser() + "登录FTP服务失败！", e);
			return false;
		}
	}

	/**
	 * @退出关闭服务器链接
	 */
	public void ftpLogOut() {
		if (null != this.ftpClient && this.ftpClient.isConnected()) {
			try {
				boolean reuslt = this.ftpClient.logout();// 退出FTP服务器
				if (reuslt) {
					logger.info("成功退出服务器");
				}
			} catch (IOException e) {
				logger.warn("退出FTP服务器异常！" + e.getMessage(), e);
			} finally {
				try {
					this.ftpClient.disconnect();// 关闭FTP服务器的连接
				} catch (IOException e) {
					logger.warn("关闭FTP服务器的连接异常！", e);
				}
			}
		}
	}

	/***
	 * 上传Ftp文件
	 * 
	 * @param localFile
	 *            当地文件
	 * @param romotUpLoadePath上传服务器路径
	 *            - 应该以/结束
	 */
	public boolean uploadFile(File localFile, String romotUpLoadePath) {
		BufferedInputStream inStream = null;
		boolean success = false;
		try {
			this.ftpClient.changeWorkingDirectory(romotUpLoadePath);// 改变工作路径
			inStream = new BufferedInputStream(new FileInputStream(localFile));
			logger.info(localFile.getName() + "开始上传.....");
			success = this.ftpClient.storeFile(localFile.getName(), inStream);
			if (success == true) {
				logger.info(localFile.getName() + "上传成功");
				return success;
			}
		} catch (FileNotFoundException e) {
			logger.error(localFile + "未找到", e);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (inStream != null) {
				try {
					inStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return success;
	}

	/***
	 * 下载文件
	 * 
	 * @param remoteFileName
	 *            待下载文件名称
	 * @param localDires
	 *            下载到当地那个路径下
	 * @param remoteDownLoadPath
	 *            remoteFileName所在的路径
	 */

	public boolean downloadFile(String remoteFileName, String localDires, String remoteDownLoadPath) {
		String strFilePath = localDires + remoteFileName;
		BufferedOutputStream outStream = null;
		boolean success = false;
		try {
			this.ftpClient.changeWorkingDirectory(remoteDownLoadPath);
			outStream = new BufferedOutputStream(new FileOutputStream(strFilePath));
			logger.info(remoteFileName + "开始下载....");
			success = this.ftpClient.retrieveFile(remoteFileName, outStream);
			if (success == true) {
				logger.info(remoteFileName + "成功下载到" + strFilePath);
				return success;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(remoteFileName + "下载失败");
		} finally {
			if (null != outStream) {
				try {
					outStream.flush();
					outStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		if (success == false) {
			logger.error(remoteFileName + "下载失败!!!");
		}
		return success;
	}

	/***
	 * @上传文件夹
	 * @param localDirectory
	 *            当地文件夹
	 * @param remoteDirectoryPath
	 *            Ftp 服务器路径 以目录"/"结束
	 */
	public boolean uploadDirectory(String localDirectory, String remoteDirectoryPath) {
		File src = new File(localDirectory);
		try {
			remoteDirectoryPath = remoteDirectoryPath + src.getName() + "/";
			this.ftpClient.makeDirectory(remoteDirectoryPath);
			// ftpClient.listDirectories();
		} catch (IOException e) {
			e.printStackTrace();
			logger.info(remoteDirectoryPath + "目录创建失败");
		}
		File[] allFile = src.listFiles();
		for (int currentFile = 0; currentFile < allFile.length; currentFile++) {
			if (!allFile[currentFile].isDirectory()) {
				String srcName = allFile[currentFile].getPath().toString();
				uploadFile(new File(srcName), remoteDirectoryPath);
			}
		}
		for (int currentFile = 0; currentFile < allFile.length; currentFile++) {
			if (allFile[currentFile].isDirectory()) {
				// 递归
				uploadDirectory(allFile[currentFile].getPath().toString(), remoteDirectoryPath);
			}
		}
		return true;
	}

	/***
	 * @下载文件夹
	 * @param localDirectoryPath本地地址
	 * @param remoteDirectory
	 *            远程文件夹
	 */
	public boolean downLoadDirectory(String localDirectoryPath, String remoteDirectory) {
		try {
			String fileName = new File(remoteDirectory).getName();
			localDirectoryPath = localDirectoryPath + fileName + "//";
			new File(localDirectoryPath).mkdirs();
			FTPFile[] allFile = this.ftpClient.listFiles(remoteDirectory);
			for (int currentFile = 0; currentFile < allFile.length; currentFile++) {
				if (!allFile[currentFile].isDirectory()) {
					downloadFile(allFile[currentFile].getName(), localDirectoryPath, remoteDirectory);
				}
			}
			for (int currentFile = 0; currentFile < allFile.length; currentFile++) {
				if (allFile[currentFile].isDirectory()) {
					String strremoteDirectoryPath = remoteDirectory + "/" + allFile[currentFile].getName();
					downLoadDirectory(localDirectoryPath, strremoteDirectoryPath);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			logger.info("下载文件夹失败");
			return false;
		}
		return true;
	}

	// FtpClient的Set 和 Get 函数
	public FTPClient getFtpClient() {
		return ftpClient;
	}

	public void setFtpClient(FTPClient ftpClient) {
		this.ftpClient = ftpClient;
	}

	public static void main(String[] args) throws IOException {
		FtpConfig ftpConfig = new FtpConfig();
		ftpConfig.setServer("172.29.12.224");
		ftpConfig.setPort(22);
		ftpConfig.setUser("o2odev");
		ftpConfig.setPassword("xd135dev");
		FtpHelper2 ftp = new FtpHelper2(ftpConfig);
		// ftp.ftpLogin();
		// 上传文件夹
		ftp.uploadDirectory("d://DataProtemp", "/home/data/");
		// 下载文件夹
		ftp.downLoadDirectory("d://tmp//", "/home/data/DataProtemp");
		ftp.ftpLogOut();
	}
}