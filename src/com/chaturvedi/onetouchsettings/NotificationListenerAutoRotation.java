package com.chaturvedi.onetouchsettings;

import android.app.Activity;
import android.os.Bundle;

public class NotificationListenerAutoRotation extends Activity
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		new ManagerDisplay(this);
		ManagerDisplay.readDisplayState();
		ManagerDisplay.toggleAutoRotationState();
		new NotificationProvider(this).createNotification();
		finish();
	}
}
