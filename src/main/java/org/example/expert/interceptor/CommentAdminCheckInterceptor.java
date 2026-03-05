package org.example.expert.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.example.expert.domain.user.enums.UserRole;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class CommentAdminCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object handler) throws IOException{

        // 1. 현재 로그인한 사용자의 id와 역할 필요.
        Long userId = (Long) request.getAttribute("userId");
        String role = (String) request.getAttribute("userRole");

        log.info("현재 로그인 관리자 id={}, userRole={}", userId, role);

        // null 값 체크.
        if (role == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "인증이 필요합니다.");
            return false;
        }

        // 2. 로그인한 사용자가 권한이 있는지 검사 필요.
        UserRole userRole = UserRole.of(role);
        log.info("ADMIN 권한 성공 time={}, uri={}, userId={}",
                java.time.LocalDateTime.now(),
                request.getRequestURI(),
                userId);

        // 3. 일치하면 삭제, 업데이트 가능. -> 접근 허용, 일치하지 않으면 -> 접근 차단
        if (!UserRole.ADMIN.equals(userRole)) {

            log.warn("관리자 권한 없음 userId={}, role={}", userId, role);
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "관리자만 가능합니다.");
            return false;
        }

        // 4. ADMIN이면 가능.
        return true;
    }


}
