package com.mydomain.eventhubssample;

import java.time.ZoneId;
import java.time.ZonedDateTime;
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

		// 3 days after
		ZonedDateTime ttl = ZonedDateTime.now(ZoneId.of("UTC")).plusDays(3);
		EventhubsSender sender = new EventhubsSender("<servicebus name>",
				"<eventhub name>", "<servicekey>", "<policy name>", ttl);
		System.out.println(sender.send(dto));
	}
}