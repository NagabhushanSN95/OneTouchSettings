package com.chaturvedi.onetouchsettings;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class SwitchOnReceiver extends BroadcastReceiver
{
	private NotificationProvider notification;
	
	public SwitchOnReceiver()
	{
		
	}

	@Override
	public void onReceive(Context context, Intent intent)
	{
		notification=new NotificationProvider(context);
		notification.createNotification();
	}

}
