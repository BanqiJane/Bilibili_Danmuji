package xyz.acproject.danmuji.tools;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.InflaterOutputStream;

import org.apache.tomcat.util.buf.HexUtils;

public class ByteUtils {
	/**
	 * Hex字符串转byte
	 * 
	 * @param inHex 待转换的Hex字符串
	 * @return 转换后的byte
	 */
	public static byte hexToByte(String inHex) {
		return (byte) Integer.parseInt(inHex, 16);
	}

	/**
	 * hex字符串转byte数组
	 * 
	 * @param inHex 待转换的Hex字符串
	 * @return 转换后的byte数组结果
	 */
	public static byte[] hexToByteArray(String inHex) {
		int hexlen = inHex.length();
		byte[] result;
		if (hexlen % 2 == 1) {
			// 奇数
			hexlen++;
			result = new byte[(hexlen / 2)];
			inHex = "0" + inHex;
		} else {
			// 偶数
			result = new byte[(hexlen / 2)];
		}
		int j = 0;
		for (int i = 0; i < hexlen; i += 2) {
			result[j] = hexToByte(inHex.substring(i, i + 2));
			j++;
		}
		return result;
	}

	/**
	 * 将两个byte数组拼接为单独对象
	 * 
	 * @param byte_1
	 * @param byte_2
	 * @return
	 */
	public static byte[] byteMerger(byte[] byte_1, byte[] byte_2) {
		byte[] byte_3 = new byte[byte_1.length + byte_2.length];
		System.arraycopy(byte_1, 0, byte_3, 0, byte_1.length);
		System.arraycopy(byte_2, 0, byte_3, byte_1.length, byte_2.length);
		return byte_3;
	}

	/**
	 * Convert byte[] to hex
	 * string.这里我们可以将byte转换成int，然后利用Integer.toHexString(int)来转换成16进制字符串。
	 * 
	 * @param src
	 * @return
	 */
	public static String bytesToHexString(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder("");
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}

	/**
	 * 将bytebuffer转为byte数组
	 * 
	 * @param bytes
	 * @return
	 */
	public static byte[] decodeValue(ByteBuffer bytes) {
		int len = bytes.limit() - bytes.position();
		byte[] bytes1 = new byte[len];
		bytes.get(bytes1);
		return bytes1;
	}

	/**
	 * 16进制转换成为string类型字符串
	 * 
	 * @param s
	 * @return
	 */
	public static String hexStringToString(String s) {
		if (s == null || s.equals("")) {
			return null;
		}
		s = s.replace(" ", "");
		byte[] baKeyword = new byte[s.length() / 2];
		for (int i = 0; i < baKeyword.length; i++) {
			try {
				baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			s = new String(baKeyword, "UTF-8");
			new String();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return s;
	}
	/**
	 * hex字符串转为zlib并解压成字符串
	 * 
	 * @param s
	 * @return
	 */
	public static String hexStringTozlibInflate(String s) { 
		if (s == null || s.equals("")) {
			return null;
		}
		s= s.replace(" ", "");
        try {  
            ByteArrayOutputStream bos = new ByteArrayOutputStream();  
            InflaterOutputStream zos = new InflaterOutputStream(bos);  
            zos.write(HexUtils.fromHexString(s));  
            zos.close();  
            s =new String(bos.toByteArray(),"utf-8");  
        } catch (Exception ex) {  
            ex.printStackTrace();  
        }  
        return s;
    }
	
	/**
	 * unicode转字符串
	 * 
	 * @param s
	 * @return
	 */
	public static String unicodeToString(String s) {
		String unicodeCompile = "(?<=\\\\u).{4}?";
        String a;
        Matcher matcher = Pattern.compile(unicodeCompile).matcher(s);
        for (; matcher.find(); ) {
            a = matcher.group();
            s = s.replace("\\u" + a, String.valueOf((char) Integer.valueOf(a, 16).intValue()));
        }
        return s;
	}
}
