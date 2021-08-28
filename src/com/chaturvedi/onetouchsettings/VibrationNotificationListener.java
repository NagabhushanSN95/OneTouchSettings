package com.chaturvedi.onetouchsettings;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;

public class VibrationNotificationListener extends Activity
{
	private AudioManager audioManager;
	private boolean vibrationState;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		audioManager=(AudioManager)getSystemService(Context.AUDIO_SERVICE);
		PhoneStateManager.readAudioState(audioManager);
		vibrationState=PhoneStateManager.getVibrationState();
		vibrationState=!vibrationState;													// Toggle State
		PhoneStateManager.setVibrationState(vibrationState);
		finish();
	}
}
