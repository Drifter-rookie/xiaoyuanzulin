package com.idleItem.tradeSystem.controller;

import com.idleItem.tradeSystem.enums.ErrorMsg;
import com.idleItem.tradeSystem.utils.RedisUtil;
import com.idleItem.tradeSystem.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 缓存管理控制器
 */
@RestController
@RequestMapping("/cache")
@CrossOrigin
public class CacheController {

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 清除指定前缀的所有缓存
     * @param prefix 缓存键前缀
     * @return 结果
     */
    @GetMapping("/clear/{prefix}")
    public ResultVo clearCache(@PathVariable String prefix) {
        try {
            // 获取所有键
            Set<String> keys = redisUtil.keys(prefix + "*");
            
            if (keys != null && !keys.isEmpty()) {
                // 删除匹配的键
                redisUtil.del(keys.toArray(new String[0]));
                
                Map<String, Object> result = new HashMap<>();
                result.put("clearedKeys", keys);
                result.put("count", keys.size());
                
                return ResultVo.success(result);
            } else {
                return ResultVo.success("没有找到匹配的缓存键");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVo.fail(ErrorMsg.SYSTEM_ERROR);
        }
    }

    /**
     * 获取所有缓存键
     * @return 缓存键列表
     */
    @GetMapping("/keys")
    public ResultVo getAllKeys() {
        try {
            Set<String> keys = redisUtil.keys("*");
            return ResultVo.success(keys);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVo.fail(ErrorMsg.SYSTEM_ERROR);
        }
    }
    
    /**
     * 清除热门商品缓存
     * @return 结果
     */
    @GetMapping("/clear-hot-items")
    public ResultVo clearHotItemsCache() {
        return clearCache("hot_items_");
    }
} 