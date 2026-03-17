package Bhuvanesh.Mysql.sql.Controller;


import Bhuvanesh.Mysql.sql.Repository.UserAuthenticationRepository;
import Bhuvanesh.Mysql.sql.Repository.UserRepository;
import Bhuvanesh.Mysql.sql.Service.ToDoService;
import Bhuvanesh.Mysql.sql.Service.UserAuthService;
import Bhuvanesh.Mysql.sql.User;
import Bhuvanesh.Mysql.sql.UserAuth;
import Bhuvanesh.Mysql.sql.Utils.JwtUtil;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j

public class UserAuthController {


    private final  UserAuthenticationRepository userAuthenticationRepository;
     private  final UserAuthService userAuthService;
     private  final PasswordEncoder passwordEncoder;

     private  final JwtUtil jwtUtil;

     private   UserAuth userAuth;

    // POST Method: Pushes data to the table
    // URL: http://localhost:8080/api/users
    @PostMapping("/login")
    public ResponseEntity<String>  loginUser(@RequestBody Map<String,String> body) {
        String email= body.get("email");
        String password =body.get("password");
        var userOptional=userAuthenticationRepository.findByEmail(email);
        if(userOptional.isEmpty()){
            return new ResponseEntity<>("please regiusted",HttpStatus.UNAUTHORIZED);
        }
        UserAuth userAuth=userOptional.get();
        if(!passwordEncoder.matches(password,userAuth.getPassword())){
            return new ResponseEntity<>("invalid user",HttpStatus.UNAUTHORIZED);

        }
        String token=jwtUtil.generateToke(email);
        return ResponseEntity.ok(token);


    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody Map<String,String> body) {

        String email= body.get("email");
        String password =body.get("password");
        password=passwordEncoder.encode(password);
        if(userAuthenticationRepository.findByEmail(email).isPresent()){
            return  new ResponseEntity<>("Email already exist",HttpStatus.CONFLICT);

        }
        userAuthService.makePostcallForDB(userAuth.builder().email(email).password(password).build());
        return new ResponseEntity<>("succesfully registeered",HttpStatus.CREATED);
    }



}