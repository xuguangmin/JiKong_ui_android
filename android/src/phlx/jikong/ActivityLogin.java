package phlx.jikong;

/* **************************************************************************

			版权所有 (C), 2001-2020, 北京飞利信科技股份有限公司

*****************************************************************************
	文件名称 : ActivityLogin.java
	作者           : 贾延刚
	生成日期 : 2014-08-07
	
	版本           : 1.0
	功能描述 : 
	
	修改历史 :
	说明           ：
	                       设置对话框，用来修改IP和端口
	                       和主Activity之间通过广播来交互

******************************************************************************/

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ActivityLogin extends Activity
{
	public static final String PHLX_LOGIN_RESULT_ACTION    = "phlx.jikong.login.result";
	public static final String PHLX_LOGIN_SET_VALUE_ACTION = "phlx.jikong.login.set.value";
		
	public static final String KEY_LBL_IP_ADDR  = "key_lbl_ip_addr";
	public static final String KEY_LBL_IP_PORT  = "key_lbl_ip_port";
	public static final String KEY_EDIT_IP_ADDR = "key_edit_ip_addr";
	public static final String KEY_EDIT_IP_PORT = "key_edit_ip_port";
	public static final String KEY_BTN_OPEN     = "key_btn_open";
	public static final String KEY_BTN_CANCEL   = "key_btn_cancel";
	public static final String KEY_BTN_SEARCH   = "key_btn_search";
	
	
	public static final String LOGIN_REQUEST = "custom.request";
	public static final int    REQUEST_ID_SEARCH = 1;
	public static final int    REQUEST_ID_OK     = 2;
	public static final int    REQUEST_ID_CANCEL = 3;
	
	public class MainUiLogin extends LinearLayout implements View.OnClickListener
	{		
		private ImageView m_imgIcon    = null;
		private TextView  m_lblIpAddr  = null;
		private TextView  m_lblIpPort  = null;
		private EditText  m_editIpAddr = null;
		private EditText  m_editIpPort = null;
		private Button    m_btnOk      = null;
		private Button    m_btnCancel  = null;
		private Button    m_btnSearch  = null;
			
		
		public MainUiLogin(Context context) 
		{
			super(context);
			setOnClickListener(this);
			
			m_imgIcon    = new ImageView(context);
			m_lblIpAddr  = new TextView(context);
			m_lblIpPort  = new TextView(context);
			m_editIpAddr = new EditText(context);  
			m_editIpPort = new EditText(context);
			m_btnOk      = new Button(context);
			m_btnCancel  = new Button(context);
			m_btnSearch  = new Button(context);
			
			m_imgIcon.setImageResource(R.drawable.ic_launcher);
			m_lblIpAddr.setText(m_strLblIpAddr);
			m_editIpAddr.setText(m_strEditIpAddr);
			m_editIpAddr.setBackgroundColor(Color.LTGRAY);
			m_editIpAddr.setGravity(Gravity.BOTTOM);
			
			m_lblIpPort.setText(m_strLblIpPort);
			m_editIpPort.setText(m_strEditIpPort);
			m_editIpPort.setBackgroundColor(Color.LTGRAY);
			
			
			m_btnOk.setText(m_strBtnOpen);
			m_btnOk.setOnClickListener(this);
			m_btnCancel.setText(m_strBtnCancel);
			m_btnCancel.setOnClickListener(this);
			m_btnSearch.setText(m_strBtnSearch);
			m_btnSearch.setOnClickListener(this);	
					
			this.setOrientation(LinearLayout.VERTICAL);
			
			// ------------  顶端部分    ------------
			LinearLayout llUp = new LinearLayout(context);
			llUp.setOrientation(LinearLayout.VERTICAL);
					
			LinearLayout llu_1 = new LinearLayout(context);
			llu_1.setGravity(Gravity.RIGHT);
			llu_1.addView(m_btnSearch);//, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.RIGHT));
			llUp.addView(llu_1);
			
			llUp.addView(m_imgIcon);
			this.addView(llUp);
			
			
			// ------------  中间部分    ------------
			LinearLayout llMiddle = new LinearLayout(context);
			llMiddle.setOrientation(LinearLayout.VERTICAL);
			llMiddle.setPadding(2, 5, 2, 2);
			
			LinearLayout llm1 = new LinearLayout(context);
			llm1.setOrientation(LinearLayout.HORIZONTAL);
			llm1.setPadding(2, 5, 2, 2);
			
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(100,ViewGroup.LayoutParams.WRAP_CONTENT);//ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);//, Gravity.LEFT);
			params.weight = 1;
			llm1.addView(m_lblIpAddr, params);
			params = new LinearLayout.LayoutParams(100,ViewGroup.LayoutParams.WRAP_CONTENT);//ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);//, Gravity.RIGHT);
			params.weight = 2;
			llm1.addView(m_editIpAddr, params);
			llMiddle.addView(llm1);
			
			LinearLayout llm2 = new LinearLayout(context);
			llm2.setOrientation(LinearLayout.HORIZONTAL);
			llm2.setPadding(2, 5, 2, 2);
			params = new LinearLayout.LayoutParams(100, ViewGroup.LayoutParams.WRAP_CONTENT);//, Gravity.LEFT);
			params.weight = 1;
			llm2.addView(m_lblIpPort, params);
			params = new LinearLayout.LayoutParams(100, ViewGroup.LayoutParams.WRAP_CONTENT);//, Gravity.RIGHT);
			params.weight = 2;
			llm2.addView(m_editIpPort, params);
			llMiddle.addView(llm2);
			
			LinearLayout llm3 = new LinearLayout(context);
			llm3.setOrientation(LinearLayout.HORIZONTAL);
			llm3.setPadding(2, 5, 2, 2);
			llm3.addView(m_btnOk, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.RIGHT));
			llm3.addView(m_btnCancel, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.RIGHT));
			llMiddle.addView(llm3);
			
			this.addView(llMiddle);
		}
		
		private String GetIpAddr() 
		{
			return m_editIpAddr.getText().toString();
		}
		private String GetIpPort() 
		{
			return m_editIpPort.getText().toString();
		}
		public void SetEditInfo(String ipAddr, String ipPort)
		{
			if(ipAddr != null) m_editIpAddr.setText(ipAddr);
			if(ipPort != null) m_editIpPort.setText(ipPort);
		}
		

		@Override
		public void onClick(View v) 
		{
			if(v == m_btnOk)
			{
				Intent i = new Intent();
				i.setAction(PHLX_LOGIN_RESULT_ACTION);
				i.putExtra(LOGIN_REQUEST, REQUEST_ID_OK);
				
				i.putExtra(KEY_EDIT_IP_ADDR, GetIpAddr());
				i.putExtra(KEY_EDIT_IP_PORT, GetIpPort());
				ActivityLogin.this.sendBroadcast(i);
				ActivityLogin.this.finish();
			}
			else if(v == m_btnCancel)
			{
				Intent i = new Intent();
				i.setAction(PHLX_LOGIN_RESULT_ACTION);
				i.putExtra(LOGIN_REQUEST, REQUEST_ID_CANCEL);
				ActivityLogin.this.sendBroadcast(i);
				ActivityLogin.this.finish();
			}
			else if(v == m_btnSearch)
			{
				Intent i = new Intent();
				i.setAction(PHLX_LOGIN_RESULT_ACTION);
				i.putExtra(LOGIN_REQUEST, REQUEST_ID_SEARCH);
				ActivityLogin.this.sendBroadcast(i);
			}		
		}
	}
	
	private MainUiLogin  m_mainView = null;
	private String       m_strLblIpAddr = "";
	private String       m_strLblIpPort = "";
	private String       m_strEditIpAddr = "";
	private String       m_strEditIpPort = "";
	private String       m_strBtnOpen = "";
	private String       m_strBtnCancel = "";
	private String       m_strBtnSearch = "";
	private SetValueReceiver  m_setReceiver = null;
	
	@Override
    public void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);
        
        // 注册广播接收器
        m_setReceiver = new SetValueReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(PHLX_LOGIN_SET_VALUE_ACTION);
        this.registerReceiver(m_setReceiver, filter);
        
        // 获取控件的字符
        Intent i = this.getIntent();
        m_strLblIpAddr    = i.getStringExtra(KEY_LBL_IP_ADDR);
        m_strLblIpPort    = i.getStringExtra(KEY_LBL_IP_PORT);
    	m_strBtnOpen      = i.getStringExtra(KEY_BTN_OPEN);
    	m_strBtnCancel    = i.getStringExtra(KEY_BTN_CANCEL);
    	m_strBtnSearch    = i.getStringExtra(KEY_BTN_SEARCH);
    	m_strEditIpAddr   = i.getStringExtra(KEY_EDIT_IP_ADDR);
    	m_strEditIpPort   = i.getStringExtra(KEY_EDIT_IP_PORT);
       
    	this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE); 
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        m_mainView = new MainUiLogin(this);
        setContentView(m_mainView);
    }
	@Override
	protected void onDestroy() 
	{
		this.unregisterReceiver(m_setReceiver);
		super.onDestroy();
	}


	private void SetEditInfo(String ipAddr, String ipPort)
	{
		if(m_mainView != null) 
		{
			m_mainView.SetEditInfo(ipAddr, ipPort);
		}
	}
	public class SetValueReceiver extends BroadcastReceiver
	{
		@Override
		public void onReceive(Context context, Intent intent) 
		{
	        if(intent.getAction().equals(PHLX_LOGIN_SET_VALUE_ACTION))
	        {
	        	String ipAddr  = intent.getStringExtra(KEY_EDIT_IP_ADDR);
	        	String strPort = intent.getStringExtra(KEY_EDIT_IP_PORT);
	        	SetEditInfo(ipAddr, strPort);
	        }
		}
	}
}

