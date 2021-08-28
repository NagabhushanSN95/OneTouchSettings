package com.chaturvedi.onetouchsettings;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;

public class NotificationListenerSound extends Activity
{
	//private AudioManager audioManager;
	//private boolean soundState;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		new ManagerAudio(this);
		ManagerAudio.readAudioState((AudioManager)this.getSystemService(Context.AUDIO_SERVICE));
		ManagerAudio.toggleSoundState();
		finish();
	}
}
