package com.idleItem.tradeSystem.service.impl;

import com.google.gson.Gson;
import com.idleItem.tradeSystem.entity.User;
import com.idleItem.tradeSystem.mapper.UserMapper;
import com.idleItem.tradeSystem.service.UserService;
import com.idleItem.tradeSystem.vo.PageVo;
import io.goeasy.GoEasy;
import io.goeasy.publish.GoEasyError;
import io.goeasy.publish.PublishListener;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;



@Service
public class UserServiceImpl implements UserService {
    private final GoEasy goEasy=new GoEasy("https://rest-hz.goeasy.io", "BC-7302d47494b342f3bc28a08c7851e523");
    @Resource
    private UserMapper userMapper;
    public User getUser(Long id){
        return userMapper.selectByPrimaryKey(id);
    }
    public User userLogin(String accountNumber, String userPassword){
        return userMapper.userLogin(accountNumber,userPassword);
    }
    public boolean userSignIn(User userModel){
        return userMapper.insert(userModel) == 1;
    }
    public boolean updateUserInfo(User userModel){
        return userMapper.updateByPrimaryKeySelective(userModel)==1;
    }

    public boolean updatePassword(String newPassword, String oldPassword,Long id){
        return userMapper.updatePassword(newPassword,oldPassword,id)==1;
    }

    public boolean updatemessagestatus(long id){
        String message = String.valueOf(id);
        goEasy.publish("message_status_updates", message,new PublishListener(){});
        return userMapper.updatemessagestatus(id)==1;
    }
    public boolean updatemessagestatus1(long id){
        String message = String.valueOf(id);
        goEasy.publish("message_status_updates1", message,new PublishListener(){});
        return userMapper.updatemessagestatus1(id)==1;
    }

    public PageVo<User> getUserByStatus(int status, int page , int nums){
        List<User> list;
        int count=0;
        if(status==0){
            count=userMapper.countNormalUser();
            list=userMapper.getNormalUser((page-1)*nums, nums);
        }else {
            count=userMapper.countBanUser();
            list=userMapper.getBanUser((page-1)*nums, nums);
        }
        return new PageVo<>(list,count);
    }
}
