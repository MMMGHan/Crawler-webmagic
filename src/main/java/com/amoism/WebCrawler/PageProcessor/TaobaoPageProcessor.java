package com.amoism.WebCrawler.PageProcessor;

import com.amoism.WebCrawler.Dao.TaobaoDao;
import com.amoism.WebCrawler.Downloader.PhantomJSDownloader;
import com.amoism.WebCrawler.Taobao;
import com.amoism.WebCrawler.Url;
import com.amoism.WebCrawler.UrlDao.Url_TaobaoDao;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.downloader.Downloader;
import us.codecraft.webmagic.processor.PageProcessor;
import Util.GetAjaxHtml;

import java.util.List;

import static Util.GetAjaxHtml.getAjaxContent;


public class TaobaoPageProcessor implements PageProcessor {

    private TaobaoDao taobaoDao = new TaobaoDao();

    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000)
            .setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.133 Safari/537.36");

    //起始网站：淘宝女装
    private static final String URL_Start = "https://s.taobao.com/list?spm=a217f.8051907.312344.1.HFZDEO&q=%E8%BF%9E%E8%A1%A3%E8%A3%99&cat=16&style=grid&seller_type=taobao";
    //
    private static final String URL_DEATIL = "https://s.taobao.com/list?spm=.*";
    //
    private static final String URL_List = "https://item.taobao.com/item.htm?spm.*";


    @Override
    public Site getSite() {
        return site;
    }

    @Override
    public void process(Page page) {

        try {
            String html = getAjaxContent(page.getUrl().get());
            System.out.println("html = " + html);
        } catch (Exception e) {
            System.out.println("error:" + e.getMessage());
        }

        List<String> urls =
                page.getHtml().xpath("//*[@id=\"listsrp-itemlist\"]/div/div").links().regex(URL_List).all();
        page.addTargetRequests(urls);

        /**
         * 放入urlhub_taobao表中 做去重
         */
        System.out.println(urls);

        for (String u : urls
                ) {
            System.out.println(u);
            Url_TaobaoDao urlDao = new Url_TaobaoDao();
            Url url = new Url();
            url.setUrl(u);
            try {
                urlDao.addurl(url);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        System.out.println("+++++++");

//        page.addTargetRequests(page.getHtml().links()
//                .regex(URL_DEATIL)
//                .all());

        //如果是详情页
        if (page.getUrl().regex(URL_List).match()) {
            Taobao taobao = new Taobao();

            taobao.setCommodity_name(page.getHtml().xpath("//*[@id=\"J_Title\"]/h3/text()").get());
            taobao.setTag(page.getHtml().xpath("//*[@id=\"J_Title\"]/p/text()").get());
            taobao.setPrice(page.getHtml().xpath("//*[@id=\"J_StrPrice\"]/em[2]/text()").get());
            taobao.setContent(page.getHtml().xpath("//*[@id=\"attributes\"]/ul/tidyText()").get());
            // 把对象存入数据库
            try {
                taobaoDao.addTaobao(taobao);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Spider.create(new TaobaoPageProcessor())
                .setDownloader(new PhantomJSDownloader("/Users/amoism/bin/phantomjs", "/Users/amoism/Projects/phantomjs/codes.js"))
                .addUrl(URL_Start)
                .thread(5)
                .run();
    }
}
