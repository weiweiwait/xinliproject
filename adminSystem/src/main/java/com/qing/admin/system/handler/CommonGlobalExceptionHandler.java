package com.qing.admin.system.handler;


import com.qing.admin.system.entity.ConditionException;
import com.qing.admin.system.entity.JsonResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常处理器
 */
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CommonGlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public JsonResponse commonExceptionHandler(HttpServletRequest httpServletRequest, Exception e) {
        String errorMsg = e.getMessage();
        if(e instanceof ConditionException) {
            String code = ((ConditionException) e).getCode();
            return new JsonResponse(errorMsg, code);
        } else {
            e.printStackTrace();
            return new JsonResponse(errorMsg, "500");
        }
    }
}
