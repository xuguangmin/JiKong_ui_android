package phlx.jikong.ccc_ui.wnd;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import phlx.jikong.ccc_ui.ui.UiStyle;

public class WndImage extends ImageView  
                      implements WndBase, View.OnTouchListener, View.OnClickListener
{
	private Callback   m_callback = null;
	private Object     m_uiBase = null;
		
	private Bitmap     m_bkImageNormal = null;
	private int        m_bkImageStyleNormal       = UiStyle.IMAGE_STYLE_STRETCH;
	private int        m_colorForegroundNormal    = Color.BLACK;
	private int        m_colorBackgroundNormal    = Color.TRANSPARENT;
	
	private Bitmap     m_bkImagePressed = null;
	private int        m_bkImageStylePressed      = UiStyle.IMAGE_STYLE_STRETCH;
	private int        m_colorForegroundPressed   = Color.BLACK;
	private int        m_colorBackgroundPressed   = Color.TRANSPARENT;
	
	//private int        m_colorForegroundFocused   = Color.BLACK;
	//private int        m_colorBackgroundFocused   = Color.TRANSPARENT;
	
	private int        m_textSizeNormal = 1;
	private Typeface   m_fontNormal  = null;
	private boolean    m_bBoldNormal = false;
	private boolean    m_bUnderLineNormal = false;
	private boolean    m_bStrikeOutNormal = false;
	private boolean    m_bItalicNormal = false;
	private Align      m_textHAlignNormal  = Align.CENTER;
	private int        m_textVAlignNormal  = WndBase.ALIGN_V_MIDDLE;
	
	private int        m_textSizePressed = 1;
	private Typeface   m_fontPressed = null;
	private boolean    m_bBoldPressed = false;
	private boolean    m_bUnderLinePressed = false;
	private boolean    m_bStrikeOutPressed = false;
	private boolean    m_bItalicPressed = false;
	private Align      m_textHAlignPressed = Align.CENTER;
	private int        m_textVAlignPressed = WndBase.ALIGN_V_MIDDLE;
	
	//private Font      m_fontFocused = null;
	
	
	private Paint      m_paint = new Paint(Paint.ANTI_ALIAS_FLAG|Paint.DITHER_FLAG);
	private Rect       m_srcRect = new Rect(0,0,0,0);
	private Rect       m_dstRect = new Rect(0,0,0,0);
	private Rect       m_bmpRect = new Rect(0,0,0,0);
	
	
	private String     m_caption = null;
	private Bitmap     m_bkImage = null;
	private int        m_bkImageStyle  = UiStyle.IMAGE_STYLE_STRETCH;
	private Typeface   m_font    = null;
	private int        m_colorForeground = Color.BLACK;
	private int        m_colorBackground = Color.TRANSPARENT;
	private int        m_textSize = 1;
	private boolean    m_bBold = false;
	private boolean    m_bUnderLine = false;
	private boolean    m_bStrikeOut = false;
	private boolean    m_bItalic = false;
	private Align      m_textHAlign = Align.CENTER;
	private int        m_textVAlign = WndBase.ALIGN_V_MIDDLE;

	public WndImage(Context context)
	{
		super(context);
		setOnTouchListener(this);
		setOnClickListener(this);
	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) 
	{
		super.onSizeChanged(w, h, oldw, oldh);
		//System.out.printf("------------ %s onSizeChanged() w = %d h = %d\n", this.getClass().toString(), w, h);
	}

	// 单击事件
	public void onClick(View v)
	{
		m_callback.WndEvent(WND_TYPE_IMAGE, WND_EVENT_CLICK, 0, m_uiBase);
	}
		
	public boolean onTouch(View v, MotionEvent event) 
	{
		switch(event.getAction())
		{
		case MotionEvent.ACTION_MOVE:			
			{
			}
			break;
		case MotionEvent.ACTION_DOWN:
			{
				m_bkImage = m_bkImagePressed;
				setImageRect(m_bkImage);
				
				m_bkImageStyle    = m_bkImageStylePressed;
				m_colorForeground = m_colorForegroundPressed;
		    	m_colorBackground = m_colorBackgroundPressed;
		    	
		    	m_font       = m_fontPressed;
		    	m_textSize   = m_textSizePressed;
				m_textHAlign = m_textHAlignPressed;
		    	m_textVAlign = m_textVAlignPressed;
		    	m_bBold      = m_bBoldPressed;
				m_bUnderLine = m_bUnderLinePressed;
				m_bStrikeOut = m_bStrikeOutPressed;
				m_bItalic    = m_bItalicPressed;
										    	
				this.invalidate();
				m_callback.WndEvent(WND_TYPE_IMAGE, WND_EVENT_PRESSED, 0, m_uiBase);
			}
			break;
		case MotionEvent.ACTION_UP:
			{
				m_bkImage = m_bkImageNormal;
				setImageRect(m_bkImage);
				m_bkImageStyle    = m_bkImageStyleNormal;
				m_colorForeground = m_colorForegroundNormal;
		    	m_colorBackground = m_colorBackgroundNormal;
		    	
		    	m_font       = m_fontNormal;
		    	m_textSize   = m_textSizeNormal;
		    	m_textHAlign = m_textHAlignNormal;
		    	m_textVAlign = m_textVAlignNormal;				
				m_bBold      = m_bBoldNormal;
				m_bUnderLine = m_bUnderLineNormal;
				m_bStrikeOut = m_bStrikeOutNormal;
				m_bItalic    = m_bItalicNormal;
		    	
				this.invalidate();
				m_callback.WndEvent(WND_TYPE_IMAGE, WND_EVENT_RELEASED, 0, m_uiBase);
			}
			break;
		case MotionEvent.ACTION_OUTSIDE:
			{}
			break;
		}
		return false;
	}
	
	protected void onDraw(Canvas canvas)
	{	
		// 背景色
		if(m_colorBackground != Color.TRANSPARENT) // (null == m_bkImage) if(m_colorBackground != Color.TRANSPARENT) 
		{
			m_paint.setColor(m_colorBackground);
			canvas.drawRect(m_dstRect, m_paint);
		}
		// 背景图
		if(m_bkImage != null) 
		{
			switch(m_bkImageStyle)
			{
			case UiStyle.IMAGE_STYLE_SUOFANG:
				canvas.drawBitmap(m_bkImage, m_srcRect, m_dstRect, null);
				break;
			default:
				canvas.drawBitmap(m_bkImage, m_srcRect, m_dstRect, null);
			}
		}
		
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
	
	private void setImageRect(Bitmap bmp)
	{
		if(null == bmp)
			return;
		
		m_srcRect.set(0, 0, bmp.getWidth(), bmp.getHeight());
		//double scale = bmp.getWidth()/bmp.getHeight();
		
	}
	
	public void SetTextAttrPressed(boolean bBold, boolean bUnderLine, boolean bStrikeOut, boolean bItalic)
	{
		m_bBoldPressed      = bBold;
		m_bUnderLinePressed = bUnderLine;
		m_bStrikeOutPressed = bStrikeOut;
		m_bItalicPressed    = bItalic;
	}
	public void SetTextAttrNormal(boolean bBold, boolean bUnderLine, boolean bStrikeOut, boolean bItalic)
	{
		m_bBoldNormal      = bBold;
		m_bUnderLineNormal = bUnderLine;
		m_bStrikeOutNormal = bStrikeOut;
		m_bItalicNormal    = bItalic;
		
		m_bBold      = bBold;
		m_bUnderLine = bUnderLine;
		m_bStrikeOut = bStrikeOut;
		m_bItalic    = bItalic;
	}
		
	public void SetTextAlignPressed(Align hAlign, int vAlign) 
	{		
		m_textHAlignPressed = hAlign;
		m_textVAlignPressed = vAlign;
	}
	public void SetTextAlignNormal(Align hAlign, int vAlign) 
	{
		m_textHAlignNormal = hAlign;
		m_textVAlignNormal = vAlign;
		m_textHAlign = hAlign;
		m_textVAlign = vAlign;
	}
	
	// 字体名	
	public void SetFontPressed(Typeface fontName)
	{
		m_fontPressed = fontName;	    
	}
	public void SetFontNormal(Typeface fontName)
	{
		m_fontNormal = fontName;
		m_font = m_fontNormal;
	}
		
	// 前、背景色，背景图
	public void SetStylePressed(int clForeground, int clBackground, Bitmap bkImage, int imageStyle)
	{
		m_bkImagePressed = bkImage;
		m_bkImageStylePressed    = imageStyle;
		m_colorForegroundPressed = clForeground;	
		m_colorBackgroundPressed = clBackground;			
	}
	public void SetStyleNormal(int clForeground, int clBackground, Bitmap bkImage, int imageStyle)
	{
		m_bkImageNormal = bkImage;
		m_bkImageStyleNormal    = imageStyle;
		m_colorForegroundNormal = clForeground;
		m_colorBackgroundNormal = clBackground;
		
		if(bkImage != null) setImageRect(bkImage);
				
		m_bkImage = bkImage;
		m_bkImageStyle     = imageStyle;
		m_colorForeground  = clForeground;
	    m_colorBackground  = clBackground;  
	}
	public void SetTextSizePressed(int size)
	{
		m_textSizePressed = size;
	}
	public void SetTextSizeNormal(int size)
	{
		m_textSizeNormal = size;
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
		m_bmpRect.set(0, 0, width, height);
	}
	@Override
	public void Clear()
	{
		m_bkImage = null;
		m_bkImageNormal = null;
		m_bkImagePressed = null;
	}
	@Override
	public View GetOwner(){return this;}

	@Override
	public void addCallback(Callback callback, Object uiBase) {
		m_callback = callback;
		m_uiBase = uiBase;
	}
}
