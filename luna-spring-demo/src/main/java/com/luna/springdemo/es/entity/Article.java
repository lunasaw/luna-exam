package com.luna.springdemo.es.entity;

import io.searchbox.annotations.JestId;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;

/**
 * (Article)实体类
 *
 * @author luna
 * @since 2020-04-10 16:07:16
 */
@Document(indexName = "luna",type = "news")
public class Article implements Serializable {
    private static final long serialVersionUID = 577823755105139324L;

    @JestId
    private Integer id;
    
    private String name;
    
    private String title;
    
    private String content;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}