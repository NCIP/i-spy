/*L
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/i-spy/LICENSE.txt for details.
 */

package gov.nih.nci.ispy.web.reports.quick;

import gov.nih.nci.caintegrator.application.cache.BusinessTierCache;
import gov.nih.nci.ispy.dto.query.ISPYclinicalDataQueryDTO;
import gov.nih.nci.ispy.service.annotation.IdMapperFileBasedService;
import gov.nih.nci.ispy.service.annotation.SampleInfo;
import gov.nih.nci.ispy.service.clinical.ClinicalDataService;
import gov.nih.nci.ispy.service.clinical.ClinicalDataServiceFactory;
import gov.nih.nci.ispy.service.clinical.PatientData;
import gov.nih.nci.ispy.service.findings.ISPYClinicalFinding;
import gov.nih.nci.ispy.service.imaging.ImagingFileBasedQueryService;
import gov.nih.nci.ispy.web.factory.ApplicationFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
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
                
                String longHeaders = "ISPY_ID, NCIA_IMAGE, INST_ID, AGECAT, RACE_ID, SSTAT, SURVDTD, CHEMOCAT, CHEMO, TAM, HERCEPTIN, DOSEDENSEANTHRA, DOSEDENSETAXANE, MENOSTATUS, SENTINELNODESAMPLE, SENTINELNODERESULT, HISTOLOGICGRADEOS, ER_TS, PGR_TS, HER2COMMUNITYPOS, HER2COMMUNITYMETHOD, SURGERYLUMPECTOMY, SURGERYMASTECTOMY, INITLUMP_FUPMAST, SURGERY, DCISONLY, PTUMOR1SZCM_MICRO, HISTOLOGICGRADEPS, NUMPOSNODES, NODESEXAMINED, PATHOLOGYSTAGE, RTTHERAPY, RTBREAST, RTBOOST, RTAXILLA, RTSNODE, RTIMAMNODE, RTCHESTW, RTOTHER, TSIZECLINICAL, NSIZECLINICAL, STAGETE, STAGENE, STAGEME, CLINICALSTAGE, CLINRESPT1_T2, CLINRESPT1_T3, CLINRESPT1_T4, Morphologic pattern at T1, MRI % change T1_T2, MRI % change T1_T3, MRI % change T1_T4, LES_T1, LES_T2, LES_T3, LES_T4";
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
                        
                        tmp = pd.getMriPctChangeT1_T2()!=null  ? String.valueOf(pd.getMriPctChangeT1_T2()) : dv;
                        td = tr.addElement("td").addText(tmp);
                        
                        tmp = pd.getMriPctChangeT1_T3()!=null  ? String.valueOf(pd.getMriPctChangeT1_T3()) : dv;
                        td = tr.addElement("td").addText(tmp);
                                                                        
                        tmp = pd.getMriPctChangeT1_T4()!=null  ? String.valueOf(pd.getMriPctChangeT1_T4()) : dv;
                        td = tr.addElement("td").addText(tmp);
                                               
                        tmp = pd.getLES_T1()!=null  ? pd.getLES_T1() : dv;
                        td = tr.addElement("td").addText(tmp);
                        
                        tmp = pd.getLES_T2()!=null  ? pd.getLES_T2() : dv;
                        td = tr.addElement("td").addText(tmp);
                        
                        tmp = pd.getLES_T3()!=null  ? pd.getLES_T3() : dv;
                        td = tr.addElement("td").addText(tmp);
                        
                        tmp = pd.getLES_T4()!=null  ? pd.getLES_T4() : dv;
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
	
	public static StringBuffer quickSampleReport(List<String> sampleIds){
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
					
					String longHeaders = "ISPY_ID, LabTrak ID, NCIA_IMAGE, INST_ID, AGECAT, RACE_ID, SSTAT, SURVDTD, CHEMO, TAM, HERCEPTIN, MENOSTATUS, SENTINELNODESAMPLE, SENTINELNODERESULT, HISTOLOGICGRADEOS, ER_TS, PGR_TS, HER2COMMUNITYPOS, HER2COMMUNITYMETHOD, SURGERYLUMPECTOMY, SURGERYMASTECTOMY, INITLUMP_FUPMAST, SURGERY, DCISONLY, PTUMOR1SZCM_MICRO, HISTOLOGICGRADEPS, NUMPOSNODES, NODESEXAMINED, PATHOLOGYSTAGE, RTTHERAPY, RTBREAST, RTBOOST, RTAXILLA, RTSNODE, RTIMAMNODE, RTCHESTW, RTOTHER, TSIZECLINICAL, NSIZECLINICAL, STAGETE, STAGENE, STAGEME, CLINICALSTAGE, CLINRESPT1_T2, CLINRESPT1_T3, CLINRESPT1_T4, Morphologic pattern at T1, MRI % change T1_T2, MRI % change T1_T3, MRI % change T1_T4";
					String[] heads = StringUtils.split(longHeaders, ",");
					for(String h : heads){
						td = tr.addElement("td").addAttribute("class", "header").addText(h.trim());
					}
					
					/*
					td = tr.addElement("td").addAttribute("class", "header").addText("Patient DID");
					td = tr.addElement("td").addAttribute("class", "header").addText("Disease");

					td = tr.addElement("td").addAttribute("class", "header").addText("LabTrack ID");
					td = tr.addElement("td").addAttribute("class", "header").addText("Histology Type");
					td = tr.addElement("td").addAttribute("class", "header").addText("Nuclear Grade");
					td = tr.addElement("td").addAttribute("class", "header").addText("Morphology");
					
					td = tr.addElement("td").addAttribute("class", "header").addText("Agents");
					td = tr.addElement("td").addAttribute("class", "header").addText("Response");
					td = tr.addElement("td").addAttribute("class", "header").addText("ER Status");
					td = tr.addElement("td").addAttribute("class", "header").addText("ER Value");
					td = tr.addElement("td").addAttribute("class", "header").addText("Size cm");
					td = tr.addElement("td").addAttribute("class", "header").addText("HER2 Status");
					
					td = tr.addElement("td").addAttribute("class", "header").addText("HER2 Value");
					td = tr.addElement("td").addAttribute("class", "header").addText("Diameter");
					td = tr.addElement("td").addAttribute("class", "header").addText("Micro Size cm");
					td = tr.addElement("td").addAttribute("class", "header").addText("% Change");
					td = tr.addElement("td").addAttribute("class", "header").addText("Response");
					td = tr.addElement("td").addAttribute("class", "header").addText("PR Status");
					td = tr.addElement("td").addAttribute("class", "header").addText("PR Value");
					td = tr.addElement("td").addAttribute("class", "header").addText("Timepoint");
					*/
					
					
					//NEW way of getting clinical data 
					//replace the for loop below with :
					//for (SampleInfo si : samples) { 
					//      you can get the labtrack id by calling si.getLabtrackId()
					//      then get the PatientData object by calling:
					//      PatientData pd= cqs.getPatientDataForPatientDID(si.getRegistrantId())
					
					
					for(SampleInfo si : samples)	{

						if(si!=null)	{
							
							//PatientData pd = cqs.getPatientDataForPatientDID(si.getISPYId());
                            Set<String> ispyIds = new HashSet<String>();
                            ispyIds.add(si.getISPYId());
                            ISPYclinicalDataQueryDTO dto = new ISPYclinicalDataQueryDTO();
                            dto.setRestrainingSamples(ispyIds);
                            Set<PatientData> pdSet = cqs.getClinicalData(dto);
                            for(PatientData pd:pdSet){    						
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
    								
    								tmp = pd.getAgeCategory()!=null  ? pd.getAgeCategory().toString() : dv;
    								td = tr.addElement("td").addText(tmp);
    								
    								tmp = pd.getRace()!=null  ? pd.getRace().toString() : dv;
    								td = tr.addElement("td").addText(tmp);
    								
    								tmp = pd.getSSTAT()!=null  ? pd.getSSTAT() : dv;
    								td = tr.addElement("td").addText(tmp);
    								
    								tmp = pd.getSURVDTD()!=null  ? pd.getSURVDTD() : dv;
    								td = tr.addElement("td").addText(tmp);
    								
    								tmp = pd.getChemo()!=null  ? pd.getChemo() : dv;
    								td = tr.addElement("td").addText(tmp);
    								
    								tmp = pd.getTAM()!=null  ? pd.getTAM() : dv;
    								td = tr.addElement("td").addText(tmp);
    								
    								tmp = pd.getHerceptin()!=null  ? pd.getHerceptin() : dv;
    								td = tr.addElement("td").addText(tmp);
    	
    								tmp = pd.getMenoStatus()!=null  ? pd.getMenoStatus() : dv;
    								td = tr.addElement("td").addText(tmp);
    								
    								tmp = pd.getSentinelNodeSample()!=null  ? pd.getSentinelNodeSample() : dv;
    								td = tr.addElement("td").addText(tmp);
    								
    								tmp = pd.getSentinelNodeResult()!=null  ? pd.getSentinelNodeResult() : dv;
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
    								
    								tmp = pd.getMriPctChangeT1_T2()!=null  ? String.valueOf(pd.getMriPctChangeT1_T2()) : dv;
    								td = tr.addElement("td").addText(tmp);
    								
    								tmp = pd.getMriPctChangeT1_T3()!=null  ? String.valueOf(pd.getMriPctChangeT1_T3()) : dv;
    								td = tr.addElement("td").addText(tmp);
    								
    								tmp = pd.getMriPctChangeT1_T4()!=null  ? String.valueOf(pd.getMriPctChangeT1_T4()) : dv;
    								td = tr.addElement("td").addText(tmp);
    							}
                            }
							/***************************/
							/*
							tmp = pd.getClinicalStage() != null && pd.getClinicalStage() != ClinicalStageType.MISSING ? pd.getClinicalStage().toString() : dv;
							td = tr.addElement("td").addText(tmp);

							//tmp = cd.getLabtrackId() != null ? cd.getLabtrackId().toString() : dv;
							//@TODO need to get correct labtrack id for clinical report
							tmp = dv;
							td = tr.addElement("td").addText(tmp);
							
							tmp = cd.getPrimaryTumorHistologyType() != null && !cd.getPrimaryTumorHistologyType().trim().equals("")? cd.getPrimaryTumorHistologyType().toString() : dv;
							td = tr.addElement("td").addText(tmp);

							tmp = cd.getPrimaryTumorNuclearGrade() != null && !cd.getPrimaryTumorNuclearGrade().trim().equals("") ? cd.getPrimaryTumorNuclearGrade().toString() : dv;
							td = tr.addElement("td").addText(tmp);
							
							tmp = cd.getTumorMorphology() != null ? cd.getTumorMorphology().toString() : dv;
							td = tr.addElement("td").addText(tmp);
							
							//this ones a list
							tmp = cd.getChemicalAgents() != null ? cd.getChemicalAgents().toString() : dv;
							td = tr.addElement("td").addText(tmp);
							
							//this ones an enum?
							tmp = cd.getClinicalResponse() != null && cd.getClinicalResponse() != ClinicalResponseType.MISSING ? cd.getClinicalResponse().toString() : dv;
							td = tr.addElement("td").addText(tmp);
							//enum
							tmp = cd.getErStatus() != null && cd.getErStatus() != ERstatusType.MISSING ? cd.getErStatus().toString() : dv;
							td = tr.addElement("td").addText(tmp);
							
							//double
							tmp = cd.getErValue() != null && String.valueOf(cd.getErValue()).length() > 0 ? String.valueOf(cd.getErValue()) : dv;
							td = tr.addElement("td").addText(tmp);
							
							//double
							tmp = cd.getGrossTumorSizeInCM() != null ? String.valueOf(cd.getGrossTumorSizeInCM()) : dv;
							td = tr.addElement("td").addText(tmp);
							
							//enum
							tmp = cd.getHER2status() != null && cd.getHER2status() != HER2statusType.MISSING ? cd.getHER2status().toString() : dv;
							td = tr.addElement("td").addText(tmp);
							
							//double
							tmp = cd.getHer2Value() != null ? String.valueOf(cd.getHer2Value()) : dv;
							td = tr.addElement("td").addText(tmp);
							
							tmp = cd.getLongestDiameter() != null ? String.valueOf(cd.getLongestDiameter()) : dv;
							td = tr.addElement("td").addText(tmp);
							
							tmp = cd.getMicroscopeTumorSizeInCM() != null ? String.valueOf(cd.getMicroscopeTumorSizeInCM()) : dv;
							td = tr.addElement("td").addText(tmp);
							
							tmp = cd.getMRIpctChange() != null ? String.valueOf(cd.getMRIpctChange()) : dv;
							td = tr.addElement("td").addText(tmp);
							
							tmp = cd.getPatientResponse() != null && cd.getPatientResponse() != ClinicalResponseType.MISSING ? cd.getPatientResponse().toString() : dv;
							td = tr.addElement("td").addText(tmp);
							
							tmp = cd.getPrStatus() != null && cd.getPrStatus() != PRstatusType.MISSING ? cd.getPrStatus().toString() : dv;
							td = tr.addElement("td").addText(tmp);
							
							tmp = cd.getPrValue() != null ? String.valueOf(cd.getPrValue()) : dv;
							td = tr.addElement("td").addText(tmp);
							
							tmp = cd.getTimepoint() != null ? cd.getTimepoint().toString() : dv;
							td = tr.addElement("td").addText(tmp);
							*/
						}
						
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return html.append(document.asXML());
	}
}
