package cn.aresoft.common.util;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AESUtil {

	private static final String AES = "AES";

	private static final String CRYPT_KEY = "HNHNhhhhHNHNnnnn";

	/**
	 * 加密
	 * 
	 * @param encryptStr
	 * @return
	 */
	public static byte[] encrypt(byte[] src, String key) throws Exception {
		Cipher cipher = Cipher.getInstance(AES);
		SecretKeySpec securekey = new SecretKeySpec(key.getBytes(), AES);
		cipher.init(Cipher.ENCRYPT_MODE, securekey);
		return cipher.doFinal(src);
	}

	/**
	 * 解密
	 * 
	 * @param decryptStr
	 * @return
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] src, String key) throws Exception {
		Cipher cipher = Cipher.getInstance(AES);
		SecretKeySpec securekey = new SecretKeySpec(key.getBytes(), AES);
		cipher.init(Cipher.DECRYPT_MODE, securekey);
		return cipher.doFinal(src);
	}

	/**
	 * 
	 * 
	 * @param b
	 * @return
	 */
	public static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
		}
		return hs.toUpperCase();
	}

	public static byte[] hex2byte(byte[] b) {
		if ((b.length % 2) != 0)
			throw new IllegalArgumentException("长度不是偶数");
		byte[] b2 = new byte[b.length / 2];
		for (int n = 0; n < b.length; n += 2) {
			String item = new String(b, n, 2);
			b2[n / 2] = (byte) Integer.parseInt(item, 16);
		}
		return b2;
	}

	/**
	 * 解密
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public final static String decrypt(String data) {
		try {
			return new String(decrypt(hex2byte(data.getBytes()), CRYPT_KEY),"UTF-8");
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * 加密
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public final static String encrypt(String data) {
		try {
			return byte2hex(encrypt(data.getBytes("UTF-8"), CRYPT_KEY));
		} catch (Exception e) {
		}
		return null;
	}

	public static void main(String[] args) throws Exception{
		
		String idDecrypt = AESUtil.decrypt("153BE2CA1AD86562BCD54A499EE941A3CE4AE92FD25402D905AF9C620F8BED5BAD70871C30CDED38CD0133C1F2403D4FB01586AEF7F45E54E8AAF71318102A24063A235731A72EF2FDE5B0BE22BD82A8E85C6CF69F87B8B4CDA5D6E0EC2026CA9566717C7265AC6FA918948ADF60767EC45B4294FF926E83C338C5B6BCFC547E5FD8544E0BDB7BBF4A09F9C4901F310AE8A74AE51D0D38181D9F9C1BF377EDB859B2EDCF3DE2E2E86C652489E37A76CD26E6EBA0A79048560DDF3663D886E3FF28C0DE2BE2C6DD194765D132F790CAFB39BB08953A3BCE43E40DE68E5AF73D5B83B73F17E29578BEAD73FDF202BEEFAF1E0977332BDDFF784EC2C2F34D67A9F38205757B42D90DBC9373BB4DC9961CC96FD99A337AFC4F7A06D1FA624CCC10C624019D04356878F8E07FAC2222BAAA897DEF568C3B52064CECFFDB65BB32A1253CCACB621E84714AC5E47EC3B4FDC3B0E25425B2659C5D402EEA288E5CA0ED6BB930F70537848E25C684C2AB3BF8566A665EC97B3E08DBADBA656ABAA6C54E84B5148DACAD16F65C1FDE865A3985330BF6B02C2F264A5837452A3E3BAD1B8617C4CEEC5E2A935624C6E7D82E1F16B53C7F2125218B45D03A72C1380F3F0F49C34AA5C0B1248E1BF81222FCA2E9352FE67F93633FA761D9A383A53A67B1FFE6554229060B9EF2BD924FBE95CE6BF5E2856F915C92DE2CAD7B8BE6463C5D49154D6B5842720EDE9A0B33436ABCA544C7332C241DB3144AD9C942841C7197DEE580F4F81882E709B9D26B58AA1327E5FEA31D63078362CAAD9496E2CBD36FF0A2AF01F06BAF29F9FA30C33655C07B9ED9A17E01050288CF5545DCD88F70CB977C8D2956F68BBFD3311D9AB2D81F4EE2BCD043C2446F85D641C2308DB2D816C40F27EDA55BBB8C92B3B4A52D4DE17E503B511CA3C2A2D8EA50D6A73FDA4832BC743DE7EC74E25BD41A41B48992F43060FFAAD5348A73BD13836D1329405439D6CC39");
		System.out.println(idDecrypt);
		System.err.println(AESUtil.encrypt("/"));
		System.err.println(AESUtil.decrypt("A6E12C102D5582BACEBC6802B941FF69"));
	}
}
