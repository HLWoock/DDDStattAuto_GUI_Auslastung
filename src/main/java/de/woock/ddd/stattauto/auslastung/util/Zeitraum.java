package de.woock.ddd.stattauto.auslastung.util;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@SuppressWarnings("serial")
public class Zeitraum implements Serializable {

	public LocalDateTime von;
	public LocalDateTime bis;
	
	public Zeitraum() {
	}
	
	public Zeitraum(Long von, Long bis) {
		this.von = Instant.ofEpochMilli(von).atZone(java.time.ZoneId.systemDefault()).toLocalDateTime();
		this.bis = Instant.ofEpochMilli(bis).atZone(java.time.ZoneId.systemDefault()).toLocalDateTime();
	}
	
	@Override
	public String toString() {
		return String.format("%s-%s", von.toString(), bis.toString());
	}
	
}
