package com.idleItem.tradeSystem.recommend.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.idleItem.tradeSystem.entity.IdleItem;
import com.idleItem.tradeSystem.recommend.dto.RelateDTO;
import com.idleItem.tradeSystem.recommend.dto.ProductDTO;
import com.idleItem.tradeSystem.vo.PageVo;

import java.util.List;

public interface RecommendService {

    List<ProductDTO> getProductData();

    List<RelateDTO> getRelateData();

    PageVo<IdleItem> recommendGoods(Long userId, Integer num);
}