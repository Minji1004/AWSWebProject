package com.jojoldu.book.springboot.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    //Optional은 "존재할 수도 있지만 안 할수도 있는 객체", 즉 "null이 될 수도 있는 객체"을 감싸고 있는 일종의 래퍼 클래스.
    //NullPointException 처리를 하지 않아도 된다.
    //Optional은 최대 1개의 원소를 가지고 있는 특별한 Stream이라 생각하면 좋다.
    Optional<User> findByEmail(String email);
}
