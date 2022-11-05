package com.example.jwttest.service;

import com.example.jwttest.dto.User;
import com.example.jwttest.jwt.JwtTokenProvider;
import com.example.jwttest.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private final UserMapper userMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public UserService(UserMapper userMapper, BCryptPasswordEncoder bCryptPasswordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.userMapper = userMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    /*  */
    public User loadUserByUsername(String id){
        return userMapper.findUser(id);
    }

    /* 로그인 */
    public ResponseEntity<String> login(User user) {

        User user1 = userMapper.validId(user.getId());

        /* 아이디 검증 */
        try {
            if (user1.getId().equals(user.getId())) {
                /* 비밀 번호 검증 */
                if (!bCryptPasswordEncoder.matches(user.getPassword(), user1.getPassword())) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 비밀번호 입니다.");
                }
            }
        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("존재하지 않는 계정입니다.");
        }
        /* 토큰 발급*/
        String token = jwtTokenProvider.createToken(user.getId());
        return ResponseEntity.status(HttpStatus.OK).header("Authorization",token).body("로그인 성공");
    }

    /* 회원가입*/
    public ResponseEntity<String> register(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userMapper.register(user);
        return ResponseEntity.status(HttpStatus.OK).body("OK");
    }
}
