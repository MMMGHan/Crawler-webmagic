package com.amoism.WebCrawler;

import us.codecraft.webmagic.selector.Selectable;

/**
 * Created by amoism on 2017/5/20.
 */
public class Url {
    private String url;
    private int mark = 0;

    public String getUrl(Selectable url) {
        return this.url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
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
        return "Url{" +
                "url='" + url + '\'' +
                ", mark=" + mark +
                '}';
    }

}
