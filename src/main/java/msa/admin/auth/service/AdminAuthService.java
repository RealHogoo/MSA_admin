package msa.admin.auth.service;

import msa.admin.auth.vo.AdminUserVO;

public interface AdminAuthService {

    /**
     * 로그인 시도
     * @return 로그인 성공 시 사용자 정보(roles 포함), 실패 시 null
     */
    AdminUserVO login(String userId, String password);

}
