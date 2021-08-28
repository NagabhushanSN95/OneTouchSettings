package com.chaturvedi.onetouchsettings;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.widget.Toast;

public class PhoneStateManager extends Activity
{
	private static boolean wifiState;
	private static boolean isTetherable=false;
	private static boolean tetheringState;
	private static boolean mobileDataState;
	private static int mobileDataSim;
	private static boolean bluetoothState;
	private static boolean visibilityState;
	private static boolean soundState;
	private static boolean vibrationState;
	
	private static Activity context;
	private static WifiManager wifiManager;
	private static ConnectivityManager tetheringManager;
	private static Method tetheringMethod;
	private static ConnectivityManager mobileDataManager;
	@SuppressWarnings("rawtypes")
	private static Class mobileDataClass;
	private static Method mobileDataMethod;
	private static Method mobileDataSimMethod;
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
		}
		
		// Check Tethering State*/
		try
		{
			tetheringManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
			tetheringMethod=ConnectivityManager.class.getDeclaredMethod("tether");
			tetheringMethod.setAccessible(true);
			
			Class tetheringClass=Class.forName(mobileDataManager.getClass().getName());
			tetheringMethod=tetheringClass.getDeclaredMethod("isTetheringSupported");
			tetheringMethod.setAccessible(true);
			isTetherable=(Boolean)tetheringMethod.invoke(tetheringManager);
		}
		catch(Exception e)
		{
			
		}
		
		// Check Mobile-Data State
		try
		{
			mobileDataManager=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
			
			// Set method to check the mobile data state
			mobileDataClass=Class.forName(mobileDataManager.getClass().getName());
			mobileDataMethod=mobileDataClass.getDeclaredMethod("getMobileDataEnabled");
			mobileDataMethod.setAccessible(true);
			mobileDataState=(Boolean)mobileDataMethod.invoke(mobileDataManager);
			
			// Change method to set the mobile data on or off
			mobileDataMethod=mobileDataClass.getDeclaredMethod("setMobileDataEnabled", boolean.class);
			mobileDataMethod.setAccessible(true);
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
	
	public static boolean isTetherable()
	{
		return isTetherable;
	}
	
	public static void setTetheringState(boolean state)
	{
		tetheringState=state;
		if(tetheringState && !isTetherable)
		{
			AlertDialog.Builder notTetherable=new AlertDialog.Builder(context);
			notTetherable.setTitle("Unable To Tether");
			notTetherable.setMessage("Connect USB First And Then Try Again");
			notTetherable.setPositiveButton("OK", null);
			notTetherable.show();
			tetheringState=false;
		}
		else
		{
			try
			{
				tetheringManager=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
	            Method[] tetheringMethods = tetheringManager.getClass().getDeclaredMethods();
	            String str = "";
	            if (tetheringState)
	                str = "tether";
	            else
	                str = "untether";
	            for (Method method : tetheringMethods)
	            {
	                if (method.getName().equals(str))
	                {
	                    try
	                    {
	                    	method.invoke(tetheringManager, "usb0");
	                        //Integer code = (Integer) method.invoke(tetheringManager, "usb0");
	                    	//code = (Integer) method.invoke(tetheringManager, "setting TH");
	                    }
	                    catch (IllegalArgumentException e)
	                    {
	                        e.printStackTrace();
	                    }
	                    catch (IllegalAccessException e)
	                    {
	                        e.printStackTrace();
	                    }
	                    catch (InvocationTargetException e)
	                    {
	                        e.printStackTrace();
	                    }
	                }
	            }
	        }
			catch(Exception e)
			{
	            
	        }
		}
    }
	
	public static boolean getTetheringState()
	{
		return tetheringState;
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
			
		}
	}
	
	public static boolean getMobileDataState()
	{
		return mobileDataState;
	}
	
	@SuppressWarnings("unchecked")
	public static void setMobileDataSim(int sim)
	{
		mobileDataSim=sim;
		// Set Method to change the Mobile Data Sim
		try
		{
			switch(mobileDataSim)
			{
				case 1:
					mobileDataSimMethod=mobileDataClass.getDeclaredMethod("switchToSim1DataNetwork");
					mobileDataSimMethod.setAccessible(true);
					mobileDataSimMethod.invoke(mobileDataManager);
					
					mobileDataSimMethod=mobileDataClass.getDeclaredMethod("onSwitchToSim1DataNetworkCallback");
					Toast.makeText(context, mobileDataSimMethod.getParameterTypes().getClass().toString(), Toast.LENGTH_LONG).show();
					mobileDataSimMethod.setAccessible(true);
					mobileDataSimMethod.invoke(mobileDataManager);
					Toast.makeText(context, mobileDataSimMethod.getParameterTypes().getClass().toString(), Toast.LENGTH_LONG).show();
					break;
					
				case 2:
					mobileDataSimMethod=mobileDataClass.getDeclaredMethod("switchToSim2DataNetwork");
					mobileDataSimMethod.setAccessible(true);
					mobileDataSimMethod.invoke(mobileDataManager);
					break;
					
				default:
					mobileDataSimMethod=mobileDataClass.getDeclaredMethod("switchToSim1DataNetwork");
					mobileDataSimMethod.setAccessible(true);
					mobileDataSimMethod.invoke(mobileDataManager);
					Toast.makeText(context, "Data Network "+"Changed To Default Sim 1", Toast.LENGTH_SHORT).show();
					break;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	public static int getMobileDataSim()
	{
		return mobileDataSim;
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
