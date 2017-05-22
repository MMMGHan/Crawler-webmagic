package com.amoism.WebCrawler.Dao;

import Util.DbUtil;
import com.amoism.WebCrawler.CsdnBlog;

import java.sql.*;

/**
 * Created by amoism on 2017/5/13.
 */
public class CsdnBlogDao {

    private static DbUtil dbUtil = new DbUtil();

    public int addCsdnBlog(CsdnBlog csdnBlog) throws Exception {
        Connection con = dbUtil.getCon();
        String sql = "INSERT INTO csdnblog (`titles`,`content`,`dates`,`tags`) VALUES ( ?, ?, ?, ?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, csdnBlog.getTitle());
        ps.setString(2, csdnBlog.getContent());
        ps.setString(3, csdnBlog.getDate());
        ps.setString(4, csdnBlog.getTags());

        int result = ps.executeUpdate();
        dbUtil.close(ps, con);
        return result;
    }
}
