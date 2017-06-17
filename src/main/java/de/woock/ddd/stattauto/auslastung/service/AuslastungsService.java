package de.woock.ddd.stattauto.auslastung.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import de.woock.ddd.stattauto.server.fuhrpark.entity.Auswahlkriterien;
import de.woock.ddd.stattauto.server.reservierung.entity.Zeitraum;


@Service
public class AuslastungsService {
	
	@Autowired DiscoveryClient dc;
	
	RestTemplate restTemplate = new RestTemplate();

	@SuppressWarnings("unchecked")
	public List<String> holeAlleStationsKuerzel() {
		return restTemplate.getForObject(String.format("%s/Stationen/kuerzel", getFuhrparkUri()), List.class);
	}
	
	public Auswahlkriterien holeStationAuswahlkriterienFuerKuerzel(String kuerzel) {
		return restTemplate.getForObject(String.format("%s/Stationen/auswahlkriterien/%s", getFuhrparkUri(), kuerzel), Auswahlkriterien.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<Integer> fahrzeugIdsFuerStationsKuerzel(String kuerzel) {
		return restTemplate.getForObject(String.format("%s/Stationen/station/kuerzel/%s/fahrzeugIds", getFuhrparkUri(), kuerzel), List.class);
	}

	public List<Zeitraum> reservierungenFuerId(Integer id) {
		return restTemplate.exchange(String.format("%s/Reservierungen/mietobjekt/%s/zeitraum", getReservierungUri(), id), HttpMethod.GET, HttpEntity.EMPTY, new ParameterizedTypeReference<List<Zeitraum>>() {})
                           .getBody();
	}

	@SuppressWarnings("unused")
	private String getAuslastungsUri() {
		return String.format("%s", dc.getInstances("AUSLASTUNG-SERVICE").get(0).getUri());
	}

	private String getFuhrparkUri() {
		return String.format("%s", dc.getInstances("FUHRPARK-SERVICE").get(0).getUri());
	}

	private String getReservierungUri() {
		return String.format("%s", dc.getInstances("RESERVIERUNG-SERVICE").get(0).getUri());
	}
}
