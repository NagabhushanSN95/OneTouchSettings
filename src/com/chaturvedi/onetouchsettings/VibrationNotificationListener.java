package com.chaturvedi.onetouchsettings;

import android.app.Activity;
import android.os.Bundle;

public class VibrationNotificationListener extends Activity
{
	//private AudioManager audioManager;
	//private boolean vibrationState;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ManagerAudio.toggleVibrationState();
		finish();
	}
}
