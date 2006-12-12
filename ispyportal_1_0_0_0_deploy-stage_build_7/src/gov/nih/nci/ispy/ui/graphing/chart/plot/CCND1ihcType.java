package gov.nih.nci.ispy.ui.graphing.chart.plot;

import java.awt.Color;

public enum CCND1ihcType {
	HIGH_EXPRESSION {public Color getColor() {return Color.GREEN; }},
	LOW_EXPRESSION {public Color getColor() {return Color.RED; }}; 
	
	public static String getOfficialGeneSymbol() { return "CCND1"; }
	
	public abstract Color getColor();
}
