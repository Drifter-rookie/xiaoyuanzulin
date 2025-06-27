package com.idleItem.tradeSystem.mapper;

        import com.baomidou.mybatisplus.extension.api.R;
        import com.idleItem.tradeSystem.entity.ReportOrder;
        import org.apache.ibatis.annotations.Mapper;
        import org.apache.ibatis.annotations.Param;

        import java.util.List;

@Mapper

public interface ReportOrderMapper {
    int deleteByPrimaryKey(Long id);
    static int selectByPrimaryKey(String reportOrder) {
        return 0;
    }
    int insert(ReportOrder reportOrder);
    int insertSelective(ReportOrder reportOrder);
    int deleteByPrimaryKey(String reportOrder);
    List<ReportOrder> getNormalReportOrder(int begin, int nums);
    List<ReportOrder> getBanReportOrder(int begin, int nums);
    int countNormalReportOrder();
    int countBanReportOrder();

    int updateByPrimaryKeySelective(ReportOrder record);
    int updateByPrimaryKey(ReportOrder record);
}

