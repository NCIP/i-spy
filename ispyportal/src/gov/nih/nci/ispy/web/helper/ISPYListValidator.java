/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/i-spy/LICENSE.txt for details.
 */

/**
 * This validator will be unique to ISPY's validation
 * process.
 */
package gov.nih.nci.ispy.web.helper;


import gov.columbia.c2b2.ispy.list.ListSubType;
import gov.columbia.c2b2.ispy.list.ListType;
import gov.columbia.c2b2.ispy.list.ListValidator;


import gov.nih.nci.caintegrator.application.service.annotation.GeneExprAnnotationService;
import gov.nih.nci.caintegrator.application.service.annotation.ReporterAnnotation;
import gov.nih.nci.ispy.service.annotation.GeneExprAnnotationServiceFactory;
import gov.nih.nci.ispy.service.annotation.IdMapperFileBasedService;
import gov.nih.nci.ispy.service.annotation.RegistrantInfo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.naming.OperationNotSupportedException;

/**
 * @author rossok
 * 
 */
public class ISPYListValidator extends ListValidator{    
   
    public ISPYListValidator() {
        super();
        // TODO Auto-generated constructor stub
    }

    public ISPYListValidator(ListType listType, List<String> unvalidatedList) throws OperationNotSupportedException {        
        super(listType, unvalidatedList);        
    }
    
    public ISPYListValidator(ListType listType, ListSubType listSubType, List<String> unvalidatedList) throws OperationNotSupportedException {
        super(listType, listSubType, unvalidatedList);             
    }

  
    public void validate(ListType listType, ListSubType listSubType, List<String> myList) {
        List<String> unvalidatedList = new ArrayList<String>();
        for(String s : myList){         
         unvalidatedList.add(s.toUpperCase());
        }
       IdMapperFileBasedService idMapper = IdMapperFileBasedService.getInstance();
       GeneExprAnnotationService geneService = GeneExprAnnotationServiceFactory.getInstance();
       Set<String> geneSymbols = new HashSet<String>();
       Set<ReporterAnnotation> reporters = new HashSet<ReporterAnnotation>();
       Set<RegistrantInfo> riSet = new HashSet<RegistrantInfo>();
       
       if(listType == ListType.PatientDID){            
            List<RegistrantInfo> entries = idMapper.getMapperEntriesForIds(unvalidatedList);
            riSet.addAll( entries );
            for(RegistrantInfo ri : riSet){
                validList.add(ri.getRegistrationId());
            }
        }
     
       else if(listType == ListType.Gene){
           for(String geneSymbol : unvalidatedList){
               reporters = geneService.getReportersForGeneSymbol(geneSymbol);
               if(reporters!=null){
                   for(ReporterAnnotation a: reporters){
                       geneSymbols.add(geneSymbol);
                   }
               }
           }
       validList.addAll(geneSymbols);
       
       }
       invalidList.addAll(unvalidatedList);
       invalidList.removeAll(validList);
    }

    @Override
    public void validate(ListType listType, List<String> unvalidatedList) throws OperationNotSupportedException {
        validate(listType, null, unvalidatedList);
    }

   

}
