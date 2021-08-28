package com.chaturvedi.onetouchsettings;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;

public class ManagerAudio
{
	private static boolean soundState;
	private static boolean vibrationState;
	
	@SuppressWarnings("unused")
	//private static Activity context;
	private static Context context;
	private static AudioManager audioManager;
	
	public ManagerAudio(Activity activity)
	{
		context=activity;
	}
	
	public ManagerAudio(Context cxt)
	{
		context=cxt;
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
	
	public static void toggleSoundState()
	{
		soundState=!soundState;
		setSoundState(soundState);
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
	
	public static void toggleVibrationState()
	{
		vibrationState=!vibrationState;
		setVibrationState(vibrationState);
	}
	
	public static boolean getVibrationState()
	{
		return vibrationState;
	}
	
	public static int getSoundIcon()
	{
		if(soundState)
			return R.drawable.sound_on;
		else
			return R.drawable.sound_off;
	}
	
	public static int getVibrationIcon()
	{
		if(vibrationState)
			return R.drawable.vibration_on;
		else
			return R.drawable.vibration_off;
	}
}
