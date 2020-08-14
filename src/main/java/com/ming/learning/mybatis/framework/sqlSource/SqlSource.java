package com.ming.learning.mybatis.framework.sqlSource;

import com.ming.learning.mybatis.framework.config.BoundSql;

/**
 * 封装了SQL源信息并且可以解析#{}和${}
 *       一个statment标签对应一个 SqlSource
 *       一个SqlSurce 对应一棵 SqlNode 树。
 */
public interface SqlSource {

    /**
     * 针对封装的Sql信息，去进行解析，获取可以直接执行的Sql语句
     * @param param 参数
     * @return
     */
    BoundSql getBoundSql(Object param);
}
