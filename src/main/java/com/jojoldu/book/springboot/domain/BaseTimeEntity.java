package com.jojoldu.book.springboot.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
//BaseTimeEntity는 모든 Entity의 상위클래스가 되어 Entity들의 createdDate, modifiedDate를 자동으로 관리하는 역할
//Jpa Entity들이 @MappedSupperclass가 선언된 클래스를 상속할 경우 클래스의 필드들도 컬럼으로 인식하도록 한다. 
@MappedSuperclass
@EntityListeners({AuditingEntityListener.class})  //BaseTimeEntity에 Auditing 기능을 포함
public class BaseTimeEntity {

    @CreatedDate  //Entity가 생성될 때 자동으로 시간이 저장
    private LocalDateTime createdDate;

    @LastModifiedDate  //조회한 Entity의 값이 변경될 때 자동으로 시간 저장
    private LocalDateTime modifiedDate;

    @CreatedBy
    private Long createdId;

    @LastModifiedBy
    private Long modifiedId;
}
