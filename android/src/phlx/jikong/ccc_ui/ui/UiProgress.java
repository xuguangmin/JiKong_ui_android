package phlx.jikong.ccc_ui.ui;

public class UiProgress extends UiBase
{
	public int     orientation     = 0;         // 滑动方向，0 横向，1 纵向
	public int     value           = 0;
	
	public UiProgress()
	{
		SetUiType(UiBase.XML_PROGRESS);		
	}
	
	public void SetProperty(int property, int value)
	{
		super.SetProperty(property, value);
		
		switch(property)
		{
		case UiPropertyEnum.CHECKED:
			this.value = value;
			break;
		}		
	}
}
