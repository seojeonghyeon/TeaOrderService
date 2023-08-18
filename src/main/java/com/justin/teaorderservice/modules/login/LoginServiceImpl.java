package com.justin.teaorderservice.modules.login;

import com.justin.teaorderservice.infra.auth.SecurityUtil;
import com.justin.teaorderservice.modules.member.Member;
import com.justin.teaorderservice.modules.member.MemberRepository;
import com.justin.teaorderservice.modules.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService{

    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final SecurityUtil securityUtil;

    /**
     *
     * @param phoneNumber 핸드폰 번호
     * @param simplePassword 간편 비밀번호
     * @return null 로그인 실패
     */
    @Override
    public Member simpleLogin(String phoneNumber, String simplePassword) {
        return memberRepository.findByPhoneNumber(phoneNumber)
                .filter(member -> passwordEncoder.matches(simplePassword, member.getSimpleEncryptedPwd()))
                .orElse(null);
    }

    /**
     *
     * @param phoneNumber 핸드폰 번호
     * @param password 비밀번호
     * @return null 로그인 실패
     */
    @Override
    public Member login(String phoneNumber, String password) {
        return memberRepository.findByPhoneNumber(phoneNumber)
                .filter(member -> passwordEncoder.matches(password, member.getEncryptedPwd()))
                .orElse(null);
    }

    @Override
    public Member getMemberWithAuthorities(String phoneNumber) {
        return memberService.findByPhoneNumber(phoneNumber);
    }

    @Transactional(readOnly = true)
    @Override
    public Member getMembersWithAuthorities() {
        return securityUtil.getCurrentUsername().flatMap(memberRepository::findByPhoneNumber).orElseGet(()->null);
    }
}