package com.idleItem.tradeSystem.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;


@Data
public class Message implements Serializable {
    /**
     * 自增主键
     */
    private Long id;
    /**
     * 用户主键id
     */
    private Long userId;

    private User fromU;
    /**
     * 闲置主键id
     */
    private Long idleId;

    private IdleItem idle;
    /**
     * 留言内容
     */
    private String content;
    /**
     * 留言时间
     */
    private Date createTime;
    /**
     * 所回复的用户
     */
    private Long toUser;

    private User toU;

    /**
     * 所回复的留言
     */
    private Long toMessage;

    private Message toM;

    private  Long status;
    private String picture;

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
        Message other = (Message) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getIdleId() == null ? other.getIdleId() == null : this.getIdleId().equals(other.getIdleId()))
            && (this.getContent() == null ? other.getContent() == null : this.getContent().equals(other.getContent()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getToUser() == null ? other.getToUser() == null : this.getToUser().equals(other.getToUser()))
            && (this.getToMessage() == null ? other.getToMessage() == null : this.getToMessage().equals(other.getToMessage()))
                && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
                && (this.getPicture() == null ? other.getPicture() == null : this.getPicture().equals(other.getPicture()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getIdleId() == null) ? 0 : getIdleId().hashCode());
        result = prime * result + ((getContent() == null) ? 0 : getContent().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getToUser() == null) ? 0 : getToUser().hashCode());
        result = prime * result + ((getToMessage() == null) ? 0 : getToMessage().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getPicture() == null) ? 0 : getPicture().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", idleId=").append(idleId);
        sb.append(", content=").append(content);
        sb.append(", createTime=").append(createTime);
        sb.append(", toUser=").append(toUser);
        sb.append(", toMessage=").append(toMessage);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append(", status=").append(status);
        sb.append(", picture=").append(picture);
        sb.append("]");
        return sb.toString();
    }
}