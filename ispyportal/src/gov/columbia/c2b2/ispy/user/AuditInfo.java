package gov.columbia.c2b2.ispy.user;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class AuditInfo implements Serializable {

    public static String FIELD_CREATEBY = "EduColumbiaC2b2IntragenDataAuditInfo_createBy";
    public static String FIELD_CREATEDATE = "EduColumbiaC2b2IntragenDataAuditInfo_createDate";
    public static String FIELD_UPDATEBY = "EduColumbiaC2b2IntragenDataAuditInfo_updateBy";
    public static String FIELD_UPDATEDATE = "EduColumbiaC2b2IntragenDataAuditInfo_updateDate";

    /** nullable persistent field */
    private String createBy;

    /** nullable persistent field */
    private java.util.Date createDate;

    /** nullable persistent field */
    private String updateBy;

    /** nullable persistent field */
    private java.util.Date updateDate;

    /** full constructor */
    public AuditInfo(java.lang.String createBy, java.util.Date createDate, java.lang.String updateBy, java.util.Date updateDate) {
        this.createBy = createBy;
        this.createDate = createDate;
        this.updateBy = updateBy;
        this.updateDate = updateDate;
    }

    /** default constructor */
    public AuditInfo() {
    }

    public java.lang.String getCreateBy() {
        return this.createBy;
    }

    public void setCreateBy(java.lang.String createBy) {
        this.createBy = createBy;
    }

    public java.util.Date getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(java.util.Date createDate) {
        this.createDate = createDate;
    }

    public java.lang.String getUpdateBy() {
        return this.updateBy;
    }

    public void setUpdateBy(java.lang.String updateBy) {
        this.updateBy = updateBy;
    }

    public java.util.Date getUpdateDate() {
        return this.updateDate;
    }

    public void setUpdateDate(java.util.Date updateDate) {
        this.updateDate = updateDate;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .toString();
    }

}
