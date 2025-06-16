
1. 프로젝트 개요
---------------------------------------
이 프로젝트는 사용자가 자전거 경로(체크포인트)를 지도 위에 저장, 선택, 삭제할 수 있는 웹 애플리케이션입니다.  
사용자는 로그인/회원가입을 통해 자신만의 체크포인트를 저장하고, 이를 시각적으로 확인할 수 있습니다.

주요 기술 스택:
- 백엔드: Java, Spring Boot
- 프론트엔드: AngularJS
- 데이터베이스: MySQL 8.0 (account_db)
- 지도 API: OpenStreetMap (Leaflet.js)

---------------------------------------
2.  MySQL 데이터베이스 설정 방법
---------------------------------------

1) MySQL이 설치되어 있고, 포트가 3306 (기본값)인지 확인합니다.  
   인스턴스 이름은 MySQL80 기준입니다.

2) MySQL workbench 실행 후 아래 명령으로 데이터베이스 생성:
   CREATE DATABASE account_db CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

---------------------------------------
3. application.properties 설정
---------------------------------------

src/main/resources/application.properties 파일 예시:

spring.datasource.url=jdbc:mysql://localhost:3306/account_db
spring.datasource.username=root
spring.datasource.password=1234  # ← 본인의 비밀번호로 수정
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=none
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

---------------------------------------
4. 프로젝트 실행 방법
---------------------------------------

1) MySQL 서버를 먼저 실행합니다.
2) jdk 17버전으로 설정합니다
3) 동기화를 해줍니다.
4) lombok 설치합니다.
5) 실행/디버그 구성의 RIDEMAP 편집에 들어가 작업디렉터리 설치한 demo 파일로 설정
6) Spring Boot 프로젝트를 실행합니다.  
   방법 예시:
   ./gradlew bootRun
   또는
   java -jar build/libs/demo-0.0.1-SNAPSHOT.jar
   또는
   상단의 실행버튼

7) 웹 브라우저에서 아래 주소로 접속:
   http://localhost:8080

---------------------------------------
5. 주요 기능 및 사용법
---------------------------------------

🔐 [회원가입 / 로그인]
- 사용자는 username, password로 계정을 생성하고 로그인할 수 있습니다.
- 로그인 성공 시 메인 지도 페이지로 이동

📍 [체크포인트 저장]
- 지도에서 특정 위치를 클릭하고, 이름과 번호를 입력하여 체크포인트 저장
- 저장된 체크포인트는 DB(account_db)에 저장됩니다.

📑 [체크포인트 불러오기 및 표시]
- 로그인한 사용자의 체크포인트만 지도에 표시됨
- 삭제된 체크포인트는 지도에서 숨김 처리되지만 DB에는 남음 (soft delete)

📑 [지도 저장]
- 임의로 지도 좌상단의 체크포인트 버튼을 이용해 점을 찍고 저장 가능
- 이후 불러오기 가능

🗑 [체크포인트 삭제]
- 체크박스를 통해 다중 선택 후 삭제 가능
- 삭제 시 DB에서는 visible = false로 변경되어 복구도 가능



📌 기타 기능:
- 각 사용자마다 고유한 체크포인트 관리
- 지도 확대/축소, 위치 이동 가능




---------------------------------------
 주의사항
---------------------------------------

- MySQL이 먼저 실행되어 있어야 Spring 서버가 정상 구동됩니다.
- DB 이름이 account_db와 정확히 일치해야 합니다.
- 포트가 3306이 아닌 경우, application.properties에서 수정이 필요합니다.
- 만약 account_db.sql import 중 오류가 발생하면, DB를 다시 삭제하고 처음부터 재생성하세요.

---------------------------------------
작성자: 김동근
제출일: 2025년 6월 XX일