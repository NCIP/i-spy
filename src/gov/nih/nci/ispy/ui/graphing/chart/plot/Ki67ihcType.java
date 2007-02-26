package gov.nih.nci.ispy.ui.graphing.chart.plot;

import java.awt.Color;

public enum Ki67ihcType {
  HIGH {public Color getColor() {return Color.RED; }},
  INTERMEDIATE {public Color getColor() {return Color.ORANGE; }},
  LOW {public Color getColor() {return Color.YELLOW; }},
  ZERO {public Color getColor() {return Color.BLUE; }};
  
  public static String getOfficialGeneSymbol() { return "MKI76"; }
  
  public abstract Color getColor();
}
