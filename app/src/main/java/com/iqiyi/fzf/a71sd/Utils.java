package com.iqiyi.fzf.a71sd;

/**
 * Created by fzf on 17-11-2.
 */

public class Utils {

    /**
     * @return 长整型
     * @方法功能 字节数组和长整型的转换
     */
    public static byte[] longToByte(long number) {
        long temp = number;
        byte[] b = new byte[8];
        for (int i = 0; i < b.length; i++) {
            b[i] = new Long(temp & 0xff).byteValue();
            // 将最低位保存在最低位
            temp = temp >> 8;
            // 向右移8位
        }
        return b;
    }

    /**
     * @return 长整型
     * @方法功能 字节数组和长整型的转换
     */
    public static long byteToLong(byte[] b) {
        long s = 0;
        long s0 = b[0] & 0xff;// 最低位
        long s1 = b[1] & 0xff;
        long s2 = b[2] & 0xff;
        long s3 = b[3] & 0xff;
        long s4 = b[4] & 0xff;// 最低位
        long s5 = b[5] & 0xff;
        long s6 = b[6] & 0xff;
        long s7 = b[7] & 0xff; // s0不变
        s1 <<= 8;
        s2 <<= 16;
        s3 <<= 24;
        s4 <<= 8 * 4;
        s5 <<= 8 * 5;
        s6 <<= 8 * 6;
        s7 <<= 8 * 7;
        s = s0 | s1 | s2 | s3 | s4 | s5 | s6 | s7;
        return s;
    }


    public static void int2ByteL(byte[] targets, int res) {
        targets[0] = (byte) (res & 0xff);// 最低位
        targets[1] = (byte) ((res >> 8) & 0xff);// 次低位
        targets[2] = (byte) ((res >> 16) & 0xff);// 次高位
        targets[3] = (byte) (res >>> 24);// 最高位,无符号右移。
    }

    public static void int2ByteH(byte[] targets, int res) {
        targets[3] = (byte) (res & 0xff);// 最高位
        targets[2] = (byte) ((res >> 8) & 0xff);
        targets[1] = (byte) ((res >> 16) & 0xff);
        targets[0] = (byte) (res >>> 24);
    }
}
