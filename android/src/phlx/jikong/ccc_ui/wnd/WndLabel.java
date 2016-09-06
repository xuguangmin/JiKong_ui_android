package phlx.jikong.ccc_ui.wnd;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.view.View;
import android.widget.TextView;


public class WndLabel extends TextView implements WndBase
{
	private Paint    m_paint = new Paint(Paint.ANTI_ALIAS_FLAG|Paint.DITHER_FLAG|Paint.SUBPIXEL_TEXT_FLAG);
	private String   m_caption = null;
	private Rect     m_srcRect = new Rect(0,0,0,0);
	private Rect     m_dstRect = new Rect(0,0,0,0);
	
	private Typeface m_font    = null;
	private int      m_colorForeground = -1;
	private int      m_colorBackground = Color.TRANSPARENT;
	private Bitmap   m_bkImage         = null;
	private int      m_textSize = 1;
	private boolean  m_bBold = false;
	private boolean  m_bUnderLine = false;
	private boolean  m_bStrikeOut = false;
	private boolean  m_bItalic = false;
	private Align    m_textHAlign = Align.CENTER;
	private int      m_textVAlign = WndBase.ALIGN_V_MIDDLE;
	
	public WndLabel(Context context)
	{
		super(context);
	}

	@Override
	protected void onDraw(Canvas canvas)
	{		
		// 背景色
		if(m_colorBackground != Color.TRANSPARENT) 
		{
			m_paint.setColor(m_colorBackground);
			canvas.drawRect(m_dstRect, m_paint);
		}
		// 背景图
		if(m_bkImage != null) canvas.drawBitmap(m_bkImage, m_srcRect, m_dstRect, null);
		// 文本		
		if(m_caption != null)  
		{			
			if(m_font != null) m_paint.setTypeface(m_font);
			if(m_bItalic)      m_paint.setTextSkewX(-0.5f);
			
			m_paint.setTextSize(m_textSize);
			m_paint.setColor(m_colorForeground);
			m_paint.setFakeBoldText(m_bBold);
			m_paint.setUnderlineText(m_bUnderLine);
			m_paint.setStrikeThruText(m_bStrikeOut);
			
			m_paint.setTextAlign(m_textHAlign);
					
			float x = 0;  // left
			if      (Align.CENTER == m_textHAlign) x = this.getWidth()/2;
			else if (Align.RIGHT  == m_textHAlign) x = this.getWidth();
						
			// 纵向坐标
			FontMetrics fm = m_paint.getFontMetrics();
			float y;	
			switch(m_textVAlign)
			{
			case WndBase.ALIGN_V_UP:
				y = Math.abs(fm.ascent);
				break;
			case WndBase.ALIGN_V_DOWN:
				y = this.getHeight()-fm.descent;
				break;
			default:
				y = this.getHeight()/2 - (fm.descent + fm.ascent)/2;
			}			
			canvas.drawText(m_caption, x, y, m_paint);
		}
	}
	
	private void setImageRect(Bitmap imgDraw)
	{
		if(null == imgDraw)
			return;
		
		m_srcRect.set(0, 0, imgDraw.getWidth(), imgDraw.getHeight());
	}
		
	public void SetTextAttrNormal(boolean bBold, boolean bUnderLine, boolean bStrikeOut, boolean bItalic)
	{
		m_bBold      = bBold;
		m_bUnderLine = bUnderLine;
		m_bStrikeOut = bStrikeOut;
		m_bItalic    = bItalic;
	}
	public void SetTextAlignNormal(Align hAlign, int vAlign) 
	{
		m_textHAlign = hAlign;
		m_textVAlign = vAlign;
	}
	public void SetFontNormal(Typeface fontName)
	{
		m_font = fontName;
	}
		
	public void SetStyleNormal(int clForeground, int clBackground, Bitmap bkImage)
	{	
		m_bkImage = bkImage;
		m_colorForeground  = clForeground;
	    m_colorBackground  = clBackground;  

	    //System.out.printf("%s SetStyleNormal clBackground = %X\n", this.getClass().toString(), clForeground);
	    
		if(bkImage != null) setImageRect(bkImage);
	}
	public void SetTextSizeNormal(int size)
	{
		m_textSize = size;	
	}
	
	public void SetDrawText(String text)
	{
		m_caption = text;
	}
	

	/* ******************  接口 WndBase  ****************** */
	@Override
	public void SetVisible(boolean b_visible) 
	{	
		this.setVisibility(b_visible ? View.VISIBLE:View.INVISIBLE);
	}
	@Override
	public void SetEnable(boolean b_enable) 
	{	
		this.setEnabled(b_enable);
	}
	@Override
	public void SetSize(int x, int y, int width, int height)
	{
		m_dstRect.set(0, 0, width, height);
	}
	@Override
	public void Clear()
	{
		m_bkImage = null;
	}
	@Override
	public View GetOwner(){return this;}

	@Override
	public void addCallback(Callback callback, Object uiBase) {
		// TODO Auto-generated method stub
		
	}
}
