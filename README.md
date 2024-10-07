# bulletin board (온라인 게시판)
사용자가 게시글을 작성하고, 댓글을 남길 수 있는 간단한 게시판 시스템입니다. </br>
게시글 작성, 조회, 수정, 삭제 기능과 파일 업로드, 댓글, 좋아요 기능 등이 있습니다.
---

# 주요 기능
1. 회원
    + 회원 가입, 로그인, 로그인 후 회원별 기능을 이용
        - 회원은 이메일과 비밀번호를 입력하여 가입한다.
        - 회원은 일반 사용자(USER)와 관리자(ADMIN)로 분류된다.
    
2. 게시글
   + 조회, 추가, 수정, 삭제 기능
      - 게시글에는 제목, 내용, 글쓴이가 있다.
   + 비밀글 기능
      - 비밀글로 설정하면 본인 외 다른 사용자는 게시글을 열람할 수 없다.
   + 파일 업로드 기능
      - 게시글에는 첨부 파일을 업로드 할 수 있다.
   + 유효성 검사 기능
      - 게시글을 추가하거나 수정하는 경우 유효성 검사를 통해 잘못된 부분을 응답
      - 게시글 제목은 공백을 포함하여 최대 15자까지 입력할 수 있다
      - 게시글 제목에는 비속어(영어)를 포함할 수 없다. (PurgoMalum API로 욕설이 포함되어 있는지 확인)
        + 유효성 검사
          - 게시글 제목은 최대 15자를 넘는 이름을 가져서는 안된다.
            - [x] 동해물과백두산이마르고닳도록
            - [ ] 동해물과 백두산이 마르고 닳도록
             ```gherkin
             Given 게시글 제목이 "동해물과 백두산이 마르고 닳도록"일 때
              When 제목을 생성하면
                Or 제목을 수정하면
              Then 400 Bad Request를 응답한다
               And "게시글의 제목은 15자를 넘길 수 없습니다."라고 응답한다. 
             ```
   
          - 제목은 비속어를 포함하지 않는다.
            - PurgoMalum에서 욕설이 포함되어 있는지 확인한다. (영어만 확인 가능)
            ```gherkin
            Given 게시글을 생성하거나 수정할 때
             When PurgoMalum에서 욕설이 포함되어 있는지 확인한다.
              And 욕설이 포함되어 있으면
             Then 400 Bad Request를 응답한다
              And "게시글의 제목에 욕설을 입력할 수 없습니다."라고 응답한다.
            ```
---
3. 카테고리
   + 글을 작성할 때는 카테고리를 선택해야 한다.
   + ADMIN 사용자는 관리자 용 페이지에서 카테고리를 등록/수정/삭제할 수 있다.
---