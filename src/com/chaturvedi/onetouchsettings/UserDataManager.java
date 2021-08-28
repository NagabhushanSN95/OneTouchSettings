package com.chaturvedi.onetouchsettings;

import android.content.Context;
import android.content.SharedPreferences;

public class UserDataManager
{
	private static final String SHARED_PREFERENCES_STATES = "States";
	private static final String KEY_TETHERING_STATE = "state_tethering";
	private static final String KEY_MOBILE_DATA_SIM = "simNo_mobile_data";
	
	private Context context;
	
	public UserDataManager(Context cxt)
	{
		context=cxt;
	}
	
	public void saveTetheringState(boolean state)
	{
		SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCES_STATES, 0);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putBoolean(KEY_TETHERING_STATE, state);
		editor.commit();
	}
	
	public void saveMobileDataSimNo(int simNo)
	{
		SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCES_STATES, 0);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putInt(KEY_MOBILE_DATA_SIM, simNo);
		editor.commit();
	}
	
	public void saveAllStates(boolean tetheringState, int mobileDataSimNo)
	{
		SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCES_STATES, 0);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putBoolean(KEY_TETHERING_STATE, tetheringState);
		editor.putInt(KEY_MOBILE_DATA_SIM, mobileDataSimNo);
		editor.commit();
	}
	
	public boolean getTetheringState()
	{
		SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCES_STATES, 0);
		if(preferences.contains(KEY_TETHERING_STATE))
		{
			return preferences.getBoolean(KEY_TETHERING_STATE, false);
		}
		else
		{
			return false;
		}
	}
	
	public int getMobileDataSimNo()
	{
		SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCES_STATES, 0);
		if(preferences.contains(KEY_MOBILE_DATA_SIM))
		{
			return preferences.getInt(KEY_MOBILE_DATA_SIM, 1);
		}
		else
		{
			return 1;
		}
	}
}
