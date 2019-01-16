package com.petecat.interchan.core.exception;

import com.petecat.interchan.protocol.Result;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Stream;

/**
 * @ClassName: ExceptionHandler
 * @Description:通用异常类
 * @author: mhuang
 * @date: 2017年7月12日 上午9:15:20
 */
@ControllerAdvice
public class CommonExceptionHandler {


    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private Environment env;

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Result<?> defaultErrorHandler(HttpServletRequest request, Exception e) {

        logger.error("---Exception Handler---Host {} invokes url {} ERROR: {}", request.getRemoteHost(), request.getRequestURL(), e.getMessage());

        Result result = new Result<>();

        if (e instanceof BusinessException) {
            BusinessException business = (BusinessException) e;
            result.setCode(business.getCode());
            result.setMessage(business.getMessage());
        } else {
            result.setCode(Result.SYS_FAILD);
            Stream<String> stream = Stream.of(env.getActiveProfiles());
            /**
             * 生产
             */
            if (stream.filter(profile -> StringUtils.containsIgnoreCase("prod", profile)).count() > 0) {
                result.setMessage("服务器异常");
            } else {
                result.setMessage(e.getMessage() + " for " + env.getProperty("spring.application.name"));
                StackTraceElement[] stes = e.getStackTrace();
                result.setData(stes);
            }
        }
        return result;
    }
}
