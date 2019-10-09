package com.game.itstar.controller;

import com.game.itstar.base.controller.BaseController;
import com.game.itstar.response.ResEntity;
import com.game.itstar.service.serviceImpl.ImageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * @Author 朱斌
 * @Date 2019/10/9  10:33
 * @Desc
 */
@RestController
@RequestMapping("/api/image")
public class ImageController extends BaseController {
    @Autowired
    private ImageServiceImpl imageService;

    /**
     * 上传图片
     *
     * @param request
     * @return
     */
    @PostMapping("/upload")
    public Object upload(MultipartHttpServletRequest request) {
        try {
            return ResEntity.success(imageService.upload(request));
        } catch (Exception ex) {
            return ResEntity.failed(ex);
        }
    }

}
