package gov.nih.nci.ispy.service.annotation;

import gov.nih.nci.caintegrator.analysis.messaging.IdGroup;
import gov.nih.nci.caintegrator.analysis.messaging.IdList;
import gov.nih.nci.ispy.service.common.TimepointType;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

public class IdMapperFileBasedService {

	private static IdMapperFileBasedService instance = null;
	private static Logger logger = Logger.getLogger(IdMapperFileBasedService.class);
	
	private Map<String, RegistrantInfo> idMap = new HashMap<String, RegistrantInfo>();
	
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
	
	public int setMappingFile(String mappingFileName) {
		
		//open and parse the file
		String line = null;
		int recordsLoaded = 0;
		RegistrantInfo entry = null;
		try {
			BufferedReader in = new BufferedReader(new FileReader(mappingFileName));
			
			String[] sampleData = null;
			String ispyID = null;
			while ((line=in.readLine()) != null) {
				  
			  sampleData = line.split("\t", -2);
			  
			  ispyID = sampleData[0];
			  
			  entry = idMap.get(ispyID);
			  
			  if (entry == null) {
			    entry = new RegistrantInfo(ispyID);
			    idMap.put(ispyID, entry);
			  }
			  
			  String labtrackId = sampleData[1];
			  
			  SampleInfo sample = new SampleInfo(ispyID, labtrackId);  
			  
			  idMap.put(labtrackId, entry);
			  
			  sample.setTimepoint(TimepointType.valueOf(sampleData[2]));
			  
			  
			  if ((sampleData[3]!=null)&&(sampleData[3].trim().length()>0)) {
			    sample.setCoreType(SampleCoreType.valueOf(sampleData[3]));
			  }
			  else {
			    sample.setCoreType(SampleCoreType.NA);
			  }
			  
			  if (sampleData.length>=5 && (sampleData[4]!=null) && (sampleData[4].trim().length() >0)) {
				  sample.setSectionInfo(sampleData[4]);
			  }
			  
			  
			  if (sampleData.length>=6 && (sampleData[5]!=null)&&(sampleData[5].trim().length()>0)) {
			    sample.addDataType(ISPYDataType.AGILENT);
			  }
			  
              
			  if (sampleData.length>=7 &&(sampleData[6]!=null)&&(sampleData[6].trim().length()>0)) {
				sample.addDataType(ISPYDataType.CDNA);
			  }
			  

			  //String calgbId = sampleData[6];
			  //idMap.put(calgbId, entry); UNCOMMENT THIS LINE WHEN WE HAVE REAL CALGBIDS
			  
			  //sample.setCalgId(calgbId);
			  
			  entry.addSample(sample);
			  
			  recordsLoaded++;
			}
			
		} catch (IOException e) {
			logger.error("IOException setting id mapping file=" + mappingFileName + " recordsLoaded=" + recordsLoaded);
			logger.error(e);
			return -recordsLoaded;
		}
		catch (Exception ex2) {
		  logger.error("Caught Exception while loading id mapping file=" + mappingFileName + " recordsLoaded=" + recordsLoaded);
		  logger.error(ex2);
		  return -recordsLoaded;
	    }
		return recordsLoaded;
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public RegistrantInfo getMapperEntryForId(String id) {
		RegistrantInfo entry = idMap.get(id);
		
	    if (entry == null) {
	      logger.warn("Could not find id mapper entry for id=" + id);
	    }
	    return entry;
	}
	
	/**
	 * Return all mapping information for the ids specifed in the id list.
	 * 
	 */
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
     * Return invalid ids specifed in the id list.
     * 
     */
    public List<String> getInvalidMapperEntriesForIds(List<String> ids) {
        List<String> invalidList = new ArrayList<String>();
        RegistrantInfo entry;
        for (String id:ids) {
          entry = idMap.get(id);
          
          if (entry != null) {
            // retList.add(entry);
          }
          else {
            logger.warn("added mapper entry for id=" + id + " to invalidList");
            invalidList.add(id);
          }
        }
        
        return invalidList;
        
    }
	
	/**
	 * Will return the sample info objects corresponding to the
	 * specified labtrack ids.  Note that order is preserved.
	 * @param sampleIds
	 * @return
	 */
	public List<SampleInfo> getSampleInfoForLabtrackIds(List<String> labtrackIds) {
		List<SampleInfo> retList = new ArrayList<SampleInfo>();
		SampleInfo si;
		for (String labtrackId : labtrackIds) {
		  si = getSampleInfoForLabtrackId(labtrackId);
		  retList.add(si);
		}
		return retList;
	}
	
	
	/**
	 * Will return the sample info object corresponding to the specified labtrack id;
	 * @param labtrackId
	 * @return
	 */
	public SampleInfo getSampleInfoForLabtrackId(String labtrackId) {
	  RegistrantInfo ri = null;
	  SampleInfo si = null;
	  
	  ri = idMap.get(labtrackId);
	  
	  if (ri == null) {
	    logger.error("No mapper entry found for labtrackid=" + labtrackId);
	  }
	  else {
		si = ri.getSample(labtrackId);
		if (si == null) {
		    logger.error("No sample info found for labtrackid=" + labtrackId);
		}  
	  }
	  
	  return si;
	}
	
	/**
	 * This method will return samples of a given dataType. 
	 * @param ids the ids to search with. These ids can be any of the ids associated with the registrant. For 
	 * example if registrantId=101 has AgilentSampleId=345 and CDNA_labtrackId=678 then specifing a data type of CDNA and
	 * passing in an id of 101 would  return the information for the CDNA sample 678.  It is also possible to use use the 
	 * AgilentSampleId=345 and dataType=CNDA to get the CDNA sample 678.  This makes it easy to integrate the data because it
	 * makes it easy to answer the question:  What is the sample information for the Agilent data associated with a registrant with a CDNA sample with labtrackId=987.
	 * @param dataType
	 * @return A list of sample ids.
	 */
	public List<SampleInfo> getSamplesForDataType(List<String> ids, ISPYDataType dataType) {
		
	  List<RegistrantInfo> regList = getMapperEntriesForIds(ids);
	  
	  List<SampleInfo> retList = new ArrayList<SampleInfo>();
 	  
	  for (RegistrantInfo reg:regList) {
		List<SampleInfo> sampleList = reg.getAssociatedSamples();
		
		for (SampleInfo sample: sampleList) {
		  if (sample.getDataTypes().contains(dataType)) {
		    retList.add(sample);
		  }
		}
	  }
	  
	  return retList;
	}
	
	/**
	 * This method will return samples of a given dataType for a given timepoint.  The most common use of this method
	 * will be to pass in a list of registrantIds a dataType and a timepoint. This method would return a list of samples
	 * of the specified dataType for the specified timepoint.
	 * @param ids
	 * @param dataType
	 * @param timepoint
	 * @return
	 */
	public List<SampleInfo> getSamplesForDataTypeAndTimepoint(List<String> ids, ISPYDataType dataType, TimepointType timepoint) {
	  	
	  List<SampleInfo> sampleList =  getSamplesForDataType(ids, dataType);
	  List<SampleInfo> retList = new ArrayList<SampleInfo>(); 	
	  for (SampleInfo sample: sampleList) {
	    if (sample.getTimepoint() == timepoint) {
	      retList.add(sample);
	    }
	  }
	  
	  return retList;
	}
	
	/**
	 * This method will return samples of a given dataType for a given timepoint.  The most common use of this method
	 * will be to pass in a list of registrantIds a dataType and a timepoint. This method would return a list of samples
	 * of the specified dataType for the specified timepoint.
	 * @param ids
	 * @param dataType
	 * @param timepoint
	 * @return
	 */
	public Set<SampleInfo> getSamplesForDataTypeAndTimepoints(List<String> ids, ISPYDataType dataType, Set<TimepointType> timepoints) {
	  	
	  List<SampleInfo> sampleList =  getSamplesForDataType(ids, dataType);
	  Set<SampleInfo> retSet = new HashSet<SampleInfo>(); 	
	  for (SampleInfo sample: sampleList) {
	    if (timepoints.contains(sample.getTimepoint())) {
	      retSet.add(sample);
	    }
	  }
	  
	  return retSet;
	}
	
	public Set<SampleInfo> getSamplesForDataTypeAndTimepoints(ISPYDataType dataType, Set<TimepointType> timepoints) {
	 
	  Set<SampleInfo> retSet = new HashSet<SampleInfo>(); 	
	  
	  for (RegistrantInfo ri : idMap.values()) {
	    retSet.addAll(ri.getSamplesForDataTypeAndTimepoints(dataType, timepoints)); 
	  }
	  return retSet;
	}
	
	/**
	 * Go though all of the ISPY samples and return those of the specified dataType and timepoint
	 * @param dataType
	 * @param timepoint
	 * @return
	 */
	public List<SampleInfo> getSamplesForDataTypeAndTimepoint(ISPYDataType dataType, TimepointType timepoint) {
	    List<SampleInfo> retList = new ArrayList<SampleInfo>();
	    List<SampleInfo> regSampleList;
		for (RegistrantInfo registrant: idMap.values()) {
		  regSampleList = registrant.getSamplesForDataTypeAndTimepoint(dataType, timepoint);
		  if (!regSampleList.isEmpty()) {
		    retList.addAll(regSampleList);
		  }
		}
		return retList;
	}

	/**
	 * Get a list of all ISPY ids.
	 * @return
	 */
	public IdGroup getAllISPYIds() {
	  IdGroup allISPYIds = new IdGroup("All_ISPY_IDs");
	  
	  for (RegistrantInfo info : idMap.values()) {
	    allISPYIds.add(info.getRegistrationId());
	  }
	  return allISPYIds;
	}
}
