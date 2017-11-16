package com.wollon.tourgass.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by JimMa on 2017/11/15.
 */
public class MD5UtilsTest {

    @Test
    public void getEnMD5() throws Exception {
        String pwd="Myc960312..";
        String newpwd=MD5Utils.getEnMD5(pwd,MD5Utils.PASSWOELD);

        Assert.assertEquals(pwd,newpwd,"72b1b4bc308ab6faf521bb2b674ad4e6");
    }

}