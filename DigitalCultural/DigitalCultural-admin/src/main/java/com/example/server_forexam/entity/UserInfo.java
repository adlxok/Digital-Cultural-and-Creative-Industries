package com.example.server_forexam.entity;

import java.util.Arrays;

public class UserInfo {
    String[] roles = new String[0];
    String introduction;
    String avatar;
    String name;

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String role) {

        String[] newRoles = Arrays.copyOf(roles, roles.length + 1);
        // 将新角色添加到数组的最后一个位置
        newRoles[newRoles.length - 1] = role;
        // 更新roles数组
        this.roles = newRoles;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
