package de.woock.ddd.stattauto.auslastung.views;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.woock.ddd.stattauto.auslastung.views.component.Chart;
import de.woock.ddd.stattauto.auslastung.views.component.SelectionPanel;
import de.woock.ddd.stattauto.auslastung.views.component.TimeSlider;
import javafx.scene.layout.VBox;


@Component
public class AuslastungsView {

	@Autowired Chart          chart;
	@Autowired SelectionPanel selectionPanel;
	@Autowired TimeSlider     timeShifter;

	public VBox initPane() {
		chart.setSelectionPanel(selectionPanel);
		chart.update();

		VBox vbox = new VBox();
		vbox.getChildren().add(selectionPanel);
		vbox.getChildren().add(timeShifter);
		vbox.getChildren().add(chart);
		return vbox;
	}
}
