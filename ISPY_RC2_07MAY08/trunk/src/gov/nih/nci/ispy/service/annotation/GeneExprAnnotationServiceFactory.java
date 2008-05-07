package gov.nih.nci.ispy.service.annotation;

import gov.nih.nci.caintegrator.application.service.annotation.GeneExprAnnotationService;


public class GeneExprAnnotationServiceFactory {

	public GeneExprAnnotationServiceFactory() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public static GeneExprAnnotationService getInstance() {
	  return GeneExprFileBasedAnnotationService.getInstance();
	}

}
