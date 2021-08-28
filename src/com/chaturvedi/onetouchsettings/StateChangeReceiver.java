package com.chaturvedi.onetouchsettings;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;

public class StateChangeReceiver extends BroadcastReceiver
{

	public StateChangeReceiver()
	{
		
	}

	@Override
	public void onReceive(Context context, Intent intent)
	{
		final NotificationProvider notification = new NotificationProvider(context);
		notification.createNotification();
		
		new Handler().postDelayed(new Runnable()
		{
			@Override
			public void run()
			{
				notification.createNotification();
			}
			
		}, 500);
		
		new Handler().postDelayed(new Runnable()
		{
			@Override
			public void run()
			{
				notification.createNotification();
			}
			
		}, 1000);
		
		new Handler().postDelayed(new Runnable()
		{
			@Override
			public void run()
			{
				notification.createNotification();
			}
			
		}, 2000);
		
		new Handler().postDelayed(new Runnable()
		{
			@Override
			public void run()
			{
				notification.createNotification();
			}
			
		}, 3000);
	}
}
