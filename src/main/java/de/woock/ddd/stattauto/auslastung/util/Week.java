package de.woock.ddd.stattauto.auslastung.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.temporal.IsoFields;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalUnit;

public class Week implements Temporal {
	private int       week      = 0;
	private YearMonth yearMonth = null;

	private Week(int week, YearMonth yearMonth) {
		this.week      = week;
		this.yearMonth = yearMonth;
	}
	
	public static Week of(int week, int year, int month) {
		return new Week(week, YearMonth.of(year, month));
	}
	
	public int getValue() {
		return week;
	}
	
	public int getYear() {
		return yearMonth.getYear();
	}
	
	public Month getMonth() {
		return yearMonth.getMonth();
	}
	
	public int getMonthValue() {
		return yearMonth.getMonthValue();
	}
	
	public int getFirstOfWeekValue() {
		LocalDate date = LocalDate.of(yearMonth.getYear(), Month.JANUARY, 10);
		if (week != 0) {
			LocalDate dayInWeek = date.with(IsoFields.WEEK_OF_WEEK_BASED_YEAR, week);
			LocalDate start = dayInWeek.with(DayOfWeek.MONDAY);
			return start.getDayOfMonth();
		} else {
			return 1;
		}
	}
	
	public LocalDate getFirstOfWeek() {
		LocalDate date = LocalDate.of(yearMonth.getYear(), Month.JANUARY, 10);
		LocalDate dayInWeek = date.with(IsoFields.WEEK_OF_WEEK_BASED_YEAR, week == 0 ? 1: week);
		LocalDate start = dayInWeek.with(DayOfWeek.MONDAY);
		return start;
	}
	
	public int getLastOfWeek() {
		LocalDate date = LocalDate.of(yearMonth.getYear(), Month.JANUARY, 10);
		LocalDate dayInWeek = date.with(IsoFields.WEEK_OF_WEEK_BASED_YEAR, week);
		LocalDate start = dayInWeek.with(DayOfWeek.MONDAY).plusDays(7);
		return start.getDayOfMonth(); 
	}
	
	@Override
	public boolean isSupported(TemporalField field) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public long getLong(TemporalField field) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isSupported(TemporalUnit unit) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Temporal with(TemporalField field, long newValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Temporal plus(long amountToAdd, TemporalUnit unit) {
		week++;
		return new Week(week, yearMonth);
	}

	@Override
	public long until(Temporal endExclusive, TemporalUnit unit) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public String toString() {
		return String.valueOf(week);
	}

}
