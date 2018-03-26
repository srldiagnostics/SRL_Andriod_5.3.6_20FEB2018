package com.srllimited.srl.util;

/**
 * Created by codefyneandroid on 06-12-2016.
 */

import java.util.regex.Pattern;

import android.widget.EditText;

public class Validation
{

	// Regular Expression
	// you can change the expression based on your need
	private static final String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	private static final String PHONE_REGEX = "\\{0-9}";

	// private static final String NAME_REGEX="^[a-zA-Z0-9_.-]*$";
	private static final String NAME_REGEX = "^[a-zA-Z_.0-9.']{1,15}$";

	//private static final String PASSWORD_REGEX="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
	//private static final String PASSWORD_REGEX="(?=.*[a-z0-9A-Z._!]).{6,}$";
	// PollError Messages
	private static final String PASSWORD_REGEX = "(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z0-9!@#$%&*]{6,}$";

	private static final String REQUIRED_MSG = "required";

	private static final String EMAIL_MSG = "invalid email";

	private static final String PHONE_MSG = "enter valid phone number";

	private static final String PIN_MSG = "Enter Valid Pin Code";

	//private static final String NAME_MSG = "enter [alphabets/numbers,_,.]only";
	private static final String NAME_MSG = "enter valid name";

	//private static final String PASSWORD_MSG = " enter atleast one lower,upper,digit and special chars[@,#,$,%,!,.] and length atleast 8";
	private static final String PASSWORD_MSG = "enter min 6 characters and must start with letter or number";

	// call this method when you need to check email validation
	public static boolean isEmailAddress(EditText editText, boolean required)
	{
		return isValid(editText, EMAIL_REGEX, EMAIL_MSG, required, "please enter email");
	}

	// call this method when you need to check phone number validation
	public static boolean isPhoneNumber(EditText editText, boolean required)
	{
		return isValid(editText, PHONE_REGEX, PHONE_MSG, required, "please enter mobile number");
	}

	public static boolean isValidName(EditText editText, boolean required)
	{
		return isValid(editText, NAME_REGEX, NAME_MSG, required, "please enter name");
	}

	public static boolean isValidFName(EditText editText, boolean required)
	{
		return isValid(editText, NAME_REGEX, NAME_MSG, required, "please enter first name");
	}

	public static boolean isValidState(EditText editText, boolean required)
	{
		return isValid(editText, NAME_REGEX, NAME_MSG, required, "please Select state");
	}

	public static boolean isValidCity(EditText editText, boolean required)
	{
		return isValid(editText, NAME_REGEX, NAME_MSG, required, "please Select City");
	}

	public static boolean isValidLName(EditText editText, boolean required)
	{
		return isValid(editText, NAME_REGEX, NAME_MSG, required, "please enter last name");
	}

	public static boolean isValidPassword(EditText editText, boolean required)
	{
		return isValid(editText, PASSWORD_REGEX, PASSWORD_MSG, required, "please enter password");
	}

	// return true if the input field is valid, based on the parameter passed
	public static boolean isValid(EditText editText, String regex, String errMsg, boolean required, String msg)
	{

		String text = editText.getText().toString().trim();
		// clearing the error, if it was previously set by some other values
		editText.setError(null);

		// text required and editText is blank, so return false
		//if (required && !(hasText(editText)))
		if (required && text.isEmpty())
		{
			editText.setError(msg);
			return false;
		}
		else
		{
			if (required && !Pattern.matches(regex, text.trim()))
			{
				editText.setError(errMsg);
				return false;
			}
		}
		// pattern doesn't match so returning false
		/*if (required && !Pattern.matches(regex, text.trim()))
		{
			editText.setError(errMsg);
			return false;
		}*/
		;

		return true;
	}

	public static boolean isValidEmail(EditText editText, String regex, String errMsg, boolean required)
	{

		String text = editText.getText().toString().trim();
		// clearing the error, if it was previously set by some other values
		editText.setError(null);

		// text required and editText is blank, so return false
		//if (required && !(hasText(editText)))
		if (required && text.isEmpty())
		{
			editText.setError("Please enter emailid");
			return false;
		}
		else
		{
			if (required && !Pattern.matches(regex, text.trim()))
			{
				editText.setError(errMsg);
				return false;
			}
		}
		// pattern doesn't match so returning false
		/*if (required && !Pattern.matches(regex, text.trim()))
		{
			editText.setError(errMsg);
			return false;
		}*/
		;

		return true;
	}

	// check the input field has any text or not
	// return true if it contains text otherwise false
	public static boolean hasText(EditText editText)
	{

		String text = editText.getText().toString().trim();
		editText.setError(null);

		// length 0 means there is no text
		if (text.length() == 0 && Pattern.matches(NAME_REGEX, text))
		{
			editText.setError(REQUIRED_MSG);
			return false;
		}

		return true;
	}

	public static boolean isValidPhoneNumber(EditText editText)
	{

		String text = editText.getText().toString().trim();
		editText.setError(null);

		// length 0 means there is no text
		if (text.length() == 0)
		{
			editText.setError("Please enter phone number");
			return false;
		}
		else
		{
			if (text.length() != 10)
			{
				editText.setError(PHONE_MSG);
				return false;
			}
		}

		return true;
	}

	public static boolean isNumericString(String input)
	{
		boolean result = false;

		if (input != null && input.length() > 0)
		{
			char[] charArray = input.toCharArray();

			for (char c : charArray)
			{
				if (c >= '0' && c <= '9')
				{
					// it is a digit
					result = true;
				}
				else
				{
					result = false;
					break;
				}
			}
		}
		return result;
	}
}
