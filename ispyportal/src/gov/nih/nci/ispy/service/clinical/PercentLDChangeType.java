package gov.nih.nci.ispy.service.clinical;

import java.io.Serializable;

public enum PercentLDChangeType implements Serializable {
  PERCENT_LD_CHANGE_T1_T2{ public String toString() { return "PERCENT LD CHANGE_T1_T2";}},
  PERCENT_LD_CHANGE_T1_T3{ public String toString() { return "PERCENT_LD_CHANGE_T1_T3";}},
  PERCENT_LD_CHANGE_T1_T4{ public String toString() { return "PERCENT_LD_CHANGE_T1_T4";}},
  PERCENT_LD_CHANGE_T2_T3{ public String toString() { return "PERCENT_LD_CHANGE_T2_T3";}},
  PERCENT_LD_CHANGE_T2_T4{ public String toString() { return "PERCENT_LD_CHANGE_T2_T4";}},
  PERCENT_LD_CHANGE_T3_T4{ public String toString() { return "PERCENT_LD_CHANGE_T3_T4";}}
}
