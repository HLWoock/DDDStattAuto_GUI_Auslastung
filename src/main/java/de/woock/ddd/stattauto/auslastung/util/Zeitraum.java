package de.woock.ddd.stattauto.auslastung.util;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;

@SuppressWarnings("serial")
public class Zeitraum implements Serializable {

	public LocalDateTime von;
	public LocalDateTime bis;
	
	public Zeitraum() {
	}
	
	public Zeitraum(Long von, Long bis) {
		this.von = LocalDateTime.ofInstant(Instant.ofEpochSecond(von), TimeZone.getDefault().toZoneId());;
		this.bis = LocalDateTime.ofInstant(Instant.ofEpochSecond(bis), TimeZone.getDefault().toZoneId());;
	}
	
	@Override
	public String toString() {
		return String.format("%s-%s", von.toString(), bis.toString());
	}
	
}
