package phlx.jikong.ccc_ui;

import android.graphics.Bitmap;

public class BmpItem 
{
	Bitmap  lpBmp;             // 位图指针
	String  filename;          // 位图文件名
	
	int     sampleSize;        // 缩放比例，等于1，为原始大小；2为1/2
	boolean b_used;            // 是否被使用
}