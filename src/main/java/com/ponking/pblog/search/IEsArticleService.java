package com.ponking.pblog.search;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ponking.pblog.model.document.EsArticle;

import java.util.List;

/**
 * @author peng
 * @date 2020/10/23--17:15
 * @Des
 **/
public interface IEsArticleService {

    /**
     * 从数据库中导入所有文章到ES
     * @return 个数
     */
    int importAll();


    /**
     * 获取全部文章信息
     * @return List<EsArticle>
     */
    List<EsArticle> queryMatchAll();

    /**
     * 根据id删除文章
     */
    void delete(Long id);

    /**
     * 根据id创建文章
     */
    EsArticle create(Long id);

    /**
     * 批量删除文章
     */
    void delete(List<Long> ids);

    /**
     * 建立文章Mapping
     */
    void createMappings();

    /**
     * 根据关键字搜索名称或者副标题
     * @param keyword
     * @param pageNum
     * @param pageSize
     * @return
     */
    Page<EsArticle> search(String keyword, Integer pageNum, Integer pageSize);


    /**
     * 删除索引
     * @return
     */
    boolean deleteIndex();

    /**
     * 判断是否存在Mapping（index）
     * @return 是 true
     */
    boolean isExitsIndex();
}
