package com.luna.spring.es.repository;

import com.luna.spring.es.entity.Article;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author Luna@win10
 * @date 2020/4/10 16:55
 */
public interface ArticleRepository extends ElasticsearchRepository<Article,Integer> {


}
