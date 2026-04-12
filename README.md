# 📅 일정관리 앱

> Spring Boot와 JPA를 활용한 일정관리 REST API 서버입니다.

---

## 📌 프로젝트 소개

일정을 생성하고 관리할 수 있는 REST API 서버입니다.
일정 생성, 조회, 수정, 삭제(CRUD) 기능을 제공하며
비밀번호를 통해 본인의 일정만 수정/삭제할 수 있습니다.
도전과제로 댓글 기능과 입력값 검증도 구현했습니다.

---

## 🛠️ 기술 스택

| 분류 | 기술 |
|---|---|
| Language | Java 17 |
| Framework | Spring Boot 3.5.5 |
| ORM | Spring Data JPA |
| Database | MySQL |
| 편의 기능 | Lombok |

---

## 📁 패키지 구조

    src/main/java/com/example/schedule
    ├── controller   # HTTP 요청 처리
    ├── service      # 비즈니스 로직
    ├── repository   # DB 접근
    ├── entity       # DB 테이블 매핑
    └── dto          # 요청/응답 데이터

---

## 📊 ERD

<img width="345" height="175" alt="화면 캡처 2026-04-13 073211" src="https://github.com/user-attachments/assets/12e980c4-2edd-4c18-b6ce-a4c892c891cb" />

---

## 📋 API 명세서

### 일정 API

| 기능 | Method | URL | Request Body | Response | 상태코드 |
|---|---|---|---|---|---|
| 일정 생성 | POST | /schedules | title, content, author, password | id, title, content, author, createdAt, modifiedAt | 201 |
| 전체 조회 | GET | /schedules | - | List | 200 |
| 단건 조회 | GET | /schedules/{id} | - | id, title, content, author, createdAt, modifiedAt, comments | 200 |
| 일정 수정 | PATCH | /schedules/{id} | title, author, password | id, title, content, author, createdAt, modifiedAt | 200 |
| 일정 삭제 | DELETE | /schedules/{id} | password | - | 204 |

### 댓글 API

| 기능 | Method | URL | Request Body | Response | 상태코드 |
|---|---|---|---|---|---|
| 댓글 생성 | POST | /schedules/{id}/comments | content, author, password | id, content, author, scheduleId, createdAt, modifiedAt | 201 |

---

## ⚙️ 공통 조건

- 3 Layer Architecture (Controller → Service → Repository) 적용
- JPA를 사용하여 DB 연동
- 수정/삭제 시 비밀번호 검증 필요
- API 응답에 비밀번호 제외
- 작성일/수정일은 JPA Auditing으로 자동 관리

---

## ✅ 구현 기능

### 필수 기능
- [x] Lv1. 일정 생성
- [x] Lv2. 일정 조회 (전체/단건)
- [x] Lv3. 일정 수정
- [x] Lv4. 일정 삭제

### 도전 기능
- [x] Lv5. 댓글 생성 (일정당 최대 10개)
- [x] Lv6. 일정 단건 조회 업그레이드 (댓글 목록 포함)
- [x] Lv7. 유저 입력값 검증
