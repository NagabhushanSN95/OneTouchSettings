package com.chaturvedi.onetouchsettings;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class MainActivity extends Activity
{
	private static ImageButton wifiButton;
	private static ImageButton tetheringButton;
	private static ImageButton mobileDataButton;
	private static ImageButton mobileDataSimButton;
	private static ImageButton bluetoothButton;
	private static ImageButton visibilityButton;
	private static ImageButton soundButton;
	private static ImageButton vibrationButton;
	
	private NotificationProvider notification;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		wifiButton=(ImageButton)findViewById(R.id.wifi);
		wifiButton.setOnClickListener(new WifiListener());
		
		mobileDataButton=(ImageButton)findViewById(R.id.internet);
		mobileDataButton.setOnClickListener(new InternetListener());
		mobileDataSimButton=(ImageButton)findViewById(R.id.mobile_data_sim);
		mobileDataSimButton.setOnClickListener(new MobileDataSimListener());
		tetheringButton=(ImageButton)findViewById(R.id.usb_tethering);
		tetheringButton.setOnClickListener(new TetheringListener());
		
		bluetoothButton=(ImageButton)findViewById(R.id.bluetooth);
		bluetoothButton.setOnClickListener(new BluetoothListener());
		visibilityButton=(ImageButton)findViewById(R.id.bluetooth_visibility);
		visibilityButton.setOnClickListener(new VisibilityListener());
		
		soundButton=(ImageButton)findViewById(R.id.sound);
		soundButton.setOnClickListener(new SoundListener());
		vibrationButton=(ImageButton)findViewById(R.id.vibrate);
		vibrationButton.setOnClickListener(new VibrateListener());
		
		
		
		new ManagerWifi(this);
		ManagerWifi.readWifiState();
		
		new ManagerMobileData(this);
		ManagerMobileData.readMobileDataState();
		
		new ManagerBluetooth(this);
		ManagerBluetooth.readBluetoothState();
		
		new ManagerAudio(this);
		ManagerAudio.readAudioState((AudioManager)this.getSystemService(Context.AUDIO_SERVICE));
		
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
					
					new ManagerWifi(MainActivity.this);
					ManagerWifi.readWifiState();
					
					new ManagerMobileData(MainActivity.this);
					ManagerMobileData.readMobileDataState();
					
					new ManagerBluetooth(MainActivity.this);
					ManagerBluetooth.readBluetoothState();
					
					new ManagerAudio(MainActivity.this);
					ManagerAudio.readAudioState((AudioManager)MainActivity.this.getSystemService(Context.AUDIO_SERVICE));
					
					setIcons();
					
					notification.createNotification();
				}
			});
			
		}
	}
	
	private void setIcons()
	{
		wifiButton.setImageResource(ManagerWifi.getWifiIcon());
		mobileDataButton.setImageResource(ManagerMobileData.getMobileDataIcon());
		mobileDataSimButton.setImageResource(ManagerMobileData.getMobileDataSimIcon());
		tetheringButton.setImageResource(ManagerMobileData.getTetheringIcon());
		bluetoothButton.setImageResource(ManagerBluetooth.getBluetoothIcon());
		visibilityButton.setImageResource(ManagerBluetooth.getVisibilityIcon());
		soundButton.setImageResource(ManagerAudio.getSoundIcon());
		vibrationButton.setImageResource(ManagerAudio.getVibrationIcon());
	}
	
	private class WifiListener implements OnClickListener
	{

		@Override
		public void onClick(View v)
		{
			ManagerWifi.toggleWifiState();
		}
		
	}
	
	private class TetheringListener implements OnClickListener
	{

		@Override
		public void onClick(View v)
		{
			ManagerMobileData.toggleMobileDataSim();
		}
		
	}
	
	private class InternetListener implements OnClickListener
	{

		@Override
		public void onClick(View v)
		{
			ManagerMobileData.toggleMobileDataState();
		}
		
	}
	
	private class MobileDataSimListener implements OnClickListener
	{

		@Override
		public void onClick(View v)
		{
			ManagerMobileData.toggleMobileDataSim();
		}
		
	}
	
	private class BluetoothListener implements OnClickListener
	{

		@Override
		public void onClick(View v)
		{
			ManagerBluetooth.toggleBluetoothState();
		}
		
	}
	
	private class VisibilityListener implements OnClickListener
	{

		@Override
		public void onClick(View v)
		{
			ManagerBluetooth.setVisibility();
		}
		
	}
	
	private class SoundListener implements OnClickListener
	{

		@Override
		public void onClick(View v)
		{
			ManagerAudio.toggleSoundState();
		}
		
	}
	
	private class VibrateListener implements OnClickListener
	{

		@Override
		public void onClick(View v)
		{
			ManagerAudio.toggleVibrationState();
		}
		
	}
}
