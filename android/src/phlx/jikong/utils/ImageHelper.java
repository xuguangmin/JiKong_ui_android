package phlx.jikong.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public final class ImageHelper 
{
	/**
	 * @category 从文件系统加载图片
	 * @param fileName 图片文件名称
	 * @return 图片
	 */	
	public static Bitmap LoadBitmapFromFile(String fileName)
	{
		Bitmap retBmp=null;
	
		try
		{
		   if(fileName!=null&&fileName.trim()!="")
		   {
			   File file=new File(fileName);
			   if(file.exists())
			   {
				   BitmapFactory.Options options=new BitmapFactory.Options(); 
				   options.inJustDecodeBounds = false;		
				   options.inPurgeable = true;
				   options.inInputShareable = true;  
				   FileInputStream fis=new FileInputStream(file);			  
				   retBmp=BitmapFactory.decodeStream(fis,null,options);			   
				   fis.close();
				   fis=null;
			   }
			   else
			   {
				   Log.e("ImageHelper.LoadBitmapFromFile", "file not exist");
				   
			   }
		   }
		}
		catch(Exception ex)
		{
			Log.e("ImageHelper.LoadBitmapFromFile", ex.getMessage());
		}
		return retBmp;
	}
	
	public static Bitmap LoadBitmapFromAssets(Context context, String filename)
	{
		if(context == null || filename == null)
			return null;
		AssetManager am = context.getAssets();
		if(am == null)
			return null;
		
		InputStream is = null;
		try
		{
			is = am.open(filename);
		}
		catch(IOException e)
		{}
		
		Bitmap bkImage = null;
		if(is != null)
		{
			try
			{
				bkImage = BitmapFactory.decodeStream(is);
			}
			catch(OutOfMemoryError e)
			{}
			
		}
		return bkImage;
	}
}
