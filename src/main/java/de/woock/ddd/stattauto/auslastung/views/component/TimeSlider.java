package de.woock.ddd.stattauto.auslastung.views.component;

import java.time.LocalDate;
import java.util.Map;

import org.springframework.stereotype.Component;

import de.woock.ddd.stattauto.auslastung.views.utils.ScaleUnit;
import javafx.geometry.Insets;
import javafx.scene.control.Slider;

@Component
public class TimeSlider extends Slider {
	
	private Chart           chart;
	private SelectionPanel  selectionPanel;

	public TimeSlider(Chart chart, SelectionPanel  selectionPanel) {
		super(0, 3, 0);
		this.chart          = chart;
		this.selectionPanel = selectionPanel;
		
		configSlider();
	    setLabelFormatter(new TimeSliderStringConverter());
		valueProperty().addListener( (ov, old_val, new_val) -> {setScaleOnChart(new_val);});
	}

	public void setScale(Map<ScaleUnit, LocalDate> map) {
		Double value = getLabelFormatter().fromString(((ScaleUnit) map.keySet().toArray()[0]).name());
		setValue(value);
		setScaleOnChart(value);
	}

	private void configSlider() {
		setWidth(500);
		setPadding(new Insets(0, 300, 0, 350));
		setMin(0);
		setMax(3);
		setValue(0);
        setMinorTickCount(0);
        setMajorTickUnit(1);
        setSnapToTicks(true);
        setShowTickMarks(true);
        setShowTickLabels(true);
	}

	private void setScaleOnChart(Number new_val) {
		chart         .setScalingType(getScalingType(new_val));
		selectionPanel.setScaleShapeForVisibility(getScalingType(new_val));
	}
	
	private ScaleUnit getScalingType(Number new_val) {
		Double d = new_val.doubleValue();
		if      (d < 0.5) return ScaleUnit.Tag;
		else if (d < 1.5) return ScaleUnit.Woche;
		else if (d < 2.5) return ScaleUnit.Monat;

		return ScaleUnit.Jahr;
	}	
}