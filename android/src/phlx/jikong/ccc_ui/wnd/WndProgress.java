package phlx.jikong.ccc_ui.wnd;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;


public class WndProgress extends View implements WndBase
{
	private DrawProgressBar  m_drawProgress    = new DrawProgressBar();
	
	public WndProgress(Context context) 
	{
		super(context);
	}

	protected void onDraw(Canvas canvas)
	{
		m_drawProgress.StartDraw(canvas);
	}
	
	public void SetOrientation(boolean b_horizontal)
	{
		m_drawProgress.SetOrientation(b_horizontal);
	}
	public void SetProgress(int progress)
	{
		m_drawProgress.SetProgress(progress);
		this.invalidate();
	}

	@Override
	public void Clear() 
	{
	}

	@Override
	public void SetSize(int x, int y, int width, int height) 
	{		
		m_drawProgress.SetBarSize(width, height);
	}

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
	public View GetOwner(){return this;}

	@Override
	public void addCallback(Callback callback, Object uiBase) {
		// TODO Auto-generated method stub
		
	}
}
