package com.ming.learning.mybatis.test;

import com.ming.learning.mybatis.po.User;
import org.apache.commons.dbcp.BasicDataSource;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;

/**
 * @Author ming
 * @time 2020/7/31 20:46
 */
public class MybatisV1 {


    private Properties prop = new Properties();


    @Test
    public void test() {

      List<User> list =  selectUserList("queryUserById",1);

//       List<User> list =  selectUserList("queryUserBySex","男");

        //查询多个参数。根据性别加年龄查询

//        Map<String, Object> param = new HashMap<>();
//        param.put("sex", "男");
//        param.put("age", 18);
//        List<User> list = selectUserList("queryUserBySexAge", param);
        System.out.println(list);
    }

    public <T> List<T> selectUserList(String statementId, Object param) {
        List<T> resultList = new ArrayList<>();
        //加载配置文件
        loadProperties("jdbc.properties");

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            //获取数据库连接 connection 使用BasicDataSource
            BasicDataSource dataSource = getBasicDataSource();
            conn = dataSource.getConnection();

            //查询条件
            ps = conn.prepareStatement(prop.getProperty("db.sql" + "." + statementId));

            //设置参数
            setParam(statementId, param, ps);

            //执行查询
            rs = ps.executeQuery();

            //处理结果
            doHandlerResult(statementId, resultList, rs);

        } catch (Exception e) {
            e.printStackTrace();
        }


        return resultList;
    }

    private <T> void doHandlerResult(String statementId, List<T> resultList, ResultSet rs) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException, NoSuchFieldException {
        String resultType = prop.getProperty("db.sql" + "." + statementId + ".resultType");
        Class<?> clazz = Class.forName(resultType);
        Object result = null;
        while (rs.next()) {
            result = clazz.newInstance();

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            for (int i = 0; i < columnCount; i++) {
                String columnName = metaData.getColumnName(i + 1);

                //通过反射给指定列对应的属性名称赋值
                //列名和属性名要一致
                Field filed = clazz.getDeclaredField(columnName);
                //暴力破解,破坏分装，可以访问私有成员
                filed.setAccessible(true);

                filed.set(result, rs.getObject(columnName)); //反射 给每个属性
            }

            resultList.add((T) result);
        }
    }

    private void setParam(String statementId, Object param, PreparedStatement ps) throws SQLException {
         if (param instanceof Map) {
            String queryParam = prop.getProperty("db.sql" + "." + statementId + ".queryParam");
            String[] queryParamArrays = queryParam.split(",");
            if (queryParamArrays != null && queryParamArrays.length > 0){
                Map<String, Object> map = (Map<String, Object>) param;
                for (int i = 0; i < queryParamArrays.length; i++) {
                    ps.setObject(i + 1, map.get(queryParamArrays[i]));
                }
            }

        } else {
            ps.setObject(1, param);
        }
    }

    private BasicDataSource getBasicDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(prop.getProperty("jdbc.mysql.driver"));
        dataSource.setUrl(prop.getProperty("jdbc.mysql.url"));
        dataSource.setUsername(prop.getProperty("jdbc.mysql.username"));
        dataSource.setPassword(prop.getProperty("jdbc.mysql.pwd"));
        return dataSource;
    }

    /**
     * 加载配置文件
     *
     * @param source 配置文件路径
     */
    private void loadProperties(String source) {
        InputStream inputStream = null;
        try {
            inputStream = this.getClass().getClassLoader().getResourceAsStream(source);
            prop.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
