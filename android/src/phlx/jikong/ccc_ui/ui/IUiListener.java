package phlx.jikong.ccc_ui.ui;

public interface IUiListener
{
	/* 
	 * 控件的事件
	 * int value 不同的事件有不同的值
	 */
	void UiEvent(int ctrlId, int ctrlEvent, int value, boolean bWarning, String strWarning, 
			        int jumpPage, UiActionGroup actionGroup);
}
