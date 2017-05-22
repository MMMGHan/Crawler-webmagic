package com.amoism.WebCrawler.Dao;

import Util.DbUtil;
import com.amoism.WebCrawler.News163;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class News163Dao {

    private static DbUtil dbUtil = new DbUtil();

    public static int addNews163(News163 news163)throws Exception{

        Connection con = dbUtil.getCon();

//        String sql = "insert into news_163 values(null,?,?,?,?,?,?)";
        String sql = "insert into news_163 values(null,?,?,?,?)";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1,news163.getTitle());
        pstmt.setString(2,news163.getContent());
        pstmt.setString(3,news163.getDate_source());
        pstmt.setString(4,news163.getTags());
//        pstmt.setInt(5,news163.getMark());
//        pstmt.setString(6, String.valueOf(news163.getUrl()));

        int result = pstmt.executeUpdate() ;
        dbUtil.close(pstmt,con);
        return result;
    }


}
