# 커뮤니티 서버 (Community Backend) 🚀

이 프로젝트는 Spring Boot 기반의 커뮤니티 서비스 백엔드 애플리케이션입니다. <br/> 사용자 관리, 게시물, 댓글, 좋아요 기능을 제공하는 RESTful API 서버입니다.

## 기술 스택 💻

- **언어**: Java 21 
- **프레임워크**: Spring Boot 3.4.3 
- **데이터베이스**: MySQL/MariaDB 
- **빌드 도구**: Gradle 
- **인증**: JWT (JSON Web Token) 

## 주요 의존성 📚

- Spring Boot Starter Web
- Spring Boot Starter Data JPA
- Spring Boot Starter Security
- JJWT (JWT 라이브러리)
- MariaDB/MySQL 커넥터
- Lombok

## 폴더 구조 📂
```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── suzy/
│   │           └── community_be/
│   │               ├── config/                 # 설정 클래스
│   │               ├── global/                 # 전역 유틸리티 및 예외 처리
│   │               │   ├── exception/          # 예외 처리 관련 클래스
│   │               │   ├── response/           # 응답 관련 클래스
│   │               │   └── utils/              # 유틸리티 클래스
│   │               ├── posts/                  # 게시물 관련 기능
│   │               │   ├── controller/         # 컨트롤러
│   │               │   ├── dto/                # DTO 클래스
│   │               │   │   ├── request/        # 요청 DTO
│   │               │   │   └── response/       # 응답 DTO
│   │               │   ├── entity/             # 엔티티 클래스
│   │               │   ├── repository/         # 리포지토리
│   │               │   └── service/            # 서비스
│   │               ├── security/               # 보안 관련 클래스
│   │               ├── sessions/               # 세션 관련 기능
│   │               │   ├── controller/
│   │               │   ├── dto/
│   │               │   └── service/
│   │               ├── users/                  # 사용자 관련 기능
│   │               │   ├── controller/
│   │               │   ├── dto/
│   │               │   ├── entity/
│   │               │   ├── repository/
│   │               │   └── service/
│   │               └── Application.java        # 애플리케이션 진입점
│   └── resources/                              # 리소스 파일
└──
```

## 데이터베이스 설계 🗃️

### 테이블 구조
![테이블 구조](https://github.com/user-attachments/assets/3fbdfadd-2940-4412-be88-511e93fdcfa5)

1. **users** - 사용자 정보 
   - id (PK)
   - email (unique)
   - password
   - nickname
   - profile
   - created_at
   - modified_at

2. **posts** - 게시물 정보 
   - id (PK)
   - user_id (FK)
   - title
   - content
   - image
   - views
   - created_at
   - modified_at

3. **comments** - 댓글 정보 
   - id (PK)
   - post_id (FK)
   - user_id (FK)
   - content
   - created_at
   - modified_at

4. **likes** - 좋아요 정보 
   - id (PK)
   - post_id (FK)
   - user_id (FK)

## 구현 기능 ✨

### 사용자 관리 
- 회원가입
- 로그인/로그아웃
- 사용자 정보 조회/수정/삭제
- 비밀번호 변경

### 게시물 관리 
- 게시물 작성/조회/수정/삭제
- 게시물 목록 조회
- 조회수 증가 기능

### 댓글 관리 
- 댓글 작성/조회/수정/삭제

### 좋아요 기능 
- 게시물 좋아요/취소
- 좋아요 상태 조회

### 인증 및 보안 
- JWT 기반 인증
- 쿠키를 통한 토큰 관리
- 보안 필터 구현

## 서버 구조 🏗️

이 애플리케이션은 계층형 아키텍처를 따릅니다:

1. **Controller Layer**: HTTP 요청을 처리하고 응답을 반환합니다.
2. **Service Layer**: 비즈니스 로직을 처리합니다.
3. **Repository Layer**: 데이터베이스와의 상호작용을 담당합니다.
4. **Entity Layer**: 데이터베이스 테이블과 매핑되는 객체를 정의합니다.

## API 응답 형식 📊

모든 API 응답은 다음과 같은 일관된 형식을 따릅니다:

```json
{
    "message": "응답 메시지",
    "data": 응답 데이터 (객체 또는 null)
}
```


## 설치 및 실행 방법 🚀

### 요구사항
- JDK 21
- MySQL 또는 MariaDB
- Gradle

### 설치 단계

1. 저장소 클론 📥

```bash
git clone https://github.com/yourusername/community-backend.git
cd community-backend
```

2. 데이터베이스 설정 🗄️
 - MySQL/MariaDB 데이터베이스 생성
 - `application.properties` 파일 생성 및 설정

```bash
spring.datasource.url=jdbc:mysql://localhost:3306/community_db
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

jwt.secret=your_jwt_secret_key
jwt.expiration=86400000
```


3. 애플리케이션 실행 🚀

```bash
./gradlew build
./gradlew bootRun
```

4. 서버 접속 🌐
   - 기본적으로 서버는 `http://localhost:8080`에서 실행됩니다.

## 개발자 정보 👩‍💻

이 프로젝트는 1인 개발로 진행되었습니다.

## 회고 및 개선사항 🔍
### 개발 회고 📝
백엔드 개발은 이번이 처음이었습니다. 2주간의 삽질(?)을 통해 이 프로젝트를 완성할 수 있었습니다. 처음 Spring Boot를 접하면서 각 단계마다 새로운 오류와 마주쳤고, 이를 해결하는 과정에서 많은 것을 배웠습니다.
데이터베이스 연결, JWT 인증 구현, 보안 설정 등에서 특히 어려움을 겪었습니다. 한 단계를 넘어갈 때마다 예상치 못한 오류가 발생했고, 팀원들과 LLM 도움도 받으며 문제를 해결했던 기억이 나는 것 같습니다. 처음에는 어려웠지만 차근차근 해결해나가는 과정이 재밌었습니다. 전체적으로 백엔드 개발의 맥락을 이해하는데 많은 도움이 되었습니다.

### 개선사항 🛠️
- 비밀번호 암호화 구현 필요
- 입력값 검증 로직 추가
- 예외 처리 강화
- 테스트 코드 작성

