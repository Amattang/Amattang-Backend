##### 수익형 앱 런칭 IT 동아리 CMC 9기 니나노팀 아맞땅 백엔드 저장소<br>

## 아맞땅 (Amattang)
집 구하기 초보를 위한 체크리스트, 아맞땅<br>
* 앱스토어 : https://apps.apple.com/kr/app/%EC%95%84%EB%A7%9E%EB%95%85/id1612198396
* 구글 플레이 스토어 : https://play.google.com/store/apps/details?id=com.amattang

### 소셜 로그인
- 카카오
- 애플

### 구현 기능
1. 체크리스트 생성, 삭제
2. 공통 질문 체크리스트 작성
   1. 기본 정보, 외부 시설, 내부 시설의 카테고리 별로 답변 작성
   2. 공통 질문 중 질문 추가/삭제
3. 사용자 지정 질문 체크리스트 작성
   1. 공통 질문 이외에 체크해야 할 사항을 카테고리-하위 항목의 형식으로 작성
4. 체크리스트 별 이미지 등록/추가/삭제/메인 이미지 등록

### 컴포넌트 아키텍처
![img_6](https://user-images.githubusercontent.com/50989437/162874850-d2ef83f7-8412-454c-8583-9c5c2f79889c.png)
<br>
spring-dotenv로 환경 변수 관리<br>
github actions + github secrets -> aws s3 -> code deploy -> aws ec2<br>
로그는 elk로 관리하였으나, 서버 사양으로 로컬에서 테스트 용도로만 사용<br>

### ERD (Mysql)
![amattang-erd](https://user-images.githubusercontent.com/50989437/162874905-28330277-ca43-429e-acd8-05d3a2fce7b6.png)

### API List (Swagger)
![img](https://user-images.githubusercontent.com/50989437/162874930-2e14d222-b4bf-45b6-8947-8d921a83b235.png)
![img_1](https://user-images.githubusercontent.com/50989437/162874938-777aac20-51f5-466f-b759-a4c437da7fb7.png)
![img_2](https://user-images.githubusercontent.com/50989437/162874941-3bd7b62d-7f6e-4409-b312-75e7fe22c7af.png)
![img_3](https://user-images.githubusercontent.com/50989437/162874954-2a7790d8-aa8e-4fe5-b708-4f2c3931a7bc.png)


### 참고
* spring-dotenv : https://github.com/paulschwarz/spring-dotenv
