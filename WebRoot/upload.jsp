<%@ page import ="gov.nih.nci.caintegrator.application.lists.ListType,
gov.nih.nci.caintegrator.application.lists.UserList,
gov.nih.nci.caintegrator.application.lists.UserListBean,
gov.nih.nci.ispy.util.ISPYUploadManager,
gov.nih.nci.ispy.web.helper.ISPYUserListBeanHelper,
gov.nih.nci.ispy.web.helper.ISPYUserListGenerator,
org.apache.struts.upload.FormFile,
java.io.File,
java.util.Map,
java.util.HashMap,
java.util.List,
org.dom4j.Document"%>
<html>
	<head>
		<title>Upload.jsp</title>

	</head>

	<body>
		
		<%
		ISPYUserListGenerator listGenerator = new ISPYUserListGenerator();
		List myUndefinedList = listGenerator.generateList(request);
		
		ISPYUploadManager uploadManager = (ISPYUploadManager) ISPYUploadManager
                    .getInstance();
            Map paramMap = new HashMap();

            
            ISPYUserListBeanHelper helper = new ISPYUserListBeanHelper(
                    request.getSession());

            UserList myList = uploadManager.createList(ListType.PatientDID,"myPatientList", myUndefinedList);
            if (myList != null) {
                paramMap = uploadManager.getParams(myList);
                helper.addList(myList);
            }
            
        %>
        
        
<script>
var my_params= new Array()
my_params["name"]="<%=paramMap.get("listName")%>";
my_params["date"]="<%=paramMap.get("date")%>";
my_params["count"]="<%=paramMap.get("items")%>";
my_params["type"]="<%=paramMap.get("type")%>";

window.parent.handleResponse(my_params);
</script>

	</body>
</html>
