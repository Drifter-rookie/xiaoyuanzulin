package com.idleItem.tradeSystem.mapper;

import com.idleItem.tradeSystem.entity.Notice;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface NoticeMapper {
    int insert(Notice record);

    int deleteByPrimaryKey(Long id);
    List<Notice> getAllNotice(int begin, int nums);
    List<Notice> getNotice();

    int getCount();
}
