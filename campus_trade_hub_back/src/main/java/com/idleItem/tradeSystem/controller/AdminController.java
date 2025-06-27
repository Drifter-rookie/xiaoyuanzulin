package com.idleItem.tradeSystem.controller;

import com.idleItem.tradeSystem.entity.Admin;
import com.idleItem.tradeSystem.entity.IdleItem;
import com.idleItem.tradeSystem.entity.User;
import com.idleItem.tradeSystem.entity.ReportItem;
import com.idleItem.tradeSystem.entity.ReportUser;
import com.idleItem.tradeSystem.entity.ReportOrder;
import com.idleItem.tradeSystem.service.ReportOrderService;
import com.idleItem.tradeSystem.enums.ErrorMsg;
import com.idleItem.tradeSystem.service.ReportItemService;
import com.idleItem.tradeSystem.service.ReportUserService;
import com.idleItem.tradeSystem.service.AdminService;
import com.idleItem.tradeSystem.service.IdleItemService;
import com.idleItem.tradeSystem.service.OrderService;
import com.idleItem.tradeSystem.service.UserService;
import com.idleItem.tradeSystem.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
@CrossOrigin
@RestController
@RequestMapping("admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private IdleItemService idleItemService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;
    @Autowired
    private ReportItemService reportItemService;

    @Autowired
    private ReportUserService reportUserService;

    @Autowired
    private ReportOrderService reportOrderService;


    @GetMapping("login")
    public ResultVo login(@RequestParam("accountNumber") @NotNull @NotEmpty String accountNumber,
                          @RequestParam("adminPassword") @NotNull @NotEmpty String adminPassword,
                          HttpSession session){
        Admin adminModel=adminService.login(accountNumber,adminPassword);
        if (null == adminModel) {
            return ResultVo.fail(ErrorMsg.EMAIL_LOGIN_ERROR);
        }
        Cookie cookie = new Cookie("adminId", String.valueOf(adminModel.getId()));
        session.setAttribute("admin",adminModel);
        return ResultVo.success(adminModel);
    }

    @GetMapping("loginOut")
    public ResultVo loginOut( HttpSession session){
        session.removeAttribute("admin");
        return ResultVo.success();
    }

    @GetMapping("list")
    public ResultVo getAdminList(HttpSession session,
                                 @RequestParam(value = "page",required = false) Integer page,
                                 @RequestParam(value = "nums",required = false) Integer nums){
        if(session.getAttribute("admin")==null){
            return ResultVo.fail(ErrorMsg.COOKIE_ERROR);
        }
        int p=1;
        int n=3;
        if(null!=page){
            p=page>0?page:1;
        }
        if(null!=nums){
            n=nums>0?nums:3;
        }
        return ResultVo.success(adminService.getAdminList(p,n));
    }

    @PostMapping("add")
    public ResultVo addAdmin(HttpSession session,
                             @RequestBody Admin admin){
        if(session.getAttribute("admin")==null){
            return ResultVo.fail(ErrorMsg.COOKIE_ERROR);
        }
        if(adminService.addAdmin(admin)){
            return ResultVo.success();
        }
        return ResultVo.fail(ErrorMsg.PARAM_ERROR);
    }

    @GetMapping("idleList")
    public ResultVo idleList(HttpSession session,
                             @RequestParam("status") @NotNull @NotEmpty Integer status,
                             @RequestParam(value = "page",required = false) Integer page,
                             @RequestParam(value = "nums",required = false) Integer nums){
        if(session.getAttribute("admin")==null){
            return ResultVo.fail(ErrorMsg.COOKIE_ERROR);
        }
        int p=1;
        int n=3;
        if(null!=page){
            p=page>0?page:1;
        }
        if(null!=nums){
            n=nums>0?nums:3;
        }
        return ResultVo.success(idleItemService.adminGetIdleList(status,p,n));
    }

    @GetMapping("updateIdleStatus")
    public ResultVo updateIdleStatus(HttpSession session,
                                     @RequestParam("id") @NotNull @NotEmpty Long id,
                                     @RequestParam("status") @NotNull @NotEmpty Integer status
                                     ){
        if(session.getAttribute("admin")==null){
            return ResultVo.fail(ErrorMsg.COOKIE_ERROR);
        }
        IdleItem idleItem=new IdleItem();
        idleItem.setId(id);
        idleItem.setIdleStatus(status.byteValue());
        if(idleItemService.updateIdleItem(idleItem)){
            return ResultVo.success();
        }
        return ResultVo.fail(ErrorMsg.SYSTEM_ERROR);
    }

    @GetMapping("orderList")
    public ResultVo orderList(HttpSession session,
                              @RequestParam(value = "page",required = false) Integer page,
                              @RequestParam(value = "nums",required = false) Integer nums){
        if(session.getAttribute("admin")==null){
            return ResultVo.fail(ErrorMsg.COOKIE_ERROR);
        }
        int p=1;
        int n=3;
        if(null!=page){
            p=page>0?page:1;
        }
        if(null!=nums){
            n=nums>0?nums:3;
        }
        return ResultVo.success(orderService.getAllOrder(p,n));
    }

    @GetMapping("deleteOrder")
    public ResultVo deleteOrder(HttpSession session,
                              @RequestParam("id") @NotNull @NotEmpty Long id){
        if(session.getAttribute("admin")==null){
            return ResultVo.fail(ErrorMsg.COOKIE_ERROR);
        }
        if(orderService.deleteOrder(id)){
            return ResultVo.success();
        }
        return ResultVo.fail(ErrorMsg.SYSTEM_ERROR);
    }
    @GetMapping("deleteAdmin")
    public ResultVo deleteAdmin(HttpSession session,
                                @RequestParam("id") @NotNull @NotEmpty Long id){
        if(session.getAttribute("admin")==null){
            return ResultVo.fail(ErrorMsg.COOKIE_ERROR);
        }
        if(adminService.deleteAdmin(id)){
            return ResultVo.success();
        }
        return ResultVo.fail(ErrorMsg.SYSTEM_ERROR);
    }
    @GetMapping("userList")
    public ResultVo userList(HttpSession session,
                             @RequestParam(value = "page",required = false) Integer page,
                             @RequestParam(value = "nums",required = false) Integer nums,
                             @RequestParam("status") @NotNull @NotEmpty Integer status){
        if(session.getAttribute("admin")==null){
            return ResultVo.fail(ErrorMsg.COOKIE_ERROR);
        }
        int p=1;
        int n=3;
        if(null!=page){
            p=page>0?page:1;
        }
        if(null!=nums){
            n=nums>0?nums:3;
        }
        return ResultVo.success(userService.getUserByStatus(status,p,n));
    }

    @GetMapping("updateUserStatus")
    public ResultVo updateUserStatus(HttpSession session,
                                     @RequestParam("id") @NotNull @NotEmpty Long id,
                                     @RequestParam("status") @NotNull @NotEmpty Integer status){
        if(session.getAttribute("admin")==null){
            return ResultVo.fail(ErrorMsg.COOKIE_ERROR);
        }
        User user=new User();
        user.setId(id);
        user.setUserStatus(status.byteValue());
        if(userService.updateUserInfo(user))
            return ResultVo.success();
        return ResultVo.fail(ErrorMsg.SYSTEM_ERROR);
    }
    @GetMapping("updateUserpassword")
    public ResultVo updateUserpassword(HttpSession session,
                                       @RequestParam("id") @NotNull @NotEmpty Long id,
                                       @RequestParam("password") @NotNull @NotEmpty String password ){
        if(session.getAttribute("admin")==null){
            return ResultVo.fail(ErrorMsg.COOKIE_ERROR);
        }
        User user=new User();
        user.setId(id);
        user.setUserPassword(password);
        if(userService.updateUserInfo(user))
            return ResultVo.success();
        return ResultVo.fail(ErrorMsg.SYSTEM_ERROR);
    }
    @GetMapping("report1List")
    public ResultVo report1List(HttpSession session,
                                @RequestParam(value = "page",required = false) Integer page,
                                @RequestParam(value = "nums",required = false) Integer nums,
                                @RequestParam("status") @NotNull @NotEmpty Integer status){
        if(session.getAttribute("admin")==null){
            return ResultVo.fail(ErrorMsg.COOKIE_ERROR);
        }
        int p=1;
        int n=3;
        if(null!=page){
            p=page>0?page:1;
        }
        if(null!=nums){
            n=nums>0?nums:3;
        }
        return ResultVo.success(reportItemService.getReportItemByStatus(status,p,n));
    }

    @GetMapping("report2List")
    public ResultVo report2List(HttpSession session,
                                @RequestParam(value = "page",required = false) Integer page,
                                @RequestParam(value = "nums",required = false) Integer nums,
                                @RequestParam("status") @NotNull @NotEmpty Integer status){
        if(session.getAttribute("admin")==null){
            return ResultVo.fail(ErrorMsg.COOKIE_ERROR);
        }
        int p=1;
        int n=3;
        if(null!=page){
            p=page>0?page:1;
        }
        if(null!=nums){
            n=nums>0?nums:3;
        }
        return ResultVo.success(reportUserService.getReportUserByStatus(status,p,n));
    }
    @GetMapping("updateRUserStatus")
    public ResultVo updateRUserStatus(HttpSession session,
                                      @RequestParam("id") @NotNull @NotEmpty Long id,
                                      @RequestParam("handleStatus") @NotNull @NotEmpty Integer handleStatus){
        if(session.getAttribute("admin")==null){
            return ResultVo.fail(ErrorMsg.COOKIE_ERROR);
        }
        ReportUser reportUser=new ReportUser();
        reportUser.setId(id);
        reportUser.setHandleStatus(handleStatus.byteValue());
        if(reportUserService.updateReportUserInfo(reportUser))
            return ResultVo.success();
        return ResultVo.fail(ErrorMsg.SYSTEM_ERROR);
    }
    @GetMapping("updateRIdleStatus")
    public ResultVo updateRIdleStatus(HttpSession session,
                                      @RequestParam("id") @NotNull @NotEmpty Long id,
                                      @RequestParam("status") @NotNull @NotEmpty Integer status){
        if(session.getAttribute("admin")==null){
            return ResultVo.fail(ErrorMsg.COOKIE_ERROR);
        }
        ReportItem reportItem=new ReportItem();
        reportItem.setId(id);
        reportItem.setHandleStatus(status.byteValue());
        if(reportItemService.updateReportIdleInfo(reportItem))
            return ResultVo.success();
        return ResultVo.fail(ErrorMsg.SYSTEM_ERROR);
    }
    @GetMapping("report3List")
    public ResultVo report3List(HttpSession session,
                                @RequestParam(value = "page",required = false) Integer page,
                                @RequestParam(value = "nums",required = false) Integer nums,
                                @RequestParam("status") @NotNull @NotEmpty Integer status){
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
        return ResultVo.success(reportOrderService.getReportOrderByStatus(status,p,n));
    }
    @GetMapping("updateROrderStatus")
    public ResultVo updateROrderStatus(HttpSession session,
                                       @RequestParam("id") @NotNull @NotEmpty Long id,
                                       @RequestParam("status") @NotNull @NotEmpty Integer status){
        if(session.getAttribute("admin")==null){
            return ResultVo.fail(ErrorMsg.COOKIE_ERROR);
        }
        ReportOrder reportOrder=new ReportOrder();
        reportOrder.setId(id);
        reportOrder.setHandleStatus(status.byteValue());
        if(reportOrderService.updateReportOrderInfo(reportOrder))
            return ResultVo.success();
        return ResultVo.fail(ErrorMsg.SYSTEM_ERROR);
    }
    @GetMapping("deleteReportItem")
    public ResultVo deleteReportItem(HttpSession session,
                                @RequestParam("id") @NotNull @NotEmpty Long id){
        if(session.getAttribute("admin")==null){
            return ResultVo.fail(ErrorMsg.COOKIE_ERROR);
        }
        if(reportItemService.deleteReportItem(id)){
            return ResultVo.success();
        }
        return ResultVo.fail(ErrorMsg.SYSTEM_ERROR);
    }
    @GetMapping("deleteReportUser")
    public ResultVo deleteReportUser(HttpSession session,
                                @RequestParam("id") @NotNull @NotEmpty Long id){
        if(session.getAttribute("admin")==null){
            return ResultVo.fail(ErrorMsg.COOKIE_ERROR);
        }
        if(reportUserService.deleteReportUser(id)){
            return ResultVo.success();
        }
        return ResultVo.fail(ErrorMsg.SYSTEM_ERROR);
    }
    @GetMapping("deleteReportOrder")
    public ResultVo deleteReportOrder(HttpSession session,
                                @RequestParam("id") @NotNull @NotEmpty Long id){
        if(session.getAttribute("admin")==null){
            return ResultVo.fail(ErrorMsg.COOKIE_ERROR);
        }
        if(reportOrderService.deleteReportOrder(id)){
            return ResultVo.success();
        }
        return ResultVo.fail(ErrorMsg.SYSTEM_ERROR);
    }
}
