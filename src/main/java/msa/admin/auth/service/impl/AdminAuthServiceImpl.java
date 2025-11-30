package msa.admin.auth.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import msa.admin.auth.persistence.AdminAuthDAO;
import msa.admin.auth.service.AdminAuthService;
import msa.admin.auth.vo.AdminUserVO;


@Service("adminAuthService")
public class AdminAuthServiceImpl implements AdminAuthService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminAuthServiceImpl.class);

    @Resource(name = "adminAuthDAO")
    private AdminAuthDAO adminAuthDAO;

    @Override
    public AdminUserVO login(String userId, String password) {
        AdminUserVO user = adminAuthDAO.selectUserForLogin(userId);
        if (user == null) {
            LOGGER.info("로그인 실패 - 없는 사용자: {}", userId);
            return null;
        }

        // TODO: 나중에 BCrypt 등으로 변경
        if (!password.equals(user.getUserPw())) {
            LOGGER.info("로그인 실패 - 비밀번호 불일치: {}", userId);
            return null;
        }

        // 역할 목록 조회
        List<String> roles = adminAuthDAO.selectUserRoles(user.getUserId());
        user.setRoleList(roles);

        LOGGER.info("로그인 성공: {} / roles={}", userId, roles);
        return user;
    }
}
