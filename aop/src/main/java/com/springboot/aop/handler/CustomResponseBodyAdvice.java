package com.springboot.aop.handler;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;


import com.alibaba.fastjson.JSON;

import java.lang.reflect.AnnotatedElement;
import java.util.Arrays;

/**
 * 统一拦截，并对响应结果封装成统一格式
 *
 * @author kobs
 * @date 2019/12/4
 */
@RestControllerAdvice(annotations = {RestController.class})
public class CustomResponseBodyAdvice  implements ResponseBodyAdvice {

    /**
     * 自定义添加需要拦截的注解
     */
    private static final Class[] ANNOTATIONS = {
            RequestMapping.class,
            GetMapping.class,
            PostMapping.class
    };

    /**
     * 该方法表示哪些请求要执行beforeBodyWrite
     *
     * @param methodParameter 方法参数
     * @param aClass 转换类型
     * @return true--执行，false--不执行
     */
    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        // 获取方法的注解
        AnnotatedElement element = methodParameter.getAnnotatedElement();
        // 若方法有拦截方法数组中的某一个注解，返回true
        return Arrays.stream(ANNOTATIONS).anyMatch(a -> a.isAnnotation() && element.isAnnotationPresent(a));
    }

    /**
     * 封装响应结果的具体业务方法
     *
     * @param o 实体对象
     * @param methodParameter 方法参数
     * @param mediaType 传输类型
     * @param aClass 转换类型
     * @param serverHttpRequest 请求数据
     * @param serverHttpResponse 响应数据
     * @return 结果封装类
     */
    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        serverHttpResponse.getHeaders().setContentType(mediaType);
        serverHttpResponse.setStatusCode(HttpStatus.OK);
        if (o instanceof CommonResponse) {
            return o;
        } else {
            CommonResponse response = new CommonResponse();
            response.setCode(HttpStatus.OK.value());
            response.setData(o);
            response.setMsg("Request success!");
            response.setSuccess(true);
            if (o instanceof String) {
                return JSON.toJSONString(response);
            }
            return response;
        }
    }
}
