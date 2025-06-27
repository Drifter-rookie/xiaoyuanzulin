package com.idleItem.tradeSystem.service;

import com.idleItem.tradeSystem.entity.ReportUser;
import com.idleItem.tradeSystem.entity.User;
import com.idleItem.tradeSystem.vo.PageVo;

public interface ReportUserService {
    boolean addReportUser(ReportUser reportUser);
    boolean updateReportUserInfo(ReportUser reportUser);
    PageVo<ReportUser> getReportUserByStatus(int status, int page , int nums);
    boolean deleteReportUser(long id);
}