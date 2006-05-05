package gov.nih.nci.ispy.service.clinical;

public class PatientData {

	
private ClinicalStageType clinicalStage;
	
private String  ispy_id, 
				dataextractdt, 
				inst_id, 
				agecat, 
				race_id, 
				sstat, 
				survdtd, 
				chemo, 
				tam, 
				herceptin, 
				menostatus, 
				sentinelnodesample, 
				sentinelnoderesult, 
				histologicgradeos, 
				er_ts, 
				pgr_ts, 
				her2communitypos, 
				her2communitymethod, 
				surgerylumpectomy, 
				surgerymastectomy, 
				initlump_fupmast, 
				surgery, 
				dcisonly, 
				ptumor1szcm_micro, 
				histologicgradeps, 
				numposnodes, 
				nodesexamined, 
				pathologystage, 
				rttherapy, 
				rtbreast, 
				rtboost, 
				rtaxilla, 
				rtsnode, 
				rtimamnode, 
				rtchestw, 
				rtother, 
				tsizeclinical, 
				nsizeclinical, 
				stagete, 
				stagene, 
				stageme, 
				clinicalstageStr, 
				clinrespt1_t2, 
				clinrespt1_t3, 
				clinrespt1_t4;
	 
	
	public PatientData(String ispy_id) {
		this.ispy_id = ispy_id;
	}
	
	public String getAgeCat() {
		return agecat;
	}


	public void setAgeCat(String agecat) {
		this.agecat = agecat;
	}


	public String getChemo() {
		return chemo;
	}


	public void setChemo(String chemo) {
		this.chemo = chemo;
	}


	public String getClinicalStageStr() {
		return clinicalstageStr;
	}
	
	public ClinicalStageType getClinicalStage() {
	  return clinicalStage;
	}


	public void setClinicalStage(String clinicalstageStr) {
		
		this.clinicalstageStr = clinicalstageStr;
		
		if ((clinicalstageStr != null)&&(clinicalstageStr.trim().length() > 0)) {
		
		  String[] cs = clinicalstageStr.split("=");
		  
		  int stageVal = Integer.parseInt(cs[0].trim());
	
		  this.clinicalStage = ClinicalStageType.getTypeforValue(stageVal);
		}
		else {
		  this.clinicalStage = ClinicalStageType.MISSING;
		}
		
	}


	public String getClinRespT1_T2() {
		return clinrespt1_t2;
	}


	public void setClinRespT1_T2(String clinrespt1_t2) {
		this.clinrespt1_t2 = clinrespt1_t2;
	}


	public String getClinRespT1_T3() {
		return clinrespt1_t3;
	}


	public void setClinRespT1_T3(String clinrespt1_t3) {
		this.clinrespt1_t3 = clinrespt1_t3;
	}


	public String getClinRespT1_T4() {
		return clinrespt1_t4;
	}


	public void setClinRespT1_T4(String clinrespt1_t4) {
		this.clinrespt1_t4 = clinrespt1_t4;
	}


	public String getDataExtractDT() {
		return dataextractdt;
	}


	public void setDataExtractDT(String dataextractdt) {
		this.dataextractdt = dataextractdt;
	}


	public String getDCISOnly() {
		return dcisonly;
	}


	public void setDCISOnly(String dcisonly) {
		this.dcisonly = dcisonly;
	}


	public String getER_TS() {
		return er_ts;
	}


	public void setER_TS(String er_ts) {
		this.er_ts = er_ts;
	}


	public String getHER2CommunityMethod() {
		return her2communitymethod;
	}


	public void setHER2CommunityMethod(String her2communitymethod) {
		this.her2communitymethod = her2communitymethod;
	}


	public String getHER2CommunityPOS() {
		return her2communitypos;
	}


	public void setHER2CommunityPOS(String her2communitypos) {
		this.her2communitypos = her2communitypos;
	}


	public String getHerceptin() {
		return herceptin;
	}


	public void setHerceptin(String herceptin) {
		this.herceptin = herceptin;
	}


	public String getHistologicGradeOS() {
		return histologicgradeos;
	}


	public void setHistologicGradeOS(String histologicgradeos) {
		this.histologicgradeos = histologicgradeos;
	}


	public String getHistologicGradePS() {
		return histologicgradeps;
	}


	public void setHistologicGradePS(String histologicgradeps) {
		this.histologicgradeps = histologicgradeps;
	}


	public String getINITLUMP_FUPMAST() {
		return initlump_fupmast;
	}


	public void setINITLUMP_FUPMAST(String initlump_fupmast) {
		this.initlump_fupmast = initlump_fupmast;
	}


	public String getInst_ID() {
		return inst_id;
	}


	public void setInst_ID(String inst_id) {
		this.inst_id = inst_id;
	}


	public String getISPY_ID() {
		return ispy_id;
	}


	public void setISPY_ID(String ispy_id) {
		this.ispy_id = ispy_id;
	}


	public String getMenoStatus() {
		return menostatus;
	}


