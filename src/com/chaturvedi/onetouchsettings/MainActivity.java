package com.chaturvedi.onetouchsettings;

import java.lang.reflect.Method;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class MainActivity extends Activity
{
	private static boolean mobileDataState;
	private static boolean bluetoothState;
	private static boolean visibilityState;
	private static boolean silentState;
	private static boolean vibrationState;
	
	private static ImageButton internetButton;
	private static ImageButton bluetoothButton;
	private static ImageButton visibilityButton;
	private static ImageButton silentButton;
	private static ImageButton vibrateButton;
	
	private static ConnectivityManager mobileDataManager;
	private static Method mobileDataMethod;
	private static BluetoothAdapter bluetoothManager;
	private static AudioManager audioManager;
	
	private NotificationProvider notification;
	/*private NotificationCompat.Builder notificationBuilder;
	private NotificationManager manager;
	private Notification notification;
	private RemoteViews notificationView;
	private Intent notificationIntent;
	private PendingIntent pendingNotificationIntent;
	private PendingIntent pendingMobileDataIntent;
	private PendingIntent pendingSilentIntent;
	private PendingIntent pendingVibrationIntent;
	private TaskStackBuilder stackBuilder;*/
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		internetButton=(ImageButton)findViewById(R.id.internet);
		internetButton.setOnClickListener(new InternetListener());
		bluetoothButton=(ImageButton)findViewById(R.id.bluetooth);
		bluetoothButton.setOnClickListener(new BluetoothListener());
		visibilityButton=(ImageButton)findViewById(R.id.bluetooth_visibility);
		visibilityButton.setOnClickListener(new VisibilityListener());
		
		silentButton=(ImageButton)findViewById(R.id.silent);
		silentButton.setOnClickListener(new SilentListener());
		vibrateButton=(ImageButton)findViewById(R.id.vibrate);
		vibrateButton.setOnClickListener(new VibrateListener());
		try
		{
			mobileDataManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
			mobileDataMethod=ConnectivityManager.class.getDeclaredMethod("setMobileDataEnabled", boolean.class);
			mobileDataMethod.setAccessible(true);
			
			bluetoothManager=BluetoothAdapter.getDefaultAdapter();
			audioManager=(AudioManager)getSystemService(Context.AUDIO_SERVICE);
		}
		catch(Exception e)
		{
			
		}
		PhoneStateManager.readPhoneState(this);
		readPhoneState();
		silentState=PhoneStateManager.getSilentState();
		vibrationState=PhoneStateManager.getVibrationState();
		setMobileData(mobileDataState);
		setSilentMode(silentState);
		setVibration(vibrationState);
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
		getMenuInflater().inflate(R.menu.settings, menu);
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
					notification.createNotification();
				}
			});
			
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void readPhoneState()
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
		/*int ringerMode=audioManager.getRingerMode();
		if(ringerMode==AudioManager.RINGER_MODE_NORMAL)
		{
			silentState=false;
			if(audioManager.getVibrateSetting(AudioManager.VIBRATE_TYPE_RINGER)==AudioManager.VIBRATE_SETTING_ON)
				vibrationState=true;
			else
				vibrationState=false;
		}
		else if(ringerMode==AudioManager.RINGER_MODE_SILENT)
		{
			silentState=true;
			vibrationState=false;
		}
		else if(ringerMode==AudioManager.RINGER_MODE_VIBRATE)
		{
			silentState=true;
			vibrationState=true;
		}
		
		if(mobileDataState)
			internetButton.setImageResource(R.drawable.mobile_data_on);
		else
			internetButton.setImageResource(R.drawable.mobile_data_off);
		
		if(silentState)
			silentButton.setImageResource(R.drawable.silent);
		else
			silentButton.setImageResource(R.drawable.sound);
		
		if(vibrationState)
			vibrateButton.setImageResource(R.drawable.vibration_on);
		else
			vibrateButton.setImageResource(R.drawable.vibration_off);*/
	}
	
	/*@SuppressLint({ "InlinedApi", "NewApi" })
	private void buildNotification()
	{
		notificationBuilder=new NotificationCompat.Builder(this);
		notificationBuilder.setSmallIcon(R.drawable.app_icon);
		notificationBuilder.setPriority(Notification.PRIORITY_HIGH);
		notificationBuilder.setContentTitle("ONE TOUCH SETTINGS");
		notificationBuilder.setContentText("Tap To Open  \"One Touch Settings\"");
		notificationBuilder.setOngoing(true);
		
		/*
		stackBuilder=TaskStackBuilder.create(this);
		stackBuilder.addParentStack(SettingsActivity.class);
		stackBuilder.addNextIntent(notificationIntent);
		resultPendingIntent=stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
		notificationBuilder.setContentIntent(resultPendingIntent);
		notification=notificationBuilder.build();/
		
		notificationView=new RemoteViews(getPackageName(), R.layout.notification_layout);
		notificationIntent=new Intent(this, SettingsActivity.class);
		pendingNotificationIntent=PendingIntent.getActivity(this, 0, notificationIntent, 0);
		notification=notificationBuilder.build();
		notification.contentView=notificationView;
		notification.contentIntent=pendingNotificationIntent;
		notification.flags=Notification.FLAG_NO_CLEAR;
		
		pendingSilentIntent=PendingIntent.getActivity(this, 0, new Intent(this, SilentNotificationListener.class), PendingIntent.FLAG_UPDATE_CURRENT);
		notificationView.setOnClickPendingIntent(R.id.notification_silent, pendingSilentIntent);
		
		manager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
		manager.notify(R.id.notification_silent, notification);
	}*/
	
	public void setMobileData(boolean action)
	{
		mobileDataState=action;
		if(mobileDataState)
			internetButton.setImageResource(R.drawable.mobile_data_on);
		else
			internetButton.setImageResource(R.drawable.mobile_data_off);
		try
		{
			mobileDataMethod.invoke(mobileDataManager, mobileDataState);
		}
		catch(Exception e)
		{
			
		}
	}
	
	@SuppressWarnings("deprecation")
	public void setSilentMode(boolean action)
	{
		silentState=action;
		if(silentState)
			silentButton.setImageResource(R.drawable.silent);
		else
			silentButton.setImageResource(R.drawable.sound);
		if(silentState)
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
				audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);		// Normal And Vibrate??
				audioManager.setVibrateSetting(AudioManager.VIBRATE_TYPE_RINGER, AudioManager.VIBRATE_SETTING_ON);
			}
			else
			{
				audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);		// Only Normal, No Vibration??
				audioManager.setVibrateSetting(AudioManager.VIBRATE_TYPE_RINGER, AudioManager.VIBRATE_SETTING_OFF);
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	private void setVibration(boolean action)
	{
		vibrationState=action;
		if(vibrationState)
			vibrateButton.setImageResource(R.drawable.vibration_on);
		else
			vibrateButton.setImageResource(R.drawable.vibration_off);
		if(vibrationState)
		{
			if(silentState)
			{
				audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);		// Silent And Vibrate
			}
			else
			{
				audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);		// Normal And Vibrate??
				audioManager.setVibrateSetting(AudioManager.VIBRATE_TYPE_RINGER, AudioManager.VIBRATE_SETTING_ON);
			}
		}
		else
		{
			if(silentState)
			{
				audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);		// Only Silent, No Vibration
			}
			else
			{
				audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);		// Only Normal, No Vibration??
				audioManager.setVibrateSetting(AudioManager.VIBRATE_TYPE_RINGER, AudioManager.VIBRATE_SETTING_OFF);
			}
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
			setMobileData(bluetoothState);
		}
		
	}
	
	private class VisibilityListener implements OnClickListener
	{

		@Override
		public void onClick(View v)
		{
			visibilityState=!visibilityState;
			setMobileData(visibilityState);
		}
		
	}
	
	private class SilentListener implements OnClickListener
	{

		@Override
		public void onClick(View v)
		{
			silentState=!silentState;
			setSilentMode(silentState);
		}

		/*@Override
		public void onReceive(Context arg0, Intent arg1)
		{
			silentState=!silentState;
			if(silentState)
				silentButton.setImageResource(R.drawable.silent);
			else
				silentButton.setImageResource(R.drawable.sound);
			setSilentMode(silentState);
		}*/
		
	}
	
	private class VibrateListener implements OnClickListener
	{

		@Override
		public void onClick(View v)
		{
			vibrationState=!vibrationState;
			setVibration(vibrationState);
		}
		
	}
}
