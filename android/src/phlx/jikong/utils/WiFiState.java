package phlx.jikong.utils;

import phlx.jikong.utils.msg.MsgIdUi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Message;

public class WiFiState
{
	public class WiFiStateReceiver extends BroadcastReceiver
	{
		@Override
		public void onReceive(Context context, Intent intent) 
		{			
			System.out.println(intent.getAction());
	        if(intent.getAction().equals(WifiManager.RSSI_CHANGED_ACTION))
	        {
	            int strength=getStrength(context);
	            System.out.println("当前信号 "+strength);
	            //wifiStateImage.setImageLevel(strength);
	        }
	        else if(intent.getAction().equals(WifiManager.NETWORK_STATE_CHANGED_ACTION))
	        {
	            NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
	            NetworkInfo.State netState = info.getState();
	            
	            switch(netState)
	            {
	            case CONNECTING:
	            	PostMessage(MsgIdUi.MSG_WIFI_STATUS, 0, 0, "网络正在连接 ...");
	            	break;
	            case CONNECTED:
	            	PostMessage(MsgIdUi.MSG_WIFI_STATUS, 1, 0, "网络已连接");
	            	break;
	            case SUSPENDED:
	            	PostMessage(MsgIdUi.MSG_WIFI_STATUS, 0, 0, "网络暂停");
	            	break;
	            case DISCONNECTING:
	            	PostMessage(MsgIdUi.MSG_WIFI_STATUS, 0, 0, "正在关闭网络 ...");
	            	break;
	            case DISCONNECTED:
	            	PostMessage(MsgIdUi.MSG_WIFI_STATUS, 0, 0, "网络已关闭");
	            	break;
	            case UNKNOWN:
	            	PostMessage(MsgIdUi.MSG_WIFI_STATUS, 0, 0, "未知网络状态");
	            	break;
	            }
	        }
	        else if(intent.getAction().equals(WifiManager.WIFI_STATE_CHANGED_ACTION))
	        {
	        	System.out.println("WIFI状态改变");
	        	
	            //WIFI开关
	            int wifistate = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, WifiManager.WIFI_STATE_DISABLED);
	            switch(wifistate)
	            {
	            case WifiManager.WIFI_STATE_DISABLING:
	            	PostMessage(MsgIdUi.MSG_WIFI_STATUS, 0, 0, "WIFI正在关闭 ...");
	            	break;
	            case WifiManager.WIFI_STATE_DISABLED:
	            	PostMessage(MsgIdUi.MSG_WIFI_STATUS, 0, 0, "WIFI已关闭");
	            	break;
	            case WifiManager.WIFI_STATE_ENABLING:
	            	PostMessage(MsgIdUi.MSG_WIFI_STATUS, 0, 0, "WIFI正在打开 ...");
	            	break;
	            case WifiManager.WIFI_STATE_ENABLED:
	            	PostMessage(MsgIdUi.MSG_WIFI_STATUS, 0, 0, "WIFI已打开，等待网络连接 ...");
	            	break;
	            case WifiManager.WIFI_STATE_UNKNOWN:
	            	PostMessage(MsgIdUi.MSG_WIFI_STATUS, 0, 0, "WIFI未知状态");
	            	break;
	            }
	        }
	        
	        // PostMessage(MsgIdUi.MSG_WIFI_STATUS, 0, 0, intent.getAction());
			
		}
		
		
		public int getStrength(Context context)
	    {
	        WifiManager wifiManager = (WifiManager) context
	                .getSystemService(Context.WIFI_SERVICE);
	        
	        WifiInfo info = wifiManager.getConnectionInfo();
	        if (info.getBSSID() != null) {
	            int strength = WifiManager.calculateSignalLevel(info.getRssi(), 5);
	            // 链接速度
	//         int speed = info.getLinkSpeed();
	//         // 链接速度单位
	//         String units = WifiInfo.LINK_SPEED_UNITS;
	//         // Wifi源名称
	//         String ssid = info.getSSID();
	            return strength;
	 
	        }
	        return 0;
	    }
	}
	
	public static final int WIFI_OPEN_FAILED      = 0;
	public static final int WIFI_OPEN_SUCCESS     = 1;
	public static final int WIFI_OPEN_CONNECTING  = 2;
	
	private Handler m_handler = null;
	private Context m_context = null;
	private WiFiStateReceiver wifiReceiver = null;
	private void PostMessage(int msgId, int arg1, int arg2, Object obj)
	{
		if(null == m_handler)
			return;
		
		Message msg = Message.obtain();
		msg.what = msgId;
		msg.arg1 = arg1;
		msg.arg2 = arg2;
		msg.obj  = obj;
		m_handler.sendMessage(msg);	
	}
	
	public void Release()
	{
		m_context.unregisterReceiver(wifiReceiver);
	}
	public boolean Initial(Context context, Handler handler)
	{
		m_handler = handler;
		m_context = context;
		
		wifiReceiver = new WiFiStateReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.RSSI_CHANGED_ACTION);
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        context.registerReceiver(wifiReceiver, filter); 
		return true;
	}
}
