package com.wp.common.vo;


import com.wp.common.enums.ResultEnum;

import java.util.HashMap;
import java.util.Map;

public class ResultVO<T> {

    private Integer code;

    private String msg;

    private T data;

    private HashMap<String, Object> map = new HashMap<>();

    public static <T> ResultVO<T> success(T t) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(ResultEnum.SUCCEED.getCode());
        resultVO.setMsg(ResultEnum.SUCCEED.getMessage());
        resultVO.setData(t);
        return resultVO;
    }

    public static boolean isSuccess(ResultVO resultVO) {
        if (resultVO.getCode().equals(ResultEnum.SUCCEED.getCode()) &&
                resultVO.getMsg().equals(ResultEnum.SUCCEED.getMessage())) {
            return true;
        }
        return false;
    }

    public static ResultVO error(ResultEnum resultEnum) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(resultEnum.getCode());
        resultVO.setMsg(resultEnum.getMessage());
        return resultVO;
    }

    public void add(String key, Object value) {
        this.map.put(key, value);
    }

    public HashMap<String, Object> getMap() {
        return map;
    }

    public void setMap(HashMap<String, Object> map) {
        this.map = map;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
