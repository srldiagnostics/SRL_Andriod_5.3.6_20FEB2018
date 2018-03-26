package com.srllimited.srl.SecureCer;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;

import android.util.Log;

/**
 * Created by Sachin.Choudhary on 6/23/2016.
 */
public class SSLConection
{
	private static TrustManager[] trustManagers;

	public static void allowAllSSL()
	{

		javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier()
		{
			@Override
			public boolean verify(String hostname, SSLSession session)
			{
				Log.e("allowAllSSL1", hostname);
				return true;
			}
		});

		javax.net.ssl.SSLContext context;

		if (trustManagers == null)
		{
			trustManagers = new TrustManager[] { new _FakeX509TrustManager() };
		}

		try
		{
			context = javax.net.ssl.SSLContext.getInstance("TLS");
			context.init(null, trustManagers, new SecureRandom());
			javax.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
		}
		catch (NoSuchAlgorithmException e)
		{
			Log.e("allowAllSSL", e.toString());
		}
		catch (KeyManagementException e)
		{
			Log.e("allowAllSSL", e.toString());
		}
	}

	public static class _FakeX509TrustManager implements javax.net.ssl.X509TrustManager
	{
		private static final X509Certificate[] _AcceptedIssuers = new X509Certificate[] {};

		public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException
		{
		}

		public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException
		{
		}

		public X509Certificate[] getAcceptedIssuers()
		{
			return (_AcceptedIssuers);
		}
	}
}
