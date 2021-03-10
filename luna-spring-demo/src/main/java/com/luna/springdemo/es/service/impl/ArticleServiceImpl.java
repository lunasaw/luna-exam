package com.luna.springdemo.es.service.impl;

import com.luna.springdemo.es.entity.Article;
import com.luna.springdemo.es.mapper.ArticleMapper;
import com.luna.springdemo.es.service.ArticleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Article)表服务实现类
 *
 * @author luna
 * @since 2020-04-10 16:07:19
 */
@Service
public class ArticleServiceImpl implements ArticleService {
    @Resource
    private ArticleMapper articleMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Article getById(Integer id) {
        return this.articleMapper.getByPrimaryKey(id);
    }
    
    /**
     * 通过Article对象查询集合
     *
     * @param article 实例对象
     * @return 实例对象
     */
    @Override
    public List<Article> getAll(Article article) {
        return this.articleMapper.getAll(article);
    }

    /**
     * 新增数据
     *
     * @param article 实例对象
     * @return 实例对象
     */
    @Override
    public Article insert(Article article) {
        this.articleMapper.insert(article);
        return article;
    }

    /**
     * 修改数据
     *
     * @param article 实例对象
     * @return 实例对象
     */
    @Override
    public Article update(Article article) {
        this.articleMapper.updateByPrimaryKey(article);
        return this.getById(article.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.articleMapper.deleteByPrimaryKey(id) > 0;
    }
}