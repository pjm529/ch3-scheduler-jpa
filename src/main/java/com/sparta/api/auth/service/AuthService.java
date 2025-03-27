package com.sparta.api.auth.service;

import com.sparta.api.auth.dto.LoginDto;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthService {
    void login(LoginDto dto, HttpServletRequest request);
}
