package xyz.acproject.danmuji.utils;

/**
 * @author Admin
 * @ClassName HexUtils
 * @Description TODO
 * @date 2023/4/19 18:48
 * @Copyright:2023
 * @From org.apache.tomcat.util.buf
 */
public class HexUtils {


    // -------------------------------------------------------------- Constants

    /**
     *  Table for HEX to DEC byte translation.
     */
    private static final int[] DEC = {
            00, 01, 02, 03, 04, 05, 06, 07,  8,  9, -1, -1, -1, -1, -1, -1,
            -1, 10, 11, 12, 13, 14, 15, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, 10, 11, 12, 13, 14, 15,
    };


    /**
     * Table for DEC to HEX byte translation.
     */
    private static final byte[] HEX =
            { (byte) '0', (byte) '1', (byte) '2', (byte) '3', (byte) '4', (byte) '5',
                    (byte) '6', (byte) '7', (byte) '8', (byte) '9', (byte) 'a', (byte) 'b',
                    (byte) 'c', (byte) 'd', (byte) 'e', (byte) 'f' };


    /**
     * Table for byte to hex string translation.
     */
    private static final char[] hex = "0123456789abcdef".toCharArray();


    // --------------------------------------------------------- Static Methods

    public static int getDec(int index) {
        // Fast for correct values, slower for incorrect ones
        try {
            return DEC[index - '0'];
        } catch (ArrayIndexOutOfBoundsException ex) {
            return -1;
        }
    }


    public static byte getHex(int index) {
        return HEX[index];
    }


    public static String toHexString(char c) {
        // 2 bytes / 4 hex digits
        StringBuilder sb = new StringBuilder(4);

        sb.append(hex[(c & 0xf000) >> 12]);
        sb.append(hex[(c & 0x0f00) >> 8]);

        sb.append(hex[(c & 0xf0) >> 4]);
        sb.append(hex[(c & 0x0f)]);

        return sb.toString();
    }


    public static String toHexString(byte[] bytes) {
        if (null == bytes) {
            return null;
        }

        StringBuilder sb = new StringBuilder(bytes.length << 1);

        for (byte aByte : bytes) {
            sb.append(hex[(aByte & 0xf0) >> 4])
                    .append(hex[(aByte & 0x0f)])
            ;
        }

        return sb.toString();
    }


    public static byte[] fromHexString(String input) {
        if (input == null) {
            return null;
        }

        if ((input.length() & 1) == 1) {
            // Odd number of characters
            throw new IllegalArgumentException("Input string must contain an even number of characters");
        }

        char[] inputChars = input.toCharArray();
        byte[] result = new byte[input.length() >> 1];
        for (int i = 0; i < result.length; i++) {
            int upperNibble = getDec(inputChars[2*i]);
            int lowerNibble =  getDec(inputChars[2*i + 1]);
            if (upperNibble < 0 || lowerNibble < 0) {
                // Non hex character
                throw new IllegalArgumentException("Input string must only contain hex digits");
            }
            result[i] = (byte) ((upperNibble << 4) + lowerNibble);
        }
        return result;
    }
}
