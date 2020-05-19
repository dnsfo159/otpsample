package com.oauth.test.login;

import org.apache.commons.codec.binary.Base32;


import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

public class OTPUtil {

    private static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";
    //생성?
    public static HashMap<String , String > generate(String name , String host){
        HashMap<String,String> map = new HashMap<String, String>();
        byte[] buffer = new byte[5 + 5 * 5];
        new Random().nextBytes(buffer);
//        Base32
        Base32 codec = new Base32();
        byte[] secretKey = Arrays.copyOf(buffer , 10);
        byte[] bEncodeedKey = codec.encode(secretKey);

        String encodeedKey = new String(bEncodeedKey);

        map.put("encodeedKey", encodeedKey);
//        map.put("url",url);       //???????


        return map;
    }
    //코드 체크
    public static boolean checkCode(String userCode, String otpkey){
        long otpnum = Integer.parseInt(userCode);
        long wave = new Date().getTime() / 30000;

        boolean result = false;

        try{
            Base32 codec = new Base32();
            byte[] decodedKey = codec.decode(otpkey);
            int window = 3;
            for(int i = -window; i <= window; ++i){
                long hash = verify_code(decodedKey , wave + i);
                if(hash == otpnum) result = true;
            }
        } catch (NoSuchAlgorithmException | InvalidKeyException e){
            e.printStackTrace();
        }

        return result;
    }


    private static int verify_code(byte[] key , long t) throws NoSuchAlgorithmException, InvalidKeyException{
        byte[] data = new byte[8];
        long value = t;
        for(int i = 8; i-- > 0; value >>>=8){
            data[i] = (byte) value;
        }

        SecretKeySpec signKey = new SecretKeySpec(key, "HmacSHA1");
        Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(signKey);
        byte[] hash = mac.doFinal(data);

        int offset = hash[20 -1] & 0xF;

        long truncatedHash = 0;
        for(int i = 0 ;  i < 4 ; ++i){
            truncatedHash <<= 8;

            truncatedHash |= (hash[offset + i] & 0xFF);
        }

        truncatedHash &= 0x7FFFFFFF;
        truncatedHash %= 1000000;


        return (int) truncatedHash;

    }

}
