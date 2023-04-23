package xyz.acproject.danmuji.utils;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.InflaterOutputStream;

/**
 * @ClassName ByteUtils
 * @Description TODO
 * @author BanqiJane
 * @date 2020年8月10日 下午12:31:34
 *
 * @Copyright:2020 blogs.acproject.xyz Inc. All rights reserved.
 */
public class ByteUtils {
	public static final int UNICODE_LEN = 2;
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
	 * @param hex 待转换的Hex字符串
	 * @return 转换后的byte数组结果
	 */
	public static byte[] hexToByteArray(String hex) {
		 int m = 0, n = 0;
	        int byteLen = hex.length() / 2; // 每两个字符描述一个字节
	        byte[] ret = new byte[byteLen];
	        for (int i = 0; i < byteLen; i++) {
	            m = i * 2 + 1;
	            n = m + 1;
	            int intVal = Integer.decode("0x" + hex.substring(i * 2, m) + hex.substring(m, n));
	            ret[i] = Byte.valueOf((byte)intVal);
	        }
	        return ret;
	}

	/**
	 * 将两个byte数组拼接为单独对象
	 * 
	 * @param byte_1  待拼接byte数组1
	 * @param byte_2  待拼接byte数组1
	 * @return 返回拼接后的byte数组
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
	 * @param src 待转byte数组
	 * @return hex16进制字符串
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
	 * @param bytes 待转bytebuffer
	 * @return 转换后的byte数组
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
	 * @param s 待转hex16进制
	 * @return 转换后的string类型字符串
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
			s = new String(baKeyword, "utf-8");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return s;
	}
	/**
	 * hex16进制字符串转为zlib并解压成字符串
	 * 
	 * @param s 待转hex16进制字符串
	 * @return 经解压后的string字符串
	 */
	public static String hexStringTozlibInflateString(String s) { 
		if (s == null || s.equals("")) {
			return null;
		}
		s= s.replace(" ", "");
        try {  
            ByteArrayOutputStream bos = new ByteArrayOutputStream();  
            InflaterOutputStream zos = new InflaterOutputStream(bos);  
            zos.write(HexUtils.fromHexString(s));
            zos.close();  
//            s =new String(bos.toByteArray(),"utf-8");  
        } catch (Exception ex) {  
            ex.printStackTrace();  
        }  
        return s;
    }
	/**
	 * byte[]转为zlib并解压成字符串
	 * 
	 * @param bs 待转byte数组
	 * @return 
	 */
	public static String BytesTozlibInflateString(byte[] bs) { 
		String s="";
        try {  
            ByteArrayOutputStream bos = new ByteArrayOutputStream();  
            InflaterOutputStream zos = new InflaterOutputStream(bos);  
            zos.write(bs);
            zos.close();  
            s =new String(bos.toByteArray(),"utf-8");  
        } catch (Exception ex) {  
            ex.printStackTrace();  
        }  
        return s;
    }
	/**
	 * byte[]的zlib解压
	 * 
	 * @param bs 待解压byte数组
	 * @return b 解压完成的byte[]
	 */
	public static byte[] BytesTozlibInflate(byte[] bs) {
		byte[] b = null;
		ByteArrayOutputStream bos = null;
		InflaterOutputStream zos = null;
		try {
			bos = new ByteArrayOutputStream();
			zos = new InflaterOutputStream(bos);
			zos.write(bs);
			zos.close();
			b = bos.toByteArray();
			return b;
		} catch (Exception ex) {
			ex.printStackTrace();
		}finally {
			if(bos!=null){
				try {
					bos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return b;
    }
	
	/**
	 * unicode转string字符串
	 * 
	 * @param s 待转unicode编码
	 * @return 经转换后string字符串
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
	
	/**
	 * 截取byte数组的一部分
	 * 
	 * @param bytes 待截取的byte数组
	 * @param begin 开始截取的位置
	 * @param count 截取的字节
	 * @return 截取后的byte数组
	 */
	public static byte[] subBytes(byte[] bytes,int begin,int count) {
//		byte[] bs = new byte[count];
//		for(int i =begin;i<begin+count;i++) bs[i-begin] =bytes[i];
		byte[] bs = new byte[count];
		System.arraycopy(bytes, begin, bs, 0, count);
		return bs;
	}
	/**
     * 转换byte数组为int（大端）
     * @return
     * @note 数组长度至少为4，按小端方式转换，即传入的bytes是大端的，按这个规律组织成int
     */
    public static int Bytes2Int_BE(byte[] bytes){
    	int addr;
        if(bytes.length==1){
            addr = bytes[0] & 0xFF;
        }else{
            addr = bytes[0] & 0xFF;
            addr = (addr << 8) | (bytes[1] & 0xff) ;
            addr = (addr << 8) | (bytes[2] & 0xff) ;
            addr = (addr << 8) | (bytes[3] & 0xff) ;
        }
        return addr;

    }
    /**
     * 转换byte数组为char（大端）
     * @return
     * @note 数组长度至少为2，按小端方式转换
     */
    public static char Bytes2Char_BE(byte[] bytes){
        if(bytes.length < 2)
            return (char)-1;
        int iRst = (bytes[0] << 8) & 0xFF;
        iRst |= bytes[1] & 0xFF;
        return (char)iRst;
    }
    /**
     * 转换byte数组为int（小端）
     * @return
     * @note 数组长度至少为4，按小端方式转换,即传入的bytes是小端的，按这个规律组织成int
     */
    public static int Bytes2Int_LE(byte[] bytes){
        if(bytes.length < 4)
            return -1;
        int iRst = (bytes[0] & 0xFF);
        iRst |= (bytes[1] & 0xFF) << 8;
        iRst |= (bytes[2] & 0xFF) << 16;
        iRst |= (bytes[3] & 0xFF)<< 24;
 
        return iRst;
    }

    /**
     * 转换byte数组为Char（小端）
     * @return
     * @note 数组长度至少为2，按小端方式转换
     */
    public static char Bytes2Char_LE(byte[] bytes){
        if(bytes.length < 2)
            return (char)-1;
        int iRst = (bytes[0] & 0xFF);
        iRst |= (bytes[1] & 0xFF) << 8;       
 
        return (char)iRst;
    }
    /**
     * 转换字符数组为定长byte[]
     * @param chars              字符数组
     * @return 若指定的定长不足返回null, 否则返回byte数组
     */
    public static byte[] Chars2Bytes_LE(char[] chars){
        if(chars == null)
            return null;
 
        int iCharCount = chars.length;       
        byte[] rst = new byte[iCharCount*UNICODE_LEN];
        int i = 0;
        for( i = 0; i < iCharCount; i++){
            rst[i*2] = (byte)(chars[i] & 0xFF);
            rst[i*2 + 1] = (byte)(( chars[i] & 0xFF00 ) >> 8);
        }   
 
        return rst;
    }
    /**
     * 转换String为byte[]
     * @param str
     * @return
     */
    public static byte[] String2Bytes_LE(String str) {
        if(str == null){
            return null;
        }
         char[] chars = str.toCharArray();
 
         byte[] rst = Chars2Bytes_LE(chars);
 
         return rst;
    }
    /**
	 * hex16进制字符串转为zlib并解压成字符串
	 * 
	 * @param s 待转hex16进制字符串
	 * @return 经解压后的string字符串
	 */
	public static byte[] hexStringTozlibInflateByteArray(String s) { 
		if (s == null || s.equals("")) {
			return null;
		}
		s= s.replace(" ", "");
		byte[] bs = null;
        try {  
            ByteArrayOutputStream bos = new ByteArrayOutputStream();  
            InflaterOutputStream zos = new InflaterOutputStream(bos);  
            zos.write(HexUtils.fromHexString(s));
            zos.close();  
            bs= bos.toByteArray();
        } catch (Exception ex) {  
            ex.printStackTrace();  
        }  
        return bs;
    }
	/**
	 * byte[] 转long
	 * 
	 * @param bs
	 * @return
	 * @throws Exception
	 */
	public static long byteslong(byte[] bs)  throws Exception {
        int bytes = bs.length;
        if(bytes > 1) {
        if((bytes % 2) != 0 || bytes > 8) {
            throw new Exception("not support");
        }}
        switch(bytes) {
        case 0:
            return 0;
        case 1:
            return (long)((bs[0] & 0xff));
        case 2:
            return (long)((bs[0] & 0xff) <<8 | (bs[1] & 0xff));
        case 4:
            return (long)((bs[0] & 0xffL) <<24 | (bs[1] & 0xffL) << 16 | (bs[2] & 0xffL) <<8 | (bs[3] & 0xffL));
        case 8:
            return (long)((bs[0] & 0xffL) <<56 | (bs[1] & 0xffL) << 48 | (bs[2] & 0xffL) <<40 | (bs[3] & 0xffL)<<32 | 
                    (bs[4] & 0xffL) <<24 | (bs[5] & 0xffL) << 16 | (bs[6] & 0xffL) <<8 | (bs[7] & 0xffL));
        default:
            throw new Exception("not support");     
        }
        //return 0;
    }
}
