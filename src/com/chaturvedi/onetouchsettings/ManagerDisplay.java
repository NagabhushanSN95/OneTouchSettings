package com.chaturvedi.onetouchsettings;

import android.app.Activity;
import android.content.Context;
import android.provider.Settings;

public class ManagerDisplay
{
	private static boolean autoRotationState=false;
	
	//private static Activity context;
	private static Context context;
	
	public ManagerDisplay(Activity activity)
	{
		context=activity;
	}
	
	public ManagerDisplay(Context cxt)
	{
		context=cxt;
	}
	
	public static void readDisplayState()
	{
		if(android.provider.Settings.System.getInt(context.getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, 0)==1)
			autoRotationState=true;
		else
			autoRotationState=false;
	}
	
	public static void setAutoRotation(boolean state)
	{
		autoRotationState=state;
		if(autoRotationState)
		{
			Settings.System.putInt(context.getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, 1);
			
		}
		else
		{
			Settings.System.putInt(context.getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, 0);
		}
	}
	
	public static void toggleAutoRotationState()
	{
		autoRotationState=!autoRotationState;
		setAutoRotation(autoRotationState);
	}
	
	public static boolean getAutoRotationState()
	{
		return autoRotationState;
	}
	
	public static int getAutoRotationIcon()
	{
		if(autoRotationState)
			return R.drawable.auto_rotation_on;
		else
			return R.drawable.auto_rotation_off;
	}
	
	public static int getAutoRotationNotificationIcon()
	{
		if(autoRotationState)
			return R.drawable.notification_auto_rotation_on;
		else
			return R.drawable.notification_auto_rotation_off;
	}
}
