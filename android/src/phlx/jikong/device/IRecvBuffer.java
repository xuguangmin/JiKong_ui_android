package phlx.jikong.device;

import phlx.jikong.utils.ByteArray;

public interface IRecvBuffer 
{
	/*
	 * ����ֵ��buffer��ʣ������ݳ���
	 */
	public abstract void RecvBufferData(ByteArray byteArray);
}
