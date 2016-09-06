/* *****************************************************************************

                  版权所有 (C), 2013-2100

 ******************************************************************************
  文件名称 : XmlParser.java
  作者           : 贾延刚
  生成日期 : 2013年

  版本           : 1.0
  功能描述 : 
                          解析界面XML文件
          
                          返回：                
                                      本类通过接口IXmlParser返回数据
                          

  修改历史 :

******************************************************************************/
package phlx.jikong.ccc_ui.xml;

import java.io.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import android.graphics.Color;
import android.graphics.Rect;
import phlx.jikong.ccc_ui.ui.*;

public class XmlParser
{
	public static final int XML_ROOT         = 1;
	public static final int XML_PAGE         = 2;
	public static final int XML_POPUP_PAGE   = 3;
	
	public interface IXmlParser
	{
		void ParseResult(int type, int idPage, Object xmlCtrl);
	}
	
	private IXmlParser m_parXml = null;
	
	//(60,122,133,44)
	boolean ParseGemometry(String str, Rect rect)
	{
		if(null == rect)
			return false;
		
		str.trim();
		if(str.charAt(0) != '(')
			return false;
		
		str = str.substring(1);
		for(int k = 0; k < 3; ++k)
		{
			int ix = str.indexOf(',');
			if(ix <= 0)
				return false;
			
			int ivalue = Integer.parseInt(str.substring(0, ix));
			str = str.substring(ix+1);
			if     (0 == k) rect.left = ivalue;
			else if(1 == k) rect.top = ivalue;
			else if(2 == k) rect.right = ivalue;
		}
		int ix = str.indexOf(')');
		if(ix <= 0)
			return false;
		
		rect.bottom = Integer.parseInt(str.substring(0, ix));
		return true;
	}
	// #ff0000
	private int ParseColor(String str)
	{	
		int result = Color.TRANSPARENT;
		str.trim();
		if(str.length() <= 0) 
			return result;
		
		if(str.charAt(0) != '#')
			return result;
		
		str = str.substring(1);		
		int r = 0,g = 0,b = 0;
		for(int k = 0; k < 3; ++k)
		{
			String cellstr = str.substring(k*2, k*2+2);
			if(cellstr == "" || cellstr.length() < 2)
				return result;
			
			int ivalue = Integer.parseInt(cellstr, 16);
			if     (0 == k) r = ivalue;
			else if(1 == k) g = ivalue;
			else if(2 == k) b = ivalue;
		}	
		return Color.rgb(r, g, b);
	}
	private boolean ParseBooleanValue(String str)
  	{  	
		boolean result = false;
		try{
			result = Integer.parseInt(str.trim()) <= 0 ? false:true;
		}
		catch(NumberFormatException e)
		{}
		return result;
  	}

	private int ParsePopupPageNo(String str)
  	{  		
  		str.trim();
  		int ix = str.indexOf('_');
  		if(ix < 0)
  			return -1;
  		
  		str = str.substring(ix+1);
  		return Integer.parseInt(str);
  	}
	
	// 把字体名转换为android能够支持的
	private int ParseFontName(String str)
	{	
		if(str.equalsIgnoreCase("宋体"))
			return UiFont.FONT_NAME_SONG;
		if(str.equalsIgnoreCase("黑体"))
			return UiFont.FONT_NAME_HEI;
		
		if(str.equalsIgnoreCase("楷体")||
		   str.equalsIgnoreCase("楷体_GB2312"))
			return UiFont.FONT_NAME_KAISHU;
			
		return UiFont.FONT_NAME_SONG;
	}
	
