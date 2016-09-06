package phlx.jikong;

import phlx.jikong.utils.lang.Language;

import android.content.Context;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.preference.EditTextPreference;

@SuppressWarnings("deprecation")
public class Prefs extends PreferenceActivity
{
	public static final String CONFIG_FILENAME  = "config";
	public static final String IP_ADDR          = "IpAddr";
	public static final String IP_PORT          = "IpPort";
	public static final String UI_FILE_TITLE    = "UiFileTitle";
	public static final String UI_FILE_VERSION  = "UiFileVersion";
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);		
		
		this.getPreferenceManager().setSharedPreferencesName(CONFIG_FILENAME);
		this.getPreferenceManager().setSharedPreferencesMode(Context.MODE_PRIVATE);
		PreferenceScreen prefScreen = this.getPreferenceManager().createPreferenceScreen(this);
		
		EditTextPreference editPref = new EditTextPreference(this);
		editPref.setKey(IP_ADDR);
		editPref.setSummary("集控主机的IP地址");
		editPref.setTitle(Language.Get(Language.LID_STRING_1));
		editPref.setOrder(0);
		prefScreen.addPreference(editPref);
		
		editPref = new EditTextPreference(this);
		editPref.setKey(IP_PORT);
		editPref.setSummary("集控主机的IP服务端口");
		editPref.setTitle(Language.Get(Language.LID_STRING_2));
		editPref.setOrder(1);
		prefScreen.addPreference(editPref);
		
		editPref = new EditTextPreference(this);
		editPref.setKey(UI_FILE_TITLE);
		editPref.setSummary("当前已经下载的界面");
		editPref.setTitle(Language.Get(Language.LID_STRING_5));
		editPref.setOrder(2);
		prefScreen.addPreference(editPref);
		
		this.setPreferenceScreen(prefScreen);
	}
	
	/* (non-Javadoc)
	 * @see android.preference.PreferenceActivity#onPreferenceTreeClick(android.preference.PreferenceScreen, android.preference.Preference)
	 */
	@Override
	public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
		// TODO Auto-generated method stub
		return super.onPreferenceTreeClick(preferenceScreen, preference);
	}
	
	public String GetIpAddress()
	{
		return getSharedPreferences(CONFIG_FILENAME, MODE_PRIVATE).getString(IP_ADDR, "192.168.1.100");
	}
	public int GetIpPort()
	{
		return Integer.parseInt(getSharedPreferences(CONFIG_FILENAME, MODE_PRIVATE).getString(IP_PORT, "9000"));
	}

}
