package msa.admin.auth.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import msa.admin.auth.service.AdminAuthService;
import msa.admin.auth.vo.AdminUserVO;


@Controller
public class LoginController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);
    public static final String SESSION_USER_KEY = "LOGIN_ADMIN";

    @Resource(name = "adminAuthService")
    private AdminAuthService adminAuthService;

    /**
     * 로그인 페이지
     */
    @RequestMapping("/login.do")
    public String loginForm(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute(SESSION_USER_KEY) != null) {
            // 이미 로그인 된 경우 - 나중에 메인 페이지로 리다이렉트
            return "redirect:/main.do";
        }
        return "auth/login";  // /WEB-INF/views/auth/login.jsp
    }

    /**
     * 로그인 처리 (JSON)
     */
    @RequestMapping(value = "/login.json", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String login(HttpServletRequest request) {
        String userId = request.getParameter("userId");
        String password = request.getParameter("password");

        LOGGER.info("로그인 시도: {}", userId);

        AdminUserVO user = adminAuthService.login(userId, password);
        if (user == null) {
            return "{\"success\":false,\"message\":\"아이디 또는 비밀번호가 올바르지 않습니다.\"}";
        }

        HttpSession session = request.getSession(true);
        session.setAttribute(SESSION_USER_KEY, user);

        // TODO: 나중에 여기서 JWT 발급도 가능
        return "{\"success\":true,\"message\":\"로그인 성공\",\"redirectUrl\":\"/main.do\"}";
    }

    /**
     * 로그아웃
     */
    @RequestMapping(value = "/logout.do")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/login.do";
    }
}
