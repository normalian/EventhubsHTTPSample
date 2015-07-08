package com.mydomain.eventhubssample;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.time.ZonedDateTime;
import java.util.Base64;
import java.util.Date;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class SASTokenGenerator {
	public static String GetSASToken(String resourceUri, String senderKey,
			String senderKeyName, ZonedDateTime ttl) {
		try {
			String expiry = GetExpiry(ttl);
			String stringToSign = URLEncoder.encode(resourceUri, "UTF-8")
					+ "\n" + expiry;
			String signature = computeMacSha256(senderKey, stringToSign);
			String sasToken = String.format(
					"SharedAccessSignature sr=%s&sig=%s&se=%s&skn=%s",
					URLEncoder.encode(resourceUri, "UTF-8"),
					URLEncoder.encode(signature, "UTF-8"), expiry,
					senderKeyName);
			return sasToken;
		} catch (UnsupportedEncodingException | InvalidKeyException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	private static String GetExpiry(ZonedDateTime ttl) {
		return String
				.valueOf((long) (Date.from(ttl.toInstant()).getTime() / 1000));
	}

	private static String computeMacSha256(String key, final String stringToSign)
			throws InvalidKeyException {
		try {
			Mac mc = Mac.getInstance("HmacSHA256");
			Key key256 = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");
			mc.init(key256);
			byte[] utf8Bytes = stringToSign.getBytes("UTF-8");
			byte[] encripted = mc.doFinal(utf8Bytes);
			return Base64.getMimeEncoder().encodeToString(encripted);
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
