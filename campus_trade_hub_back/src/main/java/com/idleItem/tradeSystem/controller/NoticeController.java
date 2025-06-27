package com.idleItem.tradeSystem.controller;

import com.idleItem.tradeSystem.entity.Notice;
import com.idleItem.tradeSystem.enums.ErrorMsg;
import com.idleItem.tradeSystem.service.NoticeService;
import com.idleItem.tradeSystem.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@CrossOrigin
@RestController
@RequestMapping("/notice")
public class NoticeController {
    @Autowired
    private NoticeService noticeService;

    @PostMapping("/addNotice")
    public ResultVo addNotice(@RequestBody Notice notice){
        notice.setAnnounceTime(new Date());
//        notice.setAdminId(Long.valueOf(adminId));
//        notice.setAdminName("超级管理员");
        if(noticeService.addNotice(notice)){
            return ResultVo.success(notice);
        }
        return ResultVo.fail(ErrorMsg.SYSTEM_ERROR);
    }

    @GetMapping("allNotice")
    public ResultVo allNotice(HttpSession session){
        return ResultVo.success(noticeService.getNotice());
    }

    @GetMapping("noticeList")
    public ResultVo noticeList(HttpSession session,
                              @RequestParam(value = "page",required = false) Integer page,
                              @RequestParam(value = "nums",required = false) Integer nums){
        if(session.getAttribute("admin")==null){
            return ResultVo.fail(ErrorMsg.COOKIE_ERROR);
        }
        int p=1;
        int n=8;
        if(null!=page){
            p=page>0?page:1;
        }
        if(null!=nums){
            n=nums>0?nums:8;
        }
        return ResultVo.success(noticeService.getAllNotice(p,n));
    }

    @GetMapping("deleteNotice")
    public ResultVo deleteNotice(HttpSession session,
                                @RequestParam("id") @NotNull @NotEmpty Long id){
        if(session.getAttribute("admin")==null){
            return ResultVo.fail(ErrorMsg.COOKIE_ERROR);
        }
        if(noticeService.deleteNotice(id)){
            return ResultVo.success();
        }
        return ResultVo.fail(ErrorMsg.SYSTEM_ERROR);
    }
}
