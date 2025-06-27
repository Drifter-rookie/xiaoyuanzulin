package com.idleItem.tradeSystem.service;

import com.idleItem.tradeSystem.entity.ReportOrder;
import com.idleItem.tradeSystem.vo.PageVo;

public interface ReportOrderService  {
    boolean addReportOrder(ReportOrder reportOrder);
    boolean updateReportOrderInfo(ReportOrder reportOrder);
    PageVo<ReportOrder> getReportOrderByStatus(int status, int page , int nums);
    boolean deleteReportOrder(long id);
}
