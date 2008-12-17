<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ispy.tld" prefix="app" %>
<%@ page import="java.util.*, gov.nih.nci.ispy.web.struts.form.*" %> 


<fieldset class="gray">
<legend class="red">P53 Mutation Status
<app:help help="Select mutation status to further filter the query." />
</legend>
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
<app:help help="Select mutation type to further filter the query." />
</legend>
	<div class="mains">
	      <div>	 &nbsp;&nbsp;       			
					<html:select property="mutationType" multiple="true" size="3">						
						<html:optionsCollection property="mutationTypeCollection" />
					</html:select>				
		 </div>
	</div>
</fieldset>



