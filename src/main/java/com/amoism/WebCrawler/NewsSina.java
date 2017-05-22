package com.amoism.WebCrawler;

/**
 * Created by amoism on 2017/5/16.
 */
public class NewsSina {
    private String title;// 标题

    private String date_source;// 日期

    private String tags;// 标签

    private String content;// 内容

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

    @Override
    public String toString() {
        return "NewsSina{" +
                "title='" + title + '\'' +
                ", date_source='" + date_source + '\'' +
                ", tags='" + tags + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
