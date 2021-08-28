package com.chaturvedi.onetouchsettings;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;

public class SilentNotificationListener extends Activity
{
	private AudioManager audioManager;
	private boolean silentState;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		audioManager=(AudioManager)getSystemService(Context.AUDIO_SERVICE);
		PhoneStateManager.readAudioState(audioManager);
		silentState=PhoneStateManager.getSilentState();
		silentState=!silentState;													// Toggle State
		PhoneStateManager.setSilentState(silentState);
		finish();
	}
}
