package com.srllimited.srl.util;

/**
 * Created by RuchiTiwari on 2/7/2017.
 */
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

public class Crypt
{
	Crypt()
	{
	}

	protected static final String md5(String toEncrypt)
	{
		try
		{
			MessageDigest exc = MessageDigest.getInstance("md5");
			exc.update(toEncrypt.getBytes());
			byte[] bytes = exc.digest();
			StringBuilder sb = new StringBuilder();

			for (int i = 0; i < bytes.length; ++i)
			{
				sb.append(String.format("%02X", new Object[] { Byte.valueOf(bytes[i]) }));
			}

			return sb.toString().toLowerCase(Locale.getDefault());
		}
		catch (Exception var5)
		{
			return "";
		}
	}

	private static String convertToHex(byte[] data)
	{
		StringBuffer buf = new StringBuffer();

		for (int i = 0; i < data.length; ++i)
		{
			int halfbyte = data[i] >>> 4 & 15;
			int two_halfs = 0;

			do
			{
				if (halfbyte >= 0 && halfbyte <= 9)
				{
					buf.append((char) (48 + halfbyte));
				}
				else
				{
					buf.append((char) (97 + (halfbyte - 10)));
				}

				halfbyte = data[i] & 15;
			}
			while (two_halfs++ < 1);
		}

		return buf.toString();
	}

	public static String SHA1(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException
	{
		MessageDigest md = MessageDigest.getInstance("SHA-1");
		byte[] sha1hash = new byte[40];
		md.update(text.getBytes("iso-8859-1"), 0, text.length());
		sha1hash = md.digest();
		return convertToHex(sha1hash);
	}

	protected static final String SHA512(String toEncrypt) throws NoSuchAlgorithmException
	{
		String message = toEncrypt;
		String out = "";

		try
		{
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			md.update(message.getBytes());
			byte[] e = md.digest();

			for (int i = 0; i < e.length; ++i)
			{
				byte temp = e[i];

				String s;
				for (s = Integer.toHexString((new Byte(temp)).byteValue()); s.length() < 2; s = "0" + s)
				{
					;
				}

				s = s.substring(s.length() - 2);
				out = out + s;
			}
		}
		catch (NoSuchAlgorithmException var8)
		{
			android.util.Log.d("Exception", var8.getMessage());
		}

		return out;
	}
}
