package de.woock.ddd.stattauto.auslastung.views.component;

import de.woock.ddd.stattauto.auslastung.views.utils.ScaleUnit;
import javafx.util.StringConverter;

public class TimeSliderStringConverter extends StringConverter<Double> {

	@Override
    public String toString(Double n) {
        if (n < 0.5) return ScaleUnit.Tag.name();
        if (n < 1.5) return ScaleUnit.Woche.name();
        if (n < 2.5) return ScaleUnit.Monat.name();

        return ScaleUnit.Jahr.name();
    }

    @Override
    public Double fromString(String s) {
        switch (s) {
            case "Tag"  : return 0d;
            case "Woche": return 1d;
            case "Monat": return 2d;
            case "Jahr" : return 3d;
            default     : return 3d;
        }
    }
}
