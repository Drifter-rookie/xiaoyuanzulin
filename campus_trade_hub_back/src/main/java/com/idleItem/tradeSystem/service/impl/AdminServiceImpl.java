package com.idleItem.tradeSystem.service.impl;

import com.idleItem.tradeSystem.entity.Admin;
import com.idleItem.tradeSystem.entity.User;
import com.idleItem.tradeSystem.mapper.AdminMapper;
import com.idleItem.tradeSystem.mapper.UserMapper;
import com.idleItem.tradeSystem.service.AdminService;
import com.idleItem.tradeSystem.vo.PageVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Resource
    private AdminMapper adminMapper;

    public Admin login(String accountNumber, String adminPassword){
        return adminMapper.login(accountNumber,adminPassword);
    }

    public PageVo<Admin> getAdminList(int page, int nums){
        List<Admin> list=adminMapper.getList((page-1)*nums,nums);
        int count=adminMapper.getCount();
        return new PageVo<>(list,count);
    }
    public Admin getAdmin(Long id){
        return adminMapper.selectByPrimaryKey(id);
    }

    public boolean addAdmin(Admin admin){
        return adminMapper.insert(admin)==1;
    }
    public boolean deleteAdmin(long id){return adminMapper.deleteByPrimaryKey(id)==1;
    }
}
