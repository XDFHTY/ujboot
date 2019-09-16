package com.cj.core.domain;

import com.cj.core.entity.AuthRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer implements Serializable {

    private String token;

    //ID
    private long customerId;

    //Name
    private String customerName;


    private String password;

    //Type
    private String customerType;

    //roles
    private List<AuthRole> roles;
}
