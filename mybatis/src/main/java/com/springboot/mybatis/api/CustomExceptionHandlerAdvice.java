package com.springboot.mybatis.api;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * 异常和错误捕捉
 *
 * @author kobs
 */
@RestControllerAdvice(annotations = {RestController.class})
public class CustomExceptionHandlerAdvice {

    public final Logger logger = LoggerFactory.getLogger(CustomExceptionHandlerAdvice.class);

    /**
     * 捕捉服务器内部错误，这里的Exception类型可以自定义
     *
     * @param exception 运行时异常
     */
    @ExceptionHandler(value = {RuntimeException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public AbstractResponse serveError(RuntimeException exception) {
        logger.error("Server error! - '{}'", exception.getMessage());
        return CommonReturnResponse.error(exception);

    }
}
