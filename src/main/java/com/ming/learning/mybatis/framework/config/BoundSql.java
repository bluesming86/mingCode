package com.ming.learning.mybatis.framework.config;

import java.util.ArrayList;
import java.util.List;

/**
 * 封装解析Sql信息后的Sql 语句和#{}转化的参数集合
 *
 * @Author ming
 * @time 2020/8/14 15:19
 */
public class BoundSql {
    //已经解析完成的sql 语句
    private String sql;
    private List<ParameterMapping> parameterMappings = new ArrayList<>();

    public BoundSql(String sql, List<ParameterMapping> parameterMappings) {
        this.sql = sql;
        this.parameterMappings = parameterMappings;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public List<ParameterMapping> getParameterMappings() {
        return parameterMappings;
    }

    public void addParameterMappings(ParameterMapping parameterMapping) {
        this.parameterMappings.add(parameterMapping);
    }
}
