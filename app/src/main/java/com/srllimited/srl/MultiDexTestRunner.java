package com.srllimited.srl;

/**
 * Created by RuchiTiwari on 1/31/2017.
 */

import android.os.Bundle;
import android.support.multidex.MultiDex;
import android.test.InstrumentationTestRunner;

/**
 * {@link android.test.InstrumentationTestRunner} for testing application needing multidex support.
 */
public class MultiDexTestRunner extends InstrumentationTestRunner
{
	@Override
	public void onCreate(Bundle arguments)
	{
		MultiDex.install(getTargetContext());
		super.onCreate(arguments);
	}
}