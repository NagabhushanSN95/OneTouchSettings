package com.chaturvedi.onetouchsettings;

import java.lang.reflect.Method;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;

public class MobileDataNotificationListener extends Activity
{
	private boolean mobileDataState;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		readMobileDataState();
		mobileDataState=PhoneStateManager.getMobileDataState();
		mobileDataState=!mobileDataState;													// Toggle State
		setMobileDataState(mobileDataState);
		finish();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void readMobileDataState()
	{
		try
		{
			ConnectivityManager mobileDataManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
			Method mobileDataMethod=ConnectivityManager.class.getDeclaredMethod("setMobileDataEnabled", boolean.class);
			mobileDataMethod.setAccessible(true);
			
			Class cmClass=Class.forName(mobileDataManager.getClass().getName());
			Method method=cmClass.getDeclaredMethod("getMobileDataEnabled");
			method.setAccessible(true);
			mobileDataState=(Boolean)method.invoke(mobileDataManager);
		}
		catch(Exception e)
		{
			
		}
	}

	private void setMobileDataState(boolean state)
	{
		try
		{
			ConnectivityManager mobileDataManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
			Method mobileDataMethod=ConnectivityManager.class.getDeclaredMethod("setMobileDataEnabled", boolean.class);
			mobileDataMethod.setAccessible(true);
			try
			{
				mobileDataMethod.invoke(mobileDataManager, mobileDataState);
			}
			catch(Exception e)
			{
				
			}
		}
		catch(Exception e)
		{
			
		}
	}
}
