package com.idleItem.tradeSystem.recommend.core;

import com.google.common.collect.Lists;
import com.idleItem.tradeSystem.recommend.dto.RelateDTO;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.IntStream;

@Slf4j
public class CoreMath {
    /**
     * 计算相关系数并排序
     */
    public static Map<Double, Long> computeNeighbor(Long key, Map<Long, List<RelateDTO>> map, int type) {
        Map<Double, Long> distMap = new TreeMap<>();
        List<RelateDTO> items = map.get(key);
        map.forEach((k, v) -> {
            // 排除此用户
            if (!k.equals(key)) {
                // 关系系数
                double coefficient = relateDist(v, items, type);
                // 关系距离
                // double distance=Math.abs(coefficient);
                distMap.put(coefficient, k);
            }
        });
        return distMap;
    }
    /**
     * 计算两个序列间的相关系数
     */
    private static double relateDist(List<RelateDTO> xList, List<RelateDTO> yList, Integer type) {
        List<Integer> xs = Lists.newArrayList();
        List<Integer> ys = Lists.newArrayList();
        xList.forEach(x -> yList.forEach(y -> {
                // 基于物品推荐时如果两个用户id相同，则计算相似度
                if (x.getUserId().longValue() == y.getUserId().longValue()) {
                    xs.add(x.getIndex());
                    ys.add(y.getIndex());
                }
        }));
        if (ys.size() == 0 || xs.size() == 0) {
            return 0d;
        }
            return cosineSimilarity(xs, ys);
    }
    /**
     * 计算向量之间的余弦相似度
     */
    private static double cosineSimilarity(List<Integer> xs, List<Integer> ys) {
        double dotProduct = 0;
        double norm1 = 0;
        double norm2 = 0;
        for (int i = 0; i < xs.size(); i++) {
            Integer x = xs.get(i);
            Integer y = ys.get(i);
            dotProduct += x * y;
            norm1 += Math.pow(x, 2);
            norm2 += Math.pow(y, 2);
        }
        return dotProduct / (Math.sqrt(norm1) * Math.sqrt(norm2));
    }

}
