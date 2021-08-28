package com.chaturvedi.onetouchsettings;

import android.content.Context;
import android.content.SharedPreferences;

public class UserDataManager
{
	private static final String SHARED_PREFERENCES_STATES = "States";
	private static final String KEY_TETHERING_STATE = "state_tethering";
	private static final String KEY_MOBILE_DATA_SIM = "simNo_mobile_data";
	
	/*private String appFolderName;
	private String prefFileName;
	private File appFolder;
	private File prefFile;
	private BufferedReader prefReader;
	private BufferedWriter prefWriter;*/
	
	private Context context;
	
	public UserDataManager(Context cxt)
	{
		context=cxt;

		/*appFolderName="One Touch Settings/.temp";
		prefFileName="preferences.txt";
		
		try
		{
			appFolder=new File(Environment.getExternalStoragePublicDirectory("Chaturvedi"), appFolderName);
			if(!appFolder.exists())
				appFolder.mkdirs();
			
			prefFile=new File(appFolder, prefFileName);
			if(!prefFile.exists())
			{
				prefWriter=new BufferedWriter(new FileWriter(prefFile));
				prefWriter.write("false");
				prefWriter.write("1");
				prefWriter.close();
			}
		}
		catch(Exception e)
		{
        	Toast.makeText(context, "User Data Manager Initializing Failed\n", Toast.LENGTH_SHORT).show();
		}*/
	}
	
	/*public void saveData(boolean tetheringState, int mobileDataSimNo)
	{
		try
		{
			prefWriter=new BufferedWriter(new FileWriter(prefFile));
			prefWriter.write(tetheringState+"\n");
			prefWriter.write(mobileDataSimNo+"\n");
			prefWriter.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
        	Toast.makeText(context, "Failed To Save Data", Toast.LENGTH_SHORT).show();
		}
	}
	
	/*public boolean getTetheringState()
	{
		try
		{
			prefReader=new BufferedReader(new FileReader(prefFile));
			Boolean state=Boolean.parseBoolean(prefReader.readLine());
			prefReader.close();
			return state;
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	public int getMobileDataSimNo()
	{
		try
		{
			prefReader=new BufferedReader(new FileReader(prefFile));
			prefReader.readLine();
			int simNo=Integer.parseInt(prefReader.readLine());
			prefReader.close();
			return simNo; 
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		catch(NumberFormatException e)
		{
			e.printStackTrace();
		}
		return 1;
	}*/
	
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
