package ipower.micromessage.service.https.impl;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import ipower.micromessage.service.https.ISSLX509TrustManager;

/**
 * 证书信任管理器实现(信任所有证书)。
 * @author 杨勇.
 * @since 2014-02-20.
 * */
public class SSLX509TrustManagerImpl implements ISSLX509TrustManager {

	@Override
	public void checkClientTrusted(X509Certificate[] chain, String authType)
	{
		
	}

	@Override
	public void checkServerTrusted(X509Certificate[] chain, String authType)
			throws CertificateException {
	}

	@Override
	public X509Certificate[] getAcceptedIssuers() 
	{
		return null;
	}

}