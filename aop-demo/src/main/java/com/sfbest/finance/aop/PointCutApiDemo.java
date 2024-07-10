package com.sfbest.finance.aop;

import com.sfbest.finance.aop.base.DefaultEchoService;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.StaticMethodMatcherPointcut;

import java.lang.reflect.Method;

public class PointCutApiDemo {

    public static void main(String[] args) {
        ProxyFactory factory = new ProxyFactory();
        factory.setTarget(new DefaultEchoService());
        Pointcut pc = new StaticMethodMatcherPointcut() {
            @Override
            public boolean matches(Method method, Class<?> targetClass) {
                return method.getName().equals("echo");
            }
        };
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(pc, new MethodBeforeAdvice() {
            @Override
            public void before(Method method, Object[] args, Object target) throws Throwable {
                System.out.println("before invoke ------------------");
            }
        });

        factory.addAdvisor(advisor);

        DefaultEchoService proxy = (DefaultEchoService)factory.getProxy();
        System.out.println(proxy.echo("nihao"));
    }
}
