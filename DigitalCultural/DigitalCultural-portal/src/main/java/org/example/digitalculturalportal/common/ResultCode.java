package org.example.digitalculturalportal.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @Author: adlx
 * @Description: 公共返回对象枚举
 * @DateTime: 2024/5/16 11:06
 **/

@ToString
@Getter
@AllArgsConstructor
public enum ResultCode {

//    通用
    SUCCESS(200, "SUCCESS"),
    ERROR(500,"服务端异常"),
    INPUT_ERROR(40010, "账号或密码错误"),
    FAIL_TITLE(301,"内容不能为空"),
    USER_EXITS(40011,"用户名已存在"),
    USER_ERROR(302,"用户不存在"),
    UPLOAD_ERROR(303,"文件为空"),
    LIST_ERROR(304,"列表为空"),
    OBJECT_ERROR(305,"对象为空");

    private final Integer code;
    private final String message;
}
