package gov.nih.nci.ispy.web.reports.quick;

import gov.nih.nci.ispy.service.clinical.ClinicalData;
import gov.nih.nci.ispy.service.clinical.ClinicalFileBasedQueryService;
import gov.nih.nci.ispy.service.clinical.ClinicalResponseType;
import gov.nih.nci.ispy.service.clinical.DiseaseStageType;
import gov.nih.nci.ispy.service.clinical.ERstatusType;
import gov.nih.nci.ispy.service.clinical.HER2statusType;
import gov.nih.nci.ispy.service.clinical.PRstatusType;
import gov.nih.nci.ispy.service.clinical.TimepointType;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class QuickClinicalReport {
	
	
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
				
				List<ClinicalData> clinicalDataList = ClinicalFileBasedQueryService.getInstance().getClinicalDataForLabtrackIds(sampleIds);
				
				if(clinicalDataList != null  && sampleIds != null){
					int count = 0;

					Element table = document.addElement("table").addAttribute("id", "reportTable").addAttribute("class", "report");
					Element tr = null;
					Element td = null;
					tr = table.addElement("tr").addAttribute("class", "header");
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
					
					
					for(ClinicalData cd : clinicalDataList){
						
						if(cd!=null)	{
							tr = table.addElement("tr").addAttribute("class", "data");
							
							String tmp = "";
							String sid = cd.getPatientId()!=null  ? cd.getPatientId() : dv;
							td = tr.addElement("td").addText(sid);
							
							String dis = cd.getDiseaseStage() != null && cd.getDiseaseStage() != DiseaseStageType.MISSING ? cd.getDiseaseStage().toString() : dv;
							td = tr.addElement("td").addText(dis);

							tmp = cd.getLabtrackId() != null ? cd.getLabtrackId().toString() : dv;
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
							tmp = String.valueOf(cd.getErValue()).length() > 0 ? String.valueOf(cd.getErValue()) : dv;
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
