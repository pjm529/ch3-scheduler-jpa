package com.sparta.common.interceptor;

import com.sparta.common.utils.ServletUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.Enumeration;

@Slf4j
public class LoggingInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		log.info("====================Request Info====================");
		log.info("request.getRequestURI() : " + request.getRequestURI());
		log.info("request.getRequestURL() : " + request.getRequestURL());
		log.info("request.getServletPath() : " + request.getServletPath());
		log.info("request.getContextPath() : " + request.getContextPath());
		log.info("request.getPathInfo() : " + request.getPathInfo());
		log.info("request.getMethod() : " + request.getMethod());
		log.info("request.getRemoteAddr() : " + request.getRemoteAddr());
		log.info("request is Ajax : " + ServletUtils.isAjaxRequest());
		log.info("====================================================");
		log.info("====================Header Info=====================");

		Enumeration<String> headNames = request.getHeaderNames();
		while (headNames.hasMoreElements()) {
			String headName = headNames.nextElement();
			log.info("header ::: [" + headName + "] " + request.getHeader(headName));
		}

		log.info("====================Body Info=======================");
		Enumeration<String> enums = request.getParameterNames();

		while (enums.hasMoreElements()) {
			String paramName = enums.nextElement();
			String[] parameters = request.getParameterValues(paramName);

			for (String parameter : parameters) {
				log.info("parameter ::: [" + paramName + "] " + parameter);
			}
		}
		log.info("====================================================");
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}
}
