package com.idleItem.tradeSystem.mapper;

import com.idleItem.tradeSystem.entity.IdleItem;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IdleItemMapper {
    int deleteByPrimaryKey(Long id);

    int insert(IdleItem record);

    int insertSelective(IdleItem record);

    IdleItem selectByPrimaryKey(Long id);

    List<IdleItem> getAllIdleItem(Long userId);

    int countIdleItem(String findValue,int attribute);

    int countIdleItemByLabel(int idleLabel,int attribute);

    int countIdleItemByStatus(int status);

    List<IdleItem> findIdleItem(String findValue,int attribute,int begin, int nums);

    List<IdleItem> findIdleItemByLabel(int idleLabel,int attribute,int begin, int nums);

    List<IdleItem> findAllIdleItem(String findValue,int begin, int nums);

    List<IdleItem> findAllIdleItemByLabel(int idleLabel,int begin, int nums);

    List<IdleItem> getIdleItemByStatus(int status, int begin, int nums);

    int updateByPrimaryKeySelective(IdleItem record);

    int updateByPrimaryKey(IdleItem record);

    List<IdleItem> findIdleByList(List<Long> idList);

    List<IdleItem> selectGoodsListByIds(List<Long> goodIds);

    List<IdleItem> findAllIdleItem1();

    int countIdleItemByLabel(int idleLabel);

    List<IdleItem> findhotItem(int num);
}