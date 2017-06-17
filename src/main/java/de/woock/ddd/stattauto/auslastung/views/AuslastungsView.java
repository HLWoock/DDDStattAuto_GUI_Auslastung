package de.woock.ddd.stattauto.auslastung.views;


import org.springframework.stereotype.Component;

import de.woock.ddd.stattauto.auslastung.views.component.Chart;
import de.woock.ddd.stattauto.auslastung.views.component.SelectionPanel;
import de.woock.ddd.stattauto.auslastung.views.component.TimeSlider;
import javafx.scene.layout.VBox;


@Component
public class AuslastungsView {

	public VBox initPane() {
				
		Chart          chart           = new Chart();
		SelectionPanel selectionPanel  = new SelectionPanel(chart);
		TimeSlider     timeShifter     = new TimeSlider(chart, selectionPanel);
		
		chart.setSelectionPanel(selectionPanel);
		chart.update();

		VBox vbox = new VBox();
		vbox.getChildren().add(selectionPanel);
		vbox.getChildren().add(chart);
		vbox.getChildren().add(timeShifter);
		return vbox;
	}
}
