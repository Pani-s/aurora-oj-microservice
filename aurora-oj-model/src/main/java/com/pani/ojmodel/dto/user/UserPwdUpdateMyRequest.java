package com.pani.ojmodel.dto.user;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户更新密码个人信息请求
 *
 * @author pani
 */
@Data
public class UserPwdUpdateMyRequest implements Serializable {

    /**
     * 用户旧密码
     */
    private String userPassword;

    /**
     * 新密码
     */
    private String newPassword;

    /**
     * 校检新密码
     */
    private String checkedNewPassword;

    private static final long serialVersionUID = 1L;
}