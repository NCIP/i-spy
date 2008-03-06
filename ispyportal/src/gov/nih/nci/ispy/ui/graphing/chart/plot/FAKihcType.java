package gov.nih.nci.ispy.ui.graphing.chart.plot;

import java.awt.Color;

public enum FAKihcType {
  NO_OVEREXPRESSION {public Color getColor() {return Color.GREEN; }}, 
  OVEREXPRESSED {public Color getColor() {return Color.RED; }};
  
  public static String getOfficialGeneSymbol() { return "PTK2"; }
  
  public abstract Color getColor();
}
