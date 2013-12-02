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
