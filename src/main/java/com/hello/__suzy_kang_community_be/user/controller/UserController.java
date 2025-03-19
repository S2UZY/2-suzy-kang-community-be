package com.hello.__suzy_kang_community_be.user.controller;

import com.hello.__suzy_kang_community_be.gobal.response.ApiResponse;
import com.hello.__suzy_kang_community_be.user.dto.request.UserRequestDto;
import com.hello.__suzy_kang_community_be.user.dto.response.UserIdResponse;
import com.hello.__suzy_kang_community_be.user.dto.response.UserResponseDto;
import com.hello.__suzy_kang_community_be.user.entity.User;
import com.hello.__suzy_kang_community_be.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@ControllerAdvice
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<ApiResponse> createUser(@RequestBody UserRequestDto requestDto) {
        Long userId = userService.createUser(requestDto);

        return ResponseEntity.status(201)
                .body(new ApiResponse("user_post_success", true, new UserIdResponse(userId)));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse> updateUser(
            @PathVariable Long id,
            @RequestBody UserRequestDto requestDto
    ){
        User updateUser = userService.updateUser(id,requestDto);
        return ResponseEntity.ok()
                .body(new ApiResponse("user_patch_success", true, UserResponseDto.from(updateUser)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getUser(
            @PathVariable Long id
    ){
        User user = userService.getUser(id);
        return ResponseEntity.ok()
                .body(new ApiResponse("user_get_success", true, UserResponseDto.from(user)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllUsers() {
        List<User> users = userService.getAllUsers();
        List<UserResponseDto> userResponseDtos = users.stream()
                .map(UserResponseDto::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(new ApiResponse("user_get_success", true,userResponseDtos));
    }

}
