package com.ming.learning.mybatis.test;

import java.sql.*;

/**
 * @Author ming
 * @time 2020/7/31 20:42
 */
public class JdbcDemo {

    public static void main(String[] args) {

        Connection connection = null;
        try{

            //加载数据库驱动
            Class.forName("com.mysql.jdbc.Driver");

            //获取连接
            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/ming","root","root");

            System.out.println("获取连接完毕");

            //查询语句
            PreparedStatement ps = connection.prepareStatement("select * from t_user where id = ?");
            //设置参数
            ps.setObject(1, 1);

            //执行获取结果
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                System.out.println(rs.getString("name"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        //加载数据库驱动

        //获取数据库连接connection = DriverManager




    }
}
