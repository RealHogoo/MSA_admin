package msa.admin.main.web;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import msa.admin.auth.vo.AdminUserVO;
import msa.admin.auth.web.LoginController;


@Controller
public class MainController {

    @RequestMapping("/main.do")
    public String main(HttpSession session, Model model) {
        AdminUserVO loginUser  = (AdminUserVO) session.getAttribute(LoginController.SESSION_KEY_LOGIN_USER);
        model.addAttribute("loginUser", loginUser);
        return "main/main";  // /WEB-INF/views/main/main.jsp
    }
}
