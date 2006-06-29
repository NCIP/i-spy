<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ispy.tld" prefix="app" %>
<%@ page import="java.util.*, gov.nih.nci.ispy.web.struts.form.*" %> 


<fieldset class="gray">
<legend class="red">Receptor Status
<app:help help="Select receptor status to further filter the query" />
</legend>
	      <div class="mains">
	      
	      <div>
	        <b class="subfield">Status</b>
					<html:select property="receptorStatus" multiple="true" size="4">
							<option>ER+</option>
							<option>ER-</option>
							<option>HER2+</option>
							<option>HER2-</option>
							<option>PR+</option>
							<option>PR-</option>
							<option>...pulled as userlist</option>
					</html:select>
			</div>
		</div>
			
</fieldset>
