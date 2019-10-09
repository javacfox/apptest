package com.game.itstar.service.serviceImpl;

import com.game.itstar.response.ResException;
import com.game.itstar.service.ImageService;
import com.game.itstar.utile.DateExtendUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author 朱斌
 * @Date 2019/10/9  10:34
 * @Desc 图片上传
 */
@Service
public class ImageServiceImpl implements ImageService {
    @Value("${file.save-path}")
    private String baseFilePath;

    private final static List<String> fileTypes = new ArrayList<String>() {{
        add("jpg");
        add("jpeg");
        add("png");
        add("gif");
        add("bmp");
    }};

    /**
     * 上传图片
     *
     * @param request
     * @return
     */
    @Override
    public String upload(MultipartHttpServletRequest request) {
        //按月来保存一个目录
        String uploadPath = DateExtendUtil.dateToString(new Date(), "yyyyMM");
        //文件路径不存在,则新建
        String checkPath = baseFilePath + "/" + uploadPath;
        File fileStream = new File(checkPath);
        if (!fileStream.exists()) {
            fileStream.mkdirs();
        }

        MultipartFile file = request.getFile("file");
        BufferedOutputStream stream;
        if (file.isEmpty()) {
            throw new ResException("上传文件不能为空");
        }

        String fileName = file.getOriginalFilename();
        String realPath;
        try {
            String relatedPath = "/" + (new SimpleDateFormat("yyyyMMddHHmmssSSS")).format(new Date()) + "_" + fileName;
            realPath = uploadPath + relatedPath;
            int len = fileName.lastIndexOf(".");
            if (len <= 0) {
                throw new ResException("文件类型有误,请确认!");
            }
            String fileType = fileName.substring(len + 1).toLowerCase();
            if (!fileTypes.contains(fileType)) {
                throw new ResException("只允许上传图片!");
            }

            byte[] bytes = file.getBytes();
            stream = new BufferedOutputStream(new FileOutputStream(new File(baseFilePath + "/" + realPath)));//设置文件路径及名字
            stream.write(bytes);// 写入
            stream.close();
        } catch (Exception e) {
            stream = null;
            e.printStackTrace();
            throw new ResException(fileName + "上传失败 => " + e.getMessage());
        }

        return realPath;
    }
}
