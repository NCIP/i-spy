package gov.nih.nci.ispy.ui.graphing.chart.plot;

import java.awt.Color;

public enum CorrScatterColorByIHCType {
	POSITIVE {public Color getColor() {return Color.GREEN; }},
	INTERMEDIATE {public Color getColor() {return Color.yellow; }},
	NEGATIVE {public Color getColor() {return Color.red; }},
	NA {public Color getColor() {return Color.GRAY; }};
	
	public abstract Color getColor();
}
