package com.xyz.pay.domain;

import com.xyz.pay.enums.StateCode;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.util.ObjectUtils;

import java.io.Serializable;
import java.util.Optional;

/**
 * 统一返回格式数据封装
 */
@Data
@NoArgsConstructor
public class Result<T> implements Serializable {
    /**
     * 是否成功
     */
    private boolean success;
    /**
     * 返回码
     */
    private int code;
    /**
     * 返回消息
     */
    private String msg;
    /**
     * 返回数据
     */
    private T data;

    private Result(int code, T data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
        this.success = StateCode.SUCCESS.code == code;
    }

    /**
     * 判断返回是否为成功
     *
     * @param result Result
     * @return 是否成功
     */
    public static boolean isSuccess(@Nullable Result<?> result) {
        return Optional.ofNullable(result).map(x -> ObjectUtils.nullSafeEquals(StateCode.SUCCESS.code, x.code)).orElse(Boolean.FALSE);
    }


    /**
     * 返回Result
     *
     * @param code 状态码
     * @param data 数据
     * @param msg  消息
     * @param <T>  T 泛型标记
     * @return Result
     */
    private static <T> Result<T> data(int code, T data, String msg) {
        return new Result<>(code, data, data == null ? "" : msg);
    }


    /**
     * 返回Result
     *
     * @param data 数据
     * @param msg  消息
     * @param <T>  T 泛型
     * @return Result
     */
    public static <T> Result<T> success(T data, String msg) {
        return data(StateCode.SUCCESS.code, data, msg);
    }


    /**
     * 返回Result
     *
     * @param data 数据
     * @param <T>  T 泛型
     * @return Result
     */
    public static <T> Result<T> success(T data) {
        return success(data, StateCode.SUCCESS.msg);
    }


    /**
     * 返回Result
     *
     * @param <T> T 泛型标记
     * @return Result
     */
    public static <T> Result<T> success() {
        return new Result<>(StateCode.SUCCESS.code, null, StateCode.SUCCESS.msg);
    }

    /**
     * 返回Result
     *
     * @param msg 消息
     * @param <T> T 泛型标记
     * @return Result
     */
    public static <T> Result<T> success(String msg) {
        return new Result<>(StateCode.SUCCESS.code, null, msg);
    }

    /**
     * 返回Result
     *
     * @return Result
     */
    public static <T> Result<T> fail() {
        return new Result<>(StateCode.FAILURE.code, null, StateCode.FAILURE.msg);
    }

    /**
     * 返回Result
     *
     * @param msg 消息
     * @param <T> T 泛型标记
     * @return Result
     */
    public static <T> Result<T> fail(String msg) {
        return new Result<>(StateCode.FAILURE.code, null, msg);
    }


    /**
     * 返回Result
     *
     * @param code 状态码
     * @param msg  消息
     * @param <T>  T 泛型标记
     * @return Result
     */
    public static <T> Result<T> fail(int code, String msg) {
        return new Result<>(code, null, msg);
    }

    /**
     * 返回Result
     *
     * @param stateCode 状态码枚举类
     * @return Result
     */
    public static <T> Result<T> fail(StateCode stateCode) {
        return new Result<>(stateCode.code, null, stateCode.msg);
    }

}
