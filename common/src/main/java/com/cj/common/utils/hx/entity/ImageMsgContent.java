package com.cj.common.utils.hx.entity;

import io.swagger.client.model.MsgContent;

/**
 * 环信 发送图片 实体类
 * Created by XD on 2019/2/28.
 */
public class ImageMsgContent extends MsgContent {
    private String url;
    private String filename;
    private String secret;
    private Size size;

    public ImageMsgContent url(String url) {
            this.url = url;
            return this;
    }

    public ImageMsgContent filename(String filename) {
            this.filename = filename;
            return this;
        }

    public ImageMsgContent secret(String secret) {
            this.secret = secret;
            return this;
        }

    public ImageMsgContent size(Size size) {
            this.size = size;
            return this;
        }

    public static class Size {
            private long width;
            private long height;

        public Size(long width, long height) {
                this.width = width;
                this.height = height;
            }
        }
}

