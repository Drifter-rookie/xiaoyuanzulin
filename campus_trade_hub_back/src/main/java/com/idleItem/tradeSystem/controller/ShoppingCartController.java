package com.idleItem.tradeSystem.controller;

import com.idleItem.tradeSystem.entity.ShoppingCart;
import com.idleItem.tradeSystem.enums.ErrorMsg;
import com.idleItem.tradeSystem.service.ShoppingCartService;
import com.idleItem.tradeSystem.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@CrossOrigin
@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;

    @PostMapping("/add")
    public ResultVo addShoppingCart(@CookieValue("shUserId")
                                @NotNull(message = "登录异常 请重新登录")
                                @NotEmpty(message = "登录异常 请重新登录") String shUserId,
                                @RequestBody ShoppingCart ShoppingCart){
        ShoppingCart.setUserId(Long.valueOf(shUserId));
        ShoppingCart.setCreateTime(new Date());
        if(shoppingCartService.addShoppingCart(ShoppingCart)){
            return ResultVo.success(ShoppingCart.getId());
        }
        return ResultVo.fail(ErrorMsg.FAVORITE_EXIT);
    }

    @GetMapping("/delete")
    public ResultVo deleteShoppingCart(@CookieValue("shUserId")
                                   @NotNull(message = "登录异常 请重新登录")
                                   @NotEmpty(message = "登录异常 请重新登录") String shUserId,
                                   @RequestParam Long id){
        if(shoppingCartService.deleteShoppingCart(id)){
            return ResultVo.success();
        }
        return ResultVo.fail(ErrorMsg.SYSTEM_ERROR);
    }

    @GetMapping("/check")
    public ResultVo checkShoppingCart(@CookieValue("shUserId")
                                  @NotNull(message = "登录异常 请重新登录")
                                  @NotEmpty(message = "登录异常 请重新登录") String shUserId,
                                  @RequestParam Long idleId){
        return ResultVo.success(shoppingCartService.isShoppingCart(Long.valueOf(shUserId),idleId));
    }

    @GetMapping("/my")
    public ResultVo getMyShoppingCart(@CookieValue("shUserId")
                                  @NotNull(message = "登录异常 请重新登录")
                                  @NotEmpty(message = "登录异常 请重新登录") String shUserId){
        return ResultVo.success(shoppingCartService.getAllShoppingCart(Long.valueOf(shUserId)));
    }
}
