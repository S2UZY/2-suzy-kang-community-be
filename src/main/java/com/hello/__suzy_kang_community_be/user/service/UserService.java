package com.hello.__suzy_kang_community_be.user.service;

import com.hello.__suzy_kang_community_be.gobal.exception.CustomException;
import com.hello.__suzy_kang_community_be.gobal.exception.ErrorCode;
import com.hello.__suzy_kang_community_be.user.dto.request.UserRequestDto;
import com.hello.__suzy_kang_community_be.user.entity.User;
import com.hello.__suzy_kang_community_be.user.repository.UserRepository;
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

        user.setPassword(requestDto.getPassword());
        user.setNickname(requestDto.getNickname());
        user.setProfile(requestDto.getProfile());

        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
}
