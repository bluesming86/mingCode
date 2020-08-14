package com.ming.learning.mybatis.framework.sqlSource;

import com.ming.learning.mybatis.framework.config.BoundSql;
import com.ming.learning.mybatis.framework.config.ParameterMapping;

import java.util.List;

/**
 * @Author ming
 * @time 2020/8/14 15:25
 */
public class StaticSqlSource implements SqlSource {

    private String sql;

    private List<ParameterMapping> parameterMappings;

    public StaticSqlSource(String sql, List<ParameterMapping> parameterMappings) {
        this.sql = sql;
        this.parameterMappings = parameterMappings;
    }

    @Override
    public BoundSql getBoundSql(Object param) {
        return null;
    }
}
