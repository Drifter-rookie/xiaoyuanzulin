package com.idleItem.tradeSystem.controller;

import com.idleItem.tradeSystem.entity.IdleItem;
import com.idleItem.tradeSystem.enums.ErrorMsg;
import com.idleItem.tradeSystem.service.IdleItemService;
import com.idleItem.tradeSystem.utils.RedisUtil;
import com.idleItem.tradeSystem.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.idleItem.tradeSystem.recommend.service.RecommendService;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

@CrossOrigin
@RestController
@RequestMapping("idle")
public class IdleItemController {
    @Autowired
    private RecommendService recommendService;

    @Autowired
    private IdleItemService idleItemService;
    
    @Autowired
    private RedisUtil redisUtil;
    
    // Redis缓存键前缀
    private static final String HOT_ITEMS_CACHE_KEY = "hot_items_";

    @PostMapping("add")
    public ResultVo addIdleItem(@CookieValue("shUserId")
                                @NotNull(message = "登录异常 请重新登录")
                                @NotEmpty(message = "登录异常 请重新登录") String shUserId,
                                @RequestBody IdleItem idleItem){
        idleItem.setUserId(Long.valueOf(shUserId));
        idleItem.setIdleStatus((byte) 1);
        idleItem.setReleaseTime(new Date());
        if(idleItemService.addIdleItem(idleItem)){
            // 添加商品后清除热门商品缓存
            clearHotItemsCache();
            return ResultVo.success(idleItem);
        }
        return ResultVo.fail(ErrorMsg.SYSTEM_ERROR);
    }

    @GetMapping("info")
    public ResultVo getIdleItem(@RequestParam Long id){
        return ResultVo.success(idleItemService.getIdleItem(id));
    }

    @GetMapping("all")
    public ResultVo getAllIdleItem(@CookieValue("shUserId")
                                   @NotNull(message = "登录异常 请重新登录")
                                   @NotEmpty(message = "登录异常 请重新登录") String shUserId){
        return ResultVo.success(idleItemService.getAllIdelItem(Long.valueOf(shUserId)));
    }
    @GetMapping("findrecommend")
    public ResultVo getRecommendItem(@CookieValue(name = "shUserId", required = false) String shUserId){
        // 如果shUserId为空或null，则使用0作为默认值
        Long userId = (shUserId == null || shUserId.isEmpty()) ? 0L : Long.valueOf(shUserId);

        // 调用recommendGoods方法并返回结果
        return ResultVo.success(recommendService.recommendGoods(userId, 8));

    }

    @GetMapping("find")
    public ResultVo findIdleItem(@RequestParam(value = "findValue",required = false) String findValue,
                                 @RequestParam(value = "attribute",required = false) Integer attribute,
                                 @RequestParam(value = "page",required = false) Integer page,
                                 @RequestParam(value = "nums",required = false) Integer nums){
        if(null==findValue){
            findValue="";
        }
        int p=1;
        int n=8;
        if(null!=page){
            p=page>0?page:1;
        }
        if(null!=nums){
            n=nums>0?nums:8;
        }
        return ResultVo.success(idleItemService.findIdleItem(findValue,attribute,p,n));
    }

    @GetMapping("label")
    public ResultVo findIdleItemByLabel(@RequestParam(value = "idleLabel",required = true) Integer idleLabel,
                                        @RequestParam(value = "attribute",required = false) Integer attribute,
                                        @RequestParam(value = "page",required = false) Integer page,
                                        @RequestParam(value = "nums",required = false) Integer nums){
        int p=1;
        int n=8;
        if(null!=page){
            p=page>0?page:1;
        }
        if(null!=nums){
            n=nums>0?nums:8;
        }
        return ResultVo.success(idleItemService.findIdleItemByLabel(idleLabel,attribute,p,n));
    }
    @GetMapping("findAll")
    public ResultVo findAllIdleItem(@RequestParam(value = "findValue",required = false) String findValue,
                                    @RequestParam(value = "page",required = false) Integer page,
                                    @RequestParam(value = "nums",required = false) Integer nums){
        if(null==findValue){
            findValue="";
        }
        int p=1;
        int n=8;
        if(null!=page){
            p=page>0?page:1;
        }
        if(null!=nums){
            n=nums>0?nums:8;
        }
        return ResultVo.success(idleItemService.findAllIdleItem(findValue,p,n));
    }
    //热门商品获取
    @GetMapping("findhot")
    public ResultVo findhotItem(){
        return ResultVo.success(idleItemService.findhotItem(8));
    }

    @GetMapping("labelAll")
    public ResultVo findAllIdleItemByLabel(@RequestParam(value = "idleLabel",required = true) Integer idleLabel,
                                           @RequestParam(value = "page",required = false) Integer page,
                                           @RequestParam(value = "nums",required = false) Integer nums){
        int p=1;
        int n=8;
        if(null!=page){
            p=page>0?page:1;
        }
        if(null!=nums){
            n=nums>0?nums:8;
        }
        return ResultVo.success(idleItemService.findAllIdleItemByLabel(idleLabel,p,n));
    }

    @PostMapping("update")
    public ResultVo updateIdleItem(@CookieValue("shUserId")
                                   @NotNull(message = "登录异常 请重新登录")
                                   @NotEmpty(message = "登录异常 请重新登录") String shUserId,
                                   @RequestBody IdleItem idleItem){
        idleItem.setUserId(Long.valueOf(shUserId));
        if(idleItemService.updateIdleItem(idleItem)){
            return ResultVo.success();
        }
        return ResultVo.fail(ErrorMsg.SYSTEM_ERROR);
    }

    /**
     * 手动清除热门商品缓存
     */
    @GetMapping("clear-hot-cache")
    public ResultVo clearHotItemsCache(){
        try {
            // 获取所有热门商品缓存键
            Set<String> keys = redisUtil.keys(HOT_ITEMS_CACHE_KEY + "*");
            
            if (keys != null && !keys.isEmpty()) {
                // 删除匹配的键
                for (String key : keys) {
                    redisUtil.del(key);
                }
                return ResultVo.success("已清除" + keys.size() + "个热门商品缓存");
            } else {
                return ResultVo.success("没有找到热门商品缓存");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVo.fail(ErrorMsg.SYSTEM_ERROR);
        }
    }
}