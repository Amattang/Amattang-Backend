package com.example.amattang.security;

import com.example.amattang.domain.user.User;
import com.example.amattang.domain.user.User.PROVIDER;
import com.example.amattang.domain.user.UserRepository;
import com.example.amattang.exception.BadRequestException;
import com.example.amattang.payload.reponse.KakaoUserInfoReponseDto;
import com.example.amattang.restTemplate.KakaoRestTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

import static com.example.amattang.exception.ExceptionMessage.NOT_FOUND_USER;

@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private KakaoRestTemplate kakaoRestTemplate = new KakaoRestTemplate();


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
    @Transactional
    public UserDetails loadUserByUserId(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException(NOT_FOUND_USER + "id => " + userId));

        return UserPrincipal.create(user);
    }

    public User registerNewUser(String accessToken, String provider) {
        provider = provider.toUpperCase();
        if (provider.equals(PROVIDER.KAKAO.name())) {
            Optional<KakaoUserInfoReponseDto> kakaoInfo = kakaoRestTemplate.getKakaoUserNickName(accessToken);
            if (kakaoInfo.isEmpty()) throw new IllegalArgumentException("유효하지 않은 카카오 액세스 토큰입니다.");
            KakaoUserInfoReponseDto userInfo = kakaoInfo.get();
            try {
                User user = User.builder()
                        .id(provider + "_" + userInfo.getId())
                        .provider(PROVIDER.KAKAO)
                        .name(userInfo.getProperties().get("nickname"))
                        .build();
                UserPrincipal.create(user);
                return userRepository.save(user);
            } catch (NullPointerException e) {
                throw new NullPointerException("카카오 애플리케이션에서 닉네임을 받아 올 수 없습니다.");
            }
        } else if (provider.equals(PROVIDER.APPLE.name())) {

        }
        throw new IllegalArgumentException("로그인을 지원하지 않는 인증 기관입니다. => " + provider);
    }


}
