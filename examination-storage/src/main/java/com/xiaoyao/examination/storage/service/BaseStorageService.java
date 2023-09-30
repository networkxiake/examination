package com.xiaoyao.examination.storage.service;

import com.xiaoyao.examination.common.exception.ErrorCode;
import com.xiaoyao.examination.common.exception.ExaminationException;
import com.xiaoyao.examination.common.interfaces.storage.StorageService;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 将存储服务抽象出一个空间，之后需要存放文件时要先申请空间，然后将文件放入空间，最后引用空间。
 * 当该类在实例化时，会判断存储空间中是否有用户默认头像，如果没有则上传用户默认头像。
 */
abstract class BaseStorageService implements StorageService, InitializingBean {
    private static final Logger log = LoggerFactory.getLogger(BaseStorageService.class);

    private final String TEMP_SPACE_KEY = "storage-temp-space";
    private final String LAST_REST_DATE_KEY = "last-reset-date";

    private final String DEFAULT_PHOTO = "default-photo.jpg";
    private final String DEFAULT_PHOTO_PATH = "images/" + DEFAULT_PHOTO;

    private final StringRedisTemplate redisTemplate;
    private final RedissonClient redissonClient;

    protected BaseStorageService(StringRedisTemplate redisTemplate, RedissonClient redissonClient) {
        this.redisTemplate = redisTemplate;
        this.redissonClient = redissonClient;
    }

    protected void initBucket() {
        if (isNotBucketExist()) {
            RLock lock = redissonClient.getLock("initBaseStorageService");
            if (lock.tryLock()) {
                try {
                    if (isNotBucketExist()) {
                        // 创建存储桶并设置访问权限为公共读
                        createPublicBucket();
                        // 上传用户默认头像
                        uploadUserDefaultPhoto(DEFAULT_PHOTO, DEFAULT_PHOTO_PATH, "image/jpeg");
                    }
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    @Override
    public String getDefaultPhotoPath() {
        return DEFAULT_PHOTO;
    }

    @Override
    public List<String> applySpaceForPhoto(String filename) {
        return List.of(USER_PHOTO_PREFIX + filename, applySpace(USER_PHOTO_PREFIX + filename));
    }

    @Override
    public List<String> applySpaceForExcel(String filename) {
        return List.of(GOODS_EXCEL_PREFIX + filename, applySpace(GOODS_EXCEL_PREFIX + filename));
    }

    @Override
    public List<String> applySpaceForImage(String filename) {
        return List.of(GOODS_IMAGE_PREFIX + filename, applySpace(GOODS_IMAGE_PREFIX + filename));
    }

    private String applySpace(String path) {
        redisTemplate.opsForZSet().add(TEMP_SPACE_KEY, path, System.currentTimeMillis());
        return getPreSignatureUrl(path, 1, TimeUnit.HOURS);
    }

    @Override
    public void referenceSpace(String path) {
        if (redisTemplate.opsForZSet().score(TEMP_SPACE_KEY, path) == null) {
            throw new ExaminationException(ErrorCode.STORAGE_SPACE_NOT_EXIST);
        }
        if (!hasFileInSpace(path)) {
            throw new ExaminationException(ErrorCode.STORAGE_SPACE_HAVE_NO_FILE);
        }
        redisTemplate.opsForZSet().remove(TEMP_SPACE_KEY, path);
    }

    @Override
    public void releaseSpace(List<String> paths) {
        paths = new ArrayList<>(paths);
        paths.removeAll(List.of(getDefaultPhotoPath()));
        if (paths.isEmpty()) {
            return;
        }

        redisTemplate.opsForZSet().remove(TEMP_SPACE_KEY, paths.toArray());
        deleteSpace(paths);
    }

    @Override
    public void releaseSpace(String path) {
        releaseSpace(List.of(path));
    }

    @Override
    public void changeReference(String oldPath, String newPath) {
        referenceSpace(newPath);
        releaseSpace(oldPath);
    }

    /**
     * 存储桶是否不存在。
     */
    protected abstract boolean isNotBucketExist();

    /**
     * 创建公共读的存储桶。
     */
    protected abstract void createPublicBucket();

    /**
     * 获取上传文件的预签名URL。
     */
    protected abstract String getPreSignatureUrl(String path, int duration, TimeUnit unit);

    /**
     * 判断指定路径的空间中是否存放了文件。
     */
    protected abstract boolean hasFileInSpace(String path);

    /**
     * 删除所有指定路径的空间。
     */
    protected abstract void deleteSpace(List<String> paths);

    /**
     * 上传用户默认头像。
     */
    protected abstract void uploadUserDefaultPhoto(String path, String filePath, String mimeType);

    /**
     * 自动释放一天前的临时空间，一天只能释放一次，子类需要创建一个定时任务来调用该方法。
     */
    protected void autoReleaseSpace() {
        String lastRestDate = redisTemplate.opsForValue().get(LAST_REST_DATE_KEY);
        if (!LocalDate.now().toString().equals(lastRestDate)) {
            RLock lock = redissonClient.getLock("autoReleaseSpace");
            if (lock.tryLock()) {
                try {
                    lastRestDate = redisTemplate.opsForValue().get(LAST_REST_DATE_KEY);
                    if (!LocalDate.now().toString().equals(lastRestDate)) {
                        Set<String> members = redisTemplate.opsForZSet().rangeByScore(TEMP_SPACE_KEY,
                                0, System.currentTimeMillis() - 24 * 60 * 60 * 1000);
                        if (members != null && !members.isEmpty()) {
                            redisTemplate.opsForZSet().remove(TEMP_SPACE_KEY, members);
                            releaseSpace(new ArrayList<>(members));
                        }
                        redisTemplate.opsForValue().set(LAST_REST_DATE_KEY, LocalDate.now().toString());
                        if (log.isInfoEnabled()) {
                            log.info("释放一天前的临时空间");
                        }
                    }
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    /**
     * 子类需要创建一个定时任务来调用autoReleaseSpace方法。
     */
    public abstract void scheduledTask();
}
