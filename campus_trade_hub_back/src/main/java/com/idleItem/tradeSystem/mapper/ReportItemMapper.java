package com.idleItem.tradeSystem.mapper;
import com.idleItem.tradeSystem.entity.ReportItem;
import com.idleItem.tradeSystem.entity.ReportUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper

public interface ReportItemMapper {
    int deleteByPrimaryKey(Long id);

    static int selectByPrimaryKey(String reportItem) {
        return 0;
    }

    int insert(ReportItem reportItem);
    int insertSelective(ReportItem reportItem);
    int deleteByPrimaryKey(String reportItem);
    List<ReportItem> getNormalReportItem(int begin, int nums);

    List<ReportItem> getBanReportItem(int begin, int nums);

    int countNormalReportItem();

    int countBanReportItem();

    int updateByPrimaryKeySelective(ReportItem record);
    int updateByPrimaryKey(ReportItem record);

}
