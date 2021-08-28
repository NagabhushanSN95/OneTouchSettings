package com.chaturvedi.onetouchsettings;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class NotificationReceiver extends Activity
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		onNewIntent(getIntent());
		finish();
	}
	
	@Override
	public void onNewIntent(Intent intent)
	{
		Bundle extras=intent.getExtras();
		if(extras!=null)
		{
			Toast.makeText(getApplicationContext(), extras.getString("Message"), Toast.LENGTH_SHORT).show();
		}
	}
}
