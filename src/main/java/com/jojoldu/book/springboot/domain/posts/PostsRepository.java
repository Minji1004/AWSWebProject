package com.jojoldu.book.springboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

//JpaRepository - DAO라 불리는 DB Layer 접근자. Entity가 DTO라 보면 됨.
// JpaRepository<Entity 클래스, PK 타입>을 상속하면 기본적인 CRUD 메소드가 자동으로 생성.
//Entity 클래스와 기본 Entity Repository는 함께 위치해야한다!!
public interface PostsRepository extends JpaRepository<Posts, Long> {

    @Query("Select p From Posts p ORDER BY p.id DESC") //JPA에서 제공하지 않는 것은 이렇게 쿼리로 작성해도 됨
    List<Posts> findAllDesc();
}
