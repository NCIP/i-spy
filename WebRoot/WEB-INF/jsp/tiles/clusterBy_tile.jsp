<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ page import="gov.nih.nci.caintegrator.dto.critieria.Constants"%>

<fieldset class="gray">
<legend class="red">
Cluster By
</legend>
	
<br>

			<html:radio styleClass="radio" property="clusterBy" value="Samples"/>
			&nbsp;Samples<br />
			<html:radio styleClass="radio" property="clusterBy" value="Genes"/>
			&nbsp;Genes<br />			
			
</fieldset>
