package com.ming.learning.mybatis.test;

import com.ming.learning.mybatis.framework.config.Configuration;
import com.ming.learning.mybatis.framework.config.MappedStatement;
import com.ming.learning.mybatis.framework.config.ParameterMapping;
import com.ming.learning.mybatis.framework.sqlNode.SqlNode;
import com.ming.learning.mybatis.framework.sqlSource.DynamicSqlSource;
import com.ming.learning.mybatis.framework.sqlSource.RawSqlSource;
import com.ming.learning.mybatis.framework.sqlSource.SqlSource;
import com.ming.learning.mybatis.po.User;
import org.apache.commons.dbcp.BasicDataSource;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;

/**
 * @Author ming
 * @time 2020/8/7 14:20
 */
public class MyBatisV2 {

    private Configuration configuration;
    private String namespace;
    private boolean isDynamic = false;

    /**
     * 面向过程 编程，
     *   先大体流程，再细化
     */
    @Test
    public void test(){
        //加载XML文件（全局配置文件和映射文件）
        loadXml("mybatis-config.xml");

        //执行查询
        Map<String,Object> params = new HashMap<>();
        params.put("username","风骚小妲己");
        params.put("sex","女");
        List<User> users = selectList("user.queryUserNameByParams",params);// 根据配置文件UserMapper 的namespace + 要查询statement id

        System.out.println(users);
    }


    private void loadXml(String location) {
        configuration = new Configuration();
        //TODO 解析xml文件，最终将信息封装到Configuration对象中

        /**
         * 分析：
         *    xml文件里都是一个标签对应一个标签
         *    因此，我们先获取文件的流对象。
         *    然后通过document对象来解析 (dom4j)
         */
        //获取全局配置文件对应的流对象。
        InputStream is = getResourceAsStream(location);

        //获取Document 对象 <使用的时dom4j 的>
        Document document = getDocument(is);

        //根据xml语义进行解析
        parseConfiguration(document.getRootElement());

    }

    /**
     * 解析config  xml配置文件
     * @param rootElement  <configuration>
     */
    private void parseConfiguration(Element rootElement) {

        Element environments = rootElement.element("environments");
        parseEnvironments(environments);

        //解析映射
        Element mappers = rootElement.element("mappers");
        parseConfigMappers(mappers);

    }

    /**
     * 解析全局配置文件中的mappers标签
     * @param mappers  <mappers></mappers>
     */
    private void parseConfigMappers(Element mappers) {
        List<Element> list = mappers.elements("mapper");

        for (Element element : list) {
            String resource = element.attributeValue("resource");

            //根据xml的路径，获取对应的输入流
            InputStream inputStream = getResourceAsStream(resource);

            //将流对象，转换成Document对象
            Document document = getDocument(inputStream);
            //针对Document对象，按照Mybatis的语义去解析Document
            parseMapper(document.getRootElement());
        }
    }

    /**
     * 解析映射文件的mapper信息
     * @param rootElement <mapper></mapper>
     */
    private void parseMapper(Element rootElement) {
        //statement id 是有 namespace + statement标签的id值组成的，用来保持唯一性，
        namespace = rootElement.attributeValue("namespace");

        //TODO 获取动态SQL标签 比如<sql>
        //TODO 获取其他标签
        List<Element> selectElements = rootElement.elements("select");
        for (Element selectElement : selectElements) {
            parseStatementElement(selectElement);
        }
    }

    /***
     * 解析statement 标签
     * @param selectElement <select></select>
     */
    private void parseStatementElement(Element selectElement) {
        String statementId = selectElement.attributeValue("id");
        if (statementId == null || "".equals(statementId)){
            //TODO 没有值的话，抛异常比较合适，完善的话要抛出异常
            return ;
        }

        //一个CRUD 标签对应一个MappedStatement对象
        //一个MappedStatement对象由一个statementId来标识，所以保证唯一性
        //StatementId = namespace + "." + CRUD 标签的id属性
        statementId = namespace + "." + statementId;


        //注意parameterType 参数可以不设置，也可以不解析。 因为，可以通过 参数对象反射出其具体类型，且再设置参数时调用的setObject，可以忽略
       /* String parameterType = selectElement.attributeValue("resultType");
        Class<?> parameterClass = resolveType(parameterType);*/

        String resultType = selectElement.attributeValue("resultType");
        //根据类型获取去结果类
        Class<?> resultClass = resolveType(resultType);

        String statementType = selectElement.attributeValue("statementType");
        statementType = statementType == null || "".equals(statementType) ? "prepared" : statementType;// 设置默认的statement类型，默认为预编译

        //TODO sqlSource 和sqlNode 的封装过程
        SqlSource sqlSource = createSqlSource(selectElement);

        //TODO 建议使用构造者模式去优化代码
        MappedStatement mappedStatement = new MappedStatement(statementId, statementType, resultType, resultClass, sqlSource);
        configuration.addMappedStatements(statementId, mappedStatement);
    }

