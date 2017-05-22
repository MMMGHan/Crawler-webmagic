package com.amoism.WebCrawler.PageProcessor;

import com.amoism.WebCrawler.CsdnBlog;
import com.amoism.WebCrawler.Dao.CsdnBlogDao;
import com.amoism.WebCrawler.Url;
import com.amoism.WebCrawler.UrlDao.Url_CsdnDao;
import com.amoism.WebCrawler.UrlDao.Url_SinaDao;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;


public class CsdnBlogPageProcessor implements PageProcessor {
    private String blogclass; // 设置csdn用户名

    private static int size = 0;// 共抓取到的文章数量

    // 抓取网站的相关配置，包括：编码、抓取间隔、重试次数等
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000)
            .setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.133 Safari/537.36");

    public Site getSite() {
        return site;
    }

    public CsdnBlogPageProcessor(String blogclass) {
        this.blogclass = blogclass;
    }

    // process是定制爬虫逻辑的核心接口，在这里编写抽取逻辑
    public void process(Page page) {
        List<String> urls = page.getHtml().xpath("//*[@id=\"listBot\"]").links()
                .regex("http://blog.csdn.net/.*/article/details/.*").all();
        page.addTargetRequests(urls);

        Url_CsdnDao urlDao = new Url_CsdnDao();
        Url url = new Url();
        url.setUrl(page.getUrl().toString());

        try {
            if (url.getMark() == 0 && !url.getUrl().equals("http://blog.csdn.net/" + blogclass + "/newarticle.html")) {
                new Url_CsdnDao().addurl(url);
                if (page.getHtml().xpath("//div[@class='article_title']//span[@class='link_title']/a/text()").toString() != null) {
                    size++;// 文章数量加1
//
                    // 用CsdnBlog类来存抓取到的数据，方便存入数据库
                    CsdnBlog csdnBlog = new CsdnBlog();

                    // 设置标题
                    csdnBlog.setTitle(
                            page.getHtml().xpath("//div[@class='article_title']//span[@class='link_title']/a/text()").get());

                    //设置内容
                    csdnBlog.setContent(
                            page.getHtml().xpath("//div[@class='article_content']/tidyText()").get());


                    // 设置日期
                    csdnBlog.setDate(
                            page.getHtml().xpath("//div[@class='article_r']/span[@class='link_postdate']/text()").get());

                    // 设置标签（可以有多个，用,来分割）
                    csdnBlog.setTags(listToString(page.getHtml()
                            .xpath("//div[@class='article_l']/span[@class='link_categories']/a/allText()").all()));

                    // 把对象存入数据库
                    try {
                        new CsdnBlogDao().addCsdnBlog(csdnBlog);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        urlDao.updatemark(page.getUrl().toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 把list转换为string，用,分割
    public static String listToString(List<String> stringList) {
        if (stringList == null) {
            return null;
        }
        StringBuilder result = new StringBuilder();
        boolean flag = false;
        for (String string : stringList) {
            if (flag) {
                result.append(",");
            } else {
                flag = true;
            }
            result.append(string);
        }
        return result.toString();
    }
}
