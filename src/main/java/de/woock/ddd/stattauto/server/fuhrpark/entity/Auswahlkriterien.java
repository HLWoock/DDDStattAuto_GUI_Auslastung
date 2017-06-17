package de.woock.ddd.stattauto.server.fuhrpark.entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Auswahlkriterien implements Serializable {

	private String stadt;
	private String kuerzel;
	private String stadtteil;
	private String standort;
	
	public Auswahlkriterien() { }

	public Auswahlkriterien(String stadt, String kuerzel, String stadtteil, String standort) {
		this.stadt     = stadt;
		this.kuerzel   = kuerzel;
		this.stadtteil = stadtteil;
		this.standort  = standort;
	}

	public String getStadt() {
		return stadt;
	}

	public void setStadt(String stadt) {
		this.stadt = stadt;
	}

	public String getKuerzel() {
		return kuerzel;
	}

	public void setKuerzel(String kuerzel) {
		this.kuerzel = kuerzel;
	}

	public String getStadtteil() {
		return stadtteil;
	}

	public void setStadtteil(String stadtteil) {
		this.stadtteil = stadtteil;
	}

	public String getStandort() {
		return standort;
	}

	public void setStandort(String standort) {
		this.standort = standort;
	}
	
	
	
}