    private SqlSource createSqlSource(Element selectElement) {
        //TODO 其他子标签的解析处理
        SqlSource sqlSource = parseScriptNode(selectElement);
        return  null;
    }

    private SqlSource parseScriptNode(Element selectElement) {
        //解析所有节点
        SqlNode mixedSqlNode =  parseDynamicTags(selectElement);

        SqlSource sqlSource ;
        //判断是否带有 ${} 或者 动态Sql标签
        if (isDynamic){
            sqlSource = new DynamicSqlSource(mixedSqlNode);
        } else {
            sqlSource = new RawSqlSource(mixedSqlNode);
        }

        return sqlSource;
    }

    private SqlNode parseDynamicTags(Element selectElement) {
        List<SqlNode> sqlNodes = new ArrayList<>();

        //获取select 标签的子元素  文本类型 获取Element类型
        return null;
    }

    /**
     * 根据全限定名获取Class对象
     * @param resultType
     * @return
     */
    private Class<?> resolveType(String resultType) {
        try {
            return Class.forName(resultType);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *  解析环境配置标签
     * @param environments <environments ></environments>
     */
    private void parseEnvironments(Element environments) {
        String aDefault = environments.attributeValue("default");//获取默认的环境

        List<Element> elements = environments.elements("environment");//获取环境的标签

        //然后根据 default 取得对应的 环境配置信息
        for (Element element : elements) {
            String id = element.attributeValue("id");
            if (aDefault.equals(id)){
                //只有时指定的 环境才继续解析

                //解析dataSource
                parseDataSource(element.element("dataSource"));
            }
        }



    }

    /**
     * 解析DataSource数据源配置
     * @param dataSource 对应<dataSource></dataSource> 标签
     */
    private void parseDataSource(Element dataSource) {
        String sourceType = dataSource.attributeValue("type");//使用的数据源类型 DBCP

        if ("DBCP".equals(sourceType)){
            BasicDataSource bds = new BasicDataSource();

            Properties properties = parseProperties(dataSource);
            bds.setDriverClassName(properties.getProperty("db.driver"));
            bds.setUrl(properties.getProperty("db.url"));
            bds.setUsername(properties.getProperty("db.username"));
            bds.setPassword(properties.getProperty("db.password"));

            configuration.setDataSource(bds);
        }

    }

    private Properties parseProperties(Element dataSource) {
        Properties properties = new Properties();

        List<Element> elements = dataSource.elements("property");
        for (Element element : elements) {
            properties.setProperty(element.attributeValue("name"), element.attributeValue("value"));
        }

        return properties;
    }

    private Document getDocument(InputStream is) {
        SAXReader reader = new SAXReader();

        try {
            Document document = reader.read(is);
            return document;
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return null;
    }

    private InputStream getResourceAsStream(String location) {
        return this.getClass().getClassLoader().getResourceAsStream(location);
    }

    public <T> List<T> selectList(String statementId, Object param) {

        List<T> resultList = new ArrayList<>();
        Connection conn = null;
        Statement statement=null;
        ResultSet rs = null;
        /**
         * 面向过程重思路
         *  1.连接的获取
         *  2.Sql的获取
         *  3.创建statement
         *  4.设置参数
         *  5.执行statement
         *  6. 处理结果
         */

        try {
            //获取statement相关的信息并封装刀MappedStatement
            MappedStatement mappedStatement = configuration.getMappedStatementById(statementId);
            //1.连接获取
            conn = getConnection();
            //TODO 2.Sql的获取 （SQLSource）
            String sql = getSql(mappedStatement);
            //3 创建statement
            statement = createStatement(conn,mappedStatement, sql);
            //TODO 4.设置参数
            setParameters(param,statement, mappedStatement);
            //5.执行statement
            rs = executeQuery(statement, sql);
            //6. 处理结果
            resultHandler(rs, resultList, mappedStatement);

        }catch (Exception e){
            e.printStackTrace();
        } finally {
            if (rs != null){
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (statement != null){
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return resultList;
    }

    private ResultSet executeQuery(Statement statement, String sql) throws Exception{
        ResultSet rs = null;
        if( statement instanceof PreparedStatement){
            PreparedStatement ps = (PreparedStatement) statement;
            rs  = ps.executeQuery();
        }else if (statement instanceof CallableStatement){
            CallableStatement cs = (CallableStatement) statement;
            rs = cs.executeQuery();
        } else {
            rs = statement.executeQuery(sql);
        }
        return rs;
    }

    /**
     * 结果处理
     *      通过保存在 xml中的 返回结果类型，
     *      然后通过类的构造器 创建对象，
     *      获取结果集列，通过暴力破解。给对象赋值
     * @param rs
     * @param resultList
     * @param mappedStatement
     * @param <T>
     * @throws Exception
     */
    private <T> void resultHandler(ResultSet rs, List<T> resultList, MappedStatement mappedStatement) throws Exception{

        //一般都是通过构造器取创建对象
        Class clazz = mappedStatement.getResultClass();
        Constructor<?> constructor = clazz.getDeclaredConstructor();

        Object result = null;
        while(rs.next()){

            //通过构造器 创建对象
            result = constructor.newInstance();

            //获取结果集中列的信息
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            for (int i = 0; i < columnCount; i++) {
                String columnName = metaData.getColumnName(i+1);

                //通过反射给指定列对应的属性名称赋值
                //列名和属性名要一致
                Field field = clazz.getDeclaredField(columnName);
                field.setAccessible(true);//暴力破解，破坏封装，可以访问私有成员
                field.set(result, rs.getObject(columnName));
            }
            resultList.add((T) result);
        }
    }

    /**
     * 设置参数
     *
     * @param param 要设置参数对象，有可能是基本类型对象，也有可能是map集合
     * @param statement  statement对象
     * @param mappedStatement  对应映射文件的配置对象
     */
    private void setParameters(Object param, Statement statement, MappedStatement mappedStatement) throws Exception{

        //得先判断  statement是什么类型 的。如果预编译的,才能传参数
        if (statement instanceof PreparedStatement){
            PreparedStatement preparedStatement = (PreparedStatement) statement;
            if (param instanceof Integer || param instanceof String || param instanceof Long
                    || param instanceof Double || param instanceof Float){

                preparedStatement.setObject(1,param);
            } else if (param instanceof Map){
                //如果是map，先强转成map
                Map<String, Object> map = (Map<String, Object>) param;

                //TODO 需要再解析SQl中时，把sql 源信息中的#{} 封装到参数集合List<ParameterMapping>
                List<ParameterMapping> parameterMappings = new ArrayList<>();

                for (int i = 0; i < parameterMappings.size(); i++) {
                    String name = parameterMappings.get(i).getName();
                    Object value = map.get(name);

                    preparedStatement.setObject(i+1, value);
                }
            }
        }
    }

    /**
     * mappedStatement.getStatementType() 创建对应的 statement
     *   因为有可能是预处理，因此 参数得传sql
     *
     * @param conn
     * @param mappedStatement 对应statement对象
     * @param sql  sql语句
     * @return
     */
    private Statement createStatement(Connection conn, MappedStatement mappedStatement, String sql) throws Exception{
        //mappedStatement.getStatementType() 创建对应的 statement
        String statementType = mappedStatement.getStatementType();
        if ("prepared".equals(statementType)){
            return conn.prepareStatement(sql);
        } else if ("callable".equals(statementType)){
            return conn.prepareCall(sql);
        } else {
            return conn.createStatement();
        }
    }


    private String getSql(MappedStatement mappedStatement) throws Exception{
        return null;
    }

    private Connection getConnection() throws Exception {
        //直接从 Configuration 中配置的 DataSource获取connection
        DataSource dataSource = configuration.getDataSource();
        return  dataSource.getConnection();
    }
}
