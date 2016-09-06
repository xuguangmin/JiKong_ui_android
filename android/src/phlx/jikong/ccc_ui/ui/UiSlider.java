package phlx.jikong.ccc_ui.ui;

public class UiSlider extends UiBase
{
	public UiStyle styleSlipper;
	public UiStyle styleBar;
	
	public int     percent         = 0;
	public int     percentShow     = 0;	      // 0 ���أ�1 ��ʾ
	public int     orientation     = 0;	      // 0 ˮƽ��1 ��ֱ
	public int     barWidth        = 0;       // ��������
	public int     barHeight       = 0;       // ��������
	public int     slipperWidth    = 0;       // �����
	public int     slipperHeight   = 0;       // �����
	
	public UiSlider()
	{
		styleSlipper = new UiStyle();
		styleBar = new UiStyle();
		SetUiType(UiBase.XML_SLIDER);
	}
	
	// TODO:
	// �����滬������ʱ����θ������ֵ
	//this.percent = value;
		
	
	public void SetProperty(int property, int value)
	{
		super.SetProperty(property, value);
		
		switch(property)
		{
		case UiPropertyEnum.CHANGED:
			this.percent = value;
			break;
		}
	}
}
