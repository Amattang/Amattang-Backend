package com.example.amattang.security;

import com.example.amattang.domain.user.User;
import com.example.amattang.domain.user.User.PROVIDER;
import com.example.amattang.domain.user.UserRepository;
import com.example.amattang.payload.reponse.KakaoUserInfoReponseDto;
import com.example.amattang.restTemplate.KakaoRestTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private KakaoRestTemplate kakaoRestTemplate = new KakaoRestTemplate();


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    public UserDetails loadUserByUserId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException());
        return UserPrincipal.create(user);
    }

    public User registerNewUser(String accessToken, String provider) {
        provider = provider.toUpperCase();
        if (provider.equals(PROVIDER.KAKAO.name())) {
            KakaoUserInfoReponseDto userInfo = kakaoRestTemplate.getKakaoUserNickName(accessToken);
            User user = User.builder()
                    .id(provider + "_" + userInfo.getId())
                    .provider(PROVIDER.KAKAO)
                    .name(userInfo.getProperties().get("nickname"))
                    .build();
            UserPrincipal.create(user);
            return userRepository.save(user);
        } else if (provider.equals(PROVIDER.APPLE.name())) {

        }
        throw new NoSuchElementException("provider 명이 잘못되었습니다.");
    }


}
