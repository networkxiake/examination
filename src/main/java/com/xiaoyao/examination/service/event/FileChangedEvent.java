package com.xiaoyao.examination.service.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class FileChangedEvent extends ApplicationEvent {
    private final String oldPath;
    private final String newPath;

    public FileChangedEvent(String oldPath, String newPath) {
        super(oldPath);
        this.oldPath = oldPath;
        this.newPath = newPath;
    }
}
