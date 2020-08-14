package com.ming.learning.mybatis.framework.sqlSource;

import com.ming.learning.mybatis.framework.config.BoundSql;
import com.ming.learning.mybatis.framework.sqlNode.SqlNode;

/**
 * 封装带${}和动态标签的Sql信息，并提供对他们的处理接口
 *  注意事项：
 *    每一次处理${} 或者动态标签，都要根据入参对象，重新去生成信息sql语句，所以每一次都要调用getBoundSql方法
 *
 * @Author ming
 * @time 2020/8/14 15:24
 */
public class DynamicSqlSource implements SqlSource {
    //封装了带有${}或者动态标签的Sql脚本（时树状结构），
    private SqlNode mixedSqlNode;

    public DynamicSqlSource(SqlNode mixedSqlNode) {
        this.mixedSqlNode = mixedSqlNode;
    }

    @Override
    public BoundSql getBoundSql(Object param) {


        return null;
    }
}
