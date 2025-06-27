package com.idleItem.tradeSystem.controller;


import com.idleItem.tradeSystem.entity.Message;
import com.idleItem.tradeSystem.enums.ErrorMsg;
import com.idleItem.tradeSystem.service.MessageService;
import com.idleItem.tradeSystem.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
@CrossOrigin
@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping("/send")
    public ResultVo sendMessage(@CookieValue("shUserId")
                                @NotNull(message = "登录异常 请重新登录")
                                @NotEmpty(message = "登录异常 请重新登录") String shUserId,
                                @RequestBody Message message){
        message.setUserId(Long.valueOf(shUserId));
        message.setCreateTime(new Date());
        if(messageService.addMessage(message)){
            return ResultVo.success(message);
        }
        return ResultVo.fail(ErrorMsg.SYSTEM_ERROR);
    }

    @GetMapping("/info")
    public ResultVo getMessage(@RequestParam Long id){
        return ResultVo.success(messageService.getMessage(id));
    }

    @GetMapping("/idle")
    public ResultVo getAllIdleMessage(@RequestParam Long idleId){
        return ResultVo.success(messageService.getAllIdleMessage(idleId));
    }

    @GetMapping("/my")
    public ResultVo getAllMyMessage(@CookieValue("shUserId")
                                        @NotNull(message = "登录异常 请重新登录")
                                        @NotEmpty(message = "登录异常 请重新登录") String shUserId){
        return ResultVo.success(messageService.getAllMyMessage(Long.valueOf(shUserId)));
    }

    @GetMapping("/delete")
    public ResultVo deleteMessage(@CookieValue("shUserId")
                                  @NotNull(message = "登录异常 请重新登录")
                                  @NotEmpty(message = "登录异常 请重新登录") String shUserId,
                                  @RequestParam Long id){
        if(messageService.deleteMessage(id)){
            return ResultVo.success();
        }
        return ResultVo.fail(ErrorMsg.SYSTEM_ERROR);
    }
    @GetMapping("updatethis")
    public ResultVo updatethis(String id){
        if(messageService.updatestatus(Long.valueOf(id)))
            return ResultVo.success();
        return ResultVo.fail(ErrorMsg.SYSTEM_ERROR);
    }
}
