# [Spring] Todo Project
#### JDBC를 활용한 CRUD 구현
JDBC(Java Database Connectivity)란?
- Java에서 데이터베이스와 연결하여 데이터를 조회, 삽입, 수정, 삭제 등의 작업을 수행할 수 있도록 지원하는 Java 표준 API
- JDBC는 Java와 데이터베이스 간의 다리 역할을 하며, 다양한 데이터베이스 시스템과 상호작용할 수 있도록 설계되었다.
  
CRUD란?
- 데이터베이스 관리 및 응용 프로그램 개발에서 사용되는 기본적인 데이터 관리 기능
- Create (생성), Read (조회), Update (수정), Delete (삭제)
## 📋 요구사항 및 구현 결과
### - Level 0 API 명세 및 ERD 작성
☑️ API 명세서 작성 <br>
![image](https://github.com/user-attachments/assets/87243853-2aab-45e6-b339-2f3088237353)<br>
☑️ ERD 작성 <br>
<img width="358" alt="image" src="https://github.com/user-attachments/assets/d83bde90-cfea-4c34-aaac-541584a68748"><br>
☑️ SQL 작성
<details>
<summary>schedul.sql</summary>
<div markdown="1">

```java
use todo;

CREATE TABLE todo
(
    todoId   BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '일정 식별자',
    contents TEXT NOT NULL COMMENT '내용',
    userName VARCHAR(10) NOT NULL COMMENT '작성자명',
    password VARCHAR(16) NOT NULL COMMENT '비밀번호',
    createdDate NOT NULL DATETIME COMMENT '작성일',
    lastModifiedDate NOT NULL DATETIME COMMENT '수정일'
);

```
</details><br>

### - Level 1 일정 생성 및 조회
☑️ 일정 생성 
- 일정 생성 시 포함되어야 할 데이터는 할일, 작성자명, 비밀번호, 작성/수정일(날짜와 시간을 모두 포함한 형태)
- 각 일정의 고유 식별자를 자동으로 생성하여 관리
- 최초 입력 시, 수정일은 작성일과 동일<br>
![image](https://github.com/user-attachments/assets/0869c4ac-a9a8-47f0-b19c-6926ab9ad418)<br>

☑️ 전체 일정 조회
- 수정일(형태: YYYY-MM-DD), 작성자명을 바탕으로 조회
- 조건 중 한 가지만을 충족하거나, 둘 다 충족을 하지 않을 수도, 두 가지를 모두 충족할 수도 있다.

<br>- case1. 수정일 검색 <br>
<img width="618" alt="image" src="https://github.com/user-attachments/assets/cef2a2ef-2b27-4c58-a040-cdf3c343749c">
<details>
<summary>결과</summary>
<div markdown="1">

```json
[
    {
        "todoId": 8,
        "contents": "내용1",
        "userName": "1111",
        "createdDate": "2024-12-09 19:05:34",
        "lastModifiedDate": "2024-12-09 23:05:34"
    },
    {
        "todoId": 10,
        "contents": "수정 최종",
        "userName": "홍길동 최종",
        "createdDate": "2024-12-09 19:41:20",
        "lastModifiedDate": "2024-12-09 22:18:18"
    },
    {
        "todoId": 9,
        "contents": "내용이다",
        "userName": "동동",
        "createdDate": "2024-12-09 19:07:49",
        "lastModifiedDate": "2024-12-09 19:07:49"
    },
    {
        "todoId": 5,
        "contents": "내용입니다~",
        "userName": "도라에몽",
        "createdDate": "2024-12-09 17:37:02",
        "lastModifiedDate": "2024-12-09 17:37:02"
    }
]

```
</details><br>
- case2. 작성자명 검색 <br>
<img width="618" alt="image" src="https://github.com/user-attachments/assets/842d19fa-d55f-4f35-aa66-2f726145d8d6"><br>
<details>
<summary>결과</summary>
<div markdown="1">

```json
[
    {
        "todoId": 13,
        "contents": "과제 세션",
        "userName": "동동이",
        "createdDate": "2024-12-10 13:47:36",
        "lastModifiedDate": "2024-12-10 13:47:36"
    },
    {
        "todoId": 12,
        "contents": "과제 세션",
        "userName": "동동이",
        "createdDate": "2024-12-10 13:21:28",
        "lastModifiedDate": "2024-12-10 13:21:28"
    },
    {
        "todoId": 11,
        "contents": "알고리즘 문제 풀기",
        "userName": "동동이",
        "createdDate": "2024-12-10 09:36:48",
        "lastModifiedDate": "2024-12-10 09:36:48"
    }
]

```
</details><br>
- case3. 수정일 && 작성자명 검색 <br>
<img width="663" alt="image" src="https://github.com/user-attachments/assets/8d745849-8e20-46ea-9dd1-8db1b1f68517"><br><br>

<br>- case4. 수정일과 작성자명 검색 X <br>
모든 일정이 내림차순으로 조회됨<br>
<img width="663" alt="image" src="https://github.com/user-attachments/assets/ec4d96a0-cb15-45b0-9b7f-6114bcc715b7"><br>
<details>
<summary>결과</summary>
<div markdown="1">

```json
[
    {
        "todoId": 4,
        "contents": "내용입니다",
        "userName": "홍길동",
        "createdDate": "2024-12-09 17:19:31",
        "lastModifiedDate": "2024-12-10 17:19:31"
    },
    {
        "todoId": 13,
        "contents": "과제 세션",
        "userName": "동동이",
        "createdDate": "2024-12-10 13:47:36",
        "lastModifiedDate": "2024-12-10 13:47:36"
    },
    {
        "todoId": 12,
        "contents": "과제 세션",
        "userName": "동동이",
        "createdDate": "2024-12-10 13:21:28",
        "lastModifiedDate": "2024-12-10 13:21:28"
    },
    {
        "todoId": 6,
        "contents": "내용입니다~~",
        "userName": "홍길동",
        "createdDate": "2024-12-09 17:38:30",
        "lastModifiedDate": "2024-12-10 10:38:30"
    },
    {
        "todoId": 11,
        "contents": "알고리즘 문제 풀기",
        "userName": "동동이",
        "createdDate": "2024-12-10 09:36:48",
        "lastModifiedDate": "2024-12-10 09:36:48"
    },
    {
        "todoId": 8,
        "contents": "내용1",
        "userName": "1111",
        "createdDate": "2024-12-09 19:05:34",
        "lastModifiedDate": "2024-12-09 23:05:34"
    },
    {
        "todoId": 10,
        "contents": "수정 최종",
        "userName": "홍길동 최종",
        "createdDate": "2024-12-09 19:41:20",
        "lastModifiedDate": "2024-12-09 22:18:18"
    },
    {
        "todoId": 9,
        "contents": "내용이다",
        "userName": "동동",
        "createdDate": "2024-12-09 19:07:49",
        "lastModifiedDate": "2024-12-09 19:07:49"
    },
    {
        "todoId": 5,
        "contents": "내용입니다~",
        "userName": "도라에몽",
        "createdDate": "2024-12-09 17:37:02",
        "lastModifiedDate": "2024-12-09 17:37:02"
    }
]

```
</details><br>

☑️ 선택한 일정 조회 <br>
todoId를 파라미터로 받아 해당하는 Row를 조회하여 응답<br>
<img width="652" alt="image" src="https://github.com/user-attachments/assets/dd528f13-11a2-47a5-94e4-a0ae8ea428b6"><br>
<br>

### - Level 2 일정 수정 및 삭제
☑️ 선택한 일정 수정 <br>
todoId를 파라미터로 받아 해당하는 Row의 비밀번호와 입력한 비밀번호를 검증한 후 내용 or 작성자명 수정
<br>- case1. 정상 수정<br>
<img width="647" alt="image" src="https://github.com/user-attachments/assets/ed9a37c8-6bc5-4f72-92b7-bb23147ea1ba"><br>
<br>- case2. 비밀번호 오류<br>
<img width="647" alt="image" src="https://github.com/user-attachments/assets/de48a27e-2a9f-4517-88cf-dfce5354fbba"><br>


<br>☑️ 선택한 일정 삭제 <br>
<br>- case1. 정상 삭제<br>
<img width="651" alt="image" src="https://github.com/user-attachments/assets/84f373aa-98d1-4e8f-b8cd-301379155177">
<br>
<br>- case2. 비밀번호 오류<br>
<img width="650" alt="image" src="https://github.com/user-attachments/assets/dae9e26c-4f3a-4481-b3bf-d0c88cfe0112">
<br>


## 💬 고찰
- Controller - Service - Repository 의 역할과 각각의 관계에 대해 이해할 수 있었다.
- 에러처리를 잘 하지 못해 아쉽다.
- 일정 생성 시 작성일과 수정일은 SQL timestamp를 활용하여 자동 생성할 수 있다는 것을 알았다. -> 수정할 것 
