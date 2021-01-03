package common.core.common.dfs.fastdfs;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.DownloadCallback;
import org.csource.fastdfs.FileInfo;
import org.csource.fastdfs.ProtoCommon;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;

import common.core.app.context.ConfigContext;
import common.core.app.log.Logger;
import common.core.app.log.LoggerFactory;
import common.core.common.assertion.util.AssertErrorUtils;
import common.core.common.dfs.DfsClient;
import common.core.common.exception.RuntimeIOException;
import common.core.common.util.ImageUtil;
import common.core.common.util.IoUtil;
import common.core.common.util.ObjectUtil;
import common.core.common.util.StopWatch;
import common.core.common.util.StringUtil;

public class FastDfsClient implements DfsClient {

	private final Logger logger = LoggerFactory.getLogger(FastDfsClient.class);

	public static final String SPLIT_GROUP_NAME_AND_FILENAME_SEPERATOR = "/";
	private static final String[] IMG_EXT_NAMES = { "bmp", "jpg", "jpeg", "png", "gif" };
	private static final Map<String, String> CONVERTTYPE_MAP = new HashMap<>();

	static {
		CONVERTTYPE_MAP.put("thumbnail", "132_132");
		CONVERTTYPE_MAP.put("medium", "800_600");
	}

	public FastDfsClient() {
		super();
		StopWatch watch = new StopWatch();
		try {
			FastDfsSetting fastDfsSetting = new FastDfsSetting();
			ConfigContext.bindConfigObject(fastDfsSetting);
			logger.debug("fastDfsSetting={}", fastDfsSetting);
			Properties properties = new Properties();
			properties.putAll(ObjectUtil.toMap(fastDfsSetting));
			ClientGlobal.initByProperties(properties);
			try {
				// ProtoCommon.activeTest(trackerServer.getSocket());
			} catch (Exception e) {
				logger.debug("ProtoCommon.activeTest(trackerServer.getSocket()) error", e);
			}
		} catch (IOException | MyException e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e);
		} finally {
			logger.debug("init FastDfsClient , elapsedTime={}", watch.elapsedTime());
		}
	}

	public StorageClient getStorageClient() {
		StopWatch watch = new StopWatch();
		try {
			TrackerClient trackerClient = new TrackerClient();
			TrackerServer trackerServer = trackerClient.getConnection();
			StorageServer storageServer = null;
			StorageClient storageClient = new StorageClient(trackerServer, storageServer);
			return storageClient;
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e);
		} finally {
			logger.debug("getStorageClient , elapsedTime={}", watch.elapsedTime());
		}
	}

	@Override
	public byte[] downloadFile(String fullFileName) {
		StopWatch watch = new StopWatch();
		try {
			String[] fileInfos = buildFilePath(fullFileName);
			byte[] datas = getStorageClient().download_file(fileInfos[0], fileInfos[1]);
			logger.debug("storageClient.download_file group={},file={},length={}", fileInfos[0], fileInfos[1], null == datas ? null : datas.length);
			return datas;
		} catch (IOException | MyException e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e);
		} finally {
			logger.debug("downloadFile, fullFileName={}, elapsedTime={}", fullFileName, watch.elapsedTime());
		}
	}

	public byte[] downloadFile(String fullFileName, long fileOffset, long downloadBytes) {
		StopWatch watch = new StopWatch();
		try {
			String[] fileInfos = buildFilePath(fullFileName);
			byte[] datas = getStorageClient().download_file(fileInfos[0], fileInfos[1], fileOffset, downloadBytes);
			logger.debug("storageClient.download_file group={},file={},length={},file_offset={},download_bytes={}", fileInfos[0], fileInfos[1], null == datas ? null : datas.length, fileOffset, downloadBytes);
			return datas;
		} catch (IOException | MyException e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e);
		} finally {
			logger.debug("downloadFile, fullFileName={}, elapsedTime={}", fullFileName, watch.elapsedTime());
		}
	}

	@Override
	public void outputFile(String fullFileName, OutputStream outputStream) {
		StopWatch watch = new StopWatch();
		try {
			String[] fileInfos = buildFilePath(fullFileName);
			getStorageClient().download_file(fileInfos[0], fileInfos[1], new DownloadCallback() {

				@Override
				public int recv(long file_size, byte[] data, int bytes) {
					if (bytes > 0)
						try {
							outputStream.write(data, 0, bytes);
						} catch (IOException e) {
							logger.error(e.getMessage(), e);
							throw new RuntimeException(e);
						} finally {
							logger.debug("recv file_size={},data.length={},bytes={}", file_size, data.length, bytes);
						}
					return 0;
				}
			});
		} catch (IOException | MyException e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e);
		} finally {
			logger.debug("outputFile, fullFileName={}, elapsedTime={}", fullFileName, watch.elapsedTime());
		}
	}

	public void outputFile(String fullFileName, OutputStream outputStream, long fileOffset, long downloadBytes) {
		StopWatch watch = new StopWatch();
		try {
			String[] fileInfos = buildFilePath(fullFileName);
			getStorageClient().download_file(fileInfos[0], fileInfos[1], fileOffset, downloadBytes, new DownloadCallback() {

				@Override
				public int recv(long file_size, byte[] data, int bytes) {
					if (bytes > 0)
						try {
							outputStream.write(data, 0, bytes);
						} catch (IOException e) {
							logger.error(e.getMessage(), e);
							throw new RuntimeException(e);
						} finally {
							logger.debug("recv file_size={},data.length={},bytes={}", file_size, data.length, bytes);
						}
					return 0;
				}
			});
		} catch (IOException | MyException e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e);
		} finally {
			logger.debug("outputFile, fullFileName={}, elapsedTime={},file_offset={},download_bytes={}", fullFileName, watch.elapsedTime(), fileOffset, downloadBytes);
		}
	}

	@Override
	public Map<String, String> getMetadatas(String fullFileName) {
		StopWatch watch = new StopWatch();
		Map<String, String> metaDatas = new HashMap<>();
		try {
			String[] fileInfos = buildFilePath(fullFileName);
			StorageClient storageClient = getStorageClient();
			NameValuePair[] metadata = storageClient.get_metadata(fileInfos[0], fileInfos[1]);
			for (int i = 0; null != metadata && i < metadata.length; i++) {
				NameValuePair nameValuePair = metadata[i];
				metaDatas.put(nameValuePair.getName(), nameValuePair.getValue());
			}
			FileInfo fileInfo = storageClient.get_file_info(fileInfos[0], fileInfos[1]);
			metaDatas.put("createTimestamp", String.valueOf(fileInfo.getCreateTimestamp().getTime()));
			metaDatas.put("fileSize", String.valueOf(fileInfo.getFileSize()));
			metaDatas.put("crc32", String.valueOf(fileInfo.getCrc32()));
			metaDatas.put("sourceIpAddr", String.valueOf(fileInfo.getSourceIpAddr()));

		} catch (IOException | MyException e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e);
		} finally {
			logger.debug("getMetadatas, fullFileName={}, elapsedTime={}", fullFileName, watch.elapsedTime());
		}
		return metaDatas;
	}

	/**
	 * @param fullFileName
	 * @param metaName
	 * @param metaValue
	 */
	@Override
	public void updateMetadata(String fullFileName, String metaName, String metaValue) {
		StopWatch watch = new StopWatch();
		String[] fileInfos = buildFilePath(fullFileName);
		try {
			StorageClient storageClient = getStorageClient();
			NameValuePair[] metadata = storageClient.get_metadata(fileInfos[0], fileInfos[1]);
			List<NameValuePair> nameValuePairList = new ArrayList<>();
			if (null != metadata) {
				nameValuePairList.containsAll(Arrays.asList(metadata));
			}
			NameValuePair item = null;
			for (NameValuePair nameValuePair : nameValuePairList) {
				if (metaName.equals(nameValuePair.getName())) {
					item = nameValuePair;
					break;
				}
			}
			if (item == null) {
				item = new NameValuePair(metaName, metaValue);
				nameValuePairList.add(item);
			} else {
				item.setName(metaName);
				item.setValue(metaValue);
			}
			storageClient.set_metadata(fileInfos[0], fileInfos[1], nameValuePairList.toArray(new NameValuePair[nameValuePairList.size()]), ProtoCommon.STORAGE_SET_METADATA_FLAG_OVERWRITE);
		} catch (IOException | MyException e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e);
		} finally {
			logger.debug("updateMetadatas, fullFileName={},metaName={},metaValue={} elapsedTime={}", fullFileName, metaName, metaValue, watch.elapsedTime());
		}
	}

	@Override
	public String uploadFile(byte[] fileBytes, String fullFileName, Map<String, String> metadatas) {
		String[] fileInfos = buildFilePath(fullFileName);
		return uploadFile(fileBytes, fileInfos[0], getFileExtName(fullFileName), metadatas);
	}

	/**
	 * 上传字节数据
	 * 
	 * @param fileBytes
	 * @param group
	 * @param fileExtName
	 * @param metadatas
	 * @return 带group的地址信息
	 */
	public String uploadFile(byte[] fileBytes, String group, String fileExtName, Map<String, String> metadatas) {
		return this.uploadFile(fileBytes, group, fileExtName, metadatas, fileBytes.length);
	}

	/**
	 * 上传字节数据
	 * 
	 * @param fileBytes
	 * @param group
	 * @param fileExtName
	 * @param metadatas
	 * @param length
	 * @return 带group的地址信息
	 */
	public String uploadFile(byte[] fileBytes, String group, String fileExtName, Map<String, String> metadatas, int length) {
		StopWatch watch = new StopWatch();
		try {
			List<NameValuePair> metaList = new ArrayList<>();
			if (null != metadatas) {
				for (Map.Entry<String, String> metadata : metadatas.entrySet()) {
					metaList.add(new NameValuePair(metadata.getKey(), metadata.getKey()));
				}
			}
			String[] results;
			StorageClient storageClient = getStorageClient();
			results = storageClient.upload_file(group, fileBytes, 0, length, fileExtName.toLowerCase(), metaList.toArray(new NameValuePair[metaList.size()]));
			return FastDfsClient.SPLIT_GROUP_NAME_AND_FILENAME_SEPERATOR + results[0] + FastDfsClient.SPLIT_GROUP_NAME_AND_FILENAME_SEPERATOR + results[1];
		} catch (IOException | MyException e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e);
		} finally {
			logger.debug("uploadFile, group={}, fileExtName={}, metadatas={}, elapsedTime={}", group, fileExtName, metadatas, watch.elapsedTime());
		}
	}

	/**
	 * @param inputStream
	 * @param fullFileName
	 * @param metadatas
	 * @return
	 */
	public String uploadInputStream(InputStream inputStream, String fullFileName, Map<String, String> metadatas) {
		String[] fileInfos = buildFilePath(fullFileName);
		return uploadInputStream(inputStream, fileInfos[0], getFileExtName(fullFileName), metadatas);
	}

	/**
	 * 将流增加到指定的文件
	 * 
	 * @param inputStream
	 * @param fullFileName
	 * @param offset
	 * @return
	 */
	public int appendInputStream(InputStream inputStream, String fullFileName, int offset) {
		byte[] fileBytes = IoUtil.bytes(inputStream);
		return this.appendFile(fileBytes, fullFileName, offset);
	}

	/**
	 * 增加数据增加到指定的文件
	 * 
	 * @param fileBytes
	 * @param fullFileName
	 * @param offset
	 * @return
	 */
	public int appendFile(byte[] fileBytes, String fullFileName, int offset) {
		StopWatch watch = new StopWatch();
		String[] fileInfos = buildFilePath(fullFileName);
		int length = 0;
		try {
			length = fileBytes.length;
			return this.getStorageClient().append_file(fileInfos[0], fileInfos[1], fileBytes, offset, length);
		} catch (IOException | MyException e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e);
		} finally {
			logger.debug("appendFile fullFileName={}, group={}, appender_filename={}, offset={}, elapsedTime={}", fullFileName, fileInfos[0], fileInfos[1], offset, watch.elapsedTime());
		}
	}

	/**
	 * 上传流数据
	 * 
	 * @param inputStream
	 * @param group
	 * @param fileExtName
	 * @param metada
	 *            tas
	 * @return 带group的地址信息
	 */
	public String uploadInputStream(InputStream inputStream, String group, String fileExtName, Map<String, String> metadatas) {
		byte[] fileBytes = IoUtil.bytes(inputStream);
		return uploadFile(fileBytes, group, fileExtName, metadatas);
	}

	public String[] buildFilePath(String fullFileName) {
		if (fullFileName.indexOf(FastDfsClient.SPLIT_GROUP_NAME_AND_FILENAME_SEPERATOR) == 0) {
			fullFileName = fullFileName.substring(1);
		}
		AssertErrorUtils.assertTrue(fullFileName.indexOf(StorageClient1.SPLIT_GROUP_NAME_AND_FILENAME_SEPERATOR) > 0, "cant't find group name in fullFileName={}", fullFileName);
		String[] results = new String[2];
		int pos = fullFileName.indexOf(SPLIT_GROUP_NAME_AND_FILENAME_SEPERATOR);
		results[0] = fullFileName.substring(0, pos); // group name
		results[1] = fullFileName.substring(pos + 1); // file name
		AssertErrorUtils.assertTrue(results[0].startsWith("group"), "cant't find group name in fullFileName={}", fullFileName);
		return results;
	}

	public String getFileExtName(String fullFileName) {
		return fullFileName.substring(fullFileName.lastIndexOf(".") + 1).toLowerCase();
	}

	public boolean isFastDfsPath(String fullFileName) {
		if (fullFileName.startsWith("/"))
			return fullFileName.startsWith("/group");
		return fullFileName.startsWith("group");
	}

	public boolean isImgPath(String fullFileName) {
		String extName = getFileExtName(fullFileName.toLowerCase());
		for (String name : IMG_EXT_NAMES) {
			if (extName.equals(name))
				return true;
		}
		return false;
	}

	public String buildFastDfsPath(String fullFileName, String groupName) {
		return buildFastDfsPath(fullFileName, groupName, null);
	}

	public String buildFastDfsPath(String fullFileName, String groupName, String convertType) {
		if (!this.isFastDfsPath(fullFileName)) {
			fullFileName = "/" + groupName + (fullFileName.startsWith("/") ? "" : "/") + fullFileName;
		}
		if (null == convertType || !isImgPath(fullFileName))
			return fullFileName;
		convertType = getFormat(convertType);
		fullFileName = fullFileName + "_" + convertType + "." + getFileExtName(fullFileName);
		return fullFileName;

	}

	public String getFormat(String convertType) {
		convertType = CONVERTTYPE_MAP.getOrDefault(convertType, convertType);
		convertType = convertType.replace('_', 'x').replace('-', 'x');
		return convertType;
	}

	public void outputFile(OutputStream outputStream, String filePath, String convertType) {
		StopWatch watch = new StopWatch();
		byte[] data = new byte[0];
		try {
			if (StringUtil.hasText(convertType)) {
				logger.debug("getConvertType={}", convertType);
				String format = this.getFormat(convertType);
				logger.debug("format={}", format);
				Map<String, String> metadatas = this.getMetadatas(filePath);
				String metaValue = null;
				if (metadatas.containsKey(format)) {
					metaValue = metadatas.get(format);
					logger.debug("get format path metaValue={}", metaValue);
				} else {
					String[] widthAndHeight = StringUtil.split(format, "x");
					int width = Integer.valueOf(widthAndHeight[0]);
					int height = Integer.valueOf(widthAndHeight[1]);
					InputStream inputStream = new ByteArrayInputStream(this.downloadFile(filePath));
					ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
					ImageUtil.thumbnailImage(inputStream, byteArrayOutputStream, this.getFileExtName(filePath), width, height);
					metaValue = this.uploadInputStream(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()), filePath, null);
					this.updateMetadata(filePath, format, metaValue);
					logger.debug("uploadInputStream and updateMetadata format={}, metaValue={}", format, metaValue);

				}
				logger.debug("filePath={},metaValue={}", filePath, metaValue);
				filePath = metaValue;
			}
			logger.debug("get file from dfs filePath={}", filePath);
			data = this.downloadFile(filePath);
			outputStream.write(data);
		} catch (IOException e) {
			throw new RuntimeIOException(e);
		} finally {
			logger.debug("fastDfsClient.outputFile, filePath={},data.length={}, elapsedTime={}", filePath, data.length, watch.elapsedTime());
		}
	}

}
