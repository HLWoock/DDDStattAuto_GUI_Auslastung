package de.woock.ddd.stattauto.auslastung.views.component;

import de.woock.ddd.stattauto.auslastung.views.utils.Scale;
import javafx.geometry.Insets;
import javafx.scene.control.Slider;
import javafx.util.StringConverter;

public class TimeShifter extends Slider {
	
	private Scale           timeTransformer = new Scale();
	private Chart           chart;
	private SelectionPanel  selectionPanel;

	public TimeShifter(Chart chart, SelectionPanel  selectionPanel) {
		super(0, 3, 0);
		this.chart = chart;
		setWidth(500);
		setPadding(new Insets(0, 300, 0, 350));
		setMin(0);
		setMax(3);
		setValue(1);
        setMinorTickCount(0);
        setMajorTickUnit(1);
        setSnapToTicks(true);
        setShowTickMarks(true);
        setShowTickLabels(true);
        
        setLabelFormatter(new StringConverter<Double>() {
            @Override
            public String toString(Double n) {
                if (n < 0.5) return "Tag";
                if (n < 1.5) return "Woche";
                if (n < 2.5) return "Monat";

                return "Jahr";
            }

            @Override
            public Double fromString(String s) {
                switch (s) {
                    case "Tag":
                        return 0d;
                    case "Woche":
                        return 1d;
                    case "Monat":
                        return 2d;
                    case "Jahr":
                        return 3d;

                    default:
                        return 3d;
                }
            }
        });

		
		valueProperty().addListener( (ov, old_val, new_val) -> {
				chart.setScale(timeTransformer.getTimeStream(new_val));
				chart.setScalingType(timeTransformer.getScalingType(new_val));
				selectionPanel.setScaleShape(timeTransformer.getScalingType(new_val));
			});
	}
}
