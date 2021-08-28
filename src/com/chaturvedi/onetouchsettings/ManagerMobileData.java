package com.chaturvedi.onetouchsettings;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class ManagerMobileData
{
	private static boolean isTetherable=false;
	private static boolean tetheringState;
	private static boolean mobileDataState;
	private static int mobileDataSim;
	
	//private static Activity context;
	private static Context context;
	
	private static ConnectivityManager tetheringManager;
	private static Method tetheringMethod;
	private static UserDataManager dataManager;
	private static ConnectivityManager mobileDataManager;
	@SuppressWarnings("rawtypes")
	private static Class mobileDataClass;
	private static Method mobileDataMethod;
	private static Method mobileDataSimMethod;
	
	public ManagerMobileData(Activity activity)
	{
		context=activity;
	}
	
	public ManagerMobileData(Context cxt)
	{
		context=cxt;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void readMobileDataState()
	{
		// Check Tethering State*/
		try
		{
			tetheringManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
			tetheringMethod=ConnectivityManager.class.getDeclaredMethod("tether");
			tetheringMethod.setAccessible(true);
			
			Class tetheringClass=Class.forName(mobileDataManager.getClass().getName());
			tetheringMethod=tetheringClass.getDeclaredMethod("isTetheringSupported");
			tetheringMethod.setAccessible(true);
			isTetherable=(Boolean)tetheringMethod.invoke(tetheringManager);
			
		}
		catch(Exception e)
		{
			
		}
		
		// Read Tethering State And Data Sim
		dataManager=new UserDataManager(context);
		tetheringState=dataManager.getTetheringState();
		mobileDataSim=dataManager.getMobileDataSimNo();
		
		// Check Mobile-Data State
		try
		{
			mobileDataManager=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
			
			// Set method to check the mobile data state
			mobileDataClass=Class.forName(mobileDataManager.getClass().getName());
			mobileDataMethod=mobileDataClass.getDeclaredMethod("getMobileDataEnabled");
			mobileDataMethod.setAccessible(true);
			mobileDataState=(Boolean)mobileDataMethod.invoke(mobileDataManager);
			
			// Change method to set the mobile data on or off
			mobileDataMethod=mobileDataClass.getDeclaredMethod("setMobileDataEnabled", boolean.class);
			mobileDataMethod.setAccessible(true);
		}
		catch(Exception e)
		{
			
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static boolean isTetherable()
	{
		isTetherable=false;
		try
		{
			Class tetheringClass=Class.forName(mobileDataManager.getClass().getName());
			tetheringMethod=tetheringClass.getDeclaredMethod("isTetheringSupported");
			tetheringMethod.setAccessible(true);
			isTetherable=(Boolean)tetheringMethod.invoke(tetheringManager);
			//Toast.makeText(context, ""+isTetherable, Toast.LENGTH_LONG).show();
		}
		catch(IllegalAccessException e)
		{
			Toast.makeText(context, e.getStackTrace()+"\n"+e.getMessage(), Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
		catch(IllegalArgumentException e) 
		{
			Toast.makeText(context, e.getStackTrace()+"\n"+e.getMessage(), Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
		catch(InvocationTargetException e)
		{
			Toast.makeText(context, e.getStackTrace()+"\n"+e.getMessage(), Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
		catch(ClassNotFoundException e)
		{
			Toast.makeText(context, e.getStackTrace()+"\n"+e.getMessage(), Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
		catch(NoSuchMethodException e)
		{
			e.printStackTrace();
		}
		return isTetherable;
	}
	
	public static void setTetheringState(boolean state)
	{
		tetheringState=state;
		isTetherable();
		if(tetheringState && !isTetherable)
		{
			AlertDialog.Builder notTetherable=new AlertDialog.Builder(context);
			notTetherable.setTitle("Unable To Tether");
			notTetherable.setMessage("Connect USB First And Then Try Again");
			notTetherable.setPositiveButton("OK", null);
			notTetherable.show();
			tetheringState=false;
		}
		else
		{
			try
			{
				tetheringManager=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
	            Method[] tetheringMethods = tetheringManager.getClass().getDeclaredMethods();
	            String str = "";
	            if (tetheringState)
	                str = "tether";
	            else
	                str = "untether";
	            for (Method method : tetheringMethods)
	            {
	            	if (method.getName().equals(str))
	                {
	                    try
	                    {
	                    	method.invoke(tetheringManager, "usb0");
	                        //Integer code = (Integer) method.invoke(tetheringManager, "usb0");
	                    	//code = (Integer) method.invoke(tetheringManager, "setting TH");
	                    	
	            			dataManager.saveAllStates(tetheringState, mobileDataSim);
	                    }
	                    catch (IllegalArgumentException e)
	                    {
	                        e.printStackTrace();
	                    }
	                    catch (IllegalAccessException e)
	                    {
	                        e.printStackTrace();
	                    }
	                    catch (InvocationTargetException e)
	                    {
	                        e.printStackTrace();
	                    }
	                }
	            }
	        }
			catch(Exception e)
			{
	            
	        }
		}
    }
	
	public static void toggleTetheringState()
	{
		tetheringState=!tetheringState;
		setTetheringState(tetheringState);
	}
	
	public static boolean getTetheringState()
	{
		return tetheringState;
	}
	
	public static void setMobileDataState(boolean state)
	{
		mobileDataState=state;
		try
		{
			int androidVersionNo = android.os.Build.VERSION.SDK_INT;
			if(androidVersionNo < Build.VERSION_CODES.GINGERBREAD)
			{
				Method dataConnMethod;
				Class telephonyManagerClass;
				Object ITelephonyStub;
				Class ITelephonyClass;
				
				TelephonyManager telephonyManager = 
						(TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
				
				/**
				 * To check if data is on or not
				 * /
				if(telephonyManager.getDataState()==TelephonyManager.DATA_CONNECTED)
				{
					mobileDataState = true;
				}
				else
				{
					mobileDataState = false;
				}*/
				
				telephonyManagerClass = Class.forName(telephonyManager.getClass().getName());
				Method getITelephonyMethod = telephonyManagerClass.getDeclaredMethod("getITelephony");
				getITelephonyMethod.setAccessible(true);
				ITelephonyStub = getITelephonyMethod.invoke(telephonyManager);
				ITelephonyClass = Class.forName(ITelephonyStub.getClass().getName());
				
				if(mobileDataState)
				{
					dataConnMethod = ITelephonyClass.getDeclaredMethod("disableDataConnectivity");
				}
				else
				{
					dataConnMethod = ITelephonyClass.getDeclaredMethod("enableDataConnectivity");
				}
				dataConnMethod.setAccessible(true);
				dataConnMethod.invoke(ITelephonyStub);
			}
			else
			{
				mobileDataMethod.invoke(mobileDataManager, mobileDataState);
			}
		}
		catch(Exception e)
		{
			
		}
	}
	
	public static void toggleMobileDataState()
	{
		mobileDataState=!mobileDataState;
		setMobileDataState(mobileDataState);
	}
	
	public static boolean getMobileDataState()
	{
		return mobileDataState;
	}
	
	@SuppressWarnings("unchecked")
	public static void setMobileDataSim(int sim)
	{
		mobileDataSim=sim;
		// Set Method to change the Mobile Data Sim
		try
		{
			switch(mobileDataSim)
			{
				case 1:
					mobileDataSimMethod=mobileDataClass.getDeclaredMethod("switchToSim1DataNetwork");
					mobileDataSimMethod.setAccessible(true);
					mobileDataSimMethod.invoke(mobileDataManager);
					
					mobileDataSimMethod=mobileDataClass.getDeclaredMethod("onSwitchToSim1DataNetworkCallback");
					Toast.makeText(context, mobileDataSimMethod.getParameterTypes().getClass().toString(), Toast.LENGTH_LONG).show();
					mobileDataSimMethod.setAccessible(true);
					mobileDataSimMethod.invoke(mobileDataManager);
					Toast.makeText(context, mobileDataSimMethod.getParameterTypes().getClass().toString(), Toast.LENGTH_LONG).show();
					break;
					
				case 2:
					mobileDataSimMethod=mobileDataClass.getDeclaredMethod("switchToSim2DataNetwork");
					mobileDataSimMethod.setAccessible(true);
					mobileDataSimMethod.invoke(mobileDataManager);
					break;
					
				default:
					mobileDataSimMethod=mobileDataClass.getDeclaredMethod("switchToSim1DataNetwork");
					mobileDataSimMethod.setAccessible(true);
					mobileDataSimMethod.invoke(mobileDataManager);
					Toast.makeText(context, "Data Network "+"Changed To Default Sim 1", Toast.LENGTH_SHORT).show();
					break;
			}
			dataManager.saveAllStates (tetheringState, mobileDataSim);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	public static void toggleMobileDataSim()
	{
		if(mobileDataSim==1)
			mobileDataSim=2;
		else
			mobileDataSim=1;
		setMobileDataSim(mobileDataSim);
	}
	
	public static int getMobileDataSim()
	{
		return mobileDataSim;
	}
	
	public static int getMobileDataIcon()
	{
		if(mobileDataState)
			return R.drawable.mobile_data_on;
		else
			return R.drawable.mobile_data_off;
	}
	
	public static int getMobileDataSimIcon()
	{
		if(mobileDataSim==1)
			return R.drawable.data_sim1;
		else
			return R.drawable.data_sim2;
	}
	
	public static int getTetheringIcon()
	{
		if(tetheringState)
			return R.drawable.usb_tethering_on;
		else
			return R.drawable.usb_tethering_off;
	}
	
	public static int getMobileDataNotificationIcon()
	{
		if(mobileDataState)
			return R.drawable.notification_mobile_data_on;
		else
			return R.drawable.notification_mobile_data_off;
	}
}
