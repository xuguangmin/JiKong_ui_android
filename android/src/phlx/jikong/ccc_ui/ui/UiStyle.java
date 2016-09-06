package phlx.jikong.ccc_ui.ui;

import android.graphics.Color;

public class UiStyle 
{
	public String bkImage         = null;
	public int    imageStyle      = IMAGE_STYLE_STRETCH;
	public int    colorForeground = Color.BLACK;
	public int    colorBackground = Color.TRANSPARENT;
	
	public static final int IMAGE_STYLE_STRETCH  = 0;           // 拉伸
	public static final int IMAGE_STYLE_PINGPU   = 1;           // 平铺
	public static final int IMAGE_STYLE_SUOFANG  = 2;           // 按比例缩放
}
