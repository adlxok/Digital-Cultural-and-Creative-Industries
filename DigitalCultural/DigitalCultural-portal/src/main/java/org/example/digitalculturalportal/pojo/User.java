package org.example.digitalculturalportal.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author adlx
 * @since 2024-07-13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户唯一标识，自增
     */
    private Long id;

    /**
     * 用户名，不能为空且唯一
     */
    private String username;

    /**
     * 密码，不能为空，通常存储哈希后的密码
     */
    private String password;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 邮箱，唯一
     */
    private String email;

    /**
     * 出生日期
     */
    private Date birthDate;

    /**
     * 性别：0->未知；1->男；2->女
     */
    private Integer gender;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 地址
     */
    private String address;

    /**
     * 城市
     */
    private String city;

    /**
     * 国家
     */
    private String country;

    /**
     * 用户头像图片地址
     */
    private String profileImageUrl;

    /**
     * 用户简介或个人介绍
     */
    private String bio;

    /**
     * 帐号启用状态:0->禁用；1->启用
     */
    private Boolean status;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    /**
     * 最后登录时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date loginTime;


}
