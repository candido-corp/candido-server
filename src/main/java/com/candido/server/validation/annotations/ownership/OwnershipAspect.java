package com.candido.server.validation.annotations.ownership;

import com.candido.server.domain.v1._common.Ownable;
import com.candido.server.domain.v1.account.Account;
import com.candido.server.exception._common.EnumExceptionName;
import com.candido.server.exception.security.auth.ExceptionForbidden;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class OwnershipAspect {

    @Autowired
    private ApplicationContext applicationContext;

    @Before("@annotation(checkOwnership)")
    public void checkOwnership(JoinPoint joinPoint, CheckOwnership checkOwnership) {
        String paramName = checkOwnership.idParam();
        Class<? extends OwnershipLoader<?>> loaderClass = checkOwnership.loader();

        Object[] args = joinPoint.getArgs();
        String[] paramNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();

        Integer id = null;
        for (int i = 0; i < paramNames.length; i++) {
            if (paramNames[i].equals(paramName)) {
                id = (Integer) args[i];
                break;
            }
        }

        if (id == null)
            throw new RuntimeException("Missing or invalid ID parameter: " + paramName);

        OwnershipLoader<?> loader = applicationContext.getBean(loaderClass);
        Ownable entity = loader.loadById(id);

        Integer currentUserId = getCurrentUserId();
        if (!entity.getOwnerId().equals(currentUserId)) {
            throw new ExceptionForbidden(EnumExceptionName.ERROR_AUTH_FORBIDDEN.name());
        }
    }

    private Integer getCurrentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Account account = (Account) auth.getPrincipal();
        return account.getId();
    }
}

