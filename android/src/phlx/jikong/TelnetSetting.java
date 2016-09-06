package phlx.jikong;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.AbsoluteLayout;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import phlx.jikong.utils.lang.Language;

@SuppressWarnings("deprecation")
class ConfigView extends TableLayout
{
	private TextView m_lblIpAddr  = null;
	private TextView m_lblIpPort  = null;
	private EditText m_editIpAddr = null;
	private EditText m_editIpPort = null;
	private TableRow m_rowFirst   = null;
	private TableRow m_rowSecond  = null;
	
	public ConfigView(Context context)
	{
		super(context);
		
		m_rowFirst   = new TableRow(context);
		m_rowSecond  = new TableRow(context);
		m_lblIpAddr  = new TextView(context);
		m_lblIpPort  = new TextView(context);
		m_editIpAddr = new EditText(context);
		m_editIpPort = new EditText(context);
		
		m_lblIpAddr.setText(Language.Get(Language.LID_STRING_1));
		m_editIpAddr.setText("192.168.1.100");
		m_lblIpPort.setText(Language.Get(Language.LID_STRING_2));
		m_editIpPort.setText("9000");
		
		m_rowFirst.addView(m_lblIpAddr);
		m_rowFirst.addView(m_editIpAddr);//, new LayoutParams(200,LayoutParams.MATCH_PARENT));
		m_rowSecond.addView(m_lblIpPort);
		m_rowSecond.addView(m_editIpPort);
		this.addView(m_rowFirst);
		this.addView(m_rowSecond);
	}
	
	public void SetEditInfo(String ipAddr, String ipPort)
	{
		m_editIpAddr.setText(ipAddr);
		m_editIpPort.setText(ipPort);
	}

	public String GetIpAddr() {return m_editIpAddr.getText().toString();}
	public String GetIpPort() {return m_editIpPort.getText().toString();}
}


@SuppressWarnings("deprecation")
class ConfigView2 extends AbsoluteLayout
{
	private TextView m_lblIpAddr  = null;
	private TextView m_lblIpPort  = null;
	private EditText m_editIpAddr = null;
	private EditText m_editIpPort = null;
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) 
	{
		// TODO Auto-generated method stub
		super.onSizeChanged(w, h, oldw, oldh);
	}

	public ConfigView2(Context context)
	{
		super(context);
		m_lblIpAddr  = new TextView(context);
		m_lblIpPort  = new TextView(context);
		m_editIpAddr = new EditText(context);
		m_editIpPort = new EditText(context);
		
		m_lblIpAddr.setText(Language.Get(Language.LID_STRING_1));
		m_editIpAddr.setText("192.168.1.100");
		m_lblIpPort.setText(Language.Get(Language.LID_STRING_2));
		m_editIpPort.setText("9000");
		
		this.addView(m_lblIpAddr,  new AbsoluteLayout.LayoutParams(100, 22, 30, 10));
		this.addView(m_editIpAddr, new AbsoluteLayout.LayoutParams(100, 30, 150, 10));
		this.addView(m_lblIpPort,  new AbsoluteLayout.LayoutParams(100, 22, 30, 50));
		this.addView(m_editIpPort, new AbsoluteLayout.LayoutParams(100, 30, 150, 50));
	}
	
	public void SetEditInfo(String ipAddr, String ipPort)
	{
		m_editIpAddr.setText(ipAddr);
		m_editIpPort.setText(ipPort);
	}

	public String GetIpAddr() {return m_editIpAddr.getText().toString();}
	public String GetIpPort() {return m_editIpPort.getText().toString();}
}

public class TelnetSetting
{
	public interface OnResultListener 
	{
        void OnResult(String ipAddr, String ipPort);
    }
	
	/*
	public void onClick(DialogInterface dialog, int which) 
	{
		switch(which)
		{
		case DialogInterface.BUTTON_POSITIVE:
			if(m_listener != null) 
			{
				m_listener.OnResult(m_dlgView.GetIpAddr(), m_dlgView.GetIpPort());
			}
			break;
		case DialogInterface.BUTTON_NEGATIVE:
			break;
		}
		dialog.dismiss();
		m_dlgView = null;
	}
		*/

	private static void CreateDialog(Context context, String strTitle, View dlgView, 
			                         DialogInterface.OnClickListener l)
	{       
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(strTitle);
        builder.setView(dlgView);
        builder.setIcon(R.drawable.ic_launcher);
        builder.setPositiveButton(Language.Get(Language.LID_STRING_OK), l);
        builder.setNegativeButton(Language.Get(Language.LID_STRING_CANCEL), l);
        builder.show();
	}
	
	private static ConfigView2  m_dlgView = null;
	private static OnResultListener  m_listener = null;
	
	public static void Show(Context context, String ipAddr, String ipPort, String strMsg, OnResultListener l)
	{
		m_listener = l;
		m_dlgView = new ConfigView2(context);
		m_dlgView.SetEditInfo(ipAddr, ipPort);
		
		CreateDialog(context, strMsg, m_dlgView, new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) 
			{
				switch(which)
				{
				case DialogInterface.BUTTON_POSITIVE:
					if(m_listener != null) 
					{
						m_listener.OnResult(m_dlgView.GetIpAddr(), m_dlgView.GetIpPort());
					}
					break;
				case DialogInterface.BUTTON_NEGATIVE:
					break;
				}
				
				dialog.dismiss();
				m_dlgView = null;
				m_listener = null;
			}
		});
	}
}


/*
 	private void ShowSetting()
	{
		TelnetSetting.Show(this, m_configure.GetIpAddr(), 
				             String.format("%d", m_configure.GetIpPort()), 
				             Language.Get(Language.LID_STRING_4), 
				             new TelnetSetting.OnResultListener(){
			@Override
			public void OnResult(String ipAddr, String ipPort) 
			{
				OnConnectionModify(ipAddr, ipPort);
			}
		});
	} 
 */
