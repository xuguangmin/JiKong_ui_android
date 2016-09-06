package phlx.jikong.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import phlx.jikong.R;
import phlx.jikong.utils.lang.Language;

public class MessageBox
{
	public static final int ID_CANCEL    = 0;
	public static final int ID_OK        = 1;
	public interface OnNotifyListener 
	{
        void OnClick(int which);
	}
	
	private static OnNotifyListener m_notify    = null;	
	private static void CreateDialog(Context context, String strText, String strTitle, String strOK, String strCancel)
	{		        
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(strTitle);
        builder.setMessage(strText);
        builder.setIcon(R.drawable.ic_launcher);
        builder.setPositiveButton(strOK, new DialogInterface.OnClickListener(){

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				if(m_notify != null) m_notify.OnClick(ID_OK);
			}
        	
        });
        
        builder.setNegativeButton(strCancel, new DialogInterface.OnClickListener(){
        	
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				if(m_notify != null) m_notify.OnClick(ID_CANCEL);
			}
        	
        });
        
        builder.show();
	}
		
	private static void CreateInfoDialog(Context context, String strText, String strTitle, String strOK)
	{		        
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(strTitle);
        builder.setMessage(strText);
        builder.setIcon(R.drawable.ic_launcher);
        builder.setPositiveButton(strOK, new DialogInterface.OnClickListener(){

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
        	
        });        
        builder.show();
	}
	
	public static void Show(Context context, 
	         String strText, String strTitle, 
	         String strOK, String strCancel,
	         OnNotifyListener l)
	{
		m_notify = l;
		CreateDialog(context, strText, strTitle, strOK, strCancel);
	}
	
	public static void Show(Context context, String strText, OnNotifyListener l)
	{
		Show(context, strText, "", Language.Get(Language.LID_STRING_OK), Language.Get(Language.LID_STRING_CANCEL), l);
	}
	
	public static void Show(Context context, String strText, String strTitle)
	{
		CreateInfoDialog(context, strText, strTitle, Language.Get(Language.LID_STRING_OK));
	}
}
