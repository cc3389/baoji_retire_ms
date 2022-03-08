package com.wit.baojims.exception;

import cn.dev33.satoken.util.SaResult;
import cn.hutool.json.JSONObject;
import com.wit.baojims.result.ResponseEnum;
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
    public SaResult handlerException(Exception e){
//        JSONObject jsonObject = new JSONObject();
        //处理后端验证失败产生的异常
        if(e instanceof MethodArgumentNotValidException){
            MethodArgumentNotValidException exception = (MethodArgumentNotValidException) e;
            return SaResult.code(300).setMsg(exception.getBindingResult().getFieldError().getDefaultMessage());
        }
        //处理业务异常
        else if(e instanceof BaojiException){
            log.error("执行异常", e);
            BaojiException exception = (BaojiException) e;
            return SaResult.code(300).setMsg(e.getMessage());
        }
        //处理其余异常
        else {
            log.error("执行异常", e);
//            jsonObject.set("error", "执行异常");
            SaResult.code(300).setMsg(e.getMessage());
        }
        return SaResult.code(300).setMsg(e.getMessage());
    }
}
