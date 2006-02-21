<%@ page import="java.util.*, java.text.*" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>


<fieldset class="gray">
<legend class="red">		
		Name Analysis Result
<b class="req">*</b>
</legend>
<br>
	<html:errors property="analysisResultName"/>
<html:text styleId="analysisResultName" property="analysisResultName" size="50" /> (should be unique)
<br />
<html:errors property="queryName"/><br />
</fieldset>


