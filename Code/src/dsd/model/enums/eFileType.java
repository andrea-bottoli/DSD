package dsd.model.enums;

public enum eFileType
{
	Sonar(1), Analog(2), Mantova(3), Modena(4);
	
	private int code;
	private eFileType(int c)
	{
		code = c;
	}

	public int getCode()
	{
		return code;
	}
}
