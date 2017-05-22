package com.amoism.WebCrawler.PageProcessor;


import com.amoism.WebCrawler.Dao.NewsSohuDao;
import com.amoism.WebCrawler.UrlDao.Url_SohuDao;
import com.amoism.WebCrawler.NewsSohu;
import com.amoism.WebCrawler.Url;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

public class NewsSohuPageProcessor implements PageProcessor {
    private static int size = 0;// 共抓取到的文章数量

    // 部分一：抓取网站的相关配置，包括抓取间隔、重试次数、设置浏览器等
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000)
            .setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.133 Safari/537.36");

    private static final String URL_LIST = "http://news.sohu.com/guoneixinwen.shtml";
    private static final String URL_DEATIL1 = "http://news.sohu.com/2017.*";
    private static final String URL_DEATIL2 = "http://www.sohu.com/a/.*";

    @Override
    public void process(Page page) {

        page.addTargetRequests(page.getHtml().xpath("//div[@class='new-article']").links().regex(URL_DEATIL1).all());
        page.addTargetRequests(page.getHtml().xpath("//div[@class='new-article']").links().regex(URL_DEATIL2).all());


        Url_SohuDao urlDao = new Url_SohuDao();

        Url url = new Url();
        url.setUrl(page.getUrl().toString());

        try {
            if (url.getMark() == 0 && !url.getUrl().equals(URL_LIST)) {
                new Url_SohuDao().addurl(url);
                if (page.getHtml().xpath("//*[@id=\"container\"]/div[1]/div[2]/h1/text()").toString() != null) {
                    size++;

                    NewsSohu newsSohu = new NewsSohu();

                    newsSohu.setTitle(
                            page.getHtml().xpath("//*[@id=\"container\"]/div[1]/div[2]/h1/text()").get());

                    newsSohu.setContent(
                            page.getHtml().xpath("//*[@id=\"contentText\"]/div[1]/allText()").get());

                    newsSohu.setDate(
                            page.getHtml().xpath("//*[@id=\"pubtime_baidu\"]/text()").get());

                    newsSohu.setSource(
                            page.getHtml().xpath("//*[@id=\"author_baidu\"]/text()").get());

                    newsSohu.setTags(
                            page.getHtml().xpath("/html/head/meta[2]/@content").get());

                    try {
                        new NewsSohuDao().addNewsSohu(newsSohu);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        urlDao.updatemark(page.getUrl().toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (page.getHtml().xpath("//*[@id=\"article-container\"]/div[2]/div[1]/div[1]/h1/text()").toString() != null) {
                    size++;

                    NewsSohu newsSohu = new NewsSohu();

                    newsSohu.setTitle(
                            page.getHtml().xpath("//*[@id=\"article-container\"]/div[2]/div[1]/div[1]/h1/text()").get());

                    newsSohu.setContent(
                            page.getHtml().xpath("//*[@id=\"article-container\"]/div[2]/div[1]/article/allText()").get());

                    newsSohu.setDate(
                            page.getHtml().xpath("//*[@id=\"article-container\"]/div[2]/div[1]/div[1]/div/text()").get());

                    newsSohu.setSource(
                            page.getHtml().xpath("//*[@id=\"user-info\"]/h4/a/text()").get());

                    try {
                        new NewsSohuDao().addNewsSohu(newsSohu);
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


    @Override
    public Site getSite() {
        return site;
    }

//    public static void main(String[] args) {
//        System.out.println("【爬虫开始】...");
//        Spider.create(new NewsSohuPageProcessor())
//                .addUrl(URL_LIST)//初始网站
//                //.setScheduler(new FileCacheQueueScheduler("/Users/amoism/Documents/news163_urlsCache/"))//url去重
//                .thread(5)
//                .start();//异步启动
//        System.out.println("【爬虫结束】共抓取" + size + "篇新闻，已保存到数据库，请查收！");
//
//    }
}
