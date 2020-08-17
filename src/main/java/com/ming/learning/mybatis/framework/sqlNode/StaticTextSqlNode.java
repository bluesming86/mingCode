package com.ming.learning.mybatis.framework.sqlNode;

import com.ming.learning.mybatis.framework.config.DynamicContext;

/**
 * 封装了 不带有${} 的sql文本
 * @Author ming
 * @time 2020/8/14 15:39
 */
public class StaticTextSqlNode implements SqlNode {

    private String sqlText;

    public StaticTextSqlNode(String sqlText) {
        this.sqlText = sqlText;
    }

    @Override
    public void apply(DynamicContext context) {
        context.appendSql(sqlText);
    }
}
