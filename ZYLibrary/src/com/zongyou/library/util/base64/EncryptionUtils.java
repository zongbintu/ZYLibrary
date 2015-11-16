package com.zongyou.library.util.base64;

import java.security.MessageDigest;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apaches.commons.codec.binary.Base64;




public class EncryptionUtils {
	public static final String KEY_SHA = "SHA";  
    public static final String KEY_MD5 = "MD5";  
    public static final String KEY_MAC = "HmacMD5";  
  
	public static byte[] decryptBASE64(String key) throws Exception {  
		Base64 base64=new Base64();
        return base64.decode(key);  
    }  
  
    public static String encryptBASE64(byte[] key) throws Exception {  
    	Base64 base64=new Base64();
        return base64.encodeAsString(key);  
    }  
      
    public static byte[] encryptMD5(byte[] data) throws Exception {  
        MessageDigest md5 = MessageDigest.getInstance(KEY_MD5);  
        md5.update(data);  
        return md5.digest();  
  
    }  
    /**
     * 加密
     * @param src
     * @return
     * @throws Exception
     */
    public static String encryptMD5(String src) throws Exception {  
        return new String(encryptMD5(src.getBytes()));  
  
    }  
  
      
    public static byte[] encryptSHA(byte[] data) throws Exception {  
        MessageDigest sha = MessageDigest.getInstance(KEY_SHA);  
        sha.update(data);  
        return sha.digest();  
  
    }  
      
    public static String initMacKey() throws Exception {  
        KeyGenerator keyGenerator = KeyGenerator.getInstance(KEY_MAC);  
        SecretKey secretKey = keyGenerator.generateKey();  
        return encryptBASE64(secretKey.getEncoded());  
    }  
  
      
    public static byte[] encryptHMAC(byte[] data, String key) throws Exception {  
        SecretKey secretKey = new SecretKeySpec(decryptBASE64(key), KEY_MAC);  
        Mac mac = Mac.getInstance(secretKey.getAlgorithm());  
        mac.init(secretKey);  
        return mac.doFinal(data);  
    } 
    
    /**
     * 加密
     * @param src 要加密的字符串
     * @return
     * @throws Exception
     */
    public static EncryDTO encode(String src) throws Exception{
    	byte[]  keybyte=src.getBytes();
    	String outstr=EncryptionUtils.encryptBASE64(keybyte).trim();
    	return new EncryDTO(outstr.substring(0,1),outstr.substring(1, outstr.length()));
    }
    
    public static String decode(String src,String key) throws Exception{
    	StringBuilder sb=new StringBuilder();
    	sb.append(key);
    	sb.append(src);
    	String outstr=sb.toString();
    	byte[]  intoutbyte =EncryptionUtils.decryptBASE64(outstr);
    	String intoutstr=new String(intoutbyte);
    	return intoutstr;
    }
    
   /* public static IsJsonDTO decode(EncryDTO dto) throws Exception{
    	StringBuilder sb=new StringBuilder();
    	sb.append(dto.getKey());
    	sb.append(dto.getCipherText());
    	String outstr=sb.toString();
    	byte[]  intoutbyte =EncryptionUtils.decryptBASE64(outstr);
    	String intoutstr=new String(intoutbyte);
    	System.out.println("--d--"+intoutstr);
    	IsJsonDTO isjson=new IsJsonDTO();
    	if(isJson(intoutstr)){
    		isjson.setIsjson(true);
    		isjson.setJson(intoutstr);
    		return isjson;
    	}else{
    		isjson.setIsjson(false);
    		isjson.setJson(intoutstr);
    		return isjson;
    	}
    }*/
   
    /*
    
    public static boolean isJson(String json) throws JsonParseException {  
        if (TextUtils.isEmpty(json)) {  
            return false;  
        }  
        boolean isjson=new JsonValidator().validate(json);
        if(isjson){
        	return true;  
        }else{
        	return false;
        }
    }*/
    
    public static void main(String[] args){
    	 String inputStr = "a13234简单加密11erewre111111111111";  
         System.out.println("加密前：  " + inputStr);  
    	try {
    		EncryDTO dto=EncryptionUtils.encode(inputStr);
    		System.out.println(dto.getKey().length()+"===key======="+dto.getKey());
    		System.out.println(dto.getCipherText().length()+"===desc======"+dto.getCipherText());
    		System.out.println("解密后："+EncryptionUtils.decode(dto.getCipherText(), dto.getKey()));
    		
    		//EncryptionUtils.decode(dto);
//    		
//    		EncryDTO dto=new EncryDTO();
//    		dto.setKey("W3si");
//    		dto.setCipherText("dXVpZCI6ImNlZmFhMjBlLTQwY2YtNDVmMS1hZGYxLTZkZTA5MTdkNjkyOCIsInNlbGxlcklkIjoiMjlkMGFiYTQtOTc5NS00OWZhLTk2NzAtMjdhYzBlYmQ0YTVhIiwic2VsbGVyTmFtZSI6IjE1MjE1MTE4MTYzIiwicHJvZHVjdE5hbWUiOiLoi7nmnpznm5bojKgiLCJzYWxlUHJpY2UiOiIwLjEwIiwiYW1vdW50IjoiMSIsImJlZ2luVGltZSI6bnVsbCwib3JkZXJTdGF0dXMiOiIwMCJ9XQ==");
////    		IsJsonDTO str=EncryptionUtils.encryptMD5(inputStr.toCharArray());
//    		System.out.println("============="+EncryptionUtils.encryptMD5(inputStr.getBytes()));
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    
}
