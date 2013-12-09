package dsd.model.enums;

public enum eCalculatedDataType
{
	TenMinutes(1), OneHour(2), OneDay(3);

	private int code;
	private eCalculatedDataType(int c)
	{
		code = c;
	}
	public int getCode()
	{
		return code;
	}

}
