package com.spring.test.config.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)//메소드의 파라미터로 선언된 객체에서만 사용할 수 있다는 뜻?
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginUser {//LoginUser라는 이름을 가진 어노테이션이 만들어진것
}
