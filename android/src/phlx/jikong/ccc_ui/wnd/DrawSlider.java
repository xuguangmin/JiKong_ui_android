package phlx.jikong.ccc_ui.wnd;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;

public class DrawSlider 
{
	private Rect        m_rectSlipper     = new Rect();              // 滑块最终的绘制尺寸
	private Rect        m_rectBar         = new Rect(0,0,0,0);       // 滑动条最终的绘制尺寸
	private int         m_colorSlipper    = Color.rgb(205,225,120);  // 无背景图时，默认滑块背景色
	private int         m_colorBar        = Color.rgb(148,126,79);   // 无背景图时，默认滑动条背景色
	
	private Rect        m_srcSlipper      = new Rect(0,0,0,0);
	private Paint       m_paintSlipper    = new Paint();
	private Rect        m_srcBar      = new Rect(0,0,0,0);
	private Paint       m_paintBar    = new Paint();
	
	private int         m_edgeWidth              = 0;        // 边界宽
	private int         m_edgeHeight             = 0;        // 边界高
	private int         m_barWidth               = 0;        // 滑动条宽
	private int         m_barHeight              = 0;        // 滑动条高
	private int         m_slipperWidth           = 0;        // 滑块宽
	private int         m_slipperHeight          = 0;        // 滑块高
	
	
	private Bitmap      m_bkImageSlipper         = null;               // 滑块背景图
	private int         m_colorBackgroundSlipper = Color.TRANSPARENT;  // 滑块背景色
	private Bitmap      m_bkImageBar             = null;               // 滑动条背景图
	private int         m_colorBackgroundBar     = Color.TRANSPARENT;  // 滑动条背景色
	private int         m_progress               = 0;                  // 滑块所处百分比
	private boolean     m_b_horizontal           = true;               // 是否横向滑动
	private boolean     m_b_prog_show            = true;               // 是否画进度数字
	
	
	// 在滑块上画进度数字
	private void DrawProgressText(Canvas canvas, Rect rectSlipper, int progress, boolean b_horizontal)
	{			
		m_paintSlipper.setColor(Color.BLACK);
	    m_paintSlipper.setTextAlign(Align.CENTER);			
		float x = rectSlipper.centerX();
		FontMetrics fm = m_paintSlipper.getFontMetrics();
		float y = rectSlipper.height()/2 - (fm.descent + fm.ascent)/2;			
		canvas.drawText(String.format("%d", progress), x,  rectSlipper.top + y, m_paintSlipper);
    }
		
	// 画滑块
	private void DrawSlipper(Canvas canvas, Rect rectSlipper)
	{
		if(m_bkImageSlipper != null)
		{
			canvas.drawBitmap(m_bkImageSlipper, m_srcSlipper, rectSlipper, null);
		}
		else
		{
			if(Color.TRANSPARENT == m_colorBackgroundSlipper) m_paintSlipper.setColor(m_colorSlipper);
			else{
				m_paintSlipper.setColor(m_colorBackgroundSlipper);	
			}
			canvas.drawRect(rectSlipper, m_paintSlipper);
		}
	}
	// 画滑动条
	private void DrawBar(Canvas canvas, Rect rectBar)
	{
		if(m_bkImageBar != null)
		{
			canvas.drawBitmap(m_bkImageBar, m_srcBar, rectBar, null);
		}
		else
		{
			if(Color.TRANSPARENT == m_colorBackgroundBar) m_paintBar.setColor(m_colorBar);
			else{
				m_paintBar.setColor(m_colorBackgroundBar);	
			}
			
			canvas.drawRect(rectBar, m_paintBar);
		}
	}
	/*
	 *  计算滑动条的位置
	 *  滑动条上下左右居中
	 */
	private int CalcuBarLocation(int edgeWidth, int barWidth)
	{
		return (int)((edgeWidth - barWidth)/2);
	}
	
	/*
	 *  画滑动条
	 *  滑动条在控件中上下左右居中
	 */
	private boolean StartDrawBar(Canvas g, int edgeWidth, int edgeHeight, 
			                              int barWidth, int barHeight)
	{	
		if(edgeWidth <= 0 || edgeHeight <= 0 || barWidth <= 0 || barHeight <= 0)
			return false;
		
		// 检查滑动条尺寸，不能超过控件边界
		if(barWidth > edgeWidth)   barWidth = edgeWidth;
		if(barHeight > edgeHeight) barHeight = edgeHeight;
				
		int left = CalcuBarLocation(edgeWidth, barWidth);
		int top  = CalcuBarLocation(edgeHeight, barHeight);
		
		m_rectBar.set(left, top, left+barWidth, top+barHeight);
		DrawBar(g, m_rectBar);
		
		return true;
	}
	
