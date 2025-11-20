package io.github.testwork.logging;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;

@Component
@Aspect
@Slf4j
public class LoggingAspect {
    @Pointcut("@within(org.springframework.web.bind.annotation.RestController)")
    public void controllerLog() {
    }

    @Pointcut("@within(org.springframework.stereotype.Service)")
    public void serviceLog() {
    }

    @Before("controllerLog()")
    public void doBeforeController(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = null;
        if (attributes != null) {
            request = attributes.getRequest();
        }
        if (request != null) {
            if (!log.isInfoEnabled()) {
                return;
            }
            log.info("CONTROLLER: METHOD: {}.{}, ARGS: {}, URL: {}, HTTP_METHOD: {}",
                    joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName(),
                    getArgsString(joinPoint),
                    request.getRequestURL().toString(),
                    request.getMethod());
        }
    }

    @AfterReturning(returning = "returnObject", pointcut = "controllerLog()")
    public void doAfterControllerReturn(JoinPoint joinPoint, Object returnObject) {
        if (!log.isInfoEnabled()) {
            return;
        }
        log.info("CONTROLLER: METHOD SUCCESS: {}.{}, RETURN: {}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                returnObject == null ? "VOID" : returnObject);
    }

    @AfterThrowing(throwing = "ex", pointcut = "controllerLog()")
    public void throwException(JoinPoint joinPoint, Exception ex) {
        if (!log.isErrorEnabled()) {
            return;
        }
        log.error("EXCEPTION: NAME: {}, MESSAGE: {}, METHOD: {}.{}, ARGS: {}",
                ex.getClass().getSimpleName(),
                ex.getMessage(),
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                getArgsString(joinPoint));
    }

    @Before("serviceLog()")
    public void doBeforeService(JoinPoint joinPoint) {
        if (!log.isInfoEnabled()) {
            return;
        }
        log.info("SERVICE: METHOD: {}.{}, ARGS: {}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                getArgsString(joinPoint));
    }

    @AfterReturning(returning = "returnObject", pointcut = "serviceLog()")
    public void doAfterServiceReturn(JoinPoint joinPoint, Object returnObject) {
        if (!log.isInfoEnabled()) {
            return;
        }
        log.info("SERVICE: METHOD SUCCESS: {}.{}, RETURN: {}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                returnObject == null ? "VOID" : returnObject);
    }

    private String getArgsString(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        return args.length > 0 ? Arrays.toString(args) : "METHOD HAS NO ARGUMENTS";
    }
}
