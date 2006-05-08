package gov.nih.nci.ispy.web.ajax;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import gov.nih.nci.caintegrator.application.lists.ListType;
import gov.nih.nci.caintegrator.application.lists.UserList;
import gov.nih.nci.ispy.service.annotation.IdMapperFileBasedService;
import gov.nih.nci.ispy.service.annotation.RegistrantInfo;
import gov.nih.nci.ispy.service.annotation.SampleInfo;
import gov.nih.nci.ispy.util.ISPYListManager;
import gov.nih.nci.ispy.web.helper.ISPYUserListBeanHelper;

public class IdLookup {

	public static IdMapperFileBasedService idMapper = null;
	
	public IdLookup() {
		// TODO Auto-generated constructor stub
		idMapper = IdMapperFileBasedService.getInstance();
	}

	public Document lookup(String ids)	{
		
		String results = "";
		//clean the input and validate
		String inputString = ids.trim().replace(" ", "");
		String[] st = StringUtils.split(inputString, ",");
		//construct the inputlist
		List<String> inputList = new ArrayList<String>();
		inputList = Arrays.asList(st);
		
		//pass the input list to the service
		List<RegistrantInfo> entries = idMapper.getMapperEntriesForIds(inputList);
			
		//process the results for return to presentation
		Document document = DocumentHelper.createDocument();
//		Element container = document.addElement("div");
		
		Element report = document.addElement( "table" ).addAttribute("name", inputString);
		for (RegistrantInfo entry:entries) {
			Element reg = report.addElement( "registrant" ).addAttribute("regId", entry.getRegistrationId());
			
			for(SampleInfo sampleInfo : entry.getAssociatedSamples())	{
				Element row = reg.addElement( "sample" );
				Element cell = row.addElement( "regId" );
				
				cell.addText(entry.getRegistrationId());
					cell = null;
				cell = row.addElement( "labtrackId" );
				cell.addText(sampleInfo.getLabtrackId());
					cell = null;
				cell = row.addElement( "timePoint" );
				cell.addText(String.valueOf(sampleInfo.getTimepoint()));
					cell = null;
				cell = row.addElement( "coreType" );
				cell.addText(String.valueOf(sampleInfo.getCoreType()));
					cell = null;
				cell = row.addElement( "sectionInfo" );
				cell.addText(String.valueOf(sampleInfo.getSectionInfo()));
			}
			
		}
		
		for (RegistrantInfo entry:entries) {
			results += "[\"" + entry.getRegistrationId() + "\"," ;
			for(SampleInfo sampleInfo : entry.getAssociatedSamples())	{
				
			}			
		}
		
		return document;
		
	}
	
	public String createPatientList(String[] list, String name){
		/*
		//create list w/ type=patient
		String success = "fail";
		ISPYListManager um = ISPYListManager.getInstance();
		try	{
			UserList ul = um.createList(ListType.PatientDID, name, Arrays.asList(list));
			ISPYUserListBeanHelper ulbh = new ISPYUserListBeanHelper();
			ulbh.addList(ul);
			success = "success";
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		return success;
		*/
		return DynamicListHelper.createPatientList(list, name);
	}
}
