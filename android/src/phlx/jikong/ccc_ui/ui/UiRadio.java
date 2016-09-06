package phlx.jikong.ccc_ui.ui;

public class UiRadio extends UiBase
{
	public String   text;
	public UiStyle  styleNormal;
	public UiFont   fontNormal  = null;
	public boolean  checked = false;
	
	public UiRadio()
	{
		styleNormal = new UiStyle();
		fontNormal  = new UiFont();
		SetUiType(UiBase.XML_RADIO);
	}
	
	public void SetProperty(int property, int value)
	{
		super.SetProperty(property, value);
		
		switch(property)
		{
		case UiPropertyEnum.CHECKED:
			this.checked = UiPropertyEnum.CHECKED_TRUE==value ? true:false;
			break;
		}
	}
}
