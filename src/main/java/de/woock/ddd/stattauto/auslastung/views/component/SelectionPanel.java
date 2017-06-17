package de.woock.ddd.stattauto.auslastung.views.component;



import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.time.temporal.Temporal;
import java.time.temporal.WeekFields;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import de.woock.ddd.stattauto.auslastung.util.Week;
import de.woock.ddd.stattauto.auslastung.views.utils.ScaleUnit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class SelectionPanel  extends GridPane {
	
	private Label              lbJahr, lbMonat, lbWoche, lbTag;
	private ComboBox<String>   cbJahr, cbMonat;
	private ComboBox<Integer>  cbWoche, cbTag;

	public SelectionPanel(Chart chart) {
		setAlignment(Pos.CENTER);
		setHgap(10);
		setVgap(10);
		setPadding(new Insets(25, 25, 25, 25));
		
		Text scenetitle = new Text("Auslastung für Station");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		add(scenetitle, 0, 0, 8, 1);

		Label lbName      = new Label("Stationskürzel:"); add(lbName     , 0, 1); TextField txName      = new TextField(); add(txName     ,  1, 1);
		Label lbStadt     = new Label("Stadt:")         ; add(lbStadt    , 3, 1); TextField txStadt     = new TextField(); add(txStadt    ,  4, 1);
		Label lbStadtteil = new Label("Stadtteil:")     ; add(lbStadtteil, 6, 1); TextField txStadtteil = new TextField(); add(txStadtteil,  7, 1);
		Label lbStandort  = new Label("Standort:")      ; add(lbStandort , 9, 1); TextField txStandort  = new TextField(); add(txStandort , 10, 1);

		ObservableList<String> olJahr  = FXCollections.observableArrayList("2016", "2017", "2018", "2019", "2020");		
		ObservableList<String> olMonat = FXCollections.observableArrayList("Jan", "Feb", "Mrz", "Apr", "Mai", "Jun", "Jul", "Aug", "Spt", "Okt", "Nov", "Dez");
		lbJahr      = new Label("Jahr:")          ; add(lbJahr     , 0, 2); cbJahr      = new ComboBox<>(olJahr) ; add(cbJahr  ,  1, 2);
		lbMonat     = new Label("Monat:")         ; add(lbMonat    , 3, 2); cbMonat     = new ComboBox<>(olMonat); add(cbMonat,   4, 2);
		lbTag       = new Label("Tag:")           ; add(lbTag      , 6, 2); cbTag       = new ComboBox<>()       ; add(cbTag   ,  7, 2);
		lbWoche     = new Label("Woche:")         ; add(lbWoche    , 9, 2); cbWoche     = new ComboBox<>()       ; add(cbWoche , 10, 2);
		
		Button bnWeiter  = new Button(">"); add(bnWeiter , 6, 3, 2, 1);
		Button bnZurueck = new Button("<"); add(bnZurueck, 4, 3, 2, 1);
		
		fillCBAndSetActualDate();
		setActionListener(chart);
	}

	public Map<ScaleUnit, Temporal> getAuswahlMap() {
		Map<ScaleUnit, Temporal> auswahlMap = new HashMap<>();
		int jahr = 2017, monat = 6, woche = 0, tag= 6;
		if (!cbJahr.getSelectionModel().isEmpty())  { jahr  = Integer.valueOf(cbJahr.getSelectionModel().getSelectedItem());}
		if (!cbMonat.getSelectionModel().isEmpty()) { monat = cbMonat.getSelectionModel().getSelectedIndex() + 1;}
		if (!cbWoche.getSelectionModel().isEmpty()) { woche = cbWoche.getSelectionModel().getSelectedItem()     ;}		
		if (!cbTag.getSelectionModel().isEmpty())   { tag   = cbTag  .getSelectionModel().getSelectedIndex() + 1;}
		auswahlMap.put(ScaleUnit.Jahr,  Year.of(jahr));
		auswahlMap.put(ScaleUnit.Monat, YearMonth.of(jahr, monat));
		auswahlMap.put(ScaleUnit.Woche, Week.of(woche, jahr, monat));
		auswahlMap.put(ScaleUnit.Tag,   LocalDate.of(jahr, monat, tag));
		return auswahlMap;
	}

	public void setScaleShapeForVisibility(ScaleUnit scaleUnit) {
		switch (scaleUnit) {
		case Jahr:
			lbJahr.setVisible(true); lbMonat.setVisible(false); lbWoche.setVisible(false); lbTag.setVisible(false);
			cbJahr.setVisible(true); cbMonat.setVisible(false); cbWoche.setVisible(false); cbTag.setVisible(false);
			break;
		case Monat:
			lbJahr.setVisible(true); lbMonat.setVisible(true); lbWoche.setVisible(false); lbTag.setVisible(false);
			cbJahr.setVisible(true); cbMonat.setVisible(true); lbWoche.setVisible(false); cbTag.setVisible(false);
			break;
		case Woche:
			lbJahr.setVisible(true); lbMonat.setVisible(true); lbWoche.setVisible(true); lbTag.setVisible(false);
			cbJahr.setVisible(true); cbMonat.setVisible(true); cbWoche.setVisible(true); cbTag.setVisible(false);
			break;
		case Tag:
			lbJahr.setVisible(true); lbMonat.setVisible(true); lbWoche.setVisible(false); lbTag.setVisible(true);
			cbJahr.setVisible(true); cbMonat.setVisible(true); cbWoche.setVisible(false); cbTag.setVisible(true);
			break;
		default:
			break;
		}
		
	}

	private void setActionListener(Chart chart) {
		cbJahr .setOnAction(v -> {fillComboBoxes(); chart.update();});
		cbMonat.setOnAction(v -> {fillComboBoxes(); chart.update();});
		cbWoche.setOnAction(v -> {cbTag.getSelectionModel()  .clearSelection(); chart.update();});
		cbTag  .setOnAction(v -> {cbWoche.getSelectionModel().clearSelection(); chart.update();});
	}

	private void fillCBAndSetActualDate() {
		LocalDate now = LocalDate.now();
		cbJahr.getSelectionModel() .select(String.valueOf(now.getYear()));
		cbMonat.getSelectionModel().select(now.getMonth().getValue() - 1);
		fillComboBoxes();
		cbTag.getSelectionModel()  .select(now.getDayOfMonth() - 1);
		cbWoche.getSelectionModel().selectFirst();
		cbWoche.setVisible(false);
		lbWoche.setVisible(false);
	}

	private void fillComboBoxes() {
		int year  = Integer.valueOf(cbJahr .getSelectionModel().getSelectedItem());
		int month = cbMonat.getSelectionModel().getSelectedIndex() + 1;
		
		if (month >= 1 && month <= 12) {
			List<Integer> range = IntStream.rangeClosed(1, LocalDate.of(year,month, 1).lengthOfMonth()).boxed().collect(Collectors.toList());
			cbTag.setItems(FXCollections.observableArrayList(range));
		
			int fromWeek = LocalDate.of(year, month, 1).get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear());
			int toweek   = LocalDate.of(year, month, LocalDate.of(year, month, 1).lengthOfMonth()).get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear());
			if (fromWeek > toweek) fromWeek = 1;
			range = IntStream.rangeClosed(fromWeek, toweek).boxed().collect(Collectors.toList());
			cbWoche.setItems(FXCollections.observableArrayList(range));
		}
		else {
			cbTag.setItems(null);
			cbWoche.setItems(null);
		}
	}

}
