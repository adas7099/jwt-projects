package com.example.jwt.item.Controller;

import com.example.jwt.item.Model.AuthRequest;
import com.example.jwt.item.entity.Users;
import com.example.jwt.item.repository.UserRepository;
import com.example.jwt.item.utils.JwtUtils;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtUtils jwtUtils;
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Users users){
       Users us= userRepository.findByUserName(users.getUsername()).orElse(null);
       if(us!=null){
           return new ResponseEntity<>("User already exist", HttpStatus.BAD_REQUEST);
       }
       users.setPassword(passwordEncoder.encode(users.getPassword()));
       userRepository.save(users);
       return ResponseEntity.ok("User registered");
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthRequest request){
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUserName(),
                            request.getPassword()
                    )
            );
        }
        catch (Exception ex){
            throw  new UsernameNotFoundException("User Not Found");
        }
        UserDetails user=userRepository.findByUserName(request.getUserName()).orElseThrow(()->new UsernameNotFoundException("User not found"));
        String  token= jwtUtils.generateToken(user);
        return ResponseEntity.ok(token);
    }

}
