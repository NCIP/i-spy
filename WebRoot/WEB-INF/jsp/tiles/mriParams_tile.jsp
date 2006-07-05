<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ispy.tld" prefix="app" %>
<%@ page import="java.util.*, gov.nih.nci.ispy.web.struts.form.*" %> 


<fieldset class="gray">
<legend class="red">MR Parameters
<app:help help="Select mri parameters to further filter the query" />
</legend>
	<div class="mains">
	      <div>
	        <b class="subfield">Morphology</b>
					<html:textarea property="morphology" value="[keyword matches]" rows="3" cols="40" />
									
			</div>		
			
			<div>
					<b class="subfield">LD (cm)</b>
					<html:select property="ldLengthOperator">			
						<html:optionsCollection property="operators" />
	  				</html:select>	  				
					<html:text property="ldLength" />(cm)
			</div>
			<div>					
					<b class="subfield">% Change</b>
					<html:select property="ldPercentChangeOperator">			
						<html:optionsCollection property="operators" />
	  				</html:select>
					<html:text property="ldPercentChange" />(cm)
			</div>
			
	</div>
</fieldset>
