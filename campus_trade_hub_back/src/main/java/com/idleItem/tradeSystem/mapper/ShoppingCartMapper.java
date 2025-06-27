package com.idleItem.tradeSystem.mapper;

import com.idleItem.tradeSystem.entity.ShoppingCart;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface ShoppingCartMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ShoppingCart record);

    int insertSelective(ShoppingCart record);

    ShoppingCart selectByPrimaryKey(Long id);
    List<ShoppingCart> getMyShoppingCart(Long userId);

    Integer checkShoppingCart(Long userId,Long idleId);

    int updateByPrimaryKeySelective(ShoppingCart record);

    int updateByPrimaryKey(ShoppingCart record);
}
