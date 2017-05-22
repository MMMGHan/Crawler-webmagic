package com.amoism.WebCrawler.Dao;

import Util.DbUtil;
import com.amoism.WebCrawler.Taobao;

import java.sql.Connection;
import java.sql.PreparedStatement;



public class TaobaoDao {
    private static DbUtil dbUtil = new DbUtil();

    public static int addTaobao(Taobao Taobao)throws Exception{

        Connection con = dbUtil.getCon();
        String sql = "insert into taobao values(null,?,?,?,?)";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1, Taobao.getCommodity_name());
        pstmt.setString(2, Taobao.getTag());
        pstmt.setString(3, Taobao.getPrice());
        pstmt.setString(4, Taobao.getContent());

        int result = pstmt.executeUpdate() ;
        dbUtil.close(pstmt,con);
        return result;
    }
}
