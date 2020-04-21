package com.spring.test.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/*
    posts class가 DB에 접근하게 해주는 클래스
    JPA에선 Repository라고 부르며 인터페이스로 생성해 JpaRepository<,>를 상속하면 생성된다.
    @Repository 어노테이션을 추가할 필요는 없지만, Entitiy 클래스와 Entity Repository는 함께 위치해야한다.
 */
public interface PostRepository extends JpaRepository<posts,Long>{
    @Query("SELECT p FROM posts p ORDER BY p.id DESC")
    List<posts> findAllDesc();

}
