package com.wollon.tourgass.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

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

    public static String sHA1(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_SIGNATURES);
            byte[] cert = info.signatures[0].toByteArray();
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] publicKey = md.digest(cert);
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < publicKey.length; i++) {
                String appendString = Integer.toHexString(0xFF & publicKey[i])
                        .toUpperCase(Locale.US);
                if (appendString.length() == 1)
                    hexString.append("0");
                hexString.append(appendString);
                hexString.append(":");
            }
            String result = hexString.toString();
            return result.substring(0, result.length()-1);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
