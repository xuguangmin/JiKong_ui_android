package phlx.jikong.ccc_ui;

import android.os.Handler;
import android.os.Message;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.Paint.Align;
import android.view.Surface;
import android.view.View;
import phlx.jikong.ccc_ui.wnd.*;
import phlx.jikong.ccc_ui.ui.*;
/*import phlx.jikong.video.Player;*/

public class CCC_UI extends WndPage 
                    implements UiPage.Callback, WndBase.Callback,
                               WndVideo.IWndVideo,
                               Handler.Callback
{
	private static final int MSG_VIDEO_PLAY       = 1;
	private static final int MSG_VIDEO_STOP       = 2;
	private static final int MSG_SHOW_PROGRESS    = 3;
	private static final int MSG_SURFACE_SIZE     = 4;
	
	private ICCC_UI           m_iCCC_UI    = null;        // 对外接口
	private UiProject         m_uiProject  = null;
	private int               m_curPage    = -1;
	private boolean           m_b_showHow  = false;
	private DelayTask         m_delayTask  = new DelayTask();
	
	private int               m_wndWidth   = 0;          // 窗口宽
	private int               m_wndHeight  = 0;          // 窗口高
	private int               m_uiPageWidth   = 0;       // UI提供的窗口宽
	private int               m_uiPageHeight  = 0;       // UI提供的窗口高
	
	//20150108为了让程序能更好的跑起来,把与视屏音屏相关的代码注释掉   lxs
	/*private Player            m_player = new Player();*/
	private Handler           m_playerHandler = null;
	private WndVideo          m_wndVideo = null;
	private String            m_strMovie = null;
	private boolean           m_hasVideo = false;
	              
			
	public CCC_UI(Context context)
	{
		super(context);
		m_uiProject  = new UiProject();
		m_delayTask = new DelayTask();
		WndManager.Initial(context);
		
		//20150108为了让程序能更好的跑起来,把与视屏音屏相关的代码注释掉  lxs
	/*	m_player.Initial(this);*/
	}	
	
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) 
	{
		super.onSizeChanged(w, h, oldw, oldh);
		System.out.printf("------------ %s onSizeChanged() w = %d h = %d\n", this.getClass().toString(), w, h);
		
		m_wndWidth  = w;
		m_wndHeight = h;
	}
	// WndPage override	
	@Override
	protected void onCustomSlide(boolean b_left) 
	{
		int idPage = this.m_uiProject.GetSlidePage(b_left);
		if(idPage > 0) 
		{
			ExecuteJumpPage(idPage);		
		}
	}
	// WndPage override
	@Override
	protected void onCustomClick() 
	{
		int idPage = this.m_uiProject.GetJumpPage();
		if(idPage > 0) 
		{
			ExecuteJumpPage(idPage);		
		}
	}
	@Override
	public void wndSurfaceChanged(Surface surface) 
	{
		//20150108为了让程序能更好的跑起来,把与视屏音屏相关的代码注释掉   lxs
		//m_player.attachSurface(surface);	
	}
	@Override
	public void wndSurfaceDestroyed() 
	{
		////20150108为了让程序能更好的跑起来,把与视屏音屏相关的代码注释掉   lxs
	//	m_player.detachSurface();
	}
	@Override
	public void PlayAction(int action) {
		switch(action)
		{
		case WndVideo.ACTION_PLAY_PAUSE:
			////20150108为了让程序能更好的跑起来,把与视屏音屏相关的代码注释掉   lxs
			//m_player.PlayPause();
			break;
		case WndVideo.ACTION_OVERLAY_SHOW:
			m_playerHandler.sendEmptyMessage(MSG_SHOW_PROGRESS);
			break;
		case WndVideo.ACTION_OVERLAY_HIDE:
			m_playerHandler.removeMessages(MSG_SHOW_PROGRESS);
			break;
		}
	}
	
	@Override
	public boolean handleMessage(Message msg) {
		switch(msg.what)
		{
		case MSG_VIDEO_PLAY:
			{
				if(m_wndVideo != null && m_strMovie != null)
				{
					//20150108为了让程序能更好的跑起来,把与视屏音屏相关的代码注释掉   lxs
				//	m_player.Play(m_strMovie);
			        m_wndVideo.SetTitle(m_strMovie);
				}
			}
			return true;
			
		case MSG_SHOW_PROGRESS:
			////20150108为了让程序能更好的跑起来,把与视屏音屏相关的代码注释掉   lxs
			//int pos = m_wndVideo.setOverlayProgress(m_player.GetTime(), m_player.GetLength());			
		//	msg = m_playerHandler.obtainMessage(MSG_SHOW_PROGRESS);
        //    m_playerHandler.sendMessageDelayed(msg, 1000 - (pos % 1000));
			return true;
		case MSG_SURFACE_SIZE:
			m_wndVideo.SetSize(msg.arg1, msg.arg2);
			return true;
		}
		return false;
	}


	
	////20150108为了让程序能更好的跑起来,把与视屏音屏相关的代码注释掉   lxs
	/*public void setSurfaceSize(int width, int height) 
	{
		Message msg = m_playerHandler.obtainMessage(MSG_SURFACE_SIZE);
        msg.arg1 = width;
        msg.arg2 = height;
        m_playerHandler.sendMessage(msg);	
	}*/
	////20150108为了让程序能更好的跑起来,把与视屏音屏相关的代码注释掉   lxs
	private double GetScaleX()  
	{
		if(m_uiPageWidth <= 0)
			return 0;
		return (double)m_wndWidth/m_uiPageWidth;
	}
	////20150108为了让程序能更好的跑起来,把与视屏音屏相关的代码注释掉   lxs
