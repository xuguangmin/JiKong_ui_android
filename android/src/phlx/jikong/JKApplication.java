package phlx.jikong;

import android.app.Application;
import android.content.Context;

public class JKApplication extends Application
{
	private static JKApplication instance;

	@Override
	public void onCreate() 
	{
		super.onCreate();
		instance = this;
		System.out.println("JKApplication onCreate");
	}
	
	public static Context getAppContext()
    {
        return instance;
    }
}
