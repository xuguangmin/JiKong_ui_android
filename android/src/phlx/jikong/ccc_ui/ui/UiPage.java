package phlx.jikong.ccc_ui.ui;

import java.util.List;
import java.util.ArrayList;

public class UiPage
{	
	public boolean bPopup  = false;
	public int     id;                       // 页编号
	public String  title    = null;
	public int     bkColor;                  // 背景颜色
	public int     fwColor;                  // 前景颜色
	public String  bkImage  = null;
	public int     imageMode;                // 拉伸、平铺、同比例缩放
	public int     jumpPage = -1;            // 单击时的弹出页
	public int     leftJumpPage = -1;        // 左滑动时的弹出页
	public int     rightJumpPage = -1;       // 右滑动时的弹出页
	
	public int     width  = 0;
	public int     height = 0;
	
	
	public interface Callback 
	{
		/*
		 *  本页的属性    
		 */
		void UiPageAttr(int id, String bkImage, int bkColor, int width, int height, boolean bPopup);
		
		/*
		 *  枚举本页上的所有子元素       
		 */
		void EnumerateUiBase(UiBase uiBase);
		
		/*
		 * 更新指定子元素的指定属性
		 */
		boolean UpdateUiBaseProperty(int property, UiBase uiBase);
	}
	
	private List<UiBase>  m_childUiList;
	private Callback      m_callback = null;
		  
	public UiPage()
	{
		id      = -1;
		title   = "";
		bkImage = "";		
		m_childUiList = new ArrayList<UiBase>();
	}
		
	public boolean AddXmlNone(UiBase uib)
	{
		return m_childUiList.add(uib);
	}
	// 根据z-index进行从小到大的排序
	public void Reorder()
	{
		UiBase uib, uib2, temp;
		int total = m_childUiList.size();
		for(int k = 0; k < total; ++k)
		{
			uib = m_childUiList.get(k);
			for(int s = k+1; s < total; ++s)
			{
				uib2 = m_childUiList.get(s);
				if(uib.z_index > uib2.z_index)
				{
					temp = uib;
					uib = uib2;
					m_childUiList.set(s, temp);	
				}
			}
			m_childUiList.set(k, uib);	
		}
	}
	
	// 得到页的标题
	public String GetTitle()
	{
		return title;
	}
	
	public boolean SetVisible(int ctrlId, boolean b_visible)
	{		
		int value = b_visible ? UiPropertyEnum.VISIBLE_TRUE : UiPropertyEnum.VISIBLE_FALSE;
		return SetControlProperty(ctrlId, UiPropertyEnum.VISIBLE, value);
	}
	public boolean SetEnable(int ctrlId, boolean b_enable)
	{
		int value = b_enable ? UiPropertyEnum.ENABLE_TRUE : UiPropertyEnum.ENABLE_FALSE;
		return SetControlProperty(ctrlId, UiPropertyEnum.ENABLE, value);
	}
	
	public boolean SetControlProperty(int ctrlId, int property, int value)
	{
		UiBase uib = null;
		for(int k = 0; k < m_childUiList.size(); ++k)
		{
			uib = m_childUiList.get(k);
			if(uib.id == ctrlId) 
			{
				uib.SetProperty(property, value);
				if(m_callback != null) m_callback.UpdateUiBaseProperty(property, uib);
				return true;
			}
		}
		return false;
		
	}
	
	public boolean Show(Callback callback)
	{						
		m_callback = callback; 
		
		this.m_callback.UiPageAttr(this.id, bkImage, bkColor, this.width, this.height, this.bPopup);
	
		// 枚举页的元素
		for(int k = 0; k < m_childUiList.size(); ++k)
		{
			UiBase uib    = m_childUiList.get(k);
			this.m_callback.EnumerateUiBase(uib);
						
			///System.out.printf("CreatePageChilds k = %d size =%d\n", k, m_childUiList.size());
		}		
		return true;
	}
}