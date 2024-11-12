package com.project.controller;

import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.project.domain.Login;
import com.project.service.LoginService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;
    private final PasswordEncoder passwordEncoder;
    private final OAuth2AuthorizedClientService authorizedClientService;

    @RequestMapping("/loginForm.do")
    public String loginForm() {
        return "login/loginForm";
    }

    @RequestMapping("signUp.do")
    public String signUp() {
        return "login/signup";
    }

    @RequestMapping("memberForm.do")
    public String signForm() {
        return "login/memberForm";
    }

    @RequestMapping("ownerForm.do")
    public String onwerForm() {
        return "login/ownerForm";
    }

    @RequestMapping("home.do")
    public String home() {
        return "home/home-category";
    }

    @RequestMapping("findPwd.do")
    public String findPwd() {
        return "login/findPwd";
    }

    // 아이디 중복검사
    @PostMapping("/idCheck")
    @ResponseBody
    public ResponseEntity<Integer> idCheck(@RequestParam("memberId") String memberId) {
        int result = loginService.isUserIdCheck(memberId);
        return ResponseEntity.ok(result);
    }

    // 닉네임 중복검사
    @PostMapping("/nicknameCheck")
    @ResponseBody
    public ResponseEntity<Integer> nicknaCheck(@RequestParam("nickname") String nickname) {
        int result = loginService.isUserNicknameCheck(nickname);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/joinMember")
    public String joinMember(@ModelAttribute("Login") Login login, Model model) {
        int result = 0;
        result = loginService.joinMember(login);

        model.addAttribute("result", result);

        return "login/joinResult";
    }

    // 일반 로그인
    @RequestMapping("/login_ok.do")
    public String login_ok(@ModelAttribute("login") Login login,
                           HttpSession session,
                           Model model) {
        Login db = loginService.getUser(login.getMemberId());
        int result = 0;

        if (db == null) {
            result = -1;
        } else {
            if (passwordEncoder.matches(login.getPasswd(), db.getPasswd())) {
                result = 1;
                session.setAttribute("loginUser", db);
                session.setAttribute("user_ID", db.getId());
                session.setAttribute("user_nickname", db.getNickname());
            } else {
                result = -1;
            }
        }

        model.addAttribute("result", result);
        model.addAttribute("login", login);

        return "login/loginResult";
    }

    // 간편 로그인(최초 회원가입 포함)
    @GetMapping("/logincheck.do")
    public String loginCheck(Authentication authentication, HttpSession session) {
        if (authentication instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
            String registrationId = oauthToken.getAuthorizedClientRegistrationId();

            // accessToken 가져오기
            OAuth2AuthorizedClient authorizedClient = authorizedClientService.loadAuthorizedClient(
                    registrationId, oauthToken.getName());
            String accessToken = authorizedClient.getAccessToken().getTokenValue();

            // accessToken을 사용해 사용자 정보 가져오기
            Map<String, Object> userInfo = fetchUserInfo(registrationId, accessToken);
            String tempId = null;
            String tempEmail = null;

            if ("naver".equals(registrationId)) {
                Map<String, Object> response = (Map<String, Object>) userInfo.get("response");
                if (response != null) {
                    tempId = (String) response.get("id");
                    tempEmail = (String) response.get("email");
                    
                    int result =0;
                    Login db = loginService.getNaverUser(tempId); // naverId 란에 tempId 있는지 조회
                    
                    
                    if(db == null) { // 첫 로그인
                        String randomNickname = "User_" + UUID.randomUUID().toString().substring(0, 8);
                    	db = loginService.insertNaverJoin(tempId, tempEmail, randomNickname);
                    	 session.setAttribute("loginUser", db);
                         session.setAttribute("user_ID", db.getMemberId());  // memberId를 세션에 저장
                         session.setAttribute("user_nickname", db.getNickname());
                    
                    }
                
                    session.setAttribute("loginUser", db);
                    session.setAttribute("user_ID", db.getMemberId());  // memberId를 세션에 저장
                    session.setAttribute("user_nickname", db.getNickname());
                    
                    return "redirect:/home.do";
   
                }
            } else if ("kakao".equals(registrationId)) {
                tempId = String.valueOf(userInfo.get("id"));
                Map<String, Object> kakaoAccount = (Map<String, Object>) userInfo.get("kakao_account");
                if (kakaoAccount != null) {
                    tempEmail = (String) kakaoAccount.get("email");
                }
            } else if ("google".equals(registrationId)) {
                tempId = (String) userInfo.get("sub");
                tempEmail = (String) userInfo.get("email");

            }

          
            return "redirect:/home.do";
        } else {
            return "redirect:/login_ok.do";
        }
    }
    
    
    private Map<String, Object> fetchUserInfo(String registrationId, String accessToken) {
        String userInfoEndpoint;
        if ("naver".equals(registrationId)) {
            userInfoEndpoint = "https://openapi.naver.com/v1/nid/me";
        } else if ("kakao".equals(registrationId)) {
            userInfoEndpoint = "https://kapi.kakao.com/v2/user/me";
        } else if ("google".equals(registrationId)) {
            userInfoEndpoint = "https://www.googleapis.com/oauth2/v3/userinfo";
        } else {
            return null;
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        HttpEntity<String> entity = new HttpEntity<>("", headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map> response = restTemplate.exchange(userInfoEndpoint, HttpMethod.GET, entity, Map.class);
        return response.getBody();
    }   
    
    
    
}
