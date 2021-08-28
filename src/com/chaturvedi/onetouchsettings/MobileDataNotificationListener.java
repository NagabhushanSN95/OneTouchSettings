package com.chaturvedi.onetouchsettings;

import android.app.Activity;
import android.os.Bundle;

public class MobileDataNotificationListener extends Activity
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ManagerMobileData.toggleMobileDataState();
		finish();
	}
}
