/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/i-spy/LICENSE.txt for details.
 */

package gov.nih.nci.ispy.ui.graphing.chart.plot;

import java.awt.Color;

public enum HER2ihcType {
	BORDERLINE {public Color getColor() {return Color.YELLOW; }},
	NO_OVEREXPRESSION {public Color getColor() {return Color.RED; }},
	OVEREXPRESSED {public Color getColor() {return Color.GREEN; }},
	WEAK_OVEREXPRESSED {public Color getColor() {return Color.ORANGE; }};
	
	public static String getOfficialGeneSymbol() { return "ERBB2"; }
	
	public abstract Color getColor();
}