/*	private double GetScaleY()  
	{
		if(m_uiPageHeight <= 0)
			return 0;
		return (double)m_wndHeight/m_uiPageHeight;
	}*/
	
	//20150108为了让程序能更好的跑起来,把与视屏音屏相关的代码注释掉   lxs
	private void PrintLog(String strLog)
	{
		System.out.printf("%s :%s\n", this.getClass().toString(), strLog);	
	}
	
	private int CalcuSampleSize(double scaleX, double scaleY)
	{
		double temp = (scaleX > scaleY) ? scaleX:scaleY;
		if(temp > 0.5)
			return 1;
		
		int sample = (int)(100/(100 * temp));
		return ((sample/2) * 2);
	}
				
	private void ExecuteAction(UiActionGroup actionGroup)
	{
		if(actionGroup == null)
			return;
		
		UiAction uiAction;
		for(int k = 0; k < actionGroup.m_xmlActionList.size(); ++k)
		{
			uiAction = actionGroup.m_xmlActionList.get(k);
			switch(uiAction.action)
			{
			case UiAction.ACTION_HIDE:
				m_uiProject.SetControlVisible(uiAction.targetId, false);
				break;
			case UiAction.ACTION_SHOW:
				m_uiProject.SetControlVisible(uiAction.targetId, true);
				break;
				
			case UiAction.ACTION_ENABLE:
				m_uiProject.SetControlEnable(uiAction.targetId, true);
				break;
			case UiAction.ACTION_DISABLE:
				m_uiProject.SetControlEnable(uiAction.targetId, false);
				break;
				
			case UiAction.ACTION_INVOKE_APP:
				m_iCCC_UI.ccc_ui_invoke_app(uiAction.parameter);
				break;
			}
		}
	}
	private void ExecuteJumpPage(int idPage)
	{	
		if(idPage <= 0)
			return;
		
		this.LoadUiPage(idPage, m_b_showHow);
	}
	
	private void ProcessUiEvent(int ctrlId, int ctrlEvent, int value, 
			                    int jumpPage, UiActionGroup actionGroup)
	{
		m_iCCC_UI.ccc_ui_event(ctrlId, ctrlEvent, value);
		
		if(jumpPage > 0)        this.ExecuteJumpPage(jumpPage);
		if(actionGroup != null) this.ExecuteAction(actionGroup);		
	}

	public void XmlUiEvent(int ctrlId, int ctrlEvent, int value, boolean bWarning,
            String strWarning, int jumpPage, UiActionGroup actionGroup) 
	{
		if(bWarning)
		{
			/*
			 * 有警告的，需要用户确认后，才会执行 
			 */
			m_delayTask.Save(ctrlId, ctrlEvent, value, jumpPage, actionGroup);
			m_iCCC_UI.ccc_ui_warning(strWarning);
			return;
		}
	
		ProcessUiEvent(ctrlId, ctrlEvent, value, jumpPage, actionGroup);
	}
	protected void XmlUiEvent(int ctrlId, int ctrlEvent, boolean bWarning, String strWarning, 
								int jumpPage, UiActionGroup actionGroup)
	{
		XmlUiEvent(ctrlId, ctrlEvent, -1, bWarning, strWarning, jumpPage, actionGroup);
	}
	protected void XmlUiEvent(int ctrlId, int ctrlEvent, int value)
	{
		XmlUiEvent(ctrlId, ctrlEvent, value, false, null, -1, null);
	}
	protected void XmlUiEvent(int ctrlId, int ctrlEvent)
	{
		XmlUiEvent(ctrlId, ctrlEvent, -1);	
	}
	
	/* ******************  接口 UiPage.Callback  ****************** */
	@Override
	public void UiPageAttr(int id, String bkImage, int bkColor, int width, int height, boolean bPopup) 
	{
		m_curPage = bPopup ? -1:id;
		m_uiPageWidth  = width;
		m_uiPageHeight = height;
		SkinManager.SetSampleSize(CalcuSampleSize(GetScaleX(), getScaleY()));
		
		this.SetBkColor(bkColor);
		this.SetBkImage(SkinManager.GetBitmap(bkImage));
	}
	@Override
	public void EnumerateUiBase(UiBase uib) 
	{
		CreatePageChilds(uib, GetScaleX(), getScaleY());		
	}
	@Override
	public boolean UpdateUiBaseProperty(int property, UiBase uiBase) 
	{
		return SetPageChildProperty(property, uiBase);
	}
		
	/* ******************  接口 WndBase.Callback  ****************** */
	@Override
	public void WndEvent(int wndType, int event, int eventExtra, Object uiBase) 
	{
		UiBase uib = (UiBase)uiBase;
		int ctrlId = uib.id;
		
		switch(wndType)
		{
		case WndBase.WND_TYPE_BUTTON:
			{
				switch(event)
				{
				case WndBase.WND_EVENT_PRESSED:
					XmlUiEvent(ctrlId, UiEventEnum.EVENT_PRESSED_BUTTON);
					break;
				case WndBase.WND_EVENT_RELEASED:
					XmlUiEvent(ctrlId, UiEventEnum.EVENT_RELEASED_BUTTON);
					break;
					
				case WndBase.WND_EVENT_CLICK:
					UiButton uibtn = (UiButton)uib;
					XmlUiEvent(ctrlId, UiEventEnum.EVENT_CLICK_BUTTON, uibtn.warning > 0 ? true:false, uibtn.warningText, uibtn.jumpPage, uibtn.agClick);
					break;
				}
			}
			break;
		case WndBase.WND_TYPE_IMAGE:
			{
				switch(event)
				{
				case WndBase.WND_EVENT_PRESSED:
					XmlUiEvent(ctrlId, UiEventEnum.EVENT_PRESSED_IMAGE);
					break;
				case WndBase.WND_EVENT_RELEASED:
					XmlUiEvent(ctrlId, UiEventEnum.EVENT_RELEASED_IMAGE);
					break;
					
				case WndBase.WND_EVENT_CLICK:
					UiImage uiImage = (UiImage)uib;
					XmlUiEvent(ctrlId, UiEventEnum.EVENT_CLICK_IMAGE, uiImage.warning > 0 ? true:false, uiImage.warningText, uiImage.jumpPage, uiImage.agClick);
					break;
				}
			}
			break;
		case WndBase.WND_TYPE_CHECKBOX:
			{
				switch(event)
				{
				case WndBase.WND_EVENT_CHECK_STATE:
					XmlUiEvent(ctrlId, UiEventEnum.CheckBoxStateChanged, eventExtra);
					break;
				}
			}
			break;
		case WndBase.WND_TYPE_RADIO:
			{
				switch(event)
				{
				case WndBase.WND_EVENT_CHECK_STATE:
					XmlUiEvent(ctrlId, UiEventEnum.RadioBoxStateChanged, eventExtra);
					break;
				}
			}
			break;
		case WndBase.WND_TYPE_SLIDER:
			{
				switch(event)
				{
				case WndBase.WND_EVENT_VALUE_CHANGE:
					XmlUiEvent(ctrlId, UiEventEnum.SliderValueChange, eventExtra);
					((UiSlider)uib).percent = eventExtra;
					break;
				}
			}
			break;
		}
		
	}
	
	private int UiToWndType(int uiType)
	{
		switch(uiType)
		{
		case UiBase.XML_BUTTON   :return WndBase.WND_TYPE_BUTTON;
		case UiBase.XML_IMAGE    :return WndBase.WND_TYPE_IMAGE;		
		case UiBase.XML_LABEL    :return WndBase.WND_TYPE_LABEL;
		case UiBase.XML_SLIDER   :return WndBase.WND_TYPE_SLIDER;
		case UiBase.XML_CHECKBOX :return WndBase.WND_TYPE_CHECKBOX;
		case UiBase.XML_RADIO    :return WndBase.WND_TYPE_RADIO;
		case UiBase.XML_PROGRESS :return WndBase.WND_TYPE_PROGRESS;
		case UiBase.XML_VIDEO    :return WndBase.WND_TYPE_VIDEO;
		}
		return WndBase.WND_TYPE_UNKNOWN;
	}
	protected Typeface CreateFont(int uiFont)
	{
		switch(uiFont)
		{
		case UiFont.FONT_NAME_HEI    :return Typeface.DEFAULT_BOLD;
		case UiFont.FONT_NAME_SONG   :return Typeface.SANS_SERIF;
		//case UiFont.FONT_NAME_KAISHU :return Typeface.SANS_SERIF;
		}
		return Typeface.DEFAULT;    
	}
	protected Align AlignXmlToH(int align)
	{
		switch(align)
		{
		case UiFont.ALIGN_LEFT_TOP:
		case UiFont.ALIGN_LEFT_MIDDLE:
		case UiFont.ALIGN_LEFT_BOTTOM:
			return Align.LEFT;
		case UiFont.ALIGN_RIGHT_TOP:
		case UiFont.ALIGN_RIGHT_MIDDLE:
		case UiFont.ALIGN_RIGHT_BOTTOM:
			return Align.RIGHT;
		}
		return Align.CENTER;    
	}
	protected int AlignXmlToV(int align)
	{
		switch(align)
		{
		case UiFont.ALIGN_LEFT_TOP:
		case UiFont.ALIGN_RIGHT_TOP:
		case UiFont.ALIGN_CENTER_TOP:
			return WndBase.ALIGN_V_UP;
		
		case UiFont.ALIGN_LEFT_BOTTOM:
		case UiFont.ALIGN_RIGHT_BOTTOM:
		case UiFont.ALIGN_CENTER_BOTTOM:
			return WndBase.ALIGN_V_DOWN;
		}
		return WndBase.ALIGN_V_MIDDLE;    
	}
	
	private void UpdateWndSize(View v, UiBase ui, double scaleX, double scaleY)
	{
		if(scaleX <= 0 || scaleY <= 0)
		{
			System.out.printf("CalcuScale() scaleX <= 0 || scaleY <= 0 \n");
			return;
		}
				
		UpdateChildSize(v, (int)(scaleX * ui.x), (int)(scaleY * ui.y), (int)(scaleX * ui.width), (int)(scaleY * ui.height));
	}
	
	private boolean SetPageChildProperty(int property, UiBase uiBase)
	{
		WndBase wndb  = WndManager.GetView(uiBase);
		if(null == wndb)
		{
			System.out.println("SetPageChildProperty WndBase == NULL");
			return false;
		}
		
		switch(property)
		{
		case UiPropertyEnum.ENABLE:
			wndb.SetEnable(uiBase.enable);
			return true;
		case UiPropertyEnum.VISIBLE:
			wndb.SetVisible(uiBase.visible);
			return true;
		}
		
		switch(uiBase.GetUiType())
		{
		case UiBase.XML_CHECKBOX:
			{	
				switch(property)
				{
				case UiPropertyEnum.CHECKED:
					((WndCheckBox)wndb).setChecked(((UiCheckBox)uiBase).checked);
					break;
				}			
			}
			break;
		case UiBase.XML_RADIO:
			{	
				switch(property)
				{
				case UiPropertyEnum.CHECKED:
					((WndRadio)wndb).setChecked(((UiRadio)uiBase).checked);
					break;
				}	
			}
			break;
		case UiBase.XML_PROGRESS:
			{	
				switch(property)
				{
				case UiPropertyEnum.CHANGED:
					((WndProgress)wndb).SetProgress(((UiProgress)uiBase).value);
					break;
				}				
			}
			break;
		case UiBase.XML_SLIDER:
			{		
				switch(property)
				{
				case UiPropertyEnum.CHANGED:
					((WndSlider)wndb).SetProgress(((UiSlider)uiBase).percent);
					break;
				}			
			}
			break;
		}
		return false;
	}
	private void CreatePageChilds(UiBase uib, double scaleX, double scaleY)
	{
		int    uiType = uib.GetUiType();		
		WndBase wndb  = WndManager.CreateView(UiToWndType(uiType), uib);
		if(null == wndb)
		{
			System.out.println("CreatePageChilds WndBase == NULL");
			return;
		}
		
		wndb.addCallback(this, uib);
		wndb.SetVisible(uib.visible);
		wndb.SetEnable(uib.enable);
		
		switch(uiType)
		{
		case UiBase.XML_BUTTON:
			{
				WndButton wndButton = (WndButton)wndb;
				UiButton ui = (UiButton)uib;
				wndButton.SetDrawText(ui.text);
				//wndButton.SetSize(x, y, width, height);
						
				// normal
				wndButton.SetFontNormal(CreateFont(ui.fontNormal.name));
				// -- wndButton.SetTextSizeNormal(ui.fontNormal.size);
				wndButton.SetTextAttrNormal(ui.fontNormal.bold, ui.fontNormal.underLine, ui.fontNormal.strikeOut, ui.fontNormal.italic);
				wndButton.SetTextAlignNormal(AlignXmlToH(ui.fontNormal.textAlign), AlignXmlToV(ui.fontNormal.textAlign));
				wndButton.SetStyleNormal(ui.styleNormal.colorForeground, ui.styleNormal.colorBackground, SkinManager.GetBitmap(ui.styleNormal.bkImage));
				
				// pressed
				wndButton.SetFontPressed(CreateFont(ui.fontPressed.name));
				// -- wndButton.SetTextSizePressed(ui.fontPressed.size);
				wndButton.SetTextAttrPressed(ui.fontPressed.bold, ui.fontPressed.underLine, ui.fontPressed.strikeOut, ui.fontPressed.italic);
				wndButton.SetTextAlignPressed(AlignXmlToH(ui.fontPressed.textAlign), AlignXmlToV(ui.fontPressed.textAlign));
				wndButton.SetStylePressed(ui.stylePressed.colorForeground, ui.stylePressed.colorBackground, SkinManager.GetBitmap(ui.stylePressed.bkImage));
				
				wndButton.SetTextSizeNormal((int)(ui.fontNormal.size * scaleY));
				wndButton.SetTextSizePressed((int)(ui.fontPressed.size * scaleY));
			}
			break;
		case UiBase.XML_IMAGE:
			{
				WndImage wndImage = (WndImage)wndb;
				UiImage ui = (UiImage)uib;
				
				wndImage.SetDrawText(ui.text);	
				//wndImage.SetSize(x, y, width, height);
				
				// normal
				wndImage.SetFontNormal(CreateFont(ui.fontNormal.name));
				// -- wndImage.SetTextSizeNormal(ui.fontNormal.size);
				wndImage.SetTextAttrNormal(ui.fontNormal.bold, ui.fontNormal.underLine, ui.fontNormal.strikeOut, ui.fontNormal.italic);
				wndImage.SetTextAlignNormal(AlignXmlToH(ui.fontNormal.textAlign), AlignXmlToV(ui.fontNormal.textAlign));
				wndImage.SetStyleNormal(ui.styleNormal.colorForeground, ui.styleNormal.colorBackground, 
						                SkinManager.GetBitmap(ui.styleNormal.bkImage), ui.styleNormal.imageStyle);	
				// pressed
				wndImage.SetFontPressed(CreateFont(ui.fontPressed.name));
				// -- wndImage.SetTextSizePressed(ui.fontPressed.size);
				wndImage.SetTextAttrPressed(ui.fontPressed.bold, ui.fontPressed.underLine, ui.fontPressed.strikeOut, ui.fontPressed.italic);
				wndImage.SetTextAlignPressed(AlignXmlToH(ui.fontPressed.textAlign), AlignXmlToV(ui.fontPressed.textAlign));
				wndImage.SetStylePressed(ui.stylePressed.colorForeground, ui.stylePressed.colorBackground, 
						                 SkinManager.GetBitmap(ui.stylePressed.bkImage), ui.stylePressed.imageStyle);
				
				wndImage.SetTextSizeNormal((int)(ui.fontNormal.size * scaleY));
				wndImage.SetTextSizePressed((int)(ui.fontPressed.size * scaleY));
			}
			break;
		case UiBase.XML_CHECKBOX:
			{
				WndCheckBox wndCheckBox = (WndCheckBox)wndb;
				UiCheckBox ui = (UiCheckBox)uib;
				
				wndCheckBox.SetDrawText(ui.text);
				wndCheckBox.setChecked(ui.checked);
				
				// normal
				wndCheckBox.SetStyleNormal(ui.styleNormal.colorForeground, ui.styleNormal.colorBackground);
				wndCheckBox.SetFontNormal(CreateFont(ui.fontNormal.name));
				// -- wndCheckBox.SetTextSizeNormal(ui.fontNormal.size);
				wndCheckBox.SetTextAttrNormal(ui.fontNormal.bold, ui.fontNormal.underLine, ui.fontNormal.strikeOut, ui.fontNormal.italic);
			
				wndCheckBox.SetTextSizeNormal((int)(ui.fontNormal.size * scaleY));
			}
			break;
		case UiBase.XML_RADIO:
			{
				WndRadio wndRadio = (WndRadio)wndb;
				UiRadio ui = (UiRadio)uib;
				
				wndRadio.setChecked(ui.checked);
				wndRadio.SetDrawText(ui.text);
				
				// normal
				wndRadio.SetStyleNormal(ui.styleNormal.colorForeground, ui.styleNormal.colorBackground);
				wndRadio.SetFontNormal(CreateFont(ui.fontNormal.name));
				// -- wndRadio.SetTextSizeNormal(ui.fontNormal.size);
				wndRadio.SetTextAttrNormal(ui.fontNormal.bold, ui.fontNormal.underLine, ui.fontNormal.strikeOut, ui.fontNormal.italic);
			
				wndRadio.SetTextSizeNormal((int)(ui.fontNormal.size * scaleX));
			}
			break;
		case UiBase.XML_LABEL:
			{
				WndLabel wndLabel = (WndLabel)wndb;
				UiLabel ui = (UiLabel)uib;
				
				wndLabel.SetDrawText(ui.text);
				//wndLabel.SetSize(x, y, width, height);
				
				// normal
				wndLabel.SetStyleNormal(ui.styleNormal.colorForeground, ui.styleNormal.colorBackground, SkinManager.GetBitmap(ui.styleNormal.bkImage));
				wndLabel.SetFontNormal(CreateFont(ui.fontNormal.name));
				wndLabel.SetTextSizeNormal(ui.fontNormal.size);
				wndLabel.SetTextAttrNormal(ui.fontNormal.bold, ui.fontNormal.underLine, ui.fontNormal.strikeOut, ui.fontNormal.italic);
				wndLabel.SetTextAlignNormal(AlignXmlToH(ui.fontNormal.textAlign), AlignXmlToV(ui.fontNormal.textAlign));
				
				wndLabel.SetTextSizeNormal((int)(ui.fontNormal.size * scaleY));
			}
			break;
		case UiBase.XML_PROGRESS:
			{
				WndProgress wndProgress = (WndProgress)wndb;
				UiProgress ui = (UiProgress)uib;
				
				wndProgress.SetOrientation(true);// 目前并无这个属性，所以固定(0==orientation)?true:false);
				wndProgress.SetProgress(ui.value);
			}
			break;
		case UiBase.XML_SLIDER:
			{
				WndSlider wnds = (WndSlider)wndb;
				UiSlider ui = (UiSlider)uib;
				
				wnds.SetProgress(ui.percent);
				wnds.SetProgressShow((0 == ui.percentShow)?false:true);
				// -- wnds.SetBarSize(ui.barWidth, ui.barHeight);
				// -- wnds.SetSlipperSize(ui.slipperWidth, ui.slipperHeight);
				wnds.SetOrientation((0==ui.orientation)?true:false);
						
				wnds.SetStyleSlipper(ui.styleSlipper.colorBackground, SkinManager.GetBitmap(ui.styleSlipper.bkImage));
				wnds.SetStyleBar(ui.styleBar.colorBackground, SkinManager.GetBitmap(ui.styleBar.bkImage));
				
				wnds.SetBarSize((int)(scaleX * ui.barWidth), (int)(scaleY * ui.barHeight));
				wnds.SetSlipperSize((int)(scaleX * ui.slipperWidth), (int)(scaleY * ui.slipperHeight));
			}
			break;
		case UiBase.XML_VIDEO:
			{
				WndVideo wndVideo = (WndVideo)wndb;
				UiVideo ui = (UiVideo)uib;
				
				wndVideo.SetVideoListener(this);		
				m_wndVideo = wndVideo;
				m_strMovie = ui.movie;
				m_hasVideo = true;
			}
			break;
		}
								
		this.add(wndb);
		UpdateWndSize(wndb.GetOwner(), uib, scaleX, scaleY);
		
		///System.out.printf("CreatePageChilds k = %d size =%d\n", k, m_childUiList.size());
		
	}
		
	public void ExecuteDelayTask()
	{
		if(m_delayTask.b_enable)
		{
			ProcessUiEvent(m_delayTask.ctrlId, 
					       m_delayTask.ctrlEvent, 
					       m_delayTask.value,
					       m_delayTask.jumpPage,
					       m_delayTask.actionGroup);	
			m_delayTask.b_enable = false;
		}	
	}
	
	// 显示或者隐藏弹出页
	public boolean InvokePage(int idPage, boolean b_show)
	{
		if(!b_show) idPage = m_curPage;
		return LoadUiPage(idPage, m_b_showHow);	
	}
		
	// 从外部设置控件的属性
	public void SetControlProperty(int ctrlId, int property, int value)
	{
		this.m_uiProject.SetControlProperty(ctrlId, property, value);
	}
	
	private void Clear()
	{				
		removeAllViews();
		this.m_uiProject.Clear();
		WndManager.Clear();
		SkinManager.ClearAll();
	}
		
	// 选择要显示的页界面
	private boolean LoadUiPage(int idPage, boolean b_showHow)
	{					 
		// 装载前清理
		if(m_hasVideo)
		{
		/*	m_player.Stop();
			m_hasVideo = false;*/
		}
		this.SetBkImage(null);
		this.removeAllViews();
		WndManager.ResetAllWnd();
		SkinManager.ResetAllBitmap();
		
		// 设置页控件属性
		this.SetShowHowMode(b_showHow);
		boolean b_load = m_uiProject.GetPageChilds(idPage, this);
		
		
		WndManager.ClearIdleWnd();
		SkinManager.ClearIdleBitmap();
		
		if(b_load) 
		{
			XmlUiEvent(idPage, UiEventEnum.PageShow, 0, false, null, -1, null);
			m_iCCC_UI.ccc_ui_load_info(idPage);
		}
		
		if(m_hasVideo) m_playerHandler.sendEmptyMessageDelayed(MSG_VIDEO_PLAY, 1000);
		return b_load;
	}
	
	/*
	 * 装载界面
	 * 
	 * 参数说明：
	 *          strZipFileName 界面zip文件
	 *          pageFirst      指定要显示的页面。否则将显示Zip文件中指定的首页
	 *          b_showHow      是否是演示模式
     */
	public boolean LoadUI(String strZipFileName, boolean b_showHow, int pageFirst)
	{
		Clear();
		if(null == strZipFileName)
			return false;
		
		// 打开zip文件
		if(!SkinManager.OpenZipFile(strZipFileName))
		{
			PrintLog(String.format("Open zip failed :%s\n", strZipFileName));
			return false;
		}
		
		// 解析XML流
		if(!m_uiProject.OpenFile(SkinManager.GetXmlInputStream()))
			return false;
		
		m_b_showHow = b_showHow;
		return LoadUiPage(pageFirst, b_showHow);
	}
	
	public boolean Initial(ICCC_UI ifCM)
	{
		m_iCCC_UI = ifCM;
		m_playerHandler = new Handler(this);
		return true;
	}
}
