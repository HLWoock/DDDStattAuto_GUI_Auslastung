package de.woock.ddd.stattauto.auslastung.views.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Scale {
	
	private Map<String, String> series = new HashMap<>();
	private List<Integer> daysOfMonth = Arrays.asList(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);
	private LocalDate localDate = LocalDate.now();
	
	public Scale() {
		tag();
		wochentage();
		jahr();
		monat();
		woche();
	}

	private void woche() {
		DayOfWeek dayOfWeek = localDate.getDayOfWeek().minus(1);
		LocalDate startDat = localDate.minusDays(dayOfWeek.getValue());
		
		String woche = "";
		
		for (int i = 0; i < 7; i++) {
			woche += (String.valueOf(startDat.getDayOfMonth())) + " ";
			startDat = startDat.plusDays(1);
		}
		series.put("Woche", woche);
	}



	private void monat() {
		int monthValue = localDate.getMonthValue();
		Integer integer = daysOfMonth.get(monthValue-1);
		String string = IntStream.rangeClosed(1, integer)
		                         .boxed()
                                 .map(String::valueOf)
                                 .collect(Collectors.joining(" ", "", ""));
		series.put("Monat", string);
	}



	private void jahr() {
		series.put("Jahr", "Jan Feb Mrz Apr Mai Jun Jul Aug Spt Okt Nov Dez");
	}



	private void wochentage() {
		series.put("Wochentage", "Mo Di Mi Do Fr Sa So");
	}



	private void tag() {
		series.put("Tag", "0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24");
	}
	


	public String getTimeStream(Number val) {
		String scale = convert(val.doubleValue());
		return series.get(scale);
	}
	
	public String convert(Double n) {
		if (n < 0.5)
			return "Tag";
		if (n < 1.5)
			return "Woche";
		if (n < 2.5)
			return "Monat";

		return "Jahr";
	}

	public String getScalingType(Number new_val) {
		return convert(new_val.doubleValue());
	}

}
