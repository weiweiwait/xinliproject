package com.qing.admin.system.controller.support;


import com.qing.admin.system.entity.ConditionException;
import com.qing.admin.system.util.TokenUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class UserSupport {

    public Integer getCurrentUserId() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String token = requestAttributes.getRequest().getHeader("token");
        Integer userId = TokenUtil.verifyToken(token);
        if(userId < 0) {
            throw new ConditionException("非法用户！");
        }
        return userId;
    }
}
