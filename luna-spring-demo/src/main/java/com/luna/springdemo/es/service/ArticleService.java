package com.luna.springdemo.es.service;

import com.luna.springdemo.es.entity.Article;
import java.util.List;

/**
 * (Article)表服务接口
 *
 * @author luna
 * @since 2020-04-10 16:07:18
 */
public interface ArticleService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Article getById(Integer id);

    /**
     * 通过Article对象查询集合
     *
     * @param article 实例对象
     * @return 实例对象
     */
    List<Article> getAll(Article article);

    /**
     * 新增数据
     *
     * @param article 实例对象
     * @return 实例对象
     */
    Article insert(Article article);

    /**
     * 修改数据
     *
     * @param article 实例对象
     * @return 实例对象
     */
    Article update(Article article);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}