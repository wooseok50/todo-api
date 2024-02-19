package com.sparta.todo.comment.entity;

import com.sparta.todo.comment.dto.CommentRequestDto;
import com.sparta.todo.todo.entity.Todo;
import com.sparta.todo.user.entity.User;
import com.sparta.todo.util.Timestamped;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
