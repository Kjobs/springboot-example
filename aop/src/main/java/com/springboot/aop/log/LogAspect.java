package com.springboot.aop.log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Modifier;

/**
 * @author kobs
 * @date 2019/12/5
 */
@Aspect
@Component
public class LogAspect {

    private final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    /**
     * 表示标注了特定注解（这里是自定义的注解CustomLogAnnotation）的方法连接点
     */
    @Pointcut("@annotation(com.springboot.aop.log.CustomLogAnnotation)")
    public void annotationLog() {}

    /**
     * 表达式说明（*表示匹配所有）
     * 表达式主体：execution()
     * arg0 : public（默认） —— 方法修饰符
     * arg1 : * —— 返回类型
     * arg2 : 方法名 —— “..”表示当前包及其子包，“*”匹配所有的类，“.*”表示匹配所有的方法
     * arg3 : ()中的内容是参数类型 ——  ()匹配没有参数；  (..)代表任意多个参数;(*)代表一个参数，但可以是任意型;(*,String)代表第一个参数为任何值,第二个为String类型
     * arg4 : 另外就是异常类型
     */
    @Pointcut("execution(public * com.springboot.aop.controller..*.*(..))")
    public void executionLog() {}

    /**
     * 前置增强：切入点之前的功能方法
     * 对于不同的切入点Pointcut支持&&、||、！等操作
     * @param joinPoint 连接点
     */
    @Before("annotationLog()")
    public void beforeMethod(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        logger.info(signature.getName());
        System.out.println("目标方法名为:" + signature.getName());
        System.out.println("目标方法所属类的简单类名:" + signature.getDeclaringType().getSimpleName());
        System.out.println("目标方法所属类的类名:" + signature.getDeclaringTypeName());
        System.out.println("目标方法声明类型:" + Modifier.toString(signature.getModifiers()));
        // 获取传入目标方法的参数列表
        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < args.length; i++) {
            System.out.println("第" + (i+1) + "个参数为:" + args[i]);
        }
        System.out.println("被代理的对象:" + joinPoint.getTarget());
        System.out.println("代理对象自己:" + joinPoint.getThis());
    }

    @After("annotationLog()")
    public void afterMethod(JoinPoint joinPoint) {
        // TODO: 后置通知具体功能实现
    }

    /**
     * 环绕通知：自定义目标执行的时机
     * @param proceedingJoinPoint 连接点，JoinPoint的子接口
     */
    @Around("executionLog()")
    public Object aroundLog(ProceedingJoinPoint proceedingJoinPoint) {
        Object result;
        try {
            // 前置通知
            System.out.println("目标方法执行前...");
            // 执行目标方法
            result = proceedingJoinPoint.proceed();
            logger.info(proceedingJoinPoint.getSignature().getName());
        } catch (Throwable e) {
            System.out.println("执行目标方法发生异常");
            throw new RuntimeException(e);
        }
        // 后置通知
        System.out.println("目标方法执行后...");
        return result;
    }
}
