package com.idleItem.tradeSystem.service;

import com.idleItem.tradeSystem.entity.IdleItem;
import com.idleItem.tradeSystem.vo.PageVo;

import java.util.List;

public interface IdleItemService {
    boolean addIdleItem(IdleItem idleItem);
    IdleItem getIdleItem(Long id);
    List<IdleItem> getAllIdelItem(Long userId);
    PageVo<IdleItem> findIdleItem(String findValue, int attribute,int page, int nums);
    PageVo<IdleItem> findIdleItemByLabel(int idleLabel, int attribute,int page, int nums);
    PageVo<IdleItem> findAllIdleItem(String findValue,int page, int nums);
    PageVo<IdleItem> findhotItem(int num);
    PageVo<IdleItem> findAllIdleItemByLabel(int idleLabel,int page, int nums);
    boolean updateIdleItem(IdleItem idleItemModel);
    PageVo<IdleItem> adminGetIdleList(int status, int page, int nums) ;
}