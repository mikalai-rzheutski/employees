package com.mastery.java.task.logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.IntStream;

@Component
@Aspect
@Log4j2
public class Logger {

    @Before("@annotation(com.mastery.java.task.logger.annotations.LogMethodCall)")
    public void logMethod(JoinPoint joinPoint) {
        log.info("METHOD CALL: {}", () -> methodInfoAsJson(joinPoint));
    }

    @Pointcut("within(@com.mastery.java.task.logger.annotations.LogAllExceptionHandlers *)")
    public void beanAnnotatedWithLogAllExceptionHandlers() {
    }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.ExceptionHandler) && args(exception,..)")
    public void exceptionHandlers(Exception exception) {
    }

    @Pointcut("exceptionHandlers(exception) && beanAnnotatedWithLogAllExceptionHandlers()")
    public void exceptionHandlersInsideAClassMarkedWithLogAllExceptionHandlers(Exception exception) {
    }

    @AfterReturning(pointcut = "exceptionHandlersInsideAClassMarkedWithLogAllExceptionHandlers(exception)",
                    returning = "result")
    public void logExceptionHandler(JoinPoint jp,
                                    Object result,
                                    Exception exception) {
        log.error("EXCEPTION OCCURRED. Error message: {}" + System.lineSeparator() + "{}",
                  () -> asJsonString(result), () -> exceptionStackTraceToString(exception));
    }

    private String exceptionStackTraceToString(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.toString();
    }

    private String methodInfoAsJson(JoinPoint joinPoint) {

        @Data
        @AllArgsConstructor
        class MethodInfo {
            private String className;

            private String methodName;

            private Map<String, Object> arguments;
        }

        String className = joinPoint.getTarget().getClass().getName();
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String methodName = methodSignature.getName();
        String[] parameterNames = methodSignature.getParameterNames();
        Object[] args = joinPoint.getArgs();
        Map<String, Object> arguments = new LinkedHashMap<>();
        IntStream.range(0, parameterNames.length).forEach(i -> arguments.put(parameterNames[i], args[i]));
        return asJsonString(new MethodInfo(className, methodName, arguments));
    }

    private String asJsonString(Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (JsonProcessingException e) {
            return "Cannot generate json of " + obj.getClass().getSimpleName();
        }
    }
}
