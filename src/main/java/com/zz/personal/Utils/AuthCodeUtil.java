package com.zz.personal.Utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Random;

public class AuthCodeUtil {
    private static Base64 base64;
    private static Logger logger=null;
    static {
        base64=new Base64();
        logger = LoggerFactory.getLogger(AuthCodeUtil.class);
    }
    //字符串分割
    private static String CutString(String str, int startIndex, int length) {
        if (startIndex >= 0) {
            if (length < 0) {
                length = length * -1;
                if (startIndex - length < 0) {
                    length = startIndex;
                    startIndex = 0;
                } else {
                    startIndex = startIndex - length;
                }
            }
            if (startIndex > str.length()) {
                return "";
            }
        } else {
            if (length < 0) {
                return "";
            } else {
                if (length + startIndex > 0) {
                    length = length + startIndex;
                    startIndex = 0;
                } else {
                    return "";
                }
            }
        }
        if (str.length() - startIndex < length) {
            length = str.length() - startIndex;
        }
        return str.substring(startIndex, startIndex + length);
    }

    /**
     * 字符串分割
     * @param str
     * @param startIndex
     * @return
     */
    private static String CutString(String str, int startIndex) {
        return CutString(str, startIndex, str.length());
    }

    /**
     * 填充rc4 box
     * @param pass
     * @param kLen
     * @return
     */
    static private byte[] GetKey(byte[] pass, int kLen) {
        byte[] mBox = new byte[kLen];
        for (int i = 0; i < kLen; i++) {
            mBox[i] = (byte) i;
        }
        int j = 0;
        for (int i = 0; i < kLen; i++) {
            j = (j + (int) ((mBox[i] + 256) % 256) + pass[i % pass.length])
                    % kLen;
            byte temp = mBox[i];
            mBox[i] = mBox[j];
            mBox[j] = temp;
        }
        return mBox;
    }

    /**
     * 生成随机字符
     * @param lens 随机长度
     * @return
     */
    private static String RandomString(int lens) {
        char[] CharArray = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k',
                'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
                'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
        int clens = CharArray.length;
        String sCode = "";
        Random random = new Random();
        for (int i = 0; i < lens; i++) {
            sCode += CharArray[Math.abs(random.nextInt(clens))];
        }
        return sCode;
    }
    public static String Encode(String source, String key){
        return Encode(source,key,0);
    }
    public static String Encode(String source, String key,int expiry) {
        StringBuilder sb=new StringBuilder();
        try {
            if (source == null || key == null) {
                return "";
            }
            int ckey_length = 4;
            String keya, keyb, keyc, cryptkey;
            key = md5lowString(key);
            keya = md5lowString(CutString(key, 0, 16));
            keyb = md5lowString(CutString(key, 16, 16));
            keyc = ckey_length > 0 ? RandomString(ckey_length) : "";
            cryptkey = keya + md5lowString(keya + keyc);
            if(expiry>0){
                sb.append(String.format("%010d",(new Date().getTime()/1000)+expiry));
            }else{
                sb.append("0000000000");
            }
            sb.append(CutString(md5lowString(source + keyb), 0, 16));
            sb.append(source);
            byte[] temp = RC4(sb.toString().getBytes("UTF-8"), cryptkey);
            return keyc + new String(base64.encode(temp));

        } catch (Exception e) {
            return "";
        }

    }

    public static String Decode(String source, String key) {

        try {
            if (source == null || key == null) {
                return "";
            }
            int ckey_length = 4;
            String keya, keyb, keyc, cryptkey, result;
            key = md5lowString(key);
            keya = md5lowString(CutString(key, 0, 16));
            keyb = md5lowString(CutString(key, 16, 16));
            keyc = ckey_length > 0 ? CutString(source, 0, ckey_length) : "";
            cryptkey = keya + md5lowString(keya + keyc);

            byte[] temp;
            temp = base64.decode(CutString(source, ckey_length));
            result = new String(RC4(temp, cryptkey));
            if(CutString(result,0,10).equals("0000000000")){
                logger.warn("util decode an indefinitely data,解密一个没有期限的密匙");
                return CutString(result, 26);
            }
            Long time=new Date().getTime()/1000;
            Long dtime=Long.valueOf(CutString(result,0,10));
            if(time<dtime){
                if (CutString(result, 10, 16).equals(
                        CutString(md5lowString(CutString(result, 26) + keyb), 0, 16))) {
                    return CutString(result, 26);
                }
            }else{
                logger.error("data expried 数据过期"+CutString(result, 26));
            }
            return "";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

    }

    private static byte[] RC4(byte[] input, String pass) {
        if (input == null || pass == null)
            return null;
        byte[] output = new byte[input.length];
        byte[] mBox = GetKey(pass.getBytes(), 256);
        int i = 0;
        int j = 0;
        for (int offset = 0; offset < input.length; offset++) {
            i = (i + 1) % mBox.length;
            j = (j + (int) ((mBox[i] + 256) % 256)) % mBox.length;
            byte temp = mBox[i];
            mBox[i] = mBox[j];
            mBox[j] = temp;
            byte a = input[offset];
            // byte b = mBox[(mBox[i] + mBox[j] % mBox.Length) % mBox.Length];
            byte b = mBox[(toInt(mBox[i]) + toInt(mBox[j])) % mBox.length];
            output[offset] = (byte) ((int) a ^ (int) toInt(b));
        }
        return output;
    }
    private static String md5lowString(String MD5) {
        return DigestUtils.md5Hex(MD5).toLowerCase();
    }
    private static int toInt(byte b) {
        return (int) ((b + 256) % 256);
    }

    public static void main(String[] args) {
        System.out.println(AuthCodeUtil.Decode("086fuQ55p3pJZLKgsjJ+3Z/8v9uamtth/3a1V5n2mVBfa1Bdkg","123456"));
    }
}