	// 页节点     按钮控件子节点
	private boolean ParseXmlVideo(UiPage uiPage, Node videoNode)
	{
		if(null == uiPage || null == videoNode)
			return false;
		
		int idPage = uiPage.id;
		NamedNodeMap nameMap = videoNode.getAttributes();
		if(null == nameMap)
			return false;
		
		UiVideo uiVideo = new UiVideo();
		Rect rect = new Rect(); 
		Node attrNode; 
		for(int k = 0; k < nameMap.getLength(); ++k)
		{
			attrNode = nameMap.item(k);
			if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrVideo.ID))
				uiVideo.id = Integer.parseInt(attrNode.getNodeValue());
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrVideo.Z_INDEX))
				uiVideo.z_index = Integer.parseInt(attrNode.getNodeValue());
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrVideo.GEOMETRY))
			{
				if(!ParseGemometry(attrNode.getNodeValue(), rect))
					return false;
				
				uiVideo.x      = rect.left;
				uiVideo.y      = rect.top;
				uiVideo.width  = rect.right;
				uiVideo.height = rect.bottom;
			}
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrVideo.VISIBLE))
				uiVideo.visible = ParseBooleanValue(attrNode.getNodeValue());
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrVideo.ENABLE))
				uiVideo.enable = ParseBooleanValue(attrNode.getNodeValue());
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrVideo.TEXT))
				uiVideo.text = attrNode.getNodeValue();
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrVideo.MOVIE))
				uiVideo.movie = attrNode.getNodeValue();
						
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrVideo.IMAGE_NORMAL))
				uiVideo.styleNormal.bkImage = attrNode.getNodeValue();
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrVideo.FOREGROUNDCOLOR))
				uiVideo.styleNormal.colorForeground = ParseColor(attrNode.getNodeValue());
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrVideo.BACKGROUNDCOLOR))
				uiVideo.styleNormal.colorBackground = ParseColor(attrNode.getNodeValue());	
		}
		
		uiVideo.idPage = idPage;
		uiPage.AddXmlNone((UiBase)uiVideo);		
		return true;
	}
		
	// 页节点     按钮控件子节点
	private boolean ParseXmlButton(UiPage uiPage, Node imageNode)
	{
		if(null == uiPage || null == imageNode)
			return false;
		
		int idPage = uiPage.id;
		NamedNodeMap nameMap = imageNode.getAttributes();
		if(null == nameMap)
			return false;
		
		UiButton ctrlImage = new UiButton();
		Rect rect = new Rect(); 
		Node attrNode; 
		for(int k = 0; k < nameMap.getLength(); ++k)
		{
			attrNode = nameMap.item(k);
			if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrImage.ID))
				ctrlImage.id = Integer.parseInt(attrNode.getNodeValue());
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrImage.Z_INDEX))
				ctrlImage.z_index = Integer.parseInt(attrNode.getNodeValue());
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrImage.GEOMETRY))
			{
				if(!ParseGemometry(attrNode.getNodeValue(), rect))
					return false;
				
				ctrlImage.x      = rect.left;
				ctrlImage.y      = rect.top;
				ctrlImage.width  = rect.right;
				ctrlImage.height = rect.bottom;
			}
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrImage.VISIBLE))
				ctrlImage.visible = ParseBooleanValue(attrNode.getNodeValue());
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrImage.ENABLE))
				ctrlImage.enable = ParseBooleanValue(attrNode.getNodeValue());
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrImage.POPUPPAGE))
				ctrlImage.jumpPage = ParsePopupPageNo(attrNode.getNodeValue());
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrImage.TEXT))
				ctrlImage.text = attrNode.getNodeValue();
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrImage.WARNING))
				ctrlImage.warning = Integer.parseInt(attrNode.getNodeValue());
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrImage.WARNING_TEXT))
				ctrlImage.warningText = attrNode.getNodeValue();
			
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrImage.IMAGE_NORMAL))
				ctrlImage.styleNormal.bkImage = attrNode.getNodeValue();
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrImage.IMAGE_STYLE))
				ctrlImage.styleNormal.imageStyle = Integer.parseInt(attrNode.getNodeValue());
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrImage.FOREGROUNDCOLOR))
				ctrlImage.styleNormal.colorForeground = ParseColor(attrNode.getNodeValue());
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrImage.BACKGROUNDCOLOR))
				ctrlImage.styleNormal.colorBackground = ParseColor(attrNode.getNodeValue());
			
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrImage.IMAGE_PRESSED))
				ctrlImage.stylePressed.bkImage = attrNode.getNodeValue();
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrImage.IMAGE_STYLE_PRESSED))
				ctrlImage.stylePressed.imageStyle = Integer.parseInt(attrNode.getNodeValue());
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrImage.FOREGROUNDCOLOR_PRESSED))
				ctrlImage.stylePressed.colorForeground = ParseColor(attrNode.getNodeValue());
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrImage.BACKGROUNDCOLOR_PRESSED))
				ctrlImage.stylePressed.colorBackground = ParseColor(attrNode.getNodeValue());
			
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrImage.IMAGE_FOCUSED))
				ctrlImage.styleFocused.bkImage = attrNode.getNodeValue();
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrImage.IMAGE_STYLE_FOCUSED))
				ctrlImage.styleFocused.imageStyle = Integer.parseInt(attrNode.getNodeValue());
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrImage.FOREGROUNDCOLOR_FOCUSED))
				ctrlImage.styleFocused.colorForeground = ParseColor(attrNode.getNodeValue());
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrImage.BACKGROUNDCOLOR_FOCUSED))
				ctrlImage.styleFocused.colorBackground = ParseColor(attrNode.getNodeValue());
			
			// normal font
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrImage.FONT))
				ctrlImage.fontNormal.name = ParseFontName(attrNode.getNodeValue());
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrImage.FONTSIZE))
				ctrlImage.fontNormal.size = Integer.parseInt(attrNode.getNodeValue());
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrImage.FONTBOLD))
				ctrlImage.fontNormal.bold = (0 == Integer.parseInt(attrNode.getNodeValue())) ? false:true;	
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrImage.FONTITALIC))
				ctrlImage.fontNormal.italic = (0 == Integer.parseInt(attrNode.getNodeValue())) ? false:true;
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrImage.FONTUNDERLINE))
				ctrlImage.fontNormal.underLine = (0 == Integer.parseInt(attrNode.getNodeValue())) ? false:true;
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrImage.FONTSTRIKEOUT))
				ctrlImage.fontNormal.strikeOut = (0 == Integer.parseInt(attrNode.getNodeValue())) ? false:true;
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrImage.TEXTALIGN))
				ctrlImage.fontNormal.textAlign = Integer.parseInt(attrNode.getNodeValue());
			
			// pressed font
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrImage.FONT_PRESSED))
				ctrlImage.fontPressed.name = ParseFontName(attrNode.getNodeValue());
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrImage.FONTSIZE_PRESSED))
				ctrlImage.fontPressed.size = Integer.parseInt(attrNode.getNodeValue());
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrImage.FONTBOLD_PRESSED))
				ctrlImage.fontPressed.bold = (0 == Integer.parseInt(attrNode.getNodeValue())) ? false:true;	
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrImage.FONTITALIC_PRESSED))
				ctrlImage.fontPressed.italic = (0 == Integer.parseInt(attrNode.getNodeValue())) ? false:true;
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrImage.FONTUNDERLINE_PRESSED))
				ctrlImage.fontPressed.underLine = (0 == Integer.parseInt(attrNode.getNodeValue())) ? false:true;
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrImage.FONTSTRIKEOUT_PRESSED))
				ctrlImage.fontPressed.strikeOut = (0 == Integer.parseInt(attrNode.getNodeValue())) ? false:true;
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrImage.TEXTALIGN_PRESSED))
				ctrlImage.fontPressed.textAlign = Integer.parseInt(attrNode.getNodeValue());
			
			
			// focused font
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrImage.FONT_FOCUSED))
				ctrlImage.fontFocused.name = ParseFontName(attrNode.getNodeValue());
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrImage.FONTSIZE_FOCUSED))
				ctrlImage.fontFocused.size = Integer.parseInt(attrNode.getNodeValue());
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrImage.FONTBOLD_FOCUSED))
				ctrlImage.fontFocused.bold = (0 == Integer.parseInt(attrNode.getNodeValue())) ? false:true;	
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrImage.FONTITALIC_FOCUSED))
				ctrlImage.fontFocused.italic = (0 == Integer.parseInt(attrNode.getNodeValue())) ? false:true;
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrImage.FONTUNDERLINE_FOCUSED))
				ctrlImage.fontFocused.underLine = (0 == Integer.parseInt(attrNode.getNodeValue())) ? false:true;
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrImage.FONTSTRIKEOUT_FOCUSED))
				ctrlImage.fontFocused.strikeOut = (0 == Integer.parseInt(attrNode.getNodeValue())) ? false:true;
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrImage.TEXTALIGN_FOCUSED))
				ctrlImage.fontFocused.textAlign = Integer.parseInt(attrNode.getNodeValue());
		}
		
		// image的子节点
		Node childNode = imageNode.getFirstChild();
		while(childNode != null)
		{
			if(childNode.getNodeType() == Node.ELEMENT_NODE)
			{
				if(childNode.getNodeName().equalsIgnoreCase(CTRL_EVENT))
				{
					ParseXmlButtonEvent(ctrlImage, childNode);
				}
			}
			childNode = childNode.getNextSibling();
		}
		ctrlImage.idPage = idPage;
		uiPage.AddXmlNone((UiBase)ctrlImage);
		
		// event		
		return true;
	}
	
	// image的子节点Event
		private boolean ParseXmlButtonEvent(UiButton ctrlImage, Node eventNode)
		{
			if(null == eventNode)
				return false;
			
			Node childNode = eventNode.getFirstChild();
			while(childNode != null)
			{
				if(childNode.getNodeType() == Node.ELEMENT_NODE)
				{
					if(childNode.getNodeName().equalsIgnoreCase(XmlCtrlEvent.CTRL_EVENT_CLICK))
					{
						ctrlImage.agClick = new UiActionGroup();
						ParseXmlEventClick(ctrlImage.agClick, childNode);
					}
				}
				childNode = childNode.getNextSibling();
			}		
			return true;	
		}
	
	/*/ image的子节点Event
	private boolean ParseXmlButtonEvent(UiButton ctrlImage, Node eventNode)
	{
		if(null == eventNode)
			return false;
		
		Node childNode = eventNode.getFirstChild();
		while(childNode != null)
		{
			if(childNode.getNodeType() == Node.ELEMENT_NODE)
			{
				if(childNode.getNodeName().equalsIgnoreCase(XmlCtrlEvent.CTRL_EVENT_CLICK))
				{
					ctrlImage.agClick = new UiActionGroup();
					ParseXmlEventClick(ctrlImage.agClick, childNode);
				}
			}
			childNode = childNode.getNextSibling();
		}		
		return true;	
	}*/
		
	// 页节点     图像控件子节点
	private boolean ParseXmlImage(UiPage uiPage, Node imageNode)
	{
		if(null == uiPage || null == imageNode)
			return false;
		
		int idPage = uiPage.id;
		NamedNodeMap nameMap = imageNode.getAttributes();
		if(null == nameMap)
			return false;
		
		UiImage ctrlImage = new UiImage();
		Rect rect = new Rect(); 
		Node attrNode; 
		for(int k = 0; k < nameMap.getLength(); ++k)
		{
			attrNode = nameMap.item(k);
			if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrImage.ID))
				ctrlImage.id = Integer.parseInt(attrNode.getNodeValue());
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrImage.Z_INDEX))
				ctrlImage.z_index = Integer.parseInt(attrNode.getNodeValue());
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrImage.GEOMETRY))
			{
				if(!ParseGemometry(attrNode.getNodeValue(), rect))
					return false;
				
				ctrlImage.x      = rect.left;
				ctrlImage.y      = rect.top;
				ctrlImage.width  = rect.right;
				ctrlImage.height = rect.bottom;
			}
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrImage.VISIBLE))
				ctrlImage.visible = ParseBooleanValue(attrNode.getNodeValue());
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrImage.ENABLE))
				ctrlImage.enable = ParseBooleanValue(attrNode.getNodeValue());
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrImage.POPUPPAGE))
				ctrlImage.jumpPage = ParsePopupPageNo(attrNode.getNodeValue());
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrImage.TEXT))
				ctrlImage.text = attrNode.getNodeValue();
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrImage.WARNING))
				ctrlImage.warning = Integer.parseInt(attrNode.getNodeValue());
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrImage.WARNING_TEXT))
				ctrlImage.warningText = attrNode.getNodeValue();
			
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrImage.IMAGE_NORMAL))
				ctrlImage.styleNormal.bkImage = attrNode.getNodeValue();
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrImage.IMAGE_STYLE))
				ctrlImage.styleNormal.imageStyle = Integer.parseInt(attrNode.getNodeValue());
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrImage.FOREGROUNDCOLOR))
				ctrlImage.styleNormal.colorForeground = ParseColor(attrNode.getNodeValue());
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrImage.BACKGROUNDCOLOR))
				ctrlImage.styleNormal.colorBackground = ParseColor(attrNode.getNodeValue());
			
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrImage.IMAGE_PRESSED))
				ctrlImage.stylePressed.bkImage = attrNode.getNodeValue();
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrImage.IMAGE_STYLE_PRESSED))
				ctrlImage.stylePressed.imageStyle = Integer.parseInt(attrNode.getNodeValue());
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrImage.FOREGROUNDCOLOR_PRESSED))
				ctrlImage.stylePressed.colorForeground = ParseColor(attrNode.getNodeValue());
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrImage.BACKGROUNDCOLOR_PRESSED))
				ctrlImage.stylePressed.colorBackground = ParseColor(attrNode.getNodeValue());
			
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrImage.IMAGE_FOCUSED))
				ctrlImage.styleFocused.bkImage = attrNode.getNodeValue();
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrImage.IMAGE_STYLE_FOCUSED))
				ctrlImage.styleFocused.imageStyle = Integer.parseInt(attrNode.getNodeValue());
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrImage.FOREGROUNDCOLOR_FOCUSED))
				ctrlImage.styleFocused.colorForeground = ParseColor(attrNode.getNodeValue());
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrImage.BACKGROUNDCOLOR_FOCUSED))
				ctrlImage.styleFocused.colorBackground = ParseColor(attrNode.getNodeValue());
			
			// normal font
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrImage.FONT))
				ctrlImage.fontNormal.name = ParseFontName(attrNode.getNodeValue());
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrImage.FONTSIZE))
				ctrlImage.fontNormal.size = Integer.parseInt(attrNode.getNodeValue());
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrImage.FONTBOLD))
				ctrlImage.fontNormal.bold = (0 == Integer.parseInt(attrNode.getNodeValue())) ? false:true;	
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrImage.FONTITALIC))
				ctrlImage.fontNormal.italic = (0 == Integer.parseInt(attrNode.getNodeValue())) ? false:true;
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrImage.FONTUNDERLINE))
				ctrlImage.fontNormal.underLine = (0 == Integer.parseInt(attrNode.getNodeValue())) ? false:true;
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrImage.FONTSTRIKEOUT))
				ctrlImage.fontNormal.strikeOut = (0 == Integer.parseInt(attrNode.getNodeValue())) ? false:true;
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrImage.TEXTALIGN))
				ctrlImage.fontNormal.textAlign = Integer.parseInt(attrNode.getNodeValue());
			
			// pressed font
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrImage.FONT_PRESSED))
				ctrlImage.fontPressed.name = ParseFontName(attrNode.getNodeValue());
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrImage.FONTSIZE_PRESSED))
				ctrlImage.fontPressed.size = Integer.parseInt(attrNode.getNodeValue());
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrImage.FONTBOLD_PRESSED))
				ctrlImage.fontPressed.bold = (0 == Integer.parseInt(attrNode.getNodeValue())) ? false:true;	
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrImage.FONTITALIC_PRESSED))
				ctrlImage.fontPressed.italic = (0 == Integer.parseInt(attrNode.getNodeValue())) ? false:true;
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrImage.FONTUNDERLINE_PRESSED))
				ctrlImage.fontPressed.underLine = (0 == Integer.parseInt(attrNode.getNodeValue())) ? false:true;
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrImage.FONTSTRIKEOUT_PRESSED))
				ctrlImage.fontPressed.strikeOut = (0 == Integer.parseInt(attrNode.getNodeValue())) ? false:true;
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrImage.TEXTALIGN_PRESSED))
				ctrlImage.fontPressed.textAlign = Integer.parseInt(attrNode.getNodeValue());
			
			
			// focused font
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrImage.FONT_FOCUSED))
				ctrlImage.fontFocused.name = ParseFontName(attrNode.getNodeValue());
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrImage.FONTSIZE_FOCUSED))
				ctrlImage.fontFocused.size = Integer.parseInt(attrNode.getNodeValue());
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrImage.FONTBOLD_FOCUSED))
				ctrlImage.fontFocused.bold = (0 == Integer.parseInt(attrNode.getNodeValue())) ? false:true;	
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrImage.FONTITALIC_FOCUSED))
				ctrlImage.fontFocused.italic = (0 == Integer.parseInt(attrNode.getNodeValue())) ? false:true;
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrImage.FONTUNDERLINE_FOCUSED))
				ctrlImage.fontFocused.underLine = (0 == Integer.parseInt(attrNode.getNodeValue())) ? false:true;
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrImage.FONTSTRIKEOUT_FOCUSED))
				ctrlImage.fontFocused.strikeOut = (0 == Integer.parseInt(attrNode.getNodeValue())) ? false:true;
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrImage.TEXTALIGN_FOCUSED))
				ctrlImage.fontFocused.textAlign = Integer.parseInt(attrNode.getNodeValue());
		}
		
		// image的子节点
		Node childNode = imageNode.getFirstChild();
		while(childNode != null)
		{
			if(childNode.getNodeType() == Node.ELEMENT_NODE)
			{
				if(childNode.getNodeName().equalsIgnoreCase(CTRL_EVENT))
				{
					ParseXmlImageEvent(ctrlImage, childNode);
				}
			}
			childNode = childNode.getNextSibling();
		}
				
		ctrlImage.idPage = idPage;
		uiPage.AddXmlNone((UiBase)ctrlImage);
		
		// event		
		return true;
	}
	
	// image的子节点Event
	private boolean ParseXmlImageEvent(UiImage ctrlImage, Node eventNode)
	{
		if(null == eventNode)
			return false;
		
		Node childNode = eventNode.getFirstChild();
		while(childNode != null)
		{
			if(childNode.getNodeType() == Node.ELEMENT_NODE)
			{
				if(childNode.getNodeName().equalsIgnoreCase(XmlCtrlEvent.CTRL_EVENT_CLICK))
				{
					ctrlImage.agClick = new UiActionGroup();
					ParseXmlEventClick(ctrlImage.agClick, childNode);
				}
			}
			childNode = childNode.getNextSibling();
		}
		
		//m_parXml.ParseResult(XML_PAGE, xmlPage.id, xmlPage);				
		return true;
		
	}
		
	public static final String CTRL_EVENT   = "Event";
	public static final String CTRL_EVENT_CLICK_HEADER   = "Method";

	
	private boolean ParseXmlEventClick(UiActionGroup agClick, Node clickNode)
	{
		if(null == agClick)
			return false;
		
		Node childNode = clickNode.getFirstChild();//.getElementsByTagName(XmlCtrlHeader.CTRL_IMAGE);
		while(childNode != null)
		{
			if(childNode.getNodeType() == Node.ELEMENT_NODE)
			{
				if(childNode.getNodeName().equalsIgnoreCase(CTRL_EVENT_CLICK_HEADER))
				{
					NamedNodeMap nameMap = childNode.getAttributes();
					if(null == nameMap)
						return false;
					
					Node attrNode; 
					UiAction action = new UiAction();
					for(int k = 0; k < nameMap.getLength(); ++k)
					{
						attrNode = nameMap.item(k);
						if(attrNode.getNodeName().equalsIgnoreCase("Event"))
							action.action = ParseAction(attrNode.getNodeValue());
						else if(attrNode.getNodeName().equalsIgnoreCase("Object"))
							action.targetId = GetLastNumberByUnderline(attrNode.getNodeValue());
						else if(attrNode.getNodeName().equalsIgnoreCase("Parameters"))
							action.parameter = attrNode.getNodeValue();
					}
					/*
					 * 计划使用android:org.mozilla.firefox;ios:----;windows:---;
					 * 这种格式来适应调用多种平台的程序
					 * 不过，
					 * 目前还未做，只是在这儿留个接口
					if(UiAction.ACTION_INVOKE_APP == action.action)
					{
						
					}*/
					agClick.AddAction(action);
				}
			}
			childNode = childNode.getNextSibling();
		}			
		return true;
	}
	
	private int GetLastNumberByUnderline(String str)
  	{  		
		str.trim();
  		int ix = str.lastIndexOf('_');
  		if(ix < 0)
  			return -1;
  		
  		str = str.substring(ix+1);
  		return Integer.parseInt(str);
  	}
	
	private int ParseAction(String str)
	{	
		if(str.equalsIgnoreCase("隐藏"))
			return UiAction.ACTION_HIDE;
		if(str.equalsIgnoreCase("显示"))
			return UiAction.ACTION_SHOW;
		
		if(str.equalsIgnoreCase("写数据"))
			return UiAction.ACTION_WRITE;
		
		if(str.equalsIgnoreCase("禁用"))
			return UiAction.ACTION_DISABLE;
		if(str.equalsIgnoreCase("可用"))
			return UiAction.ACTION_ENABLE;
		
		if(str.equalsIgnoreCase("调用其它应用程序"))
			return UiAction.ACTION_INVOKE_APP;
		if(str.equalsIgnoreCase("调用应用程序"))
			return UiAction.ACTION_INVOKE_APP;
		
		System.out.printf("ParseAction ACTION_VALID %s\n", str);
		return UiAction.ACTION_VALID;
	}
	
	// TODO: 使用image的属性，需要检查 ------   页节点     Label控件子节点 
	private boolean ParseXmlLabel(UiPage uiPage, Node labelNode)
	{
		if(null == uiPage && null == labelNode)
			return false;
		
		int idPage = uiPage.id;
		
		NamedNodeMap nameMap = labelNode.getAttributes();
		if(null == nameMap)
			return false;
		
		UiLabel ctrlLabel = new UiLabel();
		Rect rect = new Rect(); 
		Node attrNode; 
		for(int k = 0; k < nameMap.getLength(); ++k)
		{
			attrNode = nameMap.item(k);
			if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrLabel.ID))
				ctrlLabel.id = Integer.parseInt(attrNode.getNodeValue());
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrLabel.Z_INDEX))
				ctrlLabel.z_index = Integer.parseInt(attrNode.getNodeValue());
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrLabel.GEOMETRY))
			{
				if(!ParseGemometry(attrNode.getNodeValue(), rect))
					return false;
								
				ctrlLabel.x      = rect.left;
				ctrlLabel.y      = rect.top;
				ctrlLabel.width  = rect.right;
				ctrlLabel.height = rect.bottom;
			}
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrLabel.VISIBLE))
				ctrlLabel.visible = ParseBooleanValue(attrNode.getNodeValue());
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrLabel.ENABLE))
				ctrlLabel.enable = ParseBooleanValue(attrNode.getNodeValue());
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrLabel.TEXT))
				ctrlLabel.text = attrNode.getNodeValue();
			
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrLabel.BKIMAGE))
				ctrlLabel.styleNormal.bkImage = attrNode.getNodeValue();
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrLabel.IMAGE_STYLE))
				ctrlLabel.styleNormal.imageStyle = Integer.parseInt(attrNode.getNodeValue());
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrLabel.FOREGROUNDCOLOR))
				ctrlLabel.styleNormal.colorForeground = ParseColor(attrNode.getNodeValue());
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrLabel.BACKGROUNDCOLOR))
				ctrlLabel.styleNormal.colorBackground = ParseColor(attrNode.getNodeValue());
			
			// normal font
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrLabel.FONT))
				ctrlLabel.fontNormal.name = ParseFontName(attrNode.getNodeValue());
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrLabel.FONTSIZE))
			{
				ctrlLabel.fontNormal.size = Integer.parseInt(attrNode.getNodeValue());
				//System.out.printf("label font size = %d\n", ctrlLabel.fontNormal.size);
			}
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrLabel.FONTBOLD))
				ctrlLabel.fontNormal.bold = (0 == Integer.parseInt(attrNode.getNodeValue())) ? false:true;	
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrLabel.FONTITALIC))
				ctrlLabel.fontNormal.italic = (0 == Integer.parseInt(attrNode.getNodeValue())) ? false:true;
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrLabel.FONTUNDERLINE))
				ctrlLabel.fontNormal.underLine = (0 == Integer.parseInt(attrNode.getNodeValue())) ? false:true;
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrLabel.FONTSTRIKEOUT))
				ctrlLabel.fontNormal.strikeOut = (0 == Integer.parseInt(attrNode.getNodeValue())) ? false:true;
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrLabel.TEXTALIGN))
				ctrlLabel.fontNormal.textAlign = Integer.parseInt(attrNode.getNodeValue());
		}
		ctrlLabel.idPage = idPage;
		uiPage.AddXmlNone((UiBase)ctrlLabel);
		return true;
	}
	
	// 页节点     Slider控件子节点 
	private boolean ParseXmlSlider(UiPage uiPage, Node sliderNode)
	{
		if(null == uiPage && null == sliderNode)
			return false;
		
		int idPage = uiPage.id;
		
		NamedNodeMap nameMap = sliderNode.getAttributes();
		if(null == nameMap)
			return false;
		
		UiSlider ctrlSlider = new UiSlider();
		Rect rect = new Rect(); 
		Node attrNode; 
		for(int k = 0; k < nameMap.getLength(); ++k)
		{
			attrNode = nameMap.item(k);
			
			if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrSlider.ID))
				ctrlSlider.id = Integer.parseInt(attrNode.getNodeValue());
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrSlider.Z_INDEX))
				ctrlSlider.z_index = Integer.parseInt(attrNode.getNodeValue());
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrSlider.GEOMETRY))
			{
				if(!ParseGemometry(attrNode.getNodeValue(), rect))
					return false;
								
				ctrlSlider.x      = rect.left;
				ctrlSlider.y      = rect.top;
				ctrlSlider.width  = rect.right;
				ctrlSlider.height = rect.bottom;
				
			}
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrSlider.VISIBLE))
				ctrlSlider.visible = ParseBooleanValue(attrNode.getNodeValue());
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrImage.ENABLE))
				ctrlSlider.enable = ParseBooleanValue(attrNode.getNodeValue());
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrSlider.VALUE))
				ctrlSlider.percent = Integer.parseInt(attrNode.getNodeValue());
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrSlider.ORIENTATION))
				ctrlSlider.orientation = Integer.parseInt(attrNode.getNodeValue());
			
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrSlider.BAR_BK_IMAGE))
				ctrlSlider.styleBar.bkImage = attrNode.getNodeValue();
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrSlider.BAR_BK_IMAGE_STYLE))
				ctrlSlider.styleBar.imageStyle = Integer.parseInt(attrNode.getNodeValue());
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrSlider.BAR_FORE_COLOR))
				ctrlSlider.styleBar.colorForeground = ParseColor(attrNode.getNodeValue());
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrSlider.BAR_BK_COLOR))
				ctrlSlider.styleBar.colorBackground = ParseColor(attrNode.getNodeValue());
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrSlider.BAR_WIDTH))
				ctrlSlider.barWidth = Integer.parseInt(attrNode.getNodeValue());
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrSlider.BAR_HEIGHT))
				ctrlSlider.barHeight = Integer.parseInt(attrNode.getNodeValue());
			
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrSlider.SLIPPER_BK_IMAGE))
				ctrlSlider.styleSlipper.bkImage = attrNode.getNodeValue();
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrSlider.SLIPPER_BK_COLOR))
				ctrlSlider.styleSlipper.colorBackground = ParseColor(attrNode.getNodeValue());
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrSlider.SLIPPER_WIDTH))
				ctrlSlider.slipperWidth = Integer.parseInt(attrNode.getNodeValue());
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrSlider.SLIPPER_HEIGHT))
				ctrlSlider.slipperHeight = Integer.parseInt(attrNode.getNodeValue());
			
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrSlider.PROGRESS_VISIBLE))
				ctrlSlider.percentShow = Integer.parseInt(attrNode.getNodeValue());
		}
		
		//System.out.printf("----------------ParseXmlSlider barWidth=%d, barHeight = %d\n", ctrlSlider.barWidth, ctrlSlider.barHeight);
		
		ctrlSlider.idPage = idPage;
		uiPage.AddXmlNone((UiBase)ctrlSlider);
		return true;
	}	
	
	// 页节点     图像控件子节点
	private boolean ParseXmlRadio(UiPage xmlPage, Node radioNode)
	{
		if(null == xmlPage && null == radioNode)
			return false;
		
		int idPage = xmlPage.id;
		NamedNodeMap nameMap = radioNode.getAttributes();
		if(null == nameMap)
			return false;
		
		UiRadio ctrlRadio = new UiRadio();
		Rect rect = new Rect(); 
		Node attrNode; 
		for(int k = 0; k < nameMap.getLength(); ++k)
		{
			attrNode = nameMap.item(k);
			if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrRadio.ID))
				ctrlRadio.id = Integer.parseInt(attrNode.getNodeValue());
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrRadio.Z_INDEX))
				ctrlRadio.z_index = Integer.parseInt(attrNode.getNodeValue());
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrRadio.GEOMETRY))
			{
				if(!ParseGemometry(attrNode.getNodeValue(), rect))
					return false;
				
				ctrlRadio.x      = rect.left;
				ctrlRadio.y      = rect.top;
				ctrlRadio.width  = rect.right;
				ctrlRadio.height = rect.bottom;
			}
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrRadio.VISIBLE))
				ctrlRadio.visible = ParseBooleanValue(attrNode.getNodeValue());
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrRadio.ENABLE))
				ctrlRadio.enable = ParseBooleanValue(attrNode.getNodeValue());
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrRadio.TEXT))
				ctrlRadio.text = attrNode.getNodeValue();
			
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrRadio.BKIMAGE))
				ctrlRadio.styleNormal.bkImage = attrNode.getNodeValue();
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrRadio.FOREGROUNDCOLOR))
				ctrlRadio.styleNormal.colorForeground = ParseColor(attrNode.getNodeValue());
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrRadio.BACKGROUNDCOLOR))
				ctrlRadio.styleNormal.colorBackground = ParseColor(attrNode.getNodeValue());
			
			// normal font
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrRadio.FONT))
				ctrlRadio.fontNormal.name = ParseFontName(attrNode.getNodeValue());
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrRadio.FONTSIZE))
				ctrlRadio.fontNormal.size = Integer.parseInt(attrNode.getNodeValue());
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrRadio.FONTBOLD))
				ctrlRadio.fontNormal.bold = (0 == Integer.parseInt(attrNode.getNodeValue())) ? false:true;	
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrRadio.FONTITALIC))
				ctrlRadio.fontNormal.italic = (0 == Integer.parseInt(attrNode.getNodeValue())) ? false:true;
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrRadio.FONTUNDERLINE))
				ctrlRadio.fontNormal.underLine = (0 == Integer.parseInt(attrNode.getNodeValue())) ? false:true;
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrRadio.FONTSTRIKEOUT))
				ctrlRadio.fontNormal.strikeOut = (0 == Integer.parseInt(attrNode.getNodeValue())) ? false:true;
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrRadio.TEXTALIGN))
				ctrlRadio.fontNormal.textAlign = Integer.parseInt(attrNode.getNodeValue());
			
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrRadio.CHECKED))
				ctrlRadio.checked = ParseBooleanValue(attrNode.getNodeValue());			
		}
		ctrlRadio.idPage = idPage;
		xmlPage.AddXmlNone((UiBase)ctrlRadio);
		
		// event		
		return true;
	}

	// 页节点
	private boolean ParseXmlPage(Node pageNode)
	{
		if(null == pageNode)
			return false;
		
		NamedNodeMap nameMap = pageNode.getAttributes();
		if(null == nameMap)
			return false;
		
		UiPage uiPage = new UiPage();
		Node attrNode; 
		for(int k = 0; k < nameMap.getLength(); ++k)
		{
			attrNode = nameMap.item(k);
			if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrPage.ID))
				uiPage.id = Integer.parseInt(attrNode.getNodeValue());
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrPage.TITLE))
				uiPage.title = attrNode.getNodeValue();
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrPage.BKCOLOR))
				uiPage.bkColor = ParseColor(attrNode.getNodeValue());
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrPage.FWCOLOR))
				uiPage.fwColor = ParseColor(attrNode.getNodeValue());
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrPage.BKIMAGE))
				uiPage.bkImage = attrNode.getNodeValue();
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrPage.BKIMAGE_MODE))
				uiPage.imageMode = Integer.parseInt(attrNode.getNodeValue());
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrPage.POPUPPAGE))
				uiPage.jumpPage = ParsePopupPageNo(attrNode.getNodeValue());
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrPage.LEFT_POPUPPAGE))
				uiPage.leftJumpPage = ParsePopupPageNo(attrNode.getNodeValue());
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrPage.RIGHT_POPUPPAGE))
				uiPage.rightJumpPage = ParsePopupPageNo(attrNode.getNodeValue());
		}
		
		// image control
		Node childNode = pageNode.getFirstChild();//.getElementsByTagName(XmlCtrlHeader.CTRL_IMAGE);
		while(childNode != null)
		{
			if(childNode.getNodeType() == Node.ELEMENT_NODE)
			{
				if(childNode.getNodeName().equalsIgnoreCase(XmlCtrlHeader.CTRL_IMAGE))
				{
					ParseXmlImage(uiPage, childNode);
				}
				else if(childNode.getNodeName().equalsIgnoreCase(XmlCtrlHeader.CTRL_SLIDER))
				{
					ParseXmlSlider(uiPage, childNode);
				}
				else if(childNode.getNodeName().equalsIgnoreCase(XmlCtrlHeader.CTRL_LABEL))
				{
					ParseXmlLabel(uiPage, childNode);
				}
				else if(childNode.getNodeName().equalsIgnoreCase(XmlCtrlHeader.CTRL_BUTTON))
				{
					ParseXmlButton(uiPage, childNode);
				}
				else if(childNode.getNodeName().equalsIgnoreCase(XmlCtrlHeader.CTRL_CHECKBOX))
				{
					ParseXmlCheckBox(uiPage, childNode);
				}
				else if(childNode.getNodeName().equalsIgnoreCase(XmlCtrlHeader.CTRL_RADIO))
				{
					ParseXmlRadio(uiPage, childNode);
				}
				else if(childNode.getNodeName().equalsIgnoreCase(XmlCtrlHeader.CTRL_PROGRESS))
				{
					ParseXmlProgress(uiPage, childNode);
				}
				else if(childNode.getNodeName().equalsIgnoreCase(XmlCtrlHeader.CTRL_VIDEO))
				{
					ParseXmlVideo(uiPage, childNode);
				}	
			}
			childNode = childNode.getNextSibling();
		}
		
		m_parXml.ParseResult(XML_PAGE, uiPage.id, uiPage);				
		return true;
	}
	
	
	// 页节点
	private boolean ParseXmlPopupPage(Node pageNode)
	{
		if(null == pageNode)
			return false;
		
		NamedNodeMap nameMap = pageNode.getAttributes();
		if(null == nameMap)
			return false;
		
		UiPage uiPage = new UiPage();
		Node attrNode; 
		for(int k = 0; k < nameMap.getLength(); ++k)
		{
			attrNode = nameMap.item(k);
			if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrPage.ID))
				uiPage.id = Integer.parseInt(attrNode.getNodeValue());
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrPage.TITLE))
				uiPage.title = attrNode.getNodeValue();
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrPage.BKCOLOR))
				uiPage.bkColor = ParseColor(attrNode.getNodeValue());
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrPage.FWCOLOR))
				uiPage.fwColor = ParseColor(attrNode.getNodeValue());
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrPage.BKIMAGE))
				uiPage.bkImage = attrNode.getNodeValue();
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrPage.BKIMAGE_MODE))
				uiPage.imageMode = Integer.parseInt(attrNode.getNodeValue());
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrPage.POPUPPAGE))
				uiPage.jumpPage = ParsePopupPageNo(attrNode.getNodeValue());
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrPage.LEFT_POPUPPAGE))
				uiPage.leftJumpPage = ParsePopupPageNo(attrNode.getNodeValue());
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrPage.RIGHT_POPUPPAGE))
				uiPage.rightJumpPage = ParsePopupPageNo(attrNode.getNodeValue());
		}
		uiPage.bPopup = true;
		
		// image control
		Node childNode = pageNode.getFirstChild();//.getElementsByTagName(XmlCtrlHeader.CTRL_IMAGE);
		while(childNode != null)
		{
			if(childNode.getNodeType() == Node.ELEMENT_NODE)
			{
				if(childNode.getNodeName().equalsIgnoreCase(XmlCtrlHeader.CTRL_IMAGE))
				{
					ParseXmlImage(uiPage, childNode);
				}
				else if(childNode.getNodeName().equalsIgnoreCase(XmlCtrlHeader.CTRL_SLIDER))
				{
					ParseXmlSlider(uiPage, childNode);
				}
				else if(childNode.getNodeName().equalsIgnoreCase(XmlCtrlHeader.CTRL_LABEL))
				{
					ParseXmlLabel(uiPage, childNode);
				}
				else if(childNode.getNodeName().equalsIgnoreCase(XmlCtrlHeader.CTRL_BUTTON))
				{
					ParseXmlButton(uiPage, childNode);
				}
				else if(childNode.getNodeName().equalsIgnoreCase(XmlCtrlHeader.CTRL_CHECKBOX))
				{
					ParseXmlCheckBox(uiPage, childNode);
				}
				else if(childNode.getNodeName().equalsIgnoreCase(XmlCtrlHeader.CTRL_RADIO))
				{
					ParseXmlRadio(uiPage, childNode);
				}
				else if(childNode.getNodeName().equalsIgnoreCase(XmlCtrlHeader.CTRL_PROGRESS))
				{
					ParseXmlProgress(uiPage, childNode);
				}	
			}
			childNode = childNode.getNextSibling();
		}
		
		m_parXml.ParseResult(XML_POPUP_PAGE, uiPage.id, uiPage);				
		return true;
	}
	// 根节点
	private boolean parseXmlRootElement(Element rootElement)
	{
		if(null == rootElement)
			return false;
		
		NamedNodeMap nameMap = rootElement.getAttributes();
		if(nameMap != null)
		{
			UiRoot uiRoot = new UiRoot();
			Node attrNode; 
			for(int k = 0; k < nameMap.getLength(); ++k)
			{
				attrNode = nameMap.item(k);
				if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrRoot.START_PAGE))
					uiRoot.startPage = ParsePopupPageNo(attrNode.getNodeValue());
				else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrRoot.WIDTH))
					uiRoot.width = Integer.parseInt(attrNode.getNodeValue());
				else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrRoot.HEIGHT))
					uiRoot.height = Integer.parseInt(attrNode.getNodeValue());
			}
			m_parXml.ParseResult(XML_ROOT, -1, uiRoot);
		}
		
		// page
		NodeList nodes = rootElement.getChildNodes();//.getElementsByTagName(XmlCtrlHeader.CCC_PAGE);
		if(null == nodes)
			return false;
		
		for(int index = 0; index < nodes.getLength(); index++)
		{
			Node node=nodes.item(index);
			if(node.getNodeType() == Node.ELEMENT_NODE)
			{
				if(node.getNodeName().equalsIgnoreCase(XmlCtrlHeader.CCC_PAGE))
				{
					if(!ParseXmlPage(node))
						return false;
				}
				else if(node.getNodeName().equalsIgnoreCase(XmlCtrlHeader.CCC_POPUP_PAGE))
				{
					if(!ParseXmlPopupPage(node))
						return false;
				}
			}
		}
		return false;
	}
	
	/*
	 * 解析用户界面XML文件
	 * @param xmlInputStream XML文件流
	 * @return 是否解析成功
	 */
	private boolean parseXmlStream(InputStream isXml)
	{
		boolean bRetValue=true;		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try 
		{
			DocumentBuilder docBuilder = factory.newDocumentBuilder();
			Document document = docBuilder.parse(isXml);
			
			if(document!=null)
			{
				Element rootElement=document.getDocumentElement();
				if(rootElement!=null)
				{	
					parseXmlRootElement(rootElement);
				}
			}
			//xmlInputStream.close();
		} 
		//catch (ParserConfigurationException e){} 
		//catch (SAXException e) {}
		//catch (IOException e) {}
		catch(Exception ex){}
		return bRetValue;
	}
	
	public boolean OpenFile(InputStream isXml, IXmlParser parXml)
	{
		if(null == isXml)
			return false;
		
		this.m_parXml = parXml;
		return parseXmlStream(isXml);
	}
	
	// 页节点     Slider控件子节点 
	private boolean ParseXmlProgress(UiPage xmlPage, Node progressNode)
	{
		if(null == xmlPage && null == progressNode)
			return false;
		
		NamedNodeMap nameMap = progressNode.getAttributes();
		if(null == nameMap)
			return false;
		
		int idPage = xmlPage.id;
		UiProgress ctrlProgress = new UiProgress();
		Rect rect = new Rect(); 
		Node attrNode; 
		for(int k = 0; k < nameMap.getLength(); ++k)
		{
			attrNode = nameMap.item(k);
			
			if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrProgress.ID))
				ctrlProgress.id = Integer.parseInt(attrNode.getNodeValue());
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrProgress.Z_INDEX))
				ctrlProgress.z_index = Integer.parseInt(attrNode.getNodeValue());
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrProgress.GEOMETRY))
			{
				if(!ParseGemometry(attrNode.getNodeValue(), rect))
					return false;
				
				ctrlProgress.x      = rect.left;
				ctrlProgress.y      = rect.top;
				ctrlProgress.width  = rect.right;
				ctrlProgress.height = rect.bottom;
			}
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrProgress.VISIBLE))
				ctrlProgress.visible = ParseBooleanValue(attrNode.getNodeValue());
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrImage.ENABLE))
				ctrlProgress.enable = ParseBooleanValue(attrNode.getNodeValue());
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrProgress.ORIENTATION))
				ctrlProgress.orientation = Integer.parseInt(attrNode.getNodeValue());
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrProgress.VALUE))
				ctrlProgress.value = Integer.parseInt(attrNode.getNodeValue());
			
		}
		ctrlProgress.idPage = idPage;
		xmlPage.AddXmlNone((UiBase)ctrlProgress);
		return true;
	}
	
	// 页节点     图像控件子节点
	private boolean ParseXmlCheckBox(UiPage xmlPage, Node checkBoxNode)
	{
		if(null == xmlPage && null == checkBoxNode)
			return false;
		
		int idPage = xmlPage.id;
		NamedNodeMap nameMap = checkBoxNode.getAttributes();
		if(null == nameMap)
			return false;
		
		UiCheckBox uiCheckBox = new UiCheckBox();
		Rect rect = new Rect(); 
		Node attrNode; 
		for(int k = 0; k < nameMap.getLength(); ++k)
		{
			attrNode = nameMap.item(k);
			if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrRadio.ID))
				uiCheckBox.id = Integer.parseInt(attrNode.getNodeValue());
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrRadio.Z_INDEX))
				uiCheckBox.z_index = Integer.parseInt(attrNode.getNodeValue());
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrRadio.GEOMETRY))
			{
				if(!ParseGemometry(attrNode.getNodeValue(), rect))
					return false;
				
				uiCheckBox.x      = rect.left;
				uiCheckBox.y      = rect.top;
				uiCheckBox.width  = rect.right;
				uiCheckBox.height = rect.bottom;
			}
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrRadio.VISIBLE))
				uiCheckBox.visible = ParseBooleanValue(attrNode.getNodeValue());
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrRadio.ENABLE))
				uiCheckBox.enable = ParseBooleanValue(attrNode.getNodeValue());
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrRadio.TEXT))
				uiCheckBox.text = attrNode.getNodeValue();
			
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrRadio.BKIMAGE))
				uiCheckBox.styleNormal.bkImage = attrNode.getNodeValue();
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrRadio.FOREGROUNDCOLOR))
				uiCheckBox.styleNormal.colorForeground = ParseColor(attrNode.getNodeValue());
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrRadio.BACKGROUNDCOLOR))
				uiCheckBox.styleNormal.colorBackground = ParseColor(attrNode.getNodeValue());
			
			// normal font
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrRadio.FONT))
				uiCheckBox.fontNormal.name = ParseFontName(attrNode.getNodeValue());
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrRadio.FONTSIZE))
				uiCheckBox.fontNormal.size = Integer.parseInt(attrNode.getNodeValue());
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrRadio.FONTBOLD))
				uiCheckBox.fontNormal.bold = (0 == Integer.parseInt(attrNode.getNodeValue())) ? false:true;	
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrRadio.FONTITALIC))
				uiCheckBox.fontNormal.italic = (0 == Integer.parseInt(attrNode.getNodeValue())) ? false:true;
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrRadio.FONTUNDERLINE))
				uiCheckBox.fontNormal.underLine = (0 == Integer.parseInt(attrNode.getNodeValue())) ? false:true;
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrRadio.FONTSTRIKEOUT))
				uiCheckBox.fontNormal.strikeOut = (0 == Integer.parseInt(attrNode.getNodeValue())) ? false:true;
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrRadio.TEXTALIGN))
				uiCheckBox.fontNormal.textAlign = Integer.parseInt(attrNode.getNodeValue());
			
			else if(attrNode.getNodeName().equalsIgnoreCase(XmlAttrRadio.CHECKED))
				uiCheckBox.checked = ParseBooleanValue(attrNode.getNodeValue());			
		}
		uiCheckBox.idPage = idPage;
		xmlPage.AddXmlNone((UiBase)uiCheckBox);
		
		// event		
		return true;
	}
}
