package phlx.jikong.ccc_ui.wnd;

import android.content.Context;
import android.widget.Button;

public class WndButton1 extends Button 
{

	public WndButton1(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	/*

                        implements View.OnTouchListener, View.OnClickListener
{
	private Bitmap  m_imgDraw = null;
	private Bitmap  m_imgNormal = null;
	private Bitmap  m_imgPressed = null;
	private Paint   m_paint = new Paint();
	private String  m_caption = null;
	private Rect    m_srcRect = new Rect(0,0,0,0);
	private Rect    m_dstRect = new Rect(0,0,0,0);
	private IWndEvent  m_eventListener = null;

	public WndButton1(Context context)
	{
		super(context);
		setOnTouchListener(this);
		setOnClickListener(this);
	}

	public void setSize(int x, int y, int width, int height)
	{
		AbsoluteLayout.LayoutParams mlParams = new AbsoluteLayout.LayoutParams(width, height, x, y);// this.getLayoutParams();
		
		m_dstRect.set(0, 0, width, height);
		this.setLayoutParams(mlParams);
	}

	// 单击事件
	public void onClick(View v)
	{
		m_eventListener.UiComponentEvent(UiEventEnum.ButtonControlClick);
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
				m_imgDraw = m_imgPressed;
				setImageRect(m_imgDraw);
				this.invalidate();
				m_eventListener.UiComponentEvent(UiEventEnum.ButtonControlPressed);
			}
			break;
		case MotionEvent.ACTION_UP:
			{
				m_imgDraw = m_imgNormal;
				setImageRect(m_imgDraw);
				this.invalidate();
				m_eventListener.UiComponentEvent(UiEventEnum.ButtonControlReleased);
			}
			break;
		case MotionEvent.ACTION_OUTSIDE:
			{
				
			}
			break;
		}
		return false;
	}
	
	protected void onDraw(Canvas canvas)
	{
		// 背景色
		if(null == m_imgDraw)
		{
			//m_paint.setColor(m_colorBackground);
			canvas.drawRect(m_dstRect, m_paint);
		}
		
		// 背景图
		if(m_imgDraw != null) canvas.drawBitmap(m_imgDraw, m_srcRect, m_dstRect, null);
		
		if(m_caption != null)
		{
			m_paint.setTextSize(m_fontSize);
			// m_paint.setTextScaleX(this.getWidth()/this.getHeight()); 如果有这句，字体会异常大
			m_paint.setTextAlign(Paint.Align.CENTER);
			//int txtWidth = (int)m_paint.measureText(m_caption);
			//int txtHeight = (int)(m_paint.ascent() + m_paint.descent());
			
			//int x = (int)(this.getWidth() - txtWidth)/2;
			//int y = txtHeight + (int)((this.getHeight() - txtHeight)/2 );
			FontMetrics fm = m_paint.getFontMetrics();
			float x = this.getWidth()/2;
			float y = this.getHeight()/2 - (fm.descent + fm.ascent)/2;
			canvas.drawText(m_caption, x, y, m_paint);	
		}
	}
	
	private void setImageRect(Bitmap imgDraw)
	{
		if(null == imgDraw)
			return;
		
		m_srcRect.set(0, 0, imgDraw.getWidth(), imgDraw.getHeight());
	}
	public void setImagePressed(Bitmap imagePressed)
	{
		m_imgPressed = imagePressed;
	}	
	public void setImageNormal(Bitmap imageNormal)
	{
		m_imgNormal = imageNormal;
		m_imgDraw = imageNormal;
		if(imageNormal != null) setImageRect(imageNormal);
	}
	private int m_fontSize = 1;
	public void SetFontSize(int size)
	{
		m_fontSize = size;
		
	}
	public void SetDrawText(String text)
	{
		m_caption = text;
	}

	public void AddEventListener(IWndEvent l)
	{
		m_eventListener = l;
	}*/
}