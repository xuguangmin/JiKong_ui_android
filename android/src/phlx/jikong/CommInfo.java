package phlx.jikong;


import phlx.jikong.device.net.ITcpClient;
import phlx.jikong.utils.Utils;
import phlx.jikong.utils.lang.Language;

public class CommInfo 
{
	//private boolean m_wifiJitter = false;
	private boolean b_connJitter = false;
	private String  m_ipAddr     = "";
	private int     m_connStatus = ITcpClient.CONN_STATUS_CLOSE;

	public String GetConnNotify()
	{
		if(ITcpClient.CONN_STATUS_OPEN == m_connStatus)
		{
			return "连接成功";
		}
		else
		{
			return String.format("%s %s %s", Language.Get(Language.LID_STRING_3), m_ipAddr, Utils.GetJitter(b_connJitter));
		}
	}
	/*	
	public String GetWifiNotify(int wifiStatus)
	{
		if(IDevice.WIFI_OPEN_CONNECTING == wifiStatus)
		{
			m_wifiJitter = !m_wifiJitter;
			return String.format("%s %s", Language.Get(Language.LID_STRING_15), Utils.GetJitter(m_wifiJitter));	
		}
		else{
			return Language.Get((IDevice.WIFI_OPEN_SUCCESS == wifiStatus) ? Language.LID_STRING_13:Language.LID_STRING_14);
		}
	}
	*/
	public boolean IsConnected()   
	{
		return (ITcpClient.CONN_STATUS_OPEN == m_connStatus) ? true:false;
	}
	public void SetConnectStatus(int connStatus)
	{
		m_connStatus = connStatus;
		if(ITcpClient.CONN_STATUS_CONNECTING == m_connStatus)
			b_connJitter = !b_connJitter;
	}
	public void SetConnInfo(String ipAddr, int ipPort)
	{
		m_ipAddr = String.format("%s:%d", ipAddr, ipPort);
	}
}
