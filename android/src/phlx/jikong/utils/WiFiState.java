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
	            System.out.println("��ǰ�ź� "+strength);
	            //wifiStateImage.setImageLevel(strength);
	        }
	        else if(intent.getAction().equals(WifiManager.NETWORK_STATE_CHANGED_ACTION))
	        {
	            NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
	            NetworkInfo.State netState = info.getState();
	            
	            switch(netState)
	            {
	            case CONNECTING:
	            	PostMessage(MsgIdUi.MSG_WIFI_STATUS, 0, 0, "������������ ...");
	            	break;
	            case CONNECTED:
	            	PostMessage(MsgIdUi.MSG_WIFI_STATUS, 1, 0, "����������");
	            	break;
	            case SUSPENDED:
	            	PostMessage(MsgIdUi.MSG_WIFI_STATUS, 0, 0, "������ͣ");
	            	break;
	            case DISCONNECTING:
	            	PostMessage(MsgIdUi.MSG_WIFI_STATUS, 0, 0, "���ڹر����� ...");
	            	break;
	            case DISCONNECTED:
	            	PostMessage(MsgIdUi.MSG_WIFI_STATUS, 0, 0, "�����ѹر�");
	            	break;
	            case UNKNOWN:
	            	PostMessage(MsgIdUi.MSG_WIFI_STATUS, 0, 0, "δ֪����״̬");
	            	break;
	            }
	        }
	        else if(intent.getAction().equals(WifiManager.WIFI_STATE_CHANGED_ACTION))
	        {
	        	System.out.println("WIFI״̬�ı�");
	        	
	            //WIFI����
	            int wifistate = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, WifiManager.WIFI_STATE_DISABLED);
	            switch(wifistate)
	            {
	            case WifiManager.WIFI_STATE_DISABLING:
	            	PostMessage(MsgIdUi.MSG_WIFI_STATUS, 0, 0, "WIFI���ڹر� ...");
	            	break;
	            case WifiManager.WIFI_STATE_DISABLED:
	            	PostMessage(MsgIdUi.MSG_WIFI_STATUS, 0, 0, "WIFI�ѹر�");
	            	break;
	            case WifiManager.WIFI_STATE_ENABLING:
	            	PostMessage(MsgIdUi.MSG_WIFI_STATUS, 0, 0, "WIFI���ڴ� ...");
	            	break;
	            case WifiManager.WIFI_STATE_ENABLED:
	            	PostMessage(MsgIdUi.MSG_WIFI_STATUS, 0, 0, "WIFI�Ѵ򿪣��ȴ��������� ...");
	            	break;
	            case WifiManager.WIFI_STATE_UNKNOWN:
	            	PostMessage(MsgIdUi.MSG_WIFI_STATUS, 0, 0, "WIFIδ֪״̬");
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
	            // �����ٶ�
	//         int speed = info.getLinkSpeed();
	//         // �����ٶȵ�λ
	//         String units = WifiInfo.LINK_SPEED_UNITS;
	//         // WifiԴ����
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
