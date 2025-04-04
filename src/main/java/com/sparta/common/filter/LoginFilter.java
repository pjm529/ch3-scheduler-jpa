package com.sparta.common.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sparta.common.component.BaseResponse;
import com.sparta.common.component.CommonExceptionResultMessage;
import com.sparta.common.component.JSONResult;
import com.sparta.common.component.SystemValues;
import com.sparta.common.exception.CustomException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.AntPathMatcher;

import java.io.IOException;

@Slf4j
public class LoginFilter implements Filter {

    // 인증을 하지 않아도 될 URL Path 배열
    private static final String[] WHITE_LIST = {"/", "/swagger-ui/**", "/api-docs", "/swagger-ui-custom.html",
            "/v3/api-docs/**", "/api-docs/**", "/swagger-ui.html", "/api/auth/**"};

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String requestURI = httpRequest.getRequestURI();

        log.info("로그인 필터 로직 실행");

        try {
            // 화이트리스트에 해당하지 않는 경우 로그인 체크
            if (!isWhiteList(requestURI)) {
                if (httpRequest.getSession(false) == null ||
                        httpRequest.getSession(false).getAttribute(SystemValues.LOGIN_USER.getValue()) == null) {
                    throw new CustomException(CommonExceptionResultMessage.AUTHENTICATION_FAILED);
                }
            }

            // 필터 체인 계속 실행 (다음 필터 또는 서블릿/컨트롤러 호출)
            chain.doFilter(request, response);
        } catch (CustomException ex) {
            // 필터 내에서 직접 예외 처리 및 JSON 응답 작성
            handleCustomException(httpRequest, httpResponse, ex);
        }
    }

    // URL이 화이트 리스트에 포함되어 있는지 확인하는 메서드
    private boolean isWhiteList(String requestURI) {
        AntPathMatcher matcher = new AntPathMatcher();
        for (String pattern : WHITE_LIST) {
            if (matcher.match(pattern, requestURI)) {
                return true;
            }
        }
        return false;
    }

    // CustomException을 처리하여 JSON 응답을 직접 작성하는 메서드
    private void handleCustomException(HttpServletRequest request, HttpServletResponse response, CustomException ex)
            throws IOException {
        log.error("Exception URI : " + request.getRequestURI());
        log.error("customException : " + ex.getMessage(), ex);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        // BaseResponse 및 JSONResult 객체 구성
        BaseResponse res = new BaseResponse();
        res.setJsonResult(JSONResult.failure(CommonExceptionResultMessage.AUTHENTICATION_FAILED, ""));

        String responseBody = objectMapper.writeValueAsString(res);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(responseBody);
    }
}
