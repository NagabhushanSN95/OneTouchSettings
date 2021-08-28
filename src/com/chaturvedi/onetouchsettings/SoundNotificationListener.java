package com.chaturvedi.onetouchsettings;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;

public class SoundNotificationListener extends Activity
{
	private AudioManager audioManager;
	private boolean soundState;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		audioManager=(AudioManager)getSystemService(Context.AUDIO_SERVICE);
		PhoneStateManager.readAudioState(audioManager);
		soundState=PhoneStateManager.getSoundState();
		soundState=!soundState;													// Toggle State
		PhoneStateManager.setSoundState(soundState);
		finish();
	}
}
