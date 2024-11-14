package com.project.domain;

import java.time.LocalDateTime;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("login")
public class LoginDomain {

	private Long id;
    private String category;
    private String registration_number;
    private String member_id;
    private String naver_id;
    private String google_id;
    private String kakao_id;
    private String passwd;
    private String nickname;
    private String introduction;
    private String email;
    private String status;
    private LocalDateTime created_at;
    private LocalDateTime inactive_date;
    private LocalDateTime reactive_date;
    private LocalDateTime deleted_date;
    private String profile_image;
    private Long point;
    
    // 비밀번호 체크
    private String passwdCheck;
    private String mailid;
    private String domain;
}
