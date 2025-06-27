package com.idleItem.tradeSystem.service.impl;

import com.idleItem.tradeSystem.entity.ReportUser;
import com.idleItem.tradeSystem.mapper.ReportUserMapper;
import com.idleItem.tradeSystem.service.ReportUserService;
import com.idleItem.tradeSystem.vo.PageVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ReportUserServiceImpl implements ReportUserService {
    @Resource
    private ReportUserMapper ReportUserMapper;
    public boolean addReportUser(ReportUser reportUser) {
        return ReportUserMapper.insert(reportUser) == 1;
    }
    public boolean updateReportUserInfo(ReportUser reportUserModel){
        return ReportUserMapper.updateByPrimaryKeySelective(reportUserModel)==1;
    }
    public PageVo<ReportUser> getReportUserByStatus(int status, int page , int nums){
        List<ReportUser> list;
        int count=0;
        if(status==0){
            count=ReportUserMapper.countNormalReportUser();
            list=ReportUserMapper.getNormalReportUser((page-1)*nums, nums);
        }else {
            count=ReportUserMapper.countBanReportUser();
            list=ReportUserMapper.getBanReportUser((page-1)*nums, nums);
        }
        return new PageVo<>(list,count);
    }
    public boolean deleteReportUser(long id){
        return ReportUserMapper.deleteByPrimaryKey(id)==1;
    }
}