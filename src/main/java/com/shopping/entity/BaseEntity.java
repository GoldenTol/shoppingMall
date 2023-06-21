package com.shopping.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

@EntityListeners(value = {AuditingEntityListener.class})
@MappedSuperclass
@Getter @Setter
public abstract class BaseEntity extends BaseTimeEntity {
    @CreatedBy // Entity 생성시 사용자의 id를 기록합니다.
    @Column(updatable = false)
    private String createdBy; // 생성자

    @LastModifiedBy // Entity 수정시 사용자의 id를 기록합니다.
    private String modifiedBy; // 수정자
}
