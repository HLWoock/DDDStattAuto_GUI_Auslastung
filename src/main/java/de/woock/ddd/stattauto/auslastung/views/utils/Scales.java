package de.woock.ddd.stattauto.auslastung.views.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import de.woock.ddd.stattauto.auslastung.util.Week;
import de.woock.ddd.stattauto.auslastung.views.component.Chart;

public class Scales {
	
	private Map<ScaleUnit, String> series      = new HashMap<>();
	private List<Integer>          daysOfMonth = Arrays.asList(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);
	private LocalDate              localDate   = LocalDate.now();
	private Chart                  chart       = null;
	
	public Scales(Chart chart) {
		this.chart = chart;
		tag();
		wochentage();
		jahr();
		monat();
		woche();
	}

	private void woche() {
		DayOfWeek dayOfWeek = localDate.getDayOfWeek();
		LocalDate startDat  = localDate.minusDays(dayOfWeek.getValue()-1);
		
		String woche = "";
		
		for (int i = 0; i < 7; i++) {
			woche += (String.valueOf(startDat.getDayOfMonth())) + " ";
			startDat = startDat.plusDays(1);
		}
		series.put(ScaleUnit.Woche, woche);
	}
	
	private void wocheMitErstemTagDerWoche(LocalDate date) {
		String woche = "";
		
		for (int i = 0; i < 7; i++) {
			woche += date.getDayOfMonth() + " ";
			date = date.plusDays(1);
		}
		series.put(ScaleUnit.Woche, woche);
	}

	private void monat() {
		int monthValue = localDate.getMonthValue();
		Integer integer = daysOfMonth.get(monthValue-1);
		String string = IntStream.rangeClosed(1, integer)
		                         .boxed()
                                 .map(String::valueOf)
                                 .collect(Collectors.joining(" ", "", ""));
		series.put(ScaleUnit.Monat, string);
	}

	private void jahr()       {series.put(ScaleUnit.Jahr, "Jan Feb Mrz Apr Mai Jun Jul Aug Spt Okt Nov Dez"); }
	private void wochentage() {series.put(ScaleUnit.Wochentage, "Mo Di Mi Do Fr Sa So"); }
	private void tag()        {series.put(ScaleUnit.Tag, "0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24");	}
	
	public String getTimeStream(ScaleUnit scale) {
		if (scale.equals(ScaleUnit.Woche)) {
			Week woche = chart.getKalenderwoche();
			localDate = LocalDate.of(woche.getYear(), woche.getMonthValue(), woche.getFirstOfWeekValue());
			wocheMitErstemTagDerWoche(woche.getFirstOfWeek());
		}
		return series.get(scale);
	}
}
