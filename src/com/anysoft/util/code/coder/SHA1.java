package com.anysoft.util.code.coder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;

import com.anysoft.util.KeyGen;
import com.anysoft.util.code.Coder;


/**
 * SHA1 加密
 * 
 * @author duanyy
 *
 * @since 1.0.13
 */
public class SHA1 implements Coder {
	public String getAlgorithm() {
		return "sha-1";
	}
	@Override
	public String encode(String data, String key) {
		try {
			MessageDigest m = MessageDigest.getInstance(getAlgorithm());
			String content = data + key;
			m.update(content.getBytes());
			byte result[] = m.digest();
			return new String(Base64.encodeBase64(result));
		} catch (NoSuchAlgorithmException e) {
			return data;
		}
	}

	@Override
	public String decode(String data, String key) {
		return data;
	}

	@Override
	public String createKey() {
		return KeyGen.getKey(8);
	}

}