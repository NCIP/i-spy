package gov.nih.nci.ispy.service.clinical;

import gov.nih.nci.ispy.service.common.TimepointType;

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
				clinrespt1_t4,
				chemoCat,
			    dosedenseanthra,
				dosedensetaxane,
			    LES_T1,
				LES_T2,
				LES_T3,
				LES_T4;

                
                

	private String histTypeOfInvasiveTumor;
	private String histTypePS;
	private String reasonNoSurg;

	private Double patientAge;
    private Double mriPctChangeT1_T2;
    private Double mriPctChangeT1_T3;
    private Double mriPctChangeT1_T4;
    private Double mriPctChangeT2_T3;
    private Double mriPctChangeT2_T4;
    private Double mriPctChangeT3_T4;
    private Double ldT1;
    private Double ldT2;
    private Double ldT3;
    private Double ldT4;
    
    
    private Double ptumor1szcm_micro;
    
    // private rcb index size
    
    private Double rcbIndexSize;
    
    // pathology complete response (pcr)
    private String pcr;
    
    private PcrType pcrType;
    
    private String inSituHisto;
    
    private String invDzHisto;
    
    private String invDzMultiFoc;
    
    private String invDzCellularity;
    
    private String surgMargins;
    
    private String yT;
    
    private String yN;
    
    private String yM;
    
    private String inSituDz;
    
    private String inSituSpan;
    
    private String percentInSitu;
    
    private String inSituGrade;
    
    private String invDz;
    
    
    private String lVI;
    
    private String metSzLN;
    
    private String rcbClass;
    
    private String rCB_PATHSZ_1;
    
    private String rCB_PATHSZ_2;
    
    private String ptumor1szcm_micro_1;
    
    private String ptumor1szcm_micro_2;
    
    // below are new patient clinical fields with new clinical data received in september, 2007
    
    private Long height;
    
    private Long weight;
    
    private Double  bsa;
    
    private Long eRpos;
    
    private Long pgRpos;
    
    private Long fineNeedle;
    
    private Long coreNeedle;
    
    private Long incisional;
    
    private Long bilateralCa;
    
    private Long laterality;
    
    
    private Long RtBrTD;
    
    private Long RtBoTD;
    
    private Long RtAxTD;
    
    private Long RtSNTD;
    
    private Long RtIMTD;
    
    private Long RtCWTD;
    
    private Long RtOtTD;
    
    private Long localProgress;
    
    private Long distProgress;
    
    private Long t4Baseline;
    private Long t4Early;
    private Long t4Int;
    private Long t4PreS;

    private Long baseAxillary ;
    private Long earlyAxillary ;
    private Long intAxillary;
    private Long preSAxillary ;
    private Long baseInternalM ;
    private Long earlyInternalM ;
    private Long intInternalM ;
    private Long preSInternalM ;
    
    private Long baseSupra ;
    private Long earlySupra ;
    private Long intSupra;
    private Long preSSupra;




    
    
    
    
    
    
    
    
    
    
    //private String morphPatternBsl;
    
    private MorphologyType morphology;

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
		  ageCategory = AgeCategoryType.AGE_GE_89_OR_NA;
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


	public Double getRcbIndexSize() {
		return rcbIndexSize;
	}

	public void setRcbIndexSize(Double rcbIndexSize) {
		this.rcbIndexSize = rcbIndexSize;
	}

	public String getPcr() {
		return pcr;
	}

	public void setPcr(String pcr) {		
		pcrType = PcrType.getValueForString(pcr);
	}

	
	public PcrType getPcrType() {
		return pcrType;
	}

	public void setPcrType(PcrType pcrType) {
		this.pcrType = pcrType;
	}

	
	public String getInSituHisto() {
		return inSituHisto;
	}

	public void setInSituHisto(String inSituHisto) {
		this.inSituHisto = inSituHisto;
	}

	
	public String getInvDzHisto() {
		return invDzHisto;
	}

	public void setInvDzHisto(String invDzHisto) {
		this.invDzHisto = invDzHisto;
	}
 
		public String getInvDzMultiFoc() {
		return invDzMultiFoc;
	}

	public void setInvDzMultiFoc(String invDzMultiFoc) {
		this.invDzMultiFoc = invDzMultiFoc;
	}

	 
	public String getInvDzCellularity() {
		return invDzCellularity;
	}

	public void setInvDzCellularity(String invDzCellularity) {
		this.invDzCellularity = invDzCellularity;
	}

	
	
	public String getSurgMargins() {
		return surgMargins;
	}

	public void setSurgMargins(String surgMargins) {
		this.surgMargins = surgMargins;
	}

	
	public String getYT() {
		return yT;
	}

	public void setYT(String yt) {
		yT = yt;
	}
	
	

	public String getYN() {
		return yN;
	}

	public void setYN(String yn) {
		yN = yn;
	}

	 
	public String getYM() {
		return yM;
	}

	public void setYM(String ym) {
		yM = ym;
	}
	
	

	public String getInSituDz() {
		return inSituDz;
	}

	public void setInSituDz(String inSituDz) {
		this.inSituDz = inSituDz;
	}

	
	public String getInSituSpan() {
		return inSituSpan;
	}
	
	

	public String getPercentInSitu() {
		return percentInSitu;
	}

	public void setPercentInSitu(String percentInSitu) {
		this.percentInSitu = percentInSitu;
	}

	public void setInSituSpan(String inSituSpan) {
		this.inSituSpan = inSituSpan;
	}
	
	

	public String getInSituGrade() {
		return inSituGrade;
	}

	public void setInSituGrade(String inSituGrade) {
		this.inSituGrade = inSituGrade;
	}

	
	public String getInvDz() {
		return invDz;
	}

	public void setInvDz(String invDz) {
		this.invDz = invDz;
	}
	
	

	public String getLVI() {
		return lVI;
	}

	public void setLVI(String lvi) {
		lVI = lvi;
	}
	
	

	public String getMetSzLN() {
		return metSzLN;
	}

	public void setMetSzLN(String metSzLN) {
		this.metSzLN = metSzLN;
	}

	
	public String getRcbClass() {
		return rcbClass;
	}

	public void setRcbClass(String rcbClass) {
		this.rcbClass = rcbClass;
	}

	
	public String getRCB_PATHSZ_1() {
		return rCB_PATHSZ_1;
	}

	public void setRCB_PATHSZ_1(String rcb_pathsz_1) {
		rCB_PATHSZ_1 = rcb_pathsz_1;
	}
 
	 
	public String getRCB_PATHSZ_2() {
		return rCB_PATHSZ_2;
	}

	public void setRCB_PATHSZ_2(String rcb_pathsz_2) {
		rCB_PATHSZ_2 = rcb_pathsz_2;
	}

	
	public String getPtumor1szcm_micro_1() {
		return ptumor1szcm_micro_1;
	}

	public void setPtumor1szcm_micro_1(String ptumor1szcm_micro_1) {
		this.ptumor1szcm_micro_1 = ptumor1szcm_micro_1;
	}

	public String getPtumor1szcm_micro_2() {
		return ptumor1szcm_micro_2;
	}

	public void setPtumor1szcm_micro_2(String ptumor1szcm_micro_2) {
		this.ptumor1szcm_micro_2 = ptumor1szcm_micro_2;
	}

	
	
	public Long getBaseAxillary() {
		return baseAxillary;
	}

	public void setBaseAxillary(Long baseAxillary) {
		this.baseAxillary = baseAxillary;
	}

	public Long getBaseInternalM() {
		return baseInternalM;
	}

	public void setBaseInternalM(Long baseInternalM) {
		this.baseInternalM = baseInternalM;
	}

	public Long getBaseSupra() {
		return baseSupra;
	}

	public void setBaseSupra(Long baseSupra) {
		this.baseSupra = baseSupra;
	}

	public Long getBilateralCa() {
		return bilateralCa;
	}

	public void setBilateralCa(Long bilateralCa) {
		this.bilateralCa = bilateralCa;
	}

	public Double getBsa() {
		return bsa;
	}

	public void setBsa(Double bsa) {
		this.bsa = bsa;
	}

	public Long getCoreNeedle() {
		return coreNeedle;
	}

	public void setCoreNeedle(Long coreNeedle) {
		this.coreNeedle = coreNeedle;
	}

	public Long getDistProgress() {
		return distProgress;
	}

	public void setDistProgress(Long distProgress) {
		this.distProgress = distProgress;
	}

	public Long getEarlyAxillary() {
		return earlyAxillary;
	}

	public void setEarlyAxillary(Long earlyAxillary) {
		this.earlyAxillary = earlyAxillary;
	}

	public Long getEarlyInternalM() {
		return earlyInternalM;
	}

	public void setEarlyInternalM(Long earlyInternalM) {
		this.earlyInternalM = earlyInternalM;
	}

	public Long getEarlySupra() {
		return earlySupra;
	}

	public void setEarlySupra(Long earlySupra) {
		this.earlySupra = earlySupra;
	}

	public Long getERpos() {
		return eRpos;
	}

	public void setERpos(Long rpos) {
		eRpos = rpos;
	}

	public Long getFineNeedle() {
		return fineNeedle;
	}

	public void setFineNeedle(Long fineNeedle) {
		this.fineNeedle = fineNeedle;
	}

	public Long getHeight() {
		return height;
	}

	public void setHeight(Long height) {
		this.height = height;
	}

	public Long getIncisional() {
		return incisional;
	}

	public void setIncisional(Long incisional) {
		this.incisional = incisional;
	}

	public Long getIntAxillary() {
		return intAxillary;
	}

	public void setIntAxillary(Long intAxillary) {
		this.intAxillary = intAxillary;
	}

	public Long getIntInternalM() {
		return intInternalM;
	}

	public void setIntInternalM(Long intInternalM) {
		this.intInternalM = intInternalM;
	}

	public Long getIntSupra() {
		return intSupra;
	}

	public void setIntSupra(Long intSupra) {
		this.intSupra = intSupra;
	}

	public Long getLaterality() {
		return laterality;
	}

	public void setLaterality(Long laterality) {
		this.laterality = laterality;
	}

	public Long getLocalProgress() {
		return localProgress;
	}

	public void setLocalProgress(Long localProgress) {
		this.localProgress = localProgress;
	}

	public Long getPgRpos() {
		return pgRpos;
	}

	public void setPgRpos(Long pgRpos) {
		this.pgRpos = pgRpos;
	}

	public Long getPreSAxillary() {
		return preSAxillary;
	}

	public void setPreSAxillary(Long preSAxillary) {
		this.preSAxillary = preSAxillary;
	}

	public Long getPreSInternalM() {
		return preSInternalM;
	}

	public void setPreSInternalM(Long preSInternalM) {
		this.preSInternalM = preSInternalM;
	}

	public Long getPreSSupra() {
		return preSSupra;
	}

	public void setPreSSupra(Long preSSupra) {
		this.preSSupra = preSSupra;
	}

	public String getRtaxilla() {
		return rtaxilla;
	}

	public void setRtaxilla(String rtaxilla) {
		this.rtaxilla = rtaxilla;
	}

	public Long getRtAxTD() {
		return RtAxTD;
	}

	public void setRtAxTD(Long rtAxTD) {
		RtAxTD = rtAxTD;
	}

	public String getRtboost() {
		return rtboost;
	}

	public void setRtboost(String rtboost) {
		this.rtboost = rtboost;
	}

	public Long getRtBoTD() {
		return RtBoTD;
	}

	public void setRtBoTD(Long rtBoTD) {
		RtBoTD = rtBoTD;
	}

	public String getRtbreast() {
		return rtbreast;
	}

	public void setRtbreast(String rtbreast) {
		this.rtbreast = rtbreast;
	}

	public Long getRtBrTD() {
		return RtBrTD;
	}

	public void setRtBrTD(Long rtBrTD) {
		RtBrTD = rtBrTD;
	}

	public Long getRtCWTD() {
		return RtCWTD;
	}

	public void setRtCWTD(Long rtCWTD) {
		RtCWTD = rtCWTD;
	}

	public Long getRtIMTD() {
		return RtIMTD;
	}

	public void setRtIMTD(Long rtIMTD) {
		RtIMTD = rtIMTD;
	}

	public Long getRtOtTD() {
		return RtOtTD;
	}

	public void setRtOtTD(Long rtOtTD) {
		RtOtTD = rtOtTD;
	}

	public Long getRtSNTD() {
		return RtSNTD;
	}

	public void setRtSNTD(Long rtSNTD) {
		RtSNTD = rtSNTD;
	}

	public Long getT4Baseline() {
		return t4Baseline;
	}

	public void setT4Baseline(Long baseline) {
		t4Baseline = baseline;
	}

	public Long getT4Early() {
		return t4Early;
	}

	public void setT4Early(Long early) {
		t4Early = early;
	}

	public Long getT4Int() {
		return t4Int;
	}

	public void setT4Int(Long int1) {
		t4Int = int1;
	}

	public Long getT4PreS() {
		return t4PreS;
	}

	public void setT4PreS(Long preS) {
		t4PreS = preS;
	}

	public Long getWeight() {
		return weight;
	}

	public void setWeight(Long weight) {
		this.weight = weight;
	}

	public void setPrStatus(PRstatusType prStatus) {
		this.prStatus = prStatus;
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

	public MorphologyType getMorphology() {
		return morphology;
	}

	public void setMorphology(MorphologyType morphology) {
		this.morphology = morphology;
	}
	
	public void setMorphology(String morphologyStr) {
	  int morphValue = parseValue(morphologyStr);
	  MorphologyType morphType = MorphologyType.getTypeForValue(morphValue);
	  setMorphology(morphType);		
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

	public String getChemoCat() {
		return chemoCat;
	}

	public void setChemoCat(String chemoCat) {
		this.chemoCat = chemoCat;
	}

	public String getDosedenseanthra() {
		return dosedenseanthra;
	}

	public void setDosedenseanthra(String dosedenseanthra) {
		this.dosedenseanthra = dosedenseanthra;
	}

	public String getDosedensetaxane() {
		return dosedensetaxane;
	}

	public void setDosedensetaxane(String dosedensetaxane) {
		this.dosedensetaxane = dosedensetaxane;
	}

	public String getLES_T1() {
		return LES_T1;
	}

	public void setLES_T1(String les_t1) {
		LES_T1 = les_t1;
	}

	public String getLES_T2() {
		return LES_T2;
	}

	public void setLES_T2(String les_t2) {
		LES_T2 = les_t2;
	}

	public String getLES_T3() {
		return LES_T3;
	}

	public void setLES_T3(String les_t3) {
		LES_T3 = les_t3;
	}

	public String getLES_T4() {
		return LES_T4;
	}

	public void setLES_T4(String les_t4) {
		LES_T4 = les_t4;
	}

	public Double getLdT1() {
		return ldT1;
	}

	public void setLdT1(Double ldT1) {
		this.ldT1 = ldT1;
	}

	public Double getLdT2() {
		return ldT2;
	}

	public void setLdT2(Double ldT2) {
		this.ldT2 = ldT2;
	}

	public Double getLdT3() {
		return ldT3;
	}

	public void setLdT3(Double ldT3) {
		this.ldT3 = ldT3;
	}

	public Double getLdT4() {
		return ldT4;
	}

	public void setLdT4(Double ldT4) {
		this.ldT4 = ldT4;
	}

	public Double getMriPctChangeT2_T3() {
		return mriPctChangeT2_T3;
	}

	public void setMriPctChangeT2_T3(Double mriPctChangeT2_T3) {
		this.mriPctChangeT2_T3 = mriPctChangeT2_T3;
	}

	public Double getMriPctChangeT2_T4() {
		return mriPctChangeT2_T4;
	}

	public void setMriPctChangeT2_T4(Double mriPctChangeT2_T4) {
		this.mriPctChangeT2_T4 = mriPctChangeT2_T4;
	}

	public Double getMriPctChangeT3_T4() {
		return mriPctChangeT3_T4;
	}

	public void setMriPctChangeT3_T4(Double mriPctChangeT3_T4) {
		this.mriPctChangeT3_T4 = mriPctChangeT3_T4;
	}

	public String getHistTypeOfInvasiveTumor() {
		return histTypeOfInvasiveTumor;
	}

	public void setHistTypeOfInvasiveTumor(String histTypeOfInvasiveTumor) {
		this.histTypeOfInvasiveTumor = histTypeOfInvasiveTumor;
	}

	public Double getPatientAge() {
		return patientAge;
	}

	public void setPatientAge(Double patientAge) {
		this.patientAge = patientAge;
	}

	public String getReasonNoSurg() {
		return reasonNoSurg;
	}

	public void setReasonNoSurg(String reasonNoSurg) {
		this.reasonNoSurg = reasonNoSurg;
	}
	
	public void setHistTypePS(String histTypePS) {
	    this.histTypePS = histTypePS;
	}

	public String getHistTypePS() {
		return histTypePS;
	}
	

}
