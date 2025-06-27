package com.idleItem.tradeSystem.service;

import com.idleItem.tradeSystem.entity.ShoppingCart;

import java.util.List;

public interface ShoppingCartService {
    boolean addShoppingCart(ShoppingCart shoppingCart);
    boolean deleteShoppingCart(Long id);
    Integer isShoppingCart(Long userId,Long idleId);
    List<ShoppingCart> getAllShoppingCart(Long userId);
}
