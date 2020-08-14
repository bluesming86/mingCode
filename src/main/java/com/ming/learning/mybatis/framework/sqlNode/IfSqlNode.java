package com.ming.learning.mybatis.framework.sqlNode;

import com.ming.learning.mybatis.framework.config.DynamicContext;

/**
 * 封装了带有if标签的动态标签
 * @Author ming
 * @time 2020/8/14 15:40
 */
public class IfSqlNode implements SqlNode {

    private String test;

    private SqlNode mixedSqlNode;

    public IfSqlNode(String test, SqlNode mixedSqlNode) {
        this.test = test;
        this.mixedSqlNode = mixedSqlNode;
    }

    @Override
    public void apply(DynamicContext context) {

    }
}
