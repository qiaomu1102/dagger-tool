package com.springdagger.core.tool.utils.security;

/**
 * @author liu yucheng
 * @date 2018/12/13
 */
public class CustomKeyPair {

    private String publicKey;

    private String privateKey;

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public CustomKeyPair(String publicKey, String privateKey) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    public static CustomKeyPair of(String publicKey, String privateKey) {
        return new CustomKeyPair(publicKey, privateKey);
    }

}
