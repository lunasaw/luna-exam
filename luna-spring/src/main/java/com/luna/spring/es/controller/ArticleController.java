package com.luna.spring.es.controller;

import com.luna.common.dto.ResultDTO;
import com.luna.common.dto.constant.ResultCode;
import com.luna.spring.es.entity.Article;
import com.luna.spring.es.service.ArticleService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (Article)表控制层
 *
 * @author luna
 * @since 2020-04-10 16:07:19
 */
@RestController
@RequestMapping("/article")
public class ArticleController {
    /**
     * 服务对象
     */
    @Resource
    private ArticleService articleService;

    /**
     * Select list r.
     *
     * @param article
     * @return the r
     */
    @GetMapping(value = "/get")
    public ResultDTO query(Article article) {
        return new ResultDTO(true, ResultCode.SUCCESS, "查询列表", articleService.getAll(article));
    }

    /**
     * Select obj r.
     *
     * @param id
     * @return the r
     */
    @GetMapping(value = "/{id}")
    public ResultDTO get(@PathVariable("id") Integer id) {
        return new ResultDTO(true, ResultCode.SUCCESS, "查询详情", articleService.getById(id));
    }

    /**
     * Insert obj.
     *
     * @param article
     * @return the r
     */
    @PostMapping(value = "/add")
    public ResultDTO add(@RequestBody Article article) {
        return new ResultDTO(true, ResultCode.SUCCESS, "添加", articleService.insert(article));
    }

    /**
     * update obj.
     *
     * @param article
     * @return the r
     */
    @PutMapping(value = "/")
    public ResultDTO update(@RequestBody Article article) {
        return new ResultDTO(true, ResultCode.SUCCESS, "更新", articleService.update(article));
    }

    /**
     * delete obj.
     *
     * @param id
     * @return the r
     */
    @DeleteMapping(value = "/{id}")
    public ResultDTO delete(@PathVariable("id") Integer id) {
        return new ResultDTO(true, ResultCode.SUCCESS, "删除", articleService.deleteById(id));
    }
}