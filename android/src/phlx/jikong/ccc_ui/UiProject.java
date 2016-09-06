package phlx.jikong.ccc_ui;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import phlx.jikong.ccc_ui.ui.UiPage;
import phlx.jikong.ccc_ui.ui.UiRoot;
import phlx.jikong.ccc_ui.xml.XmlParser;

public class UiProject implements XmlParser.IXmlParser
{
	private UiPage            m_uiPage     = null;
	private UiRoot            m_uiRoot     = null;
	private List<UiPage>      m_uiPageList = null;
	
	public UiProject()
	{
		m_uiPageList = new ArrayList<UiPage>();
	}
	
	/* ******************  接口 IXmlParser  ****************** */
	@Override
	public void ParseResult(int type, int idPage, Object xmlCtrl)
	{
		switch(type)
    	{
    	case XmlParser.XML_ROOT:
	    	{
	    		UiRoot uiRoot = (UiRoot)xmlCtrl;
	    		m_uiRoot = uiRoot;
	    		
	    		//System.out.println("\n-----------  XML  -----------");
	    		//System.out.printf("XML root : %d %d, %d\n", uiRoot.width, uiRoot.height, uiRoot.startPage);
	    	}
    		break;
    	case XmlParser.XML_PAGE:
    	case XmlParser.XML_POPUP_PAGE:
	    	{
	    		UiPage uiPage = (UiPage)xmlCtrl;
	    		uiPage.width  = m_uiRoot.width;
	    		uiPage.height = m_uiRoot.height;
	    		m_uiPageList.add(uiPage);
	    		
	    		//System.out.println("");
	    		//System.out.printf("Page : %d %s, %s, %s\n", uiPage.id, uiPage.title, uiPage.bkImage, uiPage.bkColor);	
	    	}
	    	break;
    	}
	}
	
	private void ReorderUi()
	{
		for(int k = 0; k < m_uiPageList.size(); ++k)
		{
			UiPage uip = m_uiPageList.get(k);
			uip.Reorder();
		}
	}
	private void PrintLog(String strLog)
	{
		System.out.printf("%s :%s\n", this.getClass().toString(), strLog);	
	}
	
	public void SetControlVisible(int ctrlId, boolean b_visible)
	{
		for(int k = 0; k < m_uiPageList.size(); ++k)
		{
			UiPage uip = m_uiPageList.get(k);
			if(uip.SetVisible(ctrlId, b_visible))
				return;
		}
	}
	public void SetControlEnable(int ctrlId, boolean b_enable)
	{
		for(int k = 0; k < m_uiPageList.size(); ++k)
		{
			UiPage uip = m_uiPageList.get(k);
			if(uip.SetEnable(ctrlId, b_enable))
				return;
		}
	}
	
	// 从外部设置控件的属性
	public void SetControlProperty(int ctrlId, int property, int value)
	{
		for(int k = 0; k < m_uiPageList.size(); ++k)
		{
			UiPage uip = m_uiPageList.get(k);
			if(uip.SetControlProperty(ctrlId, property, value))
				return;
		}
	}
		
	private UiPage GetUiPage(int idPage)
	{
		if(null == m_uiRoot)   
			return null;
		
		UiPage result = null;
		for(int k = 0; k < m_uiPageList.size(); ++k)
		{
			result = m_uiPageList.get(k);
			if(result.id == idPage)
				return result;
		}		
		return result;
	}
	
	public int GetSlidePage(boolean b_left)
	{
		if(null == m_uiPage)
			return -1;
		
		return b_left ? this.m_uiPage.leftJumpPage:this.m_uiPage.rightJumpPage;		
	}
	// int idPage
	public int GetJumpPage()
	{
		if(null == m_uiPage)
			return -1;
		
		return this.m_uiPage.jumpPage;	
	}
	
	public boolean GetPageChilds(int idPage, UiPage.Callback callback)
	{
		if(idPage > 0)
		{
			m_uiPage = GetUiPage(idPage);
			if(null == m_uiPage) m_uiPage = GetUiPage(m_uiRoot.startPage);
		}
		else{
			m_uiPage = GetUiPage(m_uiRoot.startPage);	
		}
		
		if(null == m_uiPage)
		{
			PrintLog("LoadUiPage m_uiPage == null");
			return false;
		}
		
		return m_uiPage.Show(callback);
	}
	
	public void Clear()
	{
		m_uiPage     = null;
		m_uiRoot     = null;
		m_uiPageList.clear();
	}
	
	public boolean OpenFile(InputStream isXml)
	{
		// 解析XML流
		if(!(new XmlParser().OpenFile(SkinManager.GetXmlInputStream(), this)))
		{
			PrintLog("Open xml failed");
			return false;
		}
		
		if(null == m_uiRoot)
		{
			/* 
			 * 如果连根节点都没有，
			 * 说明不是一个合法的配置文件
			 */
			PrintLog("LoadUI m_uiRoot == null");
			return false;
		}
		
		// 为控件进行排序
		ReorderUi();
		return true;
	}
}
