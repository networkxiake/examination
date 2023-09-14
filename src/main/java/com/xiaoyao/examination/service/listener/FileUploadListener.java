package com.xiaoyao.examination.service.listener;

import com.xiaoyao.examination.service.event.FileConfirmedEvent;
import com.xiaoyao.examination.service.event.FileUploadedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * 清除上传时间超过一天的临时文件。
 */
@Component
@RequiredArgsConstructor
public class FileUploadListener {
    private final StringRedisTemplate redisTemplate;

    @EventListener(FileUploadedEvent.class)
    public void fileUploaded(FileUploadedEvent event) {

    }

    @EventListener(FileConfirmedEvent.class)
    public void fileConfirmed(FileConfirmedEvent event) {

    }
}
