<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ispy.tld" prefix="app" %>
<%@ page import="java.util.*, gov.nih.nci.ispy.web.struts.form.*" %> 


<fieldset class="gray">
<legend class="red">Invasive Range
<app:help help="Select range of invasive sum to further filter the query." />
</legend>
	<div class="mains">
	      <div>&nbsp;&nbsp;	
	      			invasive sum	      			        			
					<html:select property="invasiveRangeOperator">						
						<html:optionsCollection property="operators" />
					</html:select>
					<html:text property="invasiveRange" size="3" />				
		 </div>
	</div>
</fieldset>

<fieldset class="gray">
<legend class="red">Benign Range
<app:help help="Select range of benign sum to further filter the query." />
</legend>
	<div class="mains">
	      <div>&nbsp;&nbsp;	
	      			benign sum	      			        			
					<html:select property="benignRangeOperator">						
						<html:optionsCollection property="operators" />
					</html:select>
					<html:text property="benignRange" size="3" />				
		 </div>
	</div>
</fieldset>

<fieldset class="gray">
<legend class="red">Result Code
<app:help help="Select result code to further filter the query." />
</legend>
	<div class="mains">
	      <div>	 &nbsp;&nbsp;       			
					<html:select property="lossResult" multiple="true" size="3">						
						<html:optionsCollection property="lossCollection" />
					</html:select>				
		 </div>
	</div>
</fieldset>


