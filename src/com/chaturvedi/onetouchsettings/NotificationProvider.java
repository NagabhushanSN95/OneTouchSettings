package com.chaturvedi.onetouchsettings;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.widget.RemoteViews;

public class NotificationProvider
{
	private Activity context;
	private NotificationCompat.Builder notificationBuilder;
	private NotificationManager manager;
	private Notification notification;
	private RemoteViews notificationView;
	private Intent notificationIntent;
	private PendingIntent pendingNotificationIntent;
	private PendingIntent pendingMobileDataIntent;
	private PendingIntent pendingSoundIntent;
	private PendingIntent pendingVibrationIntent;
	
	public NotificationProvider(Activity activity)
	{
		context=activity;
	}
	
	@SuppressLint({ "InlinedApi", "NewApi" })
	public void createNotification()
	{
		if(android.os.Build.VERSION.SDK_INT<11)
		{
			notificationBuilder=new NotificationCompat.Builder(context);
			notificationBuilder.setSmallIcon(R.drawable.notification_icon);
			notificationBuilder.setContentTitle("One Touch Settings");
			notificationBuilder.setContentText("Click Here To Open One Touch Settings");
			notificationBuilder.setPriority(Notification.PRIORITY_HIGH);
			notificationBuilder.setOngoing(true);
			notification=notificationBuilder.build();
			
			notificationIntent=new Intent(context, MainActivity.class);
			TaskStackBuilder stackBuilder=TaskStackBuilder.create(context);
			stackBuilder.addParentStack(MainActivity.class);
			stackBuilder.addNextIntent(notificationIntent);
			pendingNotificationIntent=stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
			notificationBuilder.setContentIntent(pendingNotificationIntent);
			
			notification=notificationBuilder.build();
			manager=(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
			manager.notify(R.id.notification_sound, notification);
		}
		else
		{
			notificationBuilder=new NotificationCompat.Builder(context);
			notificationBuilder.setSmallIcon(R.drawable.notification_icon);
			notificationBuilder.setPriority(Notification.PRIORITY_HIGH);
			
			setNotificationLayout();
			notificationIntent=new Intent(context, MainActivity.class);
			pendingNotificationIntent=PendingIntent.getActivity(context, 0, notificationIntent, 0);
			notificationBuilder.setOngoing(true);
			notification=notificationBuilder.build();
			
			notification.contentView=notificationView;
			notification.contentIntent=pendingNotificationIntent;
			notification.flags=Notification.FLAG_NO_CLEAR;

			pendingMobileDataIntent=PendingIntent.getActivity(context, 0, new Intent(context, MobileDataNotificationListener.class), PendingIntent.FLAG_UPDATE_CURRENT);
			notificationView.setOnClickPendingIntent(R.id.notification_internet, pendingMobileDataIntent);
			pendingSoundIntent=PendingIntent.getActivity(context, 0, new Intent(context, SoundNotificationListener.class), PendingIntent.FLAG_UPDATE_CURRENT);
			notificationView.setOnClickPendingIntent(R.id.notification_sound, pendingSoundIntent);
			pendingVibrationIntent=PendingIntent.getActivity(context, 0, new Intent(context, VibrationNotificationListener.class), PendingIntent.FLAG_UPDATE_CURRENT);
			notificationView.setOnClickPendingIntent(R.id.notification_vibrate, pendingVibrationIntent);
			
			manager=(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
			manager.notify(R.id.notification_sound, notification);
		}
	}
	
	private void setNotificationLayout()
	{
		int mobileDataState, soundState, vibrationState;
		new ManagerWifi(context);
		new ManagerMobileData(context);
		new ManagerBluetooth(context);
		new ManagerAudio(context);
		
		if(ManagerMobileData.getMobileDataState())
			mobileDataState=1;
		else
			mobileDataState=0;
		
		if(ManagerAudio.getSoundState())
			soundState=1;
		else
			soundState=0;
		
		if(ManagerAudio.getVibrationState())
			vibrationState=1;
		else
			vibrationState=0;
		
		int state=Integer.parseInt(""+mobileDataState+""+soundState+""+vibrationState);
		switch(state)
		{
			case 000:
				notificationView=new RemoteViews(context.getPackageName(), R.layout.notification_layout_000);
				break;
				
			case 001:
				notificationView=new RemoteViews(context.getPackageName(), R.layout.notification_layout_001);
				break;
				
			case 010:
				notificationView=new RemoteViews(context.getPackageName(), R.layout.notification_layout_010);
				break;
				
			case 011:
				notificationView=new RemoteViews(context.getPackageName(), R.layout.notification_layout_011);
				break;
				
			case 100:
				notificationView=new RemoteViews(context.getPackageName(), R.layout.notification_layout_100);
				break;
				
			case 101:
				notificationView=new RemoteViews(context.getPackageName(), R.layout.notification_layout_101);
				break;
				
			case 110:
				notificationView=new RemoteViews(context.getPackageName(), R.layout.notification_layout_110);
				break;
				
			case 111:
				notificationView=new RemoteViews(context.getPackageName(), R.layout.notification_layout_111);
				break;
				
			default:
				notificationView=new RemoteViews(context.getPackageName(), R.layout.notification_layout);
				break;
		}
	}
}
