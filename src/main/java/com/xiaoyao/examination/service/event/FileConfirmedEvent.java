package com.xiaoyao.examination.service.event;

import org.springframework.context.ApplicationEvent;

/**
 * 确认使用了一个临时文件。
 */
public class FileConfirmedEvent extends ApplicationEvent {
    public FileConfirmedEvent(String path) {
        super(path);
    }
}
