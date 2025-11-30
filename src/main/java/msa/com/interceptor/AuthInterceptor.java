package msa.com.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import msa.admin.auth.web.LoginController;


public class AuthInterceptor implements HandlerInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        String uri = request.getRequestURI();
        LOGGER.debug("AuthInterceptor URI = {}", uri);

        // 로그인/헬스/정적 리소스는 통과
        if (uri.endsWith("/login.do") ||
            uri.endsWith("/login.json") ||
            uri.endsWith("/logout.do") ||
            uri.endsWith("/health.do") ||
            uri.endsWith("/health.json") ||
            uri.startsWith(request.getContextPath() + "/static/")) {
            return true;
        }

        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute(LoginController.SESSION_USER_KEY) != null) {
            return true;
        }

        // 로그인 안 된 경우 login 페이지로 리다이렉트
        response.sendRedirect(request.getContextPath() + "/login.do");
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler,
                           ModelAndView modelAndView) throws Exception {
        // 필요하면 나중에 구현, 지금은 비워둬도 됨
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler,
                                Exception ex) throws Exception {
        // 요청 완료 후 처리할 내용이 있으면 나중에 구현
    }
}
