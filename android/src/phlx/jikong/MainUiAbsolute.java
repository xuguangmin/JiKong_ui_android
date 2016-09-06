package phlx.jikong;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Button;

public class MainUiAbsolute extends RelativeLayout implements View.OnClickListener
{
	public interface OnResultListener 
	{
        void OnUiLoginLoginInfo(String ipAddr, String ipPort);
        void OnUiLoginButtonID(int id);
    }
	
	public class UiCell
	{
		public UiCell(View child, int x, int y, int width, int height)
		{
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
			this.v = child;
		}
		int x;
		int y;
		int width;
		int height;
		View v;
	}
	
	public static final int ID_CANCEL    = 0;
	public static final int ID_OK        = 1;
	public static final int ID_SEARCH    = 2;
	
	private ImageView m_imgIcon   = null;
	private TextView  m_lblIpAddr  = null;
	private TextView  m_lblIpPort  = null;
	private EditText  m_editIpAddr = null;
	private EditText  m_editIpPort = null;
	private Button    m_btnOk      = null;
	private Button    m_btnCancel  = null;
	private Button    m_btnSearch  = null;
	private OnResultListener  m_listener = null;
	private List<UiCell>      m_uiCellList = null;
	
	public MainUiAbsolute(Context context) 
	{
		super(context);
		setWillNotDraw(false);
		setOnClickListener(this);
		m_uiCellList = new ArrayList<UiCell>();
		
		m_imgIcon    = new ImageView(context);
		m_lblIpAddr  = new TextView(context);
		m_lblIpPort  = new TextView(context);
		m_editIpAddr = new EditText(context);  
		m_editIpPort = new EditText(context);
		m_btnOk      = new Button(context);
		m_btnCancel  = new Button(context);
		m_btnSearch  = new Button(context);
		
		m_imgIcon.setImageResource(R.drawable.ic_launcher);
		m_lblIpAddr.setText("IP地址");
		m_editIpAddr.setText("192.168.1.100");
		m_editIpAddr.setBackgroundColor(Color.LTGRAY);
				
		m_lblIpPort.setText("IP端口");
		m_editIpPort.setText("9000");
		m_editIpPort.setBackgroundColor(Color.LTGRAY);
		
		
		m_btnOk.setText("连接");
		m_btnOk.setOnClickListener(this);
		m_btnCancel.setText("取消");
		m_btnCancel.setOnClickListener(this);
		m_btnSearch.setText("搜索");
		m_btnSearch.setOnClickListener(this);	
				
		
		
		
		
		addUiCell(m_imgIcon,    10, 10, 20, 20);
		
		addUiCell(m_lblIpAddr,  0,  35, 20, 15);
		addUiCell(m_editIpAddr, 20, 35, 61, 15);
		addUiCell(m_btnSearch,  80, 35, 18, 15);
		
		addUiCell(m_lblIpPort,  0,  55, 20, 15);
		addUiCell(m_editIpPort, 20, 55, 78, 15);
		addUiCell(m_btnOk,      50, 75, 25, 15);
		addUiCell(m_btnCancel,  75, 75, 25, 15);	
		
		System.out.printf("---------------------- MainUiLogin\n");
		//ChangeSize(800, 600);
	}
	
	
	public void addUiCell(View child, int x, int y, int width, int height) 
	{
		UiCell uic = new UiCell(child, x, y, width, height);
		m_uiCellList.add(uic);	
		this.addView(uic.v);//, new MyAbsLayout.LayoutParams(10, 10, 10, 10));
	}
	
	protected void ChangeSize(int w, int h) 
	{
		//super.onSizeChanged(w, h, oldw, oldh);
		System.out.printf("---------------------- w = %d h = %d\n", w, h);
				
		int x, y;
		int width, height;
		for(int k = 0; k < m_uiCellList.size(); ++k)
		{
			UiCell uic = m_uiCellList.get(k);
			
			x = (uic.x * w)/100;
			y = (uic.y * h)/100;
			width = (uic.width * w)/100;
			height = (uic.height * h)/100;
		
			//this.updateViewLayout(uic.v, new MyAbsLayout.LayoutParams(width, height, x, y));
			
			ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(width, height);//ViewGroup.LayoutParams.WRAP_CONTENT);
			params.setMargins(x, y, x+width, y+height);
			this.updateViewLayout(uic.v, new RelativeLayout.LayoutParams(params));
						
			System.out.printf("---------------------- %d %d %d %d\n", x, y, width, height);
		}
		this.invalidate();
	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) 
	{
		
		System.out.println("----- onSizeChanged");
		ChangeSize(w, h);
		super.onSizeChanged(w, h, oldw, oldh);
	}
	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);	
	}
	private String GetIpAddr() {return m_editIpAddr.getText().toString();}
	private String GetIpPort() {return m_editIpPort.getText().toString();}

	@Override
	public void onClick(View v) 
	{
		if(v == m_btnOk)
		{
			if(m_listener != null) 
			{
				m_listener.OnUiLoginLoginInfo(GetIpAddr(), GetIpPort());
				m_listener.OnUiLoginButtonID(ID_OK);
			}
		}
		else if(v == m_btnCancel)
		{
			if(m_listener != null) m_listener.OnUiLoginButtonID(ID_CANCEL);
		}
		else if(v == m_btnSearch)
		{
			if(m_listener != null) m_listener.OnUiLoginButtonID(ID_SEARCH);
		}		
	}
	
	public void SetEditInfo(String ipAddr, String ipPort)
	{
		if(ipAddr != null) m_editIpAddr.setText(ipAddr);
		if(ipPort != null) m_editIpPort.setText(ipPort);
	}
	public void SetOnResultListener(OnResultListener l)
	{
		m_listener = l;
	}
}
