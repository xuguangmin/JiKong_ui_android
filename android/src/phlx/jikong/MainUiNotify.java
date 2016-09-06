package phlx.jikong;

/* **************************************************************************

			版权所有 (C), 2001-2020, 北京飞利信科技股份有限公司

*****************************************************************************
	文件名称 : MainUiNotify.java
	作者           : 贾延刚
	生成日期 : 2014-7-28
	
	版本           : 1.0
	功能描述 : 主窗口上的一个视图
	                          用来显示提示信息、进度条等
	
	修改历史 :

******************************************************************************/

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Paint.FontMetrics;
import android.view.View;

public class MainUiNotify extends View
{
	private int      m_centerX = 0;
	private int      m_centerY = 0;
	private String   m_strLog      = null;
	private boolean  m_bShowLog    = false;
	private Bitmap   m_bkImage     = null;
	private Rect     m_srcRect     = new Rect(0,0,0,0);
	private Rect     m_dstRect     = new Rect(0,0,0,0);
	
	private int      m_percent     = 0;
	private String   m_percStr     = null;
	private Rect     m_pbRectBase  = new Rect();
	private Rect     m_pbRectPerc  = new Rect();
	private Paint    m_paint       = new Paint();
			
	public MainUiNotify(Context context)
	{
		super(context);
		this.setBackgroundColor(Color.BLACK);
		m_paint.setTextAlign(Paint.Align.CENTER);
	}
	
	protected void onSizeChanged(int w, int h, int oldw, int oldh) 
	{
		super.onSizeChanged(w, h, oldw, oldh);
		//System.out.printf("------------ %s onSizeChanged() w = %d h = %d\n", this.getClass().toString(), w, h);
		
		// LOGO放到中线以上，大小为0.26倍宽高中的小值
		int cX = w/2;
		int cY = h/2;	
		int dim = (int)((w > h ? h:w) * 0.13);
		int offset = (int)(dim * 0.3);
		m_dstRect.set(cX - dim, cY-dim*2-offset, cX + dim, cY-offset);
		
		// 计算进度栏的尺寸和位置
		CalcuProgressSize(w, h);
		
		m_centerX = cX;
		m_centerY = cY;
    }
	
	// 进度栏放到底部
	private void CalcuProgressSize(int w, int h)
	{		
		int width   = (int)(w * 0.95);       // 进度条宽度
		int height  = (int)(h * 0.05);       // 进度条高度
		
		int left    = (int)((w-width)/2);
		int bottom  = h - (int)(height * 0.8) ;
		
		m_pbRectBase.set(left, bottom - height, left + width, bottom);
		m_pbRectPerc.set(m_pbRectBase);
	}
		
	private void DrawLogMessage(Canvas canvas, String strText, int height)
	{	
		m_paint.setColor(Color.WHITE);
		m_paint.setTextSize((float)(height * 0.035));
		FontMetrics fm = m_paint.getFontMetrics();
		float x = m_centerX;
		float y = m_centerY - (fm.descent + fm.ascent);
		canvas.drawText(strText, x, y, m_paint);
	}
		
	private void DrawProgressBar(Canvas canvas, int percent, String strText)
	{	
		if(m_pbRectBase.isEmpty() || m_pbRectPerc.isEmpty())
			return;
		
		m_pbRectPerc.right = m_pbRectBase.left + (m_pbRectBase.width() * percent)/100;
		
		// 背景矩形
		m_paint.setColor(Color.LTGRAY);
		canvas.drawRect(m_pbRectBase, m_paint);
		// 进度矩形
		m_paint.setColor(Color.rgb(253, 206, 90));
		canvas.drawRect(m_pbRectPerc, m_paint);
		
		// text
		if(strText != null) 
		{
			m_paint.setColor(Color.BLACK);
			m_paint.setTextSize((float)(m_pbRectBase.height() * 0.8));
			
			//FontMetrics fm = m_paint.getFontMetrics();
			//float m_txtHeight = fm.descent + fm.ascent;
			float x = m_pbRectBase.left + m_pbRectBase.width()/2;
			float y = m_pbRectBase.bottom;// + (m_pbRectBase.height() - m_txtHeight)/2;  //位置不太正常
			
			canvas.drawText(strText, x, y, m_paint);
		}
	}
	
	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);	
		
		// 背景图
		if(m_bkImage != null) canvas.drawBitmap(m_bkImage, m_srcRect, m_dstRect, null);//, m_centerX- (int)m_srcRect.width()/2, m_centerY- (int)m_srcRect.height()/2, null);//
		else{}
		
		// log text
		if(m_bShowLog) DrawLogMessage(canvas, m_strLog, this.getHeight());	
		// progress		
		if(m_percent > 0) DrawProgressBar(canvas, m_percent, m_percStr);
	}
	
	public void ClearBkImage()
	{
		if(m_bkImage != null)
		{
			if(!m_bkImage.isRecycled()) m_bkImage.recycle();
			m_bkImage = null;
		}
		this.invalidate();
	}
	
	public void ShowLog(String strText)
	{
		m_strLog = strText;
		m_bShowLog = true;
		//this.invalidate();
		this.postInvalidate();
	}
	
	public void SetProgress(int percent, String comment)
	{
		if(percent == m_percent)
			return;
		
		m_percent = percent;
		m_percStr = comment;
		this.invalidate(m_pbRectBase);
	}

	public void SetBkImage(Bitmap bkImage)
	{
		m_bkImage = bkImage;
		if(bkImage != null) m_srcRect.set(0, 0, bkImage.getWidth(), bkImage.getHeight());
		System.out.printf("Default_UI SetBkImage %s\n", (bkImage != null) ? "load":"failed");
		this.invalidate();
	}
}
