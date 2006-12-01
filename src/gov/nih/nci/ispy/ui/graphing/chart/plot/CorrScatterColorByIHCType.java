package gov.nih.nci.ispy.ui.graphing.chart.plot;

import java.awt.Color;

public enum CorrScatterColorByIHCType {
	X_POS_Y_POS {public Color getColor() {return Color.red; }},
	X_POS_Y_NEG {public Color getColor() {return Color.orange; }},
	X_NEG_Y_POS {public Color getColor() {return Color.yellow; }},
	X_NEG_Y_NEG {public Color getColor() {return Color.green; }};
	
	public abstract Color getColor();
}
