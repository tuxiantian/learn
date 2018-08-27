package com.tuxt.learn.controller;

import com.tuxt.learn.util.laijia.RSAUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by tuxiantian on 2018-05-09.
 */
@Controller
@RequestMapping(value="/serve")
public class ServeController {
    //私钥
    public final static String serve_private_key = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBALCkCXsI5UEDVj0x\n" +
            "vgc7ASBT/DKiEagjl/zkaDiK3ztaOEuSvGpZ+XL5JfsacGbjiw5ACSKQ2KtphVqO\n" +
            "fbeXLu9ZwHYfHRPgT645ZsdVa5pyEuA0T/xBIquvdIBSMVqWOu9TFdVRBgBjgxFO\n" +
            "3WX4vYlp3UdUlzbSmw/ROjT6Nw6NAgMBAAECgYEApiBgWjovgkllHgBeO+aeVI0o\n" +
            "L2aFPKDdHJaj/il9cRCUyV6jnVIpbJwtwE9JTzrJWQmYjc20OOHw7Q395mBlXl4E\n" +
            "NnHOvpTQ4ilIWNeKG4pFaaX637zfdAFiXuAkQyPioYNcTPjte8+eSLurrAjUNmso\n" +
            "uj1jejk0TqzvApnJFPECQQDWv1hbegzRnWIQs3Z45ck3pXED1NjAaemvp9uLyPwl\n" +
            "7WcmlpPUhpOoo4YobrBaPXNbcKhhRgKHV8wg1LIwdQ2bAkEA0pK3bqcpql5nGOx1\n" +
            "Qy7U+zmiHN/tOh3ysL3ffnDciSqJqSiUtzeEiGRmztOwRHhH5mOPhjEGdDz35HCt\n" +
            "XZqq9wJBANNYn+nvlXway2+qX9eeNslYPoDJ0sSTuBRTPEQ3eskM+27D1Rvdkqbu\n" +
            "wNM8FL3LKqP8nQqW71gqNVC+Y9xC5y8CQQCo+U71HnMS4bo9h9H5XWVPS8pI/XAy\n" +
            "CMItDdCAeDSgZWAePBBKjeRuRpMxOTHjX9niiNYtSr3xHNOXnsufp3O1AkAiU5fb\n" +
            "DGNpU1mamEIgS6aPkupZBDNmuu5L+MOokJ6350+hOeWnUaeuD6JjiswIwYDC57r2\n" +
            "uwiWbTuclSaa3Dvf";
    //公钥
    public final static String serve_public_key = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCwpAl7COVBA1Y9Mb4HOwEgU/wy\n" +
            "ohGoI5f85Gg4it87WjhLkrxqWfly+SX7GnBm44sOQAkikNiraYVajn23ly7vWcB2\n" +
            "Hx0T4E+uOWbHVWuachLgNE/8QSKrr3SAUjFaljrvUxXVUQYAY4MRTt1l+L2Jad1H\n" +
            "VJc20psP0To0+jcOjQIDAQAB";

    //公钥
    public final static String call_public_key = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCuQ8DTkebK5oBFHhDylJlApYfAffqzZjOSPIYe8QXnubYqI0HreLGCWSELH8IqLaIoBAlY+dz1UGAKjXM0nslSU/b6uJiylY0WoP95rcqAyZUwHblsLCz1bOT+rFstxRx1BpVUQDzOFlYO45O3RJ0lLAEeZXh1a+sS+TRS8EzAJQIDAQAB";

    @ResponseBody
    @RequestMapping(value="/check",method= RequestMethod.POST)
    public void check(@RequestParam String callData,@RequestParam String requestSource,@RequestParam String requestCode,@RequestParam String version,@RequestParam String sign){
        try {
            String callDataDecrypt = RSAUtil.RSADecrypt(callData, serve_private_key);
            System.out.println(callDataDecrypt);
            boolean verify = RSAUtil.verify(requestSource.concat(requestCode).concat(version).concat(callDataDecrypt), sign, call_public_key);
            System.out.println(verify);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
