package com.amoism.WebCrawler.PageProcessor;


import com.amoism.WebCrawler.Dao.News163Dao;
import com.amoism.WebCrawler.UrlDao.Url_163Dao;
import com.amoism.WebCrawler.News163;
import com.amoism.WebCrawler.Url;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;


import java.util.List;


public class News163PageProcessor implements PageProcessor {

    private static int size = 0;// 共抓取到的文章数量

    // 部分一：抓取网站的相关配置，包括抓取间隔、重试次数、设置浏览器等
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000)
            .setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.133 Safari/537.36");

    //网易新闻->国内->即时新闻 的 新闻列表
    private static final String URL_LIST = "http://news.163.com/domestic/";
    //即时新闻 里的 新闻连接
    private static final String URL_DEATIL = "http://news.163.com/17/.*";

    @Override
    public void process(Page page) {
        List<String> urls = page.getHtml().xpath("//ul[@class=idx_cm_list]").links().regex(URL_DEATIL).all();
        //待抓取列表
        page.addTargetRequests(urls);

        Url_163Dao urlDao = new Url_163Dao();
//
//        /**
//         * 将url放入urlhub表中 做去重处理
//         */
//        for (int i = 0; i < urls.size(); i++) {
//            Url url = new Url();
//            url.setUrl(urls.get(i));
//            if (!url.getUrl().equals(URL_LIST)) {
//                try {
//                    urlDao.addurl(url);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
        Url url = new Url();
        url.setUrl(page.getUrl().toString());

        try {
            if (url.getMark() == 0 && !url.getUrl().equals(URL_LIST)) {
                new Url_163Dao().addurl(url);
                if (page.getHtml().xpath("//div[@id='epContentLeft']/h1/text()").toString() != null) {
                    size++;
                    News163 news163 = new News163();

                    news163.setTitle(
                            page.getHtml().xpath("//div[@id='epContentLeft']/h1/text()").get());
                    news163.setContent(
                            page.getHtml().xpath("//div[@id='endText']/allText()").get());
                    news163.setDate_source(
                            page.getHtml().xpath("//div[@class='post_time_source']/allText()").get());
                    news163.setTags(
                            page.getHtml().xpath("//*[@id=\"ne_wrap\"]/head/meta[2]/@content").get());

                    try {
                        new News163Dao().addNews163(news163);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                    urlDao.updatemark(page.getUrl().toString());
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
//        Spider.create(new News163PageProcessor())
//                .addUrl(URL_LIST)//初始网站
//                .thread(5)
//                .start();//异步启动
//
//        System.out.println("【爬虫结束】共抓取" + size + "篇新闻，已保存到数据库，请查收！");
//    }
}
