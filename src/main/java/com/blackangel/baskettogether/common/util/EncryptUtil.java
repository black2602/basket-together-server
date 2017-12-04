package com.blackangel.baskettogether.common.util;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.apache.commons.codec.binary.Base64;

import com.blackangel.baskettogether.common.domain.RSA;

public class EncryptUtil {
	public static String pbkdf2(String password, String salt, int iterations) {
		try {
			PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), iterations, 20*8);
			SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			byte[] hash = skf.generateSecret(spec).getEncoded();

			return org.apache.commons.codec.binary.Base64.encodeBase64String(hash);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("error on pbkdf2", e);
		} catch (InvalidKeySpecException e) {
			throw new RuntimeException("error on pbkdf2", e);
		}
	}

	public static String newRandomKey() {
		final Random r = new SecureRandom();
		byte[] salt = new byte[32];
		r.nextBytes(salt);

		return org.apache.commons.codec.binary.Base64.encodeBase64String(salt);
	}

	public static PublicKey makeRSAPublicKey(String modulus, String exponent) throws Exception {
		RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(
				new BigInteger(modulus, 16), new BigInteger(exponent, 16));

		KeyFactory kf = KeyFactory.getInstance("RSA");
		return kf.generatePublic(publicKeySpec);
	}

	public static PrivateKey makeRSAPrivateKey(String modulus, String exponent) throws Exception {
		RSAPrivateKeySpec privateKeySpec = new RSAPrivateKeySpec(
				new BigInteger(modulus, 16), new BigInteger(exponent, 16));

		KeyFactory kf = KeyFactory.getInstance("RSA");
		return kf.generatePrivate(privateKeySpec);
	}

	public static RSA createRSA() throws Exception {
		KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
		generator.initialize(1024);
		KeyPair keyPair = generator.genKeyPair();
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PublicKey publicKey = keyPair.getPublic();
		PrivateKey privateKey = keyPair.getPrivate();

		RSAPublicKeySpec publicSpec = (RSAPublicKeySpec) keyFactory.getKeySpec(publicKey, RSAPublicKeySpec.class);
		RSAPrivateKeySpec privateSpec = (RSAPrivateKeySpec) keyFactory.getKeySpec(privateKey, RSAPrivateKeySpec.class);
		String publicKeyModulus = publicSpec.getModulus().toString(16);
		String publicKeyExponent = publicSpec.getPublicExponent().toString(16);
		
		return new RSA(privateKey, publicKeyModulus, publicKeyExponent);
	}

	/** 개인키를 이용한 RSA 복호화
	 *  @param privateKey session에 저장된 PrivateKey
	 *  @param encryptedText 암호화된 문자열
	 */
	public static String decryptRSA(PrivateKey privateKey, String encryptedText) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		byte[] decryptedBytes = cipher.doFinal(Base64.decodeBase64(encryptedText));
		
		return new String(decryptedBytes, "UTF-8");
	}

	/**
	 * 공개키를 이용한 RSA 암호화 
	 * @param publicKey
	 * @param plainText
	 */
	public static String encryptRSA(PublicKey publicKey, String plainText) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		byte[] encryptedBytes = cipher.doFinal(plainText.getBytes("UTF-8"));

		return Base64.encodeBase64String(encryptedBytes);
	}

	// 16진수 문자열을 byte 배열로 변환
	private static byte[] hexToByteArray(String hex) {
		if (hex == null || hex.length() % 2 != 0) {
			return new byte[] {};
		}

		byte[] bytes = new byte[hex.length() / 2];
		for (int i = 0; i < hex.length(); i += 2) {
			byte value = (byte) Integer.parseInt(hex.substring(i, i + 2), 16);
			bytes[(int) Math.floor(i / 2)] = value;
		}
		return bytes;
	}


}
