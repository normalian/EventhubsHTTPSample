package com.mydomain.eventhubssample;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

public class SASTokenGenerator {
	public static String GetSASToken(String resourceUri, String senderKey,
			String senderKeyName, Date ttl) {
		String expiry = GetExpiry(ttl);
		String stringToSign = URLEncoder.encode(resourceUri) + "\n" + expiry;
		String signature = "";
		try {
			signature = computeMacSha256(senderKey, stringToSign);
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		}
		String sasToken = String.format(
				"SharedAccessSignature sr=%s&sig=%s&se=%s&skn=%s",
				URLEncoder.encode(resourceUri), URLEncoder.encode(signature),
				expiry, senderKeyName);
		return sasToken;
	}

	private static String GetExpiry(Date ttl) {
		long offset = Calendar.getInstance().get(Calendar.ZONE_OFFSET)
				+ Calendar.getInstance().get(Calendar.DST_OFFSET);
		return new Long((long) ((ttl.getTime() + offset) / 1000)).toString();
	}

	private static String computeMacSha256(String key, final String stringToSign)
			throws InvalidKeyException {
		Mac mc = null;
		try {
			mc = Mac.getInstance("HmacSHA256");
			Key key256 = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");
			mc.init(key256);
		} catch (NoSuchAlgorithmException e1) {
			throw new IllegalArgumentException();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		byte[] utf8Bytes = null;
		try {
			utf8Bytes = stringToSign.getBytes("UTF-8");
		} catch (final UnsupportedEncodingException e) {
			throw new IllegalArgumentException(e);
		}

		return DatatypeConverter.printBase64Binary(mc.doFinal(utf8Bytes));
	}
}
