package phlx.jikong.ccc_ui.ui;

public class UiCheckBox extends UiBase
{
	public String   text;
	public UiStyle  styleNormal;
	public UiFont   fontNormal  = null;
	public boolean  checked = false;
	
	public UiCheckBox()
	{
		styleNormal = new UiStyle();
		fontNormal  = new UiFont();
		SetUiType(UiBase.XML_CHECKBOX);
	}
		
	public void SetProperty(int property, int value)
	{
		super.SetProperty(property, value);
		
		switch(property)
		{
		case UiPropertyEnum.CHECKED:
			this.checked = (UiPropertyEnum.CHECKED_TRUE == value) ? true:false;
			break;
		}
	}
}
