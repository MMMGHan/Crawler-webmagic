package com.amoism.WebCrawler;

import com.amoism.WebCrawler.PageProcessor.CsdnBlogPageProcessor;
import com.amoism.WebCrawler.PageProcessor.News163PageProcessor;
import com.amoism.WebCrawler.PageProcessor.NewsSinaPageProcessor;
import com.amoism.WebCrawler.PageProcessor.NewsSohuPageProcessor;

import us.codecraft.webmagic.Spider;
import com.amoism.WebCrawler.Scheduler.FileCacheQueueScheduler;

import java.util.Scanner;

public class Start {
    public static void main(String[] args) {
        String blogclass;

        System.out.println("欢迎使用爬虫系统，请选择新闻源：");
        System.out.println("1:网易国内新闻（即时新闻）；   2:新浪国内新闻（部分）；");
        System.out.println("3:搜狐国内新闻          ；   4:CSDN博客；");
        Scanner in = new Scanner(System.in);
        int num = in.nextInt();
        switch (num) {
            case 1:
                String URL_LIST1 = "http://news.163.com/domestic/";
                System.out.println("【爬虫开始】...");
                Spider.create(new News163PageProcessor())
                        .addUrl(URL_LIST1)//初始网站
                        .thread(5)
                        .start();//异步启动
                System.out.println("【爬虫结束】已保存到数据库，请查收！");
                break;
            case 2:
                String URL_LIST2 = "http://news.sina.com.cn/china/";
                System.out.println("【爬虫开始】...");
                Spider.create(new NewsSinaPageProcessor())
                        .addUrl(URL_LIST2)//初始网站
                        .thread(5)
                        .start();//异步启动
                System.out.println("【爬虫结束】已保存到数据库，请查收！");
                break;
            case 3:
                String URL_LIST3 = "http://news.sohu.com/guoneixinwen.shtml";
                System.out.println("【爬虫开始】...");
                Spider.create(new NewsSohuPageProcessor())
                        .addUrl(URL_LIST3)//初始网站
                        .thread(5)
                        .start();//异步启动

                System.out.println("【爬虫结束】已保存到数据库，请查收！");
                break;
            case 4:
                System.out.println("1：移动开发；  2： Web前端；  3：架构设计；  4：编程语言；");
                System.out.println("5：互联网 ；   6： 数据库；   7：系统运维；  8：云计算；");
                System.out.println("9：研发管理；  10：其他；");
                System.out.println("请输入想要爬取的主题：");
                Scanner scanner = new Scanner(System.in);
                int Num = scanner.nextInt();
                switch (Num) {
                    case 1:
                        blogclass = "mobile";
                        Spider.create(new CsdnBlogPageProcessor(blogclass))
                                .addUrl("http://blog.csdn.net/" + blogclass + "/newarticle.html")
                                .thread(5)
                                .start();
                        System.out.println("【爬虫结束】已保存到数据库，请查收！");
                        break;
                    case 2:
                        blogclass = "web";
                        Spider.create(new CsdnBlogPageProcessor(blogclass))
                                .addUrl("http://blog.csdn.net/" + blogclass + "/newarticle.html")
                                .thread(5)
                                .start();
                        System.out.println("【爬虫结束】已保存到数据库，请查收！");
                        break;
                    case 3:
                        blogclass = "enterprise";
                        Spider.create(new CsdnBlogPageProcessor(blogclass))
                                .addUrl("http://blog.csdn.net/" + blogclass + "/newarticle.html")
                                .thread(5)
                                .start();
                        System.out.println("【爬虫结束】已保存到数据库，请查收！");
                        break;
                    case 4:
                        blogclass = "code";
                        Spider.create(new CsdnBlogPageProcessor(blogclass))
                                .addUrl("http://blog.csdn.net/" + blogclass + "/newarticle.html")
                                .thread(5)
                                .start();
                        System.out.println("【爬虫结束】已保存到数据库，请查收！");
                        break;
                    case 5:
                        blogclass = "www";
                        Spider.create(new CsdnBlogPageProcessor(blogclass))
                                .addUrl("http://blog.csdn.net/" + blogclass + "/newarticle.html")
                                .thread(5)
                                .start();
                        System.out.println("【爬虫结束】已保存到数据库，请查收！");
                        break;
                    case 6:
                        blogclass = "database";
                        Spider.create(new CsdnBlogPageProcessor(blogclass))
                                .addUrl("http://blog.csdn.net/" + blogclass + "/newarticle.html")
                                .thread(5)
                                .start();
                        System.out.println("【爬虫结束】已保存到数据库，请查收！");
                        break;
                    case 7:
                        blogclass = "system";
                        Spider.create(new CsdnBlogPageProcessor(blogclass))
                                .addUrl("http://blog.csdn.net/" + blogclass + "/newarticle.html")
                                .thread(5)
                                .start();
                        System.out.println("【爬虫结束】已保存到数据库，请查收！");
                        break;
                    case 8:
                        blogclass = "cloud";
                        Spider.create(new CsdnBlogPageProcessor(blogclass))
                                .addUrl("http://blog.csdn.net/" + blogclass + "/newarticle.html")
                                .thread(5)
                                .start();
                        System.out.println("【爬虫结束】已保存到数据库，请查收！");
                        break;
                    case 9:
                        blogclass = "software";
                        Spider.create(new CsdnBlogPageProcessor(blogclass))
                                .addUrl("http://blog.csdn.net/" + blogclass + "/newarticle.html")
                                .thread(5)
                                .start();
                        System.out.println("【爬虫结束】已保存到数据库，请查收！");
                        break;
                    case 10:
                        blogclass = "other";
                        Spider.create(new CsdnBlogPageProcessor(blogclass))
                                .addUrl("http://blog.csdn.net/" + blogclass + "/newarticle.html")
                                .thread(5)
                                .start();
                        System.out.println("【爬虫结束】已保存到数据库，请查收！");
                        break;
                }
                break;
        }
    }

}
