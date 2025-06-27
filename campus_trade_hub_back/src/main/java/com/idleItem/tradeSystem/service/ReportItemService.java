package com.idleItem.tradeSystem.service;

import com.idleItem.tradeSystem.entity.ReportItem;
import com.idleItem.tradeSystem.entity.ReportUser;
import com.idleItem.tradeSystem.vo.PageVo;

public interface ReportItemService {
    boolean addReportItem(ReportItem reportItem);
    PageVo<ReportItem> getReportItemByStatus(int status, int page , int nums);
    boolean updateReportIdleInfo(ReportItem reportItem);
    boolean deleteReportItem(long id);
}