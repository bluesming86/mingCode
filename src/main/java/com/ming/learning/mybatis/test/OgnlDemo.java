package com.ming.learning.mybatis.test;

import com.ming.learning.mybatis.po.User;
import ognl.Ognl;
import ognl.OgnlContext;
import ognl.OgnlException;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author ming
 * @time 2020/8/17 11:03
 */
public class OgnlDemo {

    //非根元素
    @Test
    public void testOgnl1() {

        OgnlContext context = new OgnlContext();
        /**
         * 1.OgnlContext放入基本变量数据
         */
        //放入数据
        context.put("cn", "China");

        //获取数据（map）
        String value = (String) context.get("cn");
        System.out.println(value);

        /**
         * 2. OgnlContext放入对象数据
         */
    }

    @Test
    public void testOgnl2() throws OgnlException {

        OgnlContext context = new OgnlContext();

        User user = new User();
        user.setId(100);
        user.setName("Jack");
        context.setRoot(user);

        //根元素直接使用id，不需要加#号
        Object ognl = Ognl.parseExpression("id");//构建Ognl表达式
        Object value = Ognl.getValue(ognl, context, context.getRoot());//解析表达式
        System.out.println(value);

    }

    //ognl对静态方法调用的支持
    @Test
    public void testOgnl3() throws OgnlException {
        //创建一个Ognl上下文对象
        OgnlContext context = new OgnlContext();

        //Ognl 表达式语言，调用类的静态方法
        //由于Math类在开发中比较常用，所以也可以这样写
        Object ognl = Ognl.parseExpression("@@floor(10.9)");
        Object value = Ognl.getValue(ognl, context, context.getRoot());
        System.out.println(value);
    }


    @Test
    public void testOgnl4() throws OgnlException {
        //创建一个Ognl上下文对象
        OgnlContext context = new OgnlContext();

        User user = new User();
        user.setId(100);
//        user.setName("Jack");
        context.setRoot(user);

        Object ognl = Ognl.parseExpression("name != null and name != '' ");//构建
        Object value = Ognl.getValue(ognl, context, context.getRoot());
        System.out.println(value);
    }

    @Test
    public void testOgnl5() throws OgnlException {
        //创建一个Ognl上下文对象
        OgnlContext context = new OgnlContext();

        User user = new User();
        user.setId(100);
        user.setName("Jack");

        Map<String, Object> map = new HashMap<>();
        map.put("_parameter", user);

        context.setRoot(user);

        Object ognl = Ognl.parseExpression("name != null and name != '' ");//构建
        Object value = Ognl.getValue(ognl, context, context.getRoot());
        System.out.println(value);
    }
}
