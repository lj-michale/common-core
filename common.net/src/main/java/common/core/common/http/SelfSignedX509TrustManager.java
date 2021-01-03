package common.core.common.http;

import javax.net.ssl.X509TrustManager;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class SelfSignedX509TrustManager implements X509TrustManager {
	public void checkClientTrusted(X509Certificate[] certificates, String authType) throws CertificateException {
		// accept all client certificates
		// trustManager.checkClientTrusted(certificates, authType);
	}

	public void checkServerTrusted(X509Certificate[] certificates, String authType) throws CertificateException {
		// accept all certificates
		// trustManager.checkServerTrusted(certificates, authType);
	}

	public X509Certificate[] getAcceptedIssuers() {
		return null;
	}
}
