package phlx.jikong;

/* ***************************************************************************

                                        版权所有 (C), 2001-2020, 北京飞利信科技股份有限公司

******************************************************************************
     文件名称 : DeviceMsg.java
     作者           : 贾延刚
     生成日期 : 2013-10-28

     版本           : 1.0
     功能描述 : 该类实现了IDevice接口，
                               然后，把IDevice接口的调用转换为消息，通知界面刷新

     修改历史 :

******************************************************************************/

import android.os.Handler;
import android.os.Message;
import phlx.jikong.device.IDevice;
import phlx.jikong.utils.msg.MsgIdUi;
import phlx.jikong.utils.msg.ParaDiscoverDev;

public class DeviceMsg implements IDevice
{
	private Handler m_handler = null;
	public DeviceMsg(Handler handler)
	{
		m_handler = handler;
	}
	
	private void PostMessage(int msgId, int arg1, int arg2, Object obj)
	{
		if(null == m_handler)
			return;
		
		Message msg = Message.obtain();
		msg.what = msgId;
		msg.arg1 = arg1;
		msg.arg2 = arg2;
		msg.obj  = obj;
		m_handler.sendMessage(msg);	
	}
	
	public void FileHasUpdated(int fileType)
	{		
		PostMessage(MsgIdUi.MSG_UI_HAS_NEW_ZIP, fileType, 0, null);     // 如果不从检查版本开始，则无法获取到新文件的版本信息
	}
	
	public void DownloadFinished(boolean bSuccess, int fileType, String filePath)
	{
		PostMessage(MsgIdUi.MSG_UI_DL_PERCENT, 0, 0, null);
		PostMessage(MsgIdUi.MSG_UI_DL_FINISH, bSuccess ? 1:0, fileType, filePath);
	}
	public void DownloadPercent(int percent, long speed)
	{
		PostMessage(MsgIdUi.MSG_UI_DL_PERCENT, percent, (int)speed, null);	
	}
	public void DownloadFileInfo(int fileType, int fileSize, String fileName)
	{
		PostMessage(MsgIdUi.MSG_UI_ZIP_INFO, fileType, fileSize, fileName);
	}
	/* 
	 * 查询得到的文件  版本信息
	 * 
	 * fileType :文件类型
	 * version  :版本字符串（文件的唯一性标识）
	 */
	public void DownloadVersion(boolean bSuccess, int fileType, String version)
	{
		PostMessage(MsgIdUi.MSG_UI_VERSION_INFO, bSuccess ? 1:0, fileType, version);
	}
	
	public void ConnectStatus(int connStatus)
	{
		PostMessage(MsgIdUi.MSG_CONNECT_STATUS, connStatus, 0, null);
	}
	/*
	 * property:
	 *     0x1  enable
	 *               value
	 *                    0x1 true
	 *                    0x0 false
	 *     0x2  visible
	 *               value
	 *                    0x1 true
	 *                    0x0 false
	 */
	@Override
	public void ControlProperty(int ctrlId, int property, int value) 
	{
		PostMessage(MsgIdUi.MSG_UI_CTRL_PROPERTY, ctrlId, property, String.format("%d", value));
	}

	// 显示或者隐藏弹出页
	@Override
	public void InvokePage(int page_no, boolean b_show) 
	{
		PostMessage(MsgIdUi.MSG_UI_INVOKE_PAGE, page_no, b_show ? 1:0, 0);
	}

	// 查找服务器  得到的返回信息：IP地址、主机名
	@Override
	public void DiscoverDevice(String ipAddr, String hostname) 
	{
		ParaDiscoverDev para = new ParaDiscoverDev();
		para.ipAddr   = ipAddr;
		para.hostname = hostname;
		PostMessage(MsgIdUi.MSG_UI_DISCOVER_DEV, 0, 0, para);		
	}
}
