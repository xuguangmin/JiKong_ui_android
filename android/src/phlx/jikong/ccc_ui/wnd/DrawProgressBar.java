package phlx.jikong.ccc_ui.wnd;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;

public class DrawProgressBar
{
	private Rect        m_rectSlipper     = new Rect();              // 滑块最终的绘制尺寸
	private Rect        m_rectBar         = new Rect(0,0,0,0);       // 滑动条最终的绘制尺寸
	private int         m_colorSlipper    = Color.rgb(205,225,120);  // 无背景图时，默认滑块背景色
	private int         m_colorBar        = Color.rgb(148,126,79);   // 无背景图时，默认滑动条背景色
	
	private Rect        m_srcSlipper      = new Rect(0,0,0,0);
	private Paint       m_paintSlipper    = new Paint();
	private Rect        m_srcBar      = new Rect(0,0,0,0);
	private Paint       m_paintBar    = new Paint();
	
	private Bitmap      m_bkImageSlipper         = null;               // 滑块背景图
	//private int         m_colorBackgroundSlipper = Color.TRANSPARENT;  // 滑块背景色
	private Bitmap      m_bkImageBar             = null;               // 滑动条背景图
	//private int         m_colorForegroundBar     = Color.BLACK;        // 滑动条前景色
	//private int         m_colorBackgroundBar     = Color.TRANSPARENT;  // 滑动条背景色
	private int         m_progress               = 0;                  // 滑块所处百分比
	private boolean     m_b_horizontal           = true;               // 是否横向滑动

	//private int         m_orientation     = 0;        // 滑动方向，0 横向，1 纵向
	
	
	// 画百分比
	private void DrawSlipper(Canvas canvas, Rect rectSlipper)
	{
		if(m_bkImageSlipper != null)
		{
			canvas.drawBitmap(m_bkImageSlipper, m_srcSlipper, rectSlipper, null);
		}
		else
		{
			m_paintSlipper.setColor(m_colorSlipper);
			//g.fillOval(x-width/2, y-height/2, width, height);
			canvas.drawRect(rectSlipper, m_paintSlipper);
		}
	}
	// 画背景
	private void DrawBar(Canvas canvas, Rect rectBar, int progress)
	{
		if(m_bkImageBar != null)
		{
			canvas.drawBitmap(m_bkImageBar, m_srcBar, rectBar, null);
		}
		else
		{
			m_paintBar.setColor(m_colorBar);
			//g.fillOval(x-width/2, y-height/2, width, height);
			canvas.drawRect(rectBar, m_paintBar);
		}
	}
	
	// 进度数字
	private void DrawProgressText(Canvas canvas, Rect rectBar, int progress)
	{
		m_paintBar.setColor(Color.BLACK);
		m_paintBar.setTextAlign(Align.CENTER);
		
		float x = rectBar.width()/2;
					
		// 纵向坐标
		FontMetrics fm = m_paintBar.getFontMetrics();
		float y = rectBar.height()/2 - (fm.descent + fm.ascent)/2;
			
		canvas.drawText(String.format("%d%%", progress), x, y, m_paintBar);
	}
		
	/*
	 *  画百分比
	 */
	private void StartDrawSlipper(Canvas g, int barLeft, int barTop, int barWidth, int barHeight, 
			                                  boolean b_horizontal, int progress)
	{
		if(progress < 0 || progress > 100)
			return;
		
		int x = 0, y = 0, increment = 0;
		if(b_horizontal)             // 横向
		{		
			increment = (barWidth  * progress)/100;
			x = barLeft + increment;
			y = barHeight;
		}
		else
		{
			// 纵向滑动时，滑块的高度不超过滑动条的高度						
			increment = (barHeight * progress)/100;
			x = barWidth;
			y = barTop + barHeight - increment;
		}
		
		m_rectSlipper.set(0, 0, x, y);
		DrawSlipper(g, m_rectSlipper);	
		
	}
	
	public void StartDraw(Canvas g)
	{	
		DrawBar(g, m_rectBar, m_progress);
		
		StartDrawSlipper(g, m_rectBar.left, m_rectBar.top, m_rectBar.width(), m_rectBar.height(), 
				m_b_horizontal, m_progress);
		
		DrawProgressText(g, m_rectBar, m_progress);
		
	}
			
	public void SetSlipperImage(int clBackground, Bitmap bkImage)
	{
		//m_colorBackgroundSlipper  = clBackground;
		m_bkImageSlipper = bkImage;
		if(bkImage != null) m_srcSlipper.set(0, 0, bkImage.getWidth(), bkImage.getHeight());
	}
	
	public void SetBarImage(int clForeground, int clBackground, Bitmap bkImage)
	{
		//m_colorForegroundBar = clForeground;
		//m_colorBackgroundBar = clBackground;
		m_bkImageBar = bkImage;
		if(bkImage != null)  m_srcBar.set(0, 0, bkImage.getWidth(), bkImage.getHeight());
	}
	
	public void SetBarSize(int width, int height)
	{
		m_rectBar.set(0, 0, width, height);
	}
	
	public void SetOrientation(boolean b_horizontal)
	{
		m_b_horizontal = b_horizontal;
	}
	public void SetProgress(int progress)
	{
		if(progress < 0)   progress = 0;
		if(progress > 100) progress = 100;	
		if(m_progress == progress)
			return;
		
		m_progress = progress; 
	}
}