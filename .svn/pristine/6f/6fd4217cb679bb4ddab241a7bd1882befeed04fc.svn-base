package com.srllimited.srl.utilities;

import com.srllimited.srl.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by sri on 12/14/2016.
 */

public class CustomTextView extends TextView
{
	TypedArray Canvasattrs = null;

	;
	int CurvatureDegree;
	boolean isCurvature = false;
	String direction;
	String RVal;

	public CustomTextView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		init(context, attrs);
	}

	public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);
		init(context, attrs);
	}

	private void init(Context context, AttributeSet attrs)
	{

		if (!this.isInEditMode())
		{ // used for preview while designing.
			TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TextView_TypeFace);
			Typeface font = null;
			String Type = a.getString(R.styleable.TextView_TypeFace_TypeFace);
			if (Type == null)
			{
				UserTypeFace.SetRegular(this); //Set Default Font if font is not defined in xml
				return;
			}
			setStyle(Type);
		}
		else
		{
			setTypeface(Typeface.DEFAULT, Typeface.NORMAL);
		}
	}

	private void setStyle(String style)
	{
		TextStyle value = TextStyle.valueOf(style); //convert String to ENUM
		switch (value)
		{
			case BOLD:
				UserTypeFace.SetBold(this);
				break;
			case LIGHT:
				UserTypeFace.Setlight(this);
				break;
			case REGULAR:
				UserTypeFace.Setthin(this);
				break;
			case SEMIBOLD:
				UserTypeFace.SetSEMIBOLD(this);
				break;
			case EXOREGULAR:
				UserTypeFace.SetRegular(this);
				break;
			case BOLDLARGE:
				UserTypeFace.SetBoldLarge(this);
				break;
		}

	}

	public enum TextStyle
	{
		BOLD, LIGHT, REGULAR, SEMIBOLD, EXOREGULAR, BOLDLARGE
	}
}
