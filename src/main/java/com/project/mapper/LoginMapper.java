package com.project.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.project.domain.Login;

@Mapper
public interface LoginMapper {
	
	// 아이디 중복조회
	int isUserIdCheck(String memberId);
	
	// 닉네임 증복조회
	int isUserNicknameCheck(String nickname);
	
	// 회원가입
	int joinMember(Login login);
	
	// 회원조회
	Login getUser(String memberId);
	
	// 네이버, 카카오, 구글 SNS 계정조회
	Login getNaverUser(String tempId);
	Login getKakaoUser(String tempId);
	Login getGoogleUser(String tempId);
	
	// 네이버, 카카오, 구글 SNS 최초 로그인 시 회원가입
	void insertNaverJoin(Login login);
	void insertKakaoJoin(Login login);
	void insertGoogleJoin(Login login);

}
