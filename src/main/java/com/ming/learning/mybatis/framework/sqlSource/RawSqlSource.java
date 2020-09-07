package com.ming.learning.mybatis.framework.sqlSource;

import com.ming.learning.mybatis.framework.config.BoundSql;
import com.ming.learning.mybatis.framework.config.DynamicContext;
import com.ming.learning.mybatis.framework.sqlNode.SqlNode;

/**
 * 封装了 没有${}和动态标签的
 * 注意事项：
 *   当处理#{}时，只需要处理一次就可以获取对应的Sql语句
 *
 *   select * from user where id = #{}
 *
 *   处理后 select * from user where id = ?
 *
 *   因此只处理一次就够
 *
 * @Author ming
 * @time 2020/8/14 15:25
 */
public class RawSqlSource implements SqlSource {

    //一个静态的sqlSource (StaticSqlSource)
    private SqlSource  sqlSource;

    public RawSqlSource(SqlNode mixedSqlNode) {
        //真正处理#{}的逻辑 要放再构造方法中
        //把处理之后的结果，封装成一个静态的sqlSource (StaticSqlSource)

        //1.处理所有的Sql节点，获取合并之后的完整的sql语句（可能还带有#{}）
        DynamicContext context = new DynamicContext(null);
        mixedSqlNode.apply(context);
        String sqlText = context.getSql();//合并后的sql语句
        //2.调用SqlSource的处理逻辑，对#{}进行处理

    }

    @Override
    public BoundSql getBoundSql(Object param) {

        return null;
    }
}
