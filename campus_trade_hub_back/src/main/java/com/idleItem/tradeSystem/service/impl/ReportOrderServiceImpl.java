package com.idleItem.tradeSystem.service.impl;

import com.idleItem.tradeSystem.entity.ReportOrder;
import com.idleItem.tradeSystem.mapper.ReportOrderMapper;
import com.idleItem.tradeSystem.service.ReportOrderService;
import com.idleItem.tradeSystem.vo.PageVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class ReportOrderServiceImpl implements ReportOrderService {
        @Resource
        private ReportOrderMapper ReportOrderMapper;
        public boolean addReportOrder(ReportOrder reportOrder) {
            return ReportOrderMapper.insert(reportOrder) == 1;
        }

        public boolean updateReportOrderInfo(ReportOrder reportOrderModel){
            return ReportOrderMapper.updateByPrimaryKeySelective(reportOrderModel)==1;
        }
        public PageVo<ReportOrder> getReportOrderByStatus(int status, int page , int nums){
            List<ReportOrder> list;
            int count=0;
            if(status==0){
                count=ReportOrderMapper.countNormalReportOrder();
                list=ReportOrderMapper.getNormalReportOrder((page-1)*nums, nums);
            }else {
                count=ReportOrderMapper.countBanReportOrder();
                list=ReportOrderMapper.getBanReportOrder((page-1)*nums, nums);
            }
            return new PageVo<>(list,count);
        }
    public boolean deleteReportOrder(long id){
        return ReportOrderMapper.deleteByPrimaryKey(id)==1;
    }
    }

