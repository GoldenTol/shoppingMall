package com.shopping.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@EntityListeners(value = {AuditingEntityListener.class})
@MappedSuperclass
@Getter @Setter
public abstract class BaseTimeEntity {
    @CreatedDate // Entity 생성 시 자동으로 시간을 기록합니다.
    @Column(updatable = false) //  Entity 수정 시 갱신하지 않습니다.
    private LocalDateTime regDate;

    @LastModifiedDate // Entity 수정 시 자동으로 시간을 기록합니다.
    private LocalDateTime updateDate;
}
