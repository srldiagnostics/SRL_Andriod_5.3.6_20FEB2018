
package com.srllimited.srl.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import android.util.Base64;
import android.util.Base64InputStream;
import android.util.Base64OutputStream;

public class StringUtil
{
	public static String getValidString(final String string)
	{
		if (Validate.notEmpty(string))
		{
			return string;
		}
		return "";
	}

	public static String getValidString(byte[] bytes)
	{
		if (Validate.notNull(bytes))
		{
			return new String(bytes);
		}
		return "";
	}

	public static String objectToString(Serializable obj)
	{
		try
		{
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(
					new Base64OutputStream(baos, Base64.NO_PADDING | Base64.NO_WRAP));
			oos.writeObject(obj);
			oos.close();
			return baos.toString("UTF-8");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static Object stringToObject(String str)
	{
		try
		{
			return new ObjectInputStream(
					new Base64InputStream(new ByteArrayInputStream(str.getBytes()), Base64.NO_PADDING | Base64.NO_WRAP))
							.readObject();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

}
