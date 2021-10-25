package com.avi.microservices.user.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Department {


    private Long departmentId;
    private String departmentName;
    private String departmentAddress;
    private String departmentCode;

    public Department(byte[] readAllBytes) {
    }
}
