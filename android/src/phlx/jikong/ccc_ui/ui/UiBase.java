package phlx.jikong.ccc_ui.ui;

public class UiBase
{
	// ������������
	public int     idPage;
	public int     id;
	public int     x;
	public int     y;
	public int     width;
	public int     height;
	public int     z_index;
	public boolean visible;
	public boolean enable;
	public int     jumpPage = -1;
	
	/* 
	 * �޸����Ե�ֵ
	 * 
     *     property   ����
     *     value      ����ֵ
     * 
     * ˵����
     *      ��������ж�������ԣ��������������
     *      ���������ٵ����������
     */
	public void SetProperty(int property, int value)
	{
		switch(property)
		{
		case UiPropertyEnum.VISIBLE:
			this.visible = UiPropertyEnum.VISIBLE_FALSE==value ? false:true;
			break;
		case UiPropertyEnum.ENABLE:
			this.enable = UiPropertyEnum.ENABLE_FALSE==value ? false:true;
			break;
		}
	}
	

	// ��Ϊ����
	public static final int XML_NONE      = 0;
	public static final int XML_BUTTON    = 1;
	public static final int XML_IMAGE     = 2;
	public static final int XML_LABEL     = 3;
	public static final int XML_SLIDER    = 4;
	public static final int XML_CHECKBOX  = 5;
	public static final int XML_RADIO     = 6;
	public static final int XML_PROGRESS  = 7;
	public static final int XML_VIDEO     = 8;
	
	
	protected int m_uiType = XML_NONE;
		
	protected void SetUiType(int uiType) 
	{
		m_uiType = uiType;
	}
	public int GetUiType()               
	{
		return m_uiType;
	}
}