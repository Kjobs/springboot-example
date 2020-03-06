package com.springboot.redis.demo.config;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;

/**
 * @author kobs
 * @date 2019/10/15
 */
@Configuration
public class CacheKeyGenerator implements KeyGenerator {
    /**
     * 自定义缓存key的生成策略。默认的生成策略是看不懂的(乱码内容) 通过Spring 的依赖注入特性进行自定义的配置注入
     * 并且此类是一个配置类，可以更多程度的自定义配置
     *
     * @return
     */
    @Override
    public Object generate(Object target, Method method, Object... params) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(target.getClass().getName()).append("::");
        stringBuilder.append(method.getName()).append('.');
        for (Object obj : params) {
            stringBuilder.append(obj.toString()).append('-');
        }
        return stringBuilder.toString();
    }
}
