package phlx.jikong.ccc_ui.ui;

public class UiSlider extends UiBase
{
	public UiStyle styleSlipper;
	public UiStyle styleBar;
	
	public int     percent         = 0;
	public int     percentShow     = 0;	      // 0 隐藏，1 显示
	public int     orientation     = 0;	      // 0 水平，1 竖直
	public int     barWidth        = 0;       // 滑动条宽
	public int     barHeight       = 0;       // 滑动条高
	public int     slipperWidth    = 0;       // 滑块宽
	public int     slipperHeight   = 0;       // 滑块高
	
	public UiSlider()
	{
		styleSlipper = new UiStyle();
		styleBar = new UiStyle();
		SetUiType(UiBase.XML_SLIDER);
	}
	
	// TODO:
	// 当界面滑动滑块时，如何给这儿设值
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
