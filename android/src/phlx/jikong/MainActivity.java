package phlx.jikong;

/* **************************************************************************

			版权所有 (C), 2001-2020, 北京飞利信科技股份有限公司

*****************************************************************************
	文件名称 : MainActivity.java
	作者           : 贾延刚
	生成日期 : 
	
	版本           : 1.0
	功能描述 : 
	
	修改历史 :
	说明           ：
	                        程序如果在模拟器上运行，将会有如下一些不正常现象：
	            1)、搜索集控主机的功能不可用，因为模拟器没的IP地址和集控主机不在一个网段
	            2)、程序在启动时，不会自动连接集控设备，因为模拟器没有WIFI，在
	                                          程序执行了打开WIFI的操作后，不会收到网络的状态广播

******************************************************************************/

import java.lang.ref.WeakReference;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;
import phlx.jikong.ccc_ui.CCC_UI;
import phlx.jikong.ccc_ui.ICCC_UI;
import phlx.jikong.device.Device;
import phlx.jikong.utils.FileUtil;
import phlx.jikong.utils.ImageHelper;
import phlx.jikong.utils.InvokeApp;
import phlx.jikong.utils.MessageBox;
import phlx.jikong.utils.WiFiHelper;
import phlx.jikong.utils.WiFiState;
import phlx.jikong.utils.lang.Language;
import phlx.jikong.utils.msg.MsgIdUi;
import phlx.jikong.utils.msg.ParaDiscoverDev;

