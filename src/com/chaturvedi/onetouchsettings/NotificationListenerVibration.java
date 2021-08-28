package com.chaturvedi.onetouchsettings;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;

public class NotificationListenerVibration extends Activity
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_main);
		
		new ManagerAudio(this);
		ManagerAudio.readAudioState((AudioManager)this.getSystemService(Context.AUDIO_SERVICE));
		ManagerAudio.toggleVibrationState();
		new NotificationProvider(this).createNotification();
		finish();
	}
}
