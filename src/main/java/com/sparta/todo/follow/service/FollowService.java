package com.sparta.todo.follow.service;

import com.sparta.todo.follow.entity.Follow;
import com.sparta.todo.follow.repository.FollowRepository;
import com.sparta.todo.global.exception.InvalidInputException;
import com.sparta.todo.todo.dto.TodoResponseDto;
import com.sparta.todo.todo.entity.Todo;
import com.sparta.todo.todo.repository.TodoRepository;
import com.sparta.todo.user.entity.User;
import com.sparta.todo.user.service.AuthService;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class FollowService {

    private final FollowRepository followRepository;
    private final AuthService authService;
    private final TodoRepository todoRepository;

    @Transactional
    public void createFollow(User fromUser, Long toUserId) {
        authService.findUser(toUserId);
        Follow follow = Follow.builder()
                .fromUserId(fromUser.getId())
                .toUserId(toUserId)
                .build();
        followRepository.save(follow);
    }

    @Transactional
    public void deleteFollow(User fromUser, Long toUserId) {
        authService.findUser(toUserId);
        Follow follow = followRepository.findByFromUserIdAndToUserId(fromUser.getId(), toUserId)
                .orElseThrow(
                        () -> new InvalidInputException("해당 팔로우를 찾을 수 없습니다.")
                );
        followRepository.delete(follow);
    }

    public List<TodoResponseDto> getAllFollowingPost(User user) {
        List<Follow> follows = followRepository.findAllByFromUserId(user.getId());
        List<Todo> posts = new ArrayList<>();

        for (Follow follow : follows) {
            Long toUserId = follow.getToUserId();
            posts.addAll(todoRepository.findByUserId(toUserId));
        }
        return posts.stream()
                .map(TodoResponseDto::new)
                .toList();
    }
}