public class MainActivity extends Activity 
                          implements ICCC_UI
{
	public static class MyHandler extends Handler
    {
    	WeakReference<Activity> m_actRef = null;
    	MyHandler(Activity activty)
    	{
    		m_actRef = new WeakReference<Activity>(activty);
    	}
    	@Override
    	public void handleMessage(Message msg) 
    	{
    		if(m_actRef != null)
    		{
    			((MainActivity)m_actRef.get()).ProcessMsgQueue(msg);	
    		}
    	}
    }
	
	// 接收设置对话框的广播
	public class LoginSettingReceiver extends BroadcastReceiver
	{
		@Override
		public void onReceive(Context context, Intent intent) 
		{
	        if(intent.getAction().equals(ActivityLogin.PHLX_LOGIN_RESULT_ACTION))
	        {
	        	int request_id = intent.getIntExtra(ActivityLogin.LOGIN_REQUEST, 0);
	        	switch(request_id)
	        	{
	        	case ActivityLogin.REQUEST_ID_SEARCH:
	        		m_device.SearchJikong(7000);;
	        		break;
	        	case ActivityLogin.REQUEST_ID_OK:
	        		OnUiLoginLoginInfo(intent.getStringExtra(ActivityLogin.KEY_EDIT_IP_ADDR), intent.getStringExtra(ActivityLogin.KEY_EDIT_IP_PORT));
	        		ShowDefaultUI();
					RebuildConnect();
					break;
	        	case ActivityLogin.REQUEST_ID_CANCEL:
	        		ShowDefaultUI();
					RebuildConnect();
					break;
	        	}
	        }
		}
	}
	
	private void UpdateLoginSetting(String ipAddr)
	{
		Intent i = new Intent();
		i.setAction(ActivityLogin.PHLX_LOGIN_SET_VALUE_ACTION);
		i.putExtra(ActivityLogin.KEY_EDIT_IP_ADDR,  ipAddr);
		i.putExtra(ActivityLogin.KEY_EDIT_IP_PORT,  "9000");
		this.sendBroadcast(i);
	}
		
	public void OnUiLoginLoginInfo(String ipAddr, String ipPort) 
	{
		System.out.printf("------ new value %s %s\n", ipAddr, ipPort);
		if(!CheckIpAddr(ipAddr, ipPort))
		{
			MessageBox.Show(this, String.format("%s %s, %s", ipAddr, ipPort, "无效的IP地址或者端口"), "提示");
			return;
		}
		
		m_configure.SaveIpAddr(ipAddr, ipPort);
		m_commInfo.SetConnInfo(ipAddr, Integer.parseInt(ipPort));
		m_device.SetComm(ipAddr, Integer.parseInt(ipPort));	
	}
	
	
	static final int MENU_ITEM_ID_SETTING = 1;
	static final int MENU_ITEM_ID_RESTORE = 2;
	static final int MENU_ITEM_ID_LOAD    = 3;
	static final int MENU_ITEM_ID_ABOUT   = 4;
	static final int MENU_ITEM_ID_EXIT    = 5;
	
	static final int UI_STATUS_BLANK      = -1;
	static final int UI_STATUS_DEFAULT    = 0;   // 缺省UI
	static final int UI_STATUS_CCC        = 1;
	static final int UI_STATUS_LOGIN      = 2;
	
	
	private boolean       m_show_how      = false;   // 程序当前是否仅用来演示界面
	private FrameLayout   m_mainLayout    = null;
	private Context       m_context       = null;
	private MainUiNotify  m_uiNotify      = null;
	private WiFiHelper    wifiHelper      = null;
	private Device        m_device        = null;
	private CCC_UI        m_cccUI         = null;
	private int           m_uiPageNo      = -1;      
    private DownloadFile  m_downloadFile  = new DownloadFile();    
    private CommInfo      m_commInfo      = new CommInfo();
    
    private int           m_uiStatus      = UI_STATUS_BLANK;
    private MyHandler     m_handler       = new MyHandler(this);
    private DeviceMsg     m_deviceMsg     = null;
    private WiFiState     m_wifiState     = new WiFiState();
	private Configure     m_configure     = null; 
	private LoginSettingReceiver settingReceiver = null;
	
    
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		//this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		
		settingReceiver = new LoginSettingReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ActivityLogin.PHLX_LOGIN_RESULT_ACTION);
        this.registerReceiver(settingReceiver, filter); 
        
		if(!Initial(this)){
			SetToastMessage(Language.Get(Language.LID_STRING_12));
		}
	}
	
	@Override
	protected void onStart()
	{
		// TODO Auto-generated method stub
		super.onStart();
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}
	@Override
	protected void onDestroy() 
	{
		this.unregisterReceiver(settingReceiver);
		m_wifiState.Release();
		m_device.CloseComm();
		FileUtil.DeleteFolder(FileUtil.GetTempPath());
		super.onDestroy();
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) 
	{
		// Auto-generated method stub
		super.onSaveInstanceState(outState);
	}
	
	// 系统选项菜单
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.main, menu);
		CreatePopupMenu(menu);
		return true;
	}
	// 弹出菜单
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) 
	{
		CreatePopupMenu(menu);
		// Auto-generated method stub
		super.onCreateContextMenu(menu, v, menuInfo);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		return PopupMenuSelected(item);
	}
	@Override
	public boolean onContextItemSelected(MenuItem item)
	{
		PopupMenuSelected(item);
		return super.onContextItemSelected(item);
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) 
	{
		if(keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0)
		{
			MessageBox.Show(this, Language.Get(Language.LID_STRING_11), new MessageBox.OnNotifyListener() {
				
				@Override
				public void OnClick(int which) {
					if(MessageBox.ID_OK == which) MainActivity.this.finish();;
				}
			});
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	
    private void LoadMainUI(int newUiStatus)
    {
    	if(m_uiStatus == newUiStatus)
    		return;
    	   	
    	switch(newUiStatus)
    	{
    	case UI_STATUS_CCC:    		
    		m_uiNotify.setVisibility(View.INVISIBLE);
    		
    		m_cccUI.setVisibility(View.VISIBLE);	
    		this.registerForContextMenu(m_cccUI);
    		break;
    		    		
    	default:
    		m_cccUI.setVisibility(View.INVISIBLE);
    		
    		m_uiNotify.setVisibility(View.VISIBLE);
    		this.registerForContextMenu(m_uiNotify);    		
    	}
    	
    	m_uiStatus = newUiStatus;
    }
    private void ShowDefaultUI()
    {
    	LoadMainUI(UI_STATUS_DEFAULT);    	
    }
    private void ShowUiCCC()
    {
    	LoadMainUI(UI_STATUS_CCC);    	
    }
    private void ShowUiLogin()
    {
    	//LoadMainUI(UI_STATUS_LOGIN); 
    	Intent i = new Intent();
		i.setClass(this, ActivityLogin.class);
		i.putExtra(ActivityLogin.KEY_LBL_IP_ADDR,  Language.Get(Language.LID_STRING_1));
		i.putExtra(ActivityLogin.KEY_LBL_IP_PORT,  Language.Get(Language.LID_STRING_2));
		i.putExtra(ActivityLogin.KEY_BTN_OPEN,     Language.Get(Language.LID_STRING_OPEN));
		i.putExtra(ActivityLogin.KEY_BTN_CANCEL,   Language.Get(Language.LID_STRING_CANCEL));
		i.putExtra(ActivityLogin.KEY_BTN_SEARCH,   "搜索");
		i.putExtra(ActivityLogin.KEY_EDIT_IP_ADDR, m_configure.GetIpAddr());
		i.putExtra(ActivityLogin.KEY_EDIT_IP_PORT, m_configure.GetIpPortStr());
		this.startActivity(i);
    }
    
	private void SetProgress(int percent, String comment)
	{
		if(m_uiNotify == null)
			return;
		
		m_uiNotify.SetProgress(percent, comment);
	}
	private void SetWndMessage(String strText)
	{
		if(m_uiNotify == null)
			return;
		m_uiNotify.ShowLog(strText);
	}
	private void SetToastMessage(String strText)
	{
		Toast.makeText(m_context, strText, Toast.LENGTH_SHORT).show();
	}
	private void SetDebugLog(String strText)
	{
		System.out.println(String.format("--- debug ---%s :%s", this.getClass().toString(), strText));
	}	

	// 把通知信息放入到队列中
	private void SetLogMessage(String strText)
	{
		PostMessage(MsgIdUi.MSG_UI_LOG_MESSAGE, 0, 0, strText);
	}

	private boolean IsSettingStatus()
	{
		return(UI_STATUS_LOGIN == m_uiStatus) ? true:false;
	}
	private boolean Initial(Context context)
	{
		m_context = context;
		if(!InitialUI(context))
			return false;
		
		m_deviceMsg = new DeviceMsg(m_handler);
		m_configure = new Configure(this);
		wifiHelper  = new WiFiHelper(context);
		m_device    = new Device();
		
		m_wifiState.Initial(this, m_handler);
		m_device.Initial(this, m_deviceMsg, FileUtil.GetTempPath());
		
		ApplicationStart();
		return true;
	}
	
	private boolean InitialUI(Context context)
	{
		m_mainLayout = new FrameLayout(context);
			
		m_uiNotify = new MainUiNotify(context);    		
		m_uiNotify.SetBkImage(ImageHelper.LoadBitmapFromAssets(this, Global.LOGO_START));
		m_mainLayout.addView(m_uiNotify);
		
		m_cccUI = new CCC_UI(context);
		m_mainLayout.addView(m_cccUI);
		
		setContentView(m_mainLayout);
		
				
		if(!m_cccUI.Initial(this))
			return false;
		
		ShowDefaultUI();
		return true;
	}
	
	private void ApplicationStart()
	{	
		String ipAddr = m_configure.GetIpAddr();
		int ipPort    = m_configure.GetIpPort();
		m_commInfo.SetConnInfo(ipAddr, ipPort);				
		m_device.SetComm(ipAddr, ipPort);
		
		wifiHelper.OpenWifi();
		//m_device.OpenComm();
	}
	
	private void RebuildConnect()
	{
		m_device.CloseComm();
		m_device.OpenComm();
	}
	private void CreatePopupMenu(Menu menu)
	{
		/*
		int itemId = MENU_ITEM_ID_LOAD;
		String strTitle = Language.Get(Language.LID_STRING_8);
		if(m_show_how)
		{
			itemId = MENU_ITEM_ID_RESTORE;
			strTitle = Language.Get(Language.LID_STRING_7);
		}
		*/
		menu.clear();
		menu.add(0, MENU_ITEM_ID_SETTING, 1, Language.Get(Language.LID_STRING_4));
		//menu.add(0, itemId,               2, strTitle);
		menu.add(1, MENU_ITEM_ID_ABOUT,   3, Language.Get(Language.LID_STRING_ABOUT));
		menu.add(1, MENU_ITEM_ID_EXIT,    4, Language.Get(Language.LID_STRING_EXIT));
	}
	private boolean PopupMenuSelected(MenuItem item)
	{
		switch(item.getItemId())
		{
		case MENU_ITEM_ID_EXIT:
			this.finish();
			break;
		case MENU_ITEM_ID_ABOUT:
			Intent i = new Intent();
    		i.setClass(this, About.class);
    		this.startActivity(i);
			break;
		case MENU_ITEM_ID_SETTING:
			ShowUiLogin();
			break;
			
		case MENU_ITEM_ID_RESTORE:
			{
				m_show_how = false;
			}
			break;
		case MENU_ITEM_ID_LOAD:
			{
				m_show_how = true;
				NotifyLoadUIFile(GetInternalUiZip(m_configure.GetFileTitle()));
			}
			break;
		}
		return false;
	}		
			
	private void NotifyLoadUIFile(String filename)
	{
		SetLogMessage(Language.Get(Language.LID_STRING_10));	
		PostMessageDelayed(MsgIdUi.MSG_UI_LOAD_CCC, m_show_how ?1:0, 0, filename, 200);
	}
	
	private String GetInternalUiZip(String fileTitle)
	{
		return String.format("%s%s.zip", FileUtil.GetSkinPath(), fileTitle);	
	}
	
	private boolean LoadUIFile(String filename, boolean b_showHow)
	{	
		if(!FileUtil.Exists(filename))
		{
			SetLogMessage(String.format("Error :%s not exist\n", filename));
			return false;
		}
		
		return m_cccUI.LoadUI(filename, b_showHow, m_uiPageNo);
	}
			
	private boolean CheckIpAddr(String ipAddr, String ipPort) 
	{
		System.out.printf("%s %s\n", ipAddr, ipPort);
		try
		{
			Integer.parseInt(ipPort);
		}
		catch(NumberFormatException e)
		{
			e.printStackTrace();
			return false;	
		}
				
		return true;
		
	}
	
	// 装载CCC界面
	private void OnMsgLoadUiCCC(String filename, boolean b_showHow)
	{		
		if(LoadUIFile(filename, b_showHow))
		{
			ShowUiCCC(); 
		}
		else
		{
			SetDebugLog(String.format("OnMsgLoadLocalUI %s\n", "failed"));
	
			if(m_show_how)           // 演示版时
			{
				this.ShowDefaultUI();
				SetLogMessage(Language.Get(Language.LID_STRING_9));	
			}
			else
			{
				this.m_configure.SaveFileInfo("", "");
				m_device.CheckZipFileVersion();
			}
		}
	}
	// 显示提示消息
	private void OnMsgLogMessage(String strText)
	{	
		if(UI_STATUS_DEFAULT == m_uiStatus) SetWndMessage(strText);
		else{
			SetToastMessage(strText);
		}
	}
		
	private void OnMsgControlProperty(int ctrlId, int property, int value)
	{
		this.m_cccUI.SetControlProperty(ctrlId, property, value);
	}
	private void OnMsgInvokePage(int page_no, boolean b_show)
	{
		this.m_cccUI.InvokePage(page_no, b_show);
	}
	
	private void OnMsgDownloadFinished(boolean bSuccess, int fileType, String filePath)
	{
		if(IsSettingStatus())
			return;
		
		m_uiPageNo = -1;
		if(bSuccess)
		{
			boolean bResult = false;
			if(m_downloadFile.ProcessDownload(filePath, FileUtil.GetSkinPath()))
			{
				String fileTitle = m_downloadFile.GetFileTitle();		
				if(fileTitle != null)
				{
					m_configure.SaveFileInfo(fileTitle, m_downloadFile.GetFileVersion());
					NotifyLoadUIFile(GetInternalUiZip(fileTitle));
					bResult = true;
				}	
			}
			bSuccess = bResult;
		}
		if(!bSuccess)
		{	
			this.SetLogMessage(Language.Get(Language.LID_STRING_16));
			m_device.CheckZipFileVersion();
		}	
	}
	private void OnMsgDownloadPercent(int percent, int speed)
	{
		if(IsSettingStatus())
			return;
		
		SetProgress(percent, String.format("%d%% %dKB/s", percent, speed));		
	}	
	// 收到文件版本信息
	private void OnMsgVersionInfo(boolean bSuccess, int fileType, String version)
	{
		if(IsSettingStatus())
			return;
		
		if(bSuccess)
		{
			boolean b_download = false;
			String oldVersion = m_configure.GetFileVersion();
			if(version.equalsIgnoreCase(oldVersion))
			{
				System.out.printf("%s = %s\n", version, oldVersion);
				NotifyLoadUIFile(GetInternalUiZip(m_configure.GetFileTitle()));
			}
			else
			{
				System.out.printf("%s != %s\n", version, oldVersion);
				b_download = true;
			}
			
			m_downloadFile.SaveFileVersion(version);
			if(b_download) 
			{
				ShowDefaultUI();
				SetLogMessage(Language.Get(Language.LID_STRING_17));
				m_device.DownloadZipFile();
			}
		}
		else
		{
			m_device.CheckZipFileVersion();
		}
	}
	
	private void OnMsgHasNewUiCCC()
	{
		//this.SetLogMessage("服务器上有新界面");
		if(IsSettingStatus())
			return;
		
		m_device.CheckZipFileVersion();       // 如果不从检查版本开始，则无法获取到新文件的版本信息		
	}
		
	private void OnMsgConnectStatus(int connStatus)
	{
		if(IsSettingStatus())
			return;
		
		m_commInfo.SetConnectStatus(connStatus);		
		if(!m_commInfo.IsConnected()) ShowDefaultUI();
		else
		{
			this.m_device.LoginToServer();
			this.m_device.CheckZipFileVersion();
			
			System.out.printf("%s CheckZipFileVersion\n", this.getClass().toString());
		}
		
		this.SetLogMessage(m_commInfo.GetConnNotify());
	}
	
	private void OnMsgDiscoverDevice(String ipAddr, String hostname)
	{
		System.out.printf("OnMsgDiscoverDevice %s %s\n", ipAddr, hostname);
		UpdateLoginSetting(ipAddr);
	}
	private void OnMsgWifiStatus(boolean b_success, String strText)
	{
		SetLogMessage(strText);
		if(!b_success) 
		{
			if((UI_STATUS_CCC == m_uiStatus) )
				ShowDefaultUI();
		}
		else
		{
			RebuildConnect();
		}
	}
	
	private void ProcessDeviceMsg(Message msg)
	{
		if(m_show_how)           // 演示版时
			return;
		
		switch(msg.what)
		{
		case MsgIdUi.MSG_CONNECT_STATUS:
			OnMsgConnectStatus(msg.arg1);
			break;
		case MsgIdUi.MSG_UI_DISCOVER_DEV:
			ParaDiscoverDev para = (ParaDiscoverDev)msg.obj;
			OnMsgDiscoverDevice(para.ipAddr, para.hostname);
			break;
			
		case MsgIdUi.MSG_UI_HAS_NEW_ZIP:
			OnMsgHasNewUiCCC();
			break;
		
		case MsgIdUi.MSG_UI_VERSION_INFO:
			OnMsgVersionInfo((0 == msg.arg1) ? false:true, msg.arg2, (String)msg.obj);
			break;
		case MsgIdUi.MSG_UI_ZIP_INFO:
			m_downloadFile.SaveFilename((String)msg.obj); 
			break;
		case MsgIdUi.MSG_UI_DL_PERCENT:
			OnMsgDownloadPercent(msg.arg1, msg.arg2);
			break;
		case MsgIdUi.MSG_UI_DL_FINISH:
			OnMsgDownloadFinished((0 == msg.arg1)?false:true, msg.arg2, (String)msg.obj);
			break;
					
		case MsgIdUi.MSG_UI_CTRL_PROPERTY:
			OnMsgControlProperty(msg.arg1, msg.arg2, Integer.parseInt((String)msg.obj));
			break;
		case MsgIdUi.MSG_UI_INVOKE_PAGE:
			OnMsgInvokePage(msg.arg1, (0 == msg.arg2) ? false:true);
			break;
		}	
	}
	private void ProcessMsgQueue(Message msg)
	{
		switch(msg.what)
		{
		// WIFI
		case MsgIdUi.MSG_WIFI_STATUS:
			OnMsgWifiStatus((1 == msg.arg1) ? true:false, (String)msg.obj);
			break;
			
		case MsgIdUi.MSG_CONNECT_STATUS:
		case MsgIdUi.MSG_UI_DISCOVER_DEV:
		case MsgIdUi.MSG_UI_HAS_NEW_ZIP:
		case MsgIdUi.MSG_UI_VERSION_INFO:
		case MsgIdUi.MSG_UI_ZIP_INFO:
		case MsgIdUi.MSG_UI_DL_PERCENT:
		case MsgIdUi.MSG_UI_DL_FINISH:
		case MsgIdUi.MSG_UI_CTRL_PROPERTY:
		case MsgIdUi.MSG_UI_INVOKE_PAGE:
			ProcessDeviceMsg(msg);
			break;
			
		case MsgIdUi.MSG_UI_LOG_MESSAGE:
			OnMsgLogMessage((String)msg.obj);
			break;
		case MsgIdUi.MSG_UI_LOAD_CCC:
			OnMsgLoadUiCCC((String)msg.obj, (0 == msg.arg1) ? false:true);
			break;
		}
	}
	
	private void PostMessage(int what, int arg1, int arg2, Object obj)
	{
		PostMessageDelayed(what, arg1, arg2, obj, 0);	
	}
	private void PostMessageDelayed(int what, int arg1, int arg2, Object obj, long delayMillis)
	{
		Message msg = Message.obtain();
		msg.what = what;
		msg.arg1 = arg1;
		msg.arg2 = arg2;
		msg.obj  = obj;
		m_handler.sendMessageDelayed(msg, delayMillis);	
	}
		
	
	/* ******************  接口 ICCC_UI  ****************** */
	@Override
	public void ccc_ui_invoke_app(String packageName)
	{
		InvokeApp.Run(this, packageName);
		//System.out.printf("ccc_ui_invoke_app packageName = %s\n", packageName);
	}
	@Override
	public void ccc_ui_event(int ctrlId, int ctrlEvent, int value)
	{
		m_device.SendEvent(ctrlId, ctrlEvent, value);
	}	
	@Override
	public void ccc_ui_warning(String strWarning) 
	{	
		// TODO: 会异常
		MessageBox.Show(this, strWarning, new MessageBox.OnNotifyListener() {
			
			@Override
			public void OnClick(int which) {
				if(MessageBox.ID_OK == which) m_cccUI.ExecuteDelayTask();
			}
		});
	}
	@Override
	public void ccc_ui_load_info(int uiPageNo)
	{
		this.m_uiPageNo = uiPageNo;		
	}
}