package com.idleItem.tradeSystem.exception;

import java.util.Map;

public class ParamException extends RuntimeException{
    private Map map;
    public ParamException(Map map) {
        this.map = map;
    }
    public Map getMap() {
        return map;
    }
}
