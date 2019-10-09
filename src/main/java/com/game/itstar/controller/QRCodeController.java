package com.game.itstar.controller;

import com.game.itstar.qycode.QrCodeUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

/**
 * @Author 朱斌
 * @Date 2019/10/9  9:48
 * @Desc
 */
@RestController
@RequestMapping("/api/qrcode")
public class QRCodeController {

    @RequestMapping("")
    public void createQRcode(HttpServletResponse response) {
        String contents = "这是测试用的二维码";

        try {

            String logoPath = "C:\\Users\\zy962\\Desktop\\临时备份\\u=1792043407,969529386&fm=26&gp=0.jpg";
            BufferedImage qRImageWithLogo = QrCodeUtils.createImage(contents, logoPath, true);

            // 写入返回
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(qRImageWithLogo, "jpg", baos);

            byte[] QRJPG = baos.toByteArray();
            response.setHeader("Cache-Control", "no-store");
            response.setHeader("Pragma", "no-cache");
            response.setDateHeader("Expires", 0);
            response.setContentType("image/jpeg");

            ServletOutputStream os = response.getOutputStream();
            os.write(QRJPG); // 自此完成一套，图片读入，写入流，转为字节数组，写入输出流
            os.flush();
            os.close();
            baos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
