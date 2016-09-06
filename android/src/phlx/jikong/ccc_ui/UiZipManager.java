package phlx.jikong.ccc_ui;

/* ***************************************************************************

			版权所有 (C), 2001-2020, 北京飞利信科技股份有限公司

******************************************************************************
	文件名称 : SkinManager.java
	作者           : 贾延刚
	生成日期 : 
	
	版本           : 1.0
	功能描述 : 管理界面zip文件的打开，取xml文件流，根据文件名取位图
	
	修改历史 :

******************************************************************************/
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ByteArrayOutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class UiZipManager
{
	private static final int              BUFFER_SIZE = 4096;
	private static ZipFile                m_zipfile = null;
	private static String                 m_fileTitle = null;
	private static byte[]                 m_tempBuffer = new byte[BUFFER_SIZE];
	private static ByteArrayOutputStream  m_byteArry = new ByteArrayOutputStream();
	private static BitmapFactory.Options  m_bmpOptions = new BitmapFactory.Options();
			
	private static String GetUiFileTitle(String fileName)
	{		
		fileName.trim();
		if(fileName.length() <= 0) 
			return null;
		
		String prefix = ".zip";
		int ix = fileName.lastIndexOf(prefix);
		if(ix <= 0)
			return null;
		if((prefix.length() + ix) != fileName.length())
			return null;	
		fileName = fileName.substring(0, ix);
		
		ix = fileName.lastIndexOf(File.separator);
  		if(ix <= 0)
  			return null;
  		
		return fileName.substring(ix+1, fileName.length());	
	}
	
	private static InputStream GetEntryInputStream(String filename)
	{
		if(null == m_zipfile)
			return null;
		
		try
		{
			ZipEntry entry;
			Enumeration<? extends ZipEntry> entrys = m_zipfile.entries();
			while (entrys.hasMoreElements()) 
			{
				entry = (ZipEntry)entrys.nextElement();
				if (!entry.isDirectory() &&
				    entry.getName().compareToIgnoreCase(filename) == 0) 
				{					
					return m_zipfile.getInputStream(entry);
				}				
			}			
		}
		catch(IOException e)
		{
			e.printStackTrace();
			return null;
		}
		return null;
	}
	
		
	public static InputStream GetXmlInputStream()
	{		
		return GetEntryInputStream(String.format("%s.xml", m_fileTitle));
	}
	
	public static InputStream GetImageInputStream(String filename)
	{		
		return GetEntryInputStream(String.format("resource/%s", filename));
	}
	
	public static byte [] GetImageByteArray(String filename)
	{	
		if(null == m_zipfile)
			return null;
		
		try
		{
			InputStream is = GetEntryInputStream(String.format("resource/%s", filename));
			if(is == null)
				return null;
			
			m_byteArry.reset();
			int len = is.read(m_tempBuffer, 0, BUFFER_SIZE);
			while (len > 0) 
			{
				m_byteArry.write(m_tempBuffer, 0, len);
				len = is.read(m_tempBuffer, 0, BUFFER_SIZE);
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
			return null;
		}
		return m_byteArry.toByteArray();
	}
	public static boolean OpenZipFile(String filename)
	{
		m_bmpOptions.inSampleSize = 1;
		if(m_zipfile != null)
		{
			try
			{
				m_zipfile.close();	
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
			m_zipfile = null;
		}
		
		try
		{
			m_zipfile = new ZipFile(filename);
			m_fileTitle = GetUiFileTitle(filename);		
		}
		catch(IOException e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public static Bitmap GetBitmap(String imageFilename, int sampleSize)
	{
		if(null == m_zipfile)
			return null;
		
		if(null == imageFilename || imageFilename == "")
			return null;
		
		InputStream isImage = GetImageInputStream(imageFilename);
		if(isImage == null) 
			return null;
		
		Bitmap bmp = null;
		try
		{
			m_bmpOptions.inSampleSize = (sampleSize > 1) ? sampleSize:1;
			bmp = BitmapFactory.decodeStream(isImage, null, m_bmpOptions);
		}
		catch(OutOfMemoryError e)
		{
			//e.printStackTrace();
			System.out.printf("------ UiZipManager load image :%s failed\n", imageFilename);
		}
		
		try
		{
			isImage.close();
			isImage = null;
		}
		catch(IOException e)
		{}
		
		return bmp;
	}
}