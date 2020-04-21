package com.spring.test.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass//class내 변수 2개를 칼럼으로 인식하도록 한다.
@EntityListeners(AuditingEntityListener.class)//이 클래스에 auditing(회계감사)기능을 포함시킨다. auditing은 공통적으로 쓸 칼럼을 자동으로 넣는 기능이다.
public abstract class BaseTimeEntity {

    @CreatedDate//
    private LocalDateTime createdDate;

    @LastModifiedDate//
    private LocalDateTime modifiedDate;
}
