<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ page import="java.util.*"%>


<table width="80%" border="0" cellspacing="4" cellpadding="3">
  <tr><td>
  <fieldset>
		<legend>High Order Analysis:</legend><br>
		<html:errors property="org.apache.struts.action.GLOBAL_ERROR"/>
		<script type="text/javascript">Help.insertHelp("HOA_overview", " align='right'", "padding:2px;");</script>
		
			<table border="0" cellpadding="3" cellspacing="3">
				<tr><td><input type="button" class="xbutton" style="width:200px;margin-bottom: 5px;" value="Class Comparison Analysis" onclick="javascript:location.href='classcomparisonInit.do?method=setup';"></td></tr>				
				<tr><td><input type="button" class="xbutton" style="width:200px;margin-bottom: 5px;" value="Principal Component Analysis (PCA)" onclick="javascript:location.href='principalcomponentInit.do?method=setup';"></td></tr>				
				<tr><td><input type="button" class="xbutton" style="width:200px;margin-bottom: 5px;" value="Hierarchical Clustering Analysis" onclick="javascript:location.href='hierarchicalclusteringInit.do?method=setup';"></td></tr>			
				<tr><td><input type="button" class="xbutton" style="width:200px;margin-bottom: 5px;" value="Correlation Scatter Plot Analysis" onclick="javascript:location.href='correlationScatterInit.do?method=setup';"></td></tr>			
				<tr><td><input type="button" class="xbutton" style="width:200px;margin-bottom: 5px;" value="Categorical Plot Analysis" onclick="javascript:location.href='categoricalCorrelationInit.do?method=setup';"></td></tr>			
				<tr><td><input type="button" class="xbutton" style="width:200px;margin-bottom: 5px;" value="GP Integration" onclick="javascript:location.href='gpintegrationInit.do?method=setup';"></td></tr>
			</table>
	</fieldset>
	</td>
	</tr>
	
</table>


