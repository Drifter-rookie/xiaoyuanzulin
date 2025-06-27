package com.idleItem.tradeSystem.utils;

import lombok.Data;

@Data
public class R {
    //状态码
    private Integer code;
    //消息提示
    private String msg;
    //数据
    private Object data;


    public R(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    //快速返回成功01
    public static R ok(){
        return new R(200,"success",null);
    }
    //快速返回成功02
    public static R ok(String msg){
        return new R(200,msg,null);
    }
    //快速返回成功03
    public static R ok(String msg,Object data){
        return new R(200,msg,data);
    }

    //快速返回失败01
    public static R fail(){
        return new R(500,"fail",null);
    }
    //快速返回失败03
    public static R fail(String msg){
        return new R(500,msg,null);
    }
    //快速返回失败03
    public static R fail(String msg,Object data){
        return new R(500,msg,data);
    }



}
