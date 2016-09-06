package phlx.jikong.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public final class FileUtil
{
	private static final String XML_IMAGE_DIRECTORY = "resource";
	private static final String SKIN_DIRECTORY      = "skin";
	private static final String TEMP_DIRECTORY      = "temp";
	private static final String RES_DIRECTORY       = "res";
	
	// TODO:��Ҫ����SD��δ����ʱ���л������ô洢
	// Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)
	public static String GetAppPath()
	{
		File fsd = android.os.Environment.getExternalStorageDirectory();		
		return fsd.getPath() + File.separator + "ccc" + File.separator;
	}
	public static String GetResPath()
	{
		return FileUtil.GetAppPath() + RES_DIRECTORY + File.separator;
	}
	public static String GetConfigFilePath()
	{
		return GetResPath() + "config.xml";
	}
	public static String GetSkinPath()
	{
		return GetAppPath() + SKIN_DIRECTORY + File.separator;
	}
	public static String GetSkinResourcePath()
	{
		return String.format("%s%s%s", GetSkinPath(), XML_IMAGE_DIRECTORY, File.separator);
	}
	
	public static String GetTempPath()
	{
		return FileUtil.GetAppPath() + TEMP_DIRECTORY + File.separator;
	}
	public static String GetTempResourcePath()
	{
		return String.format("%s%s%s%s", GetTempPath(), File.separator, XML_IMAGE_DIRECTORY, File.separator);
	}
	
	// ��һ��·���� ��ȡ�ļ�title
	public static String GetFileTitle(String filePath, String prefix)
	{	
		filePath.trim();
		prefix.trim();
		if(filePath.length() <= 0 || prefix.length() <= 0) 
			return "";
		
		int ix = filePath.lastIndexOf(File.separator);
		if(ix <= 0) return "";
		filePath = filePath.substring(ix+1);
		
		// trim .zip
		ix = filePath.lastIndexOf(prefix);
		if(ix <= 0)
			return "";
		
		return filePath.substring(0, ix);
	}
		
	public static void DeleteFolder(String strFolder) //throws IOException
	{
		File f = new File(strFolder);       
		if(f.exists() && f.isDirectory())   //�ж����ļ�����Ŀ¼
		{
			if(0 == f.listFiles().length)     //��Ŀ¼��û���ļ���ֱ��ɾ��
			{
				f.delete();
			}
		    else                            //��������ļ��Ž����飬���ж��Ƿ����¼�Ŀ¼
		    {
		    	File delFile[] = f.listFiles();
		        int i = f.listFiles().length;
		        for(int j = 0; j < i; j++)
		        {
		           if(delFile[j].isDirectory())
		           {
		              DeleteFolder(delFile[j].getAbsolutePath());//�ݹ���ò�ȡ����Ŀ¼·��
		           }
		           delFile[j].delete();//ɾ���ļ�
		        }
		    }
		}
	}
	
	public static String GetFileName(String filePath)
	{		
		filePath.trim();
		if(filePath.length() <= 0) 
			return "";
		
		String prefix = File.separator;
		int ix = filePath.lastIndexOf(prefix);
		if(ix < 0)
			return filePath;
				  		
		return filePath.substring(ix+1, filePath.length());
	}
	
	/*
	 * �����ļ������Ŀ���ļ��в����ڣ�����Դ�ļ�������
	 * ��������false 
	 * dstFolder ��Ҫ�к�׺ \ or /
	 */
	public static boolean CopyFile(String srcFile, String dstFolder) 
	{
		if (!FileUtil.Exists(srcFile))
			return false;
		if (!FileUtil.Exists(dstFolder))
			return false;

		String filename = GetFileName(srcFile);
		if (filename == "")
			return false;

		try 
		{
			FileInputStream fis  = new FileInputStream(srcFile); 
			FileOutputStream fos = new FileOutputStream(String.format("%s%s",dstFolder, filename));
			
			int byteread = 0;
			byte[] buffer = new byte[4096];
			while ((byteread = fis.read(buffer, 0, 4096)) != -1) 
			{
				fos.write(buffer, 0, byteread);
			}
			fos.close();
			fis.close();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * @category ����ļ���չ��
	 * @param strFileName �ļ�ȫ·��
	 * @return �ļ���չ��
	 */
	public static String GetFileExtention(String strFileName)
	{
		String strRetExtName="";
		if(strFileName!=null&&strFileName.trim().length()>0)
		{
			int index=strFileName.lastIndexOf(".");
			if(index>0)
			{
				strRetExtName=strFileName.substring(index, strFileName.length());
			}
		}
		return strRetExtName.toLowerCase();
	}
    /**
     * �ж��ļ��Ƿ����
     * @return �����ڷ���true,���򷵻�false;
     */
	public static boolean Exists(String fileName)
	{
		boolean bRetValue=false;
		File file=new File(fileName);
		if(file.exists())
		{
			bRetValue=true;
		}
		return bRetValue;
	}
	/**
	 * @category ɾ���ļ�
	 * @param fileName Ҫɾ�����ļ�ȫ·��
	 * @return �Ƿ�ɾ���ɹ�,���ɹ�����true,���򷵻�false;
	 */
	public static boolean DeleteFile(String fileName)
	{
		boolean bRetValue=false;
		File file=new File(fileName);
		if(file.exists()&&file.isFile())
		{
			try
			{
				bRetValue=file.delete();
			}
			catch(SecurityException se)
			{
				
			}
		}
		return bRetValue;
	}
	/**
	 * @category �����ļ�
	 * @param fileAbsolutePath �ļ��ľ���·��
	 * @return �Ƿ񴴽��ɹ������ɹ�����true,���򷵻�false;
	 */
	public static File CreateFile(String fileAbsolutePath)
	{
		File fileRetValue=null;
		File file=new File(fileAbsolutePath);
		if(!file.exists())
		{
			try
			{
				file.createNewFile();
			}
			catch(SecurityException se)
			{
				fileRetValue=null;
			}
			catch (IOException e)
			{
				fileRetValue=null;
			}
		}
		fileRetValue=file;
		return fileRetValue;
	}	
	/**
	 * @category �����ļ���
	 * @param strDirectoryName �ļ���·������
	 * @return ��������ɹ��򷵻��ļ��ж��󣬷��򷵻�null
	 */
	public static boolean CreateDirectory(String strDirectoryName)
	{
		File directory = new File(strDirectoryName);
		if(directory.exists())
			return true;
		
		try
		{
			if(directory.mkdirs())
			{
				return true;
			}
		}
		catch(SecurityException se)
		{
			return false;
		}
		
		return false;
	}
	/**
	 * ɾ���ļ���
	 * @param strDirectoryName �ļ���·��
	 * @param deleteSubFile �Ƿ�Ҫɾ���ļ��а������ļ������ļ���,trueɾ�����ļ��м��ļ�
	 * @return �Ƿ�ɾ���ɹ�,���ɹ�����true,���򷵻�false;
	 */
	public static boolean DeleteDrirectory(String strDirectoryName,boolean deleteSubFile)
	{
		boolean bRetValue=false;
		File directory=new File(strDirectoryName);
		if(directory.exists())
		{//1
			try
			{//try
				File[] files=directory.listFiles();
				if(files!=null&&deleteSubFile)
				{//2
					for(File file:files)
					{//for
						try
						{
							if(file.isDirectory())
							{
								DeleteDrirectory(file.getAbsolutePath(),true);
							}
							else
							{
								file.delete();
							}	
							bRetValue=true;
						}
						catch(SecurityException se){bRetValue=false;}
					}//end for
				}//2
				bRetValue=directory.delete()&&bRetValue;
			}
			catch(SecurityException se)
			{}//end try
		}//1
		return bRetValue;
	}
}
