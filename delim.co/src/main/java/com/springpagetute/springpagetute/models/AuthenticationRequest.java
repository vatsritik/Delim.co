package com.springpagetute.springpagetute.models;

import lombok.Data;
//SPRING SECURITY
public @Data class AuthenticationRequest {
    private String username;
    private String password;
}
