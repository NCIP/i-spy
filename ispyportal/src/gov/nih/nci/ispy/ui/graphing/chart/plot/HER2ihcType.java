package gov.nih.nci.ispy.ui.graphing.chart.plot;

import java.awt.Color;

public enum HER2ihcType {
	BORDERLINE {public Color getColor() {return Color.ORANGE; }},
	NO_OVEREXPRESSION {public Color getColor() {return Color.GREEN; }},
	OVEREXPRESSED {public Color getColor() {return Color.RED; }},
	WEAK_OVEREXPRESSED {public Color getColor() {return Color.YELLOW; }};
	
	public static String getOfficialGeneSymbol() { return "ERBB2"; }
	
	public abstract Color getColor();
}
