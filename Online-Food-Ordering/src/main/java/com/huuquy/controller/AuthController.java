package com.huuquy.controller;

import com.huuquy.config.JwtProvider;
import com.huuquy.model.Cart;
import com.huuquy.model.USER_ROLE;
import com.huuquy.model.User;
import com.huuquy.repository.CartRepository;
import com.huuquy.repository.UserRepository;
import com.huuquy.request.Login;
import com.huuquy.response.AuthResponse;
import com.huuquy.service.CustomerUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private CustomerUserDetailsService customerUserDetailsService;

    @Autowired
    private CartRepository cartRepository;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws Exception {
        System.out.println("Register");
        User isEmailExist = userRepository.findByEmail(user.getEmail());

        if(isEmailExist !=null) {
            throw  new Exception("Email is already used with another account");
        }
        User createUser = new User();
        createUser.setEmail(user.getEmail());
        createUser.setFullName(user.getFullName());
        createUser.setRole(user.getRole());
        createUser.setPassword(passwordEncoder.encode(user.getPassword()));


        User saveUser = userRepository.save(createUser);

        Cart cart = new Cart();
        cart.setCustomer(createUser);

        cartRepository.save(cart);

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("Register success");
        authResponse.setRole(saveUser.getRole());

        return  new ResponseEntity<>(authResponse, HttpStatus.CREATED);

    }

    @PostMapping("/login")

    public ResponseEntity<AuthResponse> login(@RequestBody Login login) {
        System.out.println("Log is auth controller");
        System.out.println(login.toString());
        Authentication authentication = authenticate(login.getEmail(), login.getPassword());
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        String role = authorities.isEmpty() ? null : authorities.iterator().next().getAuthority();
        String jwt = jwtProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("Register success");
        authResponse.setRole(USER_ROLE.valueOf(role));
        System.out.println("respone: " + authResponse);

        return  new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    private Authentication authenticate(String email, String password) {
        UserDetails userDetails = customerUserDetailsService.loadUserByUsername(email);
        System.out.println("user detail:" + userDetails.getPassword());
        if(userDetails == null) {
            throw  new BadCredentialsException("Invalid email...");
        }
        System.out.println("match"+ passwordEncoder.matches(password,userDetails.getPassword()));
        if(!passwordEncoder.matches(password,userDetails.getPassword())){
            throw  new BadCredentialsException("Invalid password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
