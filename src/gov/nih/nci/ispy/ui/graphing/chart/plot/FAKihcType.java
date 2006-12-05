package gov.nih.nci.ispy.ui.graphing.chart.plot;

import java.awt.Color;

public enum FAKihcType {
  NO_OVEREXPRESSION {public Color getColor() {return Color.RED; }}, 
  OVEREXPRESSED {public Color getColor() {return Color.GREEN; }};
  
  public static String getOfficialGeneSymbol() { return "PTK2"; }
  
  public abstract Color getColor();
}
