package com.sparta.todo.entity;

import com.sparta.todo.dto.CommentRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "comment")
public class Comment extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "comment", nullable = false)
    private String comment;

    // User 간의 다대일 관계 설정
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Todo 간의 다대일 관계 설정
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "todo_id")
    private Todo todo;

    public Comment(CommentRequestDto requestDto, User user, Todo todo) {
        this.user = user;
        this.todo = todo;
        this.comment = requestDto.getComment();
        this.username = user.getUsername();
    }
    public void update(CommentRequestDto requestDto) {
        this.comment = requestDto.getComment();
    }
}
