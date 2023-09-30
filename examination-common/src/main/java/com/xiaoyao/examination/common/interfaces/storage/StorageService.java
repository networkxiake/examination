package com.xiaoyao.examination.common.interfaces.storage;

import java.util.List;

public interface StorageService {
    String USER_PHOTO_PREFIX = "photo/user/";
    String GOODS_EXCEL_PREFIX = "excel/";
    String GOODS_IMAGE_PREFIX = "photo/goods/";

    /**
     * 获取默认头像的路径。
     */
    String getDefaultPhotoPath();

    /**
     * 在用户头像的存储空间中申请一块空间，用于上传用户头像。
     *
     * @return 返回申请的空间的路径和URL，URL用于往申请的空间中上传头像。
     */
    List<String> applySpaceForPhoto(String filename);

    /**
     * 在体检套餐Excel的存储空间中申请一块空间，用于上传体检套餐Excel。
     *
     * @return 返回申请的空间的路径和URL，URL用于往申请的空间中上传文件。
     */
    List<String> applySpaceForExcel(String filename);

    /**
     * 在体检套餐封面的存储空间中申请一块空间。
     */
    List<String> applySpaceForImage(String filename);

    String getExcelDownloadingUrl(String filename);

    // ------------------------ 通用方法 ------------------------

    /**
     * 引用新申请的空间。
     */
    void referenceSpace(String path);

    /**
     * 释放指定路径的空间。
     */
    void releaseSpace(List<String> paths);

    /**
     * 释放指定路径的空间。
     */
    void releaseSpace(String path);

    /**
     * 引用新申请的空间并释放旧的空间。
     */
    void changeReference(String oldPath, String newPath);

    /**
     * 获取指定路径文件的下载URL。
     */
    String getPathDownloadingUrl(String path);
}
