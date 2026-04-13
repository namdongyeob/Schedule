package com.example.schedule.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter // 모든 필드에 대해한 getter 메서드를 자동 생성
@Entity // 이 클래스가 DB 테이블과 매핑되는 JPA 엔티티를 선언
@Table(name = "comments") // 실제 DB 테이블 이름을 "comments"로 지정
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 기본 생성자를 PROTECTED로 생성 -> 외부에서 빈 객체 직접 생성 방지
@EntityListeners(AuditingEntityListener.class) // @CreatedDatam, @LastModifiedData가 자동 동작하도록 해줌
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String content;
    @Column(nullable = false)
    private String author;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private Long scheduleId;
    @CreatedDate // entity가 처음 저장될 때 자동으로 현재 시각을 기록
    @Column(updatable = false) // 이후 수정 불가
    private LocalDateTime createdAt;
    @LastModifiedDate // entity가 수정될 떄마다 자동으로 현재 시각을 갱신
    private LocalDateTime modifiedAt;
    // 생성자
    public Comment (String content, String author, String password, Long scheduleId){
        this.content = content;
        this.author = author;
        this.password = password;
        this.scheduleId = scheduleId;
    }
}
