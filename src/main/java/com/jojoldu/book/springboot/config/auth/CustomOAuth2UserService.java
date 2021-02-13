package com.jojoldu.book.springboot.config.auth;

import com.jojoldu.book.springboot.config.auth.dto.OAuthAttributes;
import com.jojoldu.book.springboot.config.auth.dto.SessionUser;
import com.jojoldu.book.springboot.domain.user.User;
import com.jojoldu.book.springboot.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;
    private final HttpSession httpSession;


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        //registrationId - 현재 로그인 진행중인 서비스를 구분하는 코드. 구글 로그인인지, 네이버 로그인인지 구분하기 위해 사용
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        //userNameAttributeName - OAuth2 로그인 진행 시 키가 되는 필드값을 이야기. Primary Key와 같은 의미. 
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        //OAluth2User의 Attribute를 담을 클래스. 네이버 등 다른 소셜 로그인도 이 클래스를 사용
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        User user = saveOrUpdate(attributes);

        //SessionUser - 세션에 사용자 정보를 저장하기 위한 DTO 클래스. 왜 User를 사용하지 않고 따로 쓸까?
        //User 클래스에 직렬화를 구현하지 않았고, Entity이기 때문에 언제 다른 엔터티와 관계가 형성될 지 모른다.
        //자식 엔터티까지 직렬화 대상이 되면 성능이슈, 부수 효과가 발생.
        //직렬화 기능을 가진 세션 DTO(SessionUser)를 하나 추가로 만드는 것이 이후 운영 및 유지보수에 많은 도움이 됨.
        httpSession.setAttribute("user", new SessionUser(user));

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey()
        );

    }

    private User saveOrUpdate(OAuthAttributes attributes){
        User user = userRepository.findByEmail(attributes.getEmail())
                        .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
                        .orElse(attributes.toEntity());

        return userRepository.save(user);
    }
}
