package de.woock.ddd.stattauto.auslastung.views.component;

import java.io.File;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneOffset;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import de.woock.ddd.stattauto.auslastung.util.Zeitraum;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class Chart extends Canvas {
	
	
	private final int yAbstand     = 40;
	private final int xAnfang      = 200;
	private final int xEnde        = 1400;
	
	private String scalingType;
	private Map<String, Integer> metric = new HashMap<>();
	private List<List<Zeitraum>> drawnBelegungen;
	private String               drawnScale;

	public Chart() {
		super(1480, 320);
		drawTimeTable();
		drawFahrzeug();
		drawBelegung();
		metric.put("Tag"  ,  50); 
		metric.put("Woche", 198);
		metric.put("Monat",  40);
		metric.put("Jahr" , 108);
	}
	
	public void setScale(String scale) {
		drawnScale = scale;
		drawDates(scale);
		drawBelegung(drawnBelegungen);
	}

	public void setScalingType(String scalingType) {
		this.scalingType = scalingType;
		drawBelegung(drawnBelegungen);
	}

	public void drawBelegung(List<List<Zeitraum>> belegungen) {
		drawnBelegungen = belegungen;
		List<Zeitraum> einzelbelegungen = belegungen.get(0);
		Zeitraum zeitraum = einzelbelegungen.get(0);
		if (scalingType != null && scalingType.equals("Tag")) {
			float hvon = zeitraum.von.getMinute()/60.0f;
			if (zeitraum.bis.getDayOfYear() - zeitraum.von.getDayOfYear() > 1) {
				drawBelegung(1, zeitraum.von.getHour() + hvon, 24);
			} else {
				float hbis = zeitraum.bis.getMinute()/60.0f;
				drawBelegung(1, zeitraum.von.getHour() + hvon, zeitraum.bis.getHour() + hbis);
			}
		} else if (scalingType != null && scalingType.equals("Woche")) {
			float dvon = zeitraum.von.getHour()/24.0f;
			String von = String.valueOf(zeitraum.von.getDayOfMonth());
			String bis = String.valueOf(zeitraum.bis.getDayOfMonth());
			if (zeitraum.bis.getDayOfYear() - zeitraum.von.getDayOfYear() > 7) {
				drawBelegung(1, Arrays.asList(drawnScale.split(" ")).indexOf(von) + dvon, 7);
			} else {
				float dbis = zeitraum.bis.getHour()/24.0f;
				drawBelegung(1, Arrays.asList(drawnScale.split(" ")).indexOf(von) + dvon, Arrays.asList(drawnScale.split(" ")).indexOf(bis) + dbis);
			}
		} else if (scalingType != null && scalingType.equals("Monat")) {
			float dvon = zeitraum.von.getHour()/24.0f;
			float dbis = zeitraum.bis.getHour()/24.0f;
			String von = String.valueOf(zeitraum.von.getDayOfMonth());
			String bis = String.valueOf(zeitraum.bis.getDayOfMonth());
			drawBelegung(1, Arrays.asList(drawnScale.split(" ")).indexOf(von) + dvon, Arrays.asList(drawnScale.split(" ")).indexOf(bis) + dbis);
		} else if (scalingType != null && scalingType.equals("Jahr")) {
			float mvon = zeitraum.von.getDayOfMonth()/30.0f;
			float mbis = zeitraum.bis.getDayOfMonth()/30.0f;
			String von = String.valueOf(zeitraum.von.getMonth().getDisplayName(TextStyle.SHORT, Locale.GERMAN));
			String bis = String.valueOf(zeitraum.bis.getMonth().getDisplayName(TextStyle.SHORT, Locale.GERMAN));
			drawBelegung(1, Arrays.asList(drawnScale.split(" ")).indexOf(von) + mvon, Arrays.asList(drawnScale.split(" ")).indexOf(bis) + mbis);
		}
	}

	private void drawBelegung() {
		List<Zeitraum> einzelBelegungen = new ArrayList<>();
		einzelBelegungen.add(new Zeitraum(LocalDateTime.of(2017, Month.JUNE, 1,  8, 30).toEpochSecond(ZoneOffset.UTC), 
				                          LocalDateTime.of(2017, Month.JUNE, 3, 19, 45).toEpochSecond(ZoneOffset.UTC)));
		
		List<List<Zeitraum>> alleBelegungen = new ArrayList<>();
		alleBelegungen.add(einzelBelegungen);
		drawBelegung(alleBelegungen);
	}

	private void drawBelegung(int i, float von, float bis) {
		GraphicsContext gc = getGraphicsContext2D();
		clearField(i);
		setStyleBelegung(gc);
	    if (scalingType != null) {
	    	int xDist = metric.get(scalingType);
	    	gc.fillRect(200 + von * xDist, 129 + (i-1) * 40, (bis-von) * xDist, 10);
	    } 
	}

	private void setStyleBelegung(GraphicsContext gc) {
		gc.setFill(Color.RED);
	    gc.setStroke(Color.BLUE);
	    gc.setLineWidth(5);
	}

	private void drawFahrzeug() {
		GraphicsContext gc = getGraphicsContext2D();
		for (int i = 1; i <= 4; i++) {
			Image image = new Image(new File(String.format("src/main/java/de/woock/ddd/stattauto/auslastung/image/Car%s.png", i)).toURI().toString(), 350/4, 150/4, false, false);
		    setFontKennzeichen(gc);
			gc.fillText("HH LB 1378", 190, 100 + i  * 40);
			gc.drawImage(image, 30, 105 + (i - 1) * 40);
		}
	}

	private void drawDates(String dates) {
		GraphicsContext gc = getGraphicsContext2D();
		clearScala();
		
		setFontMonat(gc);
		drawJahr(gc);
		drawDatum(dates, gc);
		drawWochentage(gc); 
	}

	private void drawJahr(GraphicsContext gc) {
		if (scalingType != null && scalingType.equals("Jahr")){
			gc.fillText("Juli", 200, 50);
		}
	}

	private void drawDatum(String dates, GraphicsContext gc) {
		int c = 1;
		for (String d: dates.split(" ")){
			String date = String.valueOf(d);
		    setFontWochentage(gc);
		    if (scalingType != null) {
		    	gc.fillText(date, 200 + (c++-1) * metric.get(scalingType), 100);
		    }
		}
	}

	private void drawWochentage(GraphicsContext gc) {
		if (scalingType != null && (scalingType.equals("Woche") || scalingType.equals("Monat"))){
		    setFontWochentage(gc);
			List<String> wochentage = Arrays.asList("Mo", "Di", "Mi", "Do", "Fr", "Sa", "So");
			for (int i = 1; i <= 31; i++){
				if (scalingType != null){
					gc.fillText(wochentage.get((i-1) % 7), 200 + (i-1) * metric.get(scalingType), 85);
				}
			}
		}
	}

	private void drawTimeTable() {
		GraphicsContext gc = getGraphicsContext2D();
		Arrays.asList(1, 2, 3, 4).stream().forEach(l -> {
			gc.moveTo(xAnfang, 100 + yAbstand * l);
			gc.lineTo(xEnde, 100 + yAbstand * l);
		});
	
		gc.setLineWidth(1.0);
		gc.stroke();
	}

	private void setFontKennzeichen(GraphicsContext gc) {
		gc.setFont(Font.font("Calibri", 14));
		gc.setFill(Color.BLACK);
		gc.setTextAlign(TextAlignment.RIGHT);
	}

		private void setFontMonat(GraphicsContext gc) {
			gc.setFont(Font.font("Calibri", 20));
			gc.setFill(Color.BLACK);
			gc.setTextAlign(TextAlignment.CENTER);
		}

		private void setFontWochentage(GraphicsContext gc) {
			gc.setFont(Font.font("Calibri", 16));
			gc.setFill(Color.BLACK);
			gc.setTextAlign(TextAlignment.CENTER);
		}

		private void clearScala() {
			GraphicsContext gc = getGraphicsContext2D();
			gc.setFill(Color.WHITE);
		    gc.setStroke(Color.WHITE);
		    gc.setLineWidth(5);
		    gc.strokeLine(40, 10, 10, 40);
		    gc.fillRect(180, 35, 1300, 80);	
		}

		private void clearField(int i) {
			GraphicsContext gc = getGraphicsContext2D();
			gc.setFill(Color.WHITE);
		    gc.setStroke(Color.WHITE);
		    gc.setLineWidth(5);
		    gc.strokeLine(40, 10, 10, 40);
		    gc.fillRect(xAnfang, 34 + yAbstand+i*40, 1300, 25);
		}
}
