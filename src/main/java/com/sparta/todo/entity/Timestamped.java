package com.sparta.todo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class) // 자동으로 시간을 넣어주는 기능
public abstract class Timestamped {

    @CreatedDate // entity 객체가 생성되어 저장될 때 자동으로 저장이 된다.
    @Column(updatable = false) // 최초만(없데이트 되지 않게)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

    @LastModifiedDate // 변경된 시간 저장
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime modifiedAt;
}