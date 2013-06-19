/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/i-spy/LICENSE.txt for details.
 */

package gov.nih.nci.ispy.service.clinical;

import java.awt.Color;
import java.io.Serializable;





public enum TimepointType implements Serializable {
	T1 { public Color getColor() { return Color.GREEN; }},
	T2 { public Color getColor() { return Color.YELLOW; }},
	T3 { public Color getColor() { return Color.RED; }},
	T4 { public Color getColor() { return Color.BLUE; }};
	
	public abstract Color getColor();
}

