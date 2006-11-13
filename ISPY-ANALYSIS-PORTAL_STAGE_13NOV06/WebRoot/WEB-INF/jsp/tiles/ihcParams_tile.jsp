<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ispy.tld" prefix="app" %>
<%@ page import="java.util.*, gov.nih.nci.ispy.web.struts.form.*" %> 


<fieldset class="gray">
<legend class="red">Intensity of Stain
<app:help help="Select intensity of stain to further filter the query." />
</legend>
	<div class="mains">
	      <div>&nbsp;&nbsp;	        			
					<html:select property="stainIntensity" multiple="true" size="3">						
						<html:optionsCollection property="stainIntensityCollection" />
					</html:select>				
		 </div>
	</div>
</fieldset>

<fieldset class="gray">
<legend class="red">Percent Positive
<app:help help="Select percent positive to further filter the query." />
</legend>
	<div class="mains">
	      <div>	        			
					 &nbsp;&nbsp;between &nbsp;&nbsp;<html:text property="lowPercentPositive" />						
					% &nbsp;and &nbsp;<html:text property="upPercentPositive" />	
					%				
		 </div>
	</div>
</fieldset>

<fieldset class="gray">
<legend class="red">Localization of Stain
<app:help help="Select localization of stain to further filter the query." />
</legend>
	<div class="mains">
	      <div>	 &nbsp;&nbsp;       			
					<html:select property="stainLocalization" multiple="true" size="3">						
						<html:optionsCollection property="stainLocalizationCollection" />
					</html:select>				
		 </div>
	</div>
</fieldset>

<fieldset class="gray">
<legend class="red">Distribution of Stain
<app:help help="Select distribution of stain to further filter the query." />
</legend>
	<div class="mains">
	      <div>	  &nbsp;&nbsp;      			
					<html:select property="stainDistribution" multiple="true" size="3">						
						<html:optionsCollection property="stainDistributionCollection" />
					</html:select>				
		 </div>
	</div>
</fieldset>

