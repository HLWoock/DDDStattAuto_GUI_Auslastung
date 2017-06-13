package de.woock.ddd.stattauto.auslastung.views;


import org.springframework.stereotype.Component;

import de.woock.ddd.stattauto.auslastung.views.component.Chart;
import de.woock.ddd.stattauto.auslastung.views.component.SelectionPanel;
import de.woock.ddd.stattauto.auslastung.views.component.TimeShifter;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;


@Component
public class AuslastungsView {

	public VBox initPane() {
				
		Chart          chart = new Chart();
		SelectionPanel grid  = new SelectionPanel();
		
		Slider slider = new TimeShifter(chart, grid);

		
		VBox vbox = new VBox();
		vbox.getChildren().add(grid);
		vbox.getChildren().add(chart);
		vbox.getChildren().add(slider);
		return vbox;
	}
}
