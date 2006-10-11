<%@ page
	import="gov.nih.nci.caintegrator.application.lists.ListType,
	gov.nih.nci.caintegrator.application.lists.ListSubType,
	gov.nih.nci.caintegrator.application.lists.UserList,
	gov.nih.nci.caintegrator.application.lists.UserListBean,
	gov.nih.nci.ispy.web.helper.ISPYListValidator,
	gov.nih.nci.caintegrator.application.lists.ListManager,
	gov.nih.nci.caintegrator.application.lists.UserListBeanHelper,
	gov.nih.nci.caintegrator.application.lists.UserListGenerator,
	org.apache.commons.fileupload.DiskFileUpload,
	org.apache.commons.fileupload.FileUpload,
	org.apache.commons.fileupload.FileItem,
	java.io.File,
	java.util.Map,
	java.util.HashMap,
	java.util.Iterator,
	java.util.List,
	org.dom4j.Document"%>
<html>
	<head>
		<title>Upload.jsp</title>

	</head>

	<body>

		<%
		UserListGenerator listGenerator = new UserListGenerator();
		
		String name = "";
		String type = "";
		FileItem formFile = null;
        try {

            FileUpload fup = new FileUpload();
            //boolean isMultipart = FileUpload.isMultipartContent(request);
            // Create a new file upload handler
            // System.out.println(isMultipart);
            DiskFileUpload upload = new DiskFileUpload();

            // Parse the request
            List items = upload.parseRequest(request);

            for (Iterator i = items.iterator();i.hasNext();) {
                FileItem item = (FileItem)i.next();
                if (item.isFormField()) {
                     System.out.println(item.getString());
                     if(item.getFieldName().equalsIgnoreCase("listName")){
                        name = item.getString();
                     }
                     else if(item.getFieldName().equalsIgnoreCase("type")){
                        type = item.getString();                       
                     }
                } else {
                    // System.out.println("its a file");
                    // System.out.println(item.getName());
                    formFile = item;
                }
            }

        } catch (Exception e) {
            System.out.println(e);
        }
		List myUndefinedList = listGenerator.generateList(formFile);
            
            ListManager uploadManager = (ListManager) ListManager
                    .getInstance();
            Map paramMap = new HashMap();
            UserList myList = new UserList();

            UserListBeanHelper helper = new UserListBeanHelper(request.getSession());
            ISPYListValidator listValidator = new ISPYListValidator(ListType.valueOf(type), myUndefinedList);
            try	{
	            myList = uploadManager.createList(ListType.valueOf(type), name, myUndefinedList, listValidator);
    		}
    		catch(Exception e)	{
    			//myList = null;
    		}        
	        
            if (myList != null) {
            	myList.setListSubType(ListSubType.Custom);
                paramMap = uploadManager.getParams(myList);
                helper.addList(myList);
            }

            %>
		<script type="text/javascript">
			var my_params= new Array()
			my_params["name"]="<%=paramMap.get("listName")%>";
			my_params["date"]="<%=paramMap.get("date")%>";
			my_params["count"]="<%=paramMap.get("items")%>";
			my_params["icount"]="<%=paramMap.get("invalidItems")%>";
			my_params["type"]="<%=paramMap.get("type")%>";
			window.parent.handleResponse(my_params);
		</script>
	</body>
</html>
