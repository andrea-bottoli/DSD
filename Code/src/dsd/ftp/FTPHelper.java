package dsd.ftp;

import java.io.IOException;

import it.sauronsoftware.ftp4j.*;

public class FTPHelper
{

	public void trySOmething()
	{
		try
		{
			FTPClient client = new FTPClient();
			client.connect("ftp.host.com", 8080);
			client.login("", "");
			
			FTPFile[] list = client.list();
			
			for (FTPFile file : list)
			{
				client.download(file.getName(), new java.io.File(file.getName()));
			}
			
			client.disconnect(true);
		}
		catch (IllegalStateException e)
		{
			
			e.printStackTrace();
		}
		catch (IOException e)
		{
			
			e.printStackTrace();
		}
		catch (FTPIllegalReplyException e)
		{
			
			e.printStackTrace();
		}
		catch (FTPException e)
		{
			
			e.printStackTrace();
		}
		catch (FTPDataTransferException e)
		{
			
			e.printStackTrace();
		}
		catch (FTPAbortedException e)
		{
			
			e.printStackTrace();
		}
		catch (FTPListParseException e)
		{
			
			e.printStackTrace();
		}
	}
}
