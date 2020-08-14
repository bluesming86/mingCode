package com.ming.learning.mybatis.framework.sqlNode;

import com.ming.learning.mybatis.framework.config.DynamicContext;

/**
 * @Author ming
 * @time 2020/8/13 17:23
 */
public interface SqlNode {
    /**
     * 处理Sql节点
     * @param dc
     */
    void apply(DynamicContext dc);
}
