package org.jianeng.books.utils;

import org.springframework.stereotype.Component;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * @author wu_jianeng@foxmail.com
 * @date 2021/6/11 20:31
 */
@Component
public class RsaUtils {
    /** PrivateKey * 生成秘钥 &gt; openssl genrsa -out rsa_private_key.pem 2048
     * 转换成PKCS8格式 &gt;openssl pkcs8 -topk8 -inform * PEM -in rsa_private_key.pem -outform PEM -nocrypt
     * 在终端输出结果，去掉“-----BEGIN PRIVATE KEY-----” * “-----END PRIVATE KEY-----”
     * @return PrivateKey
     */
    public static PrivateKey getPrivateKey() {
        PrivateKey privateKey = null;
        try {
            String privateKeyStr = "PrivateKey";
            // PKCS8格式的密钥
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyStr));
            // RSA 算法
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePrivate(keySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return privateKey;
    }

    /** PublicKey 根据 秘钥 生成public key &gt; openssl rsa -in rsa_private_key.pem -out rsa_public_key.pem -pubout
     * @return PublicKey
     */
    public static PublicKey getPublicKey() {
        PublicKey publicKey = null;
        try {
            String publicKeyStr = "public key";
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyStr));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
            return null;
        }
    }
}