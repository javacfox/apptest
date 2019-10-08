package com.game.itstar.utile;


import jp.sourceforge.qrcode.data.QRCodeImage;

import java.awt.image.BufferedImage;

/**
 * @Author 朱斌
 * @Date 2019/9/30  15:09
 * @Desc
 */
public class TwoDimensionCodeImage implements QRCodeImage {

    BufferedImage bufImg;

    public TwoDimensionCodeImage(BufferedImage bufImg) {
        this.bufImg = bufImg;
    }

    @Override
    public int getHeight() {
        return bufImg.getHeight();
    }

    @Override
    public int getPixel(int x, int y) {
        return bufImg.getRGB(x, y);
    }

    @Override
    public int getWidth() {
        return bufImg.getWidth();
    }
}
