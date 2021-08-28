package com.chaturvedi.onetouchsettings;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

public class UserDataManager
{
	private String appFolderName;
	private String prefFileName;
	private File appFolder;
	private File prefFile;
	private BufferedReader prefReader;
	private BufferedWriter prefWriter;
	
	private Context context;
	
	public UserDataManager(Context cxt)
	{
		context=cxt;

		appFolderName="One Touch Settings/.temp";
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
		}
	}
	
	public void saveData(boolean tetheringState, int mobileDataSimNo)
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
	
	public boolean getTetheringState()
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
	}
}
