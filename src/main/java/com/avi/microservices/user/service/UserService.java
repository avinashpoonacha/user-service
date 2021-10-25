package com.avi.microservices.user.service;

import com.avi.microservices.user.VO.Department;
import com.avi.microservices.user.VO.ResponseObjectVO;
import com.avi.microservices.user.entity.User;
import com.avi.microservices.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.file.Files;
import java.nio.file.Paths;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private CircuitBreakerFactory circuitBreakerFactory;

    public User save(User user) {
        log.info("inside service of save User ");
        return userRepository.save(user);
    }


    public ResponseObjectVO getUserWithDepartment(Long userId) {
        log.info("inside service of getUserWithDepartment ");
        ResponseObjectVO responseObjectVO = new ResponseObjectVO();
        User user = userRepository.findByUserId(userId);

        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitbreaker");
        String url = "http://DEPARTMENT-SERVICE/departments/" +user.getDepartmentId();

        Department department = (Department) circuitBreaker.run(() -> restTemplate.getForObject(url, Department.class), throwable -> getDefaultDepartment());
        responseObjectVO.setUser(user);
        responseObjectVO.setDepartment(department);
        return responseObjectVO;
    }

    private Department getDefaultDepartment() {
        try {
            return new Department(Files.readAllBytes(Paths.get(getClass().getClassLoader().getResource("fallback.json").toURI())));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
