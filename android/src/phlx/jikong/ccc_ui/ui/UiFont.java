package phlx.jikong.ccc_ui.ui;

public class UiFont
{
	public int        name; 
	public int        size; 
	public boolean    bold; 
	public boolean    italic;
	public boolean    underLine; 
	public boolean    strikeOut;
	public int        textAlign;
	
	public static final int FONT_NAME_SONG   = 1;     // ����
	public static final int FONT_NAME_HEI    = 2;     // ����
	public static final int FONT_NAME_KAISHU = 3;     // ����
	
	public static final int ALIGN_LEFT_MIDDLE   = 129; // �����
	public static final int ALIGN_CENTER_MIDDLE = 132; // ���ж���
	public static final int ALIGN_RIGHT_MIDDLE  = 130; // �Ҷ���
	public static final int ALIGN_LEFT_TOP      = 33;  // ����
	public static final int ALIGN_CENTER_TOP    = 36;  // ����
	public static final int ALIGN_RIGHT_TOP     = 34;  // ����
	public static final int ALIGN_LEFT_BOTTOM   = 65;  // ����
	public static final int ALIGN_CENTER_BOTTOM = 68;  // ����
	public static final int ALIGN_RIGHT_BOTTOM  = 66;  // ����
}
