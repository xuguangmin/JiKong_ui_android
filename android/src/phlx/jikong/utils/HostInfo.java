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
		// 检查Wifi状态
		if (!wm.isWifiEnabled())
			wm.setWifiEnabled(true);
		WifiInfo wi = wm.getConnectionInfo();
		// 获取32位整型IP地址
		int ipAdd = wi.getIpAddress();
		// 把整型地址转换成“*.*.*.*”地址
		String ip = intToIp(ipAdd);
		return ip;
	}

	// 得到本机Mac地址
	public String getLocalMac() 
	{
		String mac = "";
		// 获取wifi管理器
		WifiManager wifiMng = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		WifiInfo wifiInfor = wifiMng.getConnectionInfo();
		mac = "本机的mac地址是：" + wifiInfor.getMacAddress();
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
			
			// 遍历所用的网络接口
			while (en.hasMoreElements()) 
			{
				NetworkInterface nif = en.nextElement();   // 得到每一个网络接口绑定的所有ip
				Enumeration<InetAddress> inet = nif.getInetAddresses();
				
				// 遍历每一个接口绑定的所有ip
				while (inet.hasMoreElements()) 
				{
					InetAddress ip = inet.nextElement();
					if (!ip.isLoopbackAddress() && 
						InetAddressUtils.isIPv4Address(ip.getHostAddress())) 
					{
						return ipaddress = ip.getHostAddress();
					}
					//ipaddress = "本机的ip是" + "：" + ip.getHostAddress();
					//System.out.printf("------ %s\n", ipaddress);
				}

			}
		} 
		catch (SocketException e) 
		{
			// Log.e("feige", "获取本地ip地址失败");
			e.printStackTrace();
		}
		return ipaddress;
	}
}
