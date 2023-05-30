# dodamdodam
## JPA와 Spring Security를 활용한 프로젝트입니다.
* 프로젝트 주제는 청년들의 문화 수준 증진을 통해 문화 강국으로 나아가는 것입니다.
* 화면은 클론코딩으로 제작하고 문구는 프로젝트 주제에 맞춰서 만들었습니다.
* 클론 코딩 사이트는 위시켓(https://makercenter.wadiz.kr/) 입니다

제가 작업한 퍼블리싱 부분은 메인 페이지, 사업 소개, 고객 센터, 채팅 입니다.

#### 백엔드 담당 분야
- 로그인, 회원가입, 판매 게시판, 결제를 담당했습니다.

#### 담당 분야 활용 기술
- JPA 기술을 활용하여 Repository를 통해 DBMS에 접근하여 정보를 조회하고 화면에 전달했습니다.

##### ▶ 로그인, 회원가입
1. Redis
2. Spring Security
3. OAuth 2.0
4. Thymeleaf
5. Ajax & Rest

##### ▶ 결제
1. 부트페이 API

##### ▶ 판매 게시판
1. Thymeleaf
2. Ajax & Rest

## 데이터베이스 테이블(DB ERD)
![dodamdodam](https://github.com/dev-mwYoon/dodamdodam/assets/122762471/35c5c3c8-afc6-453e-9516-248be04971f1)


## 😃 느낀점
1. Redis, Spring Security
&nbsp; Redis와 Spring Security를 사용하여 보안 로직을 구현하는 것은 기존의 관계형 DBMS를 사용하는 방식과는 다른 접근 방법이었습니다.
Redis와 Spring Security를 사용하여 보안 로직을 구현하는 것은 기존의 관계형 DBMS를 사용하는 방식과는 다른 접근 방법이었습니다. 이러한 기술을 적용하면 보다 편리하고 상위 수준의 기술을 활용할 수 있었으며, 이를 통해 더 나은 보안을 구현할 수 있습니다.
2. OAuth 2.0 
&nbsp; OAuth 2.0 기술을 도입하여 인가 권한을 판단하는 로직을 추가함으로써 로그인 기능에서 사용한 접근 권한 로직을 활용할 수 있다는 것을 발견했습니다. 이는 인증과 관련된 보안 로직을 간편하게 구현할 수 있음을 보여주었습니다. 이번 일을 계기로 상위 기술을 배우는 것의 즐거움과 개발자로서의 성취감을 느낄 수 있었습니다.

## 😥 아쉬운점

&nbsp; 
