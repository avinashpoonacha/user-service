package com.avi.microservices.user.controller;

import com.avi.microservices.user.VO.ResponseObjectVO;
import com.avi.microservices.user.entity.User;
import com.avi.microservices.user.service.UserService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value ="/users")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/save")
    public User saveUser(@RequestBody User user) {
        log.info("inside controller of save User ");
        return userService.save(user);

    }

    @GetMapping("/{id}")
    @RateLimiter(name = "GetUser", fallbackMethod = "fallBackMethod")
    public ResponseEntity<ResponseObjectVO> getUserWithDepartment(@PathVariable("id") Long userId){
        log.info("inside controller of getUserWithDepartment ");
        return new ResponseEntity<ResponseObjectVO>(userService.getUserWithDepartment(userId), HttpStatus.OK);
    }

    public ResponseEntity<ResponseObjectVO> fallBackMethod(Exception e){
        log.info("inside  fallback method controller of getUserWithDepartment ");
        ResponseObjectVO responseObjectVO = new ResponseObjectVO();
        responseObjectVO.setUser(new User(211100L,"","","",null));

        return new ResponseEntity<ResponseObjectVO>(responseObjectVO, HttpStatus.TOO_MANY_REQUESTS);
    }


}
