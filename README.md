# TODO 백엔드 서버 API & 회원가입, 로그인

### 프로젝트 설명
1. 회원가입, 로그인을 구현
2. Spring security 활용한 인증/인가
3. JWT를 활용하여 todo 및 댓글을 처리
4. JPA 연관관계를 통한 회원과 todo 그리고 댓글을 구현
5. todo 완료처리로 인한 상태를 관리

---

### API 명세서
https://documenter.getpostman.com/view/32386230/2s9YywfKxf
![image](https://github.com/wooseok50/todo-api/assets/155416976/446f1b93-ae59-43f8-8e87-0bb330ffa394)

---

### ERD
![스크린샷 2024-02-06 134404](https://github.com/wooseok50/todo-api/assets/155416976/d419d141-613d-4880-a49a-81efc4a876fa)

### 회고
1. Spring security를 사용하여 인증, 인가 처리를 진행했다.
   로그인을 하는 순간 sessionID가 발급이 되고 로그인 성공을 하면 session 정보가 securtiy 내부에 저장이 된다.
   저장한 sessionID로 요청이 오는 순간 비교.

   인증과정
   UsernamePasswordAuthenticationFilter <- 얘를 통해 인증
   사용자가 username과 password 제공
   UsernamePasswordAuthenticationFilter는 인증된 사용자의 정보가 담긴 Authentication 종류 중 하나인 UsernamePasswordAuthenticationToken 을 만든다.
   그다음 AuthenticationManager에게 넘긴다. 그리고 인증 시도.

   성공하면 SecurityContextHolder에다가 Authentication(UsernamePasswordAuthenticationToken) 저장
   실패하면 SecurityContextHolder를 비운다.


2. 연관관계 이해
   연관관계를 통해 user와 todo, comment를 설계하였다.
   
   ```
   // Todo User 간의 다대일 관계 설정
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
   
   // Todo Comment 간의 일대다 관계 설정
    @OneToMany(mappedBy = "todo", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();
   ```

   comment는 user들에 의해 생성되며, todo에 작성되기에
   ```
   // User 간의 다대일 관계 설정
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Todo 간의 다대일 관계 설정
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "todo_id")
    private Todo todo;
   ```

3. 개선해야 할 문제

   예외처리 부분을 아직 온전히 구현되지 않았다.
   토큰이 유효하지 않거나, 로그인 시도에 전달된 정보가 일치하지 않을 경우들 이런 상황에 대해
   인증, 인가에 대한 공부를 더 하고, 이 프로젝트에 적용하여 수정할 계획이다.

   IntelliJ에서 제공하는 ERD를 사용하지 않고 직접 설계하는 것을 공부해야겠다.

   API 명세서를 postman을 사용하여 작성해봤다.
   프런트엔드가 api를 받아 볼 때 더 잘 이해할 수 있도록 다음 프로젝트에서는 swagger 적용하여 설계할 계확이다.
   
   
 
