package com.idleItem.tradeSystem.entity;

import lombok.Data;
import java.io.Serializable;
import java.util.Date;

@Data
public class ReportItem implements Serializable{
    private Long id;
    private Byte handleStatus;
    /**
     * 举报物名称
     */
    private String idleName;
    /**
     * 所属用户的帐号姓名
     */
    private String idleUser;
    /**
     * 举报用户的帐号
     */
    private String reporterId;
    /**
     * 举报原因
     */
    private String reportLabel;
    /**
     * 举报详细原因
     */
    private String reportDetails;
    /**
     * 图片
     */
    private String pictureList;
    /**
     * 举报时间
     */
    private Date reportTime;

    private static final long serialVersionUID = 1L;
    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        ReportItem other = (ReportItem) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getIdleName() == null ? other.getIdleName() == null : this.getIdleName().equals(other.getIdleName()))
                && (this.getReportDetails() == null ? other.getReportDetails() == null : this.getReportDetails().equals(other.getReportDetails()))
                && (this.getPictureList() == null ? other.getPictureList() == null : this.getPictureList().equals(other.getPictureList()))
                && (this.getReporterId() == null ? other.getReporterId() == null : this.getReporterId().equals(other.getReporterId()))
                && (this.getIdleUser() == null ? other.getIdleUser() == null : this.getIdleUser().equals(other.getIdleUser()))
                && (this.getReportLabel() == null ? other.getReportLabel() == null : this.getReportLabel().equals(other.getReportLabel()))
                && (this.getReportTime() == null ? other.getReportTime() == null : this.getReportTime().equals(other.getReportTime()))
                && (this.getHandleStatus() == null ? other.getHandleStatus() == null : this.getHandleStatus().equals(other.getHandleStatus()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getIdleName() == null) ? 0 : getIdleName().hashCode());
        result = prime * result + ((getReporterId() == null) ? 0 : getReporterId().hashCode());
        result = prime * result + ((getReportDetails() == null) ? 0 : getReportDetails().hashCode());
        result = prime * result + ((getPictureList() == null) ? 0 : getPictureList().hashCode());
        result = prime * result + ((getHandleStatus()== null) ? 0 : getHandleStatus().hashCode());
        result = prime * result + ((getReportLabel() == null) ? 0 : getReportLabel().hashCode());
        result = prime * result + ((getIdleUser() == null) ? 0 : getIdleUser().hashCode());
        result = prime * result + ((getReportTime() == null) ? 0 : getReportTime().hashCode());
        return result;
    }



    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", idleName=").append(idleName);
        sb.append(", reportDetails=").append(reportDetails);
        sb.append(", pictureList=").append(pictureList);
        sb.append(", reportLabel=").append(reportLabel);
        sb.append(", idleUser=").append(idleUser);
        sb.append(", reporterId=").append(reporterId);
        sb.append(", handleStatus=").append(handleStatus);
        sb.append(", reportTime=").append(reportTime);
        sb.append("]");
        return sb.toString();
    }
}

