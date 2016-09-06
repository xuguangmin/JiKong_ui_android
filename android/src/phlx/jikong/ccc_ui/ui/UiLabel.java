package phlx.jikong.ccc_ui.ui;

public class UiLabel extends UiBase 
{
	public String  text;
	public UiStyle styleNormal;
	public UiFont  fontNormal  = null;
	
	public UiLabel()
	{
		styleNormal = new UiStyle();
		fontNormal  = new UiFont();
		SetUiType(UiBase.XML_LABEL);
	}
}
