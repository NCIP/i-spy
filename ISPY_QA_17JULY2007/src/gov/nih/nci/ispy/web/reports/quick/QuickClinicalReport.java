package gov.nih.nci.ispy.web.reports.quick;

import gov.nih.nci.caintegrator.application.bean.FindingReportBean;
import gov.nih.nci.caintegrator.application.cache.BusinessTierCache;
import gov.nih.nci.caintegrator.enumeration.FindingStatus;
import gov.nih.nci.caintegrator.service.findings.Finding;
import gov.nih.nci.ispy.dto.query.ISPYclinicalDataQueryDTO;
import gov.nih.nci.ispy.service.annotation.IdMapperFileBasedService;
import gov.nih.nci.ispy.service.annotation.SampleInfo;
import gov.nih.nci.ispy.service.clinical.AgeCategoryType;
import gov.nih.nci.ispy.service.clinical.ClinicalDataService;
import gov.nih.nci.ispy.service.clinical.ClinicalDataServiceFactory;
import gov.nih.nci.ispy.service.clinical.MorphologyType;
import gov.nih.nci.ispy.service.clinical.PatientData;
import gov.nih.nci.ispy.service.clinical.RaceType;
import gov.nih.nci.ispy.service.findings.ISPYClinicalFinding;
import gov.nih.nci.ispy.service.imaging.ImagingFileBasedQueryService;
import gov.nih.nci.ispy.web.factory.ApplicationFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class QuickClinicalReport {
	public static StringBuffer quickSampleReport(String sessionId, String taskId){
        
        BusinessTierCache cacheManager = ApplicationFactory.getBusinessTierCache();        
        ISPYClinicalFinding clinicalFinding = (ISPYClinicalFinding) cacheManager.getObjectFromSessionCache(sessionId, taskId);
        StringBuffer html = new StringBuffer();
        List<PatientData> patientsData = clinicalFinding.getPatientData();
        if(!patientsData.isEmpty()){  
                ImagingFileBasedQueryService iqs = (ImagingFileBasedQueryService) ImagingFileBasedQueryService.getInstance();
                Document document = DocumentHelper.createDocument();
                Element table = document.addElement("table").addAttribute("id", "reportTable").addAttribute("class", "report");
                Element tr = null;
                Element td = null;
                tr = table.addElement("tr").addAttribute("class", "header");
                
                String longHeaders = "ISPY_ID, NCIA_IMAGE, INST_ID, AGE, AGECAT, RACE_ID, SSTAT, SURVDTD, CHEMOCAT, CHEMO, TAM, HERCEPTIN, DOSEDENSEANTHRA, DOSEDENSETAXANE, MENOSTATUS, SENTINELNODESAMPLE, SENTINELNODERESULT, HIST_TYPE_INV_OS, HISTOLOGICGRADEOS, ER_TS, PGR_TS, HER2COMMUNITYPOS, HER2COMMUNITYMETHOD, SURGERYLUMPECTOMY, SURGERYMASTECTOMY, HISTOLOGICTYPEPS, INITLUMP_FUPMAST, SURGERY, DCISONLY, PTUMOR1SZCM_MICRO, HISTOLOGICGRADEPS, NUMPOSNODES, NODESEXAMINED, PATHOLOGYSTAGE, REASON_NO_SURG, RTTHERAPY, RTBREAST, RTBOOST, RTAXILLA, RTSNODE, RTIMAMNODE, RTCHESTW, RTOTHER, TSIZECLINICAL, NSIZECLINICAL, STAGETE, STAGENE, STAGEME, CLINICALSTAGE, CLINRESPT1_T2, CLINRESPT1_T3, CLINRESPT1_T4, Morphologic pattern at T1, LES_T1, LES_T2, LES_T3, LES_T4, LD_T1, LD_T2, LD_T3, LD_T4, MRI % change T1_T2, MRI % change T1_T3, MRI % change T1_T4," +
                "MRI % change T2_T3, MRI % change T2_T4, MRI % change T3_T4";

                String[] heads = StringUtils.split(longHeaders, ",");
                for(String h : heads){
                    td = tr.addElement("td").addAttribute("class", "header").addText(h.trim());
                }
                for(PatientData pd: patientsData){
                    tr = table.addElement("tr").addAttribute("class", "data");            
                    String tmp = ""; 
                    String dv = "-";
                    if(pd == null){
                        for(int i=0; i<heads.length-2;i++){
                            td = tr.addElement("td").addAttribute("class", "header").addText(dv);
                        }
                    }
                    else if(pd!=null){
                        tmp = pd.getISPY_ID()!=null  ? pd.getISPY_ID() : dv;
                        td = tr.addElement("td").addText(tmp).addAttribute("name", "patient").addAttribute("class", "patient").addAttribute("id",tmp).addElement("input").addAttribute("type","checkbox").addAttribute("name","checkable").addAttribute("class","saveElement").addAttribute("value",pd.getISPY_ID());
                        
//                        tmp = pd.getDataExtractDT()!=null  ? pd.getDataExtractDT() : dv;
//                        td = tr.addElement("td").addText(tmp);
                        
                        if(iqs.hasImagingData(pd.getISPY_ID())){
                            String link = iqs.buildImagingLink(pd);
                            try {
                                String encodedLink = URLEncoder.encode(link, "UTF-8");
                                td = tr.addElement("td").addElement("a").addAttribute("onclick","javascript:window.open('" + link + "','new','toolbar=yes,width=800,height=600,location=yes,status=yes,menubar=yes,scrollbars=yes,resizable=yes');").addElement("img").addAttribute("src","images/nciaLink.png");
                            } catch (UnsupportedEncodingException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            
                        }
                        else{
                            td = tr.addElement("td").addText(dv);
                        }
        
                        tmp = pd.getInst_ID()!=null  ? pd.getInst_ID() : dv;
                        td = tr.addElement("td").addText(tmp);
                        
                        tmp = pd.getPatientAge()!=null  ? pd.getPatientAge().toString() : dv;
                        td = tr.addElement("td").addText(tmp);
                        
                        tmp = pd.getAgeCategory()!=null  ? pd.getAgeCategory().toString() : dv;
                        td = tr.addElement("td").addText(tmp);
                        
                        tmp = pd.getRace()!=null  ? pd.getRace().toString() : dv;
                        td = tr.addElement("td").addText(tmp);
                        
                        tmp = pd.getSSTAT()!=null  ? pd.getSSTAT() : dv;
                        td = tr.addElement("td").addText(tmp);
                        
                        tmp = pd.getSURVDTD()!=null  ? pd.getSURVDTD() : dv;
                        td = tr.addElement("td").addText(tmp);
                        
                        tmp = pd.getChemoCat()!=null ? pd.getChemoCat() : dv;
                        td = tr.addElement("td").addText(tmp);
                        
                        tmp = pd.getChemo()!=null  ? pd.getChemo() : dv;
                        td = tr.addElement("td").addText(tmp);
                        
                        tmp = pd.getTAM()!=null  ? pd.getTAM() : dv;
                        td = tr.addElement("td").addText(tmp);
                        
                        tmp = pd.getHerceptin()!=null  ? pd.getHerceptin() : dv;
                        td = tr.addElement("td").addText(tmp);
                        
                        tmp = pd.getDosedenseanthra()!=null  ? pd.getDosedenseanthra() : dv;
                        td = tr.addElement("td").addText(tmp);
                        
                        tmp = pd.getDosedensetaxane()!=null  ? pd.getDosedensetaxane() : dv;
                        td = tr.addElement("td").addText(tmp);
                        
                        tmp = pd.getMenoStatus()!=null  ? pd.getMenoStatus() : dv;
                        td = tr.addElement("td").addText(tmp);
                        
                        tmp = pd.getSentinelNodeSample()!=null  ? pd.getSentinelNodeSample() : dv;
                        td = tr.addElement("td").addText(tmp);
                        
                        tmp = pd.getSentinelNodeResult()!=null  ? pd.getSentinelNodeResult() : dv;
                        td = tr.addElement("td").addText(tmp);
                        
                        tmp = pd.getHistTypeOfInvasiveTumor()!=null  ? pd.getHistTypeOfInvasiveTumor() : dv;
                        td = tr.addElement("td").addText(tmp);
                        
                        tmp = pd.getHistologicGradeOS()!=null  ? pd.getHistologicGradeOS() : dv;
                        td = tr.addElement("td").addText(tmp);
                        
                        tmp = pd.getER_TS()!=null  ? pd.getER_TS() : dv;
                        td = tr.addElement("td").addText(tmp);
                        
                        tmp = pd.getPGR_TS()!=null  ? pd.getPGR_TS() : dv;
                        td = tr.addElement("td").addText(tmp);
                        
                        tmp = pd.getHER2CommunityPOS()!=null  ? pd.getHER2CommunityPOS() : dv;
                        td = tr.addElement("td").addText(tmp);
                        
                        tmp = pd.getHER2CommunityMethod()!=null  ? pd.getHER2CommunityMethod() : dv;
                        td = tr.addElement("td").addText(tmp);
                        
                        tmp = pd.getSurgeryLumpectomy()!=null  ? pd.getSurgeryLumpectomy() : dv;
                        td = tr.addElement("td").addText(tmp);
                        
                        tmp = pd.getSurgeryMastectomy()!=null  ? pd.getSurgeryMastectomy() : dv;
                        td = tr.addElement("td").addText(tmp);
                        
                        tmp = pd.getHistTypePS()!=null  ? pd.getHistTypePS() : dv;
                        td = tr.addElement("td").addText(tmp);
                        
                        tmp = pd.getINITLUMP_FUPMAST()!=null  ? pd.getINITLUMP_FUPMAST() : dv;
                        td = tr.addElement("td").addText(tmp);
                        
                        tmp = pd.getSurgery()!=null  ? pd.getSurgery() : dv;
                        td = tr.addElement("td").addText(tmp);
                        
                        tmp = pd.getDCISOnly()!=null  ? pd.getDCISOnly() : dv;
                        td = tr.addElement("td").addText(tmp);
                        
                        tmp = pd.getPTumor1SZCM_Micro()!=null  ? pd.getPTumor1SZCM_Micro().toString() : dv;
                        td = tr.addElement("td").addText(tmp);
                        
                        tmp = pd.getHistologicGradePS()!=null  ? pd.getHistologicGradePS() : dv;
                        td = tr.addElement("td").addText(tmp);
                        
                        tmp = pd.getNumPosNodes()!=null  ? pd.getNumPosNodes() : dv;
                        td = tr.addElement("td").addText(tmp);
                        
                        tmp = pd.getNodesExamined()!=null  ? pd.getNodesExamined() : dv;
                        td = tr.addElement("td").addText(tmp);
                        
                        tmp = pd.getPathologyStage()!=null  ? pd.getPathologyStage() : dv;
                        td = tr.addElement("td").addText(tmp);
                        
                        tmp = pd.getReasonNoSurg()!=null  ? pd.getReasonNoSurg() : dv;
                        td = tr.addElement("td").addText(tmp);
                        
                        tmp = pd.getRTTherapy()!=null  ? pd.getRTTherapy() : dv;
                        td = tr.addElement("td").addText(tmp);
                        
                        tmp = pd.getRTBreast()!=null  ? pd.getRTBreast() : dv;
                        td = tr.addElement("td").addText(tmp);
                        
                        tmp = pd.getRTBOOST()!=null  ? pd.getRTBOOST() : dv;
                        td = tr.addElement("td").addText(tmp);
                        
                        tmp = pd.getRTAXILLA()!=null  ? pd.getRTAXILLA() : dv;
                        td = tr.addElement("td").addText(tmp);
                        
                        tmp = pd.getRTSNODE()!=null  ? pd.getRTSNODE() : dv;
                        td = tr.addElement("td").addText(tmp);
                        
                        tmp = pd.getRTIMAMNODE()!=null  ? pd.getRTIMAMNODE() : dv;
                        td = tr.addElement("td").addText(tmp);
                        
                        tmp = pd.getRTChestW()!=null  ? pd.getRTChestW() : dv;
                        td = tr.addElement("td").addText(tmp);
                        
                        tmp = pd.getRTOTHER()!=null  ? pd.getRTOTHER() : dv;
                        td = tr.addElement("td").addText(tmp);
                        
                        tmp = pd.getTSizeClinical()!=null  ? pd.getTSizeClinical() : dv;
                        td = tr.addElement("td").addText(tmp);
                        
                        tmp = pd.getNSizeClinical()!=null  ? pd.getNSizeClinical() : dv;
                        td = tr.addElement("td").addText(tmp);
                        
                        tmp = pd.getStageTE()!=null  ? pd.getStageTE() : dv;
                        td = tr.addElement("td").addText(tmp);
                        
                        tmp = pd.getStageNE()!=null  ? pd.getStageNE() : dv;
                        td = tr.addElement("td").addText(tmp);
                        
                        tmp = pd.getSTAGEME()!=null  ? pd.getSTAGEME() : dv;
                        td = tr.addElement("td").addText(tmp);
                        
                        tmp = pd.getClinicalStageStr()!=null  ? pd.getClinicalStageStr() : dv;
                        td = tr.addElement("td").addText(tmp);
                        
                        tmp = pd.getClinRespT1_T2()!=null  ? pd.getClinRespT1_T2() : dv;
                        td = tr.addElement("td").addText(tmp);
                        
                        tmp = pd.getClinRespT1_T3()!=null  ? pd.getClinRespT1_T3() : dv;
                        td = tr.addElement("td").addText(tmp);
                        
                        tmp = pd.getClinRespT1_T4()!=null  ? pd.getClinRespT1_T4() : dv;
                        td = tr.addElement("td").addText(tmp);
                        
                        tmp = pd.getMorphology()!=null  ? pd.getMorphology().toString() : dv;
                        td = tr.addElement("td").addText(tmp);         
                                              
                        tmp = pd.getLES_T1()!=null  ? pd.getLES_T1() : dv;
                        td = tr.addElement("td").addText(tmp);
                        
                        tmp = pd.getLES_T2()!=null  ? pd.getLES_T2() : dv;
                        td = tr.addElement("td").addText(tmp);
                        
                        tmp = pd.getLES_T3()!=null  ? pd.getLES_T3() : dv;
                        td = tr.addElement("td").addText(tmp);
                        
                        tmp = pd.getLES_T4()!=null  ? pd.getLES_T4() : dv;
                        td = tr.addElement("td").addText(tmp);
                        
                        tmp = pd.getLdT1()!=null  ? Double.toString(pd.getLdT1()) : dv;
                        td = tr.addElement("td").addText(tmp);
                        
                        tmp = pd.getLdT2()!=null  ? Double.toString(pd.getLdT2()) : dv;
                        td = tr.addElement("td").addText(tmp);
                        
                        tmp = pd.getLdT3()!=null  ? Double.toString(pd.getLdT3()) : dv;
                        td = tr.addElement("td").addText(tmp);
                        
                        tmp = pd.getLdT4()!=null  ? Double.toString(pd.getLdT4()) : dv;
                        td = tr.addElement("td").addText(tmp);
                        
                        tmp = pd.getMriPctChangeT1_T2()!=null  ? String.valueOf(pd.getMriPctChangeT1_T2()) : dv;
                        td = tr.addElement("td").addText(tmp);
                        
                        tmp = pd.getMriPctChangeT1_T3()!=null  ? String.valueOf(pd.getMriPctChangeT1_T3()) : dv;
                        td = tr.addElement("td").addText(tmp);
                                                                        
                        tmp = pd.getMriPctChangeT1_T4()!=null  ? String.valueOf(pd.getMriPctChangeT1_T4()) : dv;
                        td = tr.addElement("td").addText(tmp);
                        
                        tmp = pd.getMriPctChangeT2_T3()!=null  ? Double.toString(pd.getMriPctChangeT2_T3()) : dv;
                        td = tr.addElement("td").addText(tmp);
                        
                        tmp = pd.getMriPctChangeT2_T4()!=null  ? Double.toString(pd.getMriPctChangeT2_T4()) : dv;
                        td = tr.addElement("td").addText(tmp);
                        
                        tmp = pd.getMriPctChangeT3_T4()!=null  ? Double.toString(pd.getMriPctChangeT3_T4()) : dv;
                        td = tr.addElement("td").addText(tmp);                        
                        
                    }
                }  
            html.append(document.asXML()); 
        }
        else{
            html.append("No Data for selected clinical parameters");
        }
      return html;         
    }
	
	public static StringBuffer quickSampleReport(List<String> sampleIds,String sessionId, String taskId){
		Set set = new HashSet();
		set.addAll(sampleIds);
		 
		if(set.size() < sampleIds.size()) {
			sampleIds.clear();
			sampleIds.addAll(set);
		}

		String dv = "-";
		StringBuffer html = new StringBuffer();
		Document document = DocumentHelper.createDocument();
		
		if(sampleIds != null)	{
			
			try {
		
				IdMapperFileBasedService idMapper = IdMapperFileBasedService.getInstance();
				ImagingFileBasedQueryService iqs = (ImagingFileBasedQueryService) ImagingFileBasedQueryService.getInstance();
                
				//List<RegistrantInfo> registrants = 
				List<SampleInfo> samples = idMapper.getSampleInfoForLabtrackIds(sampleIds);
				//List<ClinicalData> clinicalDataList = new ArrayList<ClinicalData>();
				ClinicalDataService cqs = ClinicalDataServiceFactory.getInstance();
				
                List<PatientData> pdList = new ArrayList<PatientData>();
                
                
				if(samples.isEmpty())	{
					html.append("No Data");
					return html;
				}
				
				//WHEN switching to the new PatientData method of getting data
				//remove the for loop below
				
				/*
				for (SampleInfo si : samples) {
				  clinicalDataList.add(cqs.getClinicalDataForPatientDID(si.getISPYId(), si.getTimepoint()));
				}
				*/

				
				
				
				if(sampleIds != null){
					int count = 0;

					Element table = document.addElement("table").addAttribute("id", "reportTable").addAttribute("class", "report");
					Element tr = null;
					Element td = null;
					tr = table.addElement("tr").addAttribute("class", "header");
					
                    //String longHeaders = "ISPY_ID, LabTrak ID, NCIA_IMAGE, INST_ID, AGE, AGECAT, RACE_ID, SSTAT, SURVDTD, CHEMOCAT, CHEMO, TAM, HERCEPTIN, MENOSTATUS, SENTINELNODESAMPLE, SENTINELNODERESULT, HIST_TYPE_INV_OS, HISTOLOGICGRADEOS, ER_TS, PGR_TS, HER2COMMUNITYPOS, HER2COMMUNITYMETHOD, SURGERYLUMPECTOMY, SURGERYMASTECTOMY, HISTOLOGICTYPEPS, INITLUMP_FUPMAST, SURGERY, DCISONLY, PTUMOR1SZCM_MICRO, HISTOLOGICGRADEPS, NUMPOSNODES, NODESEXAMINED, PATHOLOGYSTAGE, REASON_NO_SURG, RTTHERAPY, RTBREAST, RTBOOST, RTAXILLA, RTSNODE, RTIMAMNODE, RTCHESTW, RTOTHER, TSIZECLINICAL, NSIZECLINICAL, STAGETE, STAGENE, STAGEME, CLINICALSTAGE, CLINRESPT1_T2, CLINRESPT1_T3, CLINRESPT1_T4, Morphologic pattern at T1, LES_T1, LES_T2, LES_T3, LES_T4, LD_T1, LD_T2, LD_T3, LD_T4, MRI % change T1_T2, MRI % change T1_T3, MRI % change T1_T4," +
                //"MRI % change T2_T3, MRI % change T2_T4, MRI % change T3_T4";
                    
                    String longHeaders = "ISPY_ID, LabTrak ID, NCIA_IMAGE, INST_ID, AGE, AGECAT, RACE_ID, SSTAT, SURVDTD, CHEMOCAT, CHEMO, TAM, HERCEPTIN, DOSEDENSEANTHRA, DOSEDENSETAXANE, MENOSTATUS, SENTINELNODESAMPLE, SENTINELNODERESULT, HIST_TYPE_INV_OS, HISTOLOGICGRADEOS, ER_TS, PGR_TS, HER2COMMUNITYPOS, HER2COMMUNITYMETHOD, SURGERYLUMPECTOMY, SURGERYMASTECTOMY, HISTOLOGICTYPEPS, INITLUMP_FUPMAST, SURGERY, DCISONLY, PTUMOR1SZCM_MICRO, HISTOLOGICGRADEPS, NUMPOSNODES, NODESEXAMINED, PATHOLOGYSTAGE, REASON_NO_SURG, RTTHERAPY, RTBREAST, RTBOOST, RTAXILLA, RTSNODE, RTIMAMNODE, RTCHESTW, RTOTHER, TSIZECLINICAL, NSIZECLINICAL, STAGETE, STAGENE, STAGEME, CLINICALSTAGE, CLINRESPT1_T2, CLINRESPT1_T3, CLINRESPT1_T4, Morphologic pattern at T1, LES_T1, LES_T2, LES_T3, LES_T4, LD_T1, LD_T2, LD_T3, LD_T4, MRI % change T1_T2, MRI % change T1_T3, MRI % change T1_T4," +
                    "MRI % change T2_T3, MRI % change T2_T4, MRI % change T3_T4";
                    
                    String[] heads = StringUtils.split(longHeaders, ",");
					for(String h : heads){
						td = tr.addElement("td").addAttribute("class", "header").addText(h.trim());
					}
					
					for(SampleInfo si : samples)	{

						if(si!=null)	{
							
							//PatientData pd = cqs.getPatientDataForPatientDID(si.getISPYId());
                            Set<String> ispyIds = new HashSet<String>();
                            ispyIds.add(si.getISPYId());
                            ISPYclinicalDataQueryDTO dto = new ISPYclinicalDataQueryDTO();
                            dto.setRestrainingSamples(ispyIds);
                            dto.setReturnAll(false);
                            Set<PatientData> pdSet = cqs.getClinicalData(dto);
                            
                            
                         
                            for(PatientData pd:pdSet){   
                                pdList.add(pd);
    							tr = table.addElement("tr").addAttribute("class", "data");
    							
    							String tmp = "";
    							
    							tmp = si.getISPYId()!=null  ? si.getISPYId() : dv;                                                                    
    							td = tr.addElement("td").addText(tmp).addAttribute("name", "patient").addAttribute("class", "patient").addAttribute("id",tmp).addElement("input").addAttribute("type","checkbox").addAttribute("name","checkable").addAttribute("class","saveElement").addAttribute("value",pd.getISPY_ID());
    							
    							tmp = si.getLabtrackId()!=null  ? si.getLabtrackId() : dv;
    							td = tr.addElement("td").addText(tmp);
    							
    							if(pd == null){
    								for(int i=0; i<heads.length-2;i++){
    									td = tr.addElement("td").addAttribute("class", "header").addText(dv);
    								}
    							}
    							else if(pd!=null){
//    								tmp = pd.getDataExtractDT()!=null  ? pd.getDataExtractDT() : dv;
//    								td = tr.addElement("td").addText(tmp);
                                    
                                    if(iqs.hasImagingData(pd.getISPY_ID())){
                                        String link = iqs.buildImagingLink(pd);
                                        try {
                                            String encodedLink = URLEncoder.encode(link, "UTF-8");
                                            td = tr.addElement("td").addElement("a").addAttribute("onclick","javascript:window.open('" + link + "','new','toolbar=yes,width=800,height=600,location=yes,status=yes,menubar=yes,scrollbars=yes,resizable=yes');").addElement("img").addAttribute("src","images/nciaLink.png");
                                        } catch (UnsupportedEncodingException e) {
                                            // TODO Auto-generated catch block
                                            e.printStackTrace();
                                        }
                                    }
                                    else{
                                        td = tr.addElement("td").addText(dv);
                                    }
    	
    								tmp = pd.getInst_ID()!=null  ? pd.getInst_ID() : dv;
    								td = tr.addElement("td").addText(tmp);
    								
                   
    								tmp = pd.getPatientAge()!=null  ? pd.getPatientAge().toString() : dv;
    		                        td = tr.addElement("td").addText(tmp);
    		                        
    		                        tmp = pd.getAgeCategory()!=null  ? pd.getAgeCategory().toString() : dv;
    		                        td = tr.addElement("td").addText(tmp);
    		                        
    		                        tmp = pd.getRace()!=null  ? pd.getRace().toString() : dv;
    		                        td = tr.addElement("td").addText(tmp);
    		                        
    		                        tmp = pd.getSSTAT()!=null  ? pd.getSSTAT() : dv;
    		                        td = tr.addElement("td").addText(tmp);
    		                        
    		                        tmp = pd.getSURVDTD()!=null  ? pd.getSURVDTD() : dv;
    		                        td = tr.addElement("td").addText(tmp);
    		                        
    		                        tmp = pd.getChemoCat()!=null ? pd.getChemoCat() : dv;
    		                        td = tr.addElement("td").addText(tmp);
    		                        
    		                        tmp = pd.getChemo()!=null  ? pd.getChemo() : dv;
    		                        td = tr.addElement("td").addText(tmp);
    		                        
    		                        tmp = pd.getTAM()!=null  ? pd.getTAM() : dv;
    		                        td = tr.addElement("td").addText(tmp);
    		                        
    		                        tmp = pd.getHerceptin()!=null  ? pd.getHerceptin() : dv;
    		                        td = tr.addElement("td").addText(tmp);
    		                        
    		                        tmp = pd.getDosedenseanthra()!=null  ? pd.getDosedenseanthra() : dv;
    		                        td = tr.addElement("td").addText(tmp);
    		                        
    		                        tmp = pd.getDosedensetaxane()!=null  ? pd.getDosedensetaxane() : dv;
    		                        td = tr.addElement("td").addText(tmp);
    		                        
    		                        tmp = pd.getMenoStatus()!=null  ? pd.getMenoStatus() : dv;
    		                        td = tr.addElement("td").addText(tmp);
    		                        
    		                        tmp = pd.getSentinelNodeSample()!=null  ? pd.getSentinelNodeSample() : dv;
    		                        td = tr.addElement("td").addText(tmp);
    		                        
    		                        tmp = pd.getSentinelNodeResult()!=null  ? pd.getSentinelNodeResult() : dv;
    		                        td = tr.addElement("td").addText(tmp);
    		                        
    		                        tmp = pd.getHistTypeOfInvasiveTumor()!=null  ? pd.getHistTypeOfInvasiveTumor() : dv;
    		                        td = tr.addElement("td").addText(tmp);
    		                        
    		                        tmp = pd.getHistologicGradeOS()!=null  ? pd.getHistologicGradeOS() : dv;
    		                        td = tr.addElement("td").addText(tmp);
    		                        
    		                        tmp = pd.getER_TS()!=null  ? pd.getER_TS() : dv;
    		                        td = tr.addElement("td").addText(tmp);
    		                        
    		                        tmp = pd.getPGR_TS()!=null  ? pd.getPGR_TS() : dv;
    		                        td = tr.addElement("td").addText(tmp);
    		                        
    		                        tmp = pd.getHER2CommunityPOS()!=null  ? pd.getHER2CommunityPOS() : dv;
    		                        td = tr.addElement("td").addText(tmp);
    		                        
    		                        tmp = pd.getHER2CommunityMethod()!=null  ? pd.getHER2CommunityMethod() : dv;
    		                        td = tr.addElement("td").addText(tmp);
    		                        
    		                        tmp = pd.getSurgeryLumpectomy()!=null  ? pd.getSurgeryLumpectomy() : dv;
    		                        td = tr.addElement("td").addText(tmp);
    		                        
    		                        tmp = pd.getSurgeryMastectomy()!=null  ? pd.getSurgeryMastectomy() : dv;
    		                        td = tr.addElement("td").addText(tmp);
    		                        
    		                        tmp = pd.getHistTypePS()!=null  ? pd.getHistTypePS() : dv;
    		                        td = tr.addElement("td").addText(tmp);
    		                        
    		                        tmp = pd.getINITLUMP_FUPMAST()!=null  ? pd.getINITLUMP_FUPMAST() : dv;
    		                        td = tr.addElement("td").addText(tmp);
    		                        
    		                        tmp = pd.getSurgery()!=null  ? pd.getSurgery() : dv;
    		                        td = tr.addElement("td").addText(tmp);
    		                        
    		                        tmp = pd.getDCISOnly()!=null  ? pd.getDCISOnly() : dv;
    		                        td = tr.addElement("td").addText(tmp);
    		                        
    		                        tmp = pd.getPTumor1SZCM_Micro()!=null  ? pd.getPTumor1SZCM_Micro().toString() : dv;
    		                        td = tr.addElement("td").addText(tmp);
    		                        
    		                        tmp = pd.getHistologicGradePS()!=null  ? pd.getHistologicGradePS() : dv;
    		                        td = tr.addElement("td").addText(tmp);
    		                        
    		                        tmp = pd.getNumPosNodes()!=null  ? pd.getNumPosNodes() : dv;
    		                        td = tr.addElement("td").addText(tmp);
    		                        
    		                        tmp = pd.getNodesExamined()!=null  ? pd.getNodesExamined() : dv;
    		                        td = tr.addElement("td").addText(tmp);
    		                        
    		                        tmp = pd.getPathologyStage()!=null  ? pd.getPathologyStage() : dv;
    		                        td = tr.addElement("td").addText(tmp);
    		                        
    		                        tmp = pd.getReasonNoSurg()!=null  ? pd.getReasonNoSurg() : dv;
    		                        td = tr.addElement("td").addText(tmp);
    		                        
    		                        tmp = pd.getRTTherapy()!=null  ? pd.getRTTherapy() : dv;
    		                        td = tr.addElement("td").addText(tmp);
    		                        
    		                        tmp = pd.getRTBreast()!=null  ? pd.getRTBreast() : dv;
    		                        td = tr.addElement("td").addText(tmp);
    		                        
    		                        tmp = pd.getRTBOOST()!=null  ? pd.getRTBOOST() : dv;
    		                        td = tr.addElement("td").addText(tmp);
    		                        
    		                        tmp = pd.getRTAXILLA()!=null  ? pd.getRTAXILLA() : dv;
    		                        td = tr.addElement("td").addText(tmp);
    		                        
    		                        tmp = pd.getRTSNODE()!=null  ? pd.getRTSNODE() : dv;
    		                        td = tr.addElement("td").addText(tmp);
    		                        
    		                        tmp = pd.getRTIMAMNODE()!=null  ? pd.getRTIMAMNODE() : dv;
    		                        td = tr.addElement("td").addText(tmp);
    		                        
    		                        tmp = pd.getRTChestW()!=null  ? pd.getRTChestW() : dv;
    		                        td = tr.addElement("td").addText(tmp);
    		                        
    		                        tmp = pd.getRTOTHER()!=null  ? pd.getRTOTHER() : dv;
    		                        td = tr.addElement("td").addText(tmp);
    		                        
    		                        tmp = pd.getTSizeClinical()!=null  ? pd.getTSizeClinical() : dv;
    		                        td = tr.addElement("td").addText(tmp);
    		                        
    		                        tmp = pd.getNSizeClinical()!=null  ? pd.getNSizeClinical() : dv;
    		                        td = tr.addElement("td").addText(tmp);
    		                        
    		                        tmp = pd.getStageTE()!=null  ? pd.getStageTE() : dv;
    		                        td = tr.addElement("td").addText(tmp);
    		                        
    		                        tmp = pd.getStageNE()!=null  ? pd.getStageNE() : dv;
    		                        td = tr.addElement("td").addText(tmp);
    		                        
    		                        tmp = pd.getSTAGEME()!=null  ? pd.getSTAGEME() : dv;
    		                        td = tr.addElement("td").addText(tmp);
    		                        
    		                        tmp = pd.getClinicalStageStr()!=null  ? pd.getClinicalStageStr() : dv;
    		                        td = tr.addElement("td").addText(tmp);
    		                        
    		                        tmp = pd.getClinRespT1_T2()!=null  ? pd.getClinRespT1_T2() : dv;
    		                        td = tr.addElement("td").addText(tmp);
    		                        
    		                        tmp = pd.getClinRespT1_T3()!=null  ? pd.getClinRespT1_T3() : dv;
    		                        td = tr.addElement("td").addText(tmp);
    		                        
    		                        tmp = pd.getClinRespT1_T4()!=null  ? pd.getClinRespT1_T4() : dv;
    		                        td = tr.addElement("td").addText(tmp);
    		                        
    		                        tmp = pd.getMorphology()!=null  ? pd.getMorphology().toString() : dv;
    		                        td = tr.addElement("td").addText(tmp);         
    		                                              
    		                        tmp = pd.getLES_T1()!=null  ? pd.getLES_T1() : dv;
    		                        td = tr.addElement("td").addText(tmp);
    		                        
    		                        tmp = pd.getLES_T2()!=null  ? pd.getLES_T2() : dv;
    		                        td = tr.addElement("td").addText(tmp);
    		                        
    		                        tmp = pd.getLES_T3()!=null  ? pd.getLES_T3() : dv;
    		                        td = tr.addElement("td").addText(tmp);
    		                        
    		                        tmp = pd.getLES_T4()!=null  ? pd.getLES_T4() : dv;
    		                        td = tr.addElement("td").addText(tmp);
    		                        
    		                        tmp = pd.getLdT1()!=null  ? Double.toString(pd.getLdT1()) : dv;
    		                        td = tr.addElement("td").addText(tmp);
    		                        
    		                        tmp = pd.getLdT2()!=null  ? Double.toString(pd.getLdT2()) : dv;
    		                        td = tr.addElement("td").addText(tmp);
    		                        
    		                        tmp = pd.getLdT3()!=null  ? Double.toString(pd.getLdT3()) : dv;
    		                        td = tr.addElement("td").addText(tmp);
    		                        
    		                        tmp = pd.getLdT4()!=null  ? Double.toString(pd.getLdT4()) : dv;
    		                        td = tr.addElement("td").addText(tmp);
    		                        
    		                        tmp = pd.getMriPctChangeT1_T2()!=null  ? String.valueOf(pd.getMriPctChangeT1_T2()) : dv;
    		                        td = tr.addElement("td").addText(tmp);
    		                        
    		                        tmp = pd.getMriPctChangeT1_T3()!=null  ? String.valueOf(pd.getMriPctChangeT1_T3()) : dv;
    		                        td = tr.addElement("td").addText(tmp);
    		                                                                        
    		                        tmp = pd.getMriPctChangeT1_T4()!=null  ? String.valueOf(pd.getMriPctChangeT1_T4()) : dv;
    		                        td = tr.addElement("td").addText(tmp);
    		                        
    		                        tmp = pd.getMriPctChangeT2_T3()!=null  ? Double.toString(pd.getMriPctChangeT2_T3()) : dv;
    		                        td = tr.addElement("td").addText(tmp);
    		                        
    		                        tmp = pd.getMriPctChangeT2_T4()!=null  ? Double.toString(pd.getMriPctChangeT2_T4()) : dv;
    		                        td = tr.addElement("td").addText(tmp);
    		                        
    		                        tmp = pd.getMriPctChangeT3_T4()!=null  ? Double.toString(pd.getMriPctChangeT3_T4()) : dv;
    		                        td = tr.addElement("td").addText(tmp);              
                                    
    							}
                            }
							
						}
						
					}
                    if(!pdList.isEmpty()){
                        ISPYClinicalFinding clinicalFinding = new ISPYClinicalFinding(sessionId, taskId, null, FindingStatus.Running);
                        clinicalFinding.setPatientData(pdList);
                        FindingReportBean frb = new FindingReportBean();
                        frb.setFinding(clinicalFinding);
                        ApplicationFactory.getPresentationTierCache().addPersistableToSessionCache(clinicalFinding.getSessionId(),clinicalFinding.getTaskId(), frb);
                    }
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return html.append(document.asXML());
	}
    
    
     public static HSSFWorkbook getReportExcel(Finding finding) {
         ISPYClinicalFinding clinicalFinding = (ISPYClinicalFinding) finding;
         
         HSSFWorkbook wb = new HSSFWorkbook();
         HSSFSheet sheet = wb.createSheet(clinicalFinding.getTaskId());
         
         List<PatientData> patientsData = clinicalFinding.getPatientData();
         
         
         String longHeaders = "ISPY_ID, INST_ID, AGE, AGECAT, RACE_ID, SSTAT, SURVDTD, CHEMOCAT, CHEMO, TAM, HERCEPTIN, DOSEDENSEANTHRA, DOSEDENSETAXANE, MENOSTATUS, SENTINELNODESAMPLE, " +
                "SENTINELNODERESULT, HISTOLOGICGRADEOS, ER_TS, PGR_TS, HER2COMMUNITYPOS, HER2COMMUNITYMETHOD, " +
                "SURGERYLUMPECTOMY, SURGERYMASTECTOMY, HISTOLOGICTYPEPS, INITLUMP_FUPMAST, SURGERY, DCISONLY, PTUMOR1SZCM_MICRO, " +
                "HISTOLOGICGRADEPS, NUMPOSNODES, NODESEXAMINED, PATHOLOGYSTAGE, REASON_NO_SURG, RTTHERAPY, RTBREAST, RTBOOST, " +
                "RTAXILLA, RTSNODE, RTIMAMNODE, RTCHESTW, RTOTHER, TSIZECLINICAL, NSIZECLINICAL, STAGETE, STAGENE, " +
                "STAGEME, CLINICALSTAGE, CLINRESPT1_T2, CLINRESPT1_T3, CLINRESPT1_T4, Morphologic pattern at T1," +
                "LES_T1, LES_T2, LES_T3, LES_T4, LD_T1, LD_T2, LD_T3, LD_T4, MRI % change T1_T2, MRI % change T1_T3, MRI % change T1_T4," +
                "MRI % change T2_T3, MRI % change T2_T4, MRI % change T3_T4";
         String[] heads = StringUtils.split(longHeaders, ",");
         HSSFRow row = sheet.createRow((short) 0);
         for(int j=0;j<heads.length;j++){
             HSSFCell cell = row.createCell((short) j);
             cell.setCellValue(heads[j]); 
         }
         HSSFRow dataRow;
         
         for(int i=0;i<patientsData.size();i++){
             dataRow = null;
             dataRow = sheet.createRow((short) i + 1);
             PatientData data = patientsData.get(i);
             String rowData = "";
             String noRowData = "--";
             Double rowDouble = 0.0;
             HSSFCell cell = null;
             
                 cell = dataRow.createCell( (short) 0);
                 rowData = data.getISPY_ID();
                 if(rowData==null)rowData = noRowData;
                 cell.setCellValue(rowData); 
                 
                 cell = dataRow.createCell((short) 1);
                 rowData = data.getInst_ID();
                 if(rowData==null)rowData = noRowData;
                 cell.setCellValue(rowData); 
                 
                 cell = dataRow.createCell((short) 2);
                 Double age = data.getPatientAge();
                 if(age!=null) {
                	rowData = age.toString(); 
                 }
                 else rowData = noRowData;
                 cell.setCellValue(rowData); 
                 
                 cell = dataRow.createCell((short) 3);
                 AgeCategoryType ageCat = data.getAgeCategory();
                 if(ageCat!=null){
                     rowData = ageCat.toString();
                 }
                 else rowData = noRowData;
                 cell.setCellValue(rowData);
                 
                 cell = dataRow.createCell((short) 4);
                 RaceType rt = data.getRace();
                 if(rt!=null){
                     rowData = rt.toString();
                 }
                 else rowData = noRowData;
                 cell.setCellValue(rowData);
                 
                 cell = dataRow.createCell((short) 5);
                 rowData = data.getSSTAT();
                 if(rowData==null)rowData = noRowData;
                 cell.setCellValue(rowData);
                 
                 cell = dataRow.createCell((short) 6);
                 rowData = data.getSURVDTD();
                 if(rowData==null)rowData = noRowData;
                 cell.setCellValue(rowData);
                 
                 cell = dataRow.createCell((short) 7);
                 rowData = data.getChemoCat();
                 if(rowData==null)rowData = noRowData;
                 cell.setCellValue(rowData);
                 
                 cell = dataRow.createCell((short) 8);
                 rowData = data.getChemo();
                 if(rowData==null)rowData = noRowData;
                 cell.setCellValue(rowData);
                 
                 cell = dataRow.createCell((short) 9);
                 rowData = data.getTAM();
                 if(rowData==null)rowData = noRowData;
                 cell.setCellValue(rowData);
                 
                 cell = dataRow.createCell((short) 10);
                 rowData = data.getHerceptin();
                 if(rowData==null)rowData = noRowData;
                 cell.setCellValue(rowData);
                 
                 cell = dataRow.createCell((short) 11);
                 rowData = data.getDosedenseanthra();
                 if(rowData==null)rowData = noRowData;
                 cell.setCellValue(rowData);
                 
                 cell = dataRow.createCell((short) 12);
                 rowData = data.getDosedensetaxane();
                 if(rowData==null)rowData = noRowData;
                 cell.setCellValue(rowData);
                 
                 cell = dataRow.createCell((short) 13);
                 rowData = data.getMenoStatus();
                 if(rowData==null)rowData = noRowData;
                 cell.setCellValue(rowData);
                 
                 cell = dataRow.createCell((short) 14);
                 rowData = data.getSentinelNodeSample();
                 if(rowData==null)rowData = noRowData;
                 cell.setCellValue(rowData);
                 
                 cell = dataRow.createCell((short) 15);
                 rowData = data.getSentinelNodeResult();
                 if(rowData==null)rowData = noRowData;
                 cell.setCellValue(rowData);
                 
                 cell = dataRow.createCell((short) 16);
                 rowData = data.getHistologicGradeOS();
                 if(rowData==null)rowData = noRowData;
                 cell.setCellValue(rowData);
                 
                 cell = dataRow.createCell((short) 17);
                 rowData = data.getER_TS();
                 if(rowData==null)rowData = noRowData;
                 cell.setCellValue(rowData);
                 
                 cell = dataRow.createCell((short) 18);
                 rowData = data.getPGR_TS();
                 if(rowData==null)rowData = noRowData;
                 cell.setCellValue(rowData);
                 
                 cell = dataRow.createCell((short) 19);
                 rowData = data.getHER2CommunityPOS();
                 if(rowData==null)rowData = noRowData;
                 cell.setCellValue(rowData);
                 
                 cell = dataRow.createCell((short) 20);
                 rowData = data.getHER2CommunityMethod();
                 if(rowData==null)rowData = noRowData;
                 cell.setCellValue(rowData);
                 
                 cell = dataRow.createCell((short) 21);
                 rowData = data.getSurgeryLumpectomy();
                 if(rowData==null)rowData = noRowData;
                 cell.setCellValue(rowData);
                 
                 cell = dataRow.createCell((short) 22);
                 rowData = data.getSurgeryMastectomy();
                 if(rowData==null)rowData = noRowData;
                 cell.setCellValue(rowData);
                 
                 cell = dataRow.createCell((short) 23);
                 rowData = data.getHistTypePS();
                 if(rowData==null)rowData = noRowData;
                 cell.setCellValue(rowData);
                 
                 cell = dataRow.createCell((short) 24);
                 rowData = data.getINITLUMP_FUPMAST();
                 if(rowData==null)rowData = noRowData;
                 cell.setCellValue(rowData);
                 
                 cell = dataRow.createCell((short) 25);
                 rowData = data.getSurgery();
                 if(rowData==null)rowData = noRowData;
                 cell.setCellValue(rowData);
                 
                 cell = dataRow.createCell((short) 26);
                 rowData = data.getDCISOnly();
                 if(rowData==null)rowData = noRowData;
                 cell.setCellValue(rowData);
                 
                 cell = dataRow.createCell((short) 27);
                 rowDouble = data.getPTumor1SZCM_Micro();
                 if(rowDouble!=null){
                     cell.setCellValue(rowDouble);
                 }
                     else cell.setCellValue(noRowData);
                 
                 cell = dataRow.createCell((short) 28);
                 rowData = data.getHistologicGradePS();
                 if(rowData==null)rowData = noRowData;
                 cell.setCellValue(rowData);
                 
                 cell = dataRow.createCell((short) 29);
                 rowData = data.getNumPosNodes();
                 if(rowData==null)rowData = noRowData;
                 cell.setCellValue(rowData);
                 
                 cell = dataRow.createCell((short) 30);
                 rowData = data.getNodesExamined();
                 if(rowData==null)rowData = noRowData;
                 cell.setCellValue(rowData);
                 
                 cell = dataRow.createCell((short) 31);
                 rowData = data.getPathologyStage();
                 if(rowData==null)rowData = noRowData;
                 cell.setCellValue(rowData);
                 
                 cell = dataRow.createCell((short) 32);
                 rowData = data.getReasonNoSurg();
                 if(rowData==null)rowData = noRowData;
                 cell.setCellValue(rowData);
                 
                 cell = dataRow.createCell((short) 33);
                 rowData = data.getRTTherapy();
                 if(rowData==null)rowData = noRowData;
                 cell.setCellValue(rowData);
                 
                 cell = dataRow.createCell((short) 34);
                 rowData = data.getRTBreast();
                 if(rowData==null)rowData = noRowData;
                 cell.setCellValue(rowData);
                 
                 cell = dataRow.createCell((short) 35);
                 rowData = data.getRTBOOST();
                 if(rowData==null)rowData = noRowData;
                 cell.setCellValue(rowData);
                 
                 cell = dataRow.createCell((short) 36);
                 rowData = data.getRTAXILLA();
                 if(rowData==null)rowData = noRowData;
                 cell.setCellValue(rowData);
                 
                 cell = dataRow.createCell((short) 37);
                 rowData = data.getRTSNODE();
                 if(rowData==null)rowData = noRowData;
                 cell.setCellValue(rowData);
                 
                 cell = dataRow.createCell((short) 38);
                 rowData = data.getRTIMAMNODE();
                 if(rowData==null)rowData = noRowData;
                 cell.setCellValue(rowData);
                 
                 cell = dataRow.createCell((short) 39);
                 rowData = data.getRTChestW();
                 if(rowData==null)rowData = noRowData;
                 cell.setCellValue(rowData);
                 
                 cell = dataRow.createCell((short) 40);
                 rowData = data.getRTOTHER();
                 if(rowData==null)rowData = noRowData;
                 cell.setCellValue(rowData);
                 
                 cell = dataRow.createCell((short) 41);
                 rowData = data.getTSizeClinical();
                 if(rowData==null)rowData = noRowData;
                 cell.setCellValue(rowData);
                 
                 cell = dataRow.createCell((short) 42);
                 if(rowData==null)rowData = noRowData;
                 rowData = data.getNSizeClinical();
                 cell.setCellValue(rowData);
                 
                 cell = dataRow.createCell((short) 43);
                 rowData = data.getStageTE();
                 if(rowData==null)rowData = noRowData;
                 cell.setCellValue(rowData);
                 
                 cell = dataRow.createCell((short) 44);
                 rowData = data.getStageNE();
                 if(rowData==null)rowData = noRowData;
                 cell.setCellValue(rowData);
                 
                 cell = dataRow.createCell((short) 45);
                 rowData = data.getSTAGEME();
                 if(rowData==null)rowData = noRowData;
                 cell.setCellValue(rowData);
                 
                 cell = dataRow.createCell((short) 46);
                 rowData = data.getClinicalStageStr();
                 if(rowData==null)rowData = noRowData;
                 cell.setCellValue(rowData);
                 
                 cell = dataRow.createCell((short) 47);
                 rowData = data.getClinRespT1_T2();
                 if(rowData==null)rowData = noRowData;
                 cell.setCellValue(rowData);
                 
                 cell = dataRow.createCell((short) 48);
                 rowData = data.getClinRespT1_T3();
                 if(rowData==null)rowData = noRowData;
                 cell.setCellValue(rowData);
                 
                 cell = dataRow.createCell((short) 49);
                 rowData = data.getClinRespT1_T4();
                 if(rowData==null)rowData = noRowData;
                 cell.setCellValue(rowData);
                 
                 cell = dataRow.createCell((short) 50);
                 MorphologyType mt = data.getMorphology();
                 if(mt!=null){
                     rowData = mt.toString();
                 }
                 else{
                     rowData = noRowData;
                 }
                 cell.setCellValue(rowData);                
                 
                 
                 cell = dataRow.createCell((short) 51);
                 rowData = data.getLES_T1();
                 cell.setCellValue(rowData);
                 
                 cell = dataRow.createCell((short) 52);
                 rowData = data.getLES_T2();
                 cell.setCellValue(rowData);
                 
                 cell = dataRow.createCell((short) 53);
                 rowData = data.getLES_T3();
                 cell.setCellValue(rowData);
                 
                 cell = dataRow.createCell((short) 54);
                 rowData = data.getLES_T4();
                 cell.setCellValue(rowData);
                 
                 cell = dataRow.createCell((short) 55);
                 rowDouble = data.getLdT1();
                 if(rowDouble!=null){
                     cell.setCellValue(rowDouble);
                 }
                else cell.setCellValue(noRowData);
                 
                 cell = dataRow.createCell((short) 56);
                 rowDouble = data.getLdT2();
                 if(rowDouble!=null){
                     cell.setCellValue(rowDouble);
                 }
                else cell.setCellValue(noRowData);
                 
                 cell = dataRow.createCell((short) 57);
                 rowDouble = data.getLdT3();
                 if(rowDouble!=null){
                     cell.setCellValue(rowDouble);
                 }
                else cell.setCellValue(noRowData);
                 
                 cell = dataRow.createCell((short) 58);
                 rowDouble = data.getLdT4();
                 if(rowDouble!=null){
                     cell.setCellValue(rowDouble);
                 }
                else cell.setCellValue(noRowData);                 
                 
                 
                 cell = dataRow.createCell((short) 59);
                 rowDouble = data.getMriPctChangeT1_T2();
                 if(rowDouble!=null){
                 cell.setCellValue(rowDouble);
                 }
                 else cell.setCellValue(noRowData);
                 
                 cell = dataRow.createCell((short) 60);
                 rowDouble = data.getMriPctChangeT1_T3();
                 if(rowDouble!=null){
                     cell.setCellValue(rowDouble);
                 }
                 else cell.setCellValue(noRowData);
                 
                 cell = dataRow.createCell((short) 61);
                 rowDouble = data.getMriPctChangeT1_T4();
                 if(rowDouble!=null){
                     cell.setCellValue(rowDouble);
                 }
                else cell.setCellValue(noRowData);                 
                    
                 cell = dataRow.createCell((short) 62);
                 rowDouble = data.getMriPctChangeT2_T3();
                 if(rowDouble!=null){
                     cell.setCellValue(rowDouble);
                 }
                else cell.setCellValue(noRowData);
                 
                 cell = dataRow.createCell((short) 63);
                 rowDouble = data.getMriPctChangeT2_T4();
                 if(rowDouble!=null){
                     cell.setCellValue(rowDouble);
                 }
                else cell.setCellValue(noRowData);
                 
                cell = dataRow.createCell((short) 64);
                rowDouble = data.getMriPctChangeT3_T4();
                if(rowDouble!=null){
                   cell.setCellValue(rowDouble);
                }
                else cell.setCellValue(noRowData);
             
         }
         
         return wb;
     }
    
}
