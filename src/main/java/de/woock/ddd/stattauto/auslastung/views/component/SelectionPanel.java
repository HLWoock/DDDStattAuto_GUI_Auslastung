package de.woock.ddd.stattauto.auslastung.views.component;



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
import javafx.collections.*;

public class SelectionPanel  extends GridPane {
	
	private Label     lbJahr, lbMonat, lbWoche, lbTag;
	private TextField         txWoche, txTag;
	private ComboBox<String>  cbJahr, cbMonat;

	public SelectionPanel() {
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

		ObservableList<String> olJahr  = FXCollections.observableArrayList("2016", "2017", "2018");		
		ObservableList<String> olMonat = FXCollections.observableArrayList("Jan", "Feb", "Mrz", "Apr", "Mai", "Jun", "Jul", "Aug", "Spt", "Okt", "Nov", "Dez");
		lbJahr      = new Label("Jahr:")          ; add(lbJahr     , 0, 2); cbJahr      = new ComboBox<>(olJahr) ; add(cbJahr  ,  1, 2);
		lbMonat     = new Label("Monat:")         ; add(lbMonat    , 3, 2); cbMonat     = new ComboBox<>(olMonat); add(cbMonat,   4, 2);
		lbWoche     = new Label("Woche:")         ; add(lbWoche    , 6, 2); txWoche     = new TextField()        ; add(txWoche ,  7, 2);
		lbTag       = new Label("Tag:")           ; add(lbTag      , 9, 2); txTag       = new TextField()        ; add(txTag   , 10, 2);
		
		Button bnWeiter  = new Button(">"); add(bnWeiter , 6, 3, 2, 1);
		Button bnZurueck = new Button("<"); add(bnZurueck, 4, 3, 2, 1);

	}

	public void setScaleShape(String scalingType) {
		switch (scalingType) {
		case "Jahr":
			lbJahr.setVisible(true); lbMonat.setVisible(false); lbWoche.setVisible(false); lbWoche.setVisible(false); lbTag.setVisible(false);
			cbJahr.setVisible(true); cbMonat.setVisible(false); txWoche.setVisible(false); txWoche.setVisible(false); txTag.setVisible(false);
			break;
		case "Monat":
			lbJahr.setVisible(true); lbMonat.setVisible(true); lbWoche.setVisible(false); lbWoche.setVisible(false); lbTag.setVisible(false);
			cbJahr.setVisible(true); cbMonat.setVisible(true); txWoche.setVisible(false); txWoche.setVisible(false); txTag.setVisible(false);
			break;
		case "Woche":
			lbJahr.setVisible(true); lbMonat.setVisible(true); lbWoche.setVisible(true); lbWoche.setVisible(true); lbTag.setVisible(false);
			cbJahr.setVisible(true); cbMonat.setVisible(true); txWoche.setVisible(true); txWoche.setVisible(true); txTag.setVisible(false);
			break;
		case "Tag":
			lbJahr.setVisible(true); lbMonat.setVisible(true); lbWoche.setVisible(true); lbWoche.setVisible(true); lbTag.setVisible(true);
			cbJahr.setVisible(true); cbMonat.setVisible(true); txWoche.setVisible(true); txWoche.setVisible(true); txTag.setVisible(true);
			break;
		default:
			lbJahr.setVisible(true); lbMonat.setVisible(true); lbWoche.setVisible(true); lbWoche.setVisible(true); lbTag.setVisible(true);
			cbJahr.setVisible(true); cbMonat.setVisible(true); txWoche.setVisible(true); txWoche.setVisible(true); txTag.setVisible(true);
			break;
		}
		
	}

}
