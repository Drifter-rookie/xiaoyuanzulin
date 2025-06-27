package com.idleItem.tradeSystem.service;

import com.idleItem.tradeSystem.entity.User;
import com.idleItem.tradeSystem.vo.PageVo;

import java.util.List;

public interface UserService {
    User getUser(Long id);
    User userLogin(String accountNumber, String userPassword);
    boolean userSignIn(User user);
    boolean updateUserInfo(User user);
    boolean updatePassword(String newPassword, String oldPassword,Long id);
    boolean updatemessagestatus(long id);
    boolean updatemessagestatus1(long id);
    PageVo<User> getUserByStatus(int status, int page , int nums);
}
