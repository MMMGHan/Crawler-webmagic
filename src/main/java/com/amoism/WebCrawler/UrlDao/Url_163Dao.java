package com.amoism.WebCrawler.UrlDao;

import Util.UrlUtil;
import com.amoism.WebCrawler.Url;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * Created by amoism on 2017/5/21.
 */
public class Url_163Dao {
    private static UrlUtil urlUtil = new UrlUtil();

    public static int addurl(Url url)throws Exception{

        Connection con = urlUtil.getCon();

        String sql = "insert into urlhub_163 values(null,?,?)";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1, String.valueOf(url.getUrl()));
        pstmt.setInt(2,url.getMark());

        int result = pstmt.executeUpdate() ;
        urlUtil.close(pstmt,con);
        return result;
    }

    public static boolean selecturl(String url)throws Exception{
        Connection con = urlUtil.getCon();
        String sql = "select * from urlhub_163 WHERE mark = 0 AND urls = ? ";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1,url);

        boolean result = pstmt.execute() ;
        urlUtil.close(pstmt,con);
        return result;
    }

    public static int updatemark(String url)throws Exception{
        Connection con = urlUtil.getCon();
        String sql = "UPDATE urlhub_163 set mark = 1 WHERE mark = 0 AND urls = ?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1,url);

        int result = pstmt.executeUpdate() ;
        urlUtil.close(pstmt,con);
        return result;
    }
}
