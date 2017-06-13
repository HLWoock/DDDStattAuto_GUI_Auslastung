package de.woock.ddd.stattauto.auslastung.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import de.woock.ddd.stattauto.server.auslastung.entity.Zeitraum;


@Service
public class AuslastungsService {
	
	@Autowired DiscoveryClient dc;
	
	RestTemplate restTemplate = new RestTemplate();

	public Zeitraum holeZeitraum() {
		return restTemplate.getForObject(String.format("%s/Auslastung/", getUri()), Zeitraum.class);
	}
	
	private String getUri() {
		return String.format("%s", dc.getInstances("AUSLASTUNG-SERVICE").get(0).getUri());
	}

}
