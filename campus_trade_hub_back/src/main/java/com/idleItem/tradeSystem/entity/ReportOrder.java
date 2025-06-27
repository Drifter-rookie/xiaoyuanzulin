package com.idleItem.tradeSystem.entity;

import lombok.Data;
import java.io.Serializable;
import java.util.Date;

@Data
public class ReportOrder implements Serializable {
    private Long id;
    private Byte handleStatus;
    /**
     * 被举报订单编号
     */
    private String orderId;
    /**
     * 被举报原因
     */
    private String reportReason;
    /**
     * 举报者的帐号
     */
    private String reporterId;
    /**
     * 交易状态
     */
    private String tranStatus;
    /**
     * 期待处理方式
     */
    private Byte dealWay;
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
        ReportOrder other =(ReportOrder) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getOrderId() == null ? other.getOrderId() == null : this.getOrderId().equals(other.getOrderId()))
                && (this.getReportReason() == null ? other.getReportReason() == null : this.getReportReason().equals(other.getReportReason()))
                && (this.getReporterId() == null ? other.getReporterId() == null : this.getReporterId().equals(other.getReporterId()))
                && (this.getTranStatus() == null ? other.getTranStatus() == null : this.getTranStatus().equals(other.getTranStatus()))
                && (this.getDealWay() == null ? other.getDealWay() == null : this.getDealWay().equals(other.getDealWay()))
                && (this.getReportDetails() == null ? other.getReportDetails() == null : this.getReportDetails().equals(other.getReportDetails()))
                && (this.getPictureList() == null ? other.getPictureList() == null : this.getPictureList().equals(other.getPictureList()))
                && (this.getReportTime() == null ? other.getReportTime() == null : this.getReportTime().equals(other.getReportTime()))
                && (this.getHandleStatus() == null ? other.getHandleStatus() == null : this.getHandleStatus().equals(other.getHandleStatus()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getOrderId() == null) ? 0 : getOrderId().hashCode());
        result = prime * result + ((getReportReason() == null) ? 0 : getReportReason().hashCode());
        result = prime * result + ((getReporterId() == null) ? 0 : getReporterId().hashCode());
        result = prime * result + ((getTranStatus() == null) ? 0 : getTranStatus().hashCode());
        result = prime * result + ((getDealWay() == null) ? 0 : getDealWay().hashCode());
        result = prime * result + ((getReportDetails() == null) ? 0 : getReportDetails().hashCode());
        result = prime * result + ((getPictureList() == null) ? 0 : getPictureList().hashCode());
        result = prime * result + ((getHandleStatus() == null) ? 0 : getHandleStatus().hashCode());
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
        sb.append(", orderId=").append(orderId);
        sb.append(", reportReason=").append(reportReason);
        sb.append(", reporterId=").append(reporterId);
        sb.append(", tranStatus=").append(tranStatus);
        sb.append(", dealWay=").append(dealWay);
        sb.append(", reportDetails=").append(reportDetails);
        sb.append(", pictureList=").append(pictureList);
        sb.append(", handleStatus=").append(handleStatus);
        sb.append(", reportTime=").append(reportTime);
        sb.append("]");
        return sb.toString();
    }
}



