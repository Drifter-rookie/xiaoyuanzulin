package com.idleItem.tradeSystem.controller;

import com.idleItem.tradeSystem.entity.ReportOrder;
import com.idleItem.tradeSystem.enums.ErrorMsg;
import com.idleItem.tradeSystem.service.ReportOrderService;
import com.idleItem.tradeSystem.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
@CrossOrigin
@RestController
@RequestMapping("/reportOrder")
public class ReportOrderController {

        @Autowired
        private ReportOrderService reportOrderService;

        @PostMapping("add")
        public ResultVo addReportOrder(@CookieValue("shUserId")
                                      @NotNull(message = "登录异常 请重新登录")
                                      @NotEmpty(message = "登录异常 请重新登录") String shUserId,
                                      @RequestBody ReportOrder reportOrder) {
            reportOrder.setReporterId(String.valueOf(shUserId));
            reportOrder.setHandleStatus((byte) 0);
            reportOrder.setReportTime(new Date());
            if (reportOrderService.addReportOrder(reportOrder)) {
                return ResultVo.success(reportOrder);
            }
            return ResultVo.fail(ErrorMsg.SYSTEM_ERROR);
        }
    }


