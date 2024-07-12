package org.example.digitalculturalportal.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: adlx
 * @Description: 公共返回对象
 * @DateTime: 2024/5/16 11:06
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResult {


    private long code;
    private String message;
    private Object data;

    /*
    * @Author: adlx
    * @Description: 成功返回结果
    * @DateTime: 11:36 2024/5/16
    * @Params:
    * @Return
    */
    public static CommonResult success() {
        return new CommonResult(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(),null);
    }

    /*
     * @Author: adlx
     * @Description: 成功返回结果
     * @DateTime: 11:36 2024/5/16
     * @Params:
     * @Return
     */
    public static CommonResult success(Object data) {
        return new CommonResult(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(),data);
    }

    /*
    * @Author: adlx
    * @Description: 失败返回结果
    * @DateTime: 11:46 2024/5/16
    * @Params:
    * @Return
    */
    public static CommonResult error(ResultCode resultCode) {
        return new CommonResult(resultCode.getCode(), resultCode.getMessage(), null);
    }

    /*
     * @Author: adlx
     * @Description: 失败返回结果
     * @DateTime: 11:46 2024/5/16
     * @Params:
     * @Return
     */
    public static CommonResult error(ResultCode resultCode, Object data) {
        return new CommonResult(resultCode.getCode(), resultCode.getMessage(), data);
    }
}
