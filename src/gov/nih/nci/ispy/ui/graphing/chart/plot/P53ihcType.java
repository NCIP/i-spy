package gov.nih.nci.ispy.ui.graphing.chart.plot;

import java.awt.Color;

public enum P53ihcType {
	OVEREXPRESSED {public Color getColor() {return Color.GREEN; }},
	NO_OVEREXPRESSION {public Color getColor() {return Color.RED; }}; 
	
	public static String getOfficialGeneSymbol() { return "TP53"; }
	
	public abstract Color getColor();
}
