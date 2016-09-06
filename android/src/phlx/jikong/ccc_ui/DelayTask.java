package phlx.jikong.ccc_ui;

import phlx.jikong.ccc_ui.ui.UiActionGroup;

/* 
 * 需要确认后才会执行的任务
 * 如果有的控件的任务进来，则前一个会失效
 * 目前，警告信息只放到单击事件中处理
 */

public class DelayTask 
{
	public int ctrlId;
	public int ctrlEvent;
	public int value;
	public int jumpPage;
	public UiActionGroup actionGroup;
	public boolean b_enable = false;
	
	public void Save(int ctrlId, int ctrlEvent, int value, int jumpPage, UiActionGroup actionGroup)
	{
		this.ctrlId      = ctrlId;
		this.ctrlEvent   = ctrlEvent;
		this.value       = value;
		this.jumpPage    = jumpPage;
		this.actionGroup = actionGroup;
		this.b_enable    = true;
	}
}

/*
public class DelayTask 
{
	public class UiEvent 
	{
		public int ctrlId;
		public int ctrlEvent;
		public int value;
		public int popupPage;
		public UiActionGroup actionGroup;
		
		public void SetUiEvent(int ctrlId, int ctrlEvent, int value, int popupPage, UiActionGroup actionGroup)
		{
			this.ctrlId = ctrlId;
			this.ctrlEvent = ctrlEvent;
			this.value = value;
			this.popupPage = popupPage;
			this.actionGroup = actionGroup;
		}
	}
	
	private int ctrlId = -1;
	private List<UiEvent> m_uiEventList = null;
	public DelayTask()
	{
		m_uiEventList = new ArrayList<UiEvent>();
	}
	
	public void AddNewEvent(int ctrlId, int ctrlEvent, int value, int popupPage, UiActionGroup actionGroup)
	{
		if(ctrlId != this.ctrlId) m_uiEventList.clear();
		
		UiEvent uie = new UiEvent();
		uie.SetUiEvent(ctrlId, ctrlEvent, value, popupPage, actionGroup);
		m_uiEventList.add(uie);
		this.ctrlId = ctrlId;
	}
	
	public void ClearUiEvent()
	{
		m_uiEventList.clear();
	}
}
*/
