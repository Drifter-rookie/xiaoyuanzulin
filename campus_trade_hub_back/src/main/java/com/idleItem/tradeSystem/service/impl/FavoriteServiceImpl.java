package com.idleItem.tradeSystem.service.impl;

import com.idleItem.tradeSystem.entity.Favorite;
import com.idleItem.tradeSystem.entity.IdleItem;
import com.idleItem.tradeSystem.mapper.FavoriteMapper;
import com.idleItem.tradeSystem.mapper.IdleItemMapper;
import com.idleItem.tradeSystem.service.FavoriteService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FavoriteServiceImpl implements FavoriteService {

    @Resource
    private FavoriteMapper favoriteMapper;
    @Resource
    private IdleItemMapper idleItemMapper;
    public boolean addFavorite(Favorite favorite){
        return favoriteMapper.insert(favorite)==1;
    }
    public boolean deleteFavorite(Long id){
        return favoriteMapper.deleteByPrimaryKey(id)==1;
    }
    public Integer isFavorite(Long userId,Long idleId){
        return favoriteMapper.checkFavorite(userId,idleId);
    }
    public List<Favorite> getAllFavorite(Long userId){
        List<Favorite> list=favoriteMapper.getMyFavorite(userId);
        if(list.size()>0){
            List<Long> idleIdList=new ArrayList<>();
            for(Favorite i:list){
                idleIdList.add(i.getIdleId());
            }
            List<IdleItem> idleItemModelList=idleItemMapper.findIdleByList(idleIdList);
            Map<Long, IdleItem> map=new HashMap<>();
            for(IdleItem idle:idleItemModelList){
                map.put(idle.getId(),idle);
            }
            for(Favorite i:list){
                i.setIdleItem(map.get(i.getIdleId()));
            }
        }
        return list;
    }
}
