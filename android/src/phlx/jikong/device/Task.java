/* ***************************************************************************

                  		��Ȩ���� (C), 2013-2100

 *****************************************************************************
	  �ļ����� : Task.java
	  ����           : ���Ӹ�
	  �������� : 2013��
	
	  �汾           : 1.0
	  �������� : ���浱ǰ���ڴ����ָ�����Ϣ
	
	  �޸���ʷ :

******************************************************************************/
package phlx.jikong.device;

import phlx.jikong.utils.msg.MsgTask;

public class Task 
{
	private int              m_task;
	private long             m_pcsStart;                 // ��ʼʱ�䣬����
	private int              m_delay;                    // ���ȴ�ʱ�䣬����
		
	public Task()
	{
		m_task     = MsgTask.TASK_INVALID;
		m_pcsStart = 0;
		m_delay    = 0;
	}
	public boolean IsRunning()
	{
		return m_task != MsgTask.TASK_INVALID;
	}
	public boolean IsTimeout()
	{
		return ((System.currentTimeMillis() - m_pcsStart) >= m_delay)? true:false;		
	}
	public int Current()  {return m_task;}
	
	public void Clear()
	{
		m_task = MsgTask.TASK_INVALID;
	}
		
	// �������������
	public boolean Clear(int pcs)
	{
		if(pcs == m_task)
		{
			m_task = MsgTask.TASK_INVALID;
			//System.out.printf("CTask::Clear pcs %d\n", pcs);
			return true;
		}
		return false;
	}
	
	public void NewTask(int cmdEnum, int delay)
	{
		m_task     = cmdEnum;
		m_pcsStart = System.currentTimeMillis();
		m_delay    = delay;
	}
}
