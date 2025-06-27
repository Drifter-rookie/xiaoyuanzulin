package com.idleItem.tradeSystem.service;


import com.idleItem.tradeSystem.entity.Admin;
import com.idleItem.tradeSystem.entity.User;
import com.idleItem.tradeSystem.vo.PageVo;

public interface AdminService {
    Admin getAdmin(Long id);
    Admin login(String accountNumber, String adminPassword);
    PageVo<Admin> getAdminList(int page, int nums);
    boolean addAdmin(Admin adminModel);
    boolean deleteAdmin(long id);
}
