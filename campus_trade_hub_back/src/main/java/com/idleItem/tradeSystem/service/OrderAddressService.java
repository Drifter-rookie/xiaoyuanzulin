package com.idleItem.tradeSystem.service;

import com.idleItem.tradeSystem.entity.OrderAddress;

public interface OrderAddressService {
    boolean addOrderAddress(OrderAddress orderAddress);
    boolean updateOrderAddress(OrderAddress orderAddress);
    OrderAddress getOrderAddress(Long orderId);
}
