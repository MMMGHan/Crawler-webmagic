package com.amoism.WebCrawler;

/**
 * Created by amoism on 2017/5/16.
 */
public class NewsSohu {
    private String title;// 标题

    private String date;// 日期

    private String source;// 来源

    private String tags;// 标签

    private String content;// 内容

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "NewsSohu{" +
                "title='" + title + '\'' +
                ", date='" + date + '\'' +
                ", source='" + source + '\'' +
                ", tags='" + tags + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
