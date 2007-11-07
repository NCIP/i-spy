package gov.nih.nci.ispy.dto.query;

import gov.nih.nci.caintegrator.dto.query.ClinicalQueryDTO;
import gov.nih.nci.caintegrator.enumeration.Operator;
import gov.nih.nci.ispy.service.clinical.AgeCategoryType;
import gov.nih.nci.ispy.service.clinical.ClinicalResponseType;
import gov.nih.nci.ispy.service.clinical.ClinicalStageType;
import gov.nih.nci.ispy.service.clinical.ERstatusType;
import gov.nih.nci.ispy.service.clinical.HER2statusType;
import gov.nih.nci.ispy.service.clinical.MorphologyType;
import gov.nih.nci.ispy.service.clinical.NeoAdjuvantChemoRegimenType;
import gov.nih.nci.ispy.service.clinical.PRstatusType;
import gov.nih.nci.ispy.service.clinical.PcrType;
import gov.nih.nci.ispy.service.clinical.PercentLDChangeType;
import gov.nih.nci.ispy.service.clinical.RaceType;
import gov.nih.nci.ispy.service.common.TimepointType;

import java.util.EnumSet;
import java.util.Set;

public class ISPYclinicalDataQueryDTO implements ClinicalQueryDTO {
	
	private static final long serialVersionUID = 1L;

	private String queryName;
    
    private boolean isBaseline = false;
    
    private Set<String> restrainingSamples;      
	
	private EnumSet<TimepointType> timepointValues = EnumSet.noneOf(TimepointType.class);
	
	private EnumSet<ClinicalResponseType> clinicalResponseValues = null;
	
	private EnumSet<ClinicalStageType> clinicalStageValues = null;
	
	private PercentLDChangeType percentLDChangeType = null;
    
    private EnumSet<ERstatusType> erStatusValues = null;
    
    private EnumSet<NeoAdjuvantChemoRegimenType> agentValues = null;
	
	private EnumSet<HER2statusType> her2StatusValues = null;
	
	private EnumSet<PRstatusType> prStatusValues = null;
	
	private EnumSet<AgeCategoryType> ageCategoryValues = null;
    
    private EnumSet<MorphologyType> morphologyValues = null;
	
	private EnumSet<RaceType> raceValues = null;
    
    private Operator diameterOperator;    
    private Double diameter = null;
    
    private Operator pathTumorSizeOperator;
    private Double pathTumorSize = null;
    
    // RCB index info
    
    private Operator rcbOperator;
    private Double rcbSize = null;
    
    // pathology complete response
    private EnumSet<PcrType> pcrValues = null;   
    
    
    private boolean returnAll = false;
//    private String[] morphology;
    
//    private String ldLengthOperator;
//    private double ldLength;
    
    private Operator ldPercentChangeOperator;
    private Double ldPercentChange = null;    
    
	
	
	public ISPYclinicalDataQueryDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	public Set<ClinicalResponseType> getClinicalResponseValues() {
		return clinicalResponseValues;
	}


	public void setClinicalResponseValues(
			EnumSet<ClinicalResponseType> clinicalResponseValues) {
		this.clinicalResponseValues = clinicalResponseValues;
	}


	public Set<ClinicalStageType> getClinicalStageValues() {
		return clinicalStageValues;
	}


	public void setClinicalStageValues(EnumSet<ClinicalStageType> clinicalStageValues) {
		this.clinicalStageValues = clinicalStageValues;
	}


	public Set<ERstatusType> getErStatusValues() {
		return erStatusValues;
	}


	public void setErStatusValues(EnumSet<ERstatusType> erStatusValues) {
		this.erStatusValues = erStatusValues;
	}


	public Set<HER2statusType> getHer2StatusValues() {
		return her2StatusValues;
	}


	public void setHer2StatusValues(EnumSet<HER2statusType> her2StatusValues) {
		this.her2StatusValues = her2StatusValues;
	}


	public Set<PRstatusType> getPrStatusValues() {
		return prStatusValues;
	}


	public void setPrStatusValues(EnumSet<PRstatusType> prStatusValues) {
		this.prStatusValues = prStatusValues;
	}


	public Set<TimepointType> getTimepointValues() {
		return timepointValues;
	}


	public void setTimepointValues(EnumSet<TimepointType> timepointValues) {
		this.timepointValues = timepointValues;
	}


    /**
     * @return Returns the isBaseline.
     */
    public boolean isBaseline() {
        return isBaseline;
    }


    /**
     * @param isBaseline The isBaseline to set.
     */
    public void setBaseline(boolean isBaseline) {
        this.isBaseline = isBaseline;
    }


    /**
     * @return Returns the diameter.
     */
    public double getDiameter() {
        return diameter;
    }


    /**
     * @param diameter The diameter to set.
     */
    public void setDiameter(double diameter) {
        this.diameter = diameter;
    }


    /**
     * @return Returns the diameterOperator.
     */
    public Operator getDiameterOperator() {
        return diameterOperator;
    }


