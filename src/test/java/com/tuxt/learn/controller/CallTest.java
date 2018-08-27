package com.tuxt.learn.controller;

import com.tuxt.learn.core.entity.MapBean;
import com.tuxt.learn.util.laijia.HttpUtil;
import com.tuxt.learn.util.laijia.JsonUtil;
import com.tuxt.learn.util.laijia.RSAUtil;
import org.junit.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tuxiantian on 2018-05-09.
 */
public class CallTest {
    //私钥
    public final static String call_private_key = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAK5DwNOR5srmgEUeEPKUmUClh8B9+rNmM5I8hh7xBee5tiojQet4sYJZIQsfwiotoigECVj53PVQYAqNczSeyVJT9vq4mLKVjRag/3mtyoDJlTAduWwsLPVs5P6sWy3FHHUGlVRAPM4WVg7jk7dEnSUsAR5leHVr6xL5NFLwTMAlAgMBAAECgYA/" +
            "ykFtsPzgzRurMWRWyBqJVY6p4fPBc+nw4ALglK3qs0qBhkJ9JcMkyeeUoN18fxPavg+F6ID/s5pUgo7ndjCPO4CMP+x4rlRc+1MsoKLhORfepsO4UllBuuMcS2dcQdhzbClcjQ/GRuM6LRiuvwmlUfv96d/g0pFxMAm6FnVJSQJBAPtE3g5uWOQRCEAwKJwJd1Q58Zl/rueWvHXlat4J5yzq7JZqUe0FWjo8h5+V" +
            "fUMeB2pWMKxDTz2rDccg3u7xV1MCQQCxi7hO+9bV55XtDEVE5TEyq/EfcY8anHQfu0/0GM7PHu4rheXkiEhbdD4qPsRkTo8Q+5T74gj6z/9f7kCXA/OnAkEAgsUovGH5qVij7qQhvnO+wvsVHkoxgR4WH1Wy9LQ6uEnbr7zNvomWl9CA1TE76/5o5ZLN6hlt2C56ETSuUbUROQJAMpWMPXSjEO6aWJ9WXPqvubAj" +
            "UsIX84z/yMqlTPaEdZ5qXRwZE7OAag4EaCTZ73oyaLRFDj6oME1yhPJslJoboQJBAJnYsnpm2BbT7ODItmB1LtfNtOMiaitRuLqF70Bmjv+8gP55wlexNTdRs6FuyuNyHQNNtgnG4f1AOgFP7XXIKTk=";
    //公钥
    public final static String call_public_key = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCuQ8DTkebK5oBFHhDylJlApYfAffqzZjOSPIYe8QXnubYqI0HreLGCWSELH8IqLaIoBAlY+dz1UGAKjXM0nslSU/b6uJiylY0WoP95rcqAyZUwHblsLCz1bOT+rFstxRx1BpVUQDzOFlYO45O3RJ0lLAEeZXh1a+sS+TRS8EzAJQIDAQAB";
    //公钥
    public final static String serve_public_key = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCwpAl7COVBA1Y9Mb4HOwEgU/wy\n" +
            "ohGoI5f85Gg4it87WjhLkrxqWfly+SX7GnBm44sOQAkikNiraYVajn23ly7vWcB2\n" +
            "Hx0T4E+uOWbHVWuachLgNE/8QSKrr3SAUjFaljrvUxXVUQYAY4MRTt1l+L2Jad1H\n" +
            "VJc20psP0To0+jcOjQIDAQAB";
    private final String requestCode="1001",version="1.0.1",requestSource="laijia";

    @Test
    public void test(){
        Map<String,Object> reqData = new HashMap<>();
        reqData.put("name","lilei");
        reqData.put("age",28);
        String reqDataString = JsonUtil.toJson(reqData);
        String reqDataStringEncrypt = RSAUtil.RSAEncrypt(reqDataString, serve_public_key);

        try {
            String sign = RSAUtil.getsign(requestSource.concat(requestCode).concat(version).concat(reqDataString), call_private_key);
            System.out.println(reqDataStringEncrypt);
            System.out.println(sign);
            MapBean params=new MapBean("requestCode",requestCode,"version",version,"requestSource",requestSource,"reqData",reqDataStringEncrypt,"sign",sign);
            HttpUtil.post("http://localhost:8080/learn/serve/check",params,null,null);
        } catch (SignatureException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
