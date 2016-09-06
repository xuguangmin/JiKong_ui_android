package phlx.jikong.utils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import org.apache.http.conn.util.InetAddressUtils;


public final class HostInfo {
	/*
	private static String getIp() 
	{
		WifiManager wm = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		// ���Wifi״̬
		if (!wm.isWifiEnabled())
			wm.setWifiEnabled(true);
		WifiInfo wi = wm.getConnectionInfo();
		// ��ȡ32λ����IP��ַ
		int ipAdd = wi.getIpAddress();
		// �����͵�ַת���ɡ�*.*.*.*����ַ
		String ip = intToIp(ipAdd);
		return ip;
	}

	// �õ�����Mac��ַ
	public String getLocalMac() 
	{
		String mac = "";
		// ��ȡwifi������
		WifiManager wifiMng = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		WifiInfo wifiInfor = wifiMng.getConnectionInfo();
		mac = "������mac��ַ�ǣ�" + wifiInfor.getMacAddress();
		return mac;
	}
	private static String intToIp(int i) 
	{
		return (i & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + ((i >> 16) & 0xFF)
				+ "." + (i >> 24 & 0xFF);
	}
	*/

	public static String getLocalHostIp() 
	{
		String ipaddress = "";
		try 
		{
			Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
			
			// �������õ�����ӿ�
			while (en.hasMoreElements()) 
			{
				NetworkInterface nif = en.nextElement();   // �õ�ÿһ������ӿڰ󶨵�����ip
				Enumeration<InetAddress> inet = nif.getInetAddresses();
				
				// ����ÿһ���ӿڰ󶨵�����ip
				while (inet.hasMoreElements()) 
				{
					InetAddress ip = inet.nextElement();
					if (!ip.isLoopbackAddress() && 
						InetAddressUtils.isIPv4Address(ip.getHostAddress())) 
					{
						return ipaddress = ip.getHostAddress();
					}
					//ipaddress = "������ip��" + "��" + ip.getHostAddress();
					//System.out.printf("------ %s\n", ipaddress);
				}

			}
		} 
		catch (SocketException e) 
		{
			// Log.e("feige", "��ȡ����ip��ַʧ��");
			e.printStackTrace();
		}
		return ipaddress;
	}
}
