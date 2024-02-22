package com.sparta.todo.repository;

import com.sparta.todo.comment.repository.CommentRepository;
import com.sparta.todo.todo.entity.Todo;
import com.sparta.todo.user.entity.User;
import com.sparta.todo.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;


    private Todo todo;
    private User user;

}
