package com.mydomain.eventhubssample;

import java.util.Date;
import java.util.UUID;

import com.mydomain.eventhubssample.dto.DeviceDto;

/**
 * EventHubs HTTP 通信 Java Sample!
 *
 */
public class App {
	public static void main(String[] args) {
		DeviceDto dto = new DeviceDto();
		dto.setDeviceId(UUID.randomUUID().toString());
		dto.setTemplatture("39.8");

		Date date = new Date(115, 6, 3, 11, 0);
		EventhubsSender sender = new EventhubsSender( 
 				"<servicebus name>", 
 				"<eventhub name>", 
 				"<servicekey>", "<policy name>", 
 				date); 
		System.out.println(sender.send(dto));
	}
}
