<%@ page
	import="gov.columbia.c2b2.ispy.list.ListType,
	gov.columbia.c2b2.ispy.list.ListSubType,
	gov.columbia.c2b2.ispy.list.UserListN,
	gov.columbia.c2b2.ispy.list.UserListBean,
	gov.nih.nci.ispy.web.helper.ISPYListValidator,
	gov.columbia.c2b2.ispy.list.ListManager,
	gov.columbia.c2b2.ispy.list.UserListBeanHelper,
	gov.columbia.c2b2.ispy.list.UserListGenerator,
	org.apache.commons.fileupload.DiskFileUpload,
	org.apache.commons.fileupload.FileUpload,
	org.apache.commons.fileupload.FileItem,
	java.io.File,
	java.util.Map,
	java.util.ArrayList,
	java.util.HashMap,
	java.util.HashSet,
	java.util.Iterator,
	java.util.List,
	gov.columbia.c2b2.ispy.list.ajax.*,
	org.dom4j.Document"%>
<html>
	<head>
		<title>Upload.jsp</title>

	</head>

	<body>

		<%
		UserListGenerator listGenerator = new UserListGenerator();
		
		// !! - need to use the new listValidator constructor here that takes sub-types
		//ISPYListValidator listValidator = new ISPYListValidator();
		
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
			           
            //no duplicates
			HashSet h = new HashSet();
			for (int i = 0; i < myUndefinedList.size(); i++)	{
				if(myUndefinedList.get(i)!=null && !myUndefinedList.get(i).equals(""))
					h.add(((String)myUndefinedList.get(i)).trim());
			}
			List cleanList = new ArrayList();			
			cleanList.addAll(h);
			myUndefinedList.clear();
			myUndefinedList = cleanList;
			
			ListManager uploadManager = (ListManager) ListManager.getInstance();            
            UserListN myList = new UserListN();
            UserListBeanHelper helper = new UserListBeanHelper(request.getSession());
            
            String[] tps = CommonListFunctions.parseListType(type);
			String res = "fail";
            ListSubType lst = null;
            
			ISPYListValidator listValidator = new ISPYListValidator();
            try	{
			

			ListType lt = ListType.valueOf(tps[0]);
			if(tps.length > 1 && tps[1] != null){
				//create a list out of [1]
				lst = ListSubType.valueOf(tps[1]);
				res =  CommonListFunctions.createGenericListWithSession(lt, lst, myUndefinedList, name, new ISPYListValidator(ListType.valueOf(tps[0]), ListSubType.valueOf(tps[1]), myUndefinedList), session);
			}
			else if(tps.length >0 && tps[0] != null)	{
				//no subtype, only a primary type - typically a PatientDID then
				res = CommonListFunctions.createGenericListWithSession(lt, null, myUndefinedList, name, new ISPYListValidator(ListType.valueOf(tps[0]), myUndefinedList), session);
			}
			else	{
				//no type or subtype, not good, force to clinical in catch
				throw new Exception();
			}
		
    		}
    		catch(Exception e)	{
    			System.out.println("upload failed");
    			System.out.println(e);
    		} 
/*  
            if (myList != null) {
            	ArrayList subs = new ArrayList();
            	
            	if(tps.length > 1 && tps[1]!=null)	{
            		subs.add(ListSubType.valueOf(tps[1]));
            	}
            	subs.add(ListSubType.Custom);
            	myList.setListSubType(subs);
                //paramMap = uploadManager.getParams(myList);
                helper.addList(myList);
            }
*/

            %>
		<script type="text/javascript">
			var my_params= new Array()

			window.parent.handleResponse(my_params);
		</script>
	</body>
</html>
