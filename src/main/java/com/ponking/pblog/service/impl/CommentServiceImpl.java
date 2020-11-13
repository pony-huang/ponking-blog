package com.ponking.pblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ponking.pblog.common.cache.Cache;
import com.ponking.pblog.common.constants.AuthConstants;
import com.ponking.pblog.common.util.JwtUtil;
import com.ponking.pblog.common.util.MD5Util;
import com.ponking.pblog.model.dto.CommentDto;
import com.ponking.pblog.model.entity.Comment;
import com.ponking.pblog.mapper.CommentMapper;
import com.ponking.pblog.model.vo.ArticleCommentsVo;
import com.ponking.pblog.service.ICommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.jsonwebtoken.Claims;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 评论表 服务实现类
 * </p>
 *
 * @author peng
 * @since 2020-10-04
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {

    @Autowired
    private Cache cache;

    @Override
    public List<ArticleCommentsVo> getCommentByArticleId(Long id) {
        List<ArticleCommentsVo> articleCommentsVos = baseMapper.selectList(new QueryWrapper<Comment>().eq("article_id", id))
                .stream()
                .map(item -> {
                    ArticleCommentsVo vo = new ArticleCommentsVo();
                    BeanUtils.copyProperties(item, vo);
                    return vo;
                }).collect(Collectors.toList());
        return getTreeCommentsOfParent(articleCommentsVos);
    }

    @Override
    public void addArticleComment(CommentDto commentDto, HttpServletRequest request) {
        Comment comment = new Comment();
        BeanUtils.copyProperties(commentDto,comment);
        String ip = request.getRemoteHost();
        String userAgent = request.getHeader("User-Agent");
        comment.setIp(ip);
        comment.setUserAgent(userAgent);
        comment.setStatus(0);
        comment.setEmailMd5(MD5Util.encrypt(comment.getEmail()));
        // 是否为博主评论  0：否  1：是
        String token = request.getHeader(AuthConstants.JWT_TOKEN_HEARER_NAME);
        if(token == null){
            comment.setIsAdmin(0);
        }else{

            String username = JwtUtil.getAccount(token);
            Object tokenOfCache = cache.get(AuthConstants.JWT_TOKEN_CACHE_PREFIX + username);
            if(tokenOfCache.equals(token)){
                comment.setIsAdmin(1);
                comment.setAuthor(username);
            }
            comment.setIsAdmin(0);
        }
        baseMapper.insert(comment);
    }

    public List<ArticleCommentsVo> getTreeCommentsOfParent(List<ArticleCommentsVo> source) {
        List<ArticleCommentsVo> parents = new ArrayList<>();
        for (ArticleCommentsVo vo : source) {
            if (vo.getParentId() == 0) {
                parents.add(vo);
                vo.setChildren(getTreeCommentsOfChildren(vo,source));
            }
        }
        return parents;
    }

    public List<ArticleCommentsVo> getTreeCommentsOfChildren(ArticleCommentsVo parent, List<ArticleCommentsVo> source) {
        List<ArticleCommentsVo> parents = new ArrayList<>();
        for (ArticleCommentsVo vo : source) {
            if (vo.getParentId().equals(parent.getId())) {
                parents.add(vo);
                vo.setChildren(getTreeCommentsOfChildren(vo,source));
            }
        }
        return parents;
    }
}
