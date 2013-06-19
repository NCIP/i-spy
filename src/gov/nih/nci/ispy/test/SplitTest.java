/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/i-spy/LICENSE.txt for details.
 */

package gov.nih.nci.ispy.test;

public class SplitTest {

	public SplitTest() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String str = "ACB";
		
		String[] tokens = str.split("\\|");
		
		dumpTokens(tokens);
		
	}
	
	private static void dumpTokens(String[] tokens) {
	  for (int i=0; i < tokens.length; i++) {
	    System.out.println("token " + i + " =" + tokens[i]);
	  }
		
	}

}
