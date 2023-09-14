package com.xiaoyao.examination.service.event;

import org.springframework.context.ApplicationEvent;

/**
 * 上传了一个临时文件。
 */
public class FileUploadedEvent extends ApplicationEvent {
    public FileUploadedEvent(String path) {
        super(path);
    }
}
