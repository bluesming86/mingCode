package com.ming.learning.spring;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Author ming
 * @time 2020/8/18 15:54
 */
@SuppressWarnings("restriction")
public class JdkProxyTest {

    public static void main(String[] args) {

        JavaProxyInterface javaProxyInterface = new ConcreteClass();

        JavaProxyInterface newJavaProxy = (JavaProxyInterface) Proxy.newProxyInstance(JdkProxyTest.class.getClassLoader(), new Class[]{JavaProxyInterface.class}, new MyInvocationHandler(javaProxyInterface));

        //这里可以看到这个类以及被代理，再执行方法前 执行aopMethod().
        //这里需要注意的是oneDay()和oneDayFinal()的区别。oneDayFinal的方法aopMethod执行1次，oneDay的aopMethod执行1次
        newJavaProxy.gotoSchool();
        newJavaProxy.gotoWork();
        newJavaProxy.oneDayFinal();
        newJavaProxy.oneDay();

    }
}


interface JavaProxyInterface{
    void gotoSchool();
    void gotoWork();
    void oneDay();
    void oneDayFinal();
}

/**
 * 需要被代理的类，实现顶层接口
 */
class ConcreteClass implements JavaProxyInterface{
    @Override
    public void gotoSchool() {
        System.out.println("gotoSchool");
    }

    @Override
    public void gotoWork() {
        System.out.println("gotoWork");
    }

    @Override
    public void oneDay() {
        gotoSchool();
        gotoWork();
    }

    @Override
    public void oneDayFinal() {
        gotoSchool();
        gotoWork();
    }
}

class MyInvocationHandler implements InvocationHandler{

    JavaProxyInterface javaProxy;

    public MyInvocationHandler(JavaProxyInterface javaProxyInterface) {
        this.javaProxy = javaProxyInterface;
    }

    private void aopMethod(){
        System.out.println("before method");
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        aopMethod();
        return method.invoke(javaProxy, args);
    }
}
