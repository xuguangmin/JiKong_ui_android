package phlx.jikong.ccc_ui;

import phlx.jikong.ccc_ui.ui.UiActionGroup;

/* 
 * ��Ҫȷ�Ϻ�Ż�ִ�е�����
 * ����еĿؼ��������������ǰһ����ʧЧ
 * Ŀǰ��������Ϣֻ�ŵ������¼��д���
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
