package phlx.jikong.device;

/* **************************************************************************

		版权所有 (C), 2001-2020, 北京飞利信科技股份有限公司

*****************************************************************************
	文件名称 : DownloadProgress.java
	作者           : 贾延刚
	生成日期 : 2013-11-05
	
	版本           : 1.0
	功能描述 : 下载文件的管理

******************************************************************************/

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.os.Environment;

import phlx.jikong.utils.FileUtil;

public class DownloadFile 
{
	private String            m_dirDownload;
	private String            m_filename;
	private FileOutputStream  m_file = null;
	
	
	public boolean InternalCreateFile(String filename)
	{
		if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
		{
			System.out.printf("%s, SD not found.\n", this.getClass().toString());
			return false;
		}
		Close();
		m_filename = String.format("%s%s", m_dirDownload, filename);
		if(!FileUtil.CreateDirectory(m_dirDownload))
		{
			System.out.printf("%s, CreateDirectory failed :%s\n", this.getClass().toString(), m_dirDownload);
			return false;
		}
		
		
		File saveFile= new File(m_filename);            
		try{  
			saveFile.createNewFile();            
		}            
		catch(IOException e)
		{                                
			e.printStackTrace();
			return false;
		}
		
		try 
		{
			m_file = new FileOutputStream(saveFile);
		} 
		catch(FileNotFoundException e) 
		{
			e.printStackTrace();
			return false;
		}
		catch(SecurityException e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public String GetFilePath()
	{
		return m_filename;
	}
	
	public boolean Close()
	{
		if(m_file != null)
		{
			try {
				m_file.close();
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
				return false;
			}
			m_file = null;
		}
		return true;
	}
	
	public boolean WriteFile(byte[] buffer, int offset, int count)
	{
		if(m_file == null)
		{
			System.out.printf("%s, WriteFile m_file  == null\n", this.getClass().toString());
			return false;
		}
		
		try 
		{
			m_file.write(buffer, offset, count);
		}
		catch (IOException e) 
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public boolean CreateFile(String filename)
	{
		if(!InternalCreateFile(filename))
		{
			m_file = null;
			m_filename = null;
			return false;
		}

		System.out.printf("%s, CreateFile successed\n", this.getClass().toString());
		return true;
	}
	
	public void SetSaveFolder(String strFolder)
	{
		m_dirDownload = strFolder;
	}
}
