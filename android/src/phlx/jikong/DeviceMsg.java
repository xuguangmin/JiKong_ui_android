package phlx.jikong;

/* ***************************************************************************

                                        ��Ȩ���� (C), 2001-2020, ���������ſƼ��ɷ����޹�˾

******************************************************************************
     �ļ����� : DeviceMsg.java
     ����           : ���Ӹ�
     �������� : 2013-10-28

     �汾           : 1.0
     �������� : ����ʵ����IDevice�ӿڣ�
                               Ȼ�󣬰�IDevice�ӿڵĵ���ת��Ϊ��Ϣ��֪ͨ����ˢ��

     �޸���ʷ :

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
		PostMessage(MsgIdUi.MSG_UI_HAS_NEW_ZIP, fileType, 0, null);     // ������Ӽ��汾��ʼ�����޷���ȡ�����ļ��İ汾��Ϣ
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
	 * ��ѯ�õ����ļ�  �汾��Ϣ
	 * 
	 * fileType :�ļ�����
	 * version  :�汾�ַ������ļ���Ψһ�Ա�ʶ��
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

	// ��ʾ�������ص���ҳ
	@Override
	public void InvokePage(int page_no, boolean b_show) 
	{
		PostMessage(MsgIdUi.MSG_UI_INVOKE_PAGE, page_no, b_show ? 1:0, 0);
	}

	// ���ҷ�����  �õ��ķ�����Ϣ��IP��ַ��������
	@Override
	public void DiscoverDevice(String ipAddr, String hostname) 
	{
		ParaDiscoverDev para = new ParaDiscoverDev();
		para.ipAddr   = ipAddr;
		para.hostname = hostname;
		PostMessage(MsgIdUi.MSG_UI_DISCOVER_DEV, 0, 0, para);		
	}
}
