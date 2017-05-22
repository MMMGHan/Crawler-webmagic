package com.amoism.WebCrawler.PageProcessor;


import com.amoism.WebCrawler.Dao.NewsSinaDao;
import com.amoism.WebCrawler.UrlDao.Url_SinaDao;
import com.amoism.WebCrawler.NewsSina;
import com.amoism.WebCrawler.Url;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;

public class NewsSinaPageProcessor implements PageProcessor {

    private static int size = 0;// 共抓取到的文章数量

    // 部分一：抓取网站的相关配置，包括抓取间隔、重试次数、设置浏览器等
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000)
            .setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.133 Safari/537.36");

    private static final String URL_LIST = "http://news.sina.com.cn/china/";
    private static final String URL_DEATIL = "http://news.sina.com.cn/\\?|.*/2017.*";

    @Override
    public void process(Page page) {

        page.addTargetRequests(page.getHtml().xpath("//div[@class='blk121']/a").links().regex(URL_DEATIL).all());
        List<String> urls = page.getHtml().xpath("//div[@class='blk122']/a").links().regex(URL_DEATIL).all();
        page.addTargetRequests(urls);

        Url_SinaDao urlDao = new Url_SinaDao();

        Url url = new Url();
        url.setUrl(page.getUrl().toString());


        try {
            if (url.getMark() == 0 && !url.getUrl().equals(URL_LIST)) {
                new Url_SinaDao().addurl(url);
                if (page.getHtml().xpath("//h1[@id='artibodyTitle']/text()").toString() != null) {
                    size++;
                    NewsSina newsSina = new NewsSina();

                    newsSina.setTitle(
                            page.getHtml().xpath("//*[@id='artibodyTitle']/text()").get());

                    newsSina.setContent(
                            page.getHtml().xpath("//div[@id='artibody']/allText()").get());

                    newsSina.setDate_source(
                            page.getHtml().xpath("//span[@id='navtimeSource']/allText()").get());

                    newsSina.setTags(
                            page.getHtml().xpath("/html/head/meta[4]/@content").get());

                    try {
                        new NewsSinaDao().addNewsSina(newsSina);
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
//        Spider.create(new NewsSinaPageProcessor())
//                .addUrl(URL_LIST)//初始网站
//                //.setScheduler(new FileCacheQueueScheduler("/Users/amoism/Documents/newsSina_urlsCache/"))//url去重
//                .thread(5)
//                .start();//异步启动
//        System.out.println("【爬虫结束】共抓取" + size + "篇新闻，已保存到数据库，请查收！");
//    }
}
