package com.idleItem.tradeSystem.service.impl;

import com.idleItem.tradeSystem.entity.ReportItem;
import com.idleItem.tradeSystem.mapper.ReportItemMapper;
import com.idleItem.tradeSystem.service.ReportItemService;
import com.idleItem.tradeSystem.vo.PageVo;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

@Service
public class ReportItemServiceImpl implements ReportItemService {

    @Resource
    private ReportItemMapper ReportItemMapper;

    public boolean addReportItem(ReportItem reportItem) {
        return ReportItemMapper.insert(reportItem) == 1;
    }

    public boolean updateReportIdleInfo(ReportItem reportIdleModel){
        return ReportItemMapper.updateByPrimaryKeySelective(reportIdleModel)==1;
    }
    public PageVo<ReportItem> getReportItemByStatus(int status, int page, int nums) {
        List<ReportItem> list;
        int count=0;
        if(status==0){
            count=ReportItemMapper.countNormalReportItem();
            list=ReportItemMapper.getNormalReportItem((page-1)*nums, nums);
        }else {
            count=ReportItemMapper.countBanReportItem();
            list=ReportItemMapper.getBanReportItem((page-1)*nums, nums);
        }
        return new PageVo<>(list,count);
    }
    public boolean deleteReportItem(long id){
        return ReportItemMapper.deleteByPrimaryKey(id)==1;
    }
}