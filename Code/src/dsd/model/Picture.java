package dsd.model;

public class Picture
{

	private int ID;
	private String Path;
	private int Camera;
	private long timestamp;

	public long getTimestamp()
	{
		return timestamp;
	}
	public void setTimestamp(long timestamp)
	{
		this.timestamp = timestamp;
	}
	public int getID()
	{
		return ID;
	}
	public void setID(int iD)
	{
		ID = iD;
	}
	public String getPath()
	{
		return Path;
	}
	public void setPath(String path)
	{
		Path = path;
	}
	public int getCamera()
	{
		return Camera;
	}
	public void setCamera(int camera)
	{
		Camera = camera;
	}

}
