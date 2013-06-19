/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/i-spy/LICENSE.txt for details.
 */

package gov.nih.nci.ispy.ui.graphing.chart.plot;

import java.awt.Color;

public enum Ki67ihcType {
  HIGH {public Color getColor() {return Color.GREEN; }},
  INTERMEDIATE {public Color getColor() {return Color.YELLOW; }},
  LOW {public Color getColor() {return Color.ORANGE; }},
  ZERO {public Color getColor() {return Color.BLUE; }};
  
  public static String getOfficialGeneSymbol() { return "MKI76"; }
  
  public abstract Color getColor();
}
