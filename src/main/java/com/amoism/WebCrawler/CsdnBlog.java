package com.amoism.WebCrawler;

import us.codecraft.webmagic.selector.Selectable;

/**
 * Created by amoism on 2017/5/13.
 */
public class CsdnBlog {

    private String title;// 标题

    private String date;// 日期

    private String tags;// 标签

    private String content; //文字内容

    private Selectable url;

    private int mark;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Selectable getUrl() {
        return url;
    }

    public void setUrl(Selectable url) {
        this.url = url;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    @Override
    public String toString() {
        return "CsdnBlog{" +
                "title='" + title + '\'' +
                ", date='" + date + '\'' +
                ", tags='" + tags + '\'' +
                ", content='" + content + '\'' +
                ", url=" + url +
                ", mark=" + mark +
                '}';
    }
}
