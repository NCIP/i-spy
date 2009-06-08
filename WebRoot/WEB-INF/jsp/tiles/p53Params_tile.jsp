<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ispy.tld" prefix="app" %>
<%@ page import="java.util.*, gov.nih.nci.ispy.web.struts.form.*" %> 


<fieldset class="gray">
<legend class="red">P53 Mutation Status
<a href="javascript: Help.popHelp('P53_tooltip_mutation_status');">[?]</a></legend>

	<div class="mains">
	      <div>&nbsp;&nbsp;	        			
					<html:select property="mutationStatus" multiple="true" size="3">						
						<html:optionsCollection property="mutationStatusCollection" />
					</html:select>				
		 </div>
	</div>
</fieldset>



<fieldset class="gray">
<legend class="red">Mutation Type
<a href="javascript: Help.popHelp('P53_tooltip_mutation_status');">[?]</a></legend>

	<div class="mains">
	      <div>	 &nbsp;&nbsp;       			
					<html:select property="mutationType" multiple="true" size="3">						
						<html:optionsCollection property="mutationTypeCollection" />
					</html:select>				
		 </div>
	</div>
</fieldset>



