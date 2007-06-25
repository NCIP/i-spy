package gov.nih.nci.ispy.ui.graphing.chart.plot;

import java.awt.Color;

public enum P27ihcType {
	NO_LOSS {public Color getColor() {return Color.RED; }}, 
	PARTIAL_LOSS {public Color getColor() {return Color.YELLOW; }}, 
	TOTAL_LOSS {public Color getColor() {return Color.GREEN; }};
	
	public static String getOfficalGeneSymbol() { return "Cdkn1b"; }
	
	public abstract Color getColor();
}
