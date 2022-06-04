package com.example.demo.dto;

import com.example.demo.model.Role;
import com.example.demo.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserDto extends BaseDto {

    private String username;
    private String email;
    private String password;
    private Integer status;
    private List<RoleDto> roles = new ArrayList<>();
    private UserInfoDto userInfo;
    private String avatar;

    public UserDto() {
    }

    public UserDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.status = user.getStatus();
        if (user.getRoles() != null && !user.getRoles().isEmpty()) {
            for (Role role: user.getRoles()) {
                this.roles.add(new RoleDto(role));
            }
        }
        if (user.getUserInfo() != null) {
            this.userInfo = new UserInfoDto(user.getUserInfo());
        }
        this.avatar = user.getAvatar();
        this.createBy = user.getCreatedBy();
        this.createDate = user.getCreatedAt();
        this.updateBy = user.getUpdatedBy();
        this.updateDate = user.getUpdatedAt();
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<RoleDto> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleDto> roles) {
        this.roles = roles;
    }

    public UserInfoDto getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoDto userInfo) {
        this.userInfo = userInfo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
