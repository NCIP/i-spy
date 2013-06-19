/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/i-spy/LICENSE.txt for details.
 */

package gov.nih.nci.ispy.web.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;




public abstract class AbstractGraphingTag extends TagSupport {

	protected Logger log = Logger.getLogger(this.getClass());
    
	protected final int doAfterEndTag(int returnVal) {
		reset();
		return returnVal;
	}

	protected abstract void reset();

	public int doEndTag() throws JspException {
		return doAfterEndTag(super.doEndTag());
	}

}