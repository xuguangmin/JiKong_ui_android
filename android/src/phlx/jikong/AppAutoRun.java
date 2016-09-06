package phlx.jikong;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


// ¿ª»úÆô¶¯
public class AppAutoRun extends BroadcastReceiver
{
	public void onReceive(Context context, Intent intent)
	{
		System.out.printf("AppAutoRun onReceive action = %s\n", intent.getAction());
		//VMRuntime.getRuntime().setMinimumHeapSize(CWJ_HEAP_SIZE);
		//VMRuntime.getRuntime().setTargetHeapUtilization(TARGET_HEAP_UTILIZATION); 
		//android.intent.action.BOOT_COMPLETED
		
		Intent mainIntent=new Intent(context, MainActivity.class);
		mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(mainIntent);
	}

}
