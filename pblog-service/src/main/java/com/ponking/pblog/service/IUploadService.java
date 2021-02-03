package com.ponking.pblog.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author Peng
 * @date 2020/9/1--10:46
 **/
public interface IUploadService {


    /**
     * 上传文件
     * @param file
     * @return 链接url
     */
    String upload(MultipartFile file);
}
