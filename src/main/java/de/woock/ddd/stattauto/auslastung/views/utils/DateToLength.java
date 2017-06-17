package de.woock.ddd.stattauto.auslastung.views.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.time.temporal.Temporal;
import java.util.Locale;

import de.woock.ddd.stattauto.auslastung.util.Week;

public class DateToLength {
	
	public static float monat(LocalDateTime reservierung, Temporal auswahl) {
		float  mt = 0;
		String t  = "Jan";
		if        (reservierung.getYear() < ((Year)auswahl).getValue()) {
		} else if (reservierung.getYear() == ((Year)auswahl).getValue()){
			mt = reservierung.getDayOfMonth()/30.0f;
			t  = String.valueOf(reservierung.getMonth().getDisplayName(TextStyle.SHORT, Locale.GERMAN));
		} else if (reservierung.getYear() > ((Year)auswahl).getValue()){
			t  = "Dez";
		}
		return Jahr.valueOf(t).ordinal() + 1 + mt;
	}
	
	public static float wochentag(LocalDateTime reservierung, Temporal auswahl) {
		float dt = 0;
		int   t  = 0;
		if (reservierung.getYear() == ((Week)auswahl).getYear()){
			if (reservierung.getMonthValue() == ((Week)auswahl).getMonthValue()) {
				if        (reservierung.getDayOfMonth() < ((Week)auswahl).getFirstOfWeekValue()) {
					if (((Week)auswahl).getLastOfWeek() - ((Week)auswahl).getFirstOfWeekValue() < 1
						&& ((Week)auswahl).getFirstOfWeek().getMonthValue() != reservierung.getMonthValue()) {
						dt = reservierung.getHour()/24.0f;
						t  = reservierung.getDayOfWeek().getValue();
					} 
				} else if (reservierung.getMonthValue() == ((Week)auswahl).getMonthValue()) {
//					dt = reservierung.getHour()/24.0f;
//					t  = reservierung.getDayOfMonth();
				} else if (reservierung.getMonthValue() > ((Week)auswahl).getLastOfWeek()) {
					t  = 7;
				}
			}
		}
		return t + dt;
	}
	
	public static float tag(LocalDateTime reservierung, Temporal auswahl) {
		float dt = 0;
		int   t  = 0;
		if (reservierung.getYear() == ((YearMonth)auswahl).getYear()){
			if        (reservierung.getMonthValue() < ((YearMonth)auswahl).getMonthValue()) {
			} else if (reservierung.getMonthValue() == ((YearMonth)auswahl).getMonthValue()) {
				dt = reservierung.getHour()/24.0f;
				t  = reservierung.getDayOfMonth();
			} else if (reservierung.getMonthValue() > ((YearMonth)auswahl).getMonthValue()) {
				t  = 31;
			}
		}
		return t + dt;
	}
	
	public static float stunde(LocalDateTime reservierung, Temporal auswahl) {
		float dt = 0;
		int   t  = 0;
		if (reservierung.getYear() == ((LocalDate)auswahl).getYear()){
			if (reservierung.getMonthValue() == ((LocalDate)auswahl).getMonthValue()) {
				if        (reservierung.getDayOfMonth() < ((LocalDate)auswahl).getDayOfMonth()) {
				} else if (reservierung.getDayOfMonth() == ((LocalDate)auswahl).getDayOfMonth()) {
					dt = reservierung.getMinute()/60.0f;
					t  = reservierung.getHour();
				} else if (reservierung.getDayOfMonth() > ((LocalDate)auswahl).getDayOfMonth()) {
					t  = 24;
				}
			}
		}
		return t+1 + dt;
	}
}
