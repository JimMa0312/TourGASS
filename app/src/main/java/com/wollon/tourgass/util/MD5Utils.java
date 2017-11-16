package com.wollon.tourgass.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by JimMa on 2017/11/15.
 */

public class MD5Utils {
    public final static String PASSWOELD="f;alksnflaskdf;aoielwknas123123123dl;alsidknfaPWAASW";
    public final static String USEREN="qoi2ehna;sodfu8p2qo83e28r;fiwe0qw!*&#liqonqUSer";
    /**
     * MD5加密
     * @param exString 明文
     * @param mode 加密方式
     * @return
     */
    public static String getEnMD5(String exString, String mode){
        try{
            //创建加密对象
            MessageDigest digest=MessageDigest.getInstance("md5");
            exString+=mode;
            digest.update(exString.getBytes());
            byte[] b=digest.digest();
            int i;
            StringBuffer buf=new StringBuffer("");
            for(int offset=0;offset<b.length;offset++){
                i=b[offset];
                if(i<0){
                    i+=256;
                }
                if(i<16){
                    buf.append("0");
                }
                buf.append(Integer.toHexString(i));
            }

            return buf.toString();
        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
            return null;
        }
    }
}
