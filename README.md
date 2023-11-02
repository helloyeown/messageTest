# 토이 프로젝트 : 쪽지 시스템

1. project init
   1. IDEA에서 spring boot 프로젝트 시작
   2. src 소스 참고하여 의존성 맞추기
2. db 설계
   1. h2 db 설치
   2. auto-ddl 활용하여 테스트 db 실행
   3. entity 로 db 정의
3. api 개발
   - 스택
      - 서버 : Spring Boot 2.7 (Gradle project)
      - DB : H2 (auto-ddl : create-drop)\
        https://www.h2database.com/html/main.html
   - 도메인
      - 사용자
         - 등록 시 고유 이름을 입력 받는다.
         - 중복인 이름으로는 등록/수정 할 수 없다.
         <!-- @Column(unique = true) 설정 -->
      - 쪽지
         - 읽음 상태를 확인할 수 있다.
         - 읽은 일시를 조회할 수 있다.
   - API 목록
      <!-- - 사용자 등록
      - 사용자 이름 수정
      - 사용자 조회  -->
      - 사용자 삭제
      - 쪽지 등록
      - 쪽지 내용 수정
      - 쪽지 상태 수정 (읽음)
      - 쪽지 조회
      - 쪽지 목록 조회 (페이징)
      - 쪽지 삭제
   - 선택사항
     - 로깅
       - api 요청 / 응답 시 (@ControllerAdvice)
       - api 동작 중 오류 발생 시
       - db 처리 실패 시
     - 사용자 인증 시스템
       - jwt 토큰 (패스워드 없이 아이디 정보만)
4. 로컬 테스트
   - postman
5. 시도해 보고 싶은 부분 or 추가해야 하는 작업
   - 중복 이름 예외 처리(추후 작업 예정)
   - Message Read 할 때, delFlag true인 항목 제외
   - Message modify 할 때, 삭제 됐으면 수정 못 하게