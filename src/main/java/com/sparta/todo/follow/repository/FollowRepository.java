package com.sparta.todo.follow.repository;

import com.sparta.todo.follow.entity.Follow;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    Optional<Follow> findByFromUserIdAndToUserId(Long fromUserId, Long toUserId);

    List<Follow> findAllByFromUserId(Long id);
}
