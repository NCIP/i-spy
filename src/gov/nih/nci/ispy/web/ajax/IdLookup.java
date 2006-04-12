package gov.nih.nci.ispy.web.ajax;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import gov.nih.nci.ispy.service.annotation.IdMapperFileBasedService;
import gov.nih.nci.ispy.service.annotation.RegistrantInfo;
import gov.nih.nci.ispy.service.annotation.SampleInfo;

public class IdLookup {

	public static IdMapperFileBasedService idMapper = null;
	
	public IdLookup() {
		// TODO Auto-generated constructor stub
		idMapper = IdMapperFileBasedService.getInstance();
	}

	public Document lookup(String ids)	{
		
		//String results = "";
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
			
			for(SampleInfo sampleInfo : entry.getAssociatedSamples())	{
				Element row = report.addElement( "sample" );
				Element cell = row.addElement( "regId" );
				
				cell.addText(entry.getRegistrationId());
					cell = null;
				cell = row.addElement( "labtrackId" );
				cell.addText(sampleInfo.getLabtrackId());
					cell = null;
				cell = row.addElement( "timePoint" );
				cell.addText(String.valueOf(sampleInfo.getTimepoint()));
			}
			
		}
		
		return document;
		
	}
}
