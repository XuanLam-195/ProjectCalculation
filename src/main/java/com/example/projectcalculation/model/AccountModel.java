package com.example.projectcalculation.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountModel {
    private Long id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private PermissionLevel permissionLevel;
}
