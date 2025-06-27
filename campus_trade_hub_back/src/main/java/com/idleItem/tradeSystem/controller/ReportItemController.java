package com.idleItem.tradeSystem.controller;
import com.idleItem.tradeSystem.entity.ReportItem;
import com.idleItem.tradeSystem.enums.ErrorMsg;
import com.idleItem.tradeSystem.service.ReportItemService;
import com.idleItem.tradeSystem.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
@CrossOrigin
@RestController
@RequestMapping("/reportItem")
public class ReportItemController {

    @Autowired
    private ReportItemService reportItemService;

    @PostMapping("add")
    public ResultVo addReportItem(@CookieValue("shUserId")
                               @NotNull(message = "登录异常 请重新登录")
                               @NotEmpty(message = "登录异常 请重新登录") String shUserId,
                               @RequestBody ReportItem reportItem) {
        reportItem.setReporterId(String.valueOf(shUserId));
        reportItem.setHandleStatus((byte) 0);
        reportItem.setReportTime(new Date());
        if (reportItemService.addReportItem(reportItem)) {
            return ResultVo.success(reportItem);
        }
        return ResultVo.fail(ErrorMsg.SYSTEM_ERROR);
    }
}
