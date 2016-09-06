package phlx.jikong.ccc_ui.wnd;

import android.view.View;

//�ýӿ���Ϊ�ؼ��Ļ���
public interface WndBase 
{
	// �ؼ�����
	public static final int WND_TYPE_UNKNOWN         = 0;
	public static final int WND_TYPE_BUTTON          = 1;
	public static final int WND_TYPE_IMAGE           = 2;
	public static final int WND_TYPE_LABEL           = 3;
	public static final int WND_TYPE_SLIDER          = 4;
	public static final int WND_TYPE_CHECKBOX        = 5;
	public static final int WND_TYPE_RADIO           = 6;
	public static final int WND_TYPE_PROGRESS        = 7;
	public static final int WND_TYPE_VIDEO           = 8;
	
	// �¼�����
	public static final int WND_EVENT_CLICK          = 1;
	public static final int WND_EVENT_PRESSED        = 2;
	public static final int WND_EVENT_RELEASED       = 3;
	public static final int WND_EVENT_DBLCLICK       = 4;
	public static final int WND_EVENT_VALUE_CHANGE   = 5;      // ����ֵ�仯����Я��һ������
	public static final int WND_EVENT_CHECK_STATE    = 6;      // checkbox radioѡ��״̬��Я������ 0 1
	
		
	public interface Callback 
	{
		/*
		 *  wndType      �ؼ�����
		 *  event        �¼�ID
		 *  eventExtra   �¼�Я�������ݡ���ͬ���¼����в�ͬ�����ֵ 
		 *  uiBase       
		 */
		void WndEvent(int wndType, int event, int eventExtra, Object uiBase);
	}
	
	public void addCallback(Callback callback, Object uiBase);
		
	
	
	public static final int ALIGN_V_UP     = 1; // ��
	public static final int ALIGN_V_DOWN   = 2; // ��
	public static final int ALIGN_V_MIDDLE = 3; // ��
	public View GetOwner();
	public void Clear();
	public void SetSize(int x, int y, int width, int height);
	public void SetVisible(boolean b_visible);
	public void SetEnable(boolean b_enable);
}
