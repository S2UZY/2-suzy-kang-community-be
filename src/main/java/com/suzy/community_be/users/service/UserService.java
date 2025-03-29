package com.suzy.community_be.users.service;

import com.suzy.community_be.gobal.exception.CustomException;
import com.suzy.community_be.gobal.exception.ErrorCode;
import com.suzy.community_be.users.dto.request.PasswordRequestDto;
import com.suzy.community_be.users.dto.request.UserRequestDto;
import com.suzy.community_be.users.entity.User;
import com.suzy.community_be.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;

    public Long createUser(UserRequestDto requestDto){
        if(userRepository.existsByEmail(requestDto.getEmail())){
            throw new CustomException(ErrorCode.DUPLICATE_EMAIL);
        }

        User user = User.builder()
                .email(requestDto.getEmail())
                .password(requestDto.getPassword())
                .nickname(requestDto.getNickname())
                .profile(requestDto.getProfile())
                .build();

        User savedUser = userRepository.save(user);
        return savedUser.getId();
    }

    @Transactional(readOnly = true)
    public User getUser(Long userId){
        return userRepository.findById(userId)
                .orElseThrow(()->new CustomException(ErrorCode.USER_NOT_FOUND));
    }

    public User updateUser(Long userId, UserRequestDto requestDto){
        User user = userRepository.findById(userId)
                .orElseThrow(()->new CustomException(ErrorCode.USER_NOT_FOUND));

        user.setNickname(requestDto.getNickname());
        user.setProfile(requestDto.getProfile());

        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public void deleteUser(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(()->new CustomException(ErrorCode.USER_NOT_FOUND));

        userRepository.delete(user);
    }

    public void updatePassword(Long userId, PasswordRequestDto requestDto){
        User user = userRepository.findById(userId)
                .orElseThrow(()->new CustomException(ErrorCode.USER_NOT_FOUND));

        user.setPassword(requestDto.getPassword());
    }

}
