package com.xiaoyao.examination.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 定制化的存储服务客户端。
 */
public interface StorageService {
    /**
     * 获取指定路径文件的下载URL。
     */
    String getPathDownloadingUrl(String path);

    /**
     * 确认临时文件，将临时文件转换为正式文件。
     */
    void confirmTempFile(String path);

    // ------------------------ 用户头像 ------------------------

    /**
     * 获取默认头像的路径。
     */
    String getDefaultPhotoPath();

    /**
     * 上传临时的用户头像，返回头像的路径，图片的类型要为jpg、png或jpeg。
     */
    String uploadTempUserPhoto(long userId, MultipartFile file);

    /**
     * 删除用户头像。
     */
    default void deleteUserPhoto(String path) {
        deleteUserPhoto(List.of(path));
    }

    /**
     * 批量删除用户头像。
     */
    void deleteUserPhoto(List<String> paths);

    // ------------------------ 套餐封面 ------------------------

    /**
     * 上传临时的体检套餐封面图片，返回图片的路径，图片的类型要为jpg、png或jpeg。
     * 套餐封面图片一旦确认就不可删除。
     */
    String uploadTempGoodsPhoto(long goodsId, MultipartFile file);

    // ------------------------ 套餐Excel ------------------------

    /**
     * 上传指定体检套餐的Excel文件，如果已存在则覆盖，Excel文件只支持xlsx类型。
     */
    void uploadExcel(long goodsId, MultipartFile file);

    /**
     * 获取指定体检套餐的Excel文件的下载URL。
     */
    String getExcelDownloadingUrl(long goodsId);

    /**
     * 删除指定体检套餐的Excel文件。
     */
    void deleteExcel(List<Long> goodsIds);
}
