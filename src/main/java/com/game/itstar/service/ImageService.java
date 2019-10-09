package com.game.itstar.service;

import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * @Author 朱斌
 * @Date 2019/10/9  10:37
 * @Desc
 */
public interface ImageService {
    String upload(MultipartHttpServletRequest request);
}
