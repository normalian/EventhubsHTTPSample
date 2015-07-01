package com.mydomain.eventhubssample;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mydomain.eventhubssample.dto.DeviceDto;

public class EventhubsSender {
	String resourceUri;
	String ehName;
	String senderKey;
	String senderKeyName;
	Date ttl;

	public EventhubsSender(String resourceUri, String ehName, String senderKey,
			String senderKeyName, Date ttl) {
		this.resourceUri = resourceUri;
		this.ehName = ehName;
		this.senderKey = senderKey;
		this.senderKeyName = senderKeyName;
		this.ttl = ttl;
	}

	public void send(DeviceDto dto) {
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost("https://" + this.resourceUri + "/"
				+ ehName + "/messages");
		post.setHeader("Content-type",
				"application/atom+xml;type=entry;charset=utf-8");
		try {
			ObjectMapper mapper = new ObjectMapper();

			StringEntity body = new StringEntity(mapper.writeValueAsString(dto));
			post.setEntity(body);

			post.setHeader("Authorization", SASTokenGenerator.GetSASToken(
					resourceUri, senderKey, senderKeyName, ttl));

			HttpResponse response = client.execute(post);
			System.out.println(response);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}