<%--L
   Copyright SAIC

   Distributed under the OSI-approved BSD 3-Clause License.
   See http://ncip.github.com/i-spy/LICENSE.txt for details.
L--%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ispy.tld" prefix="app" %>
<%@ page import="java.util.*, gov.nih.nci.ispy.web.struts.form.*" %> 


<fieldset class="gray">
<legend class="red">MR Parameters
<app:help help="Select mri parameters to further filter the query. Morphology text area will parse a line delimited set of keywords." />
</legend>
	<div class="mains">
	      <div>
	        <b class="subfield">Morphology</b>
					<!--<html:textarea property="morphology" value="" rows="3" cols="40" />	-->
					<html:select property="morphology" multiple="true" size="4">			
						<html:optionsCollection property="morphologyCollection" />
	  				</html:select>								
			</div>		
			
			<!--<div>
					<b class="subfield">LD (cm)</b>
					<html:select property="ldLengthOperator">			
						<html:optionsCollection property="operators" />
	  				</html:select>	  				
					<html:text property="ldLength" />(cm)
			</div>-->
			
			<div>					
					<b class="subfield">% LD Decrease</b><br/>
					<table align="left">
					  <tr><td>
					     <html:select property="ldTimepointRange">
						<html:option value="none">none</html:option>
						<html:optionsCollection property="ldTimepointRangeCollection" />
					</html:select>
					   </td>
					
					<td>
					    <html:select property="ldPercentChangeOperator">			
						<html:optionsCollection property="operators" />
	  				    </html:select>
	  				 </td>
					<td>
					<html:text property="ldPercentChange" />%
					</td></tr></table>
			</div>
			
	</div>
</fieldset>
