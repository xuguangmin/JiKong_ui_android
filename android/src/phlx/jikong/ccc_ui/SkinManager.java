package phlx.jikong.ccc_ui;

/* ***************************************************************************

			��Ȩ���� (C), 2001-2020, ���������ſƼ��ɷ����޹�˾

******************************************************************************
	�ļ����� : SkinManager.java
	����           : ���Ӹ�
	�������� : 
	
	�汾           : 1.0
	�������� : ���桢����λͼ�������ظ�ʹ���Ѵ򿪵�λͼ
	
	�޸���ʷ :

******************************************************************************/

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Bitmap.Config;
import android.graphics.Paint.Align;

public class SkinManager
{
	private static List<BmpItem>   m_bmpItemList = new ArrayList<BmpItem>();
	private static Bitmap          m_bmpError = null;                            // �ڴ治��ʱ����һ����ʾλͼ
	private static int             m_sampleSize = 1;
	
	static {
		m_bmpError = CreateBlankBitmap("�ڴ治��");
	}
	
	private static Bitmap CreateBlankBitmap(String strTitle)
	{
		Bitmap result = Bitmap.createBitmap(100, 100, Config.ARGB_8888);
		Canvas canvasTemp = new Canvas(result);
		canvasTemp.drawColor(Color.WHITE);
		Paint p = new Paint();
		
		p.setColor(Color.RED);
		p.setTextAlign(Align.CENTER);
		p.setTextSize(12);
		
		float x = result.getWidth()/2;
		float y = result.getHeight()/2;
		canvasTemp.drawText(strTitle, x, y, p);
		return result;
	}
			
	private static Bitmap GetBitmapFromList(String filename, int sampleSize)
	{
		for(int k = 0; k < m_bmpItemList.size(); ++k)
		{
			BmpItem bi = m_bmpItemList.get(k);
			if(sampleSize == bi.sampleSize &&
			   0 == filename.compareToIgnoreCase(bi.filename))
			{				
				bi.b_used = true;
				return bi.lpBmp;
			}
		}
		return null;	
	}
	
	private static boolean AddBmpItem(Bitmap lpBmp, String filename, int sampleSize)
	{		
		BmpItem biNew = new BmpItem();
		biNew.lpBmp      = lpBmp;
		biNew.filename   = filename;
		biNew.sampleSize = sampleSize;
		biNew.b_used     = true;
		return m_bmpItemList.add(biNew);
	}
	
	private static Bitmap InternalGetBitmap(String imageFilename, int sampleSize)
	{
		if(null == imageFilename)
			return null;
		if(imageFilename.length() <= 0)
			return null;
		
		Bitmap result = GetBitmapFromList(imageFilename, sampleSize);
		if(result != null)
			return result;
		
		result = UiZipManager.GetBitmap(imageFilename, sampleSize);
		if(null == result)
		{
			return m_bmpError;
		}
			
		AddBmpItem(result, imageFilename, sampleSize);
		
		return result;
	}
	
	private static void ReleaseBitmap(Bitmap bmp)
	{
		if(bmp != null) 
		{
			if(!bmp.isRecycled()) bmp.recycle();
		}
	}	
	
	public static void ClearAll()
	{
		for(int k = 0; k < m_bmpItemList.size(); ++k)
		{
			BmpItem bi = m_bmpItemList.get(k);
			ReleaseBitmap(bi.lpBmp);
		}
		m_bmpItemList.clear();
	}
	// ɾ�����ٱ�ʹ�õ�λͼ
	public static void ClearIdleBitmap()
	{
		for(int k = (m_bmpItemList.size() - 1); k >= 0 ; --k)
		{
			BmpItem bi = m_bmpItemList.get(k);
			if(!bi.b_used) 
			{
				//System.out.printf("ClearIdleBitmap %d %d Bitmap =%s\n", k, bi.sampleSize, bi.filename);
				
				ReleaseBitmap(bi.lpBmp);
				m_bmpItemList.remove(k);
			}
		}
	}
	// �ָ�λͼ�����е�ʹ��״̬Ϊδʹ��
	public static void ResetAllBitmap()
	{
		for(int k = 0; k < m_bmpItemList.size(); ++k)
		{
			BmpItem bi = m_bmpItemList.get(k);
			bi.b_used = false;
		}
	}

	public static InputStream GetXmlInputStream()
	{		
		return UiZipManager.GetXmlInputStream();
	}
	
	/*
	 * sampleSize λͼ�Ĳ�������
	 * �����2����Ϊ1/2
     * If set to a value > 1, requests the decoder to subsample the original
     * image, returning a smaller image to save memory. The sample size is
     * the number of pixels in either dimension that correspond to a single
     * pixel in the decoded bitmap. For example, inSampleSize == 4 returns
     * an image that is 1/4 the width/height of the original, and 1/16 the
     * number of pixels. Any value <= 1 is treated the same as 1. Note: the
     * decoder will try to fulfill this request, but the resulting bitmap
     * may have different dimensions that precisely what has been requested.
     * Also, powers of 2 are often faster/easier for the decoder to honor.
	 */
	public static Bitmap GetBitmap(String filename)
	{	
		return InternalGetBitmap(filename, m_sampleSize);
	}
	public static void SetSampleSize(int sampleSize)
	{
		m_sampleSize = (sampleSize > 1) ? sampleSize:1;
	}
	public static boolean OpenZipFile(String filename)
	{
		return UiZipManager.OpenZipFile(filename);
	}
}