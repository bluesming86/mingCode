import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @Author ming
 * @time 2020/8/1 9:31
 */
public class Test {
    public static void main(String[] args) {

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet  rs = null;
        try{

            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/ming","root","root");
            System.out.println("���ӳɹ�");
            ps = con.prepareStatement("select * from t_user where id = ?");
            ps.setObject(1,1);
            rs = ps.executeQuery();
            while(rs.next()){
                System.out.println(rs.getString("name"));
            }

        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(con != null){
                try{
                    con.close();
                    con = null;
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
            if(ps != null){
                try{
                    ps.close();
                    ps = null;
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
            if(rs  != null){
                try{
                    rs.close();
                    rs = null;
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }

    }
}
