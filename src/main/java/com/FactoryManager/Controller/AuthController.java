package com.FactoryManager.Controller;

import com.FactoryManager.DTO.AuthRequestDto;
import com.FactoryManager.DTO.AuthResponseDto;
import com.FactoryManager.DTO.SignUpRequestDto;
import com.FactoryManager.DTO.SignUpResponseDto;
import com.FactoryManager.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
public class AuthController {
    @Autowired
    UserService userService;



    @PostMapping(value = "/signup", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<SignUpResponseDto> signup(@Valid @ModelAttribute SignUpRequestDto signUpRequestDto) {
        System.out.println("Incoming Signup DTO: " + signUpRequestDto);
        SignUpResponseDto response = userService.signup(signUpRequestDto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public AuthResponseDto login(@Valid @RequestBody AuthRequestDto request){

        return userService.login(request);
    }

}