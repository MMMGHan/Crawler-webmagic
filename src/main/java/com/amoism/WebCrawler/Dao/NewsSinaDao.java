package com.amoism.WebCrawler.Dao;

import Util.DbUtil;
import com.amoism.WebCrawler.NewsSina;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class NewsSinaDao {

    private static DbUtil dbUtil = new DbUtil();

    public static int addNewsSina(NewsSina newsSina)throws Exception{

        Connection con = dbUtil.getCon();

        String sql = "insert into news_sina values(null,?,?,?,?)";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1,newsSina.getTitle());
        pstmt.setString(2,newsSina.getContent());
        pstmt.setString(3,newsSina.getDate_source());
        pstmt.setString(4,newsSina.getTags());

        int result = pstmt.executeUpdate() ;
        dbUtil.close(pstmt,con);
        return result;
    }
}
