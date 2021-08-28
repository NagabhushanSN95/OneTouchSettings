package com.chaturvedi.onetouchsettings;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends Activity
{
	private static final String SHARED_PREFERENCES_SETUP = "setup_preferences";
	private static final String KEY_DISCLAIMER = "disclaimer";
	
	private static ImageButton wifiButton;
	private static ImageButton tetheringButton;
	private static ImageButton mobileDataButton;
	private static ImageButton mobileDataSimButton;
	private static ImageButton bluetoothButton;
	private static ImageButton visibilityButton;
	private static ImageButton soundButton;
	private static ImageButton vibrationButton;
	private static ImageButton autoRotationButton;
	
	private NotificationProvider notification;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		SharedPreferences preferences = this.getSharedPreferences(SHARED_PREFERENCES_SETUP, 0);
		if(preferences.contains(KEY_DISCLAIMER))
		{
			if(!preferences.getBoolean(KEY_DISCLAIMER, false))
			{
				displayDisclaimer();
			}
		}
		else
		{
			displayDisclaimer();
		}
		
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
		
		autoRotationButton=(ImageButton)findViewById(R.id.auto_rotation);
		autoRotationButton.setOnClickListener(new AutoRotationListener());
		
		new ManagerWifi(this);
		ManagerWifi.readWifiState();
		
		new ManagerMobileData(this);
		ManagerMobileData.readMobileDataState();
		
		new ManagerBluetooth(this);
		ManagerBluetooth.readBluetoothState();
		
		new ManagerAudio(this);
		ManagerAudio.readAudioState((AudioManager)this.getSystemService(Context.AUDIO_SERVICE));
		
		new ManagerDisplay(this);
		ManagerDisplay.readDisplayState();
		
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
		autoRotationButton.setImageResource(ManagerDisplay.getAutoRotationIcon());
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
			ManagerMobileData.toggleTetheringState();
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
			ManagerBluetooth.toggleVisibility();
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
	
	private class AutoRotationListener implements OnClickListener
	{
		@Override
		public void onClick(View v)
		{
			ManagerDisplay.toggleAutoRotationState();
		}
	}
	
	private void displayDisclaimer()
	{
		DisplayMetrics displayMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		int screenWidth = displayMetrics.widthPixels;
		int screenHeight = displayMetrics.heightPixels;
		String disclaimerText = "";
		InputStream inputStream = getResources().openRawResource(R.raw.disclaimer);
		BufferedReader disclaimerReader = new BufferedReader(new InputStreamReader(inputStream));
		try
		{
			String line=disclaimerReader.readLine();
			while(line!=null)
			{
				disclaimerText += line + "\n";
				line=disclaimerReader.readLine();
			}
		}
		catch(Exception e)
		{
			Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
		}
		
		AlertDialog.Builder disclaimerDialog = new AlertDialog.Builder(this);
		disclaimerDialog.setTitle("DISCLAIMER!");
		disclaimerDialog.setMessage(disclaimerText);
		disclaimerDialog.setCancelable(false);
		disclaimerDialog.setPositiveButton("Understood", null);
		disclaimerDialog.show().getWindow().setLayout((int) (screenWidth*0.9), (int) (screenHeight*0.5));
		
		SharedPreferences preferences = this.getSharedPreferences(SHARED_PREFERENCES_SETUP, 0);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putBoolean(KEY_DISCLAIMER, true);
		editor.commit();
	}
}
