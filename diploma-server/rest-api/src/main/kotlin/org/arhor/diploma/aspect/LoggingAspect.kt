package org.arhor.diploma.aspect

import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.annotation.Pointcut
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.lang.invoke.MethodHandles

@Aspect
@Component
class LoggingAspect {

    @Before("execution(* *(.., @org.springframework.web.bind.annotation.ExceptionHandler (*), ..))")
    fun logExceptionHandling(joinPoint: JoinPoint) {
        for (arg in joinPoint.args) {
            if (arg is Throwable) {
                log.error(arg.message, arg)
            }
        }
    }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.ExceptionHandler)")
    fun exceptionHandlerMethod() { /* no-op */ }

    companion object {
        private val log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass())
    }
}
