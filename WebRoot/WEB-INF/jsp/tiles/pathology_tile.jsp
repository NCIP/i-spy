<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ispy.tld" prefix="app" %>
<%@ page import="java.util.*, gov.nih.nci.ispy.web.struts.form.*" %> 


<fieldset class="gray">
<legend class="red">Pathology
<a href="javascript: Help.popHelp('pathology_tooltip_clinicalq');">[?]</a></legend>

	      <div class="mains">
	      <html:errors property="pathTumorSizeParse" />
			<div>
					<b class="subfield">Path. Tumor Size</b>					
					<html:select property="pathTumorSizeOperator">			
						<html:optionsCollection property="operators" />
	  				</html:select>
					<html:text property="pathTumorSize" />(cm)
			</div>
	      <div>
	        <b class="subfield">Status</b>
					<html:select property="receptorStatus" multiple="true" size="4">
							<html:optionsCollection property="receptorCollection" />
					</html:select>
			</div>
				<div>					
					<b class="subfield">Residual Cancer Burden Index</b><br/>
					
					    <html:select property="rcbOperator">			
						<html:optionsCollection property="operators" />
	  				    </html:select>	  				
					<html:text property="rcbSize" />
					
			</div>
			
			 <div>
	        <b class="subfield">Path. Complete Response</b>
					<html:select property="pcrStatus" multiple="true" size="2">
							<html:optionsCollection property="pcrCollection" />
					</html:select>
			</div>
			
			
			
		</div>
			
</fieldset>
