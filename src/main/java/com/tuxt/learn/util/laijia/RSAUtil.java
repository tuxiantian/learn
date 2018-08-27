
package com.tuxt.learn.util.laijia;



import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class RSAUtil {
    public static final String SIGN_ALGORITHMS_MD5 = "MD5withRSA";
    public static final int MAX_DECRYPT_BLOCK = 128;

    /**
     * RSA验签名检查
     *
     * @param content           待签名数据
     * @param sign              签名值
     * @param publicKey        公钥
     * @return 布尔值
     */
    public static boolean verify(String content, String sign, String publicKey) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            byte[] encodedKey = Base64.decode(publicKey);
            PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));

            Signature signature = Signature
                    .getInstance(SIGN_ALGORITHMS_MD5);

            signature.initVerify(pubKey);
            signature.update(content.getBytes());

            return signature.verify(Base64.decode(new String(Base64.decode(sign))));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * RSA 私钥签名
     * @param privateKey
     * @param data
     * @return
     * @throws SignatureException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws InvalidKeyException
     * @throws UnsupportedEncodingException
     */
    public static String getsign(String data, String privateKey) throws SignatureException, NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, UnsupportedEncodingException {
        Base64 base64 = new Base64();
        byte[] keyBytes = base64.decode(privateKey);//对私钥做base64解码
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);
        Signature signature = Signature.getInstance(SIGN_ALGORITHMS_MD5);
        signature.initSign(priKey);
        signature.update(data.getBytes("UTF-8"));//data为要生成签名的源数据字节数组
        return base64.encode(base64.encode(signature.sign()).getBytes());
    }

    /**
     * RSA 公钥加密
     * @param content
     * @param publicKey
     * @return
     */
    public static String RSAEncrypt(String content, String publicKey) {
        try {
            KeyFactory keyf = KeyFactory.getInstance("RSA");
            PublicKey pubKey = keyf.generatePublic(new X509EncodedKeySpec(Base64.decode(publicKey)));
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);
            byte[] cipherText = cipher.doFinal(content.getBytes());
            return Base64.encode(cipherText);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * RSA 私钥解密
     * @param content
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static String RSADecrypt(String content, String privateKey) throws Exception{
        String dedata = "";
        ByteArrayOutputStream out = null;
        try{
            String replacedata = content.replace(" ", "+");
            byte[] keyBytes = Base64.decode(privateKey);
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.DECRYPT_MODE, privateK);
            byte base64dedata[] = Base64.decode(replacedata);
            int inputLen = base64dedata.length;
            out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段解密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                    cache = cipher.doFinal(base64dedata, offSet, MAX_DECRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(base64dedata, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_DECRYPT_BLOCK;
            }
            byte[] decryptedData = out.toByteArray();
            dedata = new String(decryptedData, "UTF-8");
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            out.flush();
            out.close();
        }
        return dedata;
    }
}
