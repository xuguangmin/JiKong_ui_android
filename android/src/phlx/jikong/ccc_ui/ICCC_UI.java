package phlx.jikong.ccc_ui;

public interface ICCC_UI
{
	// ����һ������Ի���
	void ccc_ui_warning(String strWarning); 
	// �ؼ���ҳ��ȵ��¼�
	void ccc_ui_event(int ctrlId, int ctrlEvent, int value);
	
	/*
	 * ���ݰ���������ָ����Ӧ�ó���
	 */
	void ccc_ui_invoke_app(String packageName);
	
	
	/*
	 * һЩ��Ϣ
	 */
	void ccc_ui_load_info(int uiPageNo);
}
