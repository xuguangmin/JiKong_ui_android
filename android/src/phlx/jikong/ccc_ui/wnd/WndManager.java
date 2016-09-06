package phlx.jikong.ccc_ui.wnd;

import java.util.List;
import java.util.ArrayList;
import android.content.Context;

public class WndManager 
{
	private static Context         m_context = null;
	private static List<WndItem>   m_wndItemList = new ArrayList<WndItem>();
		
	private static boolean AddWndItem(int vtype, WndBase wnd, boolean b_using, Object owner)
	{
		WndItem wi = new WndItem();
		wi.wnd     = wnd;
		wi.vtype   = vtype;
		wi.b_using = b_using;
		
		wi.owner   = owner;
		return m_wndItemList.add(wi);
	}
	
	private static WndBase GetViewFromList(int wndType, Object owner)
	{
		for(int k = 0; k < m_wndItemList.size(); ++k)
		{
			WndItem wi = m_wndItemList.get(k);
			if(wi.vtype == wndType && !wi.b_using)
			{
				wi.b_using = true;
				wi.owner   = owner;
				return wi.wnd;
			}
		}
		return null;	
	}
	
	private static WndBase CreateNewView(int wndType)
	{		
		if(null == m_context)
			return null;
		
		switch(wndType)
		{
		case WndBase.WND_TYPE_BUTTON    :return (new WndButton(m_context));
		case WndBase.WND_TYPE_IMAGE     :return (new WndImage(m_context));			
		case WndBase.WND_TYPE_LABEL     :return (new WndLabel(m_context));
		case WndBase.WND_TYPE_SLIDER    :return (new WndSlider(m_context));
		case WndBase.WND_TYPE_CHECKBOX  :return (new WndCheckBox(m_context));
		case WndBase.WND_TYPE_RADIO     :return (new WndRadio(m_context));
		case WndBase.WND_TYPE_PROGRESS  :return (new WndProgress(m_context));
		case WndBase.WND_TYPE_VIDEO     :return (new WndVideo(m_context));
		}
		return null;
	}
	
	public static void ClearIdleWnd()
	{
		for(int k = (m_wndItemList.size()-1); k >=0 ; --k)
		{
			WndItem wi = m_wndItemList.get(k);
			if(!wi.b_using) 
			{
				//System.out.printf("ClearIdleWnd wnd =%d\n", k);
				
				m_wndItemList.remove(k);
			}
		}
	}
	
	// 恢复缓存中的控件状态为未使用
	public static void ResetAllWnd()
	{
		for(int k = 0; k < m_wndItemList.size(); ++k)
		{
			WndItem wi = m_wndItemList.get(k);
			wi.wnd.Clear();
			wi.owner = null;
			wi.b_using = false;
		}
	}
		
	public static WndBase GetView(Object owner)
	{
		for(int k = 0; k < m_wndItemList.size(); ++k)
		{
			WndItem wi = m_wndItemList.get(k);
			if(wi.b_using && owner == wi.owner)
			{
				return wi.wnd;
			}
		}
		return null;	
		
	}
	public static WndBase CreateView(int wndType, Object owner)
	{
		WndBase v = GetViewFromList(wndType, owner);
		if(v != null)
			return v;
		
		v = CreateNewView(wndType);
		if(null == v)
			return null;
		
		//System.out.printf("jia --- %d WndManager CreateNewView\n", ++m_count);
		
		AddWndItem(wndType, v, true, owner);
		return v;
	}
	
	public static void Clear()
	{
		m_wndItemList.clear();
	}
	public static void Initial(Context context)
	{
		m_context = context;
	}

}
