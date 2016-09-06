package phlx.jikong.ccc_ui.ui;

public class UiButton extends UiBase
{	
	public String         text;
	public UiStyle        styleNormal;
	public UiStyle        stylePressed;
	public UiStyle        styleFocused;
	
	public UiFont         fontNormal  = null;
	public UiFont         fontFocused = null;
	public UiFont         fontPressed = null;
	public int            warning = 0;
	public String         warningText = null;
	public UiActionGroup  agClick = null;
	
	public UiButton()
	{
		styleNormal  = new UiStyle();
		stylePressed = new UiStyle();
		styleFocused = new UiStyle();
		
		fontNormal  = new UiFont();
		fontFocused = new UiFont();
		fontPressed = new UiFont();
		SetUiType(UiBase.XML_BUTTON);
	}
}