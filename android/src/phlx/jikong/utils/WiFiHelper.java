package phlx.jikong.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiManager.WifiLock;
import android.os.PowerManager.WakeLock;

public class WiFiHelper
{
	private WifiManager wifiManager;
	//private WifiInfo  wifiInfo;
	private WifiLock wifiLock;
	private WakeLock wakeLock;
	//private String mLockName;
	private Context mContext;
	//private PowerManager powermanager;
	public WiFiHelper(Context context)
	{
		if(context!=null)
		{
			wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
			//powermanager= (PowerManager)context.getSystemService(Context.POWER_SERVICE);
			mContext=context;
		}
	}
	
	/*
	 * 打开WiFi
	 * @return
	 */
	public boolean OpenWifi()
	{
		if(wifiManager!=null)
		{
			if(wifiManager.isWifiEnabled())
			{
				return true;
			}
			return wifiManager.setWifiEnabled(true);
		}
		return false;
	}
	
	/**
	 * 关闭WiFi
	 * @return
	 */
	public boolean CloseWifi()
	{
		if(wifiManager!=null)
		{
			if(!wifiManager.isWifiEnabled())
			{
				return true;
			}
			return wifiManager.setWifiEnabled(false);
		}
		return false;
	}
	/**
	 * 锁定Wifi
	 * @param lockName
	 */
	public boolean LockWifi(String lockName)
	{
		if(wifiLock==null)
		{
			/*
			mLockName=lockName;
			wifiLock=wifiManager.createWifiLock("WIFI"+mLockName);
			wakeLock=powermanager.newWakeLock(PowerManager.FULL_WAKE_LOCK,"POWER"+mLockName);
			wifiLock.acquire();
			wakeLock.acquire();
			*/
			return true;
		}
		return false;
	}
	/**
	 * 解除Wifi锁定
	 */
	public void UnlockWifi()
	{		
       if(wifiLock!=null&&wifiLock.isHeld())
		{
    	   wifiLock.release();
	    }
		if(wakeLock!=null&&wakeLock.isHeld())
		{
			wakeLock.release();
		}
	}
	
   public boolean IsConnected()    
   {    
	   if(mContext!=null)
	   {
	       ConnectivityManager connMgr = (ConnectivityManager)mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
	       NetworkInfo wifi =connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI); 
	   
	       if(wifi!=null&&wifi.getState()==State.CONNECTED)    
	           return true; 
	       else   
	           return false;  
	   }
	   else
	   {
		   return false;
	   }
   }  
}


/*
 private void OpenWifi()
	{
		if(wifiHelper.OpenWifi())
		{
			int times=0;
			do{
				try 
				{
					m_ifDevice.WifiStatus(IDevice.WIFI_OPEN_CONNECTING);
					if(times > 2 )//20)
					{
						break;
					}
					times++;
					Thread.sleep(500);
				} 
				catch (InterruptedException e) 
				{
				}
			}
			while(!wifiHelper.IsConnected());//判断Wifi是否开启成功，并可以正常使用
		}
	
		m_ifDevice.WifiStatus(wifiHelper.IsConnected() ? IDevice.WIFI_OPEN_SUCCESS:IDevice.WIFI_OPEN_FAILED);
			//wifiHelper.LockWifi("FLXCCSC");
	} 
 */