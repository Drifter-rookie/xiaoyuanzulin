package com.idleItem.tradeSystem.service;

import com.idleItem.tradeSystem.entity.Favorite;

import java.util.List;

public interface FavoriteService {
    boolean addFavorite(Favorite favoriteModel);
    boolean deleteFavorite(Long id);
    Integer isFavorite(Long userId,Long idleId);
    List<Favorite> getAllFavorite(Long userId);
}
