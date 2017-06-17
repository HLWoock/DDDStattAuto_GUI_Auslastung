package de.woock.ddd.stattauto.auslastung.views.component;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Year;
import java.time.YearMonth;
import java.time.ZoneOffset;
import java.time.format.TextStyle;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import de.woock.ddd.stattauto.auslastung.util.Week;
import de.woock.ddd.stattauto.auslastung.util.Zeitraum;
import de.woock.ddd.stattauto.auslastung.views.utils.DateToLength;
import de.woock.ddd.stattauto.auslastung.views.utils.ScaleUnit;
import de.woock.ddd.stattauto.auslastung.views.utils.Scales;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class Chart extends Canvas {
	
	private SelectionPanel            selectionPanel;
	private Scales                    scales = new Scales(this);
	private Map<ScaleUnit, Temporal>  auswahlMap;
	
	private final int yAbstand     = 40;
	private final int xAnfang      = 200;
	private final int xEnde        = 1400;
	
	private ScaleUnit               scaleUnit = ScaleUnit.Tag;
	private Map<ScaleUnit, Integer> metric = new HashMap<>();
	private List<List<Zeitraum>>    drawnBelegungen;

	public Chart() {
		super(1480, 320);
		metric.put(ScaleUnit.Tag  ,  50); 
		metric.put(ScaleUnit.Woche, 198);
		metric.put(ScaleUnit.Monat,  40);
		metric.put(ScaleUnit.Jahr , 108);
		drawEmptyTimeTable();
		drawFahrzeug();
	}

	public void setScalingType(ScaleUnit scaleUnit) {
		this.scaleUnit = scaleUnit;
		drawScale();
		redrawBelegungen();
	}

	public void setSelectionPanel(SelectionPanel selectionPanel) {
		this.selectionPanel = selectionPanel;
	}
	
	public void drawChartContent() {
		drawScale();
		loadBelegungenFromServer();
	}

	public void setDate(Map<ScaleUnit, Temporal> date) {
		this.auswahlMap = date;
	}

	public void update() {
		auswahlMap = selectionPanel.getAuswahlMap();
		drawScale();
		loadBelegungenFromServer();
	}

	public void drawBelegungen(List<List<Zeitraum>> belegungen) {
		if (belegungen != null) {
			auswahlMap = selectionPanel.getAuswahlMap();
			drawnBelegungen = belegungen;
			
			int fahrzeugNr = 0;
			for (List<Zeitraum> einzelbeleglngen : belegungen) {
				fahrzeugNr++;
				clearField(fahrzeugNr);
				for (Zeitraum zeitraum : einzelbeleglngen) {
					if (scaleUnit != null) {
						switch (scaleUnit) {
						case Tag: 
							drawBelegung(fahrzeugNr, DateToLength.stunde(zeitraum.von, auswahlMap.get(scaleUnit)),
									                 DateToLength.stunde(zeitraum.bis, auswahlMap.get(scaleUnit)));
							break;
						case Woche:
							drawBelegung(fahrzeugNr, DateToLength.wochentag(zeitraum.von, auswahlMap.get(scaleUnit)), 
									                 DateToLength.wochentag(zeitraum.bis, auswahlMap.get(scaleUnit)));
							break;
						case Monat:
							drawBelegung(fahrzeugNr, DateToLength.tag(zeitraum.von, auswahlMap.get(scaleUnit)), 
									                 DateToLength.tag(zeitraum.bis, auswahlMap.get(scaleUnit)));
							break;
						case Jahr:
							drawBelegung(fahrzeugNr, DateToLength.monat(zeitraum.von, auswahlMap.get(scaleUnit)),
									                 DateToLength.monat(zeitraum.bis, auswahlMap.get(scaleUnit)));
						default:
							break;
						}
					}
				}
			}
		}
	}

	private void redrawBelegungen() {
		drawBelegungen(drawnBelegungen);
	}

	private void drawBelegung(int i, float von, float bis) {
		GraphicsContext gc = getGraphicsContext2D();
		setStyleBelegung();
	    if (scaleUnit != null) {
	    	int xDist = metric.get(scaleUnit);
	    	gc.fillRect(200 + (von-1) * xDist, 129 + (i-1) * 40, (bis-von) * xDist, 10);
	    } 
	}

	private void loadBelegungenFromServer() {
		List<Zeitraum> erstesAuto = new ArrayList<>();
		erstesAuto.add(new Zeitraum(LocalDateTime.of(2017, Month.JUNE,  1,  8, 30).toEpochSecond(ZoneOffset.UTC), 
                                    LocalDateTime.of(2017, Month.JUNE,  3, 19, 45).toEpochSecond(ZoneOffset.UTC)));
		erstesAuto.add(new Zeitraum(LocalDateTime.of(2017, Month.JUNE,  5,  8, 30).toEpochSecond(ZoneOffset.UTC), 
                                    LocalDateTime.of(2017, Month.JUNE,  7, 19, 45).toEpochSecond(ZoneOffset.UTC)));
		erstesAuto.add(new Zeitraum(LocalDateTime.of(2017, Month.JUNE, 12,  8, 30).toEpochSecond(ZoneOffset.UTC), 
                                    LocalDateTime.of(2017, Month.JUNE, 13, 19, 45).toEpochSecond(ZoneOffset.UTC)));
		
		List<Zeitraum> zweitesAuto = new ArrayList<>();
		zweitesAuto.add(new Zeitraum(LocalDateTime.of(2017, Month.JUNE,  7,  8, 30).toEpochSecond(ZoneOffset.UTC), 
                                     LocalDateTime.of(2017, Month.JUNE,  8, 19, 45).toEpochSecond(ZoneOffset.UTC)));
		zweitesAuto.add(new Zeitraum(LocalDateTime.of(2017, Month.JUNE,  9,  8, 30).toEpochSecond(ZoneOffset.UTC), 
                                     LocalDateTime.of(2017, Month.JUNE, 10, 19, 45).toEpochSecond(ZoneOffset.UTC)));
		zweitesAuto.add(new Zeitraum(LocalDateTime.of(2017, Month.JUNE, 15,  8, 30).toEpochSecond(ZoneOffset.UTC), 
                                     LocalDateTime.of(2017, Month.JUNE, 17, 19, 45).toEpochSecond(ZoneOffset.UTC)));
		
		List<Zeitraum> drittesAuto = new ArrayList<>();
		drittesAuto.add(new Zeitraum(LocalDateTime.of(2017, Month.JUNE,  5,  7, 00).toEpochSecond(ZoneOffset.UTC), 
                                     LocalDateTime.of(2017, Month.JUNE, 10, 19, 45).toEpochSecond(ZoneOffset.UTC)));
		drittesAuto.add(new Zeitraum(LocalDateTime.of(2017, Month.JUNE, 15,  8, 30).toEpochSecond(ZoneOffset.UTC), 
                                     LocalDateTime.of(2017, Month.JUNE, 20, 19, 45).toEpochSecond(ZoneOffset.UTC)));
		
		List<Zeitraum> viertesAuto = new ArrayList<>();
		viertesAuto.add(new Zeitraum(LocalDateTime.of(2017, Month.JUNE,  1,  8, 30).toEpochSecond(ZoneOffset.UTC), 
                                     LocalDateTime.of(2017, Month.JUNE,  2, 19, 45).toEpochSecond(ZoneOffset.UTC)));
		viertesAuto.add(new Zeitraum(LocalDateTime.of(2017, Month.JUNE,  3,  8, 30).toEpochSecond(ZoneOffset.UTC), 
                                     LocalDateTime.of(2017, Month.JUNE,  4, 19, 45).toEpochSecond(ZoneOffset.UTC)));
		viertesAuto.add(new Zeitraum(LocalDateTime.of(2017, Month.JUNE,  5,  8, 30).toEpochSecond(ZoneOffset.UTC), 
                                     LocalDateTime.of(2017, Month.JUNE,  6, 19, 45).toEpochSecond(ZoneOffset.UTC)));
		viertesAuto.add(new Zeitraum(LocalDateTime.of(2017, Month.JUNE,  9,  8, 30).toEpochSecond(ZoneOffset.UTC), 
                                     LocalDateTime.of(2017, Month.JUNE, 11, 19, 45).toEpochSecond(ZoneOffset.UTC)));
		viertesAuto.add(new Zeitraum(LocalDateTime.of(2017, Month.JUNE, 12,  8, 30).toEpochSecond(ZoneOffset.UTC), 
		                             LocalDateTime.of(2017, Month.JUNE, 17, 19, 45).toEpochSecond(ZoneOffset.UTC)));
		viertesAuto.add(new Zeitraum(LocalDateTime.of(2017, Month.JUNE, 22,  8, 30).toEpochSecond(ZoneOffset.UTC), 
                                     LocalDateTime.of(2017, Month.JUNE, 22,  9, 45).toEpochSecond(ZoneOffset.UTC)));
		viertesAuto.add(new Zeitraum(LocalDateTime.of(2017, Month.JUNE, 22, 14, 30).toEpochSecond(ZoneOffset.UTC), 
                                     LocalDateTime.of(2017, Month.JUNE, 22, 15, 30).toEpochSecond(ZoneOffset.UTC)));

		
		List<List<Zeitraum>> alleBelegungen = new ArrayList<>();
		alleBelegungen.add(erstesAuto);
		alleBelegungen.add(zweitesAuto);
		alleBelegungen.add(drittesAuto);
		alleBelegungen.add(viertesAuto);
		drawBelegungen(alleBelegungen);
	}

	private void setStyleBelegung() {
		GraphicsContext gc = getGraphicsContext2D();
		gc.setFill(Color.RED);
	    gc.setStroke(Color.BLUE);
	    gc.setLineWidth(5);
	}

	private void drawFahrzeug() {
		GraphicsContext gc = getGraphicsContext2D();
		for (int i = 1; i <= 4; i++) {
			Image image = new Image(new File(String.format("src/main/java/de/woock/ddd/stattauto/auslastung/image/Car%s.png", i)).toURI().toString(), 350/4, 150/4, false, false);
		    setFontKennzeichen();
			gc.fillText("HH LB 1378", 190, 100 + i  * 40);
			gc.drawImage(image, 30, 105 + (i - 1) * 40);
		}
	}

	private void drawScale() {
		clearScale();
		
		drawSelectionAsText();
		drawDateScale(scales.getTimeStream(scaleUnit));
		drawWeekDayScale(); 
	}

	private void drawSelectionAsText() {
		GraphicsContext gc = getGraphicsContext2D();
		auswahlMap = selectionPanel.getAuswahlMap();
		if (scaleUnit != null) {
			clearText();
			setFontMonat();
			switch (scaleUnit) {
			case Jahr:
				int year = ((Year)auswahlMap.get(ScaleUnit.Jahr)).getValue();
				gc.fillText(String.format("%d", year), 200, 50);
				break;
			case Monat:
				year  = ((YearMonth)auswahlMap.get(ScaleUnit.Monat)).getYear();
				String month = ((YearMonth)auswahlMap.get(ScaleUnit.Monat)).getMonth().getDisplayName(TextStyle.SHORT, Locale.GERMAN);
				gc.fillText(String.format("%s %d", month, year), 200, 50);
				break;
			case Woche:
				gc.fillText("KW: " + String.valueOf(((Week)auswahlMap.get(ScaleUnit.Woche)).getValue()), 200, 50);
				break;
			case Tag:
				year  = ((LocalDate)auswahlMap.get(ScaleUnit.Tag)).getYear();
				month = ((LocalDate)auswahlMap.get(ScaleUnit.Tag)).getMonth().getDisplayName(TextStyle.SHORT, Locale.GERMAN);
				int    day   = ((LocalDate)auswahlMap.get(ScaleUnit.Tag)).getDayOfMonth();
				gc.fillText(String.format("%d.%s %d", day, month, year), 200, 50);
				break;
			default:
				break;
			}
		}
	}

	private void drawDateScale(String dates) {
		GraphicsContext gc = getGraphicsContext2D();
		int c = 1;
		for (String d: dates.split(" ")){
			String date = String.valueOf(d);
		    setFontWochentage();
		    if (scaleUnit != null) {
		    	gc.fillText(date, 200 + (c++-1) * metric.get(scaleUnit), 100);
		    }
		}
	}

	private void drawWeekDayScale() {
		GraphicsContext gc = getGraphicsContext2D();
		if (scaleUnit != null && (scaleUnit.equals("Woche") || scaleUnit.equals(ScaleUnit.Monat))){
		    setFontWochentage();
			List<String> wochentage = Arrays.asList("Mo", "Di", "Mi", "Do", "Fr", "Sa", "So");
			for (int i = 1; i <= 31; i++){
				if (scaleUnit != null){
					gc.fillText(wochentage.get((i-1) % 7), 200 + (i-1) * metric.get(scaleUnit), 85);
				}
			}
		}
	}

	private void drawEmptyTimeTable() {
		GraphicsContext gc = getGraphicsContext2D();
		Arrays.asList(1, 2, 3, 4).stream().forEach(l -> {
			gc.moveTo(xAnfang, 100 + yAbstand * l);
			gc.lineTo(xEnde, 100 + yAbstand * l);
		});
	
		gc.setLineWidth(1.0);
		gc.stroke();
	}

	private void setFontKennzeichen() {
		GraphicsContext gc = getGraphicsContext2D();
		gc.setFont(Font.font("Calibri", 14));
		gc.setFill(Color.BLACK);
		gc.setTextAlign(TextAlignment.RIGHT);
	}

		private void setFontMonat() {
			GraphicsContext gc = getGraphicsContext2D();
			gc.setFont(Font.font("Calibri", 20));
			gc.setFill(Color.BLACK);
			gc.setTextAlign(TextAlignment.CENTER);
		}

		private void setFontWochentage() {
			GraphicsContext gc = getGraphicsContext2D();
			gc.setFont(Font.font("Calibri", 16));
			gc.setFill(Color.BLACK);
			gc.setTextAlign(TextAlignment.CENTER);
		}

		private void clearScale() {
			GraphicsContext gc = getGraphicsContext2D();
			gc.setFill(Color.WHITE);
		    gc.setStroke(Color.WHITE);
		    gc.setLineWidth(5);
		    gc.strokeLine(40, 10, 10, 40);
		    gc.fillRect(180, 35, 1300, 80);	
		}

		private void clearText() {
			GraphicsContext gc = getGraphicsContext2D();
			gc.setFill(Color.WHITE);
		    gc.setStroke(Color.WHITE);
		    gc.setLineWidth(5);
		    gc.strokeLine(40, 10, 10, 40);
		    gc.fillRect(100, 10, 500, 40);	
		}
		private void clearField(int i) {
			GraphicsContext gc = getGraphicsContext2D();
			gc.setFill(Color.WHITE);
		    gc.setStroke(Color.WHITE);
		    gc.setLineWidth(5);
		    gc.strokeLine(40, 10, 10, 40);
		    gc.fillRect(xAnfang, 34 + yAbstand+i*40, 1300, 25);
		}

		public Week getKalenderwoche() {
			return (Week) selectionPanel.getAuswahlMap().get(ScaleUnit.Woche);
		}
}
