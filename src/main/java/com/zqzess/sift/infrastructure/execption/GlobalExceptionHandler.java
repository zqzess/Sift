package com.zqzess.sift.infrastructure.execption;

import com.zqzess.sift.infrastructure.Result;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

/**
 * ========================
 * Created with IntelliJ IDEA.
 *
 * @Author zqzess
 * @Date 2023/02/17 15:58
 * @Package com.zqzess.tjsys.exception
 * @GitHUb Https://github.com/zqzess
 * @Version ========================
 **/
@RestControllerAdvice
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 如果抛出的的是ServiceException，则调用该方法
     * @param se 业务异常
     * @return Result
     */
    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    public Result handle(ServiceException se){
        log.error(se.getMessage());
        return Result.fail(se.getCode(), se.getMessage());
    }

    // 处理标注了 @Validated 的类的方法调用参数校验失败导致的异常
    @ExceptionHandler(ConstraintViolationException.class)
    public Result handleConstraintViolationException(ConstraintViolationException e) {
        String message = e.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.joining("&"));
        return Result.fail(message);
    }

    // 处理表单类型请求普通参数缺失导致的异常
    @ExceptionHandler(ServletRequestBindingException.class)
    public Result handleServletRequestBindingException(ServletRequestBindingException e) {
        String message = e.getMessage();
        return Result.fail(message);
    }

    // 处理表单类型请求的复杂参数校验失败导致的异常
    @ExceptionHandler(BindException.class)
    public Result handleBindException(BindException e) {
        String message = e.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining("&"));
//        String message =e.getFieldError().getDefaultMessage();
        return Result.fail(message);
    }

    // 处理 application/json 类型请求的参数校验失败导致的异常
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining("&"));
//        String message =e.getFieldError().getDefaultMessage();
        return Result.fail(message);
    }

    // 全局异常
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e){
        log.error(e.getMessage());
        return Result.fail(String.valueOf(e));
    }
}
