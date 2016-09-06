package phlx.jikong.ccc_ui.wnd;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AbsoluteLayout;
import android.view.GestureDetector.OnGestureListener;

@SuppressWarnings("deprecation")
public class WndPage extends AbsoluteLayout 
                     implements View.OnClickListener, OnTouchListener, OnGestureListener
{	
	private boolean          m_showHow         = false;
	private Bitmap           m_bkImage         = null;
	//private Font           m_font = new Font("楷体_GB2312", Font.BOLD, 22);
	private Rect             m_srcRect         = new Rect(0,0,0,0);
	private Rect             m_dstRect         = new Rect(0,0,0,0);
	private Paint            background        = new Paint();
	private GestureDetector  m_gestureDetector = null;
	private Paint            m_msgPaint        = new Paint();
	
	public WndPage(Context context)
	{
		super(context);	
		setWillNotDraw(false);
		setOnClickListener(this);
		setOnTouchListener(this);
		setLongClickable(true);
		
		m_gestureDetector = new GestureDetector(context, this);
		m_msgPaint.setColor(Color.WHITE);
		m_msgPaint.setTextAlign(Paint.Align.CENTER);
	}
		
	/*       
	 * 在onTouch()方法中，我们调用GestureDetector的onTouchEvent()方法，
	 * 将捕捉到的MotionEvent交给GestureDetector        
	 * 来分析是否有合适的callback函数来处理用户的手势       
	 */
	public boolean onTouch(View v, MotionEvent event) 
	{
		return m_gestureDetector.onTouchEvent(event);
	}
	@Override
	public boolean onDown(MotionEvent e) {return false;}
	@Override
	public void onLongPress(MotionEvent e) {}
	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {return false;}
	@Override
	public void onShowPress(MotionEvent e) {}
	@Override
	public boolean onSingleTapUp(MotionEvent e) {return false;}
	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) 
	{		
		boolean b_left = ((e2.getX() - e1.getX()) > 0) ? false:true;
		//System.out.println(String.format("onFling :%s, velocityX = %f, velocityY = %f\n", b_left ? "left":"right", velocityX, velocityY));
		this.onCustomSlide(b_left);
		return false;
	}
	/*
     * 滑动事件
     * @param b_left 左还是右滑
     */
    protected void onCustomSlide(boolean b_left) {}
   	
    @Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
    protected void onCustomClick() {}
    
	private void DrawLogMessage(Canvas canvas, String strText)
	{		
		//FontMetrics fm = m_msgPaint.getFontMetrics();
		//float x = this.getWidth()/2;
		//float y = this.getHeight()/2 - (fm.descent + fm.ascent)/2;
		m_msgPaint.setTextSize(20);
		m_msgPaint.setColor(Color.rgb(255, 0, 0));
		m_msgPaint.setTextAlign(Align.LEFT);
		canvas.drawText(strText, 10, 30, m_msgPaint);
	}
	
	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);	
		//
		if(m_bkImage != null)
		{
			canvas.drawBitmap(m_bkImage, m_srcRect, m_dstRect, background);
		}
		if(m_showHow)
		{
			DrawLogMessage(canvas, "预览模式");
		}
	}
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) 
	{
		super.onSizeChanged(w, h, oldw, oldh);
		m_dstRect.set(0, 0, w, h);
		//System.out.printf("--------------WndPage onSizeChanged w=%d h=%d\n", w, h);
	}
	
	@Override
	public void removeAllViews() 
	{
		SetBkImage(null);
		super.removeAllViews();
	}

	private void setImageRect(Bitmap imgDraw)
	{
		if(null == imgDraw)
			return;
		
		//System.out.printf("WndPage image width= %d, height = %d\n", imgDraw.getWidth(), imgDraw.getHeight());
		m_srcRect.set(0, 0, imgDraw.getWidth(), imgDraw.getHeight());
	}
	
	public void UpdateChildSize(View v, int x, int y, int width, int height)
	{
		AbsoluteLayout.LayoutParams params = new AbsoluteLayout.LayoutParams(width, height, x, y);
		this.updateViewLayout(v, params);
	}
		
	public void SetBkColor(int bkColor)
	{
		this.setBackgroundColor(bkColor);
	}
	
	public void SetShowHowMode(boolean b)
	{
		m_showHow = b;
	}
	
	public void SetBkImage(Bitmap bkImage)
	{
		m_bkImage = bkImage;
		if(m_bkImage != null) setImageRect(m_bkImage);	
		//else System.out.println("WndPage image = null");
	}
	
	public void add(WndBase child)
	{
		this.addView(child.GetOwner());
	}
}
