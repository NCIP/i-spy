<%--L
   Copyright SAIC

   Distributed under the OSI-approved BSD 3-Clause License.
   See http://ncip.github.com/i-spy/LICENSE.txt for details.
L--%>

<%@ page language="java" %>
<%@ page import="java.util.*, java.lang.*, java.net.URLEncoder " %>
<%@ page import="java.util.ArrayList, gov.nih.nci.ispy.web.reports.quick.*" %>
<script language="javascript" src="js/Help.js"></script>
<%
	String[] sampleArray = request.getParameterValues("sampleList");
	String taskId = request.getParameter("taskId");
	StringBuffer sb = new StringBuffer();
	
	
	if(sampleArray != null && sampleArray.length > 0)	{
		List samples = Arrays.asList(sampleArray);		
		sb = QuickClinicalReport.quickSampleReport(samples);
	}
	else if(taskId != null){
		sb = QuickClinicalReport.quickSampleReport(request.getSession().getId(), taskId);
	}
	else	{
		sb.append("<br/><br/><b>No Samples Selected.  Please click back, and then select some samples.</b><br/>");
	}

%>
<html>
	<head>
		<link href="xsl/css.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="js/stripeScript.js"></script>
		<script language="JavaScript" type="text/javascript" src="js/prototype_1.5pre.js"></script>
		<script language="JavaScript" type="text/javascript" src="js/event-selectors.js"></script>
		<script type='text/javascript' src='dwr/interface/DynamicListHelper.js'></script>
		<script type='text/javascript' src='dwr/engine.js'></script>
		
		<style>
			.reportTable input, #reportTable input	{
				border: 0px;
			}
			#checkAll { border:0px;}
		</style>
		<script type="text/javascript">
		
		var reportType = "PATIENT";
		var tmpElements = Array();
		
		var Rules = {
	 		  //'.gene,.patient': function(element) {
			  //  if(element.innerHTML != "")	{
			  //	    element.innerHTML += "<input name='checkable' class='saveElement' type='checkbox' value='"+element.id+"'/>";
			//	}					
			//  },
			  '.saveElement:click' : function(element)	{
					if(element.selected || element.checked)
						tmpElements.push(element.value);
					else
						tmpElements.remove(element.value);
			  }
			
	 		};
	 		
	 	var CheckMgr = {
	 		
		 		'checkAll' : function(field)	{
					if(field.length > 1)	{
						for (i = 0; i < field.length; i++)	{
							field[i].checked = true ;
							tmpElements.push(field[i].value);
						}
					}
					else	{
						field.checked = true;
						tmpElements.push(field.value);
					}
				},
				'uncheckAll' : function(field)	{
					if(field)	{
						if(field.length > 1)	{
							for (i = 0; i < field.length; i++)	{
								field[i].checked = false ;	
							}
						}
						else	{
							field.checked = false;
						}
							
						tmpElements = new Array();
						
						if($('checkAll'))		
							$("checkAll").checked = false;
					}
							
				},
				'manageCheckAll' : function(box)	{
						if(box.checked)	{
							CheckMgr.checkAll(document.getElementsByName('checkable'));
						}
						else	{
							CheckMgr.uncheckAll(document.getElementsByName('checkable'));
						}
				}
			};
			
			var SaveElements = {
			
				'save' : function()	{
					//get the name
					$('statusImg').style.display = "";
					$('statusSpan').innerHTML = "Saving...";
					
					var name = $("listName").value;
					if(name != "")	{
						//convert the overlib list to a comma seperated list
						if(tmpElements.length > 0)	{
							
							//var commaSepList = tmpElements.join(",");
							
							if(reportType == "GENE")
								DynamicListHelper.createGeneList(tmpElements, name, SaveElements.save_cb);
							else
								DynamicListHelper.createPatientList(tmpElements, name, SaveElements.save_cb);
								
						}
						else	{
							alert("Please select some items to save");
						}
					}
					else	{
						alert("Please enter a name for your group");
					}
				},
				'save_cb' : function(txt)	{
					var results = txt == "pass" ? "List Saved" : "There was a problem saving your list";
					//alert(results); //pass | fail
					
					setTimeout(function()	{ $('statusImg').style.display = "none"; }, 1000);
					
					
					if(txt != "fail")	{
						setTimeout(function()	{$('statusSpan').innerHTML = "<b>List saved.</b>";},1000);
						//erase the name
						$("listName").value = "";
						//clear the sample list
						tmpElements = new Array();
						
					}
					else	{
						$('statusSpan').innerHTML = "<b>List did not save.  Please try again.</b>";
					}
					
					setTimeout(function()	{ $('statusSpan').innerHTML = ""; }, 2000);
					
					//uncheck them all
					CheckMgr.uncheckAll(document.getElementsByName('checkable'));
					
					
					try	{
						if(!window.opener.closed)	{							
						  window.opener.SidebarHelper.loadSidebar();
						}
							
						
					}
					catch(err)	{
						alert("cant update sidebar: " + err);
					}
					
				}
			};
	 		
	 		
			window.onload = function()	{
				Stripe.stripe('reportTable');
				EventSelectors.start(Rules);
				reportType = "PATIENT";
			};
		
		</script>
		<style>
		
		</style>
	</head>
	<body>
		<%
		String fromGraph = request.getParameter("fromGraph");		
		if(fromGraph!=null && fromGraph.equalsIgnoreCase("true")){
		
		%>
		<div>
		
			<a href="javascript:history.back()">&lt;&lt;&lt;back</a><br/>
			
	
		</div>
		<%}%>
		<div>
		<script type="text/javascript">Help.insertHelp("Clinical_report", " align='left'", "padding:2px;");</script>
		
		Save Selected: <input type="text" id="listName"/>
		<input type="button" value="save checked" onclick="SaveElements.save();"/>
		<input type="checkbox" id="checkAll" onclick="CheckMgr.manageCheckAll(this)"/>All?
		<!--  <a href="#" onclick="alert(tmpElements);return false;" >[selected]</a> -->
		<img src="images/indicator.gif" style="display:none" id="statusImg"/><span id="statusSpan" style="margin-left:15px;"></span>
		</div>
		<%= sb.toString() %>
	</body>
</html>