    /**
     * @param diameterOperator The diameterOperator to set.
     */
    public void setDiameterOperator(Operator diameterOperator) {
        this.diameterOperator = diameterOperator;
    }


//    /**
//     * @return Returns the ldLength.
//     */
//    public double getLdLength() {
//        return ldLength;
//    }
//
//
//    /**
//     * @param ldLength The ldLength to set.
//     */
//    public void setLdLength(double ldLength) {
//        this.ldLength = ldLength;
//    }


//    /**
//     * @return Returns the ldLengthOperator.
//     */
//    public String getLdLengthOperator() {
//        return ldLengthOperator;
//    }
//
//
//    /**
//     * @param ldLengthOperator The ldLengthOperator to set.
//     */
//    public void setLdLengthOperator(String ldLengthOperator) {
//        this.ldLengthOperator = ldLengthOperator;
//    }


    /**
     * @return Returns the ldPercentChange.
     */
    public double getLdPercentChange() {
        return ldPercentChange;
    }


    /**
     * @param ldPercentChange The ldPercentChange to set.
     */
    public void setLdPercentChange(double ldPercentChange) {
        this.ldPercentChange = ldPercentChange;
    }


    /**
     * @return Returns the ldPercentChangeOperator.
     */
    public Operator getLdPercentChangeOperator() {
        return ldPercentChangeOperator;
    }


    /**
     * @param ldPercentChangeOperator The ldPercentChangeOperator to set.
     */
    public void setLdPercentChangeOperator(Operator ldPercentChangeOperator) {
        this.ldPercentChangeOperator = ldPercentChangeOperator;
    }


    /**
     * @return Returns the pathTumorSizeOperator.
     */
    public Operator getPathTumorSizeOperator() {
        return pathTumorSizeOperator;
    }


    /**
     * @param pathTumorSizeOperator The pathTumorSizeOperator to set.
     */
    public void setPathTumorSizeOperator(Operator microOperator) {
        this.pathTumorSizeOperator = microOperator;
    }


    /**
     * @return Returns the pathTumorSize.
     */
    public Double getPathTumorSize() {
        return pathTumorSize;
    }


    /**
     * @param pathTumorSize The pathTumorSize to set.
     */
    public void setPathTumorSize(Double microSize) {
        this.pathTumorSize = microSize;
    }

    
    
    
    public Operator getRcbOperator() {
		return rcbOperator;
	}


	public void setRcbOperator(Operator rcbOperator) {
		this.rcbOperator = rcbOperator;
	}


	public Double getRcbSize() {
		return rcbSize;
	}


	public void setRcbSize(Double rcbSize) {
		this.rcbSize = rcbSize;
	}


	public EnumSet<PcrType> getPcrValues() {
		return pcrValues;
	}


	public void setPcrValues(EnumSet<PcrType> pcrValues) {
		this.pcrValues = pcrValues;
	}


	/**
     * @return Returns the morphologyValues.
     */
    public EnumSet<MorphologyType> getMorphologyValues() {
        return morphologyValues;
    }


    /**
     * @param morphologyValues The morphologyValues to set.
     */
    public void setMorphologyValues(EnumSet<MorphologyType> morphologyValues) {
        this.morphologyValues = morphologyValues;
    }


    /**
     * @return Returns the restrainingSamples.
     */
    public Set<String> getRestrainingSamples() {
        return restrainingSamples;
    }


    /**
     * @param restrainingSamples The restrainingSamples to set.
     */
    public void setRestrainingSamples(Set<String> restrainingSamples) {
        this.restrainingSamples = restrainingSamples;
    }


    public void setQueryName(String name) {
        this.queryName = name;
        
    }


    public String getQueryName() {
        // TODO Auto-generated method stub
        return this.queryName;
    }


    /**
     * @return Returns the agentValues.
     */
    public EnumSet<NeoAdjuvantChemoRegimenType> getAgentValues() {
        return agentValues;
    }


    /**
     * @param agentValues The agentValues to set.
     */
    public void setAgentValues(EnumSet<NeoAdjuvantChemoRegimenType> agentValues) {
        this.agentValues = agentValues;
    }


    /**
     * @return Returns the percentLDChangeType.
     */
    public PercentLDChangeType getPercentLDChangeType() {
        return percentLDChangeType;
    }


    /**
     * @param percentLDChangeType The percentLDChangeType to set.
     */
    public void setPercentLDChangeType(
            PercentLDChangeType percentLDChangeType) {
        this.percentLDChangeType = percentLDChangeType;
    }


	public EnumSet<AgeCategoryType> getAgeCategoryValues() {
		return ageCategoryValues;
	}


	public void setAgeCategoryValues(EnumSet<AgeCategoryType> ageCategoryValues) {
		this.ageCategoryValues = ageCategoryValues;
	}
	
	public EnumSet<RaceType> getRaceValues() {
		return raceValues;
	}


	public void setRaceValues(EnumSet<RaceType> raceValues) {
		this.raceValues = raceValues;
	}


	public boolean isReturnAll() {
		return returnAll;
	}


	public void setReturnAll(boolean returnAll) {
		this.returnAll = returnAll;
	}

}
