package com.sparta.common.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Enumeration;

@Slf4j
public class LoggingFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        // Filter에서 수행할 로깅 처리
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();

        log.info("=============== Request Info ===============");
        log.info("Request URI: {}", requestURI);
        log.info("Method: {}", httpRequest.getMethod());
        log.info("Remote Addr: {}", httpRequest.getRemoteAddr());

        // Header 로깅
        Enumeration<String> headerNames = httpRequest.getHeaderNames();
        while(headerNames.hasMoreElements()){
            String header = headerNames.nextElement();
            log.info("Header [{}]: {}", header, httpRequest.getHeader(header));
        }

        // Parameter 로깅
        Enumeration<String> paramNames = httpRequest.getParameterNames();
        while(paramNames.hasMoreElements()){
            String paramName = paramNames.nextElement();
            String[] paramValues = httpRequest.getParameterValues(paramName);
            for(String value : paramValues){
                log.info("Parameter [{}]: {}", paramName, value);
            }
        }
        log.info("============================================");

        // 다음 필터 또는 서블릿 호출
        chain.doFilter(request, response);
    }
}
