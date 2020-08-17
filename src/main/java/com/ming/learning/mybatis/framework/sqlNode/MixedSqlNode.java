package com.ming.learning.mybatis.framework.sqlNode;

import com.ming.learning.mybatis.framework.config.DynamicContext;

import java.util.List;

/**
 * 封装了一组SqlNode
 * @Author ming
 * @time 2020/8/14 15:40
 */
public class MixedSqlNode implements SqlNode {

    private List<SqlNode> sqlNodeList;

    public MixedSqlNode(List<SqlNode> sqlNodeList) {
        this.sqlNodeList = sqlNodeList;
    }

    @Override
    public void apply(DynamicContext context) {
        for (SqlNode sqlNode : sqlNodeList) {
            sqlNode.apply(context);
        }
    }
}
