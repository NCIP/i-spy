package gov.nih.nci.ispy.test;

import gov.nih.nci.ispy.service.annotation.IdMapperFileBasedService;
import gov.nih.nci.ispy.service.annotation.RegistrantInfo;

import java.util.ArrayList;
import java.util.List;

public class IdMapperFileBasedServiceTester {

	public IdMapperFileBasedServiceTester() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		IdMapperFileBasedService idMapper = IdMapperFileBasedService.getInstance();
		idMapper.setMappingFile("C:\\eclipse\\workspace\\ispyportal\\WebRoot\\WEB-INF\\data_files\\ID_Mapping_4-11-06.txt");
		 
		List<String> inputList = new ArrayList<String>();
		inputList.add("212283");
		inputList.add("1024");
		List<RegistrantInfo> entries = idMapper.getMapperEntriesForIds(inputList);

		for (RegistrantInfo entry:entries) {
		  System.out.println(entry);
		}
		
//		String[][] idArr = idMapper.getMappingForIds(inputList);
//		
//		for (int row=0; row < idArr.length; row++) {
//		  String[] rowData = idArr[row];
//		  for (int col=0; col < rowData.length; col++) {
//		    System.out.print(rowData[col]);
//		    if (col < rowData.length-1) {
//		    	System.out.print("\t");
//		    }
//		    else {
//		    	System.out.println();
//		    }
//		  }
//		}
		
	}

}
