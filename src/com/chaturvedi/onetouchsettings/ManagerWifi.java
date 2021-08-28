package com.chaturvedi.onetouchsettings;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiManager;

public class ManagerWifi
{
	private static boolean wifiState;
	//private static Activity context;
	private static Context context;
	private static WifiManager wifiManager;
	
	public ManagerWifi(Activity activity)
	{
		context=activity;
	}
	
	public ManagerWifi(Context cxt)
	{
		context=cxt;
	}
	
	public static void readWifiState()
	{
		// Check Wifi State
		wifiManager=(WifiManager)context.getSystemService(Context.WIFI_SERVICE);
		if(wifiManager.getWifiState()==WifiManager.WIFI_STATE_ENABLED)
			wifiState=true;
		else
			wifiState=false;
		
		/*try
		{
			//Class wifiClass=Class.forName(wifiManager.getClass().getName());
			Method[] wifiMethods = wifiManager.getClass().getDeclaredMethods();
		    for (Method method : wifiMethods)
		    {
		    	method.setAccessible(true);
		  		Toast.makeText(context, ""+method.getName(), Toast.LENGTH_SHORT).show();
		    }
		}
		catch(Exception e1)
		{
			e1.printStackTrace();
		}*/
	}
	
	public static void setWifiState(boolean state)
	{
		wifiState=state;
		wifiManager.setWifiEnabled(wifiState);
	}
	
	public static void toggleWifiState()
	{
		wifiState=!wifiState;
		wifiManager.setWifiEnabled(wifiState);
	}
	
	public static boolean getWifiState()
	{
		return wifiState;
	}
	
	public static int getWifiIcon()
	{
		if(wifiState)
			return R.drawable.wifi_on;
		else
			return R.drawable.wifi_off;
	}
}
