package com.idleItem.tradeSystem.service.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.idleItem.tradeSystem.entity.IdleItem;
import com.idleItem.tradeSystem.entity.User;
import com.idleItem.tradeSystem.mapper.IdleItemMapper;
import com.idleItem.tradeSystem.mapper.UserMapper;
import com.idleItem.tradeSystem.service.IdleItemService;
import com.idleItem.tradeSystem.utils.RedisUtil;
import com.idleItem.tradeSystem.vo.PageVo;
import io.goeasy.GoEasy;
import io.goeasy.publish.GoEasyError;
import io.goeasy.publish.PublishListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class IdleItemServiceImpl implements IdleItemService {
    private final GoEasy goEasy=new GoEasy("https://rest-hz.goeasy.io", "BC-7302d47494b342f3bc28a08c7851e523");
    private final Gson gson=new Gson();
    @Resource
    private IdleItemMapper idleItemMapper;
    @Resource
    private UserMapper userMapper;
    @Autowired
    private RedisUtil redisUtil;
    // Redis缓存键
    private static final String HOT_ITEMS_CACHE_KEY = "hot_items_";
    // 缓存过期时间（秒）
    private static final long CACHE_EXPIRE_TIME = 3600; // 1小时
    public boolean addIdleItem(IdleItem idleItem) {
        String message = gson.toJson(idleItem);
        goEasy.publish("idle_add", message,new PublishListener(){
            @Override
            public void onSuccess() {
                System.out.println("Publish success.");
            }

            @Override
            public void onFailed(GoEasyError error) {
                System.out.println("Failed to Publish message, error:" + error.getCode() + " , " + error.getContent());
            }
        });
        // 添加新商品后，清除热门商品缓存，确保数据一致性
        clearHotItemsCache();
        return idleItemMapper.insert(idleItem) == 1;
    }
    public IdleItem getIdleItem(Long id) {
        IdleItem idleItem=idleItemMapper.selectByPrimaryKey(id);
        if(idleItem!=null){
            idleItem.setUser(userMapper.selectByPrimaryKey(idleItem.getUserId()));
        }
        return idleItem;
    }
    public List<IdleItem> getAllIdelItem(Long userId) {
        return idleItemMapper.getAllIdleItem(userId);
    }
    public PageVo<IdleItem> findIdleItem(String findValue, int attribute,int page, int nums) {
        List<IdleItem> list=idleItemMapper.findIdleItem(findValue, attribute,(page - 1) * nums, nums);
        if(list.size()>0){
            List<Long> idList=new ArrayList<>();
            for(IdleItem i:list){
                idList.add(i.getUserId());
            }
            List<User> userList=userMapper.findUserByList(idList);
            Map<Long,User> map=new HashMap<>();
            for(User user:userList){
                map.put(user.getId(),user);
            }
            for(IdleItem i:list){
                i.setUser(map.get(i.getUserId()));
            }
        }
        int count=idleItemMapper.countIdleItem(findValue,attribute);
        return new PageVo<>(list,count);
    }
    public PageVo<IdleItem> findIdleItemByLabel(int idleLabel,int attribute,int page, int nums) {
        List<IdleItem> list=idleItemMapper.findIdleItemByLabel(idleLabel, attribute,(page - 1) * nums, nums);
        if(list.size()>0){
            List<Long> idList=new ArrayList<>();
            for(IdleItem i:list){
                idList.add(i.getUserId());
            }
            List<User> userList=userMapper.findUserByList(idList);
            Map<Long,User> map=new HashMap<>();
            for(User user:userList){
                map.put(user.getId(),user);
            }
            for(IdleItem i:list){
                i.setUser(map.get(i.getUserId()));
            }
        }
        int count=idleItemMapper.countIdleItemByLabel(idleLabel,attribute);
        return new PageVo<>(list,count);
    }
    public PageVo<IdleItem> findAllIdleItem(String findValue,int page, int nums) {
        List<IdleItem> list=idleItemMapper.findAllIdleItem(findValue,(page - 1) * nums, nums);
        if(list.size()>0){
            List<Long> idList=new ArrayList<>();
            for(IdleItem i:list){
                idList.add(i.getUserId());
            }
            List<User> userList=userMapper.findUserByList(idList);
            Map<Long,User> map=new HashMap<>();
            for(User user:userList){
                map.put(user.getId(),user);
            }
            for(IdleItem i:list){
                i.setUser(map.get(i.getUserId()));
            }
        }
        int count=idleItemMapper.countIdleItem(findValue,1);
        count =count+idleItemMapper.countIdleItem(findValue,2);
        return new PageVo<>(list,count);
    }
    //热门推荐（使用Redis缓存）
    public PageVo<IdleItem> findhotItem(int num) {
        // 构建缓存键
        String cacheKey = HOT_ITEMS_CACHE_KEY + num;
        
        // 尝试从缓存获取数据
        Object cacheResult = redisUtil.get(cacheKey);
        
        if (cacheResult != null) {
            System.out.println("从Redis缓存获取热门商品数据");
            // 从缓存中获取数据
            Type pageVoType = new TypeToken<PageVo<IdleItem>>(){}.getType();
            return gson.fromJson(gson.toJson(cacheResult), pageVoType);
        }
        
        System.out.println("从数据库获取热门商品数据");
        // 缓存中没有，从数据库获取
        List<IdleItem> list = idleItemMapper.findhotItem(num);
        if(list.size() > 0) {
            List<Long> idList = new ArrayList<>();
            for(IdleItem i : list) {
                idList.add(i.getUserId());
            }
            List<User> userList = userMapper.findUserByList(idList);
            Map<Long, User> map = new HashMap<>();
            for(User user : userList) {
                map.put(user.getId(), user);
            }
            for(IdleItem i : list) {
                i.setUser(map.get(i.getUserId()));
            }
        }
        
        PageVo<IdleItem> result = new PageVo<>(list, num);
        
        // 将结果存入缓存
        redisUtil.set(cacheKey, result, CACHE_EXPIRE_TIME);
        
        return result;
    }
    public PageVo<IdleItem> findAllIdleItemByLabel(int idleLabel,int page, int nums) {
        List<IdleItem> list=idleItemMapper.findAllIdleItemByLabel(idleLabel,(page - 1) * nums, nums);
        if(list.size()>0){
            List<Long> idList=new ArrayList<>();
            for(IdleItem i:list){
                idList.add(i.getUserId());
            }
            List<User> userList=userMapper.findUserByList(idList);
            Map<Long,User> map=new HashMap<>();
            for(User user:userList){
                map.put(user.getId(),user);
            }
            for(IdleItem i:list){
                i.setUser(map.get(i.getUserId()));
            }
        }
        int count=idleItemMapper.countIdleItemByLabel(idleLabel);
        return new PageVo<>(list,count);
    }
    public boolean updateIdleItem(IdleItem idleItemModel){
        boolean result = idleItemMapper.updateByPrimaryKeySelective(idleItemModel) == 1;
        if (result) {
            // 清除热门商品缓存
            clearHotItemsCache();
        }
        return result;
    }

    public PageVo<IdleItem> adminGetIdleList(int status, int page, int nums) {
        List<IdleItem> list=idleItemMapper.getIdleItemByStatus(status, (page - 1) * nums, nums);
        if(list.size()>0){
            List<Long> idList=new ArrayList<>();
            for(IdleItem i:list){
                idList.add(i.getUserId());
            }
            List<User> userList=userMapper.findUserByList(idList);
            Map<Long,User> map=new HashMap<>();
            for(User user:userList){
                map.put(user.getId(),user);
            }
            for(IdleItem i:list){
                i.setUser(map.get(i.getUserId()));
            }
        }
        int count=idleItemMapper.countIdleItemByStatus(status);
        return new PageVo<>(list,count);
    }
    // 清除热门商品缓存的方法
    private void clearHotItemsCache() {
        // 清除所有可能的热门商品缓存
        for (int i = 1; i <= 20; i++) {
            String cacheKey = HOT_ITEMS_CACHE_KEY + i;
            if (redisUtil.hasKey(cacheKey)) {
                redisUtil.del(cacheKey);
                System.out.println("清除热门商品缓存: " + cacheKey);
            }
        }
    }
}