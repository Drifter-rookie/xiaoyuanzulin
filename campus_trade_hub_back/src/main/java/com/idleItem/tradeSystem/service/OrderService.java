package com.idleItem.tradeSystem.service;

import com.idleItem.tradeSystem.entity.Order;
import com.idleItem.tradeSystem.vo.PageVo;

import java.util.List;

public interface OrderService {
    boolean addOrder(Order order);
    Order getOrder(Long id);
    boolean updateOrder(Order order);
    List<Order> getMyOrder(Long userId);
    List<Order> getMySoldIdle(Long userId);
    PageVo<Order> getAllOrder(int page, int nums);
    boolean deleteOrder(long id);
    boolean updatestatus(long id,long userid);
    boolean updatestatus1(long id);
    int countmyorder(long id);
}
