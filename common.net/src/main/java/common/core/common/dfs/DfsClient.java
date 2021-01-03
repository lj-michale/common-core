package common.core.common.dfs;

import java.io.OutputStream;
import java.util.Map;

public interface DfsClient {

	/**
	 * 上传文件
	 * 
	 * @param fileBytes
	 *            上传文件内容
	 * @param fullFileName
	 *            文件全路径，含uploadPath
	 * @param uploadPath
	 *            上传路径
	 * @param metadatas
	 *            上传meta信息
	 * @return 上传后返回的路径
	 */
	public String uploadFile(byte[] fileBytes, String fullFileName, Map<String, String> metadatas);

	/**
	 * 下载文件
	 * 
	 * @param fullFileName
	 *            文件全路径，含group
	 * @return 文件字节
	 */
	public byte[] downloadFile(String fullFileName);

	/**
	 * 获取指定文件的meta信息
	 * 
	 * @param fullFileName
	 *            文件全路径，含uploadPath
	 * @return 文件的meta信息
	 */
	public Map<String, String> getMetadatas(String fullFileName);

	/**
	 * 下载文件并输出outputStream
	 * 
	 * @param fullFileName
	 *            文件全路径，含group
	 * @param outputStream
	 */
	public void outputFile(String fullFileName, OutputStream outputStream);

	/**
	 * 更新元数据信息
	 * 
	 * @param fullFileName
	 *            文件全路径，含group
	 * @param metaName
	 *            元数据名
	 * @param metaValue
	 *            元数据值
	 */
	public void updateMetadata(String fullFileName, String metaName, String metaValue);

}
