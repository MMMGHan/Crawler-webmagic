package com.amoism.WebCrawler;

import us.codecraft.webmagic.selector.Selectable;

/**
 * Created by amoism on 2017/5/12.
 */
public class News163 {


    private String title;// 标题

    private String date_source;// 日期

    private String tags;// 标签

    private String content;// 内容

    private Selectable url;

    private int mark;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate_source() {
        return date_source;
    }

    public void setDate_source(String date_source) {
        this.date_source = date_source;
    }

    @Override
    public String toString() {
        return "News163{" +
                "title='" + title + '\'' +
                ", date_source='" + date_source + '\'' +
                ", tags='" + tags + '\'' +
                ", content='" + content + '\'' +
                ", url=" + url +
                ", mark=" + mark +
                '}';
    }
}
