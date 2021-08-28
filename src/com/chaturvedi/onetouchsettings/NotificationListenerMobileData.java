package com.chaturvedi.onetouchsettings;

import android.app.Activity;
import android.os.Bundle;

public class NotificationListenerMobileData extends Activity
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		new ManagerMobileData(this);
		ManagerMobileData.readMobileDataState();
		ManagerMobileData.toggleMobileDataState();
		new NotificationProvider(this).createNotification();
		finish();
	}
}
