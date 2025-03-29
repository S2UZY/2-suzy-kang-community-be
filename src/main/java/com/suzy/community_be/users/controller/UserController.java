package com.suzy.community_be.users.controller;

import com.suzy.community_be.gobal.response.ApiResponse;
import com.suzy.community_be.users.dto.request.PasswordRequestDto;
import com.suzy.community_be.users.dto.request.UserRequestDto;
import com.suzy.community_be.users.dto.response.UserIdResponse;
import com.suzy.community_be.users.dto.response.UserResponseDto;
import com.suzy.community_be.users.entity.User;
import com.suzy.community_be.users.service.UserService;
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
                .body(new ApiResponse("user_post_success", new UserIdResponse(userId)));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse> updateUser(
            @PathVariable Long id,
            @RequestBody UserRequestDto requestDto
    ){
        User updateUser = userService.updateUser(id,requestDto);
        return ResponseEntity.ok()
                .body(new ApiResponse("user_patch_success", UserResponseDto.from(updateUser)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getUser(
            @PathVariable Long id
    ){
        User user = userService.getUser(id);
        return ResponseEntity.ok()
                .body(new ApiResponse("user_get_success", UserResponseDto.from(user)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllUsers() {
        List<User> users = userService.getAllUsers();
        List<UserResponseDto> userResponseDtos = users.stream()
                .map(UserResponseDto::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(new ApiResponse("user_get_success",userResponseDtos));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteUser(
            @PathVariable Long id
    ){
        userService.deleteUser(id);
        return ResponseEntity.ok(new ApiResponse("user_delete_success",null));
    }

    @PatchMapping("/{id}/password")
    public ResponseEntity<ApiResponse> updatePassword(
            @PathVariable Long id,
            @RequestBody PasswordRequestDto requestDto
    ){
        userService.updatePassword(id,requestDto);
        return ResponseEntity.ok()
                .body(new ApiResponse("user_patch_success", null));
    }


}
