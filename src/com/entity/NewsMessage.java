package com.entity;

import java.util.List;

/**
 * Created by admin on 2016/11/17.
 */
public class NewsMessage extends BaseMessage {
    private List<News> Articles;
    private Integer ArticleCount;

    public List<News> getArticles() {
        return Articles;
    }

    public void setArticles(List<News> articles) {
        Articles = articles;
    }

    public Integer getArticleCount() {
        return ArticleCount;
    }

    public void setArticleCount(Integer articleCount) {
        ArticleCount = articleCount;
    }
}
