package phlx.jikong;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;

public class Configure 
{
	private static final String CONFIG_FILENAME  = "config";
	private static final String IP_ADDR          = "IpAddr";
	private static final String IP_PORT          = "IpPort";
	private static final String UI_FILE_TITLE    = "UiFileTitle";
	private static final String UI_FILE_VERSION  = "UiFileVersion";
	
	private SharedPreferences sharePrefs;
	public Configure(ContextWrapper contextWrapper)
	{
		sharePrefs = contextWrapper.getSharedPreferences(CONFIG_FILENAME, Context.MODE_PRIVATE);
	}
	
	public String GetIpAddr()    {return sharePrefs.getString(IP_ADDR, "192.168.1.100");}
	public String GetIpPortStr() {return sharePrefs.getString(IP_PORT, "9000");}
	public int GetIpPort() 
	{
		int result;
		try
		{
			String strText = sharePrefs.getString(IP_PORT, "9000");
			result = Integer.parseInt(strText);
		}
		catch(NumberFormatException e)
		{
			e.printStackTrace();
			result = 9000;	
		}
		return result;
	}

	public void SaveIpAddr(String ipAddr, String ipPort)
	{
		SharedPreferences.Editor editor = sharePrefs.edit();
		editor.putString(IP_ADDR, ipAddr);
		editor.putString(IP_PORT, ipPort);
		if(editor.commit())
		{
			System.out.println("sharePrefs.edit().commit() success");
		}
		else
		{
			System.out.println("sharePrefs.edit().commit() failed");
		}
	}
	
	public String GetFileVersion(){return sharePrefs.getString(UI_FILE_VERSION, "");}
	public String GetFileTitle()  {return sharePrefs.getString(UI_FILE_TITLE, "");}
	public void SaveFileInfo(String fileTitle, String fileVersion)
	{
		SharedPreferences.Editor editor = sharePrefs.edit();
		editor.putString(UI_FILE_TITLE,   fileTitle);
		editor.putString(UI_FILE_VERSION, fileVersion);
		if(editor.commit())
		{
			System.out.println("sharePrefs.edit().commit() success");
		}
		else
		{
			System.out.println("sharePrefs.edit().commit() failed");
		}
	}
}
