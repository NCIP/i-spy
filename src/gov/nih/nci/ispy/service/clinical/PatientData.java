package gov.nih.nci.ispy.service.clinical;

public class PatientData {

	
private ClinicalStageType clinicalStage;
	
private String  ispy_id, 
				dataextractdt, 
				inst_id, 
				//race_id, 
				sstat, 
				survdtd, 
				//chemo, 
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
				//ptumor1szcm_micro, 
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

    private Double mriPctChangeT1_T2;
    private Double mriPctChangeT1_T3;
    private Double mriPctChangeT1_T4;
    
    private Double ptumor1szcm_micro;
    
    private String morphPatternBsl;

	private ClinicalResponseType clinicalResponse_t1_t3;

	private ClinicalResponseType clinicalResponse_t1_t4;

	private ClinicalResponseType clinicalResponse_t1_t2;

	private ERstatusType erStatus;

	private PRstatusType prStatus;

	private HER2statusType her2Status;
	
	private NeoAdjuvantChemoRegimenType neoChemo;
    
	private AgeCategoryType ageCategory;
	
	private RaceType race;
    
	public PatientData(String ispy_id) {
		this.ispy_id = ispy_id;
	}
	
	public AgeCategoryType getAgeCategory() {
		return ageCategory;
	}

	
	public void setAgeCategory(AgeCategoryType ageCategory) {
	  this.ageCategory = ageCategory;
	}

	public void setAgeCategory(String agecatStr) {
		
		if ((agecatStr == null)||(agecatStr.trim().length()==0)) {
		  ageCategory = AgeCategoryType.Age_GE_89_OR_NA;
		}
		
		this.ageCategory = AgeCategoryType.getValueForString(agecatStr);
	}


	public String getChemo() {
		
		if (neoChemo != null) {
		  return neoChemo.toString();
		}
		
		return "";
	}
	
	public NeoAdjuvantChemoRegimenType getChemoValue() {
	  return neoChemo;
	}


	public void setChemo(String chemo) {
		//this.chemo = chemo;
		this.neoChemo = NeoAdjuvantChemoRegimenType.getValueForString(chemo);
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
		int crValue = parseValue(clinrespt1_t2);
		this.clinicalResponse_t1_t2 = ClinicalResponseType.getTypeForValue(crValue);
	}


	public String getClinRespT1_T3() {
		return clinrespt1_t3;
	}


	public void setClinRespT1_T3(String clinrespt1_t3) {
		this.clinrespt1_t3 = clinrespt1_t3;
		int crValue = parseValue(clinrespt1_t3);
		this.clinicalResponse_t1_t3 = ClinicalResponseType.getTypeForValue(crValue);
	}


	public String getClinRespT1_T4() {
		return clinrespt1_t4;
	}


	public void setClinRespT1_T4(String clinrespt1_t4) {
		this.clinrespt1_t4 = clinrespt1_t4;
		int crValue = parseValue(clinrespt1_t4);
		this.clinicalResponse_t1_t4 = ClinicalResponseType.getTypeForValue(crValue);
	}
	
	public ClinicalResponseType getClinicalResponse(TimepointType timepoint) {
		switch(timepoint) {
		case T1: return ClinicalResponseType.NA;
		case T2: return clinicalResponse_t1_t2;
		case T3: return clinicalResponse_t1_t3;
		case T4: return clinicalResponse_t1_t4;
		
		}
		return ClinicalResponseType.NA;
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
		this.erStatus = ERstatusType.getTypeForString(parseString(er_ts));
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
		this.her2Status = HER2statusType.getTypeForString(parseString(her2communitypos));
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
		this.prStatus = PRstatusType.getTypeForString(parseString(pgr_ts));
	}


	public Double getPTumor1SZCM_Micro() {
		return ptumor1szcm_micro;
	}


	public void setPTumor1SZCM_MICRO(Double ptumor1szcm_micro) {
		this.ptumor1szcm_micro = ptumor1szcm_micro;
	}


	public RaceType getRace() {
		return race;
	}


	public void setRace_ID(String raceStr) {
	  race = RaceType.getValueForString(raceStr);
	}
	
	public void setRace_ID(RaceType race) {
	  this.race = race;
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

	public Double getMriPctChangeT1_T2() {
		return mriPctChangeT1_T2;
	}

	public void setMriPctChangeT1_T2(Double mriPctChangeT1_T2) {
		this.mriPctChangeT1_T2 = mriPctChangeT1_T2;
	}

	public Double getMriPctChangeT1_T3() {
		return mriPctChangeT1_T3;
	}

	public void setMriPctChangeT1_T3(Double mriPctChangeT1_T3) {
		this.mriPctChangeT1_T3 = mriPctChangeT1_T3;
	}

	public Double getMriPctChangeT1_T4() {
		return mriPctChangeT1_T4;
	}

	public void setMriPctChangeT1_T4(Double mriPctChangeT1_T4) {
		this.mriPctChangeT1_T4 = mriPctChangeT1_T4;
	}

	public String getMorphPatternBsl() {
		return morphPatternBsl;
	}

	public void setMorphPatternBsl(String morphPatternBsl) {
		this.morphPatternBsl = morphPatternBsl;
	}

	public ERstatusType getErStatus() {
		return erStatus;
	}

	public PRstatusType getPrStatus() {
		return prStatus;
	}

	public HER2statusType getHER2status() {
	  return her2Status;
	}

}
