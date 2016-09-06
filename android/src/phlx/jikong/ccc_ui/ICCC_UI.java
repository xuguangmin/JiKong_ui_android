package phlx.jikong.ccc_ui;

public interface ICCC_UI
{
	// 请求一个警告对话框
	void ccc_ui_warning(String strWarning); 
	// 控件、页面等的事件
	void ccc_ui_event(int ctrlId, int ctrlEvent, int value);
	
	/*
	 * 根据包名，调用指定的应用程序
	 */
	void ccc_ui_invoke_app(String packageName);
	
	
	/*
	 * 一些信息
	 */
	void ccc_ui_load_info(int uiPageNo);
}
