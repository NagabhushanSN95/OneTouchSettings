package com.chaturvedi.onetouchsettings;

import java.lang.reflect.Method;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.util.Log;

public class PhoneStateManager extends Activity
{
	private static boolean wifiState;
	private static boolean mobileDataState;
	private static boolean bluetoothState;
	private static boolean visibilityState;
	private static boolean soundState;
	private static boolean vibrationState;
	
	private static Activity context;
	private static WifiManager wifiManager;
	private static ConnectivityManager mobileDataManager;
	private static Method mobileDataMethod;
	private static BluetoothAdapter bluetoothManager;
	private static AudioManager audioManager;
	
	public static void readPhoneState(Activity activity)
	{
		context=activity;
		readInternetState();
		readBluetoothState();
		readAudioState((AudioManager)context.getSystemService(Context.AUDIO_SERVICE));
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void readInternetState()
	{
		wifiManager=(WifiManager)context.getSystemService(Context.WIFI_SERVICE);
		if(wifiManager.getWifiState()==WifiManager.WIFI_STATE_ENABLED)
			wifiState=true;
		else
			wifiState=false;
		
		try
		{
			mobileDataManager=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
			mobileDataMethod=ConnectivityManager.class.getDeclaredMethod("setMobileDataEnabled", boolean.class);
			mobileDataMethod.setAccessible(true);
			
			Class cmClass=Class.forName(mobileDataManager.getClass().getName());
			mobileDataMethod=cmClass.getDeclaredMethod("getMobileDataEnabled");
			mobileDataMethod.setAccessible(true);
			mobileDataState=(Boolean)mobileDataMethod.invoke(mobileDataManager);
		}
		catch(Exception e)
		{
			
		}
	}
	
	public static void readBluetoothState()
	{
		bluetoothManager=BluetoothAdapter.getDefaultAdapter();
		if(bluetoothManager.getState()==BluetoothAdapter.STATE_ON)
			bluetoothState=true;
		else
			bluetoothState=false;
		
		visibilityState=bluetoothManager.isDiscovering();
	}
	
	@SuppressWarnings({ "deprecation" })
	public static void readAudioState(AudioManager manager)
	{
		audioManager=manager;
		int ringerMode=audioManager.getRingerMode();
		if(ringerMode==AudioManager.RINGER_MODE_NORMAL)
		{
			soundState=true;
			if(audioManager.getVibrateSetting(AudioManager.VIBRATE_TYPE_RINGER)==AudioManager.VIBRATE_SETTING_ON)
				vibrationState=true;
			else
				vibrationState=false;
		}
		else if(ringerMode==AudioManager.RINGER_MODE_SILENT)
		{
			soundState=false;
			vibrationState=false;
		}
		else if(ringerMode==AudioManager.RINGER_MODE_VIBRATE)
		{
			soundState=false;
			vibrationState=true;
		}
	}
	
	public static void setWifiState(boolean state)
	{
		wifiState=state;
		wifiManager.setWifiEnabled(wifiState);
	}
	
	public static boolean getWifiState()
	{
		return wifiState;
	}
	
	public static void setMobileDataState(boolean state)
	{
		mobileDataState=state;
		try
		{
			mobileDataMethod.invoke(mobileDataManager, mobileDataState);
		}
		catch(Exception e)
		{
			Log.d("Mobile Data Management", "Failed");
			if(mobileDataMethod==null)
				Log.d("Mobile Data Management", "Null Pointer");
		}
	}
	
	public static boolean getMobileDataState()
	{
		return mobileDataState;
	}
	
	public static void setBluetoothState(boolean state)
	{
		bluetoothState=state;
		if(bluetoothState)
			bluetoothManager.enable();
		else
			bluetoothManager.disable();
	}
	
	public static boolean getBluetoothState()
	{
		return bluetoothState;
	}
	
	public static void setVisibility()
	{
		Intent visibilityIntent=new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
		visibilityIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
		context.startActivity(visibilityIntent);
	}
	
	public static boolean getVisibilityState()
	{
		if(bluetoothManager.getScanMode()==BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE)
			visibilityState=true;
		else
			visibilityState=false;
		return visibilityState;
	}
	
	@SuppressWarnings("deprecation")
	public static void setSoundState(boolean state)
	{
		soundState=state;
		if(!soundState)
		{
			if(vibrationState)
			{
				audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);		// Silent And Vibrate
			}
			else
			{
				audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);		// Only Silent, No Vibration
			}
		}
		else
		{
			if(vibrationState)
			{
				audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);		// Sound And Vibrate
				audioManager.setVibrateSetting(AudioManager.VIBRATE_TYPE_RINGER, AudioManager.VIBRATE_SETTING_ON);
			}
			else
			{
				audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);		// Only Sound, No Vibration
				audioManager.setVibrateSetting(AudioManager.VIBRATE_TYPE_RINGER, AudioManager.VIBRATE_SETTING_OFF);
			}
		}
	}
	
	public static boolean getSoundState()
	{
		return soundState;
	}
	
	@SuppressWarnings("deprecation")
	public static void setVibrationState(boolean state)
	{
		vibrationState=state;
		if(vibrationState)
		{
			if(!soundState)
			{
				audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);		// Silent And Vibrate
			}
			else
			{
				audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);		// Sound And Vibrate
				audioManager.setVibrateSetting(AudioManager.VIBRATE_TYPE_RINGER, AudioManager.VIBRATE_SETTING_ON);
			}
		}
		else
		{
			if(!soundState)
			{
				audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);		// Only Silent, No Vibration
			}
			else
			{
				audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);		// Only Sound, No Vibration??
				audioManager.setVibrateSetting(AudioManager.VIBRATE_TYPE_RINGER, AudioManager.VIBRATE_SETTING_OFF);
			}
		}
	}
	
	public static boolean getVibrationState()
	{
		return vibrationState;
	}
}
