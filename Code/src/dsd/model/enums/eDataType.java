package dsd.model.enums;

public enum eDataType
{
	TenMinutes(1), OneHour(2), OneDay(3);

	private int code;
	private eDataType(int c)
	{
		code = c;
	}
	public int getCode()
	{
		return code;
	}

}
