package phlx.jikong.utils;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;

public final class InvokeApp 
{
	public static void Run(Activity activity, String packageName)
	{
		if(null == activity || null == packageName)
			return;
		
		try
		{
			PackageManager pkgManager = activity.getPackageManager();
			Intent launchIntent = pkgManager.getLaunchIntentForPackage(packageName);
			if(launchIntent != null) activity.startActivity(launchIntent);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
