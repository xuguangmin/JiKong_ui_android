package phlx.jikong.ccc_ui.ui;

public class UiVideo extends UiBase
{
	public String  text;
	public UiStyle styleNormal;
	public String  movie;
	
	public UiVideo()
	{
		styleNormal  = new UiStyle();
		SetUiType(UiBase.XML_VIDEO);
	}

}
