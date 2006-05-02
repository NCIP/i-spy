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
import gov.nih.nci.ispy.util.ISPYUploadManager;
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
		
		Element report = document.addElement( "table" );
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
			/* return data as
			 * ["regid","T1FS1","T1FS2","T1PS1"], ..
			 * for JS eval as var myData = eval([ data ]);
			 */
			results += "[\"" + entry.getRegistrationId() + "\"," ;
			for(SampleInfo sampleInfo : entry.getAssociatedSamples())	{
				
			}
			
			/*
			var myData = [
			  			["MSFT","Microsoft Corporation", "314,571.156", "32,187.000", "55000"],
			  			["ORCL", "Oracle Corporation", "62,615.266", "9,519.000", "40650"],
			  			["SAP", "SAP AG (ADR)", "40,986.328", "8,296.420", "28961"],
			  			["CA", "Computer Associates Inter", "15,606.335", "3,164.000", "16000"],
			  			["ERTS", "Electronic Arts Inc.", "14,490.895", "2,503.727", "4000"],
			  			["SFTBF", "Softbank Corp. (ADR)", "14,485.840", ".000", "6865"],
			  			["VRTS", "Veritas Software Corp.", "14,444.272", "1,578.658", "5647"],
			  			["SYMC", "Symantec Corporation", "9,932.483", "1,482.029", "4300"],
			  			["INFY", "Infosys Technologies Ltd.", "9,763.851", "830.748", "15400"],
			  			["INTU", "Intuit Inc.", "9,702.477", "1,650.743", "6700"],
			  			["ADBE", "Adobe Systems Incorporate", "9,533.050", "1,230.817", "3341"],
			  			["PSFT", "PeopleSoft, Inc.", "8,246.467", "1,941.167", "8180"],
			  			["SEBL", "Siebel Systems, Inc.", "5,434.649", "1,417.952", "5909"],
			  			["BEAS", "BEA Systems, Inc.", "5,111.813", "965.694", "3063"],
			  			["SNPS", "Synopsys, Inc.", "4,482.535", "1,169.786", "4254"],
			  			["CHKP", "Check Point Software Tech", "4,396.853", "424.769", "1203"],
			  			["MERQ", "Mercury Interactive Corp.", "4,325.488", "444.063", "1822"],
			  			["DOX", "Amdocs Limited", "4,288.017", "1,427.088", "9400"],
			  			["CTXS", "Citrix Systems, Inc.", "3,946.485", "554.222", "1670"],
			  			["KNM", "Konami Corporation (ADR)", "3,710.784", ".000", "4313"]
			  		];
			  		*/
			
		}
		
		return document;
		
	}
	
	public String createPatientList(String[] list, String name){
		//create list w/ type=patient
		String success = "fail";
		ISPYUploadManager um = ISPYUploadManager.getInstance();
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
		
	}
}
