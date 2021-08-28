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
	//private Activity context;
	private Context context;
	private NotificationCompat.Builder notificationBuilder;
	private NotificationManager manager;
	private Notification notification;
	private RemoteViews notificationView;
	private Intent notificationIntent;
	private PendingIntent pendingNotificationIntent;
	private PendingIntent pendingMobileDataIntent;
	private PendingIntent pendingSoundIntent;
	private PendingIntent pendingVibrationIntent;
	private PendingIntent pendingBluetoothIntent;
	private PendingIntent pendingBluetoothVisibilityIntent;
	private PendingIntent pendingAutoRotationIntent;
	
	public NotificationProvider(Activity activity)
	{
		context=activity;
	}
	
	public NotificationProvider(Context cxt)
	{
		context = cxt;
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
			notificationBuilder.setOngoing(true);
			
			notificationView=new RemoteViews(context.getPackageName(), R.layout.notification_layout);
			setNotificationLayout();
			notificationIntent=new Intent(context, MainActivity.class);
			pendingNotificationIntent=PendingIntent.getActivity(context, 0, notificationIntent, 0);
			notification=notificationBuilder.build();
			
			notification.contentView=notificationView;
			notification.contentIntent=pendingNotificationIntent;
			notification.flags=Notification.FLAG_NO_CLEAR;

			Intent newIntent=new Intent(context, NotificationListenerVibration.class);
			newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			pendingMobileDataIntent=PendingIntent.getActivity(context, 0, new Intent(context, NotificationListenerMobileData.class), PendingIntent.FLAG_UPDATE_CURRENT);
			notificationView.setOnClickPendingIntent(R.id.notification_internet, pendingMobileDataIntent);
			pendingSoundIntent=PendingIntent.getActivity(context, 0, new Intent(context, NotificationListenerSound.class), PendingIntent.FLAG_UPDATE_CURRENT);
			notificationView.setOnClickPendingIntent(R.id.notification_sound, pendingSoundIntent);
			pendingVibrationIntent=PendingIntent.getActivity(context, 0, new Intent(context, NotificationListenerVibration.class), PendingIntent.FLAG_UPDATE_CURRENT);
			notificationView.setOnClickPendingIntent(R.id.notification_vibrate, pendingVibrationIntent);
			pendingBluetoothIntent=PendingIntent.getActivity(context, 0, new Intent(context, NotificationListenerBluetooth.class), PendingIntent.FLAG_UPDATE_CURRENT);
			notificationView.setOnClickPendingIntent(R.id.notification_bluetooth, pendingBluetoothIntent);
			pendingBluetoothVisibilityIntent=PendingIntent.getActivity(context, 0, new Intent(context, NotificationListenerBluetoothVisibility.class), PendingIntent.FLAG_UPDATE_CURRENT);
			notificationView.setOnClickPendingIntent(R.id.notification_bluetooth_visibility, pendingBluetoothVisibilityIntent);
			pendingAutoRotationIntent=PendingIntent.getActivity(context, 0, new Intent(context, NotificationListenerAutoRotation.class), PendingIntent.FLAG_UPDATE_CURRENT);
			notificationView.setOnClickPendingIntent(R.id.notification_auto_rotation, pendingAutoRotationIntent);
			
			manager=(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
			manager.notify(R.id.notification_sound, notification);
		}
	}

	private void setNotificationLayout()
	{
		new ManagerWifi(context);
		new ManagerMobileData(context);
		new ManagerBluetooth(context);
		new ManagerAudio(context);
		
		// Set Image For Mobile Data Button
		if(ManagerMobileData.getMobileDataState())
		{
			notificationView.setImageViewResource(R.id.notification_internet, R.drawable.notification_mobile_data_on);
		}
		else
		{
			notificationView.setImageViewResource(R.id.notification_internet, R.drawable.notification_mobile_data_off);
		}
		
		// Set Image For Sound Button
		notificationView.setImageViewResource(R.id.notification_sound, ManagerAudio.getSoundNotificationIcon());
		
		// Set Image For Sound Button
		notificationView.setImageViewResource(R.id.notification_vibrate, ManagerAudio.getVibrationNotificationIcon());
		
		// Set Image For Bluetooth Button
		notificationView.setImageViewResource(R.id.notification_bluetooth, ManagerBluetooth.getBluetoothNotificationIcon());
		
		// Set Image For Bluetooth Visibility Button
		notificationView.setImageViewResource(R.id.notification_bluetooth_visibility, ManagerBluetooth.getVisibilityNotificationIcon());
		
		// Set Image For Auto Rotation Button
		notificationView.setImageViewResource(R.id.notification_auto_rotation, ManagerDisplay.getAutoRotationNotificationIcon());
	}
}
