package com.idleItem.tradeSystem.service.impl;

import com.google.gson.Gson;
import com.idleItem.tradeSystem.entity.IdleItem;
import com.idleItem.tradeSystem.entity.Order;
import com.idleItem.tradeSystem.mapper.IdleItemMapper;
import com.idleItem.tradeSystem.mapper.OrderMapper;
import com.idleItem.tradeSystem.service.OrderService;
import com.idleItem.tradeSystem.utils.OrderTask;
import com.idleItem.tradeSystem.utils.OrderTaskHandler;
import com.idleItem.tradeSystem.vo.PageVo;
import io.goeasy.GoEasy;
import io.goeasy.publish.GoEasyError;
import io.goeasy.publish.PublishListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class OrderServiceImpl implements OrderService {
    private final GoEasy goEasy=new GoEasy("https://rest-hz.goeasy.io", "BC-7302d47494b342f3bc28a08c7851e523");
    private final Gson gson=new Gson();
    @Resource
    private OrderMapper orderMapper;

    @Resource
    private IdleItemMapper idleItemMapper;
    /**
     * 新增订单，同时下架闲置
     * （订单超时：
     * 1、重新上架闲置；2、修改订单状态；
     */
    private static HashMap<Integer,ReentrantLock> lockMap=new HashMap<>();
    static {
        for(int i=0;i<100;i++){
            lockMap.put(i,new ReentrantLock(true));
        }
    }
    public boolean addOrder(Order orderModel){
        String message = gson.toJson(orderModel);
        goEasy.publish("order_create", message,new PublishListener(){
        });
        IdleItem idleItemModel=idleItemMapper.selectByPrimaryKey(orderModel.getIdleId());
        System.out.println(idleItemModel.getIdleStatus());
        if(idleItemModel.getIdleStatus()!=1){
            return false;
        }
        IdleItem idleItem=new IdleItem();
        idleItem.setId(orderModel.getIdleId());
        idleItem.setUserId(idleItemModel.getUserId());
        idleItem.setIdleStatus((byte)2);

        int key= (int) (orderModel.getIdleId()%100);
        ReentrantLock lock=lockMap.get(key);
        boolean flag;
        try {
            lock.lock();
            flag=addOrderHelp(idleItem,orderModel);
        }finally {
            lock.unlock();
        }
        return flag;
    }


    @Transactional(rollbackFor = Exception.class)
    public boolean addOrderHelp(IdleItem idleItem, Order order){
        IdleItem idleItemModel=idleItemMapper.selectByPrimaryKey(order.getIdleId());
        if(idleItemModel.getIdleStatus()!=1){
            return false;
        }
        if(idleItemMapper.updateByPrimaryKeySelective(idleItem)==1){
            if(orderMapper.insert(order)==1){
                order.setOrderStatus((byte) 5);
                //半小时未支付则取消订单
                OrderTaskHandler.addOrder(new OrderTask(order,5*60));
                updatestatus(order.getId(),order.getUserId());
                return true;
            }else {
                new RuntimeException();
            }
        }
        return false;
    }
    public Order getOrder(Long id){
        Order orderModel=orderMapper.selectByPrimaryKey(id);
        orderModel.setIdleItem(idleItemMapper.selectByPrimaryKey(orderModel.getIdleId()));
        return orderModel;
    }
    @Transactional(rollbackFor = Exception.class)
    public boolean updateOrder(Order order){
        String message = gson.toJson(order);
        //不可修改的信息
        order.setOrderNumber(null);
        order.setUserId(null);
        order.setIdleId(null);
        order.setCreateTime(null);
        if(order.getOrderStatus()==5){
            goEasy.publish("order_delete", message,new PublishListener(){
            });
            Order o=orderMapper.selectByPrimaryKey(order.getId());
            if(o.getOrderStatus()!=0){
                return false;
            }
            IdleItem idleItemModel=idleItemMapper.selectByPrimaryKey(o.getIdleId());
            if(idleItemModel.getIdleStatus()==2){
                IdleItem idleItem=new IdleItem();
                idleItem.setId(o.getIdleId());
                idleItem.setUserId(idleItemModel.getUserId());
                idleItem.setIdleStatus((byte)1);
                if(orderMapper.updateByPrimaryKeySelective(order)==1){
                    if(idleItemMapper.updateByPrimaryKeySelective(idleItem)==1){
                        return true;
                    }else {
                        new RuntimeException();
                    }
                }
                return false;
            }else{
                if(orderMapper.updateByPrimaryKeySelective(order)==1){
                    return true;
                }else {
                    new RuntimeException();
                }
            }
        }
        return orderMapper.updateByPrimaryKeySelective(order)==1;
    }
    public List<Order> getMyOrder(Long userId){
        List<Order> list=orderMapper.getMyOrder(userId);
        if(list.size()>0){
            List<Long> idleIdList=new ArrayList<>();
            for(Order i:list){
                idleIdList.add(i.getIdleId());
            }
            List<IdleItem> idleItemModelList=idleItemMapper.findIdleByList(idleIdList);
            Map<Long, IdleItem> map=new HashMap<>();
            for(IdleItem idle:idleItemModelList){
                map.put(idle.getId(),idle);
            }
            for(Order i:list){
                i.setIdleItem(map.get(i.getIdleId()));
            }
        }
        return list;
    }
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public List<Order> getMySoldIdle(Long userId){
        List<IdleItem> list=idleItemMapper.getAllIdleItem(userId);
        List<Order> orderList=null;
        if(list.size()>0){
            List<Long> idleIdList=new ArrayList<>();
            for(IdleItem i:list){
                idleIdList.add(i.getId());
            }
            orderList=orderMapper.findOrderByIdleIdList(idleIdList);
            Map<Long, IdleItem> map=new HashMap<>();
            for(IdleItem idle:list){
                map.put(idle.getId(),idle);
            }
            for(Order o:orderList){
                o.setIdleItem(map.get(o.getIdleId()));
            }
        }
        return orderList;
    }

    public PageVo<Order> getAllOrder(int page, int nums){
        List<Order> list=orderMapper.getAllOrder((page-1)*nums,nums);
        if(list.size()>0){
            List<Long> idleIdList=new ArrayList<>();
            for(Order i:list){
                idleIdList.add(i.getIdleId());
            }
            List<IdleItem> idleItemModelList=idleItemMapper.findIdleByList(idleIdList);
            Map<Long, IdleItem> map=new HashMap<>();
            for(IdleItem idle:idleItemModelList){
                map.put(idle.getId(),idle);
            }
            for(Order i:list){
                i.setIdleItem(map.get(i.getIdleId()));
            }
        }
        int count=orderMapper.countAllOrder();
        return new PageVo<>(list,count);
    }

    public boolean deleteOrder(long id){
        return orderMapper.deleteByPrimaryKey(id)==1;
    }
    public boolean updatestatus(long id,long userid){
        String message = String.valueOf(userid);
        goEasy.publish("order", message,new PublishListener(){
        });
        return orderMapper.updatestatus(id,userid)==1;
    }
    public boolean updatestatus1(long id){
        String message = String.valueOf(id);
        goEasy.publish("order1", message,new PublishListener(){
        });
        return orderMapper.updatestatus1(id)==1;
    }
    public int countmyorder(long id){
        return orderMapper.countmyOrders(id);
    }
}
