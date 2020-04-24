package com.spring.test.config.auth.dto;

import com.spring.test.domain.user.Role;
import com.spring.test.domain.user.User;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributesKey;
    private String name;
    private String email;
    private String picture;

    @Builder
    public OAuthAttributes(Map<String,Object> attributes, String nameAttributesKey, String name, String email, String picture){
        this.attributes=attributes;
        this.nameAttributesKey=nameAttributesKey;
        this.name=name;
        this.email=email;
        this.picture=picture;
    }

    //사용자 정보는 Map으로 OAuth2User에서 받기 때문에 하나하나 변환 필요
    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes){
        if("naver".equals(registrationId)) return ofNaver("id",attributes);
        return ofGoogle(userNameAttributeName, attributes);
    }

    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes){
        Map<String, Object> res=(Map<String, Object>)attributes.get("response");
        return  OAuthAttributes.builder()
                .name((String)res.get("name"))
                .email((String)res.get("email"))
                .picture((String)res.get("picture_image"))
                .attributes(res)
                .nameAttributesKey(userNameAttributeName)
                .build();
    }
    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes){
        return OAuthAttributes.builder()
                .name((String)attributes.get("name"))
                .email((String)attributes.get("email"))
                .picture((String)attributes.get("picture"))
                .attributes(attributes)
                .nameAttributesKey(userNameAttributeName)
                .build();
    }

    //User Entity 생성
    public User toEntity(){//이 엔티티가 생성되는 시점은 처음 가입할 때
        return User.builder()
                .name(name)
                .email(email)
                .picture(picture)
                .role(Role.GUEST)//처음 가입시 기본권한은 GUEST
                .build();
    }

}
