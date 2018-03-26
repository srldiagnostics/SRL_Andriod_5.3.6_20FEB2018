package com.srllimited.srl.util;

import com.srllimited.srl.BuildConfig;

public class Log
{
	private static final String TAG = Log.class.getSimpleName();

	private static final String EMPTY = "";

	/**
	 * Send a VERBOSE log message.
	 *
	 * @param tag
	 * @param format
	 * @param args
	 * @return
	 */
	public static void v(String tag, String format, Object... args)
	{
		if (BuildConfig.DEBUG)
		{
			android.util.Log.v(tag, format(format, args));
		}
	}

	/**
	 * Send a VERBOSE log message and log the exception.
	 *
	 * @param tag
	 * @param msg
	 * @param e
	 * @return
	 */
	public static void v(String tag, String msg, Throwable e)
	{
		if (BuildConfig.DEBUG)
		{
			android.util.Log.v(tag, msg, e);
		}
	}

	/**
	 * Send a VERBOSE log message and log the exception.
	 *
	 * @param tag
	 * @param format
	 * @param e
	 * @param args
	 * @return
	 */

	public static void v(String tag, String format, Throwable e, Object... args)
	{
		if (BuildConfig.DEBUG)
		{
			android.util.Log.v(tag, format(format, args), e);
		}
	}

	/**
	 * Send a DEBUG log message.
	 *
	 * @param tag
	 * @param format
	 * @param args
	 * @return
	 */

	public static void d(String tag, String format, Object... args)
	{
		if (BuildConfig.DEBUG)
		{
			android.util.Log.d(tag, format(format, args));
		}
	}

	/**
	 * Send a DEBUG log message and log the exception.
	 *
	 * @param tag
	 * @param msg
	 * @param e
	 * @return
	 */
	public static void d(String tag, String msg, Throwable e)
	{
		if (BuildConfig.DEBUG)
		{
			android.util.Log.d(tag, msg, e);
		}
	}

	/**
	 * Send a DEBUG log message and log the exception.
	 *
	 * @param tag
	 * @param format
	 * @param e
	 * @param args
	 * @return
	 */
	public static void d(String tag, String format, Throwable e, Object... args)
	{
		if (BuildConfig.DEBUG)
		{
			android.util.Log.d(tag, format(format, args), e);
		}
	}

	/**
	 * Send a WARN log message.
	 *
	 * @param tag
	 * @param format
	 * @param args
	 * @return
	 */
	public static void w(String tag, String format, Object... args)
	{
		if (BuildConfig.DEBUG)
		{
			android.util.Log.w(tag, format(format, args));
		}
	}

	/**
	 * Send a WARN log message and log the exception.
	 *
	 * @param tag
	 * @param msg
	 * @param e
	 * @return
	 */
	public static void w(String tag, String msg, Throwable e)
	{
		android.util.Log.w(tag, msg, e);
	}

	/**
	 * Send a WARN log message and log the exception.
	 *
	 * @param tag
	 * @param format
	 * @param e
	 * @param args
	 * @return
	 */
	public static void w(String tag, String format, Throwable e, Object... args)
	{
		if (BuildConfig.DEBUG)
		{
			android.util.Log.w(tag, format(format, args), e);
		}
	}

	/**
	 * Send a INFO log message.
	 *
	 * @param tag
	 * @param format
	 * @param args
	 * @return
	 */
	public static void i(String tag, String format, Object... args)
	{
		if (BuildConfig.DEBUG)
		{
			android.util.Log.i(tag, format(format, args));
		}
	}

	/**
	 * Send a INFO log message and log the exception.
	 *
	 * @param tag
	 * @param msg
	 * @param e
	 * @return
	 */
	public static void i(String tag, String msg, Throwable e)
	{
		android.util.Log.i(tag, msg, e);
	}

	/**
	 * Send a INFO log message and log the exception.
	 *
	 * @param tag
	 * @param format
	 * @param e
	 * @param args
	 * @return
	 */
	public static void i(String tag, String format, Throwable e, Object... args)
	{
		if (BuildConfig.DEBUG)
		{
			android.util.Log.i(tag, format(format, args), e);
		}
	}

	/**
	 * Send a ERROR log message.
	 *
	 * @param tag
	 * @param format
	 * @param args
	 * @return
	 */
	public static void e(String tag, String format, Object... args)
	{
		if (BuildConfig.DEBUG)
		{
			android.util.Log.e(tag, format(format, args));
		}

	}

	/**
	 * Send a ERROR log message and log the exception.
	 *
	 * @param tag
	 * @param msg
	 * @param e
	 * @return
	 */
	public static void e(String tag, String msg, Throwable e)
	{
		if (BuildConfig.DEBUG)
		{
			android.util.Log.e(tag, msg, e);
		}
	}

	/**
	 * Send a ERROR log message and log the exception.
	 *
	 * @param tag
	 * @param format
	 * @param e
	 * @param args
	 * @return
	 */
	public static void e(String tag, String format, Throwable e, Object... args)
	{
		if (BuildConfig.DEBUG)
		{
			android.util.Log.e(tag, format(format, args), e);
		}
	}

	private static String format(String format, Object... args)
	{
		try
		{
			return String.format(format == null ? EMPTY : format, args);
		}
		catch (RuntimeException e)
		{
			Log.w(TAG, "format error. reason=%s, format=%s", e.getMessage(), format);
			return String.format(EMPTY, format);
		}
	}
}