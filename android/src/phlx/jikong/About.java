package phlx.jikong;

import phlx.jikong.utils.HostInfo;
import phlx.jikong.utils.lang.Language;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

/* 版本说明
 * 
 * 0.9.3.0
 * 增加了视频播放模块
 * 为了适应这个，大幅度调整了程序结构
 * 
 * 0.9.2.5
 * Label控件解决不能隐藏的问题
 * 
 * 0.9.2.4
 * 增加ActivityLogin类，使用Activity来实现设置对话框
 * 和主Activity之间的数据交互，采用广播来进行
 */

public class About extends Activity implements View.OnClickListener
{
	public class ViewAbout extends View
	{	
		private Paint      m_paint      = new Paint(Paint.ANTI_ALIAS_FLAG|Paint.DITHER_FLAG);
		private Rect       m_srcRect    = new Rect(0,0,0,0);
		private Rect       m_dstRect    = new Rect(0,0,0,0);
		private Typeface   m_font       = Typeface.DEFAULT_BOLD;
		
		private Bitmap     m_bmpLogo    = null;
		private String     m_strProduct = "飞利信集控客户端";
		private String     m_strVesion  = "版本 0.9.3.0";
		private String     m_strWebSite = "http://www.philisense.com";
		private String     m_strIpAddr  = "";
		private String     m_strSSID    = null;
		
		public ViewAbout(Context context, OnClickListener l) 
		{
			super(context);	
			this.setBackgroundColor(Color.BLACK);
			this.setOnClickListener(l);
						
			m_bmpLogo = BitmapFactory.decodeResource(this.getResources(), R.drawable.ic_launcher);
			if(m_bmpLogo != null)
			{
				m_srcRect.set(0, 0, m_bmpLogo.getWidth(), m_bmpLogo.getHeight());
			}	
			m_strIpAddr = String.format("%s%s", Language.Get(Language.LID_STRING_18), HostInfo.getLocalHostIp());
			m_strSSID   = String.format("WIFI :%s", GetWiFiName(context));
		}

		private String GetWiFiName(Context context)
		{
			WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
	        WifiInfo info = wifiManager.getConnectionInfo();
	        
	        return info.getSSID();
		}
		@Override
		protected void onDraw(Canvas canvas) 
		{
			super.onDraw(canvas);
			DrawAll(canvas, this.getWidth(), this.getHeight());
		}
		
		protected void DrawAll(Canvas canvas, int width, int height) 
		{
			if(m_bmpLogo != null)
			{
				int x = width/2;
				int y = height/4;
				m_dstRect.set(x-m_srcRect.width()/2, y-m_srcRect.height(), x+m_srcRect.width()/2, y);
				
				canvas.drawBitmap(m_bmpLogo, m_srcRect, m_dstRect, m_paint);
			}
			
			if(m_font != null) m_paint.setTypeface(m_font);			
			m_paint.setTextAlign(Align.CENTER);
			
			float x = width/2;
			float y = height/2;
			m_paint.setColor(Color.YELLOW);
			m_paint.setTextSize((float)(height * 0.05));
			canvas.drawText(m_strProduct, x, y, m_paint);
			
		
			FontMetrics fm = m_paint.getFontMetrics();
			float fontHeight = (fm.descent + fm.ascent);
			y -= fontHeight;
			
			m_paint.setColor(Color.WHITE);
			m_paint.setTextSize((float)(height * 0.03));
			fm = m_paint.getFontMetrics();
			fontHeight = (fm.descent + fm.ascent);
			y -= fontHeight/2;
			canvas.drawText(m_strVesion, x, y, m_paint);
			
			y -= fontHeight*1.5;
			canvas.drawText(m_strWebSite, x, y, m_paint);	
			y -= fontHeight*1.5;
			canvas.drawText(m_strIpAddr, x, y, m_paint);
			if(m_strSSID != null)
			{
				y -= fontHeight*1.5;
				canvas.drawText(m_strSSID, x, y, m_paint);
			}
		}
	}

	@Override
    public void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(new ViewAbout(this, this));
    }

	@Override
	public void onClick(View v) 
	{
		this.finish();
	}
}
