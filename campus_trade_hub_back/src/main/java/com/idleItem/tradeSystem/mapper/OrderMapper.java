package com.idleItem.tradeSystem.mapper;

import com.idleItem.tradeSystem.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Long id);

    List<Order> getMyOrder(Long userId);

    List<Order> getAllOrder(int begin, int nums);

    int countAllOrder();
    int countmyOrders(long id);

    List<Order> findOrderByIdleIdList(List<Long> idleIdList);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);
    int updatestatus(@Param("id") Long id,@Param("userid") Long userid);
    int updatestatus1(@Param("id") Long id);
}