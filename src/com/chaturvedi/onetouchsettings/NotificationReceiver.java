package com.chaturvedi.onetouchsettings;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;

public class NotificationReceiver extends BroadcastReceiver
{
	public NotificationReceiver()
	{
		
	}

	@Override
	public void onReceive(Context context, Intent intent)
	{
		if(intent.getAction().equals("Wifi"))
		{
			new ManagerWifi(context);
			ManagerWifi.readWifiState();
			ManagerWifi.toggleWifiState();
			new NotificationProvider(context).createNotification();
		}
		else if(intent.getAction().equals("Mobile_Data"))
		{
			new ManagerMobileData(context);
			ManagerMobileData.readMobileDataState();
			ManagerMobileData.toggleMobileDataState();
			new NotificationProvider(context).createNotification();
		}
		else if(intent.getAction().equals("Sound"))
		{
			new ManagerAudio(context);
			ManagerAudio.readAudioState((AudioManager)context.getSystemService(Context.AUDIO_SERVICE));
			ManagerAudio.toggleSoundState();
			new NotificationProvider(context).createNotification();
		}
		else if(intent.getAction().equals("Vibration"))
		{
			new ManagerAudio(context);
			ManagerAudio.readAudioState((AudioManager)context.getSystemService(Context.AUDIO_SERVICE));
			ManagerAudio.toggleVibrationState();
			new NotificationProvider(context).createNotification();
		}
		else if(intent.getAction().equals("Bluetooth"))
		{
			new ManagerBluetooth(context);
			ManagerBluetooth.readBluetoothState();
			ManagerBluetooth.toggleBluetoothState();
			new NotificationProvider(context).createNotification();
		}
		else if(intent.getAction().equals("Bluetooth_Visibility"))
		{
			new ManagerBluetooth(context);
			ManagerBluetooth.readBluetoothState();
			ManagerBluetooth.toggleVisibility();
			new NotificationProvider(context).createNotification();
		}
		else if(intent.getAction().equals("Auto_Rotation"))
		{
			new ManagerDisplay(context);
			ManagerDisplay.readDisplayState();
			ManagerDisplay.toggleAutoRotationState();
			new NotificationProvider(context).createNotification();
		}
	}
}
