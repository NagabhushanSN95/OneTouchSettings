package com.chaturvedi.onetouchsettings;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class SwitchOnReceiver extends BroadcastReceiver
{
	private NotificationProvider notification;
	
	public SwitchOnReceiver()
	{
		
	}

	@Override
	public void onReceive(Context context, Intent intent)
	{
		Toast.makeText(context, "SwitchOnReceiver/onReceive Entered", Toast.LENGTH_SHORT).show();
		Intent service = new Intent(context, NotificationService.class);
		context.startService(service);
		
		Intent app = new Intent(context, MainActivity.class);
		app.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(app);
		
		if(intent.getAction().equals("android.intent.action.ACTION_BOOT_COMPLETED"))
		{
			Toast.makeText(context, "Boot Completed Receiver Received", Toast.LENGTH_SHORT).show();
			notification=new NotificationProvider(context);
			notification.createNotification();
		}
		
		notification=new NotificationProvider(context);
		notification.createNotification();
	}

}