	public void setMenoStatus(String menostatus) {
		this.menostatus = menostatus;
	}


	public String getNodesExamined() {
		return nodesexamined;
	}


	public void setNodesExamined(String nodesexamined) {
		this.nodesexamined = nodesexamined;
	}


	public String getNSizeClinical() {
		return nsizeclinical;
	}


	public void setNSizeClinical(String nsizeclinical) {
		this.nsizeclinical = nsizeclinical;
	}


	public String getNumPosNodes() {
		return numposnodes;
	}


	public void setNumPosNodes(String numposnodes) {
		this.numposnodes = numposnodes;
	}


	public String getPathologyStage() {
		return pathologystage;
	}


	public void setPathologyStage(String pathologystage) {
		this.pathologystage = pathologystage;
	}


	public String getPGR_TS() {
		return pgr_ts;
	}


	public void setPGR_TS(String pgr_ts) {
		this.pgr_ts = pgr_ts;
	}


	public String getPTumor1SZCM_Micro() {
		return ptumor1szcm_micro;
	}


	public void setPTumor1SZCM_MICRO(String ptumor1szcm_micro) {
		this.ptumor1szcm_micro = ptumor1szcm_micro;
	}


	public String getRace_ID() {
		return race_id;
	}


	public void setRace_ID(String race_id) {
		this.race_id = race_id;
	}


	public String getRTAXILLA() {
		return rtaxilla;
	}


	public void setRTAXILLA(String rtaxilla) {
		this.rtaxilla = rtaxilla;
	}


	public String getRTBOOST() {
		return rtboost;
	}


	public void setRTBOOST(String rtboost) {
		this.rtboost = rtboost;
	}


	public String getRTBreast() {
		return rtbreast;
	}


	public void setRTBreast(String rtbreast) {
		this.rtbreast = rtbreast;
	}


	public String getRTChestW() {
		return rtchestw;
	}


	public void setRTChestW(String rtchestw) {
		this.rtchestw = rtchestw;
	}


	public String getRTIMAMNODE() {
		return rtimamnode;
	}


	public void setRTIMAMNODE(String rtimamnode) {
		this.rtimamnode = rtimamnode;
	}


	public String getRTOTHER() {
		return rtother;
	}


	public void setRTOTHER(String rtother) {
		this.rtother = rtother;
	}


	public String getRTSNODE() {
		return rtsnode;
	}


	public void setRTSNODE(String rtsnode) {
		this.rtsnode = rtsnode;
	}


	public String getRTTherapy() {
		return rttherapy;
	}


	public void setRTTherapy(String rttherapy) {
		this.rttherapy = rttherapy;
	}


	public String getSentinelNodeResult() {
		return sentinelnoderesult;
	}


	public void setSentinelNodeResult(String sentinelnoderesult) {
		this.sentinelnoderesult = sentinelnoderesult;
	}


	public String getSentinelNodeSample() {
		return sentinelnodesample;
	}


	public void setSentinelNodeSample(String sentinelnodesample) {
		this.sentinelnodesample = sentinelnodesample;
	}


	public String getSSTAT() {
		return sstat;
	}


	public void setSSTAT(String sstat) {
		this.sstat = sstat;
	}


	public String getSTAGEME() {
		return stageme;
	}


	public void setStageME(String stageme) {
		this.stageme = stageme;
	}


	public String getStageNE() {
		return stagene;
	}


	public void setStageNE(String stagene) {
		this.stagene = stagene;
	}


	public String getStageTE() {
		return stagete;
	}


	public void setStageTE(String stagete) {
		this.stagete = stagete;
	}


	public String getSurgery() {
		return surgery;
	}


	public void setSurgery(String surgery) {
		this.surgery = surgery;
	}


	public String getSurgeryLumpectomy() {
		return surgerylumpectomy;
	}


	public void setSurgeryLumpectomy(String surgerylumpectomy) {
		this.surgerylumpectomy = surgerylumpectomy;
	}


	public String getSurgeryMastectomy() {
		return surgerymastectomy;
	}


	public void setSurgeryMastectomy(String surgerymastectomy) {
		this.surgerymastectomy = surgerymastectomy;
	}


	public String getSURVDTD() {
		return survdtd;
	}


	public void setSURVDTD(String survdtd) {
		this.survdtd = survdtd;
	}


	public String getTAM() {
		return tam;
	}


	public void setTAM(String tam) {
		this.tam = tam;
	}


	public String getTSizeClinical() {
		return tsizeclinical;
	}


	public void setTSizeClinical(String tsizeclinical) {
		this.tsizeclinical = tsizeclinical;
	}
	
	public static int parseValue(String str) {	  
		if ((str == null)||(str.trim().length()==0)) return Integer.MIN_VALUE;
		
		String[] tokens = str.split("=");
		String valStr = tokens[0].trim();
		return Integer.parseInt(valStr);	
	}
	
	public static String  parseString(String str) {
		if ((str == null)||(str.trim().length()==0)) return null;
		
		String[] tokens = str.split("=");
		return tokens[1].trim();
		
	}

}
