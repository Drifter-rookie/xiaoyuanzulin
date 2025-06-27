package com.idleItem.tradeSystem.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Notice {
    /**
     * 自增主键
     */
    private Long id;
    /**
     * 公告类型
     */
    private String noticeType;
    /**
     * 公告标题或关键句
     */
    private String keySentence;

    /**
     * 公告内容
     */
    private String content;

    /**
     * 发布时间
     */
    private Date announceTime;

    /**
     * 管理员id
     */
    private Long adminId;

    /**
     * 管理员名字
     */
    private String adminName;

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
        Notice other = (Notice) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getKeySentence() == null ? other.getKeySentence() == null : this.getKeySentence().equals(other.getKeySentence()))
                && (this.getContent() == null ? other.getContent() == null : this.getContent().equals(other.getContent()))
                && (this.getAnnounceTime() == null ? other.getAnnounceTime() == null : this.getAnnounceTime().equals(other.getAnnounceTime()))
                && (this.getAdminId() == null ? other.getAdminId() == null : this.getAdminId().equals(other.getAdminId()))
                && (this.getAdminName() == null ? other.getAdminName() == null : this.getAdminName().equals(other.getAdminName()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getKeySentence() == null) ? 0 : getKeySentence().hashCode());
        result = prime * result + ((getContent() == null) ? 0 : getContent().hashCode());
        result = prime * result + ((getAnnounceTime() == null) ? 0 : getAnnounceTime().hashCode());
        result = prime * result + ((getAdminId() == null) ? 0 : getAdminId().hashCode());
        result = prime * result + ((getAdminName() == null) ? 0 : getAdminName().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", keySentence=").append(keySentence);
        sb.append(", content=").append(content);
        sb.append(", announceTime=").append(announceTime);
        sb.append(", adminId=").append(adminId);
        sb.append(", adminName=").append(adminName);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
