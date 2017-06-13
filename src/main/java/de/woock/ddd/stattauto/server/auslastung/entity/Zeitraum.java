package de.woock.ddd.stattauto.server.auslastung.entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Zeitraum implements Serializable {

	public long von;
	public long bis;
	
	public Zeitraum() {
	}
	
	public Zeitraum(Long von, Long bis) {
		this.von = von;
		this.bis = bis;
	}
	
	public long getVon() {return von;}

	public void setVon(long von) {this.von = von;}
	
	public long getBis() {return bis;}

	public void setBis(long bis) {this.bis = bis;}
	
	@Override
	public String toString() {
		return String.format("%d-%d", von ,bis);
	}
	
}
