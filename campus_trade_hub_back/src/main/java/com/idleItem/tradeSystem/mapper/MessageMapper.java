package com.idleItem.tradeSystem.mapper;

import com.idleItem.tradeSystem.entity.Message;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MessageMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Message record);

    int insertSelective(Message record);

    Message selectByPrimaryKey(Long id);

    List<Message> getMyMessage(Long userId);

    List<Message> getIdleMessage(Long idleId);

    int updateByPrimaryKeySelective(Message record);

    int updateByPrimaryKey(Message record);
    int updatestatus(@Param("id") Long id);
}