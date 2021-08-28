package com.chaturvedi.onetouchsettings;

import android.app.Activity;
import android.os.Bundle;

public class SoundNotificationListener extends Activity
{
	//private AudioManager audioManager;
	//private boolean soundState;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ManagerAudio.toggleSoundState();
		finish();
	}
}
