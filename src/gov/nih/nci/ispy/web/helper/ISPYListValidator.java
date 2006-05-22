/**
 * This validator will be unique to ISPY's validation
 * process.
 */
package gov.nih.nci.ispy.web.helper;

import gov.nih.nci.caintegrator.application.lists.ListType;
import gov.nih.nci.caintegrator.application.lists.ListValidator;
import gov.nih.nci.caintegrator.application.service.annotation.ReporterAnnotation;
import gov.nih.nci.ispy.service.annotation.GeneExprFileBasedAnnotationService;
import gov.nih.nci.ispy.service.annotation.IdMapperFileBasedService;
import gov.nih.nci.ispy.service.annotation.RegistrantInfo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author rossok
 * 
 */
public class ISPYListValidator implements ListValidator{
    
    IdMapperFileBasedService idMapper = IdMapperFileBasedService.getInstance();
    GeneExprFileBasedAnnotationService geneService = (GeneExprFileBasedAnnotationService) GeneExprFileBasedAnnotationService.getInstance();
   
    public List<String> getValidList(ListType listType, List<String> myList) {
       List<String> validList = new ArrayList<String>();
       Set<String> geneSymbols = new HashSet<String>();
       Set<ReporterAnnotation> reporters = new HashSet<ReporterAnnotation>();
       Set<RegistrantInfo> riSet = new HashSet<RegistrantInfo>();
       
       if(listType == ListType.PatientDID){            
            List<RegistrantInfo> entries = idMapper.getMapperEntriesForIds(myList);
            riSet.addAll( entries );
            for(RegistrantInfo ri : riSet){
                validList.add(ri.getRegistrationId());
            }
        }
       else if(listType == ListType.DefaultPatientDID){            
           List<RegistrantInfo> entries = idMapper.getMapperEntriesForIds(myList);
           riSet.addAll( entries );
           for(RegistrantInfo ri : riSet){
               validList.add(ri.getRegistrationId());
           }
       }
       else{
           for(String geneSymbol : myList){
               reporters = geneService.getReportersForGeneSymbol(geneSymbol);
               if(reporters!=null){
                   for(ReporterAnnotation a: reporters){
                       geneSymbols.addAll(a.getGeneSymbols());
                   }
               }
           }
       validList.addAll(geneSymbols);
       }
       
       return validList;
    }

    public List<String> getInvalidList(ListType listType, List<String> myList) {
        List<String> invalidList = new ArrayList<String>();
        Set<ReporterAnnotation> reporters = new HashSet<ReporterAnnotation>();
        if(listType == ListType.PatientDID){
             invalidList = idMapper.getInvalidMapperEntriesForIds(myList);
        }
        else if(listType == ListType.DefaultPatientDID){
            invalidList = idMapper.getInvalidMapperEntriesForIds(myList);
       }
        else{
            for(String geneSymbol : myList){
                reporters = geneService.getReportersForGeneSymbol(geneSymbol);
                if(reporters == null){
                    invalidList.add(geneSymbol);
                }
            }
        }
        return invalidList;
    }

}
