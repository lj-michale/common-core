package common.core.web.api;

import org.springframework.web.multipart.MultipartFile;

public class MultipartFileContext {

	private static ThreadLocal<MultipartFile> MULTIPART_FILE = new ThreadLocal<>();

	public static void initContext(MultipartFile multipartFile) {
		MULTIPART_FILE.set(multipartFile);
	}
	
	public static MultipartFile getMultipartFile(){
		return MULTIPART_FILE.get();
	}
	
	public static void cleanContext(){
		MULTIPART_FILE.remove();
	}

}
