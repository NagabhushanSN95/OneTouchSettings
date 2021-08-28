package com.chaturvedi.onetouchsettings;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class NotificationService extends Service {

	public NotificationService()
	{
		// TODO Auto-generated constructor stub
	}

	@Override
	public IBinder onBind(Intent intent)
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onCreate()
	{
		super.onCreate();
		Toast.makeText(getApplicationContext(), "Notification Service Created", Toast.LENGTH_SHORT).show();
	}
	
	@Override
	public void onDestroy()
	{
		Toast.makeText(getApplicationContext(), "Notification Service Destroyed", Toast.LENGTH_SHORT).show();
		super.onDestroy();
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		Toast.makeText(getApplicationContext(), "Notification Service onStartCommand", Toast.LENGTH_SHORT).show();
		return super.onStartCommand(intent, flags, startId);
	}

}
