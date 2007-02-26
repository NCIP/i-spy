package gov.nih.nci.ispy.ui.graphing.chart.plot;

import java.awt.Color;

public enum EGFRihcType {
   NEGATIVE {public Color getColor() {return Color.GREEN; }}, 
   POSITIVE {public Color getColor() {return Color.RED; }};
   
   public static String getOfficialGeneSymbol() { return "EGFR"; }
   
   public abstract Color getColor();
}
