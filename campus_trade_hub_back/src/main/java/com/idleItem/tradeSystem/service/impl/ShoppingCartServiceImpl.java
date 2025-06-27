package com.idleItem.tradeSystem.service.impl;

import com.idleItem.tradeSystem.entity.ShoppingCart;
import com.idleItem.tradeSystem.entity.IdleItem;
import com.idleItem.tradeSystem.mapper.IdleItemMapper;
import com.idleItem.tradeSystem.mapper.ShoppingCartMapper;
import com.idleItem.tradeSystem.service.ShoppingCartService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Resource
    private ShoppingCartMapper shoppingCartMapper;
    @Resource
    private IdleItemMapper idleItemMapper;

    public boolean addShoppingCart(ShoppingCart shoppingCart){
        return shoppingCartMapper.insert(shoppingCart)==1;
    }

    public boolean deleteShoppingCart(Long id){
        return shoppingCartMapper.deleteByPrimaryKey(id)==1;
    }

    public Integer isShoppingCart(Long userId,Long idleId){
        return shoppingCartMapper.checkShoppingCart(userId,idleId);
    }

    public List<ShoppingCart> getAllShoppingCart(Long userId){
        List<ShoppingCart> list=shoppingCartMapper.getMyShoppingCart(userId);
        if(list.size()>0){
            List<Long> idleIdList=new ArrayList<>();
            for(ShoppingCart i:list){
                idleIdList.add(i.getIdleId());
            }
            List<IdleItem> idleItemModelList=idleItemMapper.findIdleByList(idleIdList);
            Map<Long, IdleItem> map=new HashMap<>();
            for(IdleItem idle:idleItemModelList){
                map.put(idle.getId(),idle);
            }
            for(ShoppingCart i:list){
                i.setIdleItem(map.get(i.getIdleId()));
            }
        }
        return list;
    }
}
