package gov.nih.nci.ispy.service.annotation;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

public class IdMapperFileBasedService {

	private static IdMapperFileBasedService instance = null;
	private static Logger logger = Logger.getLogger(IdMapperFileBasedService.class);
	
	private Map<String, RegistrantInfo> idMap = new HashMap<String, RegistrantInfo>();
	
	private static String[] headers = new String[0];
	
	private IdMapperFileBasedService() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public static IdMapperFileBasedService getInstance() { 
	  if (instance == null) {
	    instance = new IdMapperFileBasedService();
	  }
	  return instance;
	}
	
	public void setMappingFile(String mappingFileName) {
		
		//open and parse the file
		String line = null;
		
		RegistrantInfo entry = null;
		try {
			BufferedReader in = new BufferedReader(new FileReader(mappingFileName));
			
			String[] sampleData = null;
			String registrantId = null;
			while ((line=in.readLine()) != null) {
				  
			  sampleData = line.split("\t", -2);
			  
			  registrantId = sampleData[0];
			  
			  entry = idMap.get(registrantId);
			  
			  if (entry == null) {
			    entry = new RegistrantInfo(registrantId);
			    idMap.put(registrantId, entry);
			  }
			  
			  String labtrackId = sampleData[1];
			  
			  SampleInfo sample = new SampleInfo(labtrackId);  
			  
			  idMap.put(labtrackId, entry);
			  
			  sample.setTimepoint(Integer.parseInt(sampleData[2]));
			  sample.setCoreType(SampleCoreType.valueOf(sampleData[3]));
			  sample.setSectionInfo(sampleData[4]);
			  sample.setAgilentLabtrackId(sampleData[5]);
			  
			  String calgbId = sampleData[6];
			  //idMap.put(calgbId, entry); UNCOMMENT THIS LINE WHEN WE HAVE REAL CALGBIDS
			  
			  sample.setCalgId(calgbId);
			  
			  sample.setCdnaLabtrackId(sampleData[7]);
			  entry.addSample(sample);
			    
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
//	public String[] getHeaders() { return headers; }
	
	public List<RegistrantInfo> getMapperEntriesForIds(List<String> ids) {
		List<RegistrantInfo> retList = new ArrayList<RegistrantInfo>();
		RegistrantInfo entry;
		for (String id:ids) {
		  entry = idMap.get(id);
		  
		  if (entry != null) {
		     retList.add(entry);
		  }
		  else {
		    logger.warn("Could not find id mapper entry for id=" + id);
		  }
		}
		
		return retList;
		
	}
	
	/**
	 * @param ids
	 * @return a 2 dimensional array with the first row containing the column headers. Subsequent rows
	 * contain the id value for each column for a given registrant.
	 */
//	public String[][] getMappingForIds(List<String> ids) {
//		String[][] retArr = new String[ids.size()+1][headers.length];
//		
//		List<RegistrantInfo> entries = getMapperEntriesForIds(ids);
//		
//		int row =0, col=0;
//		
//		for (col=0; col < headers.length; col++) {
//		  retArr[0][col] = headers[col];
//		}
//		List<String> associatedIds;
//		for (RegistrantInfo entry:entries) {
//		  row++;
//		  retArr[row][0] = entry.getRegistrationId();
//		  associatedIds = entry.getAssociatedIds();
//		  col = 1;
//		  for (String id:associatedIds) {
//		    retArr[row][col++] = id;
//		  }
//		}
//		return retArr;
//		
//	}

}
