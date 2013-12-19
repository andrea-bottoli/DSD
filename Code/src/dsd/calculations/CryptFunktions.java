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
package dsd.calculations;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CryptFunktions {

	public static String MD5(String message) {
		MessageDigest md;

		try {
			md = MessageDigest.getInstance("MD5");
			md.update(message.getBytes());

			StringBuffer sbMD5SUM = new StringBuffer();
			byte[] digest = md.digest();

			for (byte d : digest) {
				sbMD5SUM.append(Integer.toHexString((d & 0xFF) | 0x100)
						.toLowerCase().substring(1, 3));
			}

			return sbMD5SUM.toString();

		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

}
