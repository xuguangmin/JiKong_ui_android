package phlx.jikong.utils.lang;

public final class Language 
{
	public static final int LANG_EN_US  = 0;
	public static final int LANG_ZH_CN  = 1;
	
	public static final int LID_STRING_EXIT     = 0;
	public static final int LID_STRING_FILE     = 1;
	public static final int LID_STRING_TOOL     = 2;
	public static final int LID_STRING_HELP     = 3;
	public static final int LID_STRING_ABOUT    = 4;
	public static final int LID_STRING_OPTIONS  = 5;
	public static final int LID_STRING_OPEN     = 6;
	public static final int LID_STRING_CLOSE    = 8;
	public static final int LID_STRING_TEST     = 9;
	public static final int LID_STRING_OK       = 10;
	public static final int LID_STRING_CANCEL   = 11;
		
	public static final int LID_STRING_1        = 21;
	public static final int LID_STRING_2        = 22;
	public static final int LID_STRING_3        = 23;
	public static final int LID_STRING_4        = 24;
	public static final int LID_STRING_5        = 25;
	public static final int LID_STRING_6        = 26;
	public static final int LID_STRING_7        = 27;
	public static final int LID_STRING_8        = 28;
	public static final int LID_STRING_9        = 29;
	public static final int LID_STRING_10       = 30;
	public static final int LID_STRING_11       = 31;
	public static final int LID_STRING_12       = 32;
	public static final int LID_STRING_13       = 33;
	public static final int LID_STRING_14       = 34;
	public static final int LID_STRING_15       = 35;
	public static final int LID_STRING_16       = 36;
	public static final int LID_STRING_17       = 37;
	public static final int LID_STRING_18       = 38;
	
	private static final int LID_MAX_COUNT      = 39;
	
	// �����ַ���
	static final String CN_STRING_EXIT    = "�˳�";	
	static final String CN_STRING_FILE    = "�ļ�";	
	static final String CN_STRING_TOOL    = "����";
	static final String CN_STRING_HELP    = "����";
	static final String CN_STRING_ABOUT   = "����";
	static final String CN_STRING_OPTIONS = "ѡ��";
	static final String CN_STRING_OPEN    = "����";
	static final String CN_STRING_CLOSE   = "�ر�����";
	static final String CN_STRING_TEST    = "����";
	static final String CN_STRING_CANCEL  = "ȡ��";
	static final String CN_STRING_OK      = "ȷ��";
	static final String CN_STRING_1       = "IP��ַ��";
	static final String CN_STRING_2       = "�˿ڣ�";
	static final String CN_STRING_3       = "�������ӵ�";
	static final String CN_STRING_4       = "��������  ...";
	static final String CN_STRING_5       = "���� ...";
	static final String CN_STRING_6       = "��ԭʼ������ʾ";
	static final String CN_STRING_7       = "�˳�Ԥ��";
	static final String CN_STRING_8       = "Ԥ�������ؽ���";
	static final String CN_STRING_9       = "���治����";
	static final String CN_STRING_10      = "׼����ʾ���� ...";
	static final String CN_STRING_11      = "ȷ��Ҫ�˳�������";
	static final String CN_STRING_12      = "�����ʼ��ʧ�ܣ�����������";
	static final String CN_STRING_13      = "WIFI�򿪳ɹ�";
	static final String CN_STRING_14      = "WIFI��ʧ��";
	static final String CN_STRING_15      = "���ڴ�WIFI";
	static final String CN_STRING_16      = "�����ļ�ʧ��";
	static final String CN_STRING_17      = "������ ...";
	static final String CN_STRING_18      = "����IP :";
	
	// Ӣ���ַ���
	static final String EN_STRING_EXIT    = "Exit";	
	static final String EN_STRING_FILE    = "File";
	static final String EN_STRING_TOOL    = "Tool";
	static final String EN_STRING_HELP    = "Help";
	static final String EN_STRING_ABOUT   = "About";
	static final String EN_STRING_OPTIONS = "Options";
	static final String EN_STRING_OPEN    = "Open";
	static final String EN_STRING_CLOSE   = "Close";
	static final String EN_STRING_TEST    = "Test";
	static final String EN_STRING_CANCEL  = "Cancel";
	static final String EN_STRING_OK      = "OK";
	static final String EN_STRING_1       = "Server IP:";
	static final String EN_STRING_2       = "Port:";
	static final String EN_STRING_3       = "Connecting to";
	static final String EN_STRING_4       = "Setting ...";
	static final String EN_STRING_5       = "Download ...";
	static final String EN_STRING_6       = "Origin Ratio";
	static final String EN_STRING_7       = "Open External UI";
	static final String EN_STRING_8       = "Preview Internal UI";
	static final String EN_STRING_9       = "Load UI failed";
	static final String EN_STRING_10      = "Loading ... ";
	static final String EN_STRING_11      = "Are you sure to exit ?";
	static final String EN_STRING_12      = "Application failed, please restart";
	static final String EN_STRING_13      = "WIFI opened sucessfully";
	static final String EN_STRING_14      = "Faile to open WIFI";
	static final String EN_STRING_15      = "WIFI opening";
	static final String EN_STRING_16      = "Download is failed";
	static final String EN_STRING_17      = "Downloading ...";
	static final String EN_STRING_18      = "Host IP:";
	
