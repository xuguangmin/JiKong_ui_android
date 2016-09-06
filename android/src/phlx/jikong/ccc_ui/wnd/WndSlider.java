package phlx.jikong.ccc_ui.wnd;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;


public class WndSlider extends View
                       implements WndBase, View.OnTouchListener, View.OnClickListener  // 
{	
	private Callback   m_callback = null;
	private Object     m_uiBase = null;
	
	private boolean     m_b_enable = true;
	private boolean     m_b_horizontal = true;
	
	private boolean     m_bMousePressed = true;
	private Point       m_ptOld         = new Point();
	private int         m_slide_length  = 0;        // 当前滑块已滑动的长度
	private int         m_bar_length    = 0;        // 当前滑动条的长度（除去滑块）
	private DrawSlider  m_drawSlider    = new DrawSlider();
	
	private int         m_old_progress  = -1;
		
	
	
	public WndSlider(Context context)
	{
		super(context);
		setOnTouchListener(this);
		setOnClickListener(this);
	}

	// 单击事件
	public void onClick(View v)
	{
		//m_callback.WndEvent(WND_TYPE_SLIDER, WND_EVENT_CLICK, 0, m_uiBase);
	}
		
	public boolean onTouch(View v, MotionEvent event) 
	{
		switch(event.getAction())
		{
		case MotionEvent.ACTION_MOVE:			
			{
				OnMouseDragged((int)event.getX(), (int)event.getY());
			}
			break;
		case MotionEvent.ACTION_DOWN:
			{
				if(m_drawSlider.PointInSlipperRect((int)event.getX(), (int)event.getY()))
				{
					//System.out.println("------------------------ onTouch ACTION_DOWN");
										
					m_bMousePressed = true;
					m_slide_length = m_drawSlider.GetSlideLength();
					m_bar_length   = m_drawSlider.GetBarLength();
					
					if(m_b_horizontal) m_ptOld.x = (int)event.getX();
					else               m_ptOld.y = (int)event.getY();
				}	
			}
			break;
		case MotionEvent.ACTION_UP:
			{
				if(m_bMousePressed) m_bMousePressed = false;
			}
			break;
		case MotionEvent.ACTION_OUTSIDE:
			{}
			break;
		}
		return false;	
	}
	
	private void OnMouseDragged(int X, int Y)
	{
		if(!m_b_enable)
			return;
		if(!m_bMousePressed) 
			return;
		
		int progress = 0;
		if(m_b_horizontal)
		{
			int walking = X - m_ptOld.x;
			progress = (int)(100 * ((double)(m_slide_length + walking)/m_bar_length));	
		}
		else
		{
			int walking = Y - m_ptOld.y;	
			progress = (int)(100 * ((double)(m_slide_length + walking)/m_bar_length));
			progress = 100 - progress;
		}	
		m_drawSlider.SetProgress(progress);
		this.invalidate();
		
		NotifyPercent(progress);
	}
	
	protected void onDraw(Canvas canvas)
	{
		m_drawSlider.StartDraw(canvas);
	}
	
	
	
	private void NotifyPercent(int progress)
	{
		if(!m_b_enable)
			return;
		if(progress < 0 || progress > 100)
			return;
		if(null == m_callback || progress == m_old_progress) 
			return;
		
		m_callback.WndEvent(WND_TYPE_SLIDER, WND_EVENT_VALUE_CHANGE, progress, m_uiBase);
		m_old_progress = progress;
	}
	
	
		
	public void SetBarSize(int width, int height)
	{
		m_drawSlider.SetBarSize(width, height);
	}
	public void SetSlipperSize(int width, int height)
	{
		m_drawSlider.SetSlipperSize(width, height);
	}
	
	public void SetStyleSlipper(int clBackground, Bitmap bkImage)
	{
	    m_drawSlider.SetSlipperImage(clBackground, bkImage);
	}
	public void SetStyleBar(int clBackground, Bitmap bkImage)
	{
	    m_drawSlider.SetBarImage(clBackground, bkImage);
	}
	
	public void SetProgressShow(boolean b_show)
	{
		m_drawSlider.SetProgressShow(b_show);
	}
	public void SetOrientation(boolean b_horizontal)
	{
		m_drawSlider.SetOrientation(b_horizontal);
		m_b_horizontal = b_horizontal;
	}
	public void SetProgress(int progress)
	{
		m_drawSlider.SetProgress(progress);
		this.invalidate();
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
		super.setEnabled(b_enable);
		m_b_enable = b_enable;		
	}	
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) 
	{
		super.onSizeChanged(w, h, oldw, oldh);
		//System.out.printf("------------ %s onSizeChanged() w = %d h = %d\n", this.getClass().toString(), w, h);
		this.m_drawSlider.SetEdgeSize(w, h);
	}
	
	@Override
	public void SetSize(int x, int y, int width, int height)
	{
	}
	@Override
	public void Clear()
	{
		m_drawSlider.Clear();
	}
	public View GetOwner() {return this;}

	@Override
	public void addCallback(Callback callback, Object uiBase) {
		m_callback = callback;
		m_uiBase = uiBase;
	}
}
