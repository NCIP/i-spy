<%--L
   Copyright SAIC

   Distributed under the OSI-approved BSD 3-Clause License.
   See http://ncip.github.com/i-spy/LICENSE.txt for details.
L--%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ page import="java.util.*"%>


<table width="80%" border="0" cellspacing="4" cellpadding="3">
  <tr><td>
  <fieldset>
		<legend>Advanced Analysis:</legend><br>
		<html:errors property="org.apache.struts.action.GLOBAL_ERROR"/>
			<script type="text/javascript">Help.insertHelp("Searches_overview", " align='right'", "padding:2px;");</script>
		
			<table border="0" cellpadding="3" cellspacing="3">
				<tr><td><input type="button" class="xbutton" style="width:200px;margin-bottom: 5px;" value="Clinical Query" onclick="javascript:location.href='clinicalQueryInit.do?method=setup';"></td></tr>
				<tr><td><input type="button" class="xbutton" style="width:200px;margin-bottom: 5px;" value="IHC Level of Expression Query" onclick="javascript:location.href='ihcLevelQueryInit.do?method=setup';"></td></tr>
				<tr><td><input type="button" class="xbutton" style="width:200px;margin-bottom: 5px;" value="IHC Loss of Expression Query" onclick="javascript:location.href='ihcLossQueryInit.do?method=setup';"></td></tr>
				<!--<tr><td><input type="button" class="xbutton" style="width:200px;margin-bottom: 5px;" value="FISH Query" onclick="javascript:location.href='fishQueryInit.do?method=setup';"></td></tr>	-->								
			</table>
	</fieldset>
	</td></tr>
	
</table>


