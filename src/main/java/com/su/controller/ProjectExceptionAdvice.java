package com.su.controller;

import com.su.domain.Code;
import com.su.domain.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ProjectExceptionAdvice  {
    @ExceptionHandler(Exception.class)
    public Result doException(Exception ex){
        System.out.println("捕捉到异常");
        ex.printStackTrace();
        return new Result(Code.BUSY,"服务器繁忙",null);
    }
}