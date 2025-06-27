package com.idleItem.tradeSystem.entity;

import lombok.Data;
import lombok.Getter;

import java.io.Serializable;
import java.util.Date;

/**
 * sh_user
 * @author myl
 */
@Data
public class User implements Serializable {
    /**
     * 自增主键
     */
    private Long id;
    /**
     * 账号（手机号）
     */
    private String accountNumber;
    /**
     * 登录密码
     */
    private String userPassword;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 性别
     */
    private String sex;
    /**
     * 年龄
     */
    private Integer age;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 所在校区
     */
    private String campus;
    /**
     * 注册时间
     */
    private Date signInTime;
    /**
     * 用户状态
     */
    private Byte userStatus;

    @Getter
    private Byte userMessageStatus;


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
        User other = (User) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getAccountNumber() == null ? other.getAccountNumber() == null : this.getAccountNumber().equals(other.getAccountNumber()))
                && (this.getUserPassword() == null ? other.getUserPassword() == null : this.getUserPassword().equals(other.getUserPassword()))
                && (this.getNickname() == null ? other.getNickname() == null : this.getNickname().equals(other.getNickname()))
                && (this.getAvatar() == null ? other.getAvatar() == null : this.getAvatar().equals(other.getAvatar()))
                && (this.getSex() == null ? other.getSex() == null : this.getSex().equals(other.getSex()))
                && (this.getAge() == null ? other.getAge() == null : this.getAge().equals(other.getAge()))
                && (this.getPhone() == null ? other.getPhone() == null : this.getPhone().equals(other.getPhone()))
                && (this.getCampus() == null ? other.getCampus() == null : this.getCampus().equals(other.getCampus()))
                && (this.getSignInTime() == null ? other.getSignInTime() == null : this.getSignInTime().equals(other.getSignInTime()))
                && (this.getUserMessageStatus() == null ? other.getUserMessageStatus()  == null : this.getUserMessageStatus().equals(other.getUserMessageStatus()))
                &&(this.getPicture() == null ? other.getPicture() == null : this.getPicture().equals(other.getPicture()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getAccountNumber() == null) ? 0 : getAccountNumber().hashCode());
        result = prime * result + ((getUserPassword() == null) ? 0 : getUserPassword().hashCode());
        result = prime * result + ((getNickname() == null) ? 0 : getNickname().hashCode());
        result = prime * result + ((getAvatar() == null) ? 0 : getAvatar().hashCode());
        result = prime * result + ((getSex() == null) ? 0 : getSex().hashCode());
        result = prime * result + ((getAge() == null) ? 0 : getAge().hashCode());
        result = prime * result + ((getPhone() == null) ? 0 : getPhone().hashCode());
        result = prime * result + ((getCampus() == null) ? 0 : getCampus().hashCode());
        result = prime * result + ((getSignInTime() == null) ? 0 : getSignInTime().hashCode());
        result = prime * result + ((getUserMessageStatus() == null) ? 0 : getUserMessageStatus().hashCode());
        result = prime * result + (((getPicture()) == null) ? 0 : getPicture().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", accountNumber=").append(accountNumber);
        sb.append(", userPassword=").append(userPassword);
        sb.append(", nickname=").append(nickname);
        sb.append(", avatar=").append(avatar);
        sb.append(", sex=").append(sex);
        sb.append(", age=").append(age);
        sb.append(", phone=").append(phone);
        sb.append(", campus=").append(campus);
        sb.append(", signInTime=").append(signInTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append(", userMessageStatus=").append(userMessageStatus);
        sb.append(", picture=").append(picture);
        sb.append("]");
        return sb.toString();
    }
}