package com.idleItem.tradeSystem.service.impl;


import com.idleItem.tradeSystem.entity.OrderAddress;
import com.idleItem.tradeSystem.mapper.OrderAddressMapper;
import com.idleItem.tradeSystem.service.OrderAddressService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class OrderAddressServiceImpl implements OrderAddressService {
    @Resource
    private OrderAddressMapper orderAddressMapper;
    public boolean addOrderAddress(OrderAddress orderAddress){
        return orderAddressMapper.insert(orderAddress)==1;
    }
    public boolean updateOrderAddress(OrderAddress orderAddressModel){
        orderAddressModel.setOrderId(null);
        return orderAddressMapper.updateByPrimaryKeySelective(orderAddressModel)==1;
    }
    public OrderAddress getOrderAddress(Long orderId){
        return orderAddressMapper.selectByOrderId(orderId);
    }
}