	static String m_language[] = new String[LID_MAX_COUNT];
	static void Add(int ix, String strText)
	{
		if(ix < 0 || ix >= LID_MAX_COUNT)
			return;

		m_language[ix] = strText;
	}
	
	static void LoadExternal_ZhCn()
	{
		Add(LID_STRING_EXIT,     CN_STRING_EXIT);
		Add(LID_STRING_FILE,     CN_STRING_FILE);
		Add(LID_STRING_TOOL,     CN_STRING_TOOL);
		Add(LID_STRING_HELP,     CN_STRING_HELP);
		Add(LID_STRING_ABOUT,    CN_STRING_ABOUT);
		Add(LID_STRING_OPTIONS,  CN_STRING_OPTIONS);
		Add(LID_STRING_OPEN,     CN_STRING_OPEN);
		Add(LID_STRING_CLOSE,    CN_STRING_CLOSE);
		Add(LID_STRING_TEST,     CN_STRING_TEST);
		Add(LID_STRING_OK,       CN_STRING_OK);
		Add(LID_STRING_CANCEL,   CN_STRING_CANCEL);
		
		Add(LID_STRING_1,        CN_STRING_1);
		Add(LID_STRING_2,        CN_STRING_2);
		Add(LID_STRING_3,        CN_STRING_3);
		Add(LID_STRING_4,        CN_STRING_4);
		Add(LID_STRING_5,        CN_STRING_5);
		Add(LID_STRING_6,        CN_STRING_6);
		Add(LID_STRING_7,        CN_STRING_7);
		Add(LID_STRING_8,        CN_STRING_8);
		Add(LID_STRING_9,        CN_STRING_9);
		Add(LID_STRING_10,       CN_STRING_10);
		Add(LID_STRING_11,       CN_STRING_11);
		Add(LID_STRING_12,       CN_STRING_12);
		Add(LID_STRING_13,       CN_STRING_13);
		Add(LID_STRING_14,       CN_STRING_14);
		Add(LID_STRING_15,       CN_STRING_15);
		Add(LID_STRING_16,       CN_STRING_16);
		Add(LID_STRING_17,       CN_STRING_17);
		Add(LID_STRING_18,       CN_STRING_18);
	}
	static void LoadExternal_EnUs()
	{
		Add(LID_STRING_EXIT,     EN_STRING_EXIT);
		Add(LID_STRING_FILE,     EN_STRING_FILE);
		Add(LID_STRING_TOOL,     EN_STRING_TOOL);
		Add(LID_STRING_HELP,     EN_STRING_HELP);
		Add(LID_STRING_ABOUT,    EN_STRING_ABOUT);
		Add(LID_STRING_OPTIONS,  EN_STRING_OPTIONS);
		Add(LID_STRING_OPEN,     EN_STRING_OPEN);
		Add(LID_STRING_CLOSE,    EN_STRING_CLOSE);
		Add(LID_STRING_TEST,     EN_STRING_TEST);
		Add(LID_STRING_OK,       EN_STRING_OK);
		Add(LID_STRING_CANCEL,   EN_STRING_CANCEL);
		
		Add(LID_STRING_1,        EN_STRING_1);
		Add(LID_STRING_2,        EN_STRING_2);
		Add(LID_STRING_3,        EN_STRING_3);
		Add(LID_STRING_4,        EN_STRING_4);
		Add(LID_STRING_5,        EN_STRING_5);
		Add(LID_STRING_6,        EN_STRING_6);
		Add(LID_STRING_7,        EN_STRING_7);
		Add(LID_STRING_8,        EN_STRING_8);
		Add(LID_STRING_9,        EN_STRING_9);
		Add(LID_STRING_10,       EN_STRING_10);
		Add(LID_STRING_11,       EN_STRING_11);
		Add(LID_STRING_12,       EN_STRING_12);
		Add(LID_STRING_13,       EN_STRING_13);
		Add(LID_STRING_14,       EN_STRING_14);
		Add(LID_STRING_15,       EN_STRING_15);
		Add(LID_STRING_16,       EN_STRING_16);
		Add(LID_STRING_17,       EN_STRING_17);
		Add(LID_STRING_18,       EN_STRING_18);
	}
	
	static boolean LoadExternal(int langId)
	{
		switch(langId)
		{
		case LANG_EN_US :LoadExternal_EnUs(); break;
		case LANG_ZH_CN :LoadExternal_ZhCn(); break;
		}
		return true;
	}
	
	static{
		LoadExternal(LANG_ZH_CN);
	}
	
	public static boolean Switch(int langId)
	{
		return LoadExternal(langId);
	}
	public static String Get(int ix)
	{
		if(ix < 0 || ix >= LID_MAX_COUNT)
			return "";
		
		return m_language[ix];
	}
}
