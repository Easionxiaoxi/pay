package com.xyz.pay.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 全局状态码枚举
 */
@Getter
@AllArgsConstructor
public enum StateCode {
    /**
     * 错误请求
     */
    FAILURE(0, "操作失败"),
    /**
     * 操作成功
     */
    SUCCESS(1, "操作成功"),
    /**
     * 服务器异常
     */
    INTERNAL_SERVER_ERROR(2, "服务异常"),

    /**
     * 401请求未授权
     */
    UN_AUTHORIZED(101, "请求未授权"),
    /**
     * 403请求被拒绝
     */
    REQUEST_REJECT(102, "权限不足，请求被拒绝"),
    /**
     * 404 没找到请求
     */
    METHOD_NOT_FOUND(103, "404 没找到请求"),
    /**
     * 405不支持当前请求方法
     */
    METHOD_NOT_SUPPORTED(104, "不支持当前请求方式"),
    /**
     * 415不支持当前媒体类型
     */
    MEDIA_TYPE_NOT_SUPPORTED(105, "不支持当前媒体类型"),
    /**
     * 参数校验失败
     */
    PARAM_VALID_ERROR(106, "参数校验失败"),
    /**
     * 缺少路径参数
     */
    PATH_PARAM_MISS_ERROR(107, "缺少路径参数"),
    /**
     * 缺少请求参数
     */
    REQUEST_PARAM_MISS_ERROR(108, "缺少请求参数"),
    /**
     * 参数类型不匹配
     */
    PARAM_TYPE_MISS_MATCH_ERROR(109, "参数类型不匹配"),
    /**
     * 参数反序列化失败
     */
    PARAM_DESERIALIZATION_ERROR(110, "参数反序列化失败"),
    /**
     * 参数序列化失败
     */
    PARAM_SERIALIZATION_ERROR(111, "参数序列化失败"),
    /**
     * 文件上传超出最大限制
     */
    MAX_UPLOAD_SIZE_ERROR(112, "上传的文件太大了"),
    ;
    /**
     * code编码
     */
    public final int code;
    /**
     * 信息
     */
    public final String msg;

}
