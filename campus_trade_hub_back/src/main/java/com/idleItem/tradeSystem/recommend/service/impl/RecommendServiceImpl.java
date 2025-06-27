package com.idleItem.tradeSystem.recommend.service.impl;

import com.idleItem.tradeSystem.entity.User;
import com.idleItem.tradeSystem.mapper.IdleItemMapper;
import com.idleItem.tradeSystem.mapper.FavoriteMapper;
import com.idleItem.tradeSystem.mapper.UserMapper;
import com.idleItem.tradeSystem.vo.PageVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.idleItem.tradeSystem.entity.IdleItem;
import com.idleItem.tradeSystem.entity.Favorite;
import com.idleItem.tradeSystem.recommend.core.ItemCF;
import com.idleItem.tradeSystem.recommend.dto.ProductDTO;
import com.idleItem.tradeSystem.recommend.dto.RelateDTO;
import com.idleItem.tradeSystem.recommend.service.RecommendService;
import com.idleItem.tradeSystem.service.IdleItemService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Slf4j
@Service
@AllArgsConstructor
public class RecommendServiceImpl implements RecommendService {
    @Resource
    private IdleItemMapper idleItemMapper;
    @Resource
    private FavoriteMapper favoriteMapper;
    @Resource
    private IdleItemService idleItemService;
    @Resource
    private UserMapper userMapper;
    /**
     * 获取商品表所有商品id
     * @return
     */
    @Override
    public List<ProductDTO> getProductData() {
        List<ProductDTO> productDTOList = new ArrayList<>();
        List<IdleItem> list=idleItemMapper.findAllIdleItem1();
        List<Long> idList=new ArrayList<>();
        for(IdleItem i:list)
            idList.add(i.getId());
        for (Long idlist : idList) {
            ProductDTO productDTO = new ProductDTO();
            productDTO.setProductId(idlist);
            productDTOList.add(productDTO);
        }
        return productDTOList;
    }

    /**
     * 根据所有收藏及加入购物车的数据生成物品相似度
     * @return List<RelateDTO>
     */
    @Override
    public List<RelateDTO> getRelateData() {
        List<RelateDTO> relateDTOList = new ArrayList<>();
        // 获取所有收藏数据集合
        List<Favorite> list1=favoriteMapper.selectAllFavorites();
        // 遍历物品，生成预处理数据
        for(Favorite i:list1){
            long CategoryId= idleItemMapper.selectByPrimaryKey(i.getIdleId()).getIdleLabel();
            long count=favoriteMapper.countFavoriteById(i.getIdleId());
            RelateDTO relateDTO = new RelateDTO();
            relateDTO.setUserId(i.getUserId());
            relateDTO.setProductId(i.getIdleId());
            relateDTO.setCategoryId(CategoryId);
            // 建立相似度
            relateDTO.setIndex((int) count);
            relateDTOList.add(relateDTO);
        }
        return relateDTOList;
    }

    @Override
    public PageVo<IdleItem> recommendGoods(Long userId, Integer num) {
        List<IdleItem> recommend;
        if (userId != 0) {
            List<IdleItem> recommendGoods = new ArrayList<>();
            // 获取商品数据
            List<RelateDTO> relateDTOList = getRelateData();
            List<Long> recommendIdLists = ItemCF.recommend(userId, num, relateDTOList);
            if (CollectionUtils.isNotEmpty(recommendIdLists)) {
                Map<Long, Integer> integerMap = IntStream.range(0, recommendIdLists.size()).boxed().collect(Collectors.toMap(recommendIdLists::get, i -> i));
                List<ProductDTO> productDTOList = getProductData().stream().filter(e -> recommendIdLists.contains(e.getProductId())).collect(Collectors.toList());
                System.out.println(productDTOList.size());
                List<Long> goodIds = productDTOList.stream().map(ProductDTO::getProductId).collect(Collectors.toList());
                System.out.println(goodIds.size());
                if (CollectionUtils.isNotEmpty(goodIds)) {
                    // 获取所有推荐商品
                    recommendGoods = idleItemMapper.selectGoodsListByIds(goodIds);
                    System.out.println(recommendGoods.size());
                    recommendGoods.sort(Comparator.comparingInt(o -> integerMap.get(o.getId())));
                }
            }
            recommend = recommendGoods.stream().limit(num).collect(Collectors.toList());
            if (recommend.size() < 8) {
                List<IdleItem> hot = idleItemMapper.findhotItem(8 - recommend.size());
                recommend.addAll(hot);
            }
        }
        else
        {
            recommend = idleItemMapper.findhotItem(8);
        }
        List<Long> idList = new ArrayList<>();
        for (IdleItem i : recommend) {
            idList.add(i.getUserId());
        }
        List<User> userList = userMapper.findUserByList(idList);
        Map<Long, User> map = new HashMap<>();
        for (User user : userList) {
            map.put(user.getId(), user);
        }
        for (IdleItem i : recommend) {
            i.setUser(map.get(i.getUserId()));
        }
        return new PageVo<>(recommend, 8);
    }
}