package dsd.model.enums;

public enum eCameraType
{
	Mantova(1), Modena(2);

	private int code;
	private eCameraType(int c)
	{
		code = c;
	}

	public int getCode()
	{
		return code;
	}
}
