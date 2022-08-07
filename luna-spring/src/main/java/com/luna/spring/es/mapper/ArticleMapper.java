package com.luna.spring.es.mapper;

import com.luna.spring.es.entity.Article;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * (Article)表数据库访问层
 *
 * @author luna
 * @since 2020-04-10 16:07:17
 */
@Mapper
public interface ArticleMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Article getByPrimaryKey(Integer id);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param article 实例对象
     * @return 对象列表
     */
    List<Article> getAll(Article article);

    /**
     * 新增数据
     *
     * @param article 实例对象
     * @return 影响行数
     */
    int insert(Article article);

    /**
     * 修改数据
     *
     * @param article 实例对象
     * @return 影响行数
     */
    int updateByPrimaryKey(Article article);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteByPrimaryKey(Integer id);

}