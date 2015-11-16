package com.zongyou.library.util.base64;

import java.io.UnsupportedEncodingException;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.zongyou.library.util.MD5Utils;

import android.text.TextUtils;

public class AESUtils {

	private static final int iv_size = 16;
	private int iv_index = 0;
	private Cipher cipher;
	private IvParameterSpec ivspec;
	private SecretKeySpec keyspec;
	private String iv = "";
	private String keyDeal = "";
	private String token = "";
	private String key = "";

	public AESUtils(String token, String key, int userId) {

		this.token = token;
		this.key = key;

		try {
			cipher = Cipher.getInstance("AES/CBC/NoPadding");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			iv_index = Integer.valueOf((String) Integer.valueOf(key, 24)
					.toString().subSequence(0, 1));
		} catch (Exception e) {
			iv_index = 0;
		}

		try {
			keyDeal = createKey(token, userId);
		} catch (UnsupportedEncodingException e) {
			keyDeal = "";
		}

		iv = createIv(keyDeal);
	}

	public String encrypt(String data) {
		if (TextUtils.isEmpty(token) || TextUtils.isEmpty(key)
				|| TextUtils.isEmpty(data)) {
			return data;
		}
		String vale = DecryptData(data, keyDeal, iv);
		return vale;
	}

	/**
	 * 加密
	 * 
	 * @param data
	 * @param keyDeal
	 * @param iv
	 * @return
	 */
	private String DecryptData(String data, String keyDeal, String iv) {

		ivspec = new IvParameterSpec(iv.getBytes());
		keyspec = new SecretKeySpec(keyDeal.getBytes(), "AES");

		byte[] encrypted = null;
		String vale = "";
		try {
			cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
			encrypted = cipher.doFinal(padString(data).getBytes());
			vale = EncryptionUtils.encryptBASE64(encrypted);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return vale;
	}

	/**
	 * 获得加密的key
	 * 
	 * @throws UnsupportedEncodingException
	 */
	private String createKey(String token, int userId)
			throws UnsupportedEncodingException {
		String key = "";
		if (!TextUtils.isEmpty(token)) {
			key = token + userId;
			key = MD5Utils.md5(key);
		}

		return key;
	}

	/**
	 * 获得VI
	 */
	private String createIv(String keyDeal) {
		String vi = "";

		if (!TextUtils.isEmpty(keyDeal)) {
			vi = MD5Utils.md5(keyDeal.substring(iv_index).toString()
					.subSequence(0, iv_size).toString()
					+ keyDeal);
			vi = vi.substring(iv_index).toString().substring(0, iv_size);
		}
		return vi;
	}

	/**
	 * 偏转
	 * 
	 * @param source
	 * @return
	 */
	private static String padString(String source) {
		int size = iv_size;
		int s = source.getBytes().length;
		int x = s % size;
		int padLength = size - x;
		char b = (char) padLength;
		StringBuilder str = new StringBuilder(source);

		for (int i = 0; i < padLength; i++) {
			str.append(b);
		}

		return str.toString();
	}

	/**
	 * 
	 * 解密
	 */

	public String decrypt(String data) {

		if (TextUtils.isEmpty(token) || TextUtils.isEmpty(key)
				|| TextUtils.isEmpty(data)) {
			return data;
		}

		ivspec = new IvParameterSpec(iv.getBytes());
		keyspec = new SecretKeySpec(keyDeal.getBytes(), "AES");

		String str = "";
		try {
			byte[] bt = decrypt(EncryptionUtils.decryptBASE64(data));
			str = new String(bt, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return AntipadString(str);
	}

	private byte[] decrypt(byte[] str) {

		byte[] encrypted = null;
		try {
			cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);
			encrypted = cipher.doFinal(str);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("解密失败");
		}

		return encrypted;
	}

	/**
	 * 逆偏转
	 * 
	 * @param source
	 * @return
	 */
	private String AntipadString(String source) {
		String str = source;
		if (!TextUtils.isEmpty(source)) {
			char s = source.substring(source.length() - 1, source.length())
					.toString().charAt(0);
			int i = Integer.valueOf(s);
			str = source.substring(0, source.length() - i);
		}
		return str;
	}

}
