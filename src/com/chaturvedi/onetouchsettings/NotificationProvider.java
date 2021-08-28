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
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
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
	
	private LayoutInflater notificationLayout;
	private View notificationLayoutView;
	
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
		notificationLayout=LayoutInflater.from(context);
		notificationLayoutView=notificationLayout.inflate(R.layout.notification_layout, null);
		ImageButton imageButton;
		
		new ManagerWifi(context);
		new ManagerMobileData(context);
		new ManagerBluetooth(context);
		new ManagerAudio(context);

		imageButton=(ImageButton)notificationLayoutView.findViewById(R.id.notification_internet);
		if(ManagerMobileData.getMobileDataState())
			imageButton.setImageResource(R.drawable.notification_mobile_data_on);
		else
			imageButton.setImageResource(R.drawable.notification_mobile_data_off);
		
		imageButton=(ImageButton)notificationLayoutView.findViewById(R.id.notification_sound);
		if(ManagerAudio.getSoundState())
			imageButton.setImageResource(R.drawable.notification_sound_on);
		else
			imageButton.setImageResource(R.drawable.notification_sound_off);
		
		imageButton=(ImageButton)notificationLayoutView.findViewById(R.id.notification_vibrate);
		if(ManagerAudio.getVibrationState())
			imageButton.setImageResource(R.drawable.notification_vibration_on);
		else
			imageButton.setImageResource(R.drawable.notification_vibration_off);
		
		//notificationView.removeAllViews(R.layout.notification_layout);
		//notificationView.addView(R.layout.notification_layout, notificationView);
		
	}
	
	/*private void setNotificationLayout()
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
	}*/
}
