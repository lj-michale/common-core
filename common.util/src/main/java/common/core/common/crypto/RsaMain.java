package common.core.common.crypto;

public class RsaMain {
	public static void main(String[] args) {
		String errorMessage = "please input method and keyFile and data,as : java -jar jarfile common.core.common.crypto.RSAUtils encrypt|decrypt keyFile data";
		if (args.length < 3) {
			System.err.println(errorMessage);
			return;
		}
	}
}
