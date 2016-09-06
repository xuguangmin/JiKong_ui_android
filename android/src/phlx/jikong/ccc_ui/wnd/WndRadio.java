package phlx.jikong.ccc_ui.wnd;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;

public class WndRadio extends RadioButton implements WndBase
{
	private Callback   m_callback = null;
	private Object     m_uiBase = null;
	
	public WndRadio(Context context) 
	{
		super(context);
		this.setOnCheckedChangeListener(new OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) 
			{
				m_callback.WndEvent(WND_TYPE_RADIO, WND_EVENT_CHECK_STATE, isChecked ? 1:0, m_uiBase);
			}
		});
	}

	public void SetDrawText(String text)
	{
		this.setText(text);
	}
		
	public void SetStyleNormal(int clForeground, int clBackground)
	{
		//m_colorForeground  = clForeground;
	    //m_colorBackground  = clBackground;
	    this.setTextColor(clForeground);
	    this.setBackgroundColor(clBackground);
	}
	public void SetTextSizeNormal(int size)
	{
		this.setTextSize(size);
		
	}
	public void SetFontNormal(Typeface fontName)
	{
		this.setTypeface(fontName);
	}
	//TODO: bItalic 属性未实现
	public void SetTextAttrNormal(boolean bBold, boolean bUnderLine, boolean bStrikeOut, boolean bItalic)
	{
		int flags = 0;
		if(bBold)      flags |= Paint.FAKE_BOLD_TEXT_FLAG;
		if(bUnderLine) flags |= Paint.UNDERLINE_TEXT_FLAG;
		if(bStrikeOut) flags |= Paint.STRIKE_THRU_TEXT_FLAG;		
		this.setPaintFlags(flags);
	}
	

	/* ******************  接口 WndBase  ****************** */
	@Override
	public void Clear() 
	{}
	@Override
	public void SetSize(int x, int y, int width, int height)
	{}
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
		m_callback = callback;
		m_uiBase = uiBase;
	}
}
