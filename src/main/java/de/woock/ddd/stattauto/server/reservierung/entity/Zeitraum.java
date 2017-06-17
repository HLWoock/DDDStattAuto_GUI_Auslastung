package de.woock.ddd.stattauto.server.reservierung.entity;

public class Zeitraum {

	private Long startZeit;
	private Long endZeit;
	private float tage;
	private float wochen;
	private int stunden;
	
	public Long getStartZeit() {
		return startZeit;
	}
	public void setStartZeit(Long startZeit) {
		this.startZeit = startZeit;
	}
	public Long getEndZeit() {
		return endZeit;
	}
	public void setEndZeit(Long endZeit) {
		this.endZeit = endZeit;
	}
	public float getTage() {
		return tage;
	}
	public void setTage(float tage) {
		this.tage = tage;
	}
	public float getWochen() {
		return wochen;
	}
	public void setWochen(float wochen) {
		this.wochen = wochen;
	}
	public int getStunden() {
		return stunden;
	}
	public void setStunden(int stunden) {
		this.stunden = stunden;
	}
	
	
}
