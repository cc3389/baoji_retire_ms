package com.wit.baojims.exception;

import com.wit.baojims.result.ResponseEnum;

public class BaojiException extends RuntimeException{
    public  BaojiException(ResponseEnum responseEnum){
        super(responseEnum.getMsg());
    }
}
