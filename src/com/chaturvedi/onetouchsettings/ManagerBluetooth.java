package com.chaturvedi.onetouchsettings;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;

public class ManagerBluetooth
{
	private static boolean bluetoothState;
	private static boolean visibilityState;
	
	//private static Activity context;
	private static Context context;
	private static BluetoothAdapter bluetoothManager;
	
	public ManagerBluetooth(Activity activity)
	{
		context=activity;
	}
	
	public ManagerBluetooth(Context cxt)
	{
		context=cxt;
	}
	
	public static void readBluetoothState()
	{
		bluetoothManager=BluetoothAdapter.getDefaultAdapter();
		if(bluetoothManager.getState()==BluetoothAdapter.STATE_ON)
			bluetoothState=true;
		else
			bluetoothState=false;
		
		getVisibilityState();
	}
	
	public static void setBluetoothState(boolean state)
	{
		bluetoothState=state;
		if(bluetoothState)
			bluetoothManager.enable();
		else
			bluetoothManager.disable();
	}
	
	public static void toggleBluetoothState()
	{
		bluetoothState=!bluetoothState;
		setBluetoothState(bluetoothState);
	}
	
	public static boolean getBluetoothState()
	{
		return bluetoothState;
	}
	
	public static void setVisibility(boolean state)
	{
		int numSeconds;
		visibilityState=state;
		if(visibilityState)
		{
			numSeconds=300;
		}
		else
		{
			numSeconds=1;
		}
		Intent visibilityIntent=new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
		visibilityIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, numSeconds);
		context.startActivity(visibilityIntent);
	}
	
	public static void toggleVisibility()
	{
		visibilityState=!visibilityState;
		setVisibility(visibilityState);
	}
	
	public static boolean getVisibilityState()
	{
		if(bluetoothManager.getScanMode()==BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE)
			visibilityState=true;
		else
			visibilityState=false;
		return visibilityState;
	}
	
	public static int getBluetoothIcon()
	{
		if(bluetoothState)
			return R.drawable.bluetooth_on;
		else
			return R.drawable.bluetooth_off;
	}
	
	public static int getVisibilityIcon()
	{
		if(visibilityState)
			return R.drawable.visibility_on;
		else
			return R.drawable.visibility_off;
	}
}
