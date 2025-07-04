package com.idleItem.tradeSystem.controller;


import com.idleItem.tradeSystem.entity.OrderAddress;
import com.idleItem.tradeSystem.enums.ErrorMsg;
import com.idleItem.tradeSystem.service.OrderAddressService;
import com.idleItem.tradeSystem.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
@CrossOrigin
@RestController
@RequestMapping("/order-address")
public class OrderAddressController {

    @Autowired
    private OrderAddressService orderAddressService;

    @PostMapping("/add")
    public ResultVo addOrderAddress(@CookieValue("shUserId")
                                        @NotNull(message = "登录异常 请重新登录")
                                        @NotEmpty(message = "登录异常 请重新登录") String shUserId,
                                    @RequestBody OrderAddress orderAddress){
        return ResultVo.success(orderAddressService.addOrderAddress(orderAddress));
    }

    @PostMapping("/update")
    public ResultVo updateOrderAddress(@CookieValue("shUserId")
                                       @NotNull(message = "登录异常 请重新登录")
                                       @NotEmpty(message = "登录异常 请重新登录") String shUserId,
                                       @RequestBody OrderAddress orderAddress){
        if(orderAddressService.updateOrderAddress(orderAddress)){
            return ResultVo.success(orderAddress);
        }
        return ResultVo.fail(ErrorMsg.SYSTEM_ERROR);
    }

    @GetMapping("/info")
    public ResultVo getOrderAddress(@CookieValue("shUserId")
                                    @NotNull(message = "登录异常 请重新登录")
                                    @NotEmpty(message = "登录异常 请重新登录") String shUserId,
                                    @RequestParam Long orderId){
        return ResultVo.success(orderAddressService.getOrderAddress(orderId));
    }
}
