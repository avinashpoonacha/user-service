package com.avi.microservices.user.VO;

import com.avi.microservices.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseObjectVO {

    private User user;
    private Department department;
}