	/*
	 *  画滑块
	 *  滑块
	 */
	private void StartDrawSlipper(Canvas g, int barLeft, int barTop, int barWidth, int barHeight, 
			                                  int slipperWidth, int slipperHeight, 
			                                  boolean b_horizontal, int progress)
	{
		if(slipperWidth <= 0 || slipperHeight <= 0 || progress < 0 || progress > 100)
			return;
		
		int x = 0, y = 0, increment = 0;
		if(b_horizontal)             // 横向
		{
			// 水平滑动时，滑块的宽度不超过滑动条的宽度
			if(slipperWidth > barWidth) slipperWidth = barWidth;         
			
			increment = ((barWidth - slipperWidth) * progress)/100;
			x = barLeft + increment;
			y = barTop + barHeight/2 - slipperHeight/2;
			
		}
		else
		{
			// 纵向滑动时，滑块的高度不超过滑动条的高度
			if(slipperHeight > barHeight) slipperHeight = barHeight;   
						
			increment = ((barHeight - slipperHeight) * progress)/100;
			x = barLeft + barWidth/2 - slipperWidth/2;
			y = barTop + barHeight - slipperHeight - increment;
		}
		
		m_rectSlipper.set(x, y, x+slipperWidth, y+slipperHeight);
		DrawSlipper(g, m_rectSlipper);
		if(m_b_prog_show) DrawProgressText(g, m_rectSlipper, m_progress, b_horizontal);
		
	}
	
	public void StartDraw(Canvas g)
	{		
		if(!StartDrawBar(g, m_edgeWidth, m_edgeHeight, m_barWidth, m_barHeight))
			return;
		
		// 检查滑块尺寸
		if(m_slipperWidth > m_edgeWidth)   m_slipperWidth = m_edgeWidth;
		if(m_slipperHeight > m_edgeHeight) m_slipperHeight = m_edgeHeight;
		StartDrawSlipper(g, m_rectBar.left, m_rectBar.top, m_rectBar.width(), m_rectBar.height(), 
				m_slipperWidth, m_slipperHeight, m_b_horizontal, m_progress);
		
	}
		
	public boolean PointInSlipperRect(int x, int y)
	{
		return m_rectSlipper.contains(x, y);
	}
	// 除去滑块尺寸后的剩余部分
	public int GetBarLength()  
	{
		if(m_b_horizontal)
			return m_rectBar.width() - m_rectSlipper.width();
		
		return m_rectBar.height() - m_rectSlipper.height();
	}
	// 滑块已滑动的长度
	public int GetSlideLength()  
	{
		if(m_b_horizontal)
			return m_rectSlipper.left - m_rectBar.left;
		
		return m_rectSlipper.top - m_rectBar.top;
	}
	public void Clear()
	{
		m_bkImageSlipper = null;
		m_bkImageBar = null;
		m_progress = 0;
	}
	public void SetSlipperImage(int clBackground, Bitmap bkImage)
	{
		m_colorBackgroundSlipper  = clBackground;
		m_bkImageSlipper = bkImage;
		if(bkImage != null) m_srcSlipper.set(0, 0, bkImage.getWidth(), bkImage.getHeight());
	}
	
	public void SetBarImage(int clBackground, Bitmap bkImage)
	{
		m_colorBackgroundBar = clBackground;
		m_bkImageBar = bkImage;
		if(bkImage != null)  m_srcBar.set(0, 0, bkImage.getWidth(), bkImage.getHeight());
	}
	public void SetEdgeSize(int width, int height)
	{
		m_edgeWidth  = width;
		m_edgeHeight = height;
	}
	public void SetBarSize(int width, int height)
	{
		m_barWidth  = width;
		m_barHeight = height;
	}
	public void SetSlipperSize(int width, int height)
	{
		m_slipperWidth  = width;
		m_slipperHeight = height;
	}
	public void SetProgressShow(boolean b_show)
	{
		m_b_prog_show = b_show;
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
		//System.out.printf("m_progress = %d\n", m_progress);
	}
}