package com.sparta.todo.follow.controller;

import com.sparta.todo.follow.service.FollowService;
import com.sparta.todo.global.response.CommonResponse;
import com.sparta.todo.global.util.UserDetailsImpl;
import com.sparta.todo.todo.dto.TodoResponseDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class FollowController {

    private final FollowService followService;

    @PostMapping("/follows/{toUserId}")
    public ResponseEntity<CommonResponse<Void>> createFollow(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long toUserId) {
        followService.createFollow(userDetails.getUser(), toUserId);
        return ResponseEntity.status(HttpStatus.OK.value()).body(
                CommonResponse.<Void>builder().message("팔로우가 성공하였습니다.").build());
    }

    @DeleteMapping("/follows/{toUserId}")
    public ResponseEntity<CommonResponse<Void>> deleteFollow(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long toUserId) {
        followService.deleteFollow(userDetails.getUser(), toUserId);
        return ResponseEntity.status(HttpStatus.OK.value()).body(
                CommonResponse.<Void>builder().message("팔로우가 취소되었습니다.").build());
    }

    @GetMapping("/follows/todo")
    public ResponseEntity<CommonResponse<List<TodoResponseDto>>> getAllFollowingPost(
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<TodoResponseDto> responseDtos =
                followService.getAllFollowingPost(userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK.value()).body(
                CommonResponse.<List<TodoResponseDto>>builder().data(responseDtos).build());
    }
}
