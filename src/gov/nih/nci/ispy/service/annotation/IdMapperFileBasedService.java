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
	
	private Map<String, IdMapperEntry> idMap = new HashMap<String, IdMapperEntry>();
	
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
		String headerLine = null;
		IdMapperEntry entry = null;
		try {
			BufferedReader in = new BufferedReader(new FileReader(mappingFileName));
			boolean firstLine=true;
			String[] ids = null;
			while ((line=in.readLine()) != null) {
			  if (firstLine) {
				firstLine = false;
			    headerLine = line;
			   
			    //parse the header line;
			    headers = headerLine.split("\t", -2);
			    
			  }
			  
			  ids = line.split("\t", -2);
			  String id;
			  for (int i=0; i < headers.length; i++) {
			    id = ids[i];
			    
			    if (i==0) {
			      entry = new IdMapperEntry(id, headers.length);
			    }
			    else {
			      entry.addAssociatedId(id);
			    }
			    
			    idMap.put(id, entry);
			  }
			  
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public String[] getHeaders() { return headers; }
	
	public List<IdMapperEntry> getMapperEntriesForIds(List<String> ids) {
		List<IdMapperEntry> retList = new ArrayList<IdMapperEntry>();
		IdMapperEntry entry;
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
	public String[][] getMappingForIds(List<String> ids) {
		String[][] retArr = new String[ids.size()+1][headers.length];
		
		List<IdMapperEntry> entries = getMapperEntriesForIds(ids);
		
		int row =0, col=0;
		
		for (col=0; col < headers.length; col++) {
		  retArr[0][col] = headers[col];
		}
		List<String> associatedIds;
		for (IdMapperEntry entry:entries) {
		  row++;
		  retArr[row][0] = entry.getRegistrationId();
		  associatedIds = entry.getAssociatedIds();
		  col = 1;
		  for (String id:associatedIds) {
		    retArr[row][col++] = id;
		  }
		}
		return retArr;
		
	}

}
