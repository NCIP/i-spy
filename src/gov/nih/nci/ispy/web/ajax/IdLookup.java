/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/i-spy/LICENSE.txt for details.
 */

package gov.nih.nci.ispy.web.ajax;

import gov.nih.nci.ispy.service.annotation.IdMapperFileBasedService;
import gov.nih.nci.ispy.service.annotation.RegistrantInfo;
import gov.nih.nci.ispy.service.annotation.SampleInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.apache.commons.lang.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class IdLookup {

	public static IdMapperFileBasedService idMapper = null;
	
	public IdLookup() {
		// TODO Auto-generated constructor stub
		idMapper = IdMapperFileBasedService.getInstance();
	}

	public String lookup(String ids)	{
		
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
		//Document document = DocumentHelper.createDocument();
		//Element container = document.addElement("div");
		
		JSONArray regs=new JSONArray(); //make an array of registrants
		regs.add(inputString);
	    
		//Element report = document.addElement( "table" ).addAttribute("name", inputString);
		for (RegistrantInfo entry:entries) {
			//Element reg = report.addElement( "registrant" ).addAttribute("regId", entry.getRegistrationId());
			
			//for each registrant make an array of samples
			JSONArray sams=new JSONArray();
			
			for(SampleInfo sampleInfo : entry.getAssociatedSamples())	{
				
				JSONObject sam=new JSONObject();
			    sam.put("regId",entry.getRegistrationId());
			    sam.put("labtrackId",sampleInfo.getLabtrackId());
			    sam.put("timePoint",String.valueOf(sampleInfo.getTimepoint()));
			    sam.put("coreType",String.valueOf(sampleInfo.getCoreType()));
			    sam.put("sectionInfo",String.valueOf(sampleInfo.getSectionInfo()));
			    
			    /*
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
				
				*/
			    
			    sams.add(sam);
			}
			
			regs.add(sams);
		}
		
		/*
		for (RegistrantInfo entry:entries) {
			results += "[\"" + entry.getRegistrationId() + "\"," ;
			for(SampleInfo sampleInfo : entry.getAssociatedSamples())	{
				
			}			
		}
		*/
		
		//return document;
		return regs.toString();
		
	}
	
	public String createPatientList(String[] list, String name){
        String success = "fail";
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
		try {
            success = DynamicListHelper.createPatientList(list, name);
           
        } catch (OperationNotSupportedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return success;
	}
	
	public static String getCSV(String lookup)	{
		String csv = "";
		String inputString = lookup.trim().replace(" ", "");
		String[] st = StringUtils.split(inputString, ",");
		List<String> inputList = new ArrayList<String>();
		inputList = Arrays.asList(st);		
		List<RegistrantInfo> entries = idMapper.getMapperEntriesForIds(inputList);
		
		csv += "RegId,LabTrak ID,Core Type,Timepoint,Section\r\n";
		
		for (RegistrantInfo entry:entries) {
			for(SampleInfo sampleInfo : entry.getAssociatedSamples())	{
				csv += entry.getRegistrationId() + "," 
					+ sampleInfo.getLabtrackId() + "," 
					+ sampleInfo.getCoreType().toString() + ","
					+ sampleInfo.getTimepoint().toString() + ",'"
					+ sampleInfo.getSectionInfo() + "'\r\n";
			}
		}
		return csv;
	}
}
