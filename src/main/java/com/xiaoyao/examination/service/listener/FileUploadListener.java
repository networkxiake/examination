package com.xiaoyao.examination.service.listener;

import com.xiaoyao.examination.service.StorageService;
import com.xiaoyao.examination.service.event.FileChangedEvent;
import com.xiaoyao.examination.service.event.FileConfirmedEvent;
import com.xiaoyao.examination.service.event.FileUploadedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * 清除已使用或上传时间超过一天的临时文件。
 */
@Component
@RequiredArgsConstructor
public class FileUploadListener {
    private final String TEMP_FILE_KEY = "temp-file";

    private final RedisTemplate<Object, Object> redisTemplate;
    private final StorageService storageService;

    @EventListener(FileUploadedEvent.class)
    public void fileUploaded(FileUploadedEvent event) {
        redisTemplate.opsForZSet().add(TEMP_FILE_KEY, event.getSource(), System.currentTimeMillis());
    }

    @EventListener(FileConfirmedEvent.class)
    public void fileConfirmed(FileConfirmedEvent event) {
        redisTemplate.opsForZSet().remove(TEMP_FILE_KEY, event.getSource());
    }

    /**
     * 删除之前的文件，并且使用新的临时文件。
     */
    @EventListener(FileChangedEvent.class)
    public void fileChanged(FileChangedEvent event) {
        // 不能删除默认文件
        if (!event.getOldPath().equals(storageService.getDefaultPhotoPath())) {
            storageService.deleteFile(event.getOldPath());
        }
        redisTemplate.opsForZSet().remove(TEMP_FILE_KEY, event.getNewPath());
    }

    /**
     * 每天凌晨3点自动清除一天前的临时文件。
     */
    @Scheduled(cron = "0 0 3 * * ?")
    public void clearTempFileOverOneDay() {
        // TODO 解决并发问题
        Set<Object> members = redisTemplate.opsForZSet().rangeByScore(TEMP_FILE_KEY,
                0, System.currentTimeMillis() - 24 * 60 * 60 * 1000);
        if (members != null && !members.isEmpty()) {
            redisTemplate.opsForZSet().remove(TEMP_FILE_KEY, members);
            storageService.deleteFile(members.stream().map(Object::toString).toList());
        }
    }
}
