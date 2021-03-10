package com.luna.springdemo;
import com.luna.media.javacv.CheckFace;
import com.luna.springdemo.es.entity.Article;
import com.luna.springdemo.es.mapper.ArticleMapper;
import com.luna.springdemo.es.repository.ArticleRepository;
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
