package phlx.jikong.ccc_ui.wnd;

import android.view.View;

//该接口做为控件的基类
public interface WndBase 
{
	// 控件类型
	public static final int WND_TYPE_UNKNOWN         = 0;
	public static final int WND_TYPE_BUTTON          = 1;
	public static final int WND_TYPE_IMAGE           = 2;
	public static final int WND_TYPE_LABEL           = 3;
	public static final int WND_TYPE_SLIDER          = 4;
	public static final int WND_TYPE_CHECKBOX        = 5;
	public static final int WND_TYPE_RADIO           = 6;
	public static final int WND_TYPE_PROGRESS        = 7;
	public static final int WND_TYPE_VIDEO           = 8;
	
	// 事件类型
	public static final int WND_EVENT_CLICK          = 1;
	public static final int WND_EVENT_PRESSED        = 2;
	public static final int WND_EVENT_RELEASED       = 3;
	public static final int WND_EVENT_DBLCLICK       = 4;
	public static final int WND_EVENT_VALUE_CHANGE   = 5;      // 整数值变化，会携带一个整数
	public static final int WND_EVENT_CHECK_STATE    = 6;      // checkbox radio选择状态，携带整数 0 1
	
		
	public interface Callback 
	{
		/*
		 *  wndType      控件类型
		 *  event        事件ID
		 *  eventExtra   事件携带的数据。不同的事件，有不同意义的值 
		 *  uiBase       
		 */
		void WndEvent(int wndType, int event, int eventExtra, Object uiBase);
	}
	
	public void addCallback(Callback callback, Object uiBase);
		
	
	
	public static final int ALIGN_V_UP     = 1; // 上
	public static final int ALIGN_V_DOWN   = 2; // 中
	public static final int ALIGN_V_MIDDLE = 3; // 下
	public View GetOwner();
	public void Clear();
	public void SetSize(int x, int y, int width, int height);
	public void SetVisible(boolean b_visible);
	public void SetEnable(boolean b_enable);
}
