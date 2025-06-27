package com.idleItem.tradeSystem.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.idleItem.tradeSystem.utils.JwtUtils;
import com.idleItem.tradeSystem.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 跨域预检请求放行
        if ("OPTIONS".equals(request.getMethod())) {
            return true;
        }

        // 获取请求头中的token
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            try {
                // 验证token
                String username = jwtUtils.getUsernameFromToken(token);
                if (username != null) {
                    // 将用户信息设置到请求属性中，方便后续使用
                    request.setAttribute("userId", jwtUtils.getUserIdFromToken(token));
                    request.setAttribute("username", username);
                    request.setAttribute("roleCode", jwtUtils.getRoleCodeFromToken(token));
                    return true;
                }
            } catch (Exception e) {
                responseError(response, "无效的令牌");
                return false;
            }
        }

        responseError(response, "请先登录");
        return false;
    }

    private void responseError(HttpServletResponse response, String message) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        
        ResultVo resultVo = new ResultVo();
        resultVo.setMsg(message);
        resultVo.setStatus_code(401);
        
        ObjectMapper mapper = new ObjectMapper();
        response.getWriter().write(mapper.writeValueAsString(resultVo));
    }
} 