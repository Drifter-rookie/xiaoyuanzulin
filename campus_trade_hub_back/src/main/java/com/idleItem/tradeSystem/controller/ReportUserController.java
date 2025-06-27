package com.idleItem.tradeSystem.controller;
import com.idleItem.tradeSystem.entity.ReportUser;
import com.idleItem.tradeSystem.enums.ErrorMsg;
import com.idleItem.tradeSystem.service.ReportUserService;
import com.idleItem.tradeSystem.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
@CrossOrigin
@RestController
@RequestMapping("/reportUser")
public class ReportUserController {

    @Autowired
    private ReportUserService reportUserService;

    @PostMapping("add")
    public ResultVo addReportUser(@CookieValue("shUserId")
                                  @NotNull(message = "登录异常 请重新登录")
                                  @NotEmpty(message = "登录异常 请重新登录") String shUserId,
                                  @RequestBody ReportUser reportUser) {
        reportUser.setReporterId(String.valueOf(shUserId));
        reportUser.setHandleStatus((byte) 0);
        reportUser.setReportTime(new Date());
        if (reportUserService.addReportUser(reportUser)) {
            return ResultVo.success(reportUser);
        }
        return ResultVo.fail(ErrorMsg.SYSTEM_ERROR);
    }
}

