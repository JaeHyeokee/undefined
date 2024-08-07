package com.lec.spring.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    //유저 번호
    private Long userId;

//    private Long id;

    // 유저 닉네임
    private String nickname;
    @JsonIgnore
    private String password;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRe_password() {
        return re_password;
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

    public LocalDateTime getRegDate() {
        return regDate;
    }

    public void setRegDate(LocalDateTime regDate) {
        this.regDate = regDate;
    }

    public String getPhonenum() {
        return phonenum;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }

    public List<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<Authority> authorities) {
        this.authorities = authorities;
    }

    @ToString.Exclude
    @JsonIgnore
    private String re_password;
    @JsonIgnore
    private String username;

    private String email;

    @JsonIgnore
    private LocalDateTime regDate;

    private String phonenum;

    private List<Authority> authorities = new ArrayList<>();

    // oAuth2 Client
    private String provider;
    private String providerId;

    // ROLE_PROVIDER 권한 여부
    private Boolean hasRoleProvider;

    // ROLE_MASTER 권한 여부
    private Boolean hasRoleMaster;


}






