package com.code.dagger57;

public class KeyStrategy {

    public String generateKey(String line){
        return org.apache.commons.codec.digest.DigestUtils.sha256Hex(line);
    }
}
