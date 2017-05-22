package com.amoism.WebCrawler.Dao;

import Util.DbUtil;
import com.amoism.WebCrawler.NewsSohu;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * Created by amoism on 2017/5/16.
 */
public class NewsSohuDao {
    private static DbUtil dbUtil = new DbUtil();

    public static int addNewsSohu(NewsSohu newsSohu)throws Exception{
        Connection con = dbUtil.getCon();
        String sql = "insert into news_sohu values(null,?,?,?,?,?)";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1,newsSohu.getTitle());
        pstmt.setString(2,newsSohu.getContent());
        pstmt.setString(3,newsSohu.getDate());
        pstmt.setString(4,newsSohu.getSource());
        pstmt.setString(5,newsSohu.getTags());

        int result = pstmt.executeUpdate() ;
        dbUtil.close(pstmt,con);
        return result;
    }
}
