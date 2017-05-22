package com.amoism.WebCrawler;

/**
 * Created by amoism on 2017/5/21.
 */
public class Taobao {

    private String commodity_name; //商品名称

    private String tag;  //商品描述

    private String price;  //商品价格

    private String content;  //商品详情

    public String getCommodity_name() {
        return commodity_name;
    }

    public void setCommodity_name(String commodity_name) {
        this.commodity_name = commodity_name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Taobao{" +
                "commodity_name='" + commodity_name + '\'' +
                ", tag='" + tag + '\'' +
                ", price='" + price + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
