package Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * Created by amoism on 2017/5/20.
 */
public class UrlUtil {

    private static String jdbcName= "com.mysql.jdbc.Driver";
    //数据库地址
    private static String dbUrl="jdbc:mysql://localhost:3306/UrlHub";
    //用户名
    private static String dbUserName = "root";
    //密码
    private static String dbPassword = "kkkkk";



    /**
     * 获取数据库连接
     * @return
     * @throws Exception
     */

    public Connection getCon()throws Exception{
        Class.forName(jdbcName);
        Connection con = DriverManager.getConnection(dbUrl,dbUserName,dbPassword);
        return con;
    }

    public void close(Statement stmt, Connection con) throws Exception{
        if (stmt!=null)
        {
            stmt.close();
            if (con!=null){
                con.close();
            }
        }
    }
}
