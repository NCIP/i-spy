package gov.nih.nci.ispy.service.common;

import java.awt.Color;
import java.io.Serializable;





public enum TimepointType implements Serializable {
	T1 { public Color getColor() { return Color.GREEN; }},
	T2 { public Color getColor() { return Color.YELLOW; }},
	T3 { public Color getColor() { return Color.RED; }},
	T4 { public Color getColor() { return Color.BLUE; }};
	
	public abstract Color getColor();
}


