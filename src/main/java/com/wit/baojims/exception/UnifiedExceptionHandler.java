package com.wit.baojims.exception;

import cn.hutool.json.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@RestControllerAdvice
public class UnifiedExceptionHandler {

    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public String handlerException(Exception e){
        JSONObject jsonObject = new JSONObject();
        //处理后端验证失败产生的异常
        if(e instanceof MethodArgumentNotValidException){
            MethodArgumentNotValidException exception = (MethodArgumentNotValidException) e;
            jsonObject.set("error", exception.getBindingResult().getFieldError().getDefaultMessage()+"1");
        }
        //处理业务异常
        else if(e instanceof BaojiException){
            log.error("执行异常", e);
            BaojiException exception = (BaojiException) e;
            jsonObject.set("error", exception.getMessage()+"2");
        }
        //处理其余异常
        else {
            log.error("执行异常", e);
            jsonObject.set("error", "执行异常3");
        }
        return jsonObject.toString();
    }
}
