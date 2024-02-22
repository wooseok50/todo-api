package com.sparta.todo.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.sparta.todo.user.entity.User;
import com.sparta.todo.user.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User user;

    @Test
    @DisplayName("findByUsername 테스트")
    void findByUsernameTest() {
        // given
        User user = new User("name", "name1234");
        userRepository.save(user);

        // when
        Optional<User> findUser = userRepository.findByUsername(user.getUsername());

        // then
        assertEquals(user.getUsername(), findUser.get().getUsername());
    }
}
