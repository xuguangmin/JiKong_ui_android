package phlx.jikong.device.net;

public interface ITcpClient 
{	
	public static final int CONN_STATUS_CLOSE       = 0;   // 已关闭
	public static final int CONN_STATUS_OPEN        = 1;   // 已打开
	public static final int CONN_STATUS_CONNECTING  = 2;   // 正在连接
			
	public abstract void RecvDataFromComm(byte buffer[], int data_len);
	public abstract void ConnectStatus(int connStatus);
}