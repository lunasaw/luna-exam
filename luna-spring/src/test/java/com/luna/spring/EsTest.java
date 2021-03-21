package com.luna.spring;

import com.luna.spring.es.entity.Article;
import com.luna.spring.es.mapper.ArticleMapper;
import com.luna.spring.es.repository.ArticleRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author Luna@win10
 * @date 2020/4/10 16:02
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class EsTest {

	@Resource
	ArticleMapper articleMapper;

	@Autowired
	ArticleRepository articleRepository;

	@Test
	public void cTest() {
		Article article = new Article();
		articleRepository.index(article);
	}

}
