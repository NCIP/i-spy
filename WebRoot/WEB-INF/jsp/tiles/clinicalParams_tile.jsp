<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ispy.tld" prefix="app" %>
<%@ page import="java.util.*, gov.nih.nci.ispy.web.struts.form.*" %> 


<fieldset class="gray">
<legend class="red">Clinical Parameters
<a href="javascript: Help.popHelp('clinical_parameters_tooltip_clinicalq');">[?]</a></legend>
</legend>
       <div class="mains">
	       <div>
	        <b class="subfield">Clinical Stage</b>
					<html:select property="clinicalStages" multiple="true" size="4">
							<html:optionsCollection property="clinicalStageCollection" />
					</html:select>
			</div>
			<!--<div>
			 <b class="subfield">Histology Type</b>
					<html:select property="histologyType" multiple="true" size="4">
						<option>Necrosis</option>
						<option>Lobular carcinoma</option>
						<option>Ductal carcinoma</option>
						<option>Mixed Ductal/Lobular carcinoma</option>
						<option>Other</option>
						<option>...pulled as userlist</option>
					</html:select>
			</div>
			<div>
				<b class="subfield">Nuclear Grade</b>
					<html:select property="nuclearGrade" size="4" multiple="true">
						<option>Grade III (high)</option>
						<option>Grade II (intermediate)</option>
						<option>Grade I (low)</option>
						<option>Indeterminate</option>
					</html:select>
			</div>-->
			<div>
					<b class="subfield">Agents<br/></b>
					<html:select property="agents" size="4" multiple="true">
						<html:optionsCollection property="agentsCollection" />
					</html:select>
					<br/>
								
			</div>
			<div>
					<b class="subfield">Clinical Response</b>
					<html:select property="response" size="4" multiple="true">
						<html:optionsCollection property="responseCollection" />
					</html:select>
			</div>
			<!--<html:errors property="diameterParse" />
			<div>
					<b class="subfield">Diameter</b>					
					<html:select property="diameterOperator">			
						<html:optionsCollection property="operators" />
	  				</html:select>
					<html:text property="diameter" />(cm)
			</div>-->
			
			<div>
			      <b class="subfield">Race</b>
			      <html:select property="race" size="4" multiple="true">			            
						<html:optionsCollection property="raceCollection" />
	  			  </html:select>
	  	    </div>
	  	    <div>
			      <b class="subfield">Age</b>
			      <html:select property="age" size="4" multiple="true">				            
						<html:optionsCollection property="ageCollection" />
	  			  </html:select>
	  	    </div>
			
	</div>

</fieldset>
