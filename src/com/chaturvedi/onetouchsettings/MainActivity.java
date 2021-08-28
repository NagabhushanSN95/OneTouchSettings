package com.chaturvedi.onetouchsettings;

import java.lang.reflect.Method;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class MainActivity extends Activity
{
	private static boolean wifiState;
	private static boolean tetheringState;
	private static boolean mobileDataState;
	private static boolean bluetoothState;
	private static boolean visibilityState;
	private static boolean soundState;
	private static boolean vibrationState;

	private static ImageButton wifiButton;
	private static ImageButton tetheringButton;
	private static ImageButton internetButton;
	private static ImageButton bluetoothButton;
	private static ImageButton visibilityButton;
	private static ImageButton soundButton;
	private static ImageButton vibrateButton;
	
	private static ConnectivityManager mobileDataManager;
	private static Method mobileDataMethod;
	
	private NotificationProvider notification;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		wifiButton=(ImageButton)findViewById(R.id.wifi);
		wifiButton.setOnClickListener(new WifiListener());
		tetheringButton=(ImageButton)findViewById(R.id.usb_tethering);
		tetheringButton.setOnClickListener(new TetheringListener());
		internetButton=(ImageButton)findViewById(R.id.internet);
		internetButton.setOnClickListener(new InternetListener());
		bluetoothButton=(ImageButton)findViewById(R.id.bluetooth);
		bluetoothButton.setOnClickListener(new BluetoothListener());
		visibilityButton=(ImageButton)findViewById(R.id.bluetooth_visibility);
		visibilityButton.setOnClickListener(new VisibilityListener());
		
		soundButton=(ImageButton)findViewById(R.id.sound);
		soundButton.setOnClickListener(new SoundListener());
		vibrateButton=(ImageButton)findViewById(R.id.vibrate);
		vibrateButton.setOnClickListener(new VibrateListener());
		try
		{
			mobileDataManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
			mobileDataMethod=ConnectivityManager.class.getDeclaredMethod("setMobileDataEnabled", boolean.class);
			mobileDataMethod.setAccessible(true);
		}
		catch(Exception e)
		{
			
		}
		PhoneStateManager.readPhoneState(this);
		wifiState=PhoneStateManager.getWifiState();
		tetheringState=PhoneStateManager.getTetheringState();
		readMobileDataState();
		bluetoothState=PhoneStateManager.getBluetoothState();
		visibilityState=PhoneStateManager.getVisibilityState();
		soundState=PhoneStateManager.getSoundState();
		vibrationState=PhoneStateManager.getVibrationState();
		notification=new NotificationProvider(this);
		notification.createNotification();
		Timer timer=new Timer();
		Refresh refresher= new Refresh();
		timer.scheduleAtFixedRate(refresher, 0, 1000);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public  class Refresh extends TimerTask
	{
		@Override
		public void run() 
		{
			runOnUiThread(new Runnable()
			{
				@Override
				public void run()
				{
					PhoneStateManager.readPhoneState(MainActivity.this);
					wifiState=PhoneStateManager.getWifiState();
					tetheringState=PhoneStateManager.getTetheringState();
					readMobileDataState();
					bluetoothState=PhoneStateManager.getBluetoothState();
					visibilityState=PhoneStateManager.getVisibilityState();
					soundState=PhoneStateManager.getSoundState();
					vibrationState=PhoneStateManager.getVibrationState();
					setIcons();
					
					notification.createNotification();
				}
			});
			
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void readMobileDataState()
	{
		try
		{
			Class cmClass=Class.forName(mobileDataManager.getClass().getName());
			Method method=cmClass.getDeclaredMethod("getMobileDataEnabled");
			method.setAccessible(true);
			mobileDataState=(Boolean)method.invoke(mobileDataManager);
		}
		catch(Exception e)
		{
			
		}
	}
	
	public void setMobileData(boolean action)
	{
		try
		{
			mobileDataMethod.invoke(mobileDataManager, mobileDataState);
		}
		catch(Exception e)
		{
			
		}
	}
	
	private void setIcons()
	{
		if(wifiState)
			wifiButton.setImageResource(R.drawable.wifi_on);
		else
			wifiButton.setImageResource(R.drawable.wifi_off);

		if(tetheringState)
			tetheringButton.setImageResource(R.drawable.usb_tethering_on);
		else
			tetheringButton.setImageResource(R.drawable.usb_tethering_off);
		
		if(mobileDataState)
			internetButton.setImageResource(R.drawable.mobile_data_on);
		else
			internetButton.setImageResource(R.drawable.mobile_data_off);
		
		if(bluetoothState)
			bluetoothButton.setImageResource(R.drawable.bluetooth_on);
		else
			bluetoothButton.setImageResource(R.drawable.bluetooth_off);
		
		if(visibilityState)
			visibilityButton.setImageResource(R.drawable.visibility_on);
		else
			visibilityButton.setImageResource(R.drawable.visibility_off);
		
		if(soundState)
			soundButton.setImageResource(R.drawable.sound_on);
		else
			soundButton.setImageResource(R.drawable.sound_off);
		
		if(vibrationState)
			vibrateButton.setImageResource(R.drawable.vibration_on);
		else
			vibrateButton.setImageResource(R.drawable.vibration_off);
	}
	
	private class WifiListener implements OnClickListener
	{

		@Override
		public void onClick(View v)
		{
			wifiState=!wifiState;
			PhoneStateManager.setWifiState(wifiState);
		}
		
	}
	
	private class TetheringListener implements OnClickListener
	{

		@Override
		public void onClick(View v)
		{
			tetheringState=!tetheringState;
			PhoneStateManager.setTetheringState(tetheringState);
		}
		
	}
	
	private class InternetListener implements OnClickListener
	{

		@Override
		public void onClick(View v)
		{
			mobileDataState=!mobileDataState;
			setMobileData(mobileDataState);
		}
		
	}
	
	private class BluetoothListener implements OnClickListener
	{

		@Override
		public void onClick(View v)
		{
			bluetoothState=!bluetoothState;
			PhoneStateManager.setBluetoothState(bluetoothState);
		}
		
	}
	
	private class VisibilityListener implements OnClickListener
	{

		@Override
		public void onClick(View v)
		{
			PhoneStateManager.setVisibility();
		}
		
	}
	
	private class SoundListener implements OnClickListener
	{

		@Override
		public void onClick(View v)
		{
			soundState=!soundState;
			PhoneStateManager.setSoundState(soundState);
		}
		
	}
	
	private class VibrateListener implements OnClickListener
	{

		@Override
		public void onClick(View v)
		{
			vibrationState=!vibrationState;
			PhoneStateManager.setVibrationState(vibrationState);
		}
		
	}
}
