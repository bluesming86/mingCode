package com.ming.learning.mybatis.framework.config;

import java.util.HashMap;
import java.util.Map;

/**
 * 动态上下文，
 *   用来存放解析过程中的sql语句的拼接 和参数集合
 * @Author ming
 * @time 2020/8/14 15:36
 */
public class DynamicContext {
    private StringBuffer sb = new StringBuffer();
    private Map<String, Object> bindings = new HashMap<>();

    public DynamicContext(Object param) {
        //为了处理${}时，需要用到的入参对象
        this.bindings.put("_parameter", param);
    }

    public String getSql() {
        return sb.toString();
    }

    public void appendSql(String sqlText) {
        this.sb.append(sqlText);
        this.sb.append(" ");//加个空格，保险作用，怕两个文本挨着会报错
    }

    public Map<String, Object> getBindings() {
        return bindings;
    }

}
