package com.idleItem.tradeSystem.mapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.idleItem.tradeSystem.entity.ReportUser;
import com.idleItem.tradeSystem.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper

public interface ReportUserMapper {
    int deleteByPrimaryKey(Long id);


    static int selectByPrimaryKey(String reportUser) {
        return 0;
    }

    int insert(ReportUser reportUser);
    int insertSelective(ReportUser reportUser);
    int deleteByPrimaryKey(String reportUser);
    List<ReportUser> getNormalReportUser(int begin, int nums);

    List<ReportUser> getBanReportUser(int begin, int nums);

    int countNormalReportUser();

    int countBanReportUser();

    int updateByPrimaryKeySelective(ReportUser record);
    int updateByPrimaryKey(ReportUser record);
}
