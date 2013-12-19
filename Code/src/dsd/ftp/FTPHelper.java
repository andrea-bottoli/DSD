/*******************************************************************************
 * Copyright 2013 Andrea Bottoli, Lorenzo Pagliari, Marko Br?i?, Dzana Kujan, Nikola Radisavljevic, Jörn Tillmanns, Miraldi Fifo
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
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
