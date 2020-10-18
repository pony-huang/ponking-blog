package com.ponking.pblog.config;

import com.google.common.base.CaseFormat;
import com.ponking.pblog.mapper.ConfigMapper;
import com.ponking.pblog.model.entity.BlogConfig;
import com.ponking.pblog.model.params.AliyunOSS;
import com.ponking.pblog.service.IConfigService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.List;

/**
 * 博客配置
 *
 * @author peng
 * @date 2020/10/17--16:54
 * @Des
 **/
public class PBlogConfig {

    private String blogTitle;

    private String blogKeywords;

    private String blogUrl;

    private String blogAuthor;

    private String blogAvatar;

    private String blogNotice;

    private String blogDescription;

    private String defaultImage;

    private String authorDescription;

    private String filingIcp;

    private String filingSecurity;

    private String commentCheck;

    private String backgroundList;

    private String blogHead;

    private String blogScript;

    private String fileStorage;

    private String authorCity;

    private AliyunOSS aliyunOSS;

    public void setBlogTile(String blogTile) {
        this.blogTitle = blogTile;
    }

    public String getBlogKeywords() {
        return blogKeywords;
    }

    public void setBlogKeywords(String blogKeywords) {
        this.blogKeywords = blogKeywords;
    }

    public String getBlogUrl() {
        return blogUrl;
    }

    public void setBlogUrl(String blogUrl) {
        this.blogUrl = blogUrl;
    }

    public String getDefaultImage() {
        return defaultImage;
    }

    public void setDefaultImage(String defaultImage) {
        this.defaultImage = defaultImage;
    }

    public String getAuthorDescription() {
        return authorDescription;
    }

    public void setAuthorDescription(String authorDescription) {
        this.authorDescription = authorDescription;
    }

    public String getFilingIcp() {
        return filingIcp;
    }

    public void setFilingIcp(String filingIcp) {
        this.filingIcp = filingIcp;
    }

    public String getFilingSecurity() {
        return filingSecurity;
    }

    public void setFilingSecurity(String filingSecurity) {
        this.filingSecurity = filingSecurity;
    }

    public String getCommentCheck() {
        return commentCheck;
    }

    public void setCommentCheck(String commentCheck) {
        this.commentCheck = commentCheck;
    }

    public String getBackgroundList() {
        return backgroundList;
    }

    public void setBackgroundList(String backgroundList) {
        this.backgroundList = backgroundList;
    }

    public String getBlogHead() {
        return blogHead;
    }

    public void setBlogHead(String blogHead) {
        this.blogHead = blogHead;
    }

    public String getBlogScript() {
        return blogScript;
    }

    public void setBlogScript(String blogScript) {
        this.blogScript = blogScript;
    }

    public String getFileStorage() {
        return fileStorage;
    }

    public void setFileStorage(String fileStorage) {
        this.fileStorage = fileStorage;
    }

    public String getBlogTitle() {
        return blogTitle;
    }

    public void setBlogTitle(String blogTitle) {
        this.blogTitle = blogTitle;
    }

    public String getBlogAuthor() {
        return blogAuthor;
    }

    public void setBlogAuthor(String blogAuthor) {
        this.blogAuthor = blogAuthor;
    }

    public String getBlogAvatar() {
        return blogAvatar;
    }

    public void setBlogAvatar(String blogAvatar) {
        this.blogAvatar = blogAvatar;
    }

    public String getBlogNotice() {
        return blogNotice;
    }

    public void setBlogNotice(String blogNotice) {
        this.blogNotice = blogNotice;
    }

    public String getBlogDescription() {
        return blogDescription;
    }

    public void setBlogDescription(String blogDescription) {
        this.blogDescription = blogDescription;
    }

    public String getAuthorCity() {
        return authorCity;
    }

    public void setAuthorCity(String authorCity) {
        this.authorCity = authorCity;
    }

    public AliyunOSS getAliyunOSS() {
        return aliyunOSS;
    }

    public void setAliyunOSS(AliyunOSS aliyunOSS) {
        this.aliyunOSS = aliyunOSS;
    }
}
