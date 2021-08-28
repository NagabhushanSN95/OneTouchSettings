package com.chaturvedi.onetouchsettings;

import android.app.Activity;
import android.os.Bundle;

public class NotificationListenerBluetoothVisibility extends Activity
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		new ManagerBluetooth(this);
		ManagerBluetooth.readBluetoothState();
		ManagerBluetooth.toggleVisibility();
		finish();
	}
}
