package com.idleItem.tradeSystem.controller;

import com.idleItem.tradeSystem.entity.Order;
import com.idleItem.tradeSystem.enums.ErrorMsg;
import com.idleItem.tradeSystem.service.OrderService;
import com.idleItem.tradeSystem.utils.IdFactoryUtil;
import com.idleItem.tradeSystem.utils.OrderTaskHandler;
import com.idleItem.tradeSystem.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
@CrossOrigin
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/add")
    public ResultVo addOrder(@CookieValue("shUserId")
                             @NotNull(message = "登录异常 请重新登录")
                             @NotEmpty(message = "登录异常 请重新登录") String shUserId,
                             @RequestBody Order order){
        if(OrderTaskHandler.orderService==null){
            OrderTaskHandler.orderService=orderService;
        }
        order.setOrderNumber(IdFactoryUtil.getOrderId());
        order.setCreateTime(new Date());
        order.setUserId(Long.valueOf(shUserId));
        order.setOrderStatus((byte) 0);
        order.setPaymentStatus((byte)0);
        if(orderService.addOrder(order)){
            return ResultVo.success(order);
        }
        return ResultVo.fail(ErrorMsg.SYSTEM_ERROR);
    }

    @GetMapping("/info")
    public ResultVo getOrderInfo(@CookieValue("shUserId")
                                 @NotNull(message = "登录异常 请重新登录")
                                 @NotEmpty(message = "登录异常 请重新登录") String shUserId,
                                 @RequestParam Long id){
        Order order=orderService.getOrder(id);
        if(order.getUserId().equals(Long.valueOf(shUserId))||
                order.getIdleItem().getUserId().equals(Long.valueOf(shUserId))){
            return ResultVo.success(order);
        }
        return ResultVo.fail(ErrorMsg.SYSTEM_ERROR);
    }

    @PostMapping("/update")
    public ResultVo updateOrder(@CookieValue("shUserId")
                             @NotNull(message = "登录异常 请重新登录")
                             @NotEmpty(message = "登录异常 请重新登录") String shUserId,
                             @RequestBody Order order){
        if(order.getPaymentStatus()!=null&&order.getPaymentStatus().equals((byte) 1)){
            order.setPaymentTime(new Date());
        }
        if(orderService.updateOrder(order)){
            return ResultVo.success(order);
        }
        return ResultVo.fail(ErrorMsg.SYSTEM_ERROR);
    }

    @GetMapping("/my")
    public ResultVo getMyOrder(@CookieValue("shUserId")
                                 @NotNull(message = "登录异常 请重新登录")
                                 @NotEmpty(message = "登录异常 请重新登录") String shUserId){
        return ResultVo.success(orderService.getMyOrder(Long.valueOf(shUserId)));
    }

    @GetMapping("/my-sold")
    public ResultVo getMySoldIdle(@CookieValue("shUserId")
                               @NotNull(message = "登录异常 请重新登录")
                               @NotEmpty(message = "登录异常 请重新登录") String shUserId){
        return ResultVo.success(orderService.getMySoldIdle(Long.valueOf(shUserId)));
    }
    @GetMapping("updatethis")
    public ResultVo updatethis(String id,String userid){
        if(orderService.updatestatus(Long.valueOf(id),Long.valueOf(userid)))
            return ResultVo.success();
        return ResultVo.fail(ErrorMsg.SYSTEM_ERROR);
    }
    @GetMapping("updatethis1")
    public ResultVo updatethis1(String id){
        if(orderService.updatestatus1(Long.valueOf(id)))
            return ResultVo.success();
        return ResultVo.fail(ErrorMsg.SYSTEM_ERROR);
    }
    @GetMapping("count")
    public int count(String id){
        if(orderService.countmyorder(Long.valueOf(id))>=0)
            return orderService.countmyorder(Long.valueOf(id));
        return -1;
    }
}
