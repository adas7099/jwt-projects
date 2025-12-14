package com.example.jwt.book.controller;

import com.example.jwt.book.Utils.JwtUtils;
import com.example.jwt.book.entity.User;
import com.example.jwt.book.model.JwtResponse;
import com.example.jwt.book.model.LoginRequest;
import com.example.jwt.book.model.RegisterRequest;
import com.example.jwt.book.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class LoginController {
    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    JwtUtils jwtUtils;
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest){
        String password=passwordEncoder.encode(registerRequest.getPassword());
        User user=new User(null,registerRequest.getRoles(),password,registerRequest.getEmail(),registerRequest.getUsername(),null);
        userRepository.save(user);
        return new ResponseEntity<>("User Created", HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest request){
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
        }
        catch (Exception ex){
            throw  new UsernameNotFoundException("User Not Found");
        }
        UserDetails userDetails=userDetailsService.loadUserByUsername(request.getUsername());
        List<String> roles=userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        String token = jwtUtils.generateToken(request.getUsername(),roles);
        JwtResponse response=new JwtResponse(token,request.getUsername(),String.join(",",roles));
        return ResponseEntity.ok(response);
    }
}
