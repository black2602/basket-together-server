package com.blackangel.baskettogether.common.domain;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.RSAPublicKeySpec;

public class RSA {
    private PrivateKey privateKey;
    private String modulus;
    private String exponent;

	public RSA(PrivateKey privateKey, String modulus, String exponent) {
		super();
		this.privateKey = privateKey;
		this.modulus = modulus;
		this.exponent = exponent;
	}
	
	public PrivateKey getPrivateKey() {
		return privateKey;
	}
	public void setPrivateKey(PrivateKey privateKey) {
		this.privateKey = privateKey;
	}
	public String getModulus() {
		return modulus;
	}
	public void setModulus(String modulus) {
		this.modulus = modulus;
	}
	public String getExponent() {
		return exponent;
	}
	public void setExponent(String exponent) {
		this.exponent = exponent;
	}
	
	public PublicKey buildPublicKey() throws Exception {
		RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(
                new BigInteger(modulus, 16), new BigInteger(exponent, 16));

        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(publicKeySpec);
	}

    
}
