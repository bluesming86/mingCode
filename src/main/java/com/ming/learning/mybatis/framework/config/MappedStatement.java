package com.ming.learning.mybatis.framework.config;

import com.ming.learning.mybatis.framework.sqlSource.SqlSource;

/**
 * 封装了statment标签中的信息
 *     标签为 select 、insert 、 update、 delete 标签
 * @Author ming
 * @time 2020/8/11 20:22
 */
public class MappedStatement {

    private String statementId;//对应映射文件中的 namespace + id

    private String statementType;//statement类型 即prepared预编译，callable存储过程执行，statement 普通不带参数  对应映射文件中的 statementType属性

    private String resultType;//结果返回类型  对应映射文件中的 statementType属性

    private Class resultClass;//结果返回类  对应映射文件中的 statementType属性

    private SqlSource sqlSource;//Sql 源信息，一个Statement对应一个 SqlSource

    public MappedStatement(String statementId, String statementType, String resultType, Class resultClass, SqlSource sqlSource) {
        this.statementId = statementId;
        this.statementType = statementType;
        this.resultType = resultType;
        this.resultClass = resultClass;
        this.sqlSource = sqlSource;
    }

    public String getStatementId() {
        return statementId;
    }

    public void setStatementId(String statementId) {
        this.statementId = statementId;
    }

    public String getStatementType() {
        return statementType;
    }

    public void setStatementType(String statementType) {
        this.statementType = statementType;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public Class getResultClass() {
        return resultClass;
    }

    public void setResultClass(Class resultClass) {
        this.resultClass = resultClass;
    }

    public SqlSource getSqlSource() {
        return sqlSource;
    }

    public void setSqlSource(SqlSource sqlSource) {
        this.sqlSource = sqlSource;
    }
}